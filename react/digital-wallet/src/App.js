import React, { Component } from 'react';
import { Route, BrowserRouter, Switch } from 'react-router-dom'
import './dist/css/App.css';
import Login from './Components/Login';
import Logout from './Components/Logout';
import Register from './Components/Register';
import Transfer from './Components/Transfer';
import Account from './Components/Account';
import CashIn from './Components/CashIn';
import Transactions from './Components/Transactions';
import PrivateRoute from './Components/PrivateRoute';
import NotFound from './Components/NotFound';

/**
 * @author Fabian Frangella
 * Component that contains the app routing
 */
class App extends Component {
  
  render() {
    return (    
    <BrowserRouter>
      <Switch>
          <Route exact path="/" component={Login} />
          <Route path="/register" component={Register} />
          <PrivateRoute path="/transfer" component={Transfer} />
          <PrivateRoute path="/cashin" component={CashIn} />
          <PrivateRoute path="/account" component={Account}/>
          <PrivateRoute path="/transactions" component={Transactions}/>
          <PrivateRoute path="/logout" component={Logout}/>
          <Route path="*" component={NotFound} />
      </Switch>
  </BrowserRouter>
    );
  }
}

export default App;
