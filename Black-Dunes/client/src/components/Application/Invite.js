import React, {Component} from 'react'
import Select from 'react-select'
import {Button, Card, CardBody, Col, Container, Row} from 'reactstrap'
import {request} from '../../api/api';

class Invite extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            selectedUsers: []
        };

        this.handleInviteSubmit = this.handleInviteSubmit.bind(this);
        this.getUsers = this.getUsers.bind(this);
        this.sendInvites = this.sendInvites.bind(this);
    }

    componentDidMount() {
        this.getUsers();
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
        const userIDs = this.state.selectedUsers.map(user => user.value);
        let updated = request({userIDs: userIDs}, 'sendInvites');
        updated.then((values) => {
           if (values) {
               return this.setState({error: ''})
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

    listUserInvites() {
        return <li/>;
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
                                    onChange={(selected) => {this.setState({selectedUsers: selected})}}
                                />
                                <Button className="send-invite" sm={12} md={12} lg={12} type="submit" value="Register" data-test="submit" onClick={this.handleInviteSubmit}>
                                    Submit
                                </Button>
                            </div>
                        </div>
                    </Col>
                    <Col>
                        <div className = "user-invites">
                            <h3>Edit Invites</h3>
                            {this.listUserInvites()}
                        </div>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default Invite;