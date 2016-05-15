package com.educar.cryptoapp.SQLite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.educar.cryptoapp.Categoria;
import com.educar.cryptoapp.Cuenta;

import java.util.ArrayList;

/**
 * Created by Eduardo on 12/05/2016.
 */
public class CCuenta {


    //nombre de la tabla
    public static final String TABLE_NAME = "Cuentas";
    //indica el tipo de dato
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";

    //nombres de los campos de la tabla
    private static final String CUENTA_ID = BaseColumns._ID;
    private static final String CUENTA_NOMBRE = "nombre";
    private static final String CUENTA_USUARIO = "usuario";
    private static final String CUENTA_PASS = "contraseña";
    private static final String CUENTA_CATEGORIA = "categoria";
    private static final String CUENTA_LOGO ="logo";


    public static final String CREATE_SCRIPT_CUENTA = "create table " + TABLE_NAME + "("
            + CUENTA_ID  + " " + INT_TYPE + " primary key autoincrement,"
            + CUENTA_NOMBRE + " " + STRING_TYPE + " not null,"
            + CUENTA_USUARIO + " " + STRING_TYPE + " null,"
            + CUENTA_PASS + " " + STRING_TYPE + " not null,"
            + CUENTA_CATEGORIA + " " + INT_TYPE + " not null,"
            + CUENTA_LOGO + " " + INT_TYPE + "not null,"
            + "FOREIGN KEY(" + CUENTA_CATEGORIA + ") REFERENCES " + CCategoria.TABLE_NAME
            + "(" + CCategoria.CATEGORIA_ID + ")"
            + "FOREIGN KEY(" + CUENTA_LOGO + ") REFERENCES " + CLogo.TABLE_NAME
            + "(" + CLogo.LOGO_ID + "))";


    /**
     * Metodo que inserta una nueva cuenta en la tabla Cuentas
     * @param n es el nombre del registo
     * @param u es el usuario de la cuenta
     * @param p  es la contraseña
     * @param c es la categoria a la que pertenece la cuenta
     * @param l es el logo de la cuenta
     * @return true si se ha realizado la insercción,false si no se ha realizado
     */
    public static boolean insertNewCuenta(String n,String u,String p,int c,int l)
    {
        boolean cuentaInsertada = false;
        //se crea un contenedor donde se almacenan todos los datos
        ContentValues values = new ContentValues();
        values.put(CUENTA_NOMBRE,n);
        values.put(CUENTA_USUARIO,u);
        values.put(CUENTA_PASS,p);
        values.put(CUENTA_CATEGORIA,c);
        values.put(CUENTA_LOGO,l);
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        long resultado = database.insert(TABLE_NAME,null,values);
        if(resultado != -1)
        {
            cuentaInsertada = true;
        }
        DatabaseManager.getInstance().closeDatabase();

        return cuentaInsertada;
    }


    /**
     * Metodo que hace una modificación en la tabla Cuentas
     * @param id es la id del registro a modificar
     * @param n es el nombre
     * @param u es el usuario
     * @param p es la contraseña
     * @param c es la categoria de la cuenta
     * @param l es el logo
     * @return true si se ha realizado la modificación,false si no se ha realizado
     */
    public static boolean updateCuenta(String id,String n,String u,String p,int c,int l)
    {
        boolean registroModificado = false;
        //se crea un contenedor de valores
        ContentValues values = new ContentValues();
        //se añade los valores a modificar
        values.put(CUENTA_NOMBRE,n);
        values.put(CUENTA_USUARIO,u);
        values.put(CUENTA_PASS,p);
        values.put(CUENTA_CATEGORIA,c);
        values.put(CUENTA_LOGO,l);

        //es la condicion que indica en que registro se realiza la modificación
        String selection = CUENTA_ID + "= ?";
        //es el parametro a comparar con la condicion anterior
        String[] selectionArgs = new String[]{id};
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        int num = database.update(TABLE_NAME, values, selection, selectionArgs);
        if(num != 0)
        {
            registroModificado = true;
        }

        DatabaseManager.getInstance().closeDatabase();
        return registroModificado;
    }


