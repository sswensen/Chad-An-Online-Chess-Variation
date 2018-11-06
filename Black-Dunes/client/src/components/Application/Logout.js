import React, {Component} from 'react'
import {Container, Card, CardHeader, CardBody, FormGroup, Col, Label, Input, Form, Row} from 'reactstrap'
import {ButtonGroup, Button} from 'reactstrap'
import {request} from "../../api/api";

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Logout extends Component {
    constructor() {
        super();
        this.state = {
            error: '',
        };

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

        this.props.clearLogin();
        return this.setState({error: 'Successfully logged out'});
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

                <Button sm={6} md={2} lg={2}
                        type="submit" value="Log Out" data-test="submit">Logout?
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
                        <div className="Logout">
                            {this.create_input_fields('Logout')}

                        </div>
                    </CardBody>
                </Card>
            </Container>
        );
    }
}

export default Logout;