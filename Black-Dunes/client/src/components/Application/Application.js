import React, {Component} from 'react'
import Home from './Home'
import Game from './Game/components/Game'
import Login from './Login'
import Logout from './Logout';

/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
    render() {
        switch (this.props.page) {
            case 'home':
                return <Home/>;
            case 'game':
                return <Game />;
            case 'login':
                return <Login error={this.props.info['error']} updateUsername={this.props.info['updateUsername']}
                                  updatePassword={this.props.info['updatePassword']} updateLogin={this.props.info['updateLogin']}/>;
            case 'logout':
                return <Logout clearLogin={this.props.info['clearLogin']}/>;
            default:
                return <div/>;
            }
    }
}

export default Application;