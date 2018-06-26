package rtn.com.br.schedule.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.models.UserTask;

/**
 * Created by bergtorres on 24/06/2018
 */
public class UserTaskAdapter extends BaseAdapter {

    private List<UserTask> mUserTasks;
    private Activity mActivity;

    private TextView mTaskName;
    private TextView mTaskPriority;
    private TextView mTaskStatus;

    public UserTaskAdapter(List<UserTask> userTasks, Activity activity) {
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

        mTaskName = view.findViewById(R.id.textView_title_userTaskList);
        mTaskStatus = view.findViewById(R.id.textView_status_userTaskList);
        mTaskPriority = view.findViewById(R.id.textView_priority_userTaskList);

        mTaskName.setText("Nome da tarefa: " + userTask.getTitle());

        String status = "";
        switch (userTask.getStatus()) {
            case 0: status = "Não iniciada";
                break;
            case 1: status = "Em andamento";
                break;
            case 2: status = "Cancelada";
                break;
            case 3: status = "Concluída";
                break;
            default:
                break;
        }

        mTaskStatus.setText("Status: " + status);

        String priority = "";
        switch (userTask.getPriority()) {
            case 1: priority = "Alta";
                break;
            case 2: priority = "Média";
                break;
            case 3: priority = "Baixa";
                break;
            default:
                break;
        }

        mTaskPriority.setText("Prioridade: " + priority);

        return view;
    }
}
