import {Component} from "react";
import React from "react";
import {ACCESS_TOKEN, getCurrentUser, isAccountValid, isCodeValid, registerUser} from "../../Resources/emtAPI";
import Employee from "./User/Employee/Employee";
import Manager from "../Home/User/Manager";

class Home extends Component{

    constructor(props){
        super(props)
        this.state = {
            user: "",
            showEmployeeData: false,
            showManagerData: false
        }
        this.signOut.bind(this)

    }

    componentDidMount() {

        if(sessionStorage.getItem("removeToken") != null){
            if(localStorage.getItem(ACCESS_TOKEN) != null)
                localStorage.removeItem(ACCESS_TOKEN)
            if(sessionStorage.getItem(ACCESS_TOKEN) != null) {
                sessionStorage.removeItem(ACCESS_TOKEN)
            }
            sessionStorage.removeItem("removeToken")
            this.props.history.push('/login');
        }
        else if (localStorage.getItem(ACCESS_TOKEN) == null &&
            sessionStorage.getItem(ACCESS_TOKEN) == null) {
            this.props.history.push('/login');
        }
        else{
            getCurrentUser(ACCESS_TOKEN)
                .then((data) => {
                    this.setState({
                        user: data
                    })
                    sessionStorage.setItem("id", data.id)
                    sessionStorage.setItem("role", data.role)
                    console.log(data.role)
                    if(data.role === "USER"){
                        if(sessionStorage.getItem(ACCESS_TOKEN) != null)
                            sessionStorage.removeItem(ACCESS_TOKEN)
                        if(localStorage.getItem(ACCESS_TOKEN) != null)
                            localStorage.removeItem(ACCESS_TOKEN)
                        this.props.history.push('/activation');
                    }
                    else if(data.role === "EMPLOYEE"){
                        this.setState({
                            showEmployeeData: true,
                            showManagerData: false
                        })
                    }
                    else if(data.role === "MANAGER" || data.role === "ADMIN"){
                        this.setState({
                            showEmployeeData: false,
                            showManagerData: true
                        })
                    }
                });
        }
    }

    signOut = (s) =>{

        s.preventDefault()

        if(sessionStorage.getItem(ACCESS_TOKEN) != null)
            sessionStorage.removeItem(ACCESS_TOKEN)

        if(localStorage.getItem(ACCESS_TOKEN) != null)
            localStorage.removeItem(ACCESS_TOKEN)

        this.props.history.push("/login")

    }


    render() {
        return (
            <div className="container mx-auto text-center">
                <h4 className="mt-5 mb-5">Welcome {this.state.user.name}!</h4>

                <button type="button" className="btn btn-outline-info" onClick={this.signOut.bind(this)}>
                    Sign Out
                </button>

                {
                    this.state.showEmployeeData &&
                        <Employee user={this.state.user}
                                  signOut={this.signOut.bind(this)}/>
                }

                {
                    this.state.showManagerData &&
                        <Manager user={this.state.user}/>
                }

            </div>
        );
    }
}
export default Home;