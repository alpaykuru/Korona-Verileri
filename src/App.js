import React, { Component } from 'react';
import './App.css';
import Form from './form.js';
import Graphic from './graphic.js';
import '../node_modules/react-linechart/dist/styles.css';

class App extends Component {
render() {
    return (
      <div className="App">
      <Form />
      <Graphic />
      </div>
    );
  }
}
export default App;