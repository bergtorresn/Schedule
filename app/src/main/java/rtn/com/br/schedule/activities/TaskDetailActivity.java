package rtn.com.br.schedule.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.models.UserTask;

public class TaskDetailActivity extends AppCompatActivity {

    // Propertiers
    private String mStatus[] = {"Não iniciada", "Em andamento", "Cancelada", "Concluída"};
    private ArrayAdapter mArrayAdapter;
    private Integer mStatusSelected;

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
        UserTask userTask = (UserTask) intent.getSerializableExtra("UserTask");

        // Config Adapter
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mStatus);

        // Config TaskName
        mTaskName = findViewById(R.id.textView_task_name);
        mTaskName.setText(userTask.getTitle());

        // Config TaskDescription
        mTaskDescription = findViewById(R.id.textView_task_description);
        mTaskDescription.setText(userTask.getDescription());

        // Config Spinner
        mSpinner = findViewById(R.id.spinner_status);
        mSpinner.setAdapter(mArrayAdapter);
        mSpinner.setSelection(userTask.getStatus());
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
                Log.i("TASK", "ITEM SELECIONADO " + mStatusSelected);
            }
        });
    }
}
