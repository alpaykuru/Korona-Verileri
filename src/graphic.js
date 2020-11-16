import React from "react";
import {ResponsiveLine} from "@nivo/line";
import axios from 'axios';

import {Col, Button, Form, FormGroup, Label, Input, FormText, Card, CardBody, Row, Container, Badge} from 'reactstrap';


class Graphic extends React.Component {
    state = {selectedIl: '', kumulatif: false, graphData: [],currentCity:'Adana'};

    handleSubmit = async (event) => {
        event.preventDefault();
        const yeniData = {
            selectedIl: this.state.selectedIl,
            kumulatif: this.state.kumulatif
        };
        const resp = await axios.get('http://localhost:8080/api/coronaData/il', {
            params: {
                selectedIl: yeniData.selectedIl,
                kumulatif: yeniData.kumulatif
            }
        })
            .then((response) => {
                if (response.data !== "") {
                    this.setState({graphData: response.data})
                } else {
                    this.setState({graphData: []})
                }
                this.setState({currentCity: this.state.selectedIl})
            })
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit} style={{height: 350}} className="App">
            <Container className="themed-container">
                        <Row>
                            <Col sm="12" md={{ size: 6, offset: 3 }}>
                                <Badge color="info">{this.state.currentCity}</Badge>
                            </Col>
                        </Row>
                </Container>
                <ResponsiveLine
                    data={this.state.graphData}
                    margin={{top: 50, right: 110, bottom: 50, left: 60}}
                    xScale={{type: "point"}}
                    yScale={{type: "linear", stacked: false, min: 0, max: "auto"}}
                    axisTop={null}
                    axisRight={null}
                    axisBottom={{
                        orient: "bottom",
                        tickSize: 5,
                        tickPadding: 5,
                        tickRotation: 0,
                        legend: "Tarih",
                        legendOffset: 36,
                        legendPosition: "middle"
                    }}
                    axisLeft={{
                        orient: "left",
                        tickSize: 5,
                        tickPadding: 5,
                        tickRotation: 0,
                        legend: "Korona Verileri",
                        legendOffset: -40,
                        legendPosition: "middle"
                    }}
                    colors={{scheme: "nivo"}}
                    pointSize={10}
                    pointColor={{theme: "background"}}
                    pointBorderWidth={2}
                    pointBorderColor={{from: "serieColor"}}
                    pointLabel="y"
                    pointLabelYOffset={-12}
                    useMesh={true}
                    legends={[
                        {
                            anchor: "bottom-right",
                            direction: "column",
                            justify: false,
                            translateX: 100,
                            translateY: 0,
                            itemsSpacing: 0,
                            itemDirection: "left-to-right",
                            itemWidth: 80,
                            itemHeight: 20,
                            itemOpacity: 0.75,
                            symbolSize: 12,
                            symbolShape: "circle",
                            symbolBorderColor: "rgba(0, 0, 0, .5)",
                            effects: [
                                {
                                    on: "hover",
                                    style: {
                                        itemBackground: "rgba(0, 0, 0, .03)",
                                        itemOpacity: 1
                                    }
                                }
                            ]
                        }
                    ]}
                />
                <div>
                    <Card>
                        <CardBody>
                            <Form>
                                <Container className="themed-container">
                                    <Row>
                                        <Col sm="12" md={{size: 6, offset: 3}}>

