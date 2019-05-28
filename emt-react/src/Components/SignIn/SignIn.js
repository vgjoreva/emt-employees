import {Component} from "react";
import React from "react";
import {ACCESS_TOKEN, login} from '../../Resources/emtAPI';

class SignIn extends Component{

    constructor(props){
        super(props)
        this.state = {
            email: "",
            errorMessage: "",
            pwd: "",
            rememberMe: "off",
            showErrorMsg: false,
        }
    }

    componentDidMount(){
        if(localStorage.getItem(ACCESS_TOKEN) != null ||
            sessionStorage.getItem(ACCESS_TOKEN) != null){
            this.props.history.push('/home');
        }
    }

    changeUsername(c) {
        this.setState({ email: c.target.value  });
    }

    changePassword(c) {
        this.setState({ pwd: c.target.value });
    }

    changeRememberMe(c) {
        this.setState({ rememberMe: c.target.value });
    }

    handleSubmit = (e) => {
        e.preventDefault();
        console.log(this.state.pwd);

        let loginToken = {
            email: this.state.email,
            password: this.state.pwd
        }

        console.log(this.state.rememberMe)
        login(loginToken)
            .then(response => {
                console.log('token', response.accessToken);
                if(this.state.rememberMe === "on"){
                    localStorage.setItem(ACCESS_TOKEN, response.accessToken)
                }
                else{
                    sessionStorage.setItem(ACCESS_TOKEN, response.accessToken)
                }
                this.props.history.push('/home');
            }).catch(() => {
                this.setState({
                    errorMessage: "Invalid username or password",
                    showErrorMsg: true,
                })
        });
    }

    render() {
        return (
            <div className="container mx-auto text-center">
                <h4 className="mt-5 mb-5">Sign in</h4>

                <form className="form col-5 mx-auto">

                    {
                        this.state.showErrorMsg &&
                        <div className="alert alert-danger" role="alert">
                            {this.state.errorMessage}
                        </div>
                    }

                    <div className="form-group">
                        <label htmlFor="email">E-mail address:</label>
                        <input id="email"
                               type="email"
                               className="form-control"
                               name="email"
                               onChange={n => this.changeUsername(n)} required/>
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input id="password"
                               type="password"
                               className="form-control"
                               name="password"
                               onChange={n => this.changePassword(n)} required/>
                    </div>

                    <div className="form-group">
                        <div className="form-inline justify-content-center">
                            <label htmlFor="rememberUser">Remember me? </label>
                            <input
                                id="rememberUser"
                                type="checkbox"
                                className="form-control"
                                name="rememberUser"
                                onChange={n => this.changeRememberMe(n)}/>
                        </div>
                    </div>
                    <div className="form-group">
                        <a href="/login/forgot_password" className="text-center">Forgot password?</a>
                    </div>

                    <button type="submit" onClick={this.handleSubmit} className="btn btn-warning m-3">Sign In</button>
                </form>
                <button className="btn-sm btn-secondary mx-auto m-3">
                    <a className="btn-link text-light text-decoration-none" href="/sign_up">Sign Up</a>
                </button>
            </div>
            
        );
    }
}
export default SignIn;