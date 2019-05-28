import React, { Component } from 'react';
import UpdateUserInfo from "./UpdateInfo/UpdateUserInfo";
import NewPassword from "./UpdateInfo/NewPassword";


class Employee extends Component{

    constructor(props) {
        super(props)
        this.state = {
            showUpdateUserFlag: true,
            showNewPasswordFlag: false
        }
    }

    showUpdateUser = () =>{
        this.setState({
            showUpdateUserFlag: true,
            showNewPasswordFlag: false
        })
    }

    showNewPassword = () =>{
        this.setState({
            showUpdateUserFlag: false,
            showNewPasswordFlag: true
        })
    }

    render() {

    return (

        <div className="container mx-auto text-center">

            {
                this.state.showUpdateUserFlag &&

                <div>
                    <nav className="navbar navbar-expand-sm text-warning justify-content-center">
                        <ul className="navbar-nav profile-ul">
                            <li className="nav-item mr-5 profile-li">
                                <a className="nav-link profile-a active-profile-item" onClick={this.showUpdateUser} href="#">
                                    Personal information</a>
                            </li>
                            <li className="nav-item ml-5 profile-li">
                                <a className="nav-link profile-a" onClick={this.showNewPassword} href="#">Change password</a>
                            </li>
                        </ul>
                    </nav>

                    <UpdateUserInfo user = {this.props.user}/>
                </div>

            }

            {
                this.state.showNewPasswordFlag &&

                <div>
                    <nav className="navbar navbar-expand-sm text-warning justify-content-center">
                        <ul className="navbar-nav profile-ul">
                            <li className="nav-item mr-5 profile-li">
                                <a className="nav-link profile-a" onClick={this.showUpdateUser}  href="#">
                                    Personal information
                                </a>
                            </li>
                            <li className="nav-item ml-5 profile-li">
                                <a className="nav-link profile-a active-profile-item" onClick={this.showNewPassword} href="#">
                                    Change password
                                </a>
                            </li>
                        </ul>
                    </nav>

                    <NewPassword user = {this.props.user} />

                </div>
            }

        </div>

    )
}

}
export default Employee;