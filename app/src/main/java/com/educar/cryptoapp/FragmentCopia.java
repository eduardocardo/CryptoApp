package com.educar.cryptoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FragmentCopia extends Fragment {

    final static private String APP_KEY = "xugsp28c5gnn9pg";
    final static private String APP_SECRET = "qo1sm2ikit1k4cu";

    private TextView tituUltimaCopia,fechaCopia,alojaCopia,recuperaDatos,textoRecuperarCopia;
    private Button hacerCopia,restaurarCopia;

    private DropboxAPI<AndroidAuthSession> mDBApi;
    private AppKeyPair appKeys;
    private AndroidAuthSession session;
    public static FragmentCopia newInstance() {
        FragmentCopia fragment = new FragmentCopia();
        return fragment;
    }

    public FragmentCopia(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_copia, container, false);

        tituUltimaCopia =(TextView)view.findViewById(R.id.tv_tituloUltimaCopia);
        fechaCopia =(TextView)view.findViewById(R.id.tv_fechaUltimaCopia);
        alojaCopia =(TextView)view.findViewById(R.id.tv_alojamientoCopia);
        recuperaDatos =(TextView)view.findViewById(R.id.tv_tituloRecuperarDatos);
        textoRecuperarCopia =(TextView)view.findViewById(R.id.tv_textoRecuperarCopia);
        hacerCopia =(Button)view.findViewById(R.id.bt_hacerCopia);
        restaurarCopia =(Button)view.findViewById(R.id.bt_restaurarCopia);

        tituUltimaCopia.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorPrimary));
        fechaCopia.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorPrimaryText));
        alojaCopia.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorPrimaryText));
        recuperaDatos.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorPrimary));
        textoRecuperarCopia.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorPrimaryText));
        hacerCopia.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorText_Icon));
        restaurarCopia.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(),R.color.ColorText_Icon));

        //dropboxx
        appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<>(session);


       // mDBApi.getSession().startOAuth2Authentication(getActivity().getApplicationContext());
        hacerCopia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              /*  String path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
                File file = new File(path + "foto.jpg");
                FileInputStream inputStream = null;
                DropboxAPI.Entry response = null;
                try {
                    inputStream = new FileInputStream(file);
                    response = mDBApi.putFile("/screen.png", inputStream,
                            file.length(), null, null);
                } catch (DropboxException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);*/
                new Upload().execute();

            }
        });

        restaurarCopia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* File file = new File("/SHA.txt");
                FileOutputStream outputStream = null;
                DropboxAPI.DropboxFileInfo info = null;
                try {
                    outputStream = new FileOutputStream(file);
                    info = mDBApi.getFile("/magnum-opus.txt", null, outputStream, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DropboxException e) {
                    e.printStackTrace();
                }

                Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);*/
                new Download().execute();
            }
        });
        return view;
    }

    /*public void onResume() {
        super.onResume();

        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();

                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }*/


    public class Upload extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){

        }

        protected String doInBackground(String... arg0) {

            DropboxAPI.Entry response = null;

            try {
                String path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
                // Define path of file to be upload
                File file = new File(path + "foto.jpg");
                FileInputStream inputStream = new FileInputStream(file);

                //put the file to dropbox
                response = mDBApi.putFile("/foto4.png", inputStream,
                        file.length(), null, null);
                Log.e("DbExampleLog", "The uploaded file's rev is: " + response.rev);

            } catch (Exception e){

                e.printStackTrace();
            }

            return response.rev;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.isEmpty() == false){
                Toast.makeText(getActivity().getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                Log.e("DbExampleLog", "The uploaded file's rev is: " + result);
            }
        }
    }


    public class Download extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){

        }

        protected String doInBackground(String... arg0) {

            DropboxAPI.DropboxFileInfo info = null;

            try {
               // String path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";
                // Define path of file to be download
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "foto2.png");
                FileOutputStream  outputStream = new FileOutputStream(file);

                //get the file to dropbox
                info = mDBApi.getFile("/foto2.png", null,
                        outputStream, null);
                Log.e("DbExampleLog", "The downloaded file's rev is: " + info.getMetadata().rev);

            } catch (Exception e){

                e.printStackTrace();
            }

            return info.getMetadata().rev;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.isEmpty() == false){
                 Toast.makeText(getActivity().getApplicationContext(), "File Downloaded ", Toast.LENGTH_LONG).show();
                Log.e("DbExampleLog", "The uploaded file's rev is: " + result);
            }
        }
    }

    public void onResume() {
        super.onResume();

        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();

                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }



}
