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

import java.util.Date;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.firebase.FirebaseService;
import rtn.com.br.schedule.helpers.Alerts;
import rtn.com.br.schedule.interfaces.CallbackDatabase;
import rtn.com.br.schedule.models.UserTask;

public class CreateTaskFragment extends Fragment {

    private Button mButtonSend;
    private EditText mEditTextName;
    private RadioGroup mRadioGroupPriority;
    private RadioButton mRadioButtonSelected;

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        mEditTextName = view.findViewById(R.id.fragment_newtask_edittexttaskname);
        mRadioGroupPriority = view.findViewById(R.id.fragment_newtask_radiogrouppriority);
        mButtonSend = view.findViewById(R.id.fragment_newtask_buttonsend);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserTask userTask = new UserTask();
                String name = mEditTextName.getText().toString();
                Integer radioButtonSelected = mRadioGroupPriority.getCheckedRadioButtonId();

                if (radioButtonSelected > 0){
                    mRadioButtonSelected = view.findViewById(radioButtonSelected);
                    switch (mRadioButtonSelected.getId()){
                        case R.id.fragment_newtask_radiobuttonhigh:
                            userTask.setPriority(0);
                            break;
                        case R.id.fragment_newtask_radiobuttonavarage:
                            userTask.setPriority(1);
                            break;
                        case R.id.fragment_newtask_radiobuttonlow:
                            userTask.setPriority(2);
                            break;
                    }
                }

                userTask.setName(name);
                userTask.setStatus(0);
                userTask.setCreated_at(new Date());

                sendNewTask(userTask);
            }
        });

        return view;
    }

    private void sendNewTask(UserTask userTask) {
        FirebaseService.createUserTask(userTask, getActivity(), new CallbackDatabase() {
            @Override
            public void onCallback(Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("TASK", "SUCCESS CREATE TASK");
                    Toast.makeText(getActivity(), "Tarefa criada com sucesso!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    Alerts.genericAlert("Atenção", "Não foi possível criar esta tarefa, tente novamente.", getActivity());
                }
            }
        });
    }

}
