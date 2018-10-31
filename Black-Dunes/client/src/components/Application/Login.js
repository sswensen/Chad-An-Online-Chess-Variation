import React, {Component} from 'react'
import {Container, Card, CardHeader, CardBody, FormGroup} from 'reactstrap'
import {ButtonGroup, Button} from 'reactstrap'
import Options from "./Options";

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: ""
        };
    }

    validateForm() {
        return this.state.email.length > 0 && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    handleSubmit = event => {
        event.preventDefault();
        this.props.updateLogin(this.state.email, this.state.password)
    };

    render() {
        return (
            <Container>
                <Card>
                    <CardBody>
                        <div className="Login">
                            <form onSubmit={this.handleSubmit}>
                                <FormGroup controlId="email" bsSize="large">
                                    <ControlLabel>Email</ControlLabel>
                                    <FormControl
                                        autoFocus
                                        type="email"
                                        value={this.state.email}
                                        onChange={this.handleChange}
                                    />
                                </FormGroup>
                                <FormGroup controlId="password" bsSize="large">
                                    <ControlLabel>Password</ControlLabel>
                                    <FormControl
                                        value={this.state.password}
                                        onChange={this.handleChange}
                                        type="password"
                                    />
                                </FormGroup>
                                <Button
                                    block
                                    bsSize="large"
                                    disabled={!this.validateForm()}
                                    type="submit"
                                >
                                    Login
                                </Button>
                            </form>
                        </div>
                    </CardBody>
                </Card>
            </Container>
        );
    }

    render() {
        const buttons = this.props.config.units.map((unit) =>
            <Button
                key={'distance_button_' + unit}
                className='btn-outline-dark unit-button'
                active={this.props.options.unit === unit}
                value={unit}
                onClick={(event) => this.props.updateLogin('unit', event.target.value)}
            >
                {unit.charAt(0).toUpperCase() + unit.slice(1)}
            </Button>
        );

        return (
            <Container>
                <Card>
                    <CardBody>
                        <p>Select the options you wish to use.</p>
                        <ButtonGroup>
                            {buttons}
                        </ButtonGroup>
                    </CardBody>
                </Card>
            </Container>
        )
    }
}

export default Login;