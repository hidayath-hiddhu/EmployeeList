package com.example.retrofitsample.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.retrofitsample.R;
import com.example.retrofitsample.RetrofitInstance;
import com.example.retrofitsample.adapters.UsersAdapter;
import com.example.retrofitsample.apis.UsersApiService;
import com.example.retrofitsample.databinding.ActivityMainBinding;
import com.example.retrofitsample.models.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

     List<Users> usersList;
     UsersAdapter usersAdapter;


    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Employee List");

        usersList = new ArrayList<>();


    }

    @Override
    protected void onResume() {
        super.onResume();
       connectionCheck();
    }

    @Override
    protected void onPause() {
        super.onPause();
        connectionCheck();
    }




    private void connectionCheck() {
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.internet_error))
                    .setPositiveButton("OK", null).show();
        }else{
            getUsers();
        }
    }

    private void getUsers() {


        UsersApiService usersApiService = RetrofitInstance.getUsersApiService();

        Call<List<Users>> call = usersApiService.getUsers();


        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(@NonNull Call<List<Users>> call, @NonNull Response<List<Users>> response) {


                if (response.code() != 200){ // 200 code is for successful response

                    Log.e("response.code!= 200", "onResponse: Not successful");
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

                List<Users> user = response.body();

                assert user != null;
                for (Users users : user){

                    usersList.add(users);

                    viewData(usersList);

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Users>> call, @NonNull Throwable t) {

                Log.e("onFailure", "onFailure: "+ t.getMessage());
            }


        });

    }

    private void viewData(List<Users> usersList) {

        usersAdapter = new UsersAdapter(this, usersList);
        binding.rvRetrofit.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRetrofit.setAdapter(usersAdapter);


    }
}
/*

    A REST API or RESTful API is an architectural style for an application program interface (API) that uses HTTP requests to access and use data.
     That data can be used to GET, PUT, POST and DELETE data types, which refers to the reading, updating, creating and deleting of operations concerning resources.
     */
