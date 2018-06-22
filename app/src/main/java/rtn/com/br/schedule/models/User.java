package rtn.com.br.schedule.models;

import java.util.ArrayList;

/**
 * Created by bergtorres on 17/06/2018
 */
public class User {

    private String name;
    private ArrayList<UserTask> tasks;

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<UserTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<UserTask> tasks) {
        this.tasks = tasks;
    }
}
