package rtn.com.br.schedule.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserTaskOutput {

    public static String dateOutput(Date date){
        SimpleDateFormat ft =
                new SimpleDateFormat("dd/MM/yyyy 'às' hh:mm");
        String dateString = ft.format(date);
        return dateString;
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
        return priorityToString;
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
        return statusToString;
    }


}
