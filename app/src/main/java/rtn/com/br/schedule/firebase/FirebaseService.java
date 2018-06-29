package rtn.com.br.schedule.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.interfaces.CallbackAuthResult;
import rtn.com.br.schedule.interfaces.CallbackDatabase;
import rtn.com.br.schedule.helpers.InternetConnection;
import rtn.com.br.schedule.interfaces.CallbackDataSnapshot;
import rtn.com.br.schedule.models.TaskItem;
import rtn.com.br.schedule.models.User;
import rtn.com.br.schedule.models.UserTask;

/**
 * Created by bergtorres on 16/06/2018
 */
public class FirebaseService {

    /** =============
     *  FirebaseAuth
     *  =============
     */

    /**
     * Método responsável por criar um perfil de acesso com email e senha para o usuário
     *
     * @param email
     * @param password
     * @param activity
     * @param callback O método recebe como parâmetro a interface que implementa Task<AuthResult>
     */
    public static void createAuthUser(String email, String password, final Activity activity, final CallbackAuthResult callback) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");

            GetFirebase.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            callback.onCallback(task);
                        }
                    });
        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }

    /**
     * Método responsável por efetuar o login no aplicativo
     *
     * @param email
     * @param password
     * @param activity
     * @param callback O método recebe como parâmetro a interface que implementa CallbackAuthResult
     */
    public static void singIn(String email, String password, Activity activity, final CallbackAuthResult callback) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");

            GetFirebase.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            callback.onCallback(task);
                        }
                    });
        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }


    /**
     * Método responsável por efetuar o Logout do aplicativo
     */
    public static void singOut() {

        try {
            GetFirebase.getFirebaseAuth().signOut();
            Log.i("AUTH", "SUCCESS SINGOUT");
        } catch (Exception e) {
            Log.i("AUTH", "ERROR SINGOUT " + e.getMessage());
        }
    }


    /**
     * Método responsável por verificar se existe um usuáiro logado no aplicativo,
     *
     * @return boolean
     */
    public static boolean checkIfHaveUser() {

        FirebaseUser user = GetFirebase.getFirebaseAuth().getCurrentUser();

        if (user != null) {
            Log.i("AUTH", "HAVE USER");
            return true;
        }
        Log.i("AUTH", "DONT HAVE USER");
        return false;
    }

    /**
     * Método responsável por retornar o usuário logado
     *
     * @return FirebaseUser
     */
    private static FirebaseUser getUser() {
        FirebaseUser user = GetFirebase.getFirebaseAuth().getCurrentUser();

        if (user != null) {
            return user;
        }

        return null;
    }

    /** ================
     *  FirebaseDatabase
     *  ================
     */

    /**
     * Método responsável por criar no Database um nó para um novo usuário,
     * o nó terá como râmo principal o Uid e como râmo filho o nome do usuário
     */
    public static void createUserInDB(User user, final CallbackDatabase callback) {
        GetFirebase.getFireDatabaseReferenceUsers()
                .child(getUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.onCallback(task);

            }
        });
    }

    /**
     * Método responsável por criar uma tarefa no nó do usuário atual
     * @param userTask
     * @param activity
     */
    public static void createTaskItem(UserTask userTask, Activity activity, final CallbackDatabase callback) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");
            GetFirebase.getFireDatabaseReferenceUsers()
                    .child(getUser().getUid())
                    .child("tasks").child(userTask.getKey())
                    .child("taskitems").push()
                    .setValue(userTask.getTaskItems().get(0)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    callback.onCallback(task);
                }
            });

        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }

    /**
     * Método responsável por criar uma tarefa no nó do usuário atual
     * @param userTask
     * @param activity
     */
    public static void createUserTask(UserTask userTask, Activity activity, final CallbackDatabase callback) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            Log.i("INTERNET", "CONECTED");
            GetFirebase.getFireDatabaseReferenceUsers()
                    .child(getUser().getUid())
                    .child("tasks").push()
                    .setValue(userTask).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    callback.onCallback(task);
                }
            });

        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }

    /**
     * Método responsável por solicitar as tarefas contidas no nó do usuário
     * @param activity
     * @param callback O método recebe como parâmetro a interface que implementa DataSnapshot
     */
    public static void getTasks(Activity activity, final CallbackDataSnapshot callback) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            GetFirebase.getFireDatabaseReferenceUsers()
                    .child(getUser().getUid())
                    .child("tasks")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            callback.onCallbackDataSnapshot(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            callback.onCallbackDatabaseError(databaseError);
                        }
                    });
        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }

    /**
     * Método responsável por solicitar as tarefas contidas no nó do usuário
     * @param activity
     * @param callback O método recebe como parâmetro a interface que implementa DataSnapshot
     */
    public static void getTaskItems(UserTask userTask, Activity activity, final CallbackDataSnapshot callback) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            GetFirebase.getFireDatabaseReferenceUsers()
                    .child(getUser().getUid())
                    .child("tasks").child(userTask.getName()).child("taskItems")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            callback.onCallbackDataSnapshot(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            callback.onCallbackDatabaseError(databaseError);
                        }
                    });
        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }

    /**
     * Método responsável por editar o status da tarefa
     * @param activity
     * @param userTask
     */
    public static void editTask(Activity activity, UserTask userTask) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            GetFirebase.getFireDatabaseReferenceUsers()
                    .child(getUser().getUid())
                    .child("tasks")
                    .child(userTask.getName()).child("status")
                    .setValue(userTask.getStatus());
        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }

    /**
     * Método responsável por deletar a tarefa do nó do usuário
     * @param activity
     * @param userTask
     */
    public static void deleteTask(Activity activity, UserTask userTask) {
        if (InternetConnection.CheckInternetConnection(activity.getApplicationContext())) {
            GetFirebase.getFireDatabaseReferenceUsers()
                    .child(getUser().getUid())
                    .child("tasks")
                    .child(userTask.getName())
                    .removeValue();
        } else {
            Log.i("INTERNET", "NOT CONECTED");
            Alerts.alertInternet(activity);
        }
    }
}
