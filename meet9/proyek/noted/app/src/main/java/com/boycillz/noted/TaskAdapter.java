package com.boycillz.noted;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskModel> tasks;

    public TaskAdapter(ArrayList<TaskModel> tasks) {
        this.tasks = tasks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewDescription;
        public TextView textViewDateTime;

        public ViewHolder(View view) {
            super(view);
            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewDescription = view.findViewById(R.id.textViewDescription);
            textViewDateTime = view.findViewById(R.id.textViewDateTime);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel task = tasks.get(position);
        holder.textViewTitle.setText(task.getTitle());
        holder.textViewDescription.setText(task.getDescription());
        holder.textViewDateTime.setText(task.getDateTime());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
