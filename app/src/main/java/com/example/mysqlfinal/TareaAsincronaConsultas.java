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
import java.util.ArrayList;


public class TareaAsincronaConsultas extends AsyncTask<ArrayList<Agenda>,Void,ArrayList<Agenda>>{

    Connection conexionMySQL = null;
    private Statement st = null;
    private ResultSet rs = null;
    private ArrayList<Agenda> columnas = null;

    @Override
    protected ArrayList<Agenda> doInBackground(ArrayList<Agenda>... datos)
    {
        columnas = new ArrayList<Agenda>();
        String sql = "Select nombre, apellidos, fecha from Agenda order by fecha asc";

        String SERVIDOR = datos[0].get(0).getIp();
        String PUERTO = datos[0].get(0).getPuerto();
        String BD = datos[0].get(0).getBaseDatos();
        String USUARIO = datos[0].get(0).getUsuario();
        String PASSWORD = datos[0].get(0).getPass().toString();

        try{
            /*Establecemos la conexión con el Servidor MySQL indicándole la cadena de conexión formada por la dirección ip,
            puerto del servidor, la Base de Datos a la que vamos a conectarnos, y el usuario y contraseña de acceso al servidor.*/
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BD,
                    USUARIO,PASSWORD);

            st = conexionMySQL.createStatement();
            rs = st.executeQuery(sql);
            /*Comprobamos que el cursor esté situado en la primera fila.*/
            if(rs.first())
            {
                do
                {
                    Agenda agenda = new Agenda(rs.getString("nombre"),rs.getString("apellidos"),rs.getString("fecha"));
                    columnas.add(agenda);
                    Log.d("Consulta", agenda.getNombre().toString() + agenda.getApellidos().toString() + agenda.getFecha().toString());
                }while(rs.next());
            }

        }catch(SQLException ex)
        {
            Log.d("Consulta citas", ex.getMessage());
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