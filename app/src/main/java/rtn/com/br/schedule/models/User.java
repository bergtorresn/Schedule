package rtn.com.br.schedule.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bergtorres on 17/06/2018
 */
public class User {

    private String uid;
    private String name;
    private String email;

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
