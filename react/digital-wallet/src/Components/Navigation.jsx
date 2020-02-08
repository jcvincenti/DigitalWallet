import React, { Component } from 'react'
import Navbar from 'react-bootstrap/Navbar';
import Image from 'react-bootstrap/Image';
import logo from '../dist/img/DigitalWalletLogo.png';
import '../dist/css/Navigation.css';
import { Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom'

class Navigation extends Component {
  render() {
    return (
      <Navbar bg="dark" expand="lg" variant="dark">
        <Navbar.Brand>          
        <Link to= {{pathname: 'account',state: {idCard: this.props.id}}}>
          <Image className="logo" src={logo} fluid ></Image>
          </Link>
          </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <Navbar.Brand>
              <Link className="link" to= {{pathname: 'account', state: {idCard: this.props.id}}}>Account</Link>
            </Navbar.Brand>
            <Navbar.Brand>
              <Link className="link" to= {{pathname: 'transactions',state: {idCard: this.props.id}}}>Transactions</Link>
            </Navbar.Brand>
            <Navbar.Brand>
              <Link className="link" to= {{pathname: 'cashin', state: {idCard: this.props.id}}}>Cash In</Link>
            </Navbar.Brand>
            <Navbar.Brand>
              <Link className="link" to= {{pathname: 'transfer',state: {idCard: this.props.id}}}>Transfer</Link>
            </Navbar.Brand>
            </Nav>
            <Nav>
            <Navbar.Brand>
            <Link className="link" to= "logout">Logout</Link>
            </Navbar.Brand>
            </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  }
}

export default Navigation