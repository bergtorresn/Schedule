package rtn.com.br.schedule.models;

import java.io.Serializable;
import java.util.Date;

public class UserTask implements Serializable {

    private String name;
    private Date created_at;
    private String description;
    private Integer priority; // 0 - Alta, 1 - Média, 2 - Baixa
    private Integer status; // 0 - Não Iniciada, 1 - Em Andamento, 2 - Cancelada, 3 - Concluída

    public UserTask() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
