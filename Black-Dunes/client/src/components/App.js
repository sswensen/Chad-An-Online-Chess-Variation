import React, {Component} from 'react';
import './format.css'
import Header from './Marginals/Header';
import Application from './Application/Application';
import FooterComponent from './Marginals/FooterComponent';
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
                {title: 'Register', page: 'register', link: '/register'},
                {title: 'Login', page: 'login', link: '/login'}
            ],
            config: null,
            userID: '-1',
            error: '',
            username: 'null',
            password: 'null',
            nickname: 'null'
        };

        this.updateAuth = this.updateAuth.bind(this);
        this.updateLogin = this.updateLogin.bind(this);
        this.clearLogin = this.clearLogin.bind(this);
        this.updateUsername = this.updateUsername.bind(this);
        this.updatePassword = this.updatePassword.bind(this);
        this.updateEmail = this.updateEmail.bind(this);
        this.updateNickname = this.updateNickname.bind(this);
        this.registerUser = this.registerUser.bind(this);
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

    updateLoginBasedOnResponse(value) {
        if (value != null && value["userID"] > -1) {
            this.setState({
                'userID': value["userID"],
                'nickname': value["nickName"],
                'error': 'Logged in successfully!'
            });
            window.location = './#';
        } else {
            this.setState({'error': 'Invalid username or password!'})
        }
        this.updateAuth(value);
    }

    updateRegisterBasedOnResponse(value) {
        if (value > -1) {
            this.setState({
                userID: value,
                'error': 'Registered successfully!'
            });
            window.location = './#';
        } else {
            this.setState({'error': 'Please try again. Duplicate !'}) // TODO
        }
        this.updateAuth(value);
    }

    updateUsername(user) {
        this.setState({username: user});
    }

    updatePassword(pass) {
        this.setState({password: pass});
    }

    updateEmail(email) {
        this.setState({email: email});
    }

    updateNickname(nickname) {
        this.setState({nickname: nickname})
    }

    async updateLogin(username, password) {
        let user = {
            username: username,
            password: password
        };

        let updated = request(user, 'login');
        updated.then((values) => {
            this.updateLoginBasedOnResponse(values)
        });
    }

    async registerUser(username, password, email, nickname) {
        let user = {
            username: username,
            password: password,
            email: email,
            nickname: nickname
        };

        let updated = request(user, 'register');
        updated.then((values) => {
            this.updateRegisterBasedOnResponse(values);
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
                        <div className="outer-container">
                            <Header pages={this.state.pages} auth={this.state.auth}/>
                            <TransitionGroup className="inner-content">
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
                            <FooterComponent/>
                        </div>
                    )}/>
                </div>
            </Router>
        )
    }

    updateAuth(auth) {
        this.setState({
            auth: auth["userID"],
        });
        if (auth["userID"] > -1) {
            this.setState({
                pages: [
                    {title: 'Home', page: 'home', link: '/'},
                    {title: 'Profile', page: 'profile', link: '/profile'},
                    {title: 'Notifications', page: 'notifications', link: '/notifications'},
                    {title: 'Invite', page: 'invite', link: '/invite'},
                    {title: 'Game', page: 'game', link: '/game'},
                    {title: 'Logout', page: 'logout', link: '/logout'},
                ]
            });
            return <Redirect to='/login'/>
        } else {
            this.setState({
                pages: [
                    {title: 'Home', page: 'home', link: '/'},
                    {title: 'Register', page: 'register', link: '/register'},
                    {title: 'Login', page: 'login', link: '/login'}
                ]
            })
        }
    }

    render() {
        const childInformation = {
            userID: this.state.userID,
            nickname: this.state.nickname,
            error: this.state.error,
            updateUsername: this.updateUsername,
            updatePassword: this.updatePassword,
            updateLogin: this.updateLogin,
            clearLogin: this.clearLogin,
            updateEmail: this.updateEmail,
            updateNickname: this.updateNickname,
            registerUser: this.registerUser
        };
        const routes = this.state.pages.map((element) =>
            <Route exact path={element['link']} key={"route_".concat(element['page'])}
                   render={() => <Application page={element['page']} info={childInformation}/>}/>
        );
        return (<div> {this.reactiveRouter(routes)} </div>)
    }
}

export default App;