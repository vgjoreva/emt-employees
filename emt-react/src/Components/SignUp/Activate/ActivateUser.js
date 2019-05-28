import {Component} from "react";
import React from "react";
import {ACCESS_TOKEN, isCodeValid, registerUser, getCurrentUser} from "../../../Resources/emtAPI";

class ActivateUser extends Component{

    constructor(props){
        super(props)
        this.state = {
            code: 0,
            errorMessage: "",
            showErrorMsg: false
        }

    }

    componentDidMount() {

        if(sessionStorage.getItem("isActivated") != null){
            sessionStorage.removeItem("isActivated")
            this.props.history.push('/login');
        }
        if (localStorage.getItem(ACCESS_TOKEN) != null ||
            sessionStorage.getItem(ACCESS_TOKEN) != null) {
            this.props.history.push('/home');
        }
        else if(this.props.match.params.code != null){
            this.registerUserHandler()
        }

        if(sessionStorage.getItem("errorMessage") != null && sessionStorage.getItem("showErrorMsg") != null){
            this.setState({
                errorMessage: sessionStorage.getItem("errorMessage"),
                showErrorMsg: sessionStorage.getItem("showErrorMsg")
            })

            sessionStorage.removeItem("errorMessage")
            sessionStorage.removeItem("showErrorMsg")
        }

    }

    changeCode(c) {
        this.setState({ code: c.target.value });
    }

    registerUserHandler = () => {

        let code = this.state.code
        if(this.props.match.params.code != null)
            code = this.props.match.params.code

        console.log(code)
        isCodeValid(code)
            .then(response => response.text())
            .then((data) => {
                console.log('data: ', data)
                this.setState({
                    errorMessage: data
                }, () => {

                    if(data === "True"){
                        registerUser(code)
                        this.setState({
                            errorMessage: "",
                            showErrorMsg: false,
                        })
                        sessionStorage.setItem("isActivated", "true")
                    }
                    else{
                        this.setState({
                            errorMessage: "Invalid code",
                            showErrorMsg: true
                        })
                        sessionStorage.setItem("errorMessage", "Invalid code")
                        sessionStorage.setItem("showErrorMsg", "true")
                    }

                })
            })

    }

    render() {
        return (
            <div className="container mx-auto text-center">
                <h4 className="mt-5 mb-5">Activate user account</h4>
                <form className="form col-5 mx-auto">

                    {
                        this.state.showErrorMsg &&
                        <div className="alert alert-danger" role="alert">
                            {this.state.errorMessage}
                        </div>
                    }

                    <div className="form-group">
                        <label htmlFor="code">Enter activation code:</label>
                        <input id="code"
                               type="text"
                               className="form-control"
                               onChange={n => this.changeCode(n)}
                               name="code"
                               required/>
                    </div>

                    <button type="submit"
                            className="btn btn-primary"
                            onClick={this.registerUserHandler.bind(this)}>Submit</button>
                </form>

            </div>
        );
    }
}
export default ActivateUser;