import React, {Component} from 'react'
import {Container, Card, CardHeader, CardBody, FormGroup, Col, Label, Input, Form, Row} from 'reactstrap'
import {ButtonGroup, Button} from 'reactstrap'
import {request} from "../../api/api";

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Register extends Component {
    constructor() {
        super();
        this.state = {
            username: '',
            nickname: '',
            email: '',
            password: '',
            error: '',
        };

        this.handlePassChange = this.handlePassChange.bind(this);
        this.handleUserChange = this.handleUserChange.bind(this);
        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.handleNicknameChange = this.handleNicknameChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.dismissError = this.dismissError.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            error: nextProps.error
        });
    }

    dismissError() {
        this.setState({error: ''});
    }

    handleSubmit(evt) {
        evt.preventDefault();

        if (!this.state.username) {
            return this.setState({error: 'Username is required'});
        }

        if (!this.state.password) {
            return this.setState({error: 'Password is required'});
        }

        if (!this.state.email) {
            return this.setState({error: 'Email is required'});
        }

        if (!this.state.nickname) {
            return this.setState({error: 'Nickname is required'});
        }

        this.props.registerUser(this.state.username, this.state.password, this.state.email, this.state.nickname);
        return this.setState({error: ''});
    }

    handleUserChange(evt) {
        this.setState({
            username: evt.target.value,
        });
    };

    handlePassChange(evt) {
        this.setState({
            password: evt.target.value,
        });
    }

    handleEmailChange(evt) {
        this.setState({
            email: evt.target.value,
        })
    }

    handleNicknameChange(evt) {
        this.setState({
            nickname: evt.target.value,
        });
    }

    create_input_fields(title) {
        return (
            <Form inline onSubmit={this.handleSubmit}>
                {
                    this.state.error &&
                        <Col md={12} lg={12}>
                            <Label data-test="error" onClick={this.dismissError}>
                                <button onClick={this.dismissError}>✖</button>
                                    {this.state.error}
                            </Label>
                        </Col>

                }
                <div>
                    <div>
                        <Label>{title}</Label>
                    </div>
                    <div>
                        <Input placeholder="Username" type="text"
                               data-test="username" value={this.state.username}
                               onChange={this.handleUserChange}/>
                    </div>
                    <div>
                        <Input placeholder="Password" type="password"
                               data-test="password" value={this.state.password}
                               onChange={this.handlePassChange}/>
                    </div>
                    <div>
                        <Input placeholder="Email" type="text"
                               data-test="Email" value={this.state.email}
                               onChange={this.handleEmailChange}/>
                    </div>
                    <div>
                        <Input placeholder="Nickname" type="text"
                               data-test="Nickname" value={this.state.nickname}
                               onChange={this.handleNicknameChange}/>
                    </div>
                    <div>
                        <Button
                                type="submit" value="register" data-test="submit">Submit
                        </Button>
                    </div>
                </div>


        </Form>
    );
    }

    render() {
        // NOTE: I use data-attributes for easier E2E testing
        // but you don't need to target those (any css-selector will work)

        return (
            <Container>
                <Card>
                    <CardBody>
                        <div className="Register">
                            {this.create_input_fields('Register')}
                        </div>
                    </CardBody>
                </Card>
            </Container>
    );
    }
}

export default Register;