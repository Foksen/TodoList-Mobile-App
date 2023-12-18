package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {
        // Создание адаптера, наполнение контентом, привязка к RecyclerView
        TaskAdapter adapter = new TaskAdapter(this, new ArrayList<>(Arrays.asList(Database.data)));
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(adapter);

        // Переход на другую активности по ФАБ
        binding.fab.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, CreationActivity.class);
            startActivity(intent);
        });
    }
}