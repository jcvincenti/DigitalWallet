import React from 'react';
import { Route, Redirect } from 'react-router-dom';

/**
 * @author Fabian Frangella
 * Route component used for routing components that should not be accessible without loggin
 */
const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        localStorage.getItem('user')
            ? <Component {...props} />
            : <Redirect to={{ pathname: '/', state: { from: props.location } }} />
    )} />
)

export default PrivateRoute
