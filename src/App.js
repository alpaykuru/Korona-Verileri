import React, { Component } from 'react';
import Graphic from './graphic.js';
import KoronaForm from "./KoronaForm";

class App extends Component {
render() {
    return (
      <div>
      <KoronaForm />
      <Graphic />
      </div>
    );
  }
}
export default App;