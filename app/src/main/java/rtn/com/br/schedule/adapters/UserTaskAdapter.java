package rtn.com.br.schedule.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rtn.com.br.schedule.R;
import rtn.com.br.schedule.helpers.DataOutput;
import rtn.com.br.schedule.models.UserTask;

public class UserTaskAdapter extends RecyclerView.Adapter<UserTaskAdapter.ViewHolder> {

    private List<UserTask> mUserTasks;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewName;
        private TextView mTextViewCreated_at;

        public ViewHolder(View v) {
            super(v);

            mTextViewName = v.findViewById(R.id.adapter_textViewName);
            mTextViewCreated_at = v.findViewById(R.id.adapter_textViewCreatedAt);
        }
    }

    public UserTaskAdapter(List<UserTask> userTasks) {
        mUserTasks = userTasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextViewName.setText(Html.fromHtml(DataOutput.nameOutput(mUserTasks.get(position).getName())));
        holder.mTextViewCreated_at.setText(Html.fromHtml(DataOutput.dateOutput(mUserTasks.get(position).getCreated_at())));
    }

    @Override
    public int getItemCount() {
        return mUserTasks.size();
    }

}
