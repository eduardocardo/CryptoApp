package com.educar.cryptoapp.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.educar.cryptoapp.R;

/**
 * Created by Eduardo on 08/05/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    //indica el nombre de la base de datos
    private static final String DATABASE_NAME ="Cuentas.db";
    //indica la version
    private static final int DATABASE_VERSION = 20;

    /**
     * Constructor de la clase DBHelper
     * @param context es el contexto donde se creará la bd
     */
    public DbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        //se realiza la creacion de la tabla Categorias
        db.execSQL(CCategoria.CREATE_SCRIPT_CATEGORIA);
        db.execSQL(CCategoria.INSERT_SCRIPT1);
        db.execSQL(CCategoria.INSERT_SCRIPT2);
        db.execSQL(CCategoria.INSERT_SCRIPT3);
        db.execSQL(CCategoria.INSERT_SCRIPT4);
        db.execSQL(CCategoria.INSERT_SCRIPT5);
        db.execSQL(CCategoria.INSERT_SCRIPT6);
        db.execSQL(CCategoria.INSERT_SCRIPT7);
        db.execSQL(CCategoria.INSERT_SCRIPT8);

        //se realiza la creacion de la tabla Logos
        db.execSQL(CLogo.CREATE_SCRIPT_LOGO);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT1);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT2);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT3);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT4);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT5);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT6);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT7);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT8);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT9);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT10);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT11);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT12);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT13);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT14);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT15);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT16);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT17);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT18);
        db.execSQL(CLogo.INSERT_LOGO_SCRIPT19);


        //se realiza la creación de la tabla Cuentas
        db.execSQL(CCuenta.CREATE_SCRIPT_CUENTA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CCategoria.TABLE_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + CLogo.TABLE_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + CCuenta.TABLE_NAME );
        onCreate(db);
    }


    /**
     * Metodo que hace efectivo las referencias de integridad de las llaves foráneas
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }



}
