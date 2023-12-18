package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupButton();
        setupAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAdapter();
    }

    private void setupButton() {
        // Переход на другую активности по ФАБ
        binding.fab.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, CreationActivity.class);
            startActivity(intent);
        });
    }

    private void setupAdapter() {
        new SetupAdapterTask().execute();
    }

    private class SetupAdapterTask extends AsyncTask<Void, Void, Boolean> {
        ArrayList<TaskEntity> tasksList;        // Список, куда будут записаны данные из БД

        @Override
        protected void onPreExecute() {
            tasksList = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteOpenHelper todolistDatabaseHelper = new TodolistDatabaseHelper(MainActivity.this);       // Объект класса-обёртки для работы с БД
            try {
                SQLiteDatabase db = todolistDatabaseHelper.getReadableDatabase();                    // Объект базы данных
                Cursor tasksCursor = db.query(                                                       // Курсор для чтения БД
                        "TASKS",
                        new String[] {"TITLE", "DESCRIPTION"},
                        null, null, null, null, null
                );
                if (tasksCursor.moveToFirst()) {         // Чтение записей, запись в список
                    do {
                        String title = tasksCursor.getString(0);
                        String desc = tasksCursor.getString(1);
                        tasksList.add(new TaskEntity(title, desc));
                    } while (tasksCursor.moveToNext());
                }
                tasksCursor.close();
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
                // Создание адаптера, наполнение контентом из списка записей, привязка к RecyclerView
                TaskAdapter adapter = new TaskAdapter(MainActivity.this, tasksList);
                binding.recView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                binding.recView.setAdapter(adapter);
            }
            else {
                Toast.makeText(MainActivity.this, "Ошибка при чтении базы данных", Toast.LENGTH_SHORT).show();
            }
        }
    }
}