package com.example.tema3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>{
    private Context myContext;
    private List<Todo> todolist;
    private TodoFragment fragment;

    public TodoAdapter(Context myContext,List<Todo> todolist,TodoFragment fragment){
        this.myContext = myContext;
        this.todolist = todolist;
        this.fragment = fragment;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(myContext).inflate(R.layout.recyclerview_todos,parent,false);
        return new TodoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TodoViewHolder holder,int position){
        Todo todo = todolist.get(position);
        holder.title = todo.getTitle();
        holder.textViewTitle.setText(todo.getTitle());
        if(todo.getCompleted()=="yes"){
            holder.textViewComplete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_black_24dp, 0, 0, 0);
        }
        else{
            holder.textViewComplete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close_black_24dp, 0, 0, 0);
        }
    }
    @Override
    public int getItemCount(){
        return todolist.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewTitle, textViewComplete;
        String title;
        public TodoViewHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewComplete = itemView.findViewById(R.id.textViewComplete);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
           ((TodoFragment)fragment).selectTime(title);
        }

    }
}
