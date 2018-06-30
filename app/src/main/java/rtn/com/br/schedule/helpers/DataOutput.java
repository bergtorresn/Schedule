package rtn.com.br.schedule.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataOutput {

    public static String nameOutput(String name){
        String textFinal = "<b>Nome: </b>" + name;
        return textFinal;
    }

    public static String descriptionOutput(String name){
        String textFinal = "<b>Descrição: </b>" + name;
        return textFinal;
    }

    public static String dateOutput(Date date){
        SimpleDateFormat ft =
                new SimpleDateFormat("dd/MM/yyyy 'às' hh:mm");
        String dateString = ft.format(date);

        String textFinal = "<b>Data: </b>" + dateString;
        return textFinal;
    }

    public static String priorityOutput(Integer priority){
        String priorityToString = "";
        switch (priority) {
            case 0: priorityToString = "Alta";
                break;
            case 1: priorityToString = "Média";
                break;
            case 2: priorityToString = "Baixa";
                break;
        }

        String textFinal = "<b>Prioridade: </b>" + priorityToString;
        return textFinal;
    }

    public static String statusOutput(Integer status){
        String statusToString = "";
        switch (status) {
            case 0: statusToString = "Não iniciada";
                break;
            case 1: statusToString = "Em andamento";
                break;
            case 2: statusToString = "Cancelada";
                break;
            case 3: statusToString = "Concluída";
                break;
        }

        String textFinal = "<b>Status: </b>" + statusToString;
        return textFinal;
    }


}
