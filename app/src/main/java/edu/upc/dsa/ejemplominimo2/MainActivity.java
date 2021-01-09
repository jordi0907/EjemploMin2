package edu.upc.dsa.ejemplominimo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import edu.upc.dsa.ejemplominimo2.models.Element;
import edu.upc.dsa.ejemplominimo2.models.Museums;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    MuseumService museumService;
    List<Element>  elementList;

    //Para crear el recycler
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Esto es configuracion del recycler
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //Configuracion del retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://do.diba.cat/api/dataset/museus/format/")
                // .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        museumService = retrofit.create(MuseumService.class);

        if (!existSharedPreferences()) {
            LaunchLoginActivity();
        }
        else{
            getMuseums();
        }
    }

    private void LaunchLoginActivity() {

        Intent intent = new Intent(MainActivity.this ,LoginActivity.class);
        intent.putExtra("User1","jordi");
        startActivityForResult(intent,1);
    }

    private boolean existSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("UserInfo", 0);
       // SharedPreferences.Editor prefs = (SharedPreferences.Editor) getSharedPreferences("UserInfo", 0).edit();
        //prefs.clear().apply();
        String name = prefs.getString("Username", "");//"No name defined" is the default value.
        String pass = prefs.getString("Password", "");//"No name defined" is the default value.
        NotifyUser("existe Preferences" +!name.equals(""));
        //return false;
        return !name.equals("");
    }




    private void NotifyUser(String MSG){
        Toast toast = Toast.makeText(MainActivity.this,MSG,Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            String user = data.getStringExtra("User");
            String password = data.getStringExtra("Pass");
            NotifyUser("el usuario es" + user);
            NotifyUser("la contrase" + password);
            getMuseums();
        }

    }

    private void getMuseums() {
        try {
            Call<Museums> museums = museumService.getMuseums();
            /* Android Doesn't allow synchronous execution of Http Request and so we must put it in queue*/
            museums.enqueue(new Callback<Museums>() {
                @Override
                public void onResponse(Call<Museums> call, Response<Museums> response) {

                    //Retrieve the result containing in the body
                    if (response.isSuccessful()) {
                        Museums museums = response.body();
                        elementList=museums.getElements();
                        // non empty response, Mapping Json via Gson...
                        NotifyUser("Server Response Ok");
                        buildRecyclerView();
                    } else {
                        // empty response...
                        NotifyUser("Request Failed!");
                    }
                }

                @Override
                public void onFailure(Call<Museums> call, Throwable t) {
                    NotifyUser("Error,could not retrieve data!");
                }
            });
        } catch (Exception e) {
            NotifyUser("Exception: " + e.toString());
        }
    }

    private void buildRecyclerView() {
        mAdapter = new MyAdapter(elementList);
        recyclerView.setAdapter(mAdapter);
    }



}