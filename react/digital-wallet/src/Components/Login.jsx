import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import axios from 'axios';
import { Button, Form, Alert } from "react-bootstrap";
import '../dist/css/Login.css';
import Footer from './Footer'
import logo from '../dist/img/DigitalWalletLogo.png';

/**
 * @author Fabian Frangella
 * Component for login
 */
class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            idCard: '',
            validate: false,
            alert: {
                show: false,
                variant: "danger",
                message: ""
            }
        };
        this.handleLogin = this.handleLogin.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleRegister = this.handleRegister.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    /**
     * function used to request a login to the API and access the app
     * @param {Event} ev
     */
    handleLogin = (ev) => {
        ev.preventDefault();
        axios.post(`http://localhost:7000/login`, {
            email: this.state.email,
            password: this.state.password,
        }).then((response) => {
            axios.get(`http://localhost:7000/users/user-id/${this.state.email}`)
                .then((response) => {
                    localStorage.setItem("user", response.data.idCard)
                    this.props.history.push('/account', { idCard: response.data.idCard })
                })
        }).catch((error) => {
            if (this.state.email !== '' && this.state.password !== '') {
                this.setState({
                    alert: {
                        show: true,
                        variant: "danger",
                        message: error.response.data.message
                    }
                })
            }
        })
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
     * function used to redirect the user to the register page
     */
    handleRegister() {
        this.props.history.push('/register');
    }

    /**
     * function used to validate the login form and send the login request to the API
     * @param {Event} event 
     */
    handleSubmit(event) {
        this.handleLogin(event)
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        this.setState({ validated: true });

    };

    /**
     * render the login page
     */
    render() {
        return (
            <div className="Login">
                <div className="row justify-content-center">
                    <img className="logo" src={logo} alt="DigitalWallet Logo"></img>
                </div>
                <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                    <Form.Group controlId="email">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            required
                            autoFocus
                            type="email"
                            placeholder="Enter your E-Mail"
                            value={this.state.email}
                            onChange={event => this.handleChange(event.target.value, 'email')}
                        />
                        <Form.Control.Feedback type="invalid">
                            This is a required field.
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="password">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            required
                            type="password"
                            value={this.state.password}
                            onChange={event => this.handleChange(event.target.value, 'password')}
                            placeholder="Enter your password"
                        />
                        <Form.Control.Feedback type="invalid">
                            This is a required field.
                        </Form.Control.Feedback>
                        <br></br>
                        <Alert variant={this.state.alert.variant} show={this.state.alert.show}>
                            {this.state.alert.message}
                        </Alert>
                    </Form.Group>
                    <Button block="large" type="submit">
                        Login
                    </Button>
                    <Button variant="secondary" block="large" onClick={(ev) => this.handleRegister(ev)}>
                        Register
                    </Button>
                </Form>
                <div className="App">
                    <Footer />
                </div>
            </div>
        );
    }
}

export default withRouter(Login)

