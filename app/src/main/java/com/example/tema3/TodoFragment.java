package com.example.tema3;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    private String url = "https://jsonplaceholder.typicode.com/todos?userId=";
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Todo> todoList;
    private RecyclerView.Adapter adapter;
    public TodoFragment(int id){
        url = url+id;


    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.todo_fragment,container,false);
        mList = view.findViewById(R.id.recyclerview_todos);
        todoList = new ArrayList<>();
        adapter = new TodoAdapter(getActivity().getApplicationContext(),todoList,TodoFragment.this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        getTodos();
        return view;
    }

    private void getTodos(){
        ProgressDialog dialog = new ProgressDialog(getContext() , R.style.AppCompatAlertDialogStyle);
        dialog.setMessage("Please wait ...");
        dialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int index = 0; index< response.length();index++){
                    try{
                        dialog.dismiss();
                        JSONObject jsonObject = response.getJSONObject(index);
                        Todo todo = new Todo();
                        todo.setUserId(jsonObject.getInt("userId"));
                        todo.setId(jsonObject.getInt("id"));
                        todo.setTitle(jsonObject.getString("title"));
                        if(jsonObject.getBoolean("completed") == true)
                            todo.setCompleted("yes");
                        else
                            todo.setCompleted("no");
                        todoList.add(todo);
                    }catch (JSONException e){
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                dialog.dismiss();
                Toast.makeText(getContext(),"Volley error "+error.getMessage(),Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

    }
    public void selectTime(String title){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.user_fragment, new TimeFragment(title),"tag_fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
