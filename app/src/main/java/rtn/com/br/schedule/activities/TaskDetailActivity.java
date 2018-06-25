package rtn.com.br.schedule.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.models.UserTask;

public class TaskDetailActivity extends AppCompatActivity {

    // Propertiers
    private String mStatus[] = {"Não iniciada", "Em andamento", "Cancelada", "Concluída"};
    private ArrayAdapter mArrayAdapter;
    private Integer mStatusSelected;
    private UserTask mUserTask;
    private AlertDialog.Builder mAlertDialog;

    // UI Elements
    private Spinner mSpinner;
    private Button mBtnUpdateTask;
    private TextView mTaskName;
    private TextView mTaskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Get UserTask
        Intent intent = getIntent();
        mUserTask = (UserTask) intent.getSerializableExtra("UserTask");

        // Config Adapter
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mStatus);

        // Config TaskName
        mTaskName = findViewById(R.id.textView_task_name);
        mTaskName.setText(mUserTask.getTitle());

        // Config TaskDescription
        mTaskDescription = findViewById(R.id.textView_task_description);
        mTaskDescription.setText(mUserTask.getDescription());

        // Config Spinner
        mSpinner = findViewById(R.id.spinner_status);
        mSpinner.setAdapter(mArrayAdapter);
        mSpinner.setSelection(mUserTask.getStatus());
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Config ButtonUpdate
        mBtnUpdateTask = findViewById(R.id.btn_task_update);
        mBtnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserTask.setStatus(mStatusSelected);
                FirebaseService.editTask(TaskDetailActivity.this, mUserTask);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_task_detail:
                alertRemoveTask();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void alertRemoveTask(){

        mAlertDialog = new AlertDialog.Builder(this);

        mAlertDialog.setMessage("Deseja remover essa tarefa?");

        mAlertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseService.deleteTask(TaskDetailActivity.this, mUserTask);
                finish();
            }
        });

        mAlertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        mAlertDialog.setCancelable(false);
        mAlertDialog.create();
        mAlertDialog.show();

    }
}
