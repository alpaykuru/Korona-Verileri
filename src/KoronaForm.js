import React, {Component} from 'react';
import axios from 'axios';

import { Col, Button, Form, FormGroup, Label, Input, FormText, Card, CardBody, Row, Container, Badge, Alert } from 'reactstrap';


class KoronaForm extends React.Component {
    state = {text: '', sonuc: ''};

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
            .then((response) => {
                this.setState({sonuc: response.data.sonuc})
                alert(response.data.sonuc);
            })
        this.setState({text: ''});
    };

    render() {
        return (
            <div>
                <Container className="themed-container">
                        <Row>
                            <Col sm="12" md={{ size: 6, offset: 3 }}>
                                <h3>Korona Verileri</h3>
                            </Col>
                        </Row>
                </Container>
                <Card>
                    <CardBody>
                        <Form>
                            <form onSubmit={this.handleSubmit}>
                                <Container>
                                    <Row>
                                        <Col sm="12" md={{ size: 6, offset: 3 }}>
                                            <FormGroup>
                                                <Label for="exampleText">Haber Giriniz</Label>
                                                <Input type="textarea" id="w3review" name="w3review" rows="4" cols="50"
                                                       value={this.state.text}
                                                       onChange={event => this.setState({text: event.target.value})}
                                                       required />
                                            </FormGroup>
                                            <Button color="success">Kaydet</Button>
                                        </Col>
                                    </Row>
                                </Container>
                            </form>
                        </Form>
                    </CardBody>
                </Card>
            </div>
        );
    }
}

export default KoronaForm;