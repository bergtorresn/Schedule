package rtn.com.br.schedule.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.models.UserTask;

public class TaskDetailActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private Button mBtnUpdateTask;
    private String mStatus[] = {"Não iniciada", "Em andamento", "Cancelada", "Concluída"};
    private ArrayAdapter mArrayAdapter;
    private Integer mStatusSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        mBtnUpdateTask = findViewById(R.id.btn_task_update);
        mSpinner = findViewById(R.id.spinner_status);
        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mStatus);
        mSpinner.setAdapter(mArrayAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBtnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TASK", "ITEM SELECIONADO " + mStatusSelected);
            }
        });
    }
}
