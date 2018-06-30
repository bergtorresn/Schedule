package rtn.com.br.schedule.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.DataOutput;
import rtn.com.br.schedule.interfaces.CallbackDatabase;
import rtn.com.br.schedule.models.TaskItem;
import rtn.com.br.schedule.models.UserTask;

public class TaskDetailFragment extends Fragment {

    // UI Elements
    private TextView mTextViewName;
    private TextView mTextViewCreated_at;
    private TextView mTextViewPriority;
    private TextView mTextViewDescription;
    private Spinner mSpinnerStatus;
    private Button mButtonSave;
    private TaskItem mTaskItem;
    private String mUserTaskUid;

    // Propertiers
    private String mArrayStatus[] = {"Não iniciada", "Em andamento", "Cancelada", "Concluída"};
    private Integer mStatusSelected;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_removetask, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.taskdetail_buttonMenu_delete:
                alertRemoveTask();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        Bundle bundle = getArguments();
        mTaskItem = (TaskItem) bundle.getSerializable("taskItem");
        mUserTaskUid = bundle.getString("uid");

        mTextViewName = view.findViewById(R.id.fragment_taskdetail_textViewName);
        mTextViewCreated_at = view.findViewById(R.id.fragment_taskdetail_textViewCreatedAt);
        mTextViewPriority = view.findViewById(R.id.fragment_taskdetail_textViewPriority);
        mTextViewDescription = view.findViewById(R.id.fragment_taskdetail_textViewDescription);
        mSpinnerStatus = view.findViewById(R.id.fragment_taskdetail_spinnerStatus);
        mButtonSave = view.findViewById(R.id.fragment_taskdetail_btnSave);

        mTextViewName.setText(Html.fromHtml(DataOutput.nameOutput(mTaskItem.getName())));
        mTextViewDescription.setText(Html.fromHtml(DataOutput.descriptionOutput(mTaskItem.getDescription())));
        mTextViewCreated_at.setText(Html.fromHtml(DataOutput.dateOutput(mTaskItem.getCreated_at())));
        mTextViewPriority.setText(Html.fromHtml(DataOutput.priorityOutput(mTaskItem.getPriority())));

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, mArrayStatus);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerStatus.setAdapter(arrayAdapter);
        mSpinnerStatus.setSelection(mTaskItem.getStatus());
        mSpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseService.updateTaskItem(getActivity(), mStatusSelected, mUserTaskUid, mTaskItem.getUid(), new CallbackDatabase() {
                    @Override
                    public void onCallback(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Não foi possível atualizar esta tarefa!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        return view;
    }

    private void alertRemoveTask() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Deseja remover essa tarefa?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseService.removeTaskItem(getActivity(), mStatusSelected, mUserTaskUid, mTaskItem.getUid(), new CallbackDatabase() {
                    @Override
                    public void onCallback(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Não foi possível remover esta tarefa!", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        }
                    }
                });
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Alert", "Não");
            }
        });

        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

}
