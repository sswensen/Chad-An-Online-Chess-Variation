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
        this.handleAcceptSubmit = this.handleAcceptSubmit.bind(this);
        this.getUsers = this.getUsers.bind(this);
        this.sendInvites = this.sendInvites.bind(this);
    }

    componentDidMount() {
        this.getUsers();
        this.getInvites();
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            message: nextProps.message
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
                return this.setState({
                    message: 'Invites sent!',
                    selectedUsers: []
                });
            } else {
                return this.setState({message: 'Sending invites failed'});
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
                    message: '',
                    invitations: values
                });
            } else {
                return this.setState({message: 'Getting invites failed'});
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
            return this.setState({message: 'You must select at least one user'});
        } else {
            this.sendInvites();
            return true;
        }
    }

    async handleAcceptSubmit(invitationID) {
        const body = {
            type: 'accept',
            inviteID: invitationID.toString()
        };
        let updated = request(body, 'invitationInteraction');
        updated.then((value) => {
           if (value) {
               alert('The game was accepted!');
               this.getInvites();
           }
        });
    }

    async handleRejectSubmit(invitationID) {
        const body = {
            type: 'reject',
            inviteID: invitationID.toString()
        };
        let updated = request(body, 'invitationInteraction');
        updated.then((value) => {
            if (value) {
                alert('The invite has been successfully rejected');
                this.getInvites();
            }
        })
    }

    async handleCancelSubmit(invitationID) {
        const body = {
            type: 'cancel',
            inviteID: invitationID.toString()
        };
        let updated = request(body, 'invitationInteraction');
        updated.then((value) => {
            if (value) {
                alert('The invite has been successfully cancelled');
                this.getInvites();
            }
        })
    }

    getInvitationRow(invitation) {
        if (invitation['user1ID'] === this.props.userID) {
            return (
                <tr>
                    <td>{invitation['user2ID']}</td>
                    <td>
                        <Button
                            outline color="secondary"
                            size="sm"
                            onClick={() => {this.handleCancelSubmit(invitation['notificationID'])}}>
                            Cancel
                        </Button>
                    </td>
                </tr>
            );
        } else {
            return (
                <tr>
                    <td>{invitation['user1ID']}</td>
                    <td>
                        <Button
                            className="accept-button"
                            outline color="success"
                            size="sm"
                            onClick={() => {this.handleAcceptSubmit(invitation['notificationID'])}}>
                            Accept
                        </Button>
                        <Button
                            outline color="danger"
                            size="sm"
                            onClick={() => {this.handleRejectSubmit(invitation['notificationID'])}}>
                            Reject
                        </Button>
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
                                        data-test="submit" onClick={() => {this.handleInviteSubmit()}}>
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