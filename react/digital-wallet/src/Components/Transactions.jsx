import React, {Component} from 'react';
import axios from 'axios';
import Footer from './Footer'
import Navigation from './Navigation'


export default class Transfer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            transactions: [],
            currentUser: localStorage.user,
            records: false
        };

    }

    renderTransactions = () => {
        return this.state.transactions.map(e => <tr>
            <td>{e.dateTime.substring(0, 10)}</td>
            <td>{e.dateTime.substring(11, 19)}</td>
            <td> {e.description} </td>
            <td className={`${e.cashOut ? 'text-danger' : 'text-success'}`}> {Math.abs(e.amount)} </td>
        </tr>)
    }

    componentDidMount = () => {

        axios
            .get(`http://localhost:7000/users/user/${this.state.currentUser}`)
            .then(response =>
                axios
                    .get(`http://localhost:7000/transactions/${response.data.cvu}`)
                    .then(response => {
                        this.setState({
                            transactions: response.data.reverse(),
                            records: response.data.length > 0
                        })
                    })
            )

    }

    render() {
        return (
            <div className="App">
                <Navigation id={this.state.currentUser}/>
                <header className="App-header">
                    <div className="container">
                        <div className="row justify-content-center">
                            <div className="col-md-6">
                                <h3 className="text-primary"> Transactions </h3>
                            </div>
                        </div>
                        <hr style={{color: '#0275d8', backgroundColor: '#0275d8', height: 1}}/>
                        <div className="container">
                            <div hidden={this.state.records}>
                                <i className="fas fa-exclamation-triangle fa-10x"></i>
                                <h1 className="display-4">No records</h1>
                            </div>

                            <table hidden={!this.state.records} className="table-dark col">
                                <thead>
                                <tr>
                                    <th scope="col">Date</th>
                                    <th scope="col">Time</th>
                                    <th scope="col">Description</th>
                                    <th scope="col">Amount</th>
                                </tr>
                                </thead>
                                <tbody>
                                {this.renderTransactions()}
                                </tbody>
                            </table>

                        </div>
                    </div>
                </header>
                <Footer/>
            </div>

        );
    }
}