                                            <Form inline>
                                                <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
                                                    <Label for="exampleEmail" className="mr-sm-2">İl Seçiniz</Label>
                                                    <Input type="select" name="select" id="exampleSelect"
                                                           value={this.state.selectedIl}
                                                           onChange={event => this.setState({selectedIl: event.target.value})}>
                                                        <option defaultValue value="Adana">Adana</option>
                                                        <option value="Adana">Adana</option>
                                                        <option value="Adana">Adana</option>
                                                        <option value="Adıyaman">Adıyaman</option>
                                                        <option value="Afyonkarahisar">Afyonkarahisar</option>
                                                        <option value="Ağrı">Ağrı</option>
                                                        <option value="Aksaray">Aksaray</option>
                                                        <option value="Amasya">Amasya</option>
                                                        <option value="Ankara">Ankara</option>
                                                        <option value="Antalya">Antalya</option>
                                                        <option value="Ardahan">Ardahan</option>
                                                        <option value="Artvin">Artvin</option>
                                                        <option value="Aydın">Aydın</option>
                                                        <option value="Balıkesir">Balıkesir</option>
                                                        <option value="Bartın">Bartın</option>
                                                        <option value="Batman">Batman</option>
                                                        <option value="Bayburt">Bayburt</option>
                                                        <option value="Bilecik">Bilecik</option>
                                                        <option value="Bingöl">Bingöl</option>
                                                        <option value="Bitlis">Bitlis</option>
                                                        <option value="Bolu">Bolu</option>
                                                        <option value="Burdur">Burdur</option>
                                                        <option value="Bursa">Bursa</option>
                                                        <option value="Çanakkale">Çanakkale</option>
                                                        <option value="Çankırı">Çankırı</option>
                                                        <option value="Çorum">Çorum</option>
                                                        <option value="Denizli">Denizli</option>
                                                        <option value="Diyarbakır">Diyarbakır</option>
                                                        <option value="Düzce">Düzce</option>
                                                        <option value="Edirne">Edirne</option>
                                                        <option value="Elazığ">Elazığ</option>
                                                        <option value="Erzincan">Erzincan</option>
                                                        <option value="Erzurum">Erzurum</option>
                                                        <option value="Eskişehir">Eskişehir</option>
                                                        <option value="Gaziantep">Gaziantep</option>
                                                        <option value="Giresun">Giresun</option>
                                                        <option value="Gümüşhane">Gümüşhane</option>
                                                        <option value="Hakkari">Hakkari</option>
                                                        <option value="Hatay">Hatay</option>
                                                        <option value="Iğdır">Iğdır</option>
                                                        <option value="Isparta">Isparta</option>
                                                        <option value="İstanbul">İstanbul</option>
                                                        <option value="İzmir">İzmir</option>
                                                        <option value="Kahramanmaraş">Kahramanmaraş</option>
                                                        <option value="Karabük">Karabük</option>
                                                        <option value="Karaman">Karaman</option>
                                                        <option value="Kars">Kars</option>
                                                        <option value="Kastamonu">Kastamonu</option>
                                                        <option value="Kayseri">Kayseri</option>
                                                        <option value="Kırıkkale">Kırıkkale</option>
                                                        <option value="Kırklareli">Kırklareli</option>
                                                        <option value="Kırşehir">Kırşehir</option>
                                                        <option value="Kilis">Kilis</option>
                                                        <option value="Kocaeli">Kocaeli</option>
                                                        <option value="Konya">Konya</option>
                                                        <option value="Kütahya">Kütahya</option>
                                                        <option value="Malatya">Malatya</option>
                                                        <option value="Manisa">Manisa</option>
                                                        <option value="Mardin">Mardin</option>
                                                        <option value="Mersin">Mersin</option>
                                                        <option value="Muğla">Muğla</option>
                                                        <option value="Muş">Muş</option>
                                                        <option value="Nevşehir">Nevşehir</option>
                                                        <option value="Niğde">Niğde</option>
                                                        <option value="Ordu">Ordu</option>
                                                        <option value="Osmaniye">Osmaniye</option>
                                                        <option value="Rize">Rize</option>
                                                        <option value="Sakarya">Sakarya</option>
                                                        <option value="Samsun">Samsun</option>
                                                        <option value="Siirt">Siirt</option>
                                                        <option value="Sinop">Sinop</option>
                                                        <option value="Sivas">Sivas</option>
                                                        <option value="Şanlıurfa">Şanlıurfa</option>
                                                        <option value="Şırnak">Şırnak</option>
                                                        <option value="Tekirdağ">Tekirdağ</option>
                                                        <option value="Tokat">Tokat</option>
                                                        <option value="Trabzon">Trabzon</option>
                                                        <option value="Tunceli">Tunceli</option>
                                                        <option value="Uşak">Uşak</option>
                                                        <option value="Van">Van</option>
                                                        <option value="Yalova">Yalova</option>
                                                        <option value="Yozgat">Yozgat</option>
                                                        <option value="Zonguldak">Zonguldak</option>
                                                    </Input>
                                                </FormGroup>
                                                <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
                                                    <Label check>
                                                        <Input type="checkbox"
                                                               onChange={event => this.setState({kumulatif: !this.state.kumulatif})}/>
                                                       Kümülatif
                                                    </Label>
                                                </FormGroup>
                                                <Button color="primary">Grafiği Güncelle</Button>
                                            </Form>


                                        </Col>
                                    </Row>
                                </Container>
                            </Form>
                        </CardBody>
                    </Card>

                </div>
            </form>
        );
    }
}

export default Graphic;