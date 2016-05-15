package com.educar.cryptoapp.SQLite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.educar.cryptoapp.Categoria;
import com.educar.cryptoapp.R;

import java.util.ArrayList;

/**
 * Created by Eduardo on 08/05/2016.
 */
public class CCategoria {

    //nombre de la tabla
    public static final String TABLE_NAME = "Categorias";
    //indica el tipo de dato
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";

    //nombres de los campos de la tabla
    public static final String CATEGORIA_ID = BaseColumns._ID;
    public static final String CATEGORIA_NOMBRE = "nombre";
    public static final String CATEGORIA_IMAGE = "imagen";


    //script de la creación de la tabla
    public static final String CREATE_SCRIPT_CATEGORIA = "create table " + TABLE_NAME + "("
            + CATEGORIA_ID  + " " + INT_TYPE + " primary key autoincrement,"
            + CATEGORIA_NOMBRE + " " + STRING_TYPE + " not null,"
            + CATEGORIA_IMAGE + " " + STRING_TYPE + " not null)";

    //script de inserccion de datos en la tabla

    public static final String INSERT_SCRIPT1 = "insert into " + TABLE_NAME +
            " values(null," + "\"Correos\"," + R.drawable.correo + ")";
    public static final String INSERT_SCRIPT2 = "insert into " + TABLE_NAME +
            " values(null," + "\"Redes social\"," + R.drawable.share + ")";
    public static final String INSERT_SCRIPT3 = "insert into " + TABLE_NAME +
            " values(null," + "\"Sitios web\"," + R.drawable.internet + ")";
    public static final String INSERT_SCRIPT4 = "insert into " + TABLE_NAME +
            " values(null," + "\"Juegos\"," +  R.drawable.juegos + ")";
    public static final String INSERT_SCRIPT5 = "insert into " + TABLE_NAME +
            " values(null," + "\"Compras\"," + R.drawable.compras + ")";
    public static final String INSERT_SCRIPT6 = "insert into " + TABLE_NAME +
            " values(null," + "\"Utilidades\"," + R.drawable.utilidades + ")";
    public static final String INSERT_SCRIPT7 = "insert into " + TABLE_NAME +
            " values(null," + "\"Finanzas\"," + R.drawable.finanzas + ")";
    public static final String INSERT_SCRIPT8 = "insert into " + TABLE_NAME +
            " values(null," + "\"Otro\"," + R.drawable.otros + ")";


    /**
     * Metodo que obtiene una coleccion con todas las categorías
     * @return una arrayList con todas las categorías
     */
    public static ArrayList<Categoria> getAllCategorias()
    {
       ArrayList<Categoria> categorias = new ArrayList<>();
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        //se recoge en un cursor todos los elementos de esa base de datos
        Cursor c = database.rawQuery("Select * from " + TABLE_NAME,null);

        if(c.moveToFirst())
        {
            int id;
            String nombre;
            int imagen;
            Categoria categoria;
            do{
                id = Integer.parseInt(c.getString(c.getColumnIndex(CATEGORIA_ID)));
                nombre = c.getString(c.getColumnIndex(CATEGORIA_NOMBRE));
                imagen = Integer.parseInt(c.getString(c.getColumnIndex(CATEGORIA_IMAGE)));
                categoria = new Categoria(id,nombre,imagen);
                categorias.add(categoria);

            }while (c.moveToNext());

        }
        DatabaseManager.getInstance().closeDatabase();

        return categorias;

    }

    /**
     * Método que obtiene los nombres de las categorías
     * @return una colección con los nombres de las categorías
     */
    public static ArrayList<String> getAllNombresCategorias()
    {
        ArrayList<String> nombres = new ArrayList<>();
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        //se recoge en un cursor todos los elementos de esa base de datos
        Cursor c = database.rawQuery("Select * from " + TABLE_NAME,null);

        if(c.moveToFirst())
        {
            String nombre;
            do{
                nombre = c.getString(c.getColumnIndex(CATEGORIA_NOMBRE));
                nombres.add(nombre);
            }while (c.moveToNext());

        }
        DatabaseManager.getInstance().closeDatabase();

        return nombres;
    }


