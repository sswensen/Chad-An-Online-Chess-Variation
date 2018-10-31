import React, {Component} from 'react';
import './format.css'
import Header from './Marginals/Header';
import Application from './Application/Application';
import Footer from './Marginals/Footer';
import { HashRouter as Router, Route, Switch, Redirect} from 'react-router-dom'
import { TransitionGroup, CSSTransition } from 'react-transition-group'

class App extends Component {
  constructor (props){
    super(props);
    this.pages = [
      { title: 'T00 TripCo', page: 'home', link: '/'},
      { title: 'Calculator', page: 'calc', link: '/calculator'},
        { title: 'Options', page: 'options', link: '/options' },
        { title: 'Login', page: 'login', link: '/login' }
    ]
  }

  reactiveRouter(routes) {
    return (
      <Router>
        <div id="App">
          <Route render={({ location }) => (
            <div>
              <Header pages={this.pages}/>
              <TransitionGroup>
                <CSSTransition
                  key={location.pathname}
                  appear
                  timeout={{enter:900, exit:0}}
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

  render() {
    const routes = this.pages.map( (element) =>
        <Route exact path={element['link']} key={"route_".concat(element['page'])}
          render={() => <Application page={element['page']}/>}/>
      );
    return ( <div> { this.reactiveRouter(routes) } </div> )
  }
}

export default App;
