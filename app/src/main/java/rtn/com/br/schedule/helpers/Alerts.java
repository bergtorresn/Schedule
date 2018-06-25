package rtn.com.br.schedule.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import rtn.com.br.schedule.activities.NewTaskActivity;
import rtn.com.br.schedule.activities.TaskDetailActivity;
import rtn.com.br.schedule.activities.TaskListActivity;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.models.UserTask;

/**
 * Created by bergtorres on 17/06/2018
 */
public class Alerts {

    private static AlertDialog.Builder alert;

    public static void genericAlert(String title, String message, Activity activity){

        alert = new AlertDialog.Builder(activity);

        alert.setTitle(title);

        alert.setMessage(message);

        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "OK");
            }
        });

        alert.create();
        alert.show();

    }

    public static void alertConnection(Activity activity){

        alert = new AlertDialog.Builder(activity);

        alert.setTitle("Atenção");

        alert.setMessage("Não foi possível se comunicar com o servidor, tente novamente.");

        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "OK");
            }
        });

        alert.create();
        alert.show();

    }

    public static void alertInternet(Activity activity){

        alert = new AlertDialog.Builder(activity);

        alert.setTitle("Atenção");

        alert.setMessage("Verifique sua conexão com a internet e tente novamente.");

        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "OK");
            }
        });

        alert.create();
        alert.show();

    }

    public static void alertSigOut(final Activity activity){

        alert = new AlertDialog.Builder(activity);

        alert.setMessage("Deseja sair da sua conta?");

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseService.singOut();
                activity.startActivity(new Intent(activity, NewTaskActivity.class));
            }
        });

        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        alert.setCancelable(false);
        alert.create();
        alert.show();

    }

    public static void alertRemoverTask(final Activity activity, final UserTask userTask){

        alert = new AlertDialog.Builder(activity);

        alert.setMessage("Deseja remover essa tarefa?");

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseService.deleteTask(activity, userTask);
                activity.finish();
            }
        });

        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        alert.setCancelable(false);
        alert.create();
        alert.show();

    }
}
