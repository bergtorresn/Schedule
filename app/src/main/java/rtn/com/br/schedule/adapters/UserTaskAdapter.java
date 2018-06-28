package rtn.com.br.schedule.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.helpers.UserTaskOutput;
import rtn.com.br.schedule.models.UserTask;

/**
 * Created by bergtorres on 24/06/2018
 */
public class UserTaskAdapter extends BaseAdapter {

    private ArrayList<UserTask> mUserTasks;
    private Activity mActivity;

    private TextView mTaskName;
    private TextView mTaskPriority;
    private TextView mTaskStatus;
    private TextView mTaskCreatedAt;

    public UserTaskAdapter(ArrayList<UserTask> userTasks, Activity activity) {
        mUserTasks = userTasks;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mUserTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = mActivity.getLayoutInflater().
                inflate(R.layout.list_usertask_layout, parent, false);

        UserTask userTask = mUserTasks.get(position);

        mTaskName = view.findViewById(R.id.usertaskadapter_textViewName);
        mTaskStatus = view.findViewById(R.id.usertaskadapter_textViewStatus);
        mTaskPriority = view.findViewById(R.id.usertaskadapter_textViewPriority);
        mTaskCreatedAt = view.findViewById(R.id.usertaskadapter_textViewCreatedAt);

        mTaskName.setText("Nome da tarefa: " + userTask.getTitle());
        mTaskCreatedAt.setText(UserTaskOutput.dateOutput(userTask.getCreated_at()));

        mTaskStatus.setText(UserTaskOutput.statusOutput(userTask.getStatus()));
        mTaskPriority.setText(UserTaskOutput.priorityOutput(userTask.getPriority()));

        return view;
    }
}
