/*
  DO NOT TOUCH THIS FILE UNLESS YOU KNOW WHAT YOU ARE DOING.
  I DON'T EVEN UNDERSTAND IT AND I MADE THE THING!
*/

import React from 'react'
import ReactDOM from 'react-dom'
import { AppContainer } from 'react-hot-loader'
import App from './components/App.js';

const render = Component => {
  ReactDOM.render(
    <AppContainer>
      <Component />
    </AppContainer>,
    document.getElementById('root')
  )
}

render(App);

if (module.hot) {
  module.hot.accept('./components/App.js', () => {
      render(App)
      render(require('./components/App.js'))
  })
}
