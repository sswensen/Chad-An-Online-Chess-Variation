import React, {Component} from 'react'
import {Container, Card, CardHeader, CardBody, Label} from 'reactstrap'

export default class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            messageDisplay: true
        }
    }

    dismissError() {
        this.props.setState({message: ''});
    }

    dispalyMessage() {
        if (this.state.messageDisplay) {
            return (
                <div className="messageDisplay">
                    <button onClick={this.setState({messageDisplay: false})}>✖</button>
                    {this.props.message}
                </div>
        );
        } else {
            return <div className="messageDisplay"></div>;
        }
    }
    render() {
        return (
            <div>
                <div>
                    <Label data-test="error" onClick={this.dismissError}>
                        {this.dispalyMessage()}
                    </Label>
                </div>
                <Container>
                    <Card>
                        <CardBody>
                            <p className="lead">"Do you even lift, bro?"</p>
                            <ul>
                                <li>
                                    Each player owns a 3x3 castle surrounded by 12 squares of walls.
                                </li>
                                <li>
                                    Rooks and queens move unimpeded by castles and walls.
                                </li>
                                <li>
                                    If a rook ends in an enemy castle, it is promoted to a Queen.
                                </li>
                                <li>
                                    The king can move and capture in the same way as a chess king or knight
                                </li>
                                <li>
                                    Kings cannot leave their castle and can capture any enemy piece in the castle.
                                </li>
                                <li>
                                    A rook or queen may capture an opponent's rook or queen only when one of these pieces is
                                    on the enemy's wall, and the other piece is in its own castle. This is the only scenario
                                    in which rooks and queens can capture.
                                </li>
                                <li>
                                    In other situations rooks and queens cannot capture, and simply block one another's
                                    movement.
                                </li>
                                <li>
                                    Castle walls do not block check
                                </li>
                                <li>
                                    Plan the trip with the options you selected.
                                    Review and revise the trip origin and order.
                                    Save the trip map and itinerary for future reference.
                                </li>
                            </ul>
                        </CardBody>
                    </Card>
                </Container>
            </div>
        )
    }
}