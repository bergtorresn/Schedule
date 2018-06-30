package rtn.com.br.schedule.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.interfaces.CallbackDatabase;
import rtn.com.br.schedule.models.TaskItem;
import rtn.com.br.schedule.models.UserTask;

public class CreateTaskItemFragment extends Fragment {

    private Button mButtonSave;
    private EditText mEditTextName;
    private EditText mEditTextDescription;
    private RadioGroup mRadioGroupPriority;
    private RadioButton mRadioButtonSelected;
    private UserTask mUserTask;

    public CreateTaskItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_task_item, container, false);

        Bundle bundle = getArguments();
        mUserTask = (UserTask) bundle.getSerializable("userTask");

        mEditTextName = view.findViewById(R.id.fragment_createItemTask_edtName);
        mEditTextDescription = view.findViewById(R.id.fragment_createItemTask_edtDescription);
        mRadioGroupPriority = view.findViewById(R.id.fragment_createItemTask_radioGrp);
        mButtonSave = view.findViewById(R.id.fragment_createItemTask_btnSave);

        mButtonSave.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                String description = mEditTextDescription.getText().toString();
                Integer radioButtonSelected = mRadioGroupPriority.getCheckedRadioButtonId();

                if (!name.isEmpty()) {
                    if (!description.isEmpty()) {
                        TaskItem taskItem = new TaskItem();
                        if (radioButtonSelected > 0) {
                            mRadioButtonSelected = view.findViewById(radioButtonSelected);
                            switch (mRadioButtonSelected.getId()) {
                                case R.id.fragment_createItemTask_radioBtnHigh:
                                    taskItem.setPriority(0);
                                    break;
                                case R.id.fragment_createItemTask_radioBtnAvarage:
                                    taskItem.setPriority(1);
                                    break;
                                case R.id.fragment_createItemTask_radioBtnLow:
                                    taskItem.setPriority(2);
                                    break;
                            }
                            taskItem.setName(name);
                            taskItem.setDescription(description);
                            taskItem.setStatus(0);
                            taskItem.setCreated_at(new Date());

                            createNewTaskItem(mUserTask.getUid(), taskItem);
                        } else {
                            Alerts.genericAlert("Atenção", "Selecione uma opção de prioridade para esta tarefa.", getActivity());
                        }
                    } else {
                        Alerts.genericAlert("Atenção", "Informe uma descrição para esta tarefa.", getActivity());
                    }
                } else {
                    Alerts.genericAlert("Atenção", "Informe um nome para esta tarefa.", getActivity());
                }

            }
        });

        return view;
    }

    private void createNewTaskItem(String userTaskUid, TaskItem taskItem) {
        FirebaseService.createTaskItem(userTaskUid, taskItem, getActivity(), new CallbackDatabase() {
            @Override
            public void onCallback(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Tarefa criada com sucesso!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    Alerts.genericAlert("Atenção", "Não foi possível criar esta tarefa, tente novamente.", getActivity());
                }
            }
        });
    }

}
