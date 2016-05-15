package com.educar.cryptoapp.actividades;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.educar.cryptoapp.AES;
import com.educar.cryptoapp.Categoria;
import com.educar.cryptoapp.Cuenta;
import com.educar.cryptoapp.FragmentNuevo;
import com.educar.cryptoapp.Logo;
import com.educar.cryptoapp.R;
import com.educar.cryptoapp.SQLite.CCategoria;
import com.educar.cryptoapp.SQLite.CCuenta;
import com.educar.cryptoapp.SQLite.CLogo;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ModificarCuentaActivity extends AppCompatActivity {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;

    private MaterialBetterSpinner categorias;
    private TextInputLayout til_nombre,til_usuario,til_pass;
    private TextView textoModificar;
    private ImageView iconoRegistro,info;
    private Button aceptar,cancelar,addIcon;
    private String catego;
    private Logo logo;
    private Categoria categoria;
    private String nombreRegistro,usuario,contraseña;
    private int idLogo;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private IconoAdapter adapter;
    private int botonPulsado;
    private Cuenta cuenta;
    private EditText nomRegistro,usu,contra;
    private boolean imagenBorrada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cuenta);
        setInit();
        setData();
        setListener();

    }

    /**
     * Metodo que inicializa los componentes de la activity
     */
    public void setInit()
    {
        til_nombre =(TextInputLayout)findViewById(R.id.til_nombreRegistroModificar);
        til_usuario =(TextInputLayout)findViewById(R.id.til_registerUsuarioModificar);
        til_pass = (TextInputLayout)findViewById(R.id.til_registerPassModificar);
        textoModificar =(TextView)findViewById(R.id.tv_modificarDatos);
        aceptar =(Button)findViewById(R.id.bt_aceptarModificacion);
        cancelar =(Button)findViewById(R.id.bt_cancelarModificacion);
        addIcon = (Button)findViewById(R.id.bt_seleccionaIconoModificar);
        iconoRegistro =(ImageView)findViewById(R.id.iv_imageCuentaModificar);
        info =(ImageView)findViewById(R.id.iv_infoModificar);
        nomRegistro=(EditText)findViewById(R.id.et_nombreRegistroModificar);
        usu =(EditText)findViewById(R.id.et_registerUsuarioModificar);
        contra =(EditText)findViewById(R.id.et_registerPassModificar);
        categorias = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinnerModificar);
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar_modificarCuenta);
        setSupportActionBar(toolbar);
        cuenta = (Cuenta)getIntent().getSerializableExtra("cuenta");
        logo = CLogo.getLogoById(String.valueOf(cuenta.getIcono()));
    }

    /**
     * Método que añade datos a los componentes de la activity
     */
    public void setData()
    {
        addIcon.setTextColor(ContextCompat.getColor(ModificarCuentaActivity.this, R.color.ColorText_Icon));
        //se obtiene los nombres de las categorias de la tabla Categorias y se cargan en el spinner
        final ArrayList<String> nombreCategorias =  CCategoria.getAllNombresCategorias();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ModificarCuentaActivity.this,
                android.R.layout.simple_dropdown_item_1line, nombreCategorias);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(arrayAdapter);
        textoModificar.setTextColor(ContextCompat.getColor(ModificarCuentaActivity.this, R.color.ColorPrimary));
        aceptar.setTextColor(ContextCompat.getColor(ModificarCuentaActivity.this, R.color.ColorText_Icon));
        cancelar.setTextColor(ContextCompat.getColor(ModificarCuentaActivity.this, R.color.ColorText_Icon));
        iconoRegistro.setImageResource(logo.getImagen()); //se añade el icono
        categorias.setText(arrayAdapter.getItem(cuenta.getCategoria()-1)); //se selecciona en el spinner la categoria
        catego = arrayAdapter.getItem(cuenta.getCategoria() - 1); //se guarda el nombre de la categoria
        til_nombre.getEditText().setText(cuenta.getRegistro()); //se añade el nombre de registro
        try {
            til_usuario.getEditText().setText((AES.desencriptar(cuenta.getUsuario(), FragmentNuevo.key))); //se añade el usuario desencriptado
            til_pass.getEditText().setText(AES.desencriptar(cuenta.getPass(), FragmentNuevo.key)); //se añade la pass desencriptada
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    public void setListener()
    {
        //evento que lanza un dialogo que permite al usuario escoger un icono
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    botonPulsado = 0;
                    testPermission();
                } else {
                    showDialogIcon();
                }
            }
        });

        //evento que lanza un dialogo que muestre una informacion sobre los iconos
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    botonPulsado = 1;
                    testPermission();
                }
                else
                {
                    showDialogInfo();

                }

            }
        });

        //evento sobre el spinner categorias que guarda el nombre de la categoría seleccionada
        categorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                catego = (String) parent.getItemAtPosition(position);


            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (catego != null) { //se comprueba que se ha seleccionado una categoria

                    //se obtiene la categoria a partir del nombre seleccionado en el spinner
                    categoria = CCategoria.getCategoriaByName(catego);

                    if(validarNombreRegistro(til_nombre.getEditText().getText().toString()))
                    {
                        //se guarda el nombre de registro
                        nombreRegistro = til_nombre.getEditText().getText().toString();
                        //se obtiene el usuario
                        usuario = til_usuario.getEditText().getText().toString();
                        if(usuario == null) //en el caso de que el usuario no introduzca un usuario
                        {
                            usuario= "";
                        }
                        //se encripta este texto
                        try {
                            usuario = AES.encriptar(usuario,FragmentNuevo.key);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //se comprueba que el campo contraseña no esta vacio
                        if(validarPass(til_pass.getEditText().getText().toString()))
                        {
                            try {
                                //se guarda la contraseña de forma encriptada
                                contraseña = AES.encriptar(til_pass.getEditText().getText().toString(),FragmentNuevo.key);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //se comprueba si el usuario ha escogido un logo de la lista
                            if(logo != null && !imagenBorrada)
                            {
                                idLogo = logo.getId();
                            }
                            else //si el usuario no selecciona un icono se le asignara el icon genérico de la app
                            {
                                idLogo = CLogo.getLogoById(String.valueOf(19)).getId();
                            }
                            //con todos los datos se crea un objeto Cuenta
                            cuenta = new Cuenta(cuenta.getIdCuenta(),nombreRegistro,usuario
                                    ,contraseña,categoria.getIdCategoria(),idLogo);
                            ProgressDialog update = new ProgressDialog(ModificarCuentaActivity.this);
                            update.setMessage("Modificando cuenta");
                            new MyTask(ModificarCuentaActivity.this,update).execute();




                        }
                    }
                } else {
                    Snackbar.make(v, "Selecciona una categoría", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        //evento sobre la imagen del icono,al pulsar sobre la imagen se borra la imagen
        iconoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconoRegistro.setImageResource(android.R.color.transparent);
                imagenBorrada = true;
            }
        });



    }

    @TargetApi(Build.VERSION_CODES.M)
    public void testPermission() {
        if (!Settings.canDrawOverlays(ModificarCuentaActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + ModificarCuentaActivity.this.getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        }
        else
        {
            if(botonPulsado == 1) {
                showDialogInfo();
            }
            else
            {
                showDialogIcon();
            }
        }
    }

    /**
     * Metodo que muestra un dialogo de información
     */
    public void showDialogInfo()
    {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ModificarCuentaActivity.this.getApplicationContext());
        alertDialogBuilder.setTitle(getResources().getString(R.string.infoIcono))
                .setMessage(R.string.msjIcono)
                .setCancelable(false)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }

    /**
     * Método que muestra un diálogo que contiene una lista de iconos a seleccionar
     */
    public void showDialogIcon(){

        final Dialog dialog = new Dialog(ModificarCuentaActivity.this);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        LayoutInflater inflater = LayoutInflater.from(ModificarCuentaActivity.this);
        View view = inflater.inflate(R.layout.layout_lista_iconos, null);

        ArrayList<Logo> logos = CLogo.getAllLogos();

        // Obtener el Recycler
        recycler = (RecyclerView) view.findViewById(R.id.rv_listaIconos);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(ModificarCuentaActivity.this, 3);
        recycler.setLayoutManager(lManager);

        adapter = new IconoAdapter(logos);
        adapter.setOnItemClickListener(new IconoAdapter.IOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, Object object) {
                logo = (Logo)object;
                iconoRegistro.setImageResource(logo.getImagen());
                imagenBorrada = false;
                dialog.dismiss();
            }
        });

        recycler.setAdapter(adapter);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

    }

    private boolean validarNombreRegistro(String nombre)
    {

        //expresion regular comprueba que el campo tiene algo
        Pattern pa = Pattern.compile("^[^ ]+$");
        if(!pa.matcher(nombre).matches())
        {
            til_nombre.setErrorEnabled(true);
            til_nombre.setError("Rellene campo");
            return false;
        }
        else
        {
            til_nombre.setError(null);
            til_nombre.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validarPass(String nombre)
    {

        //expresion regular comprueba que el campo tiene algo
        Pattern pa = Pattern.compile("^[^ ]+$");
        if(!pa.matcher(nombre).matches())
        {
            til_pass.setErrorEnabled(true);
            til_pass.setError("Rellene campo");
            return false;
        }
        else
        {
            til_pass.setError(null);
            til_pass.setErrorEnabled(false);
        }
        return true;
    }


    /**
     * Clase que muestra un dialogo de progreso al ir guardando un registro en la base de datos
     */
    public class MyTask extends AsyncTask<Void,Void,Void> {

        Activity activity;
        ProgressDialog progressDialog;
        boolean registroGuardado;

        public MyTask(Activity act,ProgressDialog pro)
        {
            activity = act;
            progressDialog = pro;
        }

        public void onPreExecute() {
            progressDialog.show();

        }

        public void onPostExecute(Void unused) {

            progressDialog.dismiss();
            if(registroGuardado)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("cuenta",cuenta);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();


            }
            else
            {
                Toast.makeText(ModificarCuentaActivity.this, "No se ha podido realizar la modificacion el registro", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
                //se realiza la inserccion en la tabla Cuentas
                registroGuardado = CCuenta.updateCuenta(String.valueOf(cuenta.getIdCuenta()), cuenta.getRegistro(), cuenta.getUsuario(),
                        cuenta.getPass(), cuenta.getCategoria(), cuenta.getIcono());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
