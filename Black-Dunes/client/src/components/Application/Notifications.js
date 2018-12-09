import React, {Component} from 'react'
import {Col, Container, ListGroup, ListGroupItem, Row} from 'reactstrap'
import {request} from '../../api/api';

class Notifications extends Component {
    constructor(props) {
        super(props);
        this.state = {
            notifications: []
        };

        this.getNotifications = this.getNotifications.bind(this);
    }

    componentDidMount() {
        this.getNotifications();
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            error: nextProps.error
        });
    }

    async getNotifications() {
        let user = {
            notificationType: 'notification',
            userID: this.props.userID
        };
        let updated = request(user, 'getNotifications');
        updated.then((values) => {
            this.setState({notifications: values});
        });
        return this.setState({notifications: []})
    }

    render() {
        return (
            <Container>
                <Row>
                    <Col>
                        <div>
                            <h3>Notifications</h3>
                        </div>
                        <div className="notifications">
                            <ListGroup>
                                {this.state.notifications.map((notification) => (
                                    <ListGroupItem>{notification['message']}</ListGroupItem>
                                ))}
                            </ListGroup>
                        </div>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default Notifications;