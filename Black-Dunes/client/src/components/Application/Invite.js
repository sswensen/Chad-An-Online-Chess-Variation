import React, {Component} from 'react'
import Select from 'react-select'
import {Button, Card, CardBody, Container} from 'reactstrap'
import {request} from "../../api/api";

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Invite extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [
                {value: 'freddy', label: 'Freddy'},
                {value: 'sly', label: 'Sly Ry'},
                {value: 'abby', label: 'Abby'},
                {value: 'sandy', label: 'Sandy'}
            ],
            selectedUsers: [],
        };

        this.handleInviteSubmit = this.handleInviteSubmit.bind(this)
    }

    // componentDidMount() {
    //     const users  = this.getUsers();
    //     this.setState({users: users})
    // }

    componentWillReceiveProps(nextProps) {
        this.setState({
            error: nextProps.error
        });
    }

    async getUsers() {
        let updated = request(this.props.userID, 'getUsers');
        updated.then((values) => {
            Invite.formatUsers(values);
            return true;
        });
        return false;
    }

    async sendInvites() {
        let updated = request({selectedUsers: this.state.selectedUsers}, 'sendInvites');
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
        for (let user in values) {
            users.append({value: user.userID, label: user.nickName});
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
                <Card>
                    <CardBody>
                        <div>
                            <h3>Invite Users</h3>
                        </div>
                        <div className="Invite">
                            <div className="UserSelection">
                                <Select
                                    closeMenuOnSelect={false}
                                    options={this.state.users}
                                    isMulti
                                    onChange={(selected) => {this.setState({selectedUsers: selected})}}
                                />
                                <Button sm={12} md={12} lg={12} type="submit" value="Register" data-test="submit" onClick={this.handleInviteSubmit}>
                                    Submit
                                </Button>
                            </div>
                            <div className = "UserInvites">
                                {this.listUserInvites()}
                            </div>
                        </div>
                    </CardBody>
                </Card>
            </Container>
        );
    }
}

export default Invite;