import React, {Component} from 'react'
import {Container} from 'reactstrap'
import Info from './Info'
import Options from './Options'
import Calculator from './Calculator/Calculator'
import Login from "./Login"

import {get_config, request} from '../../api/api'
import Logout from "./Logout";
import Register from "./Register";

/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
    constructor(props) {
        super(props);
        this.state = {
            config: null,
            userID: '-1',
            error: '',
            username: 'null',
            password: 'null',
            email: 'null',
            nickname: 'null',
            trip: {
                type: "trip",
                title: "",
                options: {
                    unit: "miles"
                },
                places: [],
                distances: [],
                map: '<svg width="1920" height="20" xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg"><g></g></svg>'
            }
        };
        this.updateTrip = this.updateTrip.bind(this);
        this.updateBasedOnResponse = this.updateBasedOnResponse.bind(this);
        this.updateOptions = this.updateOptions.bind(this);
        this.updateLogin = this.updateLogin.bind(this);
        this.registerUser = this.registerUser.bind(this);
        this.clearLogin = this.clearLogin.bind(this);
        this.updateUsername = this.updateUsername.bind(this);
        this.updatePassword = this.updatePassword.bind(this);
        this.updateEmail = this.updateEmail.bind(this);
        this.updateNickname = this.updateNickname.bind(this);
    }

    componentWillMount() {
        get_config().then(
            config => {
                this.setState({
                    config: config
                })
            }
        );
    }

    updateTrip(field, value) {
        let trip = this.state.trip;
        trip[field] = value;
        this.setState(trip);
    }

    updateBasedOnResponse(value) {
        //console.log("User ID Returned from database is " + value);
        if (value > -1) {
            this.setState({
                'userID': value,
                'error': 'Logged in successfully!'
            });
            //window.location = './'; // This actually does a refresh which is what we don't want because it clears the userID
            window.location = './#';
        } else {
            this.setState({'error': 'Invalid username or password!'})
        }
        this.props.updateAuth(value);
    }

    updateOptions(option, value) {
        let trip = this.state.trip;
        trip.options[option] = value;
        this.setState(trip);
    }

    updateUsername(user) {
        this.setState({username: user});
    }

    updatePassword(pass) {
        this.setState({password: pass})
    }

    updateEmail(email) {
        this.setState({email: email});
    }

    updateNickname(nickname) {
        this.setState({nickname: nickname})
    }

    async registerUser(username, password, email, nickname) {
        //console.log(username);
        //console.log(password);
        let user = {
            username: username,
            password: password,
            email: email,
            nickname: nickname
        };

        let updated = request(user, 'register');
        updated.then((values) => {
            this.updateBasedOnResponse(values)
            console.log(values);
        });
    }

    async updateLogin(username, password) {
        //console.log(username);
        //console.log(password);
        let user = {
            username: username,
            password: password
        };

        let updated = request(user, 'login');
        updated.then((values) => {
            this.updateBasedOnResponse(values)
        });
    }

    clearLogin() {
        this.setState({
            'userID': -1,
            'error': 'Logged out successfully!'
        });
        this.props.updateAuth(-1);
        window.location = './#/login';
    }

    render() {
        if (this.state.config)
            switch (this.props.page) {
                case 'home':
                    return <Info/>;
                case 'calc':
                    return <Calculator unit={this.state.trip.options.unit}/>;
                case 'options':
                    return <Options options={this.state.trip.options} config={this.state.config}
                                    updateOptions={this.updateOptions}/>;
                case 'login':
                    return <Login error={this.state.error} updateUsername={this.updateUsername}
                                  updatePassword={this.updatePassword} updateLogin={this.updateLogin}/>;
                case 'logout':
                    return <Logout clearLogin={this.clearLogin}/>;
                case 'register':
                    return <Register error={this.state.error}
                                     updateUsername={this.updateUsername}
                                     updatePassword={this.updatePassword}
                                     updateEmail={this.updateEmail}
                                     updateNickname={this.updateNickname}
                                     registerUser={this.registerUser}/>;
                default:
                    return <div/>;
            }

        return <div/>
    }
}

export default Application;