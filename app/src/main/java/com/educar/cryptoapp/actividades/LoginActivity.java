package com.educar.cryptoapp.actividades;


import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.educar.cryptoapp.R;
import com.educar.cryptoapp.SQLite.DatabaseManager;
import com.educar.cryptoapp.SQLite.DbHelper;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextView avisoPass,acceso;
    private Button acceder;
    private DbHelper dbHelper;
    private DatabaseManager db;
    private TextInputLayout til_pass;
    private EditText pass,repass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setData();
        setListeners();

       /* check=(ImageView)findViewById(R.id.iv_button);
        SVG svg = SVGParser.getSVGFromResource(getResources(),R.raw.check2);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                check.setAnimation(animFadein);
                startActivity(new Intent(LoginActivity.this,PortadaActivity.class));
            }
        });*/


    }

    public void init()
    {
        avisoPass=(TextView)findViewById(R.id.tv_avisoPass);
        acceso =(TextView)findViewById(R.id.tv_acceso);
        acceder=(Button)findViewById(R.id.bt_acceder);
        dbHelper = new DbHelper(getApplicationContext().getApplicationContext());
        DatabaseManager.initializeInstance(dbHelper);
        til_pass =(TextInputLayout)findViewById(R.id.til_pass);
        pass =(EditText)findViewById(R.id.et_pass);
        repass =(EditText)findViewById(R.id.et_rePass);
    }


    public void setData()
    {
        acceder.setTextColor(ContextCompat.getColor(this, R.color.blanco_perfecto));
        avisoPass.setTextColor(ContextCompat.getColor(this, R.color.blanco_perfecto));
        acceso.setTextColor(ContextCompat.getColor(this, R.color.amarillo));



    }

    public void setListeners()
    {
        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!pass.getText().toString().trim().equalsIgnoreCase(""))
                {
                   if(!repass.getText().toString().trim().equalsIgnoreCase(""))
                   {
                         Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                         acceder.setAnimation(animFadein);
                         startActivity(new Intent(LoginActivity.this,PortadaActivity.class));
                   }
                    else
                   {
                       repass.setError("Este campo no puede estar vacío");
                   }
                }
                else
                {
                    pass.setError("Este campo no puede estar vacío");
                }


            }
        });

        //evento sobre el edittext pass
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!pass.getText().toString().equalsIgnoreCase("")) {
                    pass.setError(null);
                }
            }
        });

        //evento sobre el edittext de repetir contraseña
        repass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!repass.getText().toString().equalsIgnoreCase("")) {
                    repass.setError(null);
                }
            }
        });
    }





}
