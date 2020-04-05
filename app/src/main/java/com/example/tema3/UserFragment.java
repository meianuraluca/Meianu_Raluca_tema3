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

public class UserFragment extends Fragment {
    private String url = "https://my-json-server.typicode.com/MoldovanG/JsonServer/users?fbclid=IwAR2f8YWy20R9kpbpWwBkGhfwgSL9p7h8WnnIeT0YwGCSguG4jAZLV1im4GE";
    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<User> userList;
    private RecyclerView.Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.user_fragment,container,false);
        mList = view.findViewById(R.id.recyclerview_users);
        userList = new ArrayList<>();
        adapter = new UserAdapter(getActivity().getApplicationContext(),userList,UserFragment.this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        getUsers();
        return view;

    }
    private void getUsers(){
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
                        User user = new User();
                        user.setId(jsonObject.getInt("id"));
                        user.setName(jsonObject.getString("name"));
                        user.setUserName(jsonObject.getString("username"));
                        user.setEmail(jsonObject.getString("email"));
                        userList.add(user);
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

                Toast.makeText(getContext(),"Volley error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

    }

    public void gotoTodo(int id){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.user_fragment, new TodoFragment(id));
        transaction.addToBackStack(null);
        transaction.commit();
    }


    }