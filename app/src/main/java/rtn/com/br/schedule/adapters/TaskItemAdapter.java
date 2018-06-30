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
import rtn.com.br.schedule.models.TaskItem;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.ViewHolder> {

    private List<TaskItem> mTaskItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewName;
        private TextView mTextViewCreated_at;
        private TextView mTextViewPriority;
        private TextView mTextViewStatus;

        public ViewHolder(View v) {
            super(v);

            mTextViewName = v.findViewById(R.id.adapter_textViewName);
            mTextViewCreated_at = v.findViewById(R.id.adapter_textViewCreatedAt);
            mTextViewPriority = v.findViewById(R.id.adapter_textViewPriority);
            mTextViewStatus = v.findViewById(R.id.adapter_textViewStatus);
        }
    }

    public TaskItemAdapter(List<TaskItem> taskItems) {
        mTaskItems = taskItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextViewName.setText(Html.fromHtml(DataOutput.nameOutput(mTaskItems.get(position).getName())));
        holder.mTextViewCreated_at.setText(Html.fromHtml(DataOutput.dateOutput(mTaskItems.get(position).getCreated_at())));
        holder.mTextViewPriority.setText(Html.fromHtml(DataOutput.priorityOutput(mTaskItems.get(position).getPriority())));
        holder.mTextViewStatus.setText(Html.fromHtml(DataOutput.statusOutput(mTaskItems.get(position).getStatus())));
    }

    @Override
    public int getItemCount() {
        return mTaskItems.size();
    }

}