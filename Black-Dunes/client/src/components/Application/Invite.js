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

        this.handleSubmit = this.handleSubmit.bind(this)
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            error: nextProps.error
        });
    }

    async getUsers() {
        let updated = request('', 'getUsers');
        updated.then((values) => {
            this.formatUsers(values);
        })
    }

    formatUsers(values) {
        for (let i = 0; i < values.length; i++) {
            this.state.users.append({value: i, label: values[i]})
        }
    }

    handleSubmit() {
        if (this.state.selectedUsers.length === 0) {
            return this.setState({error: 'You must select at least one user'});
        }

        return this.setState({error: ''});
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
                                <Button sm={12} md={12} lg={12} type="submit" value="Register" data-test="submit" onClick={this.handleSubmit}>
                                    Submit
                                </Button>
                            </div>
                        </div>
                    </CardBody>
                </Card>
            </Container>
        );
    }
}

export default Invite;