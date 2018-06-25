package rtn.com.br.schedule.models;

import java.io.Serializable;
import java.util.Date;

public class UserTask implements Serializable{

    private String uid;
    private String title;
    private Date created_at;
    private String description;
    private Integer prioridade; // 1 - Alta, 2 - Média, 3 - Baixa
    private Integer status; // 0 - Não Iniciada, 1 - Em Andamento, 2 - Cancelada, 3 - Concluída

    public UserTask() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
