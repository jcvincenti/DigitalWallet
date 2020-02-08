import React, {Component} from 'react';
import axios from 'axios';
import Footer from './Footer'
import Navigation from './Navigation'
import {Alert,Modal,Button} from 'react-bootstrap';


export default class Account extends Component {
    constructor(props) {
        super(props);
        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            cvu: '',
            amount: 0,
            firstNameClass: "form-control",
            lastNameClass: "form-control",
            emailClass: "form-control",
            setShowModal: false,
            alert: {
                show: false,
                variant: "danger",
                message: ""
            },
            currentUser: localStorage.user
        };
        this.handleAccount = this.handleAccount.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        axios.get(`http://localhost:7000/users/user/${this.state.currentUser}`)
            .then((response) => {
                this.setState({
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    email: response.data.email,
                    cvu: response.data.cvu
                })
                axios.get(`http://localhost:7000/account/${this.state.cvu}`)
                    .then((response) => {
                        this.setState({
                            amount: response.data.amount
                        })
                    })
            })
    }

    handleChange(value, prop) {
        this.setState(prevState => ({...prevState, [prop]: value}));
    }

    validate = () => (this.state.firstName.length >= 1) &
        (this.state.lastName.length >= 1) & this.regedixEmail(this.state.email)

    handleAccount = (ev) => {
        ev.preventDefault();
        if (this.validate()) {
            axios.post(`http://localhost:7000/users/userData/${this.state.currentUser}`, {
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                email: this.state.email
            }).then((response) => {
                this.setState({
                    alert: {
                        show: true,
                        variant: "success",
                        message: "Changes saved successfully"
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
        } else {
            this.setState({
                alert: {
                    show: true,
                    variant: "danger",
                    message: "Please check the red fields."
                }
            })
        }
        this.setState({setShowModal: false})
    }
    verifyFirstName = (ev, value) => {
        ev.preventDefault();
        if (value.length >= 1) {
            this.setState({
                firstNameClass: "form-control is-valid"
            })
        } else {
            this.setState({
                firstNameClass: "form-control is-invalid"
            })
        }
    }

    verifyLastName = (ev, value) => {
        ev.preventDefault();
        if (value.length >= 1) {
            this.setState({
                lastNameClass: "form-control is-valid"
            })
        } else {
            this.setState({
                lastNameClass: "form-control is-invalid"
            })
        }
    }
    regedixEmail = (mail) => /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(mail);

    verifyEmail = (ev, value) => {
        ev.preventDefault();
        if (this.regedixEmail(value)) {
            this.setState({
                emailClass: "form-control is-valid"
            })
        } else {
            this.setState({
                emailClass: "form-control is-invalid"
            })
        }
    }

    showModal(){
        this.setState({setShowModal: true})
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
                            Are you sure you want to change your personal information?
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={handleClose}>
                                Close
                            </Button>
                            <Button variant="primary" onClick={(ev) => this.handleAccount(ev)}>
                                Yes
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </>
                <Navigation id={this.state.currentUser}/>
                <header className="App-header">
                    <div className="container col-8">
                        <div className="row justify-content-center">
                            <div className="col-md-6">
                                <h3 className="text-primary"> Account </h3>
                            </div>
                        </div>
                        <hr style={{color: '#0275d8', backgroundColor: '#0275d8', height: 1}}/>
                       
                            <div className="row">
                                <div className="form-group col-md-6">
                                    <label>First Name</label>
                                    <input type="text"
                                           onBlur={(ev) => this.verifyFirstName(ev, ev.target.value)}
                                           className={this.state.firstNameClass}
                                           placeholder= "Enter your First Name"
                                           value={this.state.firstName}
                                           onChange={event => this.handleChange(event.target.value, 'firstName')}>
                                    </input>
                                    <div className="invalid-feedback">
                                        First Name can only contain letters and cannot be empty.
                                    </div>
                                </div>
                                <div className="form-group col-md-6">
                                    <label>Last Name</label>
                                    <input type="text"
                                           onBlur={(ev) => this.verifyLastName(ev, ev.target.value)}
                                           className={this.state.lastNameClass}
                                           placeholder= "Enter your Last Name"
                                           value={this.state.lastName}
                                           onChange={event => this.handleChange(event.target.value, 'lastName')}>
                                    </input>
                                    <div className="invalid-feedback">
                                        Last Name can only contain letters and cannot be empty.
                                    </div>
                                </div>
                            </div>
                            <div className="row">
                                <div className="form-group col-md-6">
                                    <label>Email</label>
                                    <input type="text"
                                           onBlur={(ev) => this.verifyEmail(ev, ev.target.value)}
                                           className={this.state.emailClass}
                                           placeholder= "Enter your E-Mail"
                                           value={this.state.email}
                                           onChange={event => this.handleChange(event.target.value, 'email')}></input>
                                    <div className="invalid-feedback">
                                        Please enter a valid email address.
                                    </div>
                                </div>
                                <div className="form-group col-md-3">
                                    <label>CVU</label>
                                    <input type="number"
                                           className="form-control"
                                           value={this.state.cvu}
                                           disabled={true}>
                                    </input>
                                </div>
                                <div className="form-group col-md-3">
                                    <label>Amount</label>
                                    <input type="number"
                                           className="form-control"
                                           value={this.state.amount}
                                           disabled={true}>
                                    </input>
                                </div>
                            </div>
                            <button type="submit" onClick={() => this.showModal()}
                                    className="btn btn-primary">Change<i
                                className="far fa-paper-plane"></i></button>
      
                        <br/>
                        <div className="row justify-content-center">
                            <div className="col-xs-12">
                                <br/>
                                <Alert variant={this.state.alert.variant} show={this.state.alert.show}>
                                    {this.state.alert.message}
                                </Alert>
                            </div>
                        </div>
                    </div>
                </header>
                <Footer/>
            </div>
        );
    }
};