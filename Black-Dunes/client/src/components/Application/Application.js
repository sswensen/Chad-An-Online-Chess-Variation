import React, {Component} from 'react'
import {Container} from 'reactstrap'
import Info from './Info'
import Options from './Options'
import Calculator from './Calculator/Calculator'
import Login from "./Login"

import {get_config, request} from '../../api/api'

/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
    constructor(props) {
        super(props);
        this.state = {
            config: null,
            username: 'null',
            password: 'null',
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
        this.updateUsername = this.updateUsername.bind(this);
        this.updatePassword = this.updatePassword.bind(this);
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
        this.setState({'trip': value});
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

    async updateLogin() {
        console.log(this.state.username);
        console.log(this.state.password);
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
                    return <Login updateUsername={this.updateUsername} updatePassword={this.updatePassword} updateLogin={this.updateLogin}/>;
                default:
                    return <div/>;
            }

        return <div/>
    }
}

export default Application;