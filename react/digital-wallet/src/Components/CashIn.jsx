import React, { Component } from 'react';
import axios from 'axios';
import Footer from './Footer'
import Navigation from './Navigation'
import {Alert, Modal, Button} from 'react-bootstrap';


export default class Transfer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cvu: "",
            amount: 0,
            cardNumber: 0,
            cardNumberWSpaces: "",
            fullName: "",
            securityCode: 0,
            endDate: "",
            cardType: "debit",
            amountClass: "form-control",
            cardNumberClass: "form-control",
            securityCodeClass: "form-control",
            fullNameClass: "form-control",
            endDateClass: "form-control",
            setShowModal: false,
            alert : {
                show: false,
                variant: "danger",
                message: ""
            },
            currentUser: localStorage.user
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleCashIn = this.handleCashIn.bind(this);
    }

    handleAmount = (ev, value) =>{
        ev.preventDefault();
        if(value === "" || value <= 0){
            this.setState({amountClass: "form-control is-invalid"});
        }else{
            this.setState({amountClass: "form-control is-valid"});
        }
    }

    handleCard = (ev, value) =>{
        ev.preventDefault();
        if(value.length === 16){
            this.setState({cardNumberClass: "form-control is-valid"});
        }else{
            this.setState({cardNumberClass: "form-control is-invalid"});
        }
    }

    handleSecurityCode = (ev, value) =>{
        ev.preventDefault();
        if(value.length !== 3 || isNaN(value)){
            this.setState({securityCodeClass: "form-control is-invalid"});
        }else{
            this.setState({securityCodeClass: "form-control is-valid"});
        }
    }

    handleFullName = (ev, value) =>{
        ev.preventDefault();
        if(value === ""){
            this.setState({fullNameClass: "form-control is-invalid"});
        }else{
            this.setState({fullNameClass: "form-control is-valid"});
        }
    }

    validateCardNumber = () => {return this.state.cardNumber.length === 16;}
    validateSecurityCode = () => {return !(isNaN(this.state.securityCode)) && this.state.securityCode.length === 3;}
    validateFullName = () => {return this.state.fullName !== "";}
    validateEndDate = () => {
        const re = /^(0[1-9]|1[012])[/](20)\d\d$/;
        return re.test(this.state.endDate);
    }

    handleEndDate = (ev, value) =>{
        ev.preventDefault();
        const re = /^(0[1-9]|1[012])[/](20)\d\d$/;
        if(re.test(value)){
            this.setState({endDateClass: "form-control is-valid"});
        }else{
            this.setState({endDateClass: "form-control is-invalid"});
        }
    }

    handleOpen = (ev) => {
    ev.preventDefault();
    if(this.validateCardNumber() && this.validateSecurityCode() && this.validateFullName() && this.validateEndDate()){
        this.setState({
            setShowModal: true,
            alert:{
                show:false
            }
        })
    }else{
        this.setState({
            alert: {
                show: true,
                variant: "danger",
                message: "Please check all the fields."
            }})
    }
    }

    handleClose = () => {
        this.setState({
            setShowModal: false
        })
    }

    handleChange(value, prop) {
            this.setState(prevState => ({ ...prevState, [prop]: value }));
    }

    handleCashIn = (ev) => {
        ev.preventDefault();
        var modifiedCardNumber = this.state.cardNumber;
            while (modifiedCardNumber > 0)
                    {
                        this.state.cardNumberWSpaces = this.state.cardNumberWSpaces + " " + modifiedCardNumber.substring(0,4);
                        modifiedCardNumber =   modifiedCardNumber.substring(4);// Trim String
                    }
            axios.get(`http://localhost:7000/users/user/${this.state.currentUser}`)
                        .then(response => {
                            this.setState({cvu: response.data.cvu});
                            axios.post(`http://localhost:7000/cashIn`, {
                                        cvu: this.state.cvu,
                                        amount: this.state.amount,
                                        cardNumber: this.state.cardNumberWSpaces,
                                        fullName: this.state.fullName,
                                        endDate: this.state.endDate,
                                        securityCode: this.state.securityCode,
                                        cardType: this.state.cardType
                                    }).then((response) => {
                                        this.setState({
                                        setShowModal: false,
                                        alert: {
                                            show: true,
                                            variant: "success",
                                            message: response.data,
                                        }})
                                    }).catch((error) => {
                                        this.setState({
                                        setShowModal: false,
                                        alert: {
                                            show: true,
                                            variant: "danger",
                                            message: error.response.data.message
                                        }})
                                    })
                        }
            )
    }

    render() {
        return (
        <div className="App">
                <>
                    <Modal show={this.state.setShowModal} onHide={this.handleClose} animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title>Are you sure?</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            Are you sure you want to cash in {this.state.amount} USD
                            to your account? <b>This decition cannot be undone.</b>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleClose}>
                                Close
                            </Button>
                            <Button variant="primary" onClick={(ev) => this.handleCashIn(ev)}>
                                Yes, transfer!
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </>
            <Navigation id = {this.state.currentUser}/>
            <header className="App-header">

            <div className = "container">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <h3 className="text-primary"> Cash In </h3>
                </div>
            </div>
            <hr style={{color: '#0275d8', backgroundColor: '#0275d8', height: 1}} />
              <form onSubmit={this.handleOpen}>
                  <div className="row">
                      <div className="form-group col-md-6">
                        <label>Amount</label>
                        <div className="input-group">
                           <div className="input-group-prepend">
                                <div className="input-group-text">
                                    <i className="fa fa-money"></i>
                                </div>
                           </div>
                            <input type="number"
                                className={this.state.amountClass}
                                onBlur={ev => this.handleAmount(ev, ev.target.value)}
                                placeholder="Enter the amount of money"
                                onChange={event => this.handleChange(event.target.value, 'amount')}>
                            </input>
                            <div className="invalid-feedback">
                                The amount cannot be empty or negative.
                            </div>
                        </div>
                      </div>
                      <div className="form-group col-md-6 d-flex align-items-center justify-content-center">
                        <div className="row">
                            <div className="form-check form-check-inline col-md-5">
                              <input
                                className="form-check-input"
                                type="radio"
                                name="exampleRadios"
                                id="tarjetaDebito"
                                value='debit'
                                checked={this.state.cardType === 'debit'}
                                onChange={event => this.handleChange(event.target.value, 'cardType')}>
                              </input>
                              <label className="form-check-label">
                                Debit
                              </label>
                            </div>
                            <div className="form-check form-check-inline col-md-5">
                              <input
                                className="form-check-input"
                                type="radio"
                                name="exampleRadios"
                                id="tarjetaCredito"
                                value='credit'
                                checked={this.state.cardType === 'credit'}
                                onChange={event => this.handleChange(event.target.value, 'cardType')}>
                              </input>
                              <label className="form-check-label">
                                Credit
                              </label>
                            </div>
                        </div>
                      </div>
                  </div>
                  <div className="row">
                      <div className="form-group col-md-6">
                        <label>Card Number</label>
                        <div className="input-group">
                           <div className="input-group-prepend">
                                <div className="input-group-text">
                                    <i className="far fa-credit-card"></i>
                                </div>
                           </div>
                            <input type= "number"
                                 className={this.state.cardNumberClass}
                                 placeholder="Enter your card number"
                                 onBlur={ev => this.handleCard(ev, ev.target.value)}
                                 onChange={event => this.handleChange(event.target.value, 'cardNumber')}>
                            </input>
                            <div className="invalid-feedback">
                                The card must have 16 digits.
                            </div>
                        </div>
                      </div>
                      <div className="form-group col-md-6">
                        <label>Security Code</label>
                        <div className="input-group">
                           <div className="input-group-prepend">
                                <div className="input-group-text">
                                    <i className="far fa-credit-card"></i>
                                </div>
                           </div>
                            <input type= "password"
                                className={this.state.securityCodeClass}
                                placeholder="Security code (3 digit number)"
                                onBlur={ev => this.handleSecurityCode(ev, ev.target.value)}
                                onChange={event => this.handleChange(event.target.value, 'securityCode')}>
                            </input>
                            <div className="invalid-feedback">
                                3 digit number at the back of your card.
                            </div>
                        </div>
                      </div>
                  </div>
                  <div className="row">
                      <div className="form-group col-md-6">
                        <label>Full Name</label>
                        <div className="input-group">
                            <div className="input-group-prepend">
                                <div className="input-group-text">
                                    <i className="fa fa-address-card"></i>
                                </div>
                            </div>
                            <input type= "text"
                                className={this.state.fullNameClass}
                                placeholder="Enter the card's full name"
                                onBlur={ev => this.handleFullName(ev, ev.target.value)}
                                onChange={event => this.handleChange(event.target.value, 'fullName')}>
                            </input>
                            <div className="invalid-feedback">
                                The name cannot be empty.
                            </div>
                        </div>
                      </div>
                      <div className="form-group col-md-6">
                        <label>End Date</label>
                        <div className="input-group">
                            <div className="input-group-prepend">
                                <div className="input-group-text">
                                    <i className="fa fa-calendar"></i>
                                </div>
                            </div>
                            <input type= "text"
                                className={this.state.endDateClass}
                                placeholder="Use the MM/YYYY format"
                                onBlur={ev => this.handleEndDate(ev, ev.target.value)}
                                onChange={event => this.handleChange(event.target.value, 'endDate')}>
                            </input>
                            <div className="invalid-feedback">
                                Use the MM/YYYY format.
                            </div>
                        </div>
                      </div>
                  </div>
                  <button type="submit" onSubmit={ev => this.handleOpen(ev)} className="btn btn-primary">Cash In <i className="far fa-paper-plane"></i></button>
              </form>
              <br/>
              <div className="row justify-content-center">
                <div className="col-xs-12">
                    <Alert variant={this.state.alert.variant} show={this.state.alert.show}>
                        {this.state.alert.message}
                    </Alert>
                </div>
              </div>
            </div>
            </header>
            <Footer />
          </div>

        );
    }
}