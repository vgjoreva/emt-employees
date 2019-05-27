import {Component} from "react";
import React from "react";
import {ACCESS_TOKEN, getCurrentUser} from "../../Resources/emtAPI";

class Home extends Component{

    constructor(props){
        super(props)
        this.state = {
            role: ""
        }

    }

    componentDidMount() {
        if (localStorage.getItem(ACCESS_TOKEN) == null ||
            sessionStorage.getItem(ACCESS_TOKEN) == null) {
            this.props.history.push('/login');
        }
        else{
            getCurrentUser()
        }
    }

    /*changeEmail(c) {
        this.setState({ email: c.target.value });
    }*/



    render() {
        return (
            <div className="container mx-auto text-center">
                <h4 className="mt-5 mb-5">Forgot password</h4>

            </div>
        );
    }
}
export default Home;