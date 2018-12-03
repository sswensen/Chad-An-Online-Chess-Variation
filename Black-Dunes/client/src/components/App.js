import React, {Component} from 'react';
import './format.css'
import Header from './Marginals/Header';
import Application from './Application/Application';
import Footer from './Marginals/Footer';
import {HashRouter as Router, Route, Switch, Redirect} from 'react-router-dom'
import {TransitionGroup, CSSTransition} from 'react-transition-group'
import {get_config, request} from '../api/api';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            auth: -1,
            pages: [
                {title: 'Home', page: 'home', link: '/'},
                {title: 'Game', page: 'game', link: '/game'},
                {title: 'Login', page: 'login', link: '/login'},
                {title: 'Register', page: 'register', link: '/register'}
            ],
            config: null,
            userID: '-1',
            error: '',
            username: 'null',
            password: 'null',
        };

        this.updateAuth = this.updateAuth.bind(this);
        this.updateBasedOnResponse = this.updateBasedOnResponse.bind(this);
        this.updateLogin = this.updateLogin.bind(this);
        this.clearLogin = this.clearLogin.bind(this);
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
        this.updateAuth(value);
    }

    }

    updatePassword(pass) {
        this.setState({password: pass})
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
        this.updateAuth(-1);
        window.location = './#/login';
    }


    reactiveRouter(routes) {
        return (
            <Router>
                <div id="App">
                    <Route render={({location}) => (
                        <div>
                            <Header pages={this.state.pages} auth={this.state.auth}/>
                            <TransitionGroup>
                                <CSSTransition
                                    key={location.pathname}
                                    appear
                                    timeout={{enter: 900, exit: 0}}
                                    classNames='pagefade'
                                >
                                    <Switch location={location}>
                                        {routes}
                                    </Switch>
                                </CSSTransition>
                            </TransitionGroup>
                            <Footer/>
                        </div>
                    )}/>
                </div>
            </Router>
        )
    }

    updateAuth(auth) {
        this.setState({
            auth: auth,
        });
        if(auth > -1) {
            this.setState({
                pages: [
                    {title: 'Home', page: 'home', link: '/'},
                    {title: 'Game', page: 'game', link: '/game'},
                    {title: 'Logout', page: 'logout', link: '/logout'},
                    {title: 'Register', page: 'register', link: '/register'}
                ]
            });
            return <Redirect to='/login'  />
        } else {
            this.setState({
                pages: [
                    {title: 'Home', page: 'home', link: '/'},
                    {title: 'Game', page: 'game', link: '/game'},
                    {title: 'Login', page: 'login', link: '/login'},
                    {title: 'Register', page: 'register', link: '/register'}
                ]
            })
        }
    }

    render() {
        const childInformation = {
            error: this.state.error,
            updateUsername: this.updateUsername,
            updatePassword: this.updatePassword,
            updateLogin: this.updateLogin,
            clearLogin: this.clearLogin
        };
        const routes = this.state.pages.map((element) =>
            <Route exact path={element['link']} key={"route_".concat(element['page'])}
                   render={() => <Application page={element['page']} info={childInformation}/>}/>
        );
        return (<div> {this.reactiveRouter(routes)} </div>)
    }
}

export default App;