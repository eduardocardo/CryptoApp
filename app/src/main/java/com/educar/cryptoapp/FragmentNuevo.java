package com.educar.cryptoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.educar.cryptoapp.SQLite.CCategoria;
import com.educar.cryptoapp.SQLite.CCuenta;
import com.educar.cryptoapp.SQLite.CLogo;
import com.educar.cryptoapp.SQLite.DatabaseManager;
import com.educar.cryptoapp.actividades.IconoAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;

public class FragmentNuevo extends Fragment {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;

    private MaterialBetterSpinner categorias;
    private TextInputLayout til_nombre,til_usuario,til_pass;
    private Button addRegister,addIcon;
    private String catego;
    private View view;
    private ImageView iconoRegistro,info;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private IconoAdapter adapter;
    private int botonPulsado;
    private TextView textoNuevoRegistro;
    private Logo logo;
    private Categoria categoria;
    private String nombreRegistro,usuario,contraseña;
    private int idLogo;
    private boolean imagenBorrada; //indica cuando el usuario ha pulsaso sobre el icono para borrarlo
    public static SecretKey key;




    public static FragmentNuevo newInstance() {
        FragmentNuevo fragment = new FragmentNuevo();
        return fragment;
    }

    public FragmentNuevo(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_nuevo, container, false);
        til_nombre =(TextInputLayout)view.findViewById(R.id.til_nombreRegistro);
        til_usuario =(TextInputLayout)view.findViewById(R.id.til_registerUsuario);
        til_pass = (TextInputLayout)view.findViewById(R.id.til_registerPass);
        addIcon = (Button)view.findViewById(R.id.bt_seleccionaIcono);
        addIcon.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorText_Icon));
        iconoRegistro =(ImageView)view.findViewById(R.id.iv_imageCuenta);
        info =(ImageView)view.findViewById(R.id.iv_info);
        textoNuevoRegistro =(TextView)view.findViewById(R.id.tv_textoNuevoRegistro);
        //se obtiene los nombres de las categorias de la tabla Categorias y se cargan en el spinner
        final ArrayList<String> nombreCategorias =  CCategoria.getAllNombresCategorias();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, nombreCategorias);

        categorias = (MaterialBetterSpinner)
                view.findViewById(R.id.android_material_design_spinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(arrayAdapter);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                key = AES.generarKey("1234");

                return null;
            }
        }.execute();



        //evento sobre el spinner categorias que guarda el nombre de la categoría seleccionada
        categorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                catego = (String) parent.getItemAtPosition(position);


            }
        });


        addRegister = (Button) view.findViewById(R.id.bt_addRegister);
        addRegister.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorText_Icon));
        //evento que añade un nuevo registro en la base de datos
        addRegister.setOnClickListener(new View.OnClickListener() {
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
                            usuario = AES.encriptar(usuario,key);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //se comprueba que el campo contraseña no esta vacio
                       if(validarPass(til_pass.getEditText().getText().toString()))
                       {
                           try {
                               //se guarda la contraseña de forma encriptada
                               contraseña = AES.encriptar(til_pass.getEditText().getText().toString(),key);
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
                           //con todos los datos se hace la insercion en la base de datos
                           ProgressDialog pagando = new ProgressDialog(getActivity());
                           pagando.setCancelable(false);
                           pagando.setMessage(getResources().getString(R.string.guardandoRegistro));
                           new MyTask(getActivity(),pagando).execute();

                       }
                    }
                } else {
                    Snackbar.make(v, "Selecciona una categoría", Snackbar.LENGTH_SHORT).show();
                }


            }
        });

        //evento que lanza un dialogo que permite al usuario escoger un icono
        addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    botonPulsado = 0;
                    testPermission();
                }
                else
                {
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

        iconoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconoRegistro.setImageResource(android.R.color.transparent);
                imagenBorrada = true;
            }
        });


        return view;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void testPermission() {
        if (!Settings.canDrawOverlays(getActivity())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getActivity().getPackageName()));
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

    public void showDialogInfo()
    {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getActivity().getApplicationContext());
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

    public void showDialogIcon(){

        final Dialog dialog = new Dialog(getActivity().getApplicationContext());
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.layout_lista_iconos, null);

        ArrayList<Logo> logos = CLogo.getAllLogos();

        // Obtener el Recycler
        recycler = (RecyclerView) view.findViewById(R.id.rv_listaIconos);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
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


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(getActivity())) {
                if(botonPulsado == 1) {
                    showDialogInfo();
                }
                else
                {
                    showDialogIcon();
                }
            }
        }
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
                Toast.makeText(getActivity(), "Registro guardado", Toast.LENGTH_SHORT).show();
                //se resetean los valores del formulario
                iconoRegistro.setImageResource(android.R.color.transparent);
                categorias.setText("");
                til_nombre.getEditText().setText("");
                til_usuario.getEditText().setText("");
                til_pass.getEditText().setText("");
                til_pass.clearFocus(); //se elimina el focus del ultimo edittext

            }
            else
            {
                Toast.makeText(getActivity(), "No se ha podido guardar el registro", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
                //se realiza la inserccion en la tabla Cuentas
             registroGuardado = CCuenta.insertNewCuenta(nombreRegistro,usuario,contraseña,categoria.getIdCategoria(),idLogo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
