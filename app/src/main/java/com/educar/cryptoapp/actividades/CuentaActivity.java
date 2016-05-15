package com.educar.cryptoapp.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.educar.cryptoapp.AES;
import com.educar.cryptoapp.Cuenta;
import com.educar.cryptoapp.FragmentNuevo;
import com.educar.cryptoapp.Logo;
import com.educar.cryptoapp.R;
import com.educar.cryptoapp.SQLite.CCuenta;
import com.educar.cryptoapp.SQLite.CLogo;
import com.educar.cryptoapp.SQLite.DatabaseManager;

public class CuentaActivity extends AppCompatActivity {

    public static final int MODIFICATION_REQUEST_CODE = 1;

    private ImageView imagenOjo,imagenLogo;
    private TextView user,pass,nombreLogo;
    private boolean isVisible;
    private Button copiaUsuario,copiaPass,modificarCuenta,eliminarCuenta;
    private View toolbar;
    private RelativeLayout fondoImagen;
    private Cuenta cuenta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
       imagenOjo =(ImageView)findViewById(R.id.iv_ojo);
        user=(TextView)findViewById(R.id.tv_usuario);
        pass=(TextView)findViewById(R.id.tv_pass);
        copiaUsuario =(Button)findViewById(R.id.bt_copiaUsuario);
        copiaPass =(Button)findViewById(R.id.bt_copiaPass);
        modificarCuenta =(Button)findViewById(R.id.bt_modificar);
        eliminarCuenta =(Button)findViewById(R.id.bt_elminar);
        nombreLogo =(TextView)findViewById(R.id.tv_nombreLogo);
        imagenLogo =(ImageView)findViewById(R.id.iv_iconoCuenta);
        fondoImagen =(RelativeLayout)findViewById(R.id.lo_fondoImagen);
        android.support.v7.widget.Toolbar toolbar =(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_cuenta);
        setSupportActionBar(toolbar);
       // toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorYahoo));
       // nombreLogo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.ColorText_Icon));

        //se obtiene la cuenta pasada por el intent
        cuenta = (Cuenta)getIntent().getSerializableExtra("cuenta");
        //se obtiene el logo asociado a esta cuenta
        Logo logo = CLogo.getLogoById(String.valueOf(cuenta.getIcono()));
        //se desencripta el usuario y la contraseña
        try {
            user.setText(AES.desencriptar(cuenta.getUsuario(), FragmentNuevo.key));
            pass.setText(AES.desencriptar(cuenta.getPass(),FragmentNuevo.key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //se pone el color de fondo y la imagen asociado al logo
        fondoImagen.setBackgroundResource(logo.getColor());
        imagenLogo.setImageResource(logo.getImagenGrande());

        isVisible = false;


        copiaUsuario.setTextColor(ContextCompat.getColor(this,R.color.ColorPrimary));
        copiaPass.setTextColor(ContextCompat.getColor(this,R.color.ColorPrimary));
        modificarCuenta.setTextColor(ContextCompat.getColor(this,R.color.ColorText_Icon));
        eliminarCuenta.setTextColor(ContextCompat.getColor(this,R.color.ColorText_Icon));

        imagenOjo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVisible) {
                    user.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisible = true;
                    imagenOjo.setImageResource(R.drawable.ojo);
                } else {

                    user.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isVisible = false;
                    imagenOjo.setImageResource(R.drawable.ojo_tachado);
                }
            }
        });

        //evento que copia en el portapapeles el usuario
        copiaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copiarAlPortapapeles(user.getText().toString());
                Toast.makeText(CuentaActivity.this, "Copiado usuario", Toast.LENGTH_SHORT).show();
            }
        });

        //evento que copia en el portapapeles la contraseña
        copiaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copiarAlPortapapeles(pass.getText().toString());
                Toast.makeText(CuentaActivity.this, "Copiada contraseña", Toast.LENGTH_SHORT).show();
            }
        });

        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se solicita la confirmacion del borrado con un dialogo
                final AlertDialog.Builder builder = new AlertDialog.Builder(CuentaActivity.this);
                builder.setTitle("Confirmar eliminación")
                       .setMessage("Estas seguro de borrar este registro?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                //se crea un progressdialog que se mostrará mientras se realiza
                                // el borrado en la base de datos
                                ProgressDialog eliminando = new ProgressDialog(CuentaActivity.this);
                                eliminando.setCancelable(false);
                                eliminando.setMessage(getResources().getString(R.string.eliminandoRegistro));
                                //se lanza el hilo que realiza el borrado
                                new DeletedTask(CuentaActivity.this,eliminando).execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        modificarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuentaActivity.this,ModificarCuentaActivity.class);
                intent.putExtra("cuenta",cuenta);
                startActivityForResult(intent,MODIFICATION_REQUEST_CODE);
            }
        });
    }

    /**
     * Método que copia al portapapeles un texto pasado por parámetro
     * @param texto es el texto a copiar en el portapapeles
     */
    private void copiarAlPortapapeles(String texto)
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Texto copiado",texto);
        clipboard.setPrimaryClip(clip);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MODIFICATION_REQUEST_CODE){
            switch (resultCode)
            {
                case RESULT_OK:
                   // cuenta = (Cuenta)data.getSerializableExtra("cuenta");
                    recreate();


                    break;
                case RESULT_CANCELED :

                    break;
            }
        }

        }

    /**
     * Clase que muestra un dialogo de progreso al ir eliminando un registro en la base de datos
     */
    public class DeletedTask extends AsyncTask<Void,Void,Void> {

        Activity activity;
        ProgressDialog progressDialog;
        boolean registroBorrado;

        public DeletedTask(Activity act,ProgressDialog pro)
        {
            activity = act;
            progressDialog = pro;
        }

        public void onPreExecute() {
            progressDialog.show();

        }

        public void onPostExecute(Void unused) {

            progressDialog.dismiss();
            if(registroBorrado)  //si se ha realizado el borrado se vuelve a la activity portada
            {
                Intent intent = new Intent(CuentaActivity.this,PortadaActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(CuentaActivity.this, "No se ha podido eliminar el registro", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
                //se realiza la eliminación en la tabla Cuentas
                registroBorrado = CCuenta.deleteRegister(String.valueOf(cuenta.getIdCuenta()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
