package com.spacece.videolibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private final ArrayList<Topic> list=new ArrayList<Topic>();

    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerAdapter.RecyclerViewClickListener listener;

    private RequestQueue mQueue;

    private final String url = "http://13.127.85.161/SpacTube/api_pagination.php?pagenum=1&pagelen=2";

    /*private void generateTopicsList(JSONArray response) {
        for(int i=0; i< response.length();i++){
            Log.i("JSON", "List generator");
            JSONObject response_element = null;
            try {
                response_element = response.getJSONObject(i);
                     Topic newTopic;
                         newTopic = new Topic(response_element.getString("vId"),
                        response_element.getString("topicName"),
                        response_element.getString("description"),
                        response_element.getString("duration"));
                list.add(newTopic);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/

    // Access the RequestQueue through your singleton class.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mQueue = Volley.newRequestQueue(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject response_element = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));

                            Topic newTopic = new Topic(response_element.toString(Integer.parseInt("v_id")),
                                    response_element.getString("title"),
                                    response_element.getString("filter"),
                                    response_element.getString("length"),
                                    response_element.getString("v_URL"),
                                    response_element.getString("v_date"),
                                    response_element.getString("v_uni_no"),
                                    response_element.getString("desc"),
                                    response_element.getString("status"));

                            list.add(newTopic);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

        recyclerView= findViewById(R.id.RecycleView);

        setAdapter();
    }

    private void setAdapter() {
        setOnClickListener();
        RecyclerAdapter adapter = new RecyclerAdapter(list,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), topic_activity.class);
                intent.putExtra("topic_name", list.get(position).getTopic_name());
                intent.putExtra("vid", list.get(position).getV_id());
                intent.putExtra("discrp", list.get(position).getDescription());

                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.search,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
   }
