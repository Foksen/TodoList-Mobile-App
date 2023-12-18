package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databinding.TaskEntityBinding;

import java.util.List;

// Класс адаптера, реализующий адаптер для нужных нам элементов списка (из 2 текстовых полей)
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    // Список данных в обёртке
    private final List<TaskEntity> data;
    // Разметка адаптера
    private final LayoutInflater localInflater;

    // Конструктор, передаём в него данные и разметку
    public TaskAdapter(Context context, List<TaskEntity>data) {
        this.data = data;
        this.localInflater = LayoutInflater.from(context);
    }

    // "Раздувание", преобразование разметки, создание вьюхолдера
    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskEntityBinding binding = TaskEntityBinding.inflate(localInflater, parent, false);
        return new ViewHolder(binding);
    }

    // Будет запускаться для каждого элемента списка, будет передавать его номер. По его номеру смотрим в списке
    // данных, берём оттуда данные для его заполнения
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskEntity item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.desc.setText(item.getDesc());
        if (item.getDesc().isEmpty()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,0);
            holder.title.setLayoutParams(params);
            holder.desc.setVisibility(View.GONE);
        }
    }

    // Получить количество записей
    @Override
    public int getItemCount() {
        return data.size();
    }

    // Класс элемента списка (состоит из 2 текстовых полей)
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        public ViewHolder(@NonNull TaskEntityBinding binding) {
            super(binding.getRoot());
            title = binding.title;
            desc = binding.desc;
        }
    }
}
