import React, { Component } from 'react';
import axios from 'axios';

class Form extends React.Component {
 state = { text: '',sonuc: '' };

   handleSubmit = async (event) => {
    event.preventDefault();
    const yeniData = {
      text: this.state.text
    };

    const resp = await axios.get('http://localhost:8080/api/coronaData/add', {
    params: {
      text: yeniData
    }
  })
 .then( (response) => {
    this.setState({sonuc: response.data.sonuc})
    alert(response.data.sonuc);
  })
    this.setState({ text: '' });
  };

	render() {
  	return (
    	<form onSubmit={this.handleSubmit}>
      <span className="formtext">Korona Grafiği</span>
      <label>Haber giriniz:  
        <textarea id="w3review" name="w3review" rows="4" cols="50"
          value={this.state.text}
          onChange={event => this.setState({ text: event.target.value })}
          required 
        ></textarea>
      </label>
        <button>Yükle!</button>
    	</form>
    );
  }
}
export default Form;