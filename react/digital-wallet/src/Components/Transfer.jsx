import React, { Component } from 'react';
import axios from 'axios';
import Footer from './Footer'
import Navigation from './Navigation'
import { Button, Modal, Alert } from 'react-bootstrap';

export default class Transfer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fromCVU: '',
            toCVU: '', //157250770 de prueba
            amount: 0,
            firstNameTo: "",
            lastNameTo: "",
            cvuClass: "form-control",
            amountClass: "form-control",
            setShowModal: false,
            alert: {
                show: false,
                variant: "danger",
                message: ""
            },
            currentUser: localStorage.user

        };
        this.handleTransfer = this.handleTransfer.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        axios.get(`http://localhost:7000/users/user/${this.state.currentUser}`)
            .then((response) => {
                this.setState({
                    fromCVU: response.data.cvu
                })
            }).catch((error) => {
                alert(error.response.data.message);
            })
    }

    handleChange(value, prop) {
        this.setState(prevState => ({ ...prevState, [prop]: value }));
    }

    handleGetNameTo = (ev) => {
        this.verifyCVU(ev, this.state.toCVU);
        this.verifyAmount(ev, this.state.amount);

        if (this.state.fromCVU === this.state.toCVU) {
            this.setState({
                setShowModal: false,
                alert: {
                    show: true,
                    variant: "danger",
                    message: "You cant transfer money to yourself!"
                }
            })
        } else {
            axios.get(`http://localhost:7000/users/userByCVU/${this.state.toCVU}`)
                .then((response) => {
                    this.setState({
                        firstNameTo: response.data.firstName,
                        lastNameTo: response.data.lastName,
                        setShowModal: true,
                        alert: {
                            show: false
                        }
                    })
                }).catch((error) => {
                    this.setState({
                        setShowModal: false,
                        alert: {
                            show: true,
                            variant: "danger",
                            message: "Invalid user. Please, verify CVU number"
                        }
                    })
                })
        }
    }

    handleTransfer = (ev) => {
        ev.preventDefault();
        this.setState({
            setShowModal: false,
        })

        axios.post(`http://localhost:7000/transfer`, {
            fromCVU: this.state.fromCVU,
            toCVU: this.state.toCVU,
            amount: this.state.amount
        }).then((response) => {
            this.setState({
                alert: {
                    show: true,
                    variant: "success",
                    message: response.data
                }
            })
        }).catch((error) => {
            this.setState({
                alert: {
                    show: true,
                    variant: "danger",
                    message: error.response.data.message
                }
            })
        })
    }

    verifyCVU = (ev, value) => {
        ev.preventDefault();
        if (value.length !== 9) {
            this.setState({
                cvuClass: "form-control is-invalid",
            })
        } else {
            this.setState({
                cvuClass: "form-control is-valid",
            })
        }
    }

    verifyAmount = (ev, value) => {
        ev.preventDefault();
        if (value === "" || value <= 0) {
            this.setState({
                amountClass: "form-control is-invalid",
            })
        } else {
            this.setState({
                amountClass: "form-control is-valid",
            })
        }
    }

    render() {
        const handleClose = () => this.setState({
            setShowModal: false,
        })
        return (
            <div className="App">
                <>
                    <Modal show={this.state.setShowModal} onHide={handleClose} animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title>Are you sure?</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            Are you sure you want to transfer {this.state.amount} USD
                            to {this.state.firstNameTo} {this.state.lastNameTo} CVU {this.state.toCVU}? <b>This decision
                            cannot be undone.</b>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={handleClose}>
                                Close
                            </Button>
                            <Button variant="primary" onClick={(ev) => this.handleTransfer(ev)}>
                                Yes, transfer!
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </>
                <Navigation id={this.state.currentUser} />

                <header className="App-header">

                    <div className="container col-4">
                        <div className="row justify-content-center">
                            <div className="col-md-6">
                                <h3 className="text-primary"> Transfer </h3>
                            </div>
                        </div>
                        <hr style={{ color: '#0275d8', backgroundColor: '#0275d8', height: 1 }} />
                        <div>
                            <div className="form-group">
                                <label>CVU</label>
                                <input type="number"
                                    id="CVU"
                                    onBlur={(ev) => this.verifyCVU(ev, ev.target.value)}
                                    className={this.state.cvuClass}
                                    placeholder="Enter CVU to transfer (9 digits)"
                                    onChange={event => this.handleChange(event.target.value, 'toCVU')}></input>
                                <div className="invalid-feedback">
                                    The cvu must have 9 digits.
                                </div>
                                <small id="emailHelp" className="form-text text-muted">We'll never share your transfer
                                    with
                                    anyone else.</small>
                            </div>
                            <div className="form-group">
                                <label>Amount</label>
                                <input
                                    onBlur={(ev) => this.verifyAmount(ev, ev.target.value)}
                                    id="amount"
                                    type="number"
                                    className={this.state.amountClass}
                                    placeholder="Enter amount"
                                    onChange={event => this.handleChange(event.target.value, 'amount')}></input>
                                <div className="invalid-feedback">
                                    The amount cannot be empty or negative.
                                </div>
                            </div>
                            <button onClick={(ev) => this.handleGetNameTo(ev)} className="btn btn-primary">Transfer! <i
                                className="far fa-paper-plane"></i></button>
                        </div>
                    </div>
                    <br></br>
                    <div className="row">
                        <div className="col-xs-12">
                            <Alert variant={this.state.alert.variant} show={this.state.alert.show}>
                                {this.state.alert.message}
                            </Alert>
                        </div>
                    </div>
                </header>

                <Footer />
            </div>

        );
    }
}