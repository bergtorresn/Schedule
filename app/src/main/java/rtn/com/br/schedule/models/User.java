package rtn.com.br.schedule.models;

import java.util.List;

/**
 * Created by bergtorres on 17/06/2018
 */
public class User {

    private String name;
    private List<Task> tasks;

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
