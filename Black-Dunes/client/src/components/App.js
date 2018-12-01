import React, {Component} from 'react';
import './format.css'
import Header from './Marginals/Header';
import Application from './Application/Application';
import Footer from './Marginals/Footer';
import {HashRouter as Router, Route, Switch, Redirect} from 'react-router-dom'
import {TransitionGroup, CSSTransition} from 'react-transition-group'

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            auth: -1,
            pages: [
                {title: 'Home', page: 'home', link: '/'},
                {title: 'Calculator', page: 'calc', link: '/calculator'},
                {title: 'Options', page: 'options', link: '/options'},
                {title: 'Game', page: 'game', link: '/game'},
                {title: 'Login', page: 'login', link: '/login'}
            ]
        };
        this.updateAuth = this.updateAuth.bind(this);

        // this.pages = [
        //     {title: 'Home', page: 'home', link: '/'},
        //     {title: 'Calculator', page: 'calc', link: '/calculator'},
        //     {title: 'Options', page: 'options', link: '/options'},
        //     // (this.state.auth > -1 ? {
        //     //     title: 'Logout',
        //     //     page: 'logout',
        //     //     link: '/logout'
        //     // } : {
        //     //
        //     //     title: 'Login',
        //     //     page: 'login',
        //     //     link: '/login'
        //     // }),
        //     //{ title: 'Login', page: 'login', link: '/login' }
        //     { title: (this.state.auth > -1 ? 'Logout' : 'Login'), page: 'login', link: '/login' }
        // ];
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
        //console.log("Auth updated to " + auth);
        this.setState({
            auth: auth,
        });
        if(auth > -1) {
            this.setState({
                pages: [
                    {title: 'Home', page: 'home', link: '/'},
                    {title: 'Calculator', page: 'calc', link: '/calculator'},
                    {title: 'Options', page: 'options', link: '/options'},
                    {title: 'Game', page: 'game', link: '/game'},
                    {title: 'Logout', page: 'logout', link: '/logout'}
                ]
            });
            return <Redirect to='/login'  />
        } else {
            this.setState({
                pages: [
                    {title: 'Home', page: 'home', link: '/'},
                    {title: 'Calculator', page: 'calc', link: '/calculator'},
                    {title: 'Options', page: 'options', link: '/options'},
                    {title: 'Game', page: 'game', link: '/game'},
                    {title: 'Login', page: 'login', link: '/login'}
                ]
            })
        }
        //(this.state.auth > -1 ? 'Logout' : 'Login')
    }

    render() {
        //console.log("[App] Auth is: " + this.state.auth);
        const routes = this.state.pages.map((element) =>
            <Route exact path={element['link']} key={"route_".concat(element['page'])}
                   render={() => <Application page={element['page']} updateAuth={this.updateAuth}/>}/>
        );
        return (<div> {this.reactiveRouter(routes)} </div>)
    }
}

export default App;
