import React, { Component } from 'react';
import TextField from '@material-ui/core/TextField';
import {ACCESS_TOKEN, updateUserPassword} from "../../../../../Resources/emtAPI";

class NewPassword extends Component{


    constructor(props){
        super(props)
        this.state = {
            password: "",
            repwd: "",
            errorMessage: "",
            showErrorMsg: false
        }
        props.signOut.bind(this)
    }

    changePassword(c) {
        this.setState({ password: c.target.value });
    }

    changeRePassword(c) {
        this.setState({ repwd: c.target.value });
    }

    editUser = (s) =>{

        s.preventDefault()

        if(this.state.password === "" || this.state.repwd === ""){
            this.setState({
                errorMessage: "Please enter the password in both fields",
                showErrorMsg: true
            });
        }
        else if(this.state.password !== this.state.repwd){
            this.setState({
                errorMessage: "Passwords don't match.",
                showErrorMsg: true
            });
        }
        else{
            let user = {
                id: this.props.user.id,
                password: this.state.password
            }

            updateUserPassword(user).then(response => response.text())
                .then(() => {
                    this.setState({
                        errorMessage: "",
                        showErrorMsg: false
                    })
                    sessionStorage.setItem("removeToken", "true")
                    window.location.reload()
                })

        }

    }

    signOut(){
        this.props.signOut.bind(this)
    }

    render() {

        return (

            <form className="form" noValidate autoComplete="off">

                {
                    this.state.showErrorMsg &&
                    <div className="alert alert-danger col-lg-5 mx-auto" role="alert">
                        {this.state.errorMessage}
                    </div>
                }

                <div className="form-group">
                    <TextField
                        required
                        id="password"
                        label="New Password"
                        type="password"
                        margin="normal"
                        variant="outlined"
                        onChange={this.changePassword.bind(this)}
                    />
                </div>

                <div className="form-group">
                    <TextField
                        required
                        id="repwd"
                        label="Confirm password"
                        type="password"
                        margin="normal"
                        variant="outlined"
                        onChange={this.changeRePassword.bind(this)}
                    />
                </div>

                <button type="submit" onClick={n => this.editUser(n)} className="btn btn-primary mx-auto text-center">Submit</button>


            </form>

        )
    }

}
export default NewPassword;