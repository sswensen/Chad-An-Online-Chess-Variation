import React, { Component } from 'react'
import { Container, Row, Col } from 'reactstrap'
import { Card, CardHeader, CardBody } from 'reactstrap'
import { Button } from 'reactstrap'
import { Form, FormGroup, Label, Input } from 'reactstrap'

export default class Calculator
  extends Component {
  constructor(props) {
    super(props);
  }

  create_input_fields(title) {
    return (
      <Form inline>
              <Col md={4} lg={2}>
                <Label>{title}</Label>
              </Col>
              <Col sm={6} md={4} lg={4}>
                <Input style={{width: "100%"}} placeholder="Latitude"/>
              </Col>
              <Col sm={6} md={4} lg={4}>
                <Input style={{width: "100%"}} placeholder="Longitude"/>
              </Col>
      </Form>
      );
  }

  render() {
    const distance = 0;

    return (
      <Container>
          {this.create_input_fields('First Location')}
          {this.create_input_fields('Second Location')}

          <Card>
            <CardBody>
              <Row>
                <Col md={6}>
                  <Button>Calculate</Button>
                </Col>
                <Col md={6}>
                  {distance} {this.props.unit}
                </Col>
              </Row>
            </CardBody>
          </Card>
      </Container>
    )
  }
}