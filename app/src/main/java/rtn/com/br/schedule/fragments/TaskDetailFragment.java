package rtn.com.br.schedule.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.helpers.DataOutput;
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
    private UserTask mUserTask;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mArrayStatus);
        mSpinnerStatus.setAdapter(arrayAdapter);
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
               // mUserTask.setStatus(mStatusSelected);
               // FirebaseService.updateTaskItem(getActivity(), mUserTask);
            }
        });


        return view;
    }

}
