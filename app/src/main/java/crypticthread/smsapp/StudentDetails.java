package crypticthread.smsapp;

import java.io.Serializable;

/**
 * Created by User on 2/3/2017.
 */

public class StudentDetails implements Serializable {
    private String student_id;
    private String firstName;
    private String lastName;
    private String rollNo;
    private String grade;

    public StudentDetails(String student_id, String firstName, String lastName, String rollNo, String grade) {
        this.student_id = student_id;

        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNo = rollNo;
        this.grade = grade;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
