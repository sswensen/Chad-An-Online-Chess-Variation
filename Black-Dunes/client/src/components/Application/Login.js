import React, {Component} from 'react'
import {Container, Card, CardHeader, CardBody, FormGroup} from 'reactstrap'
import {ButtonGroup, Button} from 'reactstrap'
import {request} from "../../api/api";

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Login extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <form onSubmit={this.props.updateLogin()}>
                <label>
                    Username:

                    <input type="host" placeholder="localhost" onChange={(event) => {this.props.updateUsername(event.target.value)}} />
                </label>
                <br/>
                <label>
                    Password:

                    <input type="port" placeholder="8088" onChange={(event) => {this.props.updatePassword(event.target.value)}} />
                </label>
                <input type="submit" value="Submit"/>
            </form>
        );
    }
}

export default Login;