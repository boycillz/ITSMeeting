package com.boycillz.todo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.boycillz.todo.model.Todo;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todos WHERE id = :todoId")
    Todo getTodoById(int todoId);

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    List<Todo> getAllTodos();
}
