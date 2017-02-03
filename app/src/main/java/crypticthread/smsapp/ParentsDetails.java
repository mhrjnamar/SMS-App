package crypticthread.smsapp;

import java.io.Serializable;

/**
 * Created by User on 2/3/2017.
 */

public class ParentsDetails implements Serializable {
    private String id;
    private String first_name;
    private String last_name;
    private String user_name;
    private String role;
    private String email;
    private String clildrens_ids;
    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClildrens_ids() {
        return clildrens_ids;
    }

    public void setClildrens_ids(String clildrens_ids) {
        this.clildrens_ids = clildrens_ids;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ParentsDetails(String id, String first_name, String last_name, String user_name, String role, String email, String clildrens_ids, String photo) {

        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.role = role;
        this.email = email;
        this.clildrens_ids = clildrens_ids;
        this.photo = photo;
    }
}
