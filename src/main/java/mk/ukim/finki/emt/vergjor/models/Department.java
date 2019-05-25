package mk.ukim.finki.emt.vergjor.models;

import javax.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private int department_id;

    @Column(name = "department_name")
    private String department_name;

    public Department(){}

    public int getId() {
        return department_id;
    }

    public void setId(int department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
}
