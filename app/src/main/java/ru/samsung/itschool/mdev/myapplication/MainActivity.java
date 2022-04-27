package ru.samsung.itschool.mdev.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.samsung.itschool.mdev.myapplication.adapter.MyAdapter;
import ru.samsung.itschool.mdev.myapplication.api.AnekdotAPI;
import ru.samsung.itschool.mdev.myapplication.model.Anekdot;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://umorili.herokuapp.com/";
    private AnekdotAPI api;
    private RecyclerView recyclerView;
    private ArrayList<Anekdot> anekdotArrayList = new ArrayList<>();
    private EditText ed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        ed = findViewById(R.id.editTextTextPersonName);
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // retrofit request
            }
        });
        */

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MyAdapter myAdapter = new MyAdapter(anekdotArrayList);
        recyclerView.setAdapter(myAdapter);

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
        api = retrofit.create(AnekdotAPI.class);
        api.getAnekdotList("anekdot.ru",15,"new anekdot").enqueue(new Callback<ArrayList<Anekdot>>() {
            @Override
            public void onResponse(Call<ArrayList<Anekdot>> call, Response<ArrayList<Anekdot>> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    anekdotArrayList.addAll(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Anekdot>> call, Throwable t) {
                    Log.d("RRR",t.getMessage());
            }
        });


    }
}