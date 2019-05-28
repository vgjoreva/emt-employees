import React, { Component } from 'react';
import TextField from '@material-ui/core/TextField';
import Select from "react-select";
import {ACCESS_TOKEN, getCurrentUser, updateUserInfo} from "../../../../../Resources/emtAPI";

const levels = [
    { label: "Junior Developer", value: 0 },
    { label: "Mid Level Developer", value: 1 },
    { label: "Senior Developer", value: 2 },
    { label: "Junior Tester", value: 3 },
    { label: "Mid Level Tester", value: 4 },
    { label: "Senior Tester", value: 5 },
];

class UpdateUserInfo extends Component{

    constructor(props) {
        super(props)
        this.state = {
            full_name: props.user.name,
            email: props.user.email,
            department_name: props.user.department_name,
            level: props.user.level
        }
    }

    changeFullName = (n) => {
        this.setState({ full_name: n.target.value });
    }

    changeEmail(c) {
        this.setState({ email: c.target.value });
    }

    changeEmploymentLevel = (level) => {
        this.setState({level});
    }

    editUser = (s) => {

        s.preventDefault()

        let user = {
            id: this.props.user.id,
            email: this.state.email,
            level: this.state.level.value,
            full_name: this.state.full_name
        }
        console.log(user)

        updateUserInfo(user)
        getCurrentUser(ACCESS_TOKEN)
            .then((data) => {
                this.setState({
                    full_name: data.name,
                    email: data.email,
                    department_name: data.department_name,
                    level: data.level
                })
                window.location.reload()
            })
    }

    render() {

        return (

            <form className="form m-2" noValidate autoComplete="off">

                <div className="form-group">
                    <TextField
                        id="full_name"
                        label="Full Name"
                        value={this.state.full_name}
                        margin="normal"
                        variant="outlined"
                        onChange={this.changeFullName.bind(this)}
                    />
                </div>

                <div className={"form-group"}>
                    <TextField
                        id="email"
                        label="E-mail Address"
                        value={this.state.email}
                        margin="normal"
                        variant="outlined"
                        onChange={this.changeEmail.bind(this)}
                    />
                </div>

                <div className={"form-group col-lg-3 mx-auto"}>
                    <label className="text-sm-left text-muted">Position</label>
                    <Select
                        name = "Position"
                        options = {levels}
                        onChange={this.changeEmploymentLevel.bind(this)}
                        defaultValue={levels.filter(option => option.value === this.state.level)}/>
                </div>

                <div className={"form-group col-lg-3 mx-auto"}>
                    <TextField
                        disabled
                        id="department"
                        label="Department"
                        value={this.state.department_name}
                        margin="normal"
                        variant="outlined"
                    />
                </div>

                <button type="submit" onClick={n => this.editUser(n)} className="btn btn-primary mx-auto text-center">Submit</button>
            </form>

        )
    }

}
export default UpdateUserInfo;