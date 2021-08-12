package com.example.mysqlfinal;

//Aplicación Android que permite conectar con un servidor MySQL y mostrar
//los registros almacenados en un ListView, simulando una agenda de citas.
//academiaandroid.com
//
//by José Antonio Gázquez Rodríguez

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

/*Clase MainActivity, que implementa la lógica del menú para la conexión a
la base de datos externa.*/
public class MainActivity extends Activity {
	
	/*Declaramos los componentes necesarios para introducir los datos de conexión al servidor.*/
	private EditText edServidor, edPuerto, edUsuario, edPassword;	
	private String baseDatos = "citas";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*Enlazamos los componentes con los recursos definidos en el layout.*/
        edServidor = (EditText)findViewById(R.id.edServidor);
        edPuerto  = (EditText)findViewById(R.id.edPuerto);
        edUsuario = (EditText)findViewById(R.id.edUsuario);
        edPassword = (EditText)findViewById(R.id.edPassword);

	}

	/*Función que establecerá la conexión con el Servidor si los datos introducidos son correctos.
    Devuelve un valor de verdadero o falso que indicará si se ha podido establecer la conexión.*/
    public boolean conectarMySQL()
    {
    		/*Variable boolean que almacenará si el estado de la conexión es true o false.*/
    		boolean estadoConexion = false;
    		/*Inicializamos la Clase Connection encargada de conectar con la base de datos.*/ 
    		Connection conexionMySQL = null;

    		/*Se declaran varias variables de tipo String
    		que almacenarán los valores introducidos por pantalla.*/
    		String dbUserName = edUsuario.getText().toString();
    		String dbPassword = edPassword.getText().toString();
    		String puerto = edPuerto.getText().toString();
    		String ip = edServidor.getText().toString();

    		/*Asignamos el driver a una variable de tipo String.*/
    		String driver = "com.mysql.jdbc.Driver";



		try{
                /*Cargamos el driver del conector JDBC.*/
                Class.forName(driver).newInstance();

				String[] datos = new String[]{ip,puerto,baseDatos,dbUserName,dbPassword};
				estadoConexion = new com.example.mysqlfinal.TareaAsincrona().execute(datos).get();
				if(estadoConexion)
					Toast.makeText(MainActivity.this,"Datos de conexión correctos.", Toast.LENGTH_LONG).show();

    		}catch(Exception ex)
    		{
    			Toast.makeText(MainActivity.this,"Error al comprobar las credenciales: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    		}
    	return estadoConexion;  	
    }
    
    /*Evento On Click que realiza la llamada a la función conexionMySQL() obteniendo un valor de verdadero
    o falso para la petición de conexión.*/
    public void conexionMySQL(View view)
    {
    	Intent intent = new Intent(this,Citas.class);
    	/*Si el valor devuelto por la función es true, pasaremos los datos de la conexión a la siguiente Activity.*/
    	if(conectarMySQL())
    		{	
	    		Toast.makeText(this, "Los datos de conexión introducidos son correctos."
	    		, Toast.LENGTH_LONG).show();
	    		intent.putExtra("servidor", edServidor.getText().toString());
	    		intent.putExtra("puerto", edPuerto.getText().toString());
	    		intent.putExtra("usuario", edUsuario.getText().toString());
	    		intent.putExtra("password", edPassword.getText().toString());
	    		intent.putExtra("datos", baseDatos);
	    		startActivity(intent);
    		}
    }    
}
