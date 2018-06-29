package rtn.com.br.schedule.models;

import java.io.Serializable;
import java.util.Date;

public class TaskItem implements Serializable {

    private String name;
    private String description;
    private Date created_at;
    private Integer priority; // 0 - Alta, 1 - Média, 2 - Baixa
    private Integer status; // 0 - Não Iniciada, 1 - Em Andamento, 2 - Cancelada, 3 - Concluída

    public TaskItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
