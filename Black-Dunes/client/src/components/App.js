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
  }

  reactiveRouter(routes) {
    return (
      <Router>
        <div id="App">
          <Route render={({ location }) => (
            <div>
              <Header/>
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
    const routes = [
        <Route exact path={'/'} key="route_home" render={() => <Application page={'home'}/>} />
    ]
    return ( <div> { this.reactiveRouter(routes) } </div> )
  }
}

export default App;
