import React, {Component} from 'react'
import {Container} from 'reactstrap'
import Info from './Info'
import Options from './Options'
import Calculator from './Calculator/Calculator'

import {get_config, request} from '../../api/api'
import Login from "./Login";

/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
    constructor(props) {
        super(props);
        this.state = {
            config: null,
            user: {},
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

    async updateLogin(username, password) {
        try {
            console.log("Asking for trips...");
            let stuff = await
                fetch(`http://localhost:4567/getTrips?num=all`);
            //console.log("Url:", `http://localhost:4567/getTrips?num=all`);
            let json = await
                stuff.json();
            let obj = {};
            json.forEach(elem => obj[elem.name] = elem);
            this.setState({
                user: obj,
            });
            console.log("Received trips", obj);
        }
        catch (e) {
            console.error(e);
        }
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
                    return <Login updateLogin={this.updateLogin}/>;
                default:
                    return <div/>;
            }

        return <div/>
    }
}

export default Application;