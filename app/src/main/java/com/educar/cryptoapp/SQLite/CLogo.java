package com.educar.cryptoapp.SQLite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.educar.cryptoapp.Logo;
import com.educar.cryptoapp.R;

import java.util.ArrayList;

/**
 * Created by Eduardo on 09/05/2016.
 */
public class CLogo {

    //nombre de la tabla
    public static final String TABLE_NAME = "Logos";
    //indica el tipo de dato
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";

    //nombres de los campos de la tabla
    public static final String LOGO_ID = BaseColumns._ID;
    public static final String LOGO_NOMBRE = "nombre";
    public static final String LOGO_IMAGE = "imagen";
    public static final String LOGO_GRANDE = "imagen_grande";
    public static final String LOGO_COLOR = "color";


    //script de la creación de la tabla
    public static final String CREATE_SCRIPT_LOGO = "create table " + TABLE_NAME + "("
            + LOGO_ID  + " " + INT_TYPE + " primary key autoincrement,"
            + LOGO_NOMBRE + " " + STRING_TYPE + " not null,"
            + LOGO_IMAGE + " " + INT_TYPE + " not null,"
            + LOGO_GRANDE + " " + INT_TYPE + " not null,"
            + LOGO_COLOR + " " + INT_TYPE + " not null)";

    //script de inserccion de datos en la tabla

    public static final String INSERT_LOGO_SCRIPT1 = "insert into " + TABLE_NAME +
            " values(null," + "\"Amazon\"," + R.drawable.amazon +"," + R.drawable.amazon_grande + ","
            + R.color.colorAmazon +")";
    public static final String INSERT_LOGO_SCRIPT2 = "insert into " + TABLE_NAME +
            " values(null," + "\"Facebook\"," + R.drawable.fb +"," + R.drawable.facebook_grande +","
            + R.color.colorFacebook +")";
    public static final String INSERT_LOGO_SCRIPT3 = "insert into " + TABLE_NAME +
            " values(null," + "\"Dropbox\"," + R.drawable.dropbox + "," + R.drawable.dropbox_grande +","
            + R.color.colorDropbox + ")";
    public static final String INSERT_LOGO_SCRIPT4 = "insert into " + TABLE_NAME +
            " values(null," + "\"Ebay\"," + R.drawable.ebay + "," + R.drawable.ebay_grande +","
            + R.color.blanco_perfecto +")";
    public static final String INSERT_LOGO_SCRIPT5 = "insert into " + TABLE_NAME +
            " values(null," + "\"Gmail\"," + R.drawable.gmail + "," + R.drawable.gmail_grande +","
            + R.color.blanco_perfecto +")";
    public static final String INSERT_LOGO_SCRIPT6 = "insert into " + TABLE_NAME +
            " values(null," + "\"Instagram\"," + R.drawable.instagram + "," + R.drawable.instagram_grande +","
            + R.color.colorInstagram +")";
    public static final String INSERT_LOGO_SCRIPT7 = "insert into " + TABLE_NAME +
            " values(null," + "\"Linkedin\"," + R.drawable.linkedin + "," + R.drawable.linkedin_grande +","
            + R.color.colorLinkedin +")";
    public static final String INSERT_LOGO_SCRIPT8 = "insert into " + TABLE_NAME +
            " values(null," + "\"Origin\"," + R.drawable.origin + "," + R.drawable.origin_grande +","
            + R.color.blanco_perfecto +")";
    public static final String INSERT_LOGO_SCRIPT9 = "insert into " + TABLE_NAME +
            " values(null," + "\"Outlook\"," + R.drawable.outlook + "," + R.drawable.outlook_grande +","
            + R.color.colorOutlook +")";
    public static final String INSERT_LOGO_SCRIPT10 = "insert into " + TABLE_NAME +
            " values(null," + "\"Paypal\"," + R.drawable.paypal + "," + R.drawable.paypal_grande +","
            + R.color.colorPaypal +")";
    public static final String INSERT_LOGO_SCRIPT11 = "insert into " + TABLE_NAME +
            " values(null," + "\"Reddit\"," + R.drawable.reddit + "," + R.drawable.reddit_grande +","
            + R.color.blanco_perfecto +")";
    public static final String INSERT_LOGO_SCRIPT12 = "insert into " + TABLE_NAME +
            " values(null," + "\"Skype\"," + R.drawable.skype + "," + R.drawable.skype_grande +","
            + R.color.colorSkype +")";
    public static final String INSERT_LOGO_SCRIPT13 = "insert into " + TABLE_NAME +
            " values(null," + "\"Steam\"," + R.drawable.steam + "," + R.drawable.steam_grande +","
            + R.color.colorSteam +")";
    public static final String INSERT_LOGO_SCRIPT14 = "insert into " + TABLE_NAME +
            " values(null," + "\"Twitter\"," + R.drawable.twitter + "," + R.drawable.twitter_grande +","
            + R.color.colorTwitter +")";
    public static final String INSERT_LOGO_SCRIPT15 = "insert into " + TABLE_NAME +
            " values(null," + "\"Vimeo\"," + R.drawable.vimeo + "," + R.drawable.vimeo_grande +","
            + R.color.colorVimeo +")";
    public static final String INSERT_LOGO_SCRIPT16 = "insert into " + TABLE_NAME +
            " values(null," + "\"WOW\"," + R.drawable.wow + "," + R.drawable.wow_grande +","
            + R.color.colorWow +")";
    public static final String INSERT_LOGO_SCRIPT17 = "insert into " + TABLE_NAME +
            " values(null," + "\"Yahoo\"," + R.drawable.yahoo + "," + R.drawable.yahoo_grande +","
            + R.color.colorYahoo +")";
    public static final String INSERT_LOGO_SCRIPT18 = "insert into " + TABLE_NAME +
            " values(null," + "\"Youtube\"," + R.drawable.youtube + "," + R.drawable.youtube_grande +","
            + R.color.blanco_perfecto +")";
    public static final String INSERT_LOGO_SCRIPT19 = "insert into " + TABLE_NAME +
            " values(null," + "\"Genérico\"," + R.drawable.circlelock + "," + R.drawable.circlelock +","
            + R.color.ColorPrimary +")";


