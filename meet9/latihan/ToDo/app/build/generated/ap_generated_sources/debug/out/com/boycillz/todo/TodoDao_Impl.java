package com.boycillz.todo;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.boycillz.todo.model.Todo;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TodoDao_Impl implements TodoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Todo> __insertionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter<Todo> __deletionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter<Todo> __updateAdapterOfTodo;

  public TodoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTodo = new EntityInsertionAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `todos` (`id`,`title`,`description`,`isCompleted`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.id);
        if (value.title == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.title);
        }
        if (value.description == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.description);
        }
        final int _tmp = value.isCompleted ? 1 : 0;
        stmt.bindLong(4, _tmp);
      }
    };
    this.__deletionAdapterOfTodo = new EntityDeletionOrUpdateAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `todos` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfTodo = new EntityDeletionOrUpdateAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `todos` SET `id` = ?,`title` = ?,`description` = ?,`isCompleted` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.id);
        if (value.title == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.title);
        }
        if (value.description == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.description);
        }
        final int _tmp = value.isCompleted ? 1 : 0;
        stmt.bindLong(4, _tmp);
        stmt.bindLong(5, value.id);
      }
    };
  }

  @Override
  public void insert(final Todo todo) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTodo.insert(todo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Todo todo) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTodo.handle(todo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Todo todo) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTodo.handle(todo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Todo getTodoById(final int todoId) {
    final String _sql = "SELECT * FROM todos WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, todoId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
      final Todo _result;
      if(_cursor.moveToFirst()) {
        _result = new Todo();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _result.title = null;
        } else {
          _result.title = _cursor.getString(_cursorIndexOfTitle);
        }
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _result.description = null;
        } else {
          _result.description = _cursor.getString(_cursorIndexOfDescription);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
        _result.isCompleted = _tmp != 0;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
