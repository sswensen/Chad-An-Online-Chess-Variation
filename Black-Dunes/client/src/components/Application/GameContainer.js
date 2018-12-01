import React, {Component} from 'react'
import {Container, Card, CardHeader, CardBody} from 'reactstrap'
import './Game/index.css';
import Game from "./Game/components/game";

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class GameContainer extends Component{
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <Container>
                <Card>
                    <CardBody>
                        <Game />
                    </CardBody>
                </Card>
            </Container>
        )
    }
}

export default GameContainer;