    /**
     * Metodo que hace una insercion en la base de datos Categoria
     * @param n es el nombre de la categoria
     * @param i es  la imagen de la categoria
     * @return true si se realiza la inserción,false si no se realiza
     */
    public static boolean insertNewCategoria(String n,int i)
    {
        boolean categoriaInsertada = false;
        //se crea un contenedor donde se almacenan todos los datos
        ContentValues values = new ContentValues();
        values.put(CATEGORIA_NOMBRE,n);
        values.put(CATEGORIA_IMAGE,i);
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        long resultado = database.insert(TABLE_NAME, null, values);
        if(resultado != -1)
        {
            categoriaInsertada = true;
        }
        DatabaseManager.getInstance().closeDatabase();

        return categoriaInsertada;
    }


    /**
     * Metodo que hace una modificacion en la base de datos Categorias
     * @param id es la id de la categoria
     * @param n es el nombre de la categoria
     * @param i es la imagen
     * @return true si se ha podido realizar la inserción,false si no se ha podido
     */
    public static boolean updateLogo(String id,String n,int i)
    {
        boolean registroModificado = false;
        //se crea un contenedor de valores
        ContentValues values = new ContentValues();
        //se añade los valores a modificar
        values.put(CATEGORIA_NOMBRE,n);
        values.put(CATEGORIA_IMAGE,i);


        //es la condicion que indica en que registro se realiza la modificación
        String selection = CATEGORIA_ID + "= ?";
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
     * Metodo que elimina un registro de la base de datos segun la id pasada por parámetro
     * @param id es la id del registro a eliminar
     * @return true si se ha podido eliminar el registro,false si no se ha podido
     */
    public static boolean deleteRegister(String id)
    {
        boolean registroBorrado = false;
        //es la condicion que indica en que registro se realiza la eliminacion
        String selection = CATEGORIA_ID + "= ?";
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
     * Metodo que obtiene un objeto Categoria en funcion del nombre pasado por parámetro
     * @param nom  es el nombre de la categoria a buscar
     * @return un objeto de tipo Categoria
     */
    public static Categoria getCategoriaByName(String nom)
    {
        Categoria categoria = null;
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        String query = "select * from " + TABLE_NAME + " where " + CATEGORIA_NOMBRE + " = ?";
        Cursor c = database.rawQuery(query, new String[]{nom});

        if(c.moveToFirst())
        {
            String nombre;
            int id;
            int icono;
            do{
                id = Integer.parseInt(c.getString(c.getColumnIndex(CATEGORIA_ID)));
                nombre = c.getString(c.getColumnIndex(CATEGORIA_NOMBRE));
                icono = Integer.parseInt(c.getString(c.getColumnIndex(CATEGORIA_IMAGE)));
                categoria = new Categoria(id,nombre,icono);
            }while (c.moveToNext());

        }
        DatabaseManager.getInstance().closeDatabase();

        return categoria;
    }

    /**
     * Metodo que obtiene una categoría en función de la id pasada por parametro
     * @param i es la id de la categoría a buscar
     * @return un objeto de tipo Categoría
     */
    public static Categoria getCategoriaById(String i)
    {
        Categoria categoria = null;
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        String query = "select * from " + TABLE_NAME + " where " + CATEGORIA_ID + " = ?";
        Cursor c = database.rawQuery(query,new String[]{i});

        if(c.moveToFirst())
        {
            String nombre;
            int id;
            int icono;
            do{
                id = Integer.parseInt(c.getString(c.getColumnIndex(CATEGORIA_ID)));
                nombre = c.getString(c.getColumnIndex(CATEGORIA_NOMBRE));
                icono = Integer.parseInt(c.getString(c.getColumnIndex(CATEGORIA_IMAGE)));
                categoria = new Categoria(id,nombre,icono);
            }while (c.moveToNext());

        }
        DatabaseManager.getInstance().closeDatabase();

        return categoria;
    }



}
