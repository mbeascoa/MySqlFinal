package com.example.mysqlfinal;

//Aplicación Android que permite conectar con un servidor MySQL y mostrar
//los registros almacenados en un ListView, simulando una agenda de citas.
//academiaandroid.com
//
//by José Antonio Gázquez Rodríguez

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TareaAsincrona extends AsyncTask<String, Void, Boolean>{

    private Connection conexionMySQL;

    @Override
    protected Boolean doInBackground(String... datos)
    {
        String SERVIDOR = datos[0];
        String PUERTO = datos[1];
        String BD = datos[2];
        String USUARIO = datos[3];
        String PASSWORD = datos[4];
        boolean estadoConexion = false;
        try{
            /*Establecemos la conexión con el Servidor MySQL indicándole la cadena de conexión formada por la dirección ip,
            puerto del servidor, la Base de Datos a la que vamos a conectarnos, y el usuario y contraseña de acceso al servidor.*/
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BD,
                    USUARIO,PASSWORD);

            if(!conexionMySQL.isClosed())
            {
                estadoConexion = true;
            }
        }catch(SQLException ex)
        {
            Log.d("Tarea asincrona",ex.getMessage());
        }
        finally
        {
            try
            {
                if(conexionMySQL != null)
                conexionMySQL.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return estadoConexion;
    }
}
