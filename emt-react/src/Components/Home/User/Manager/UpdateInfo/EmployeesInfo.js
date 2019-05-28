import React, {Component} from "react";
import TextField from "@material-ui/core/TextField/TextField";
import Select from "react-select";
import {ACCESS_TOKEN, getCurrentUser, updateUserInfo, registerUser} from "../../../../../Resources/emtAPI";
import MaterialTable from 'material-table';


const levels = [
    { label: "Junior Developer", value: 0 },
    { label: "Mid Level Developer", value: 1 },
    { label: "Senior Developer", value: 2 },
    { label: "Junior Tester", value: 3 },
    { label: "Mid Level Tester", value: 4 },
    { label: "Senior Tester", value: 5 },
];

class EmployeesInfo extends Component{


    constructor(props) {
        super(props)
        this.state = {
            full_name: "",
            email: "",
            department_name: "",
            level: "",
            columns: "",
            data: ""
        }
    }

    componentDidMount(){
        this.setState({
            columns: [
                { title: 'Full Name', field: 'full_name' },
                { title: 'E-mail address', field: 'email' },
                { title: 'Position', field: 'position' },
                { title: 'Department', field: 'department' }
            ],
            data: [
                { name: 'Mehmet', surname: 'Baran', birthYear: 1987, birthCity: 63 },
                {
                    name: 'Zerya Betül',
                    surname: 'Baran',
                    birthYear: 2017,
                    birthCity: 34,
                },
            ],
        })
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

    }


    render() {

        return (
            <div>

            <form className="form col-lg-12" noValidate autoComplete="off">

                {
                    this.state.showErrorMsg &&
                    <div className="alert alert-danger col-lg-5 mx-auto" role="alert">
                        {this.state.errorMessage}
                    </div>
                }

                <div className="form-group">
                    <div className="row">
                    <div className="m-1">
                    <TextField
                        id="full_name"
                        label="Full Name"
                        value={this.state.full_name}
                        margin="dense"
                        variant="outlined"
                        onChange={this.changeFullName.bind(this)}
                    />
                    </div>

                        <div className="m-1">
                    <TextField
                        id="email"
                        label="E-mail Address"
                        value={this.state.email}
                        margin="dense"
                        variant="outlined"
                        onChange={this.changeEmail.bind(this)}
                    />
                        </div>

                    <div className={"col-lg-3 m-1"}>
                        <Select
                            name = "Position"
                            options = {levels}
                            onChange={this.changeEmploymentLevel.bind(this)}
                            defaultValue={levels.filter(option => option.value === this.state.level)}/>
                    </div>

                        <div className="m-1">
                    <TextField
                        disabled
                        id="department"
                        label="Department"
                        value={this.state.department_name}
                        margin="dense"
                        variant="outlined"
                    />
                        </div>
                </div>
                </div>
                <button type="submit" onClick={n => this.editUser(n)} className="btn btn-primary m-1 text-center">Update</button>
                <button type="submit" onClick={n => this.editUser(n)} className="btn btn-primary m-1 text-center">Search</button>

            </form>

        </div>
        )
    }

}
export default EmployeesInfo;