    /**
     * Metodo que obtiene todos los logos disponibles
     * @return una colección con todos los logos
     */
    public static ArrayList<Logo> getAllLogos()
    {
        ArrayList<Logo> logos = new ArrayList<>();
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        //se recoge en un cursor todos los elementos de esa base de datos
        Cursor c = database.rawQuery("Select * from " + TABLE_NAME,null);

        if(c.moveToFirst())
        {
            int id;
            String nombre;
            int imagen;
            int imagenGrande;
            int color;
            Logo logo;
            do{
                id = Integer.parseInt(c.getString(c.getColumnIndex(LOGO_ID)));
                nombre = c.getString(c.getColumnIndex(LOGO_NOMBRE));
                imagen = Integer.parseInt(c.getString(c.getColumnIndex(LOGO_IMAGE)));
                imagenGrande = Integer.parseInt(c.getString(c.getColumnIndex(LOGO_GRANDE)));
                color = Integer.parseInt(c.getString(c.getColumnIndex(LOGO_COLOR)));
                logo = new Logo(id,nombre,imagen,imagenGrande,color);
                logos.add(logo);

            }while (c.moveToNext());

        }
        DatabaseManager.getInstance().closeDatabase();

        return logos;

    }


    /**
     * Metodo que obtiene un logo determinado por la id pasada por parámetro
     * @param i es la id del logo a buscar
     * @return un logo
     */
    public static Logo getLogoById(String i)
    {
        Logo logo = null;
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        //se obtiene el cursor con el logo que tiene la id pasada por parametro
        String query = "select * from " + TABLE_NAME + " where " + LOGO_ID + " = ?";
        Cursor c =  database.rawQuery(query,new String[]{i});

        //se recorre el cursor para obtener los datos
        if(c != null)
        {
            //se recorre el cursor obteniendo los datos que contiene
            while(c.moveToNext())
            {
                int  id = Integer.parseInt(c.getString(c.getColumnIndex(LOGO_ID)));
                String  nombre = c.getString(c.getColumnIndex(LOGO_NOMBRE));
                int imagen = Integer.parseInt(c.getString(c.getColumnIndex(LOGO_IMAGE)));
                int imagenGrande = Integer.parseInt(c.getString(c.getColumnIndex(LOGO_GRANDE)));
                int color = Integer.parseInt(c.getString(c.getColumnIndex(LOGO_COLOR)));
                logo = new Logo(id,nombre,imagen,imagenGrande,color);
            }
        }

        DatabaseManager.getInstance().closeDatabase();

        return logo;

    }


    /**
     * Metodo que hace una insercion en la base de datos Logos
     * @param n es el nombre del logo
     * @param i es un entero que hace referencia a la imagen del logo
     * @param iG es la referencia a la imagen grande del logo
     * @param c es el color asignado a un logo
     * @return true si se realiza la inserción,false si no se realiza
     */
    public static boolean insertNewLogo(String n,int i,int iG,int c)
    {
        boolean logoInsertado = false;
        //se crea un contenedor donde se almacenan todos los datos
        ContentValues values = new ContentValues();
        values.put(LOGO_NOMBRE,n);
        values.put(LOGO_IMAGE,i);
        values.put(LOGO_GRANDE,iG);
        values.put(LOGO_COLOR,c);
        //se obtiene la base de datos
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        long resultado = database.insert(TABLE_NAME,null,values);
        if(resultado != -1)
        {
            logoInsertado = true;
        }
        DatabaseManager.getInstance().closeDatabase();

        return logoInsertado;
    }


    /**
     * Metodo que hace una modificacion en la tabla Logos
     * @param id es la id del logo
     * @param n es el nombre del logo
     * @param i es la imagen
     * @param iG es la imagen grande
     * @param c es el color
     * @return true si se ha podido realizar la inserciön,false si no se ha podido
     */
    public static boolean updateLogo(String id,String n,int i,int iG,int c)
    {
        boolean registroModificado = false;
        //se crea un contenedor de valores
        ContentValues values = new ContentValues();
        //se añade los valores a modificar
        values.put(LOGO_NOMBRE,n);
        values.put(LOGO_IMAGE,i);
        values.put(LOGO_GRANDE,iG);
        values.put(LOGO_COLOR,c);

        //es la condicion que indica en que registro se realiza la modificación
        String selection = LOGO_ID + "= ?";
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
    public boolean deleteRegister(String id)
    {
        boolean registroBorrado = false;
        //es la condicion que indica en que registro se realiza la eliminacion
        String selection = LOGO_ID + "= ?";
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
}
