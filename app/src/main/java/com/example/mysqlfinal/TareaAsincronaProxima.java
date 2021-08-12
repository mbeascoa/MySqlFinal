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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TareaAsincronaProxima extends AsyncTask<String[],Void,String[]> {

    Connection conexionMySQL = null;
    private Statement st = null;
    private ResultSet rs = null;

    @Override
    protected String[] doInBackground(String[]... datos)
    {
        String sql = "Select nombre, apellidos, fecha, descripcion from Agenda where fecha in (select MIN(fecha) from Agenda)";
        String nombre = null;
        String apellidos = null;
        String fecha = null;
        String descripcion = null;
        String[] columnas = null;
        String SERVIDOR = datos[0][0];
        String PUERTO = datos[0][1];
        String BD = datos[0][2];
        String USUARIO = datos[0][3];
        String PASSWORD = datos[0][4];
        try{
            /*Establecemos la conexión con el Servidor MySQL indicándole la cadena de conexión formada por la dirección ip,
            puerto del servidor, la Base de Datos a la que vamos a conectarnos, y el usuario y contraseña de acceso al servidor.*/
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BD,
                    USUARIO,PASSWORD);
            st = conexionMySQL.createStatement();
			/*Se ejecutará la consulta que devolverá el registro con la fecha de la cita más próxima,
			a excepción del campo id.*/
            rs = st.executeQuery(sql);
            while(rs.next())
            {
                nombre = rs.getString("nombre");
                apellidos = rs.getString("apellidos");
                fecha = rs.getString("fecha");
                descripcion = rs.getString("descripcion");
            }
            columnas = new String[]{nombre, apellidos, fecha, descripcion};

        }catch(SQLException ex)
        {
            Log.d("Próxima cita", ex.getMessage());
        }
        finally
        {
            try
            {
                st.close();
                rs.close();
                conexionMySQL.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return columnas;
    }
}
