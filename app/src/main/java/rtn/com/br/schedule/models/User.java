package rtn.com.br.schedule.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bergtorres on 17/06/2018
 */
public class User implements Serializable {

    private String name;
    private String email;
    private List<UserTask> userTasks;

    public User() {}

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

    public List<UserTask> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(List<UserTask> userTasks) {
        this.userTasks = userTasks;
    }
}
