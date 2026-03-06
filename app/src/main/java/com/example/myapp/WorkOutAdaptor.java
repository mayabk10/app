package com.example.myapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class WorkOutAdaptor extends ListAdapter<WorkOuts,WorkOutAdaptor.WorkOutViewHolder> {
private List<WorkOuts> workOuts;


    public WorkOutAdaptor(List<WorkOuts> workOuts) {
        super(new DiffUtil.ItemCallback<WorkOuts>() {
            @Override
            public boolean areItemsTheSame(@NonNull WorkOuts oldItem, @NonNull WorkOuts newItem) {
                return oldItem.getDate() == newItem.getDate();
            }

            @Override
            public boolean areContentsTheSame(@NonNull WorkOuts oldItem, @NonNull WorkOuts newItem) {
                return false;
            }
        });
        this.workOuts = workOuts;
    }
    public void setWorkOuts(List<WorkOuts> workOuts){
        this.workOuts=workOuts;
        submitList(workOuts);
    }

    public List<WorkOuts> getWorkOuts() {
        return workOuts;
    }

    public class WorkOutViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public WorkOutViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView2);
        }
    }

    @NonNull
    @Override
    public WorkOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_out,parent,false);
        WorkOutViewHolder workOutViewHolder = new WorkOutViewHolder(view);
        return workOutViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkOutViewHolder holder, int position) {
        holder.textView.setText(workOuts.get(position).toString());
    }
@Override
    public int getItemCount(){
        return workOuts.size();
}

}
