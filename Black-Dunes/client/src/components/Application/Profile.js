import React, {Component} from 'react'
import {Col, Container, Row} from 'reactstrap'
import {request} from "../../api/api";

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            info: {},
            games: [],
            error: ''
        };

        this.getProfile = this.getProfile.bind(this);
        this.getGames = this.getGames.bind(this);
    }

    componentWillMount() {
        this.getProfile();
        this.getGames();
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            error: nextProps.error
        });
    }

    async getProfile() {
        let updated = request({userID: this.props.userID}, 'getProfile');
        updated.then((values) => {
            console.log(values);
            this.setState({
                info: values
            })
        });
    }

    async getGames() {
        let update = request({userID: this.props.userID}, 'getGames');
        update.then((value => {
            console.log(value);
            this.setState({
                games: value
            })
        }));
    }

    render() {
        console.log(this.state.info['userID']);
        return (
            <Container>
                <Row>
                    <Col>
                        <h3>Profile</h3>
                        <table>
                            <tbody>
                                <tr>
                                    <th>UserID</th>
                                    <th>Nickname</th>
                                    <th>Email</th>
                                    <th>Wins</th>
                                    <th>Losses</th>
                                </tr>
                                <tr>
                                    <td>{this.state.info['userID']}</td>
                                    <td>{this.state.info['nickname']}</td>
                                    <td>{this.state.info['email']}</td>
                                    <td>{this.state.info['wins']}</td>
                                    <td>{this.state.info['losses']}</td>
                                </tr>
                            </tbody>
                        </table>
                    </Col>
                    <Col>
                        <div>
                            <h3>Games</h3>
                        </div>
                        <table>
                            <tbody>
                                <tr>
                                    <th>GameId</th>
                                    <th>UserID</th>
                                    <th>OpponentID</th>
                                </tr>
                                {this.state.games.map((row) => (
                                    <tr>
                                        <td>{row[0]}</td>
                                        <td>{row[1]}</td>
                                        <td>{row[2]}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default Profile;