    /**
     * Metodo que elimina un registro de la tabla Cuentas segun la id pasada por parámetro
     * @param id es la id del registro a eliminar
     * @return true si se ha podido eliminar el registro,false si no se ha podido
     */
    public static boolean deleteRegister(String id)
    {
        boolean registroBorrado = false;
        //es la condicion que indica en que registro se realiza la eliminacion
        String selection = CUENTA_ID + "= ?";
        //es el parametro a comparar con la condicion anterior
        String[] selectionArgs = new String[]{id};
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        int num = database.delete(TABLE_NAME, selection, selectionArgs);
        if(num != 0)
        {
            registroBorrado = true;
        }
        DatabaseManager.getInstance().closeDatabase();
        return registroBorrado;
    }


    /**
     * Metodo que obtiene una colección con todas las cuentas que tengan la misma categoría
     * que viene determinada por la id pasada por parämetro
     * @return una arrayList con todas las cuentas con la misma categoría
     */
    public static ArrayList<Cuenta> getAllCuentasByCategoria(String i)
    {
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        //se recoge en un cursor todos los elementos de esa base de datos
        String query = "select * from " + TABLE_NAME + " where " + CUENTA_ID + " =?";
        Cursor c =  database.rawQuery(query, new String[]{i});

        if(c.moveToFirst())
        {
            int id;
            String nombre;
            String usuario;
            String pass;
            int categoria;
            int logo;
            Cuenta cuenta;
            do{
                id = Integer.parseInt(c.getString(c.getColumnIndex(CUENTA_ID)));
                nombre = c.getString(c.getColumnIndex(CUENTA_NOMBRE));
                usuario =  c.getString(c.getColumnIndex(CUENTA_USUARIO));
                pass =  c.getString(c.getColumnIndex(CUENTA_PASS));
                categoria = Integer.parseInt(c.getString(c.getColumnIndex(CUENTA_CATEGORIA)));
                logo = Integer.parseInt(c.getString(c.getColumnIndex(CUENTA_LOGO)));
                cuenta = new Cuenta(id,nombre,usuario,pass,categoria,logo);
                cuentas.add(cuenta);

            }while (c.moveToNext());

        }
        DatabaseManager.getInstance().closeDatabase();

        return cuentas;

    }


    /**
     * Metodo que obtiene una cuenta en función de la id pasada por parámetro
     * @param i es la id de la cuenta a buscar
     * @return una cuenta
     */
    public static Cuenta getCuentaById(String i)
    {
        Cuenta cuenta = null;
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        //se recoge en un cursor todos los elementos de esa base de datos
        String query = "select * from " + TABLE_NAME + " where " + CUENTA_ID + " = ?";
        Cursor c =  database.rawQuery(query,new String[]{i});

        if(c != null)
        {
            int id;
            String nombre;
            String usuario;
            String pass;
            int categoria;
            int logo;

            //se recorre el cursor obteniendo los datos que contiene
            while(c.moveToNext())
            {
                id = Integer.parseInt(c.getString(c.getColumnIndex(CUENTA_ID)));
                nombre = c.getString(c.getColumnIndex(CUENTA_NOMBRE));
                usuario =  c.getString(c.getColumnIndex(CUENTA_USUARIO));
                pass =  c.getString(c.getColumnIndex(CUENTA_PASS));
                categoria = Integer.parseInt(c.getString(c.getColumnIndex(CUENTA_CATEGORIA)));
                logo = Integer.parseInt(c.getString(c.getColumnIndex(CUENTA_LOGO)));
                cuenta = new Cuenta(id,nombre,usuario,pass,categoria,logo);
            }

        }
        DatabaseManager.getInstance().closeDatabase();

        return cuenta;

    }






}
