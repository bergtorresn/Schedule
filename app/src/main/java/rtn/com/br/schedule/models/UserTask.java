package rtn.com.br.schedule.models;

import java.util.Date;

public class UserTask {

    private String title;
    private Date created_at;
    private String description;
    private Integer prioridade; // 1 - Alta, 2 - Média, 3 - Baixa

    public UserTask() {}

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
}
