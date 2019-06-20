package com.example.ovais.medicure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppointDoctorActivity extends AppCompatActivity {

    public static final String URL = "http://ovaisbutt.000webhostapp.com/medicure/getDoctors.php";

    RecyclerView recyclerView;
    ProductAdapter adapter;

    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_doctor);
        productList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewDoctor);
        recyclerView.setHasFixedSize(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDial();
        loadDoctors();
    }

    private void loadDoctors()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray products = new JSONArray(response);
                    for(int i = 0 ; i<products.length(); i++)
                    {
                        JSONObject productObject = products.getJSONObject(i);

                        String id = productObject.getString("did");
                        String name = productObject.getString("dname");
                        String rating = productObject.getString("drating");
                        String specialization = productObject.getString("dspecialization");
                        String fees = productObject.getString("dfees");
                        String address = productObject.getString("daddress");
                        String timing = productObject.getString("dcontact");
                        String contact = productObject.getString("dtimings");
                        String landline = productObject.getString("dlandline");
                        String email = productObject.getString("demail");

                        Product product = new Product(id, name, rating, specialization, fees, address, timing, contact, landline, email);
                        productList.add(product);

                    }
                    adapter = new ProductAdapter(AppointDoctorActivity.this, productList);
                    recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AppointDoctorActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void progressDial () {
        class ProgressDialogue   extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected String doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(AppointDoctorActivity.this, "Fetching Data", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }
        }
    }
}

