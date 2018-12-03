import React, {Component} from 'react'
import {Container, Card, CardHeader, CardBody, FormGroup, Col, Label, Input, Form, Row} from 'reactstrap'
import {ButtonGroup, Button} from 'reactstrap'
import {request} from "../../api/api";

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Login extends Component {
    constructor() {
        super();
        this.state = {
            username: '',
            password: '',
            error: '',
        };

        this.handlePassChange = this.handlePassChange.bind(this);
        this.handleUserChange = this.handleUserChange.bind(this);
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

        this.props.updateLogin(this.state.username, this.state.password);
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

                <Col md={2} lg={2}>
                    <Label>{title}</Label>
                </Col>
                <Col sm={6} md={4} lg={4}>
                    <Input style={{width: "100%"}} placeholder="Username" type="text"
                           autoComplete="off"
                           data-test="username" value={this.state.username}
                           onChange={this.handleUserChange}/>
                </Col>
                <Col sm={6} md={4} lg={4}>
                    <Input style={{width: "100%"}} placeholder="Password" type="password"
                           autoComplete="off"
                           data-test="password" value={this.state.password}
                           onChange={this.handlePassChange}/>
                </Col>

                <Button sm={6} md={2} lg={2}
                        type="submit" value="Log In" data-test="submit">Submit
                </Button>
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
                        <div className="Login">
                            {this.create_input_fields('Login')}

                        </div>
                    </CardBody>
                </Card>
            </Container>
        );
    }
}

export default Login;