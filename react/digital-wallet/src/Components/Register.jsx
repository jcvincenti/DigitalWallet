import React, { Component } from 'react';
import axios from 'axios';
import { Button, Alert, InputGroup, Form } from "react-bootstrap";
import '../dist/css/Register.css';
import Footer from './Footer'
import logo from '../dist/img/DigitalWalletLogo.png';

/**
 * @author Fabian Frangella 
 * Component for register
 */
export default class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            idCard: '',
            validated: false,
            alert : {
                show: false,
                variant: "danger",
                message: ""
            }
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleRegister = this.handleRegister.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this)
        this.validateEmail = this.validateEmail.bind(this)
    }

     /**
     * function used to bind the values of inputs to the component state
     * @param {*} value 
     * @param {prop} prop 
     */
    handleChange(value, prop) {
        this.setState(prevState => ({ ...prevState, [prop]: value }));
    }


    /**
     * function used to perform a register request to the API
     * @param {Event} ev 
     */
    handleRegister(ev) {
        ev.preventDefault()
        axios.post(`http://localhost:7000/register`, {
            email: this.state.email,
            firstName: this.state.firstName,
            idCard: this.state.idCard,
            lastName: this.state.lastName,
            password: this.state.password
        }).then((response) => {
            if (response.status === 201) {
                this.props.history.push('/');
            }
        }).catch((error) => {
            this.setState({
                alert: {
                    show: true,
                    variant: "danger",
                    message: error.response.data.message===undefined ? "Please complete all fields" : error.response.data.message    
                }
            })
        })
    }

    /**
     * function used to validate an email address
     * @param {String} email 
     */
    validateEmail(email){
        const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    /**
     * function used to validate the register form and send the register request to the API
     * @param {event} event 
     */
    handleSubmit(event) {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }

        this.setState({ validated: true });

        if (this.validateEmail(this.state.email)){
            this.handleRegister(event)
        }  
    };

    render() {
        return (
            <div className="Register">
                <div className="row justify-content-center">
                    <img className="logo" src={logo} alt="DigitalWallet Logo"></img>
                </div>
                <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                    <Form.Group controlId="email">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            required
                            type="email"
                            placeholder="Enter your E-Mail"
                            value={this.state.email}
                            onChange={event => this.handleChange(event.target.value, 'email')}
                        />
                        <Form.Control.Feedback type="invalid">
                            This is a required field.
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="firstName">
                        <Form.Label>First Name</Form.Label>
                        <Form.Control
                            required
                            type="text"
                            value={this.state.firstName}
                            onChange={event => this.handleChange(event.target.value, 'firstName')}
                            placeholder="Enter your First Name"
                        />
                        <Form.Control.Feedback type="invalid">
                            This is a required field.
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="lastName">
                        <Form.Label>Last Name</Form.Label>
                        <InputGroup>
                            <Form.Control
                                required
                                type="text"
                                value={this.state.lastName}
                                onChange={event => this.handleChange(event.target.value, 'lastName')}
                                placeholder="Enter your Last Name"
                            />
                            <Form.Control.Feedback type="invalid">
                                This is a required field.
                            </Form.Control.Feedback>
                        </InputGroup>
                    </Form.Group>
                    <Form.Group controlId="idCard">
                        <Form.Label>ID Card</Form.Label>
                        <InputGroup>
                            <Form.Control
                                required
                                type="number"
                                value={this.state.idCard}
                                onChange={event => this.handleChange(event.target.value, 'idCard')}
                                placeholder="Enter your ID Card"
                            />
                            <Form.Control.Feedback type="invalid">
                                This is a required field.
                            </Form.Control.Feedback>
                        </InputGroup>
                    </Form.Group>
                    <Form.Group>
                        <Form.Group controlId="password">
                            <Form.Label>Password</Form.Label>
                            <InputGroup>
                                <Form.Control
                                    required
                                    type="text"
                                    value={this.state.password}
                                    onChange={event => this.handleChange(event.target.value, 'password')}
                                    type="password"
                                    placeholder="*********"
                                />
                                <Form.Control.Feedback type="invalid">
                                    This is a required field.
                            </Form.Control.Feedback>
                            </InputGroup>
                        </Form.Group>
                        <Alert variant={this.state.alert.variant} show={this.state.alert.show}>
                            {this.state.alert.message}
                        </Alert>
                    </Form.Group>
                    <Button block="large" type="submit">
                        Register
                    </Button>
                    <Button block="large" href="/" variant="secondary">
                        Back to Login
                    </Button>
                </Form>
                <div className="App">
                    <Footer />
                </div>
            </div>
        );

    }
}