package rtn.com.br.schedule.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserTask implements Serializable {

    private String key;
    private String name;
    private Date created_at;
    private Integer priority; // 0 - Alta, 1 - Média, 2 - Baixa
    private Integer status; // 0 - Não Iniciada, 1 - Em Andamento, 2 - Cancelada, 3 - Concluída
    private List<TaskItem> taskItems;

    public UserTask() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TaskItem> getTaskItems() {
        return taskItems;
    }

    public void setTaskItems(List<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }
}
