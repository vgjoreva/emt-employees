import React, { Component } from 'react';
import './App.css';
import '../../Components/SignIn/SignIn';
import '../../Components/SignIn/ForgetPassword/ForgetPassword';
import '../../Components/SignUp/SignUp';
import '../../Components/SignUp/Activate/ActivateUser';
import {Route, Redirect} from 'react-router-dom';
import SignIn from "../SignIn/SignIn";
import ForgetPassword from "../SignIn/ForgetPassword/ForgetPassword";
import SignUp from "../SignUp/SignUp";
import ActivateUser from "../SignUp/Activate/ActivateUser";
import Home from "../Home/Home";

class App extends Component{

    render() {
        return (
            <div>
                <Route exact path="/" render={() => (<Redirect to="/login" />)} />
                <Route exact path='/login' component={SignIn} />
                <Route exact path='/login/forgot_password' component={ForgetPassword} />
                <Route exact path='/sign_up' component={SignUp} />
                <Route exact path='/activation' component={ActivateUser} />
                <Route exact path="/activation/:code" component={ActivateUser} />
                <Route exact path='/home' component={Home} />
            </div>
        );
    }
}
export default App;
