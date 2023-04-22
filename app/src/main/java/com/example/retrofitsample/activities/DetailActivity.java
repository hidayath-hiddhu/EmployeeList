package com.example.retrofitsample.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofitsample.R;
import com.example.retrofitsample.RetrofitInstance;
import com.example.retrofitsample.apis.UsersApiService;
import com.example.retrofitsample.models.Users;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView id, email, name, phoneNumber, address, companyName, webSite;
    Users usersListDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Employee Detail");

        id = findViewById(R.id.tv_id);
        email = findViewById(R.id.tv_email);
        name = findViewById(R.id.tv_name);
        phoneNumber = findViewById(R.id.tv_phone_number);
        address = findViewById(R.id.tv_address);
        companyName = findViewById(R.id.tv_company_name);
        webSite = findViewById(R.id.tv_website);



        connectionCheck();
    }



    private void connectionCheck() {
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){

            new AlertDialog.Builder(DetailActivity.this)
                    .setTitle(getResources().getString(R.string.employee_detail))
                    .setMessage(getResources().getString(R.string.internet_error))
                    .setPositiveButton("OK", null).show();
        }else{
            getUsersDetails();
        }
    }

    private void getUsersDetails() {



        UsersApiService usersApiService = RetrofitInstance.getUsersApiService();

        Call<Users> call = usersApiService.getUserDetails();


        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {


                if (response.code() != 200){ // 200 code is for successful response

                    Log.e("response.code!= 200", "onResponse: Not successful");
                    Toast.makeText(DetailActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

                usersListDetail  = response.body();


                assert usersListDetail != null;

                String strId = String.valueOf(usersListDetail.getId());

                id.setText(strId);
               name.setText(usersListDetail.getUsername());
               email.setText(usersListDetail.getEmail().toLowerCase());
               phoneNumber.setText(usersListDetail.getPhone());
              address.setText((usersListDetail.getAddress().getSuite() +","+ usersListDetail.getAddress().getStreet() +",\n"+
                      usersListDetail.getAddress().getCity() +"-" + usersListDetail.getAddress().getZipcode()));

              companyName.setText(usersListDetail.getCompany().getName());
              webSite.setText(usersListDetail.getWebsite());


            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {

                Log.e("onFailure", "onFailure: "+ t.getMessage());
            }


        });


    }



}