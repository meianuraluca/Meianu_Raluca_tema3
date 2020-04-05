package com.example.tema3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersViewHolder>{
    private Context myContext;
    private List<User> userList;
    private UserFragment frag;

    public UserAdapter(Context myContext,List<User> userList, UserFragment frag){
        this.frag = frag;
        this.myContext = myContext;
        this.userList = userList;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(myContext).inflate(R.layout.recyclerview_user,parent,false);
        return new UsersViewHolder(view);
    }
    @Override
    public void onBindViewHolder(UsersViewHolder holder,int position){
        User user = userList.get(position);
        holder.id = position;
        holder.textViewName.setText(user.getName());
        holder.textViewUserName.setText(user.getUserName());
        holder.textViewEmail.setText(user.getEmail());

    }
    @Override
    public int getItemCount(){
        return userList.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewName, textViewEmail, textViewUserName;
        int id;
        public UsersViewHolder(View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            ((UserFragment)frag).gotoTodo(id+1);
        }

    }
}
