package com.example.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.databinding.ActivityCreationBinding;

import java.util.Objects;

public class CreationActivity extends AppCompatActivity {
    ActivityCreationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Устанавливаем ActionBar, включаем переход на родительскую активность (указана в манифесте)
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Развёртка для меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_creation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Действия при выборе элементов меню (кнопки сохранить)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_task) {
            if (binding.editTitle.getText().toString().isEmpty()) {
                binding.editTitle.setError("Обязательное поле");
            }
            else {
                new InsertTaskTask().execute();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // Класс-обёртка для асинхронного обновления базы данных
    private class InsertTaskTask extends AsyncTask<Void, Void, Boolean> {
        private ContentValues taskValues;

        @Override
        protected void onPreExecute() {
            taskValues = new ContentValues();
            taskValues.put("TITLE", binding.editTitle.getText().toString());
            taskValues.put("DESCRIPTION", binding.editDesc.getText().toString());
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteOpenHelper todolistDatabaseHelper = new TodolistDatabaseHelper(CreationActivity.this);       // Объект класса-обёртки для работы с БД
            try {
                SQLiteDatabase db = todolistDatabaseHelper.getReadableDatabase();                    // Объект базы данных
                db.insert("TASKS", null, taskValues);
                db.close();
                return true;
            } catch (SQLiteException e) {   // Обработка исключений
                Log.e("TODOLIST", Objects.requireNonNull(e.getMessage()));
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(CreationActivity.this, "Задача добавлена", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(CreationActivity.this, "Ошибка при добавлении!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
