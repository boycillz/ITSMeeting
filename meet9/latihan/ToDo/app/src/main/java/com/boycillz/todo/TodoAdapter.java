package com.boycillz.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boycillz.todo.model.Todo;

import java.util.List;
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> todoList;
    private OnItemClickListener listener;

    public TodoAdapter(List<Todo> todoList, OnItemClickListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Todo todo = todoList.get(position);
        holder.tvTitle.setText(todo.title);
        holder.tvDescription.setText(todo.description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(todo);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemDelete(todo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Todo todo);
        void onItemDelete(Todo todo);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;
        private ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
