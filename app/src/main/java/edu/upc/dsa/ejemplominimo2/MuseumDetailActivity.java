package edu.upc.dsa.ejemplominimo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MuseumDetailActivity extends AppCompatActivity {

    TextView adrecaNomT;
    TextView adrecaT;
    TextView codipostalT;
    ImageView escutT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);

        Intent intent = getIntent();
        String adrecaNom= intent.getStringExtra("adrecaNom");
        NotifyUser("la adreca" + adrecaNom);
        String adreca= intent.getStringExtra("adreca");
        String codiPostal= intent.getStringExtra("codiPostal");
        String escut= intent.getStringExtra("escut");

        adrecaNomT =  (TextView)findViewById(R.id.adrecaNomText);
        adrecaT =  (TextView)findViewById(R.id.adrecaText);
        codipostalT = (TextView)findViewById(R.id.codiPostalText);
        escutT = (ImageView) findViewById(R.id.escut);

        adrecaNomT.setText(adrecaNom);
        adrecaT.setText(adreca);
        codipostalT.setText(codiPostal);
        Picasso.get().load(escut).into(escutT);




    }












    private void NotifyUser(String MSG){
        Toast toast = Toast.makeText(MuseumDetailActivity.this,MSG,Toast.LENGTH_SHORT);
        toast.show();
    }





}