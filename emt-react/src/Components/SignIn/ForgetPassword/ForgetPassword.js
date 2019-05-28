import {Component} from "react";
import React from "react";
import {ACCESS_TOKEN, doesUserExist, forgotPassword} from "../../../Resources/emtAPI";

class ForgetPassword extends Component{

    constructor(props){
        super(props)
        this.state = {
            email: "",
            customClassName: "alert alert-danger",
            errorMessage: "",
            showErrorMsg: false
        }

    }

    componentDidMount() {
        if (localStorage.getItem(ACCESS_TOKEN) != null ||
            sessionStorage.getItem(ACCESS_TOKEN) != null) {
            this.props.history.push('/home');
        }
        else{
            if(sessionStorage.getItem("errorMessage") != null &&
                sessionStorage.getItem("showErrorMsg") != null &&
                sessionStorage.getItem("email") != null &&
                sessionStorage.getItem("customClassName") != null){

                this.setState({
                    email: sessionStorage.getItem("email"),
                    customClassName: sessionStorage.getItem("customClassName"),
                    errorMessage: sessionStorage.getItem("errorMessage"),
                    showErrorMsg: sessionStorage.getItem("showErrorMsg")
                })

                sessionStorage.removeItem("errorMessage")
                sessionStorage.removeItem("showErrorMsg")
                sessionStorage.removeItem("email")
                sessionStorage.removeItem("customClassName")

            }
        }
    }

    changeEmail(c) {
        this.setState({ email: c.target.value });
    }


    forgotPasswordHandler = () =>{

        let emailValidation = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        if(emailValidation.test(this.state.email)) {
            console.log(this.state.email)
            doesUserExist(this.state.email)
                .then(response => response.text())
                .then((data) => {
                    this.setState({
                        errorMessage: data
                    }, () => {
                        console.log(this.state.errorMessage)
                        if (this.state.errorMessage === "Email already exists") {
                            forgotPassword(this.state.email)
                            this.setState({
                                errorMessage: "New password sent to email",
                                showErrorMsg: true,
                                customClassName: "alert alert-success"
                            })
                            sessionStorage.setItem("errorMessage", "New password sent to email")
                            sessionStorage.setItem("customClassName", "alert alert-success")
                            sessionStorage.setItem("showErrorMsg", "true")

                        }
                        else if(this.state.errorMessage === "Valid") {
                            this.setState({
                                errorMessage: "User with the given email doesn't exist",
                                showErrorMsg: true
                            })
                            sessionStorage.setItem("errorMessage", "User with the given email doesn't exist")
                            sessionStorage.setItem("customClassName", "alert alert-danger")
                            sessionStorage.setItem("showErrorMsg", "true")
                        }

                        sessionStorage.setItem("email", this.state.email)


                    })
                })
        }
        else{
            this.setState({
                errorMessage: "Invalid email address",
                showErrorMsg: true
            })
        }
    }

    goBack(){
        this.props.history.push("/login")
    }

    render() {
        return (
            <div className="container mx-auto text-center">
                <h4 className="m-5">Forgot password</h4>
                <form className="form col-5 mx-auto">

                    {
                        this.state.showErrorMsg &&
                        <div className={this.state.customClassName} role="alert">
                            {this.state.errorMessage}
                        </div>
                    }

                    <button type="button" className="btn btn-outline-info" onClick={this.goBack.bind(this)}>
                        Back
                    </button>

                    <div className="form-group">
                        <label htmlFor="email">Enter your e-mail address:</label>
                        <input id="email"
                               type="email"
                               className="form-control"
                               name="email"
                               value={this.state.email}
                               onChange={this.changeEmail.bind(this)}
                               required/>
                    </div>

                    <button type="submit"
                            className="btn btn-primary"
                            onClick={this.forgotPasswordHandler.bind(this)}>Submit</button>
                </form>

            </div>
        );
    }
}
export default ForgetPassword;