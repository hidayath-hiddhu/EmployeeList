package com.example.retrofitsample.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitsample.R;
import com.example.retrofitsample.activities.DetailActivity;
import com.example.retrofitsample.models.Users;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    Context context;
    List<Users> usersList;


    public UsersAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.retrofit_row_item, parent , false);

        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {


        holder.employeeName.setText(usersList.get(position).getUsername());
        holder.email.setText(usersList.get(position).getEmail().toLowerCase());

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), DetailActivity.class);

            view.getContext().startActivity(intent);

        });


    }

  /*  public void setData(List<Movie> items){
        this.movieList = items;
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView employeeName, email;


        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

           employeeName = itemView.findViewById(R.id.tv_holder_employee_name);
           email = itemView.findViewById(R.id.tv_holder_employee_email);


        }
    }
}
