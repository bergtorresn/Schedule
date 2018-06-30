package rtn.com.br.schedule.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserTask implements Serializable {

    private String uid;
    private String name;
    private Date created_at;

    public UserTask() {
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
