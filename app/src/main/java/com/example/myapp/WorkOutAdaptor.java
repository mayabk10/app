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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WorkOutAdaptor extends ListAdapter<WorkOuts,WorkOutAdaptor.WorkOutViewHolder> {
private List<WorkOuts> workOuts;
    public interface OnWorkOutClickListener{
        void onWorkOutClick(WorkOuts workOuts);
    }
    private OnWorkOutClickListener onWorkOutClickListener;

    public WorkOutAdaptor(List<WorkOuts> workOuts,OnWorkOutClickListener onWorkOutClickListener) {
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
        this.onWorkOutClickListener = onWorkOutClickListener;
    }
    public void setWorkOuts(List<WorkOuts> workOuts){
        this.workOuts=workOuts;
        submitList(workOuts);
    }

    public List<WorkOuts> getWorkOuts() {
        return workOuts;
    }

    public class WorkOutViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate;
        public TextView textViewKind;
        public TextView textViewExercise;

        public WorkOutViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewKind = itemView.findViewById(R.id.textViewKind);
            textViewExercise = itemView.findViewById(R.id.textViewExercise);
        }
    }

    @NonNull
    @Override
    public WorkOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_out,parent,false);
        WorkOutViewHolder workOutViewHolder = new WorkOutViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adaptorPosition = workOutViewHolder.getAbsoluteAdapterPosition();
                WorkOuts workOuts1 = workOuts.get(adaptorPosition);
                onWorkOutClickListener.onWorkOutClick(workOuts1);
            }
        });
        return workOutViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkOutViewHolder holder, int position) {

        holder.textViewDate.setText(getDate(workOuts.get(position).getDate()));
        holder.textViewKind.setText(workOuts.get(position).getKind());
        holder.textViewExercise.setText(workOuts.get(position).getMainExercise());
    }
@Override
    public int getItemCount(){
        return workOuts.size();
}
    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(milliSeconds));
        return dateString;
    }

}

