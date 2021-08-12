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
import java.sql.Statement;

public class TareaAsincronaInsertar extends AsyncTask<String[],Void,Void> {

    private Connection conexionMySQL = null;
    private Statement st = null;

    @Override
    protected Void doInBackground(String[]... datos)
    {
        /*Se declaran e inicicializan varios strings con los datos de conexión y nueva cita recibidos desde
        el hilo principal de la aplicación. Se accederán a ellos a través de su posición en el array.*/
        String NOMBRE = datos[0][0];
        String APELLIDOS = datos[0][1];
        String FECHA = datos[0][2];
        String DESCRIPCION = datos[0][3];
        String SERVIDOR = datos[0][4];
        String PUERTO = datos[0][5];
        String BD = datos[0][6];
        String USUARIO = datos[0][7];
        String PASSWORD = datos[0][8];

        try{
            /*Establecemos la conexión con el Servidor MySQL indicándole la cadena de conexión formada por la dirección ip,
            puerto del servidor, la base de datos a la que vamos a conectarnos, y el usuario y contraseña de acceso al servidor.*/
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BD,
                    USUARIO,PASSWORD);
            st = conexionMySQL.createStatement();
            /*Se ejecutará la consulta que insertará una nueva cita con los datos introducidos por el usuario.*/
            st.executeUpdate("INSERT INTO `agenda` (nombre, apellidos, fecha, descripcion) VALUES " +
                    "('" + NOMBRE + "','" + APELLIDOS + "','" + FECHA + "','" + DESCRIPCION + "')");
        }catch(SQLException ex)
        {
            Log.d("Nueva cita", ex.getMessage());
        }
        finally
        {
            try
            {
                st.close();
                conexionMySQL.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
