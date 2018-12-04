import React, {Component} from 'react'
import Select from 'react-select'
import {Button, Col, Container, Row, Table} from 'reactstrap'
import {request} from '../../api/api';

class Invite extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            selectedUsers: [],
            invitations: []
        };

        this.handleInviteSubmit = this.handleInviteSubmit.bind(this);
        this.getUsers = this.getUsers.bind(this);
        this.sendInvites = this.sendInvites.bind(this);
    }

    componentDidMount() {
        this.getUsers();
        this.getInvites();
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            error: nextProps.error
        });
    }

    async getUsers() {
        let user = {
            userID: this.props.userID
        };
        let updated = request(user, 'getUsers');
        updated.then((values) => {
            this.setState({users: Invite.formatUsers(values)});
        });
        return this.setState({users: []})
    }

    async sendInvites() {
        const body = {
            senderID: this.props.userID,
            userIDs: this.state.selectedUsers.map(user => user.value)
        };
        let updated = request(body, 'sendInvites');
        updated.then((values) => {
            if (values) {
                return this.setState({error: ''});
            } else {
                return this.setState({error: 'Sending invites failed'});
            }
        });
    }

    async getInvites() {
        const body = {
            notificationType: 'invitation',
            userID: this.props.userID
        };
        let updated = request(body, 'getNotifications');
        updated.then((values) => {
            if (values) {
                return this.setState({
                    error: '',
                    invitations: values
                });
            } else {
                return this.setState({error: 'Sending invites failed'});
            }
        });
    }

    static formatUsers(values) {
        let users = [];
        for (let i = 0; i < values.length; i++) {
            users.push({value: values[i]['userID'], label: values[i]['nickname']});
        }
        return users;
    }

    handleInviteSubmit() {
        if (this.state.selectedUsers.length === 0) {
            return this.setState({error: 'You must select at least one user'});
        } else {
            this.sendInvites();
            return this.setState({error: ''});
        }
    }

    getInvitationRow(invitation) {
        if (invitation['user1ID'] === this.props.userID) {
            return (
                <tr>
                    <td>{invitation['user2ID']}</td>
                    <td>
                        <Button outline color="secondary" size="sm">Cancel</Button>
                    </td>
                </tr>
            );
        } else {
            return (
                <tr>
                    <td>{invitation['user1ID']}</td>
                    <td>
                        <Button className="accept-button" outline color="success" size="sm">Accept</Button>
                        <Button outline color="danger" size="sm">Reject</Button>
                    </td>
                </tr>
            );
        }
    }

    render() {
        return (
            <Container>
                <Row>
                    <Col>
                        <div>
                            <h3>Invite to New Game</h3>
                        </div>
                        <div className="invite-users">
                            <div className="user-selection">
                                <Select
                                    closeMenuOnSelect={false}
                                    options={this.state.users}
                                    isMulti
                                    onChange={(selected) => {
                                        this.setState({selectedUsers: selected})
                                    }}
                                />
                                <Button className="send-invite" sm={12} md={12} lg={12} type="submit" value="Register"
                                        data-test="submit" onClick={this.handleInviteSubmit}>
                                    Submit
                                </Button>
                            </div>
                        </div>
                    </Col>
                    <Col>
                        <div>
                            <h3>Invitation Interaction</h3>
                        </div>
                        <Table>
                            <thead>
                            <tr>
                                <th>Opponent</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.state.invitations.map((invitation => (
                                this.getInvitationRow(invitation)
                            )))}
                            </tbody>
                        </Table>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default Invite;