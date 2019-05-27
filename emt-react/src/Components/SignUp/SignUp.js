import {Component} from "react";
import React from "react";
import Select from "react-select";
import {ACCESS_TOKEN, createUser, doesUserExist} from '../../Resources/emtAPI';


const levels = [
    { label: "Junior Developer", value: 0 },
    { label: "Mid Level Developer", value: 1 },
    { label: "Senior Developer", value: 2 },
    { label: "Junior Tester", value: 3 },
    { label: "Mid Level Tester", value: 4 },
    { label: "Senior Tester", value: 5 },
];


class SignUp extends Component{

    constructor(props){
        super(props)
        this.state = {
            email: "",
            errorMessage: "",
            full_name: "",
            password: "",
            repwd: "",
            level: 0,
            showErrorMsg: false
        }

    }

    componentDidMount() {
        if (localStorage.getItem(ACCESS_TOKEN) != null) {
            console.log('token', localStorage.getItem(ACCESS_TOKEN));
            this.props.history.push('/home');
        }
    }

    changeFullName = (n) => {
        this.setState({ full_name: n.target.value });
    }

    changeEmail(c) {
        this.setState({ email: c.target.value });
    }

    changePassword(c) {
        this.setState({ password: c.target.value });
    }

    changeRePassword(c) {
        this.setState({ repwd: c.target.value });
    }

    changeEmploymentLevel = (level) => {
        this.setState({level});
    }      

    signUpUser = (s) => {

        s.preventDefault();

        let user = {
            email: this.state.email,
            level: this.state.level.value,
            full_name: this.state.full_name,
            password: this.state.password
        }
        console.log(user)

        doesUserExist(user.email).then(response => response.text())
            .then((data) => {
                console.log('data: ', data)
                this.setState({
                    errorMessage: data
                }, () => {
                    console.log(user.email)
                    console.log(this.state.errorMessage)

                    if(user.email === "" || user.level === "" ||
                        user.full_name === "" || user.password === "" || this.state.repwd === ""){

                        this.setState({
                            errorMessage: "Missing information. Please fill out all of the fields.",
                            showErrorMsg: true
                        });
                    }
                    else if(this.state.password !== this.state.repwd){
                        this.setState({
                            errorMessage: "Passwords don't match.",
                            showErrorMsg: true
                        });
                    }
                    else if(this.state.errorMessage !== "Valid"){
                        this.setState({
                            errorMessage: "User already exists!",
                            showErrorMsg: true
                        });
                    }
                    else{
                        console.log(user);
                        createUser(user);
                        this.props.history.push('/activation');
                        this.setState({
                            showErrorMsg: false,
                            showSuccessMsg: true
                        });
                    }

                });

            });
    }

    render() {

        const { level } = this.state;

        return (
            <div className="container mx-auto">
                <h4 className="mt-5 mb-5 text-center">Sign Up</h4>

                <form className="form col-5 mx-auto">

                    {
                        this.state.showErrorMsg &&
                        <div className="alert alert-danger" role="alert">
                            {this.state.errorMessage}
                        </div>
                    }

                    <div className="form-group">
                        <label htmlFor="full_name">Full name:</label>
                        <input id="full_name" type="text" className="form-control"
                               name="full_name" placeholder="Enter name"
                               onChange={n => this.changeFullName(n)} required />
                    </div>

                    <div className="form-group">
                        <label htmlFor="email">E-mail address:</label>
                        <input id="email" type="email" className="form-control" name="email"
                               placeholder="Enter email"
                               onChange={n => this.changeEmail(n)}required />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input id="password" type="password" className="form-control"
                               name="password" placeholder="Enter a password"
                               onChange={n => this.changePassword(n)} required />
                    </div>

                    <div className="form-group">
                        <label htmlFor="repassword">Confirm password:</label>
                        <input id="repassword" type="password" className="form-control" name="repassword"
                               onChange={n => this.changeRePassword(n)} placeholder=""
                               required />
                    </div>

                    <div className="form-group">
                        <label htmlFor="level">Select an employment position:</label>
                        <Select options = {levels} onChange={n => this.changeEmploymentLevel(n)}/>
                    </div>

                    <button type="submit" onClick={n => this.signUpUser(n)} className="btn btn-primary mx-auto text-center">Submit</button>
                </form>

            </div>
        );
    }
}
export default SignUp;