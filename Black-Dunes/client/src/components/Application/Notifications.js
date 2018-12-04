import React, {Component} from 'react'
import {Col, Container, ListGroup, ListGroupItem, Row} from 'reactstrap'
import {request} from '../../api/api';

class Notifications extends Component {
    constructor(props) {
        super(props);
        this.state = {
            notifications: ['You suck Joe', 'You are the worst', 'Hope you are having fun! -Mom']
        };

        this.getNotifications = this.getNotifications.bind(this);
    }

    // componentDidMount() {
    //     this.getNotifications();
    // }

    componentWillReceiveProps(nextProps) {
        this.setState({
            error: nextProps.error
        });
    }

    async getNotifications() {
        let user = {
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
                                    <ListGroupItem>{notification}</ListGroupItem>
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