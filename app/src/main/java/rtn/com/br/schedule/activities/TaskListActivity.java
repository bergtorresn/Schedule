package rtn.com.br.schedule.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.adapters.UserTaskAdapter;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.helpers.CallbackDatabase;
import rtn.com.br.schedule.models.UserTask;

public class TaskListActivity extends AppCompatActivity {

    // - UI Elements
    private FloatingActionButton mFloatingActionButton;
    private ListView mListView;
    private AlertDialog.Builder mAlert;

    // - Properties
    private String mArrayStatus[] = {"Não iniciada", "Em andamento", "Cancelada", "Concluída"};
    private String mArrayPriority[] = {"Alta", "Média", "Baixa"};
    private List<UserTask> mListUserTasks;
    private UserTaskAdapter mUserTaskAdapter;
    private Integer mPrioritySelected;
    private Integer mStatusSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mFloatingActionButton = findViewById(R.id.btn_newTask);
        mListView = findViewById(R.id.listview_tasks);

        mListUserTasks = new ArrayList<>();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskListActivity.this, NewTaskActivity.class));
            }
        });

        fetchUserTasks();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_menu_singout:
                alertSigOut();
                break;
            case R.id.btn_menu_filter:
                alertFilter();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchUserTasks() {
        FirebaseService.getTasks(this, new CallbackDatabase() {
            @Override
            public void onCallbackDataSnapshot(DataSnapshot dataSnapshot) {
                mListUserTasks.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserTask userTask = snapshot.getValue(UserTask.class);
                    userTask.setUid(snapshot.getKey());
                    if (!mListUserTasks.contains(userTask)) {
                        mListUserTasks.add(userTask);
                    }
                }
                configListView();
            }

            @Override
            public void onCallbackDatabaseError(DatabaseError databaseError) {
                Alerts.genericAlert("Atenção", "Não foi possível se comunicar como servidor, tente novamente.", TaskListActivity.this);
            }
        });
    }

    private void configListView() {
        listViewSortbyPriority();
        mUserTaskAdapter = new UserTaskAdapter(mListUserTasks, this);

        mListView.setAdapter(mUserTaskAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserTask userTask = mListUserTasks.get(position);
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                intent.putExtra("UserTask", userTask);
                startActivity(intent);
            }
        });
    }

    private void listViewSortbyPriority() {
        Collections.sort(mListUserTasks, new Comparator<UserTask>() {
            public int compare(UserTask one, UserTask other) {
                return one.getPriority().compareTo(other.getPriority());
            }
        });
    }

    private void alertSigOut() {

        mAlert = new AlertDialog.Builder(this);
        mAlert.setMessage("Deseja sair da sua conta?");
        mAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseService.singOut();
                finish();
            }
        });

        mAlert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        mAlert.setCancelable(false);
        mAlert.create();
        mAlert.show();

    }

    private void alertFilter() {

        // 1 - Pega o layout do Spinner
        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner_layout, null);
        // 2 - Pega a instacia do Spinner
        Spinner mSpinnerStatus = mView.findViewById(R.id.spinner_dialog_status);
        Spinner mSpinnerProprity = mView.findViewById(R.id.spinner_dialog_priority);
        // 3 - Adapter para ler a lista
        ArrayAdapter<String> mAdapterStatus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mArrayStatus);
        ArrayAdapter<String> mAdapterPriority = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mArrayPriority);
        // 4 - Configura o adapter para exibir em DropDown
        mAdapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 5 - Adiciona o adapter ao Spinner
        mSpinnerStatus.setAdapter(mAdapterStatus);
        mSpinnerProprity.setAdapter(mAdapterPriority);

        mSpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerProprity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPrioritySelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAlert = new AlertDialog.Builder(this);
        mAlert.setTitle("Escolha um filtro");
        mAlert.setNeutralButton("Aplicar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sorteByPriorityAndStatus(mPrioritySelected, mStatusSelected);
            }
        });

        mAlert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        mAlert.setView(mView); // Add Spinner no Alerta
        mAlert.setCancelable(false);
        mAlert.create();
        mAlert.show();
    }

    /**
     * Método responsável por aplicar um filtro na lista de tarefas,
     * o 1º loop verifica se a tarefa tem prioridade e status informados,caso tenha, a tarefa é add em uma lista
     * o 2º loop adiciona as outras tarefas, fazendo uma ordenação por odem de add na lista.
     *
     * @param priority número da prioridade: 0 - Alta, 1 - Média, 2 - Baixa
     * @param status número do status: 0 - Não Iniciada, 1 - Em Andamento, 2 - Cancelada, 3 - Concluída
     */
    private void sorteByPriorityAndStatus(Integer priority, Integer status) {
        List<UserTask> arrayAux = new ArrayList<>();
        arrayAux.addAll(mListUserTasks);
        mListUserTasks.clear();
        for (UserTask userTask : arrayAux) {
            if (userTask.getPriority() == priority && userTask.getStatus() == status){
                mListUserTasks.add(userTask);
            }
        }
        for (UserTask userTask: arrayAux) {
            if (!mListUserTasks.contains(userTask)){
                mListUserTasks.add(userTask);
            }
        }
        mUserTaskAdapter.notifyDataSetChanged();
    }

}
