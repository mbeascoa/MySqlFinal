package com.example.mysqlfinal;

//Aplicación Android que permite conectar con un servidor MySQL y mostrar
//los registros almacenados en un ListView, simulando una agenda de citas.
//academiaandroid.com
//
//by José Antonio Gázquez Rodríguez

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*Clase Citas, que hereda de la clase base ListActivity, que permite mostrar
las citas almacenadas en un componente de tipo selección ListView.*/
public class Citas extends ListActivity {

		/*Declaramos los componentes y clases necesarias 
		para realizar consultas a una base de datos en MySQL.*/
		public TextView txtBaseDatos, txtPuerto, txtServidor, txtUsuario,
		txtNombreAgenda, txtFechaAgenda, txtDescripcionAgenda, txtTotalRegistros;
		private EditText edPassword,edNombre, edApellidos, edFecha, edDescripcion;
		private Bundle bundle;
		private ListView listaAgenda;
        String[] datos = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_citas);

		
		/*Enlazamos las variables con los componentes definidos en el layout.*/
		txtBaseDatos = (TextView)findViewById(R.id.txtBaseDatos);
		txtPuerto = (TextView)findViewById(R.id.txtPuerto);
		txtServidor = (TextView)findViewById(R.id.txtServidor);
		txtUsuario = (TextView)findViewById(R.id.txtUsuario);
		edPassword= (EditText)findViewById(R.id.edPasswordCon);

        edNombre = (EditText)findViewById(R.id.edNombre);
        edApellidos = (EditText)findViewById(R.id.edApellidos);
        edFecha = (EditText)findViewById(R.id.edFecha);
        edDescripcion = (EditText)findViewById(R.id.edDescrpcion);

		listaAgenda = (ListView)findViewById(android.R.id.list);
		
		txtNombreAgenda = (TextView)findViewById(R.id.txtNombreAgenda);
		txtFechaAgenda = (TextView)findViewById(R.id.txtFechaAgenda);
		txtDescripcionAgenda = (TextView)findViewById(R.id.txtDescripcionAgenda);
		txtTotalRegistros = (TextView)findViewById(R.id.txtTotalRegistros);
		
		bundle = getIntent().getExtras();
		/*Obtenemos los valores de conexión introducidos en la Activity principal.*/
		txtPuerto.setText(bundle.getString("puerto"));
		txtServidor.setText(bundle.getString("servidor"));
		txtBaseDatos.setText(bundle.getString("datos"));
		txtUsuario.setText(bundle.getString("usuario"));		
		edPassword.setText(bundle.getString("password"));

        //Se inicializa un Array de Strings, que almacena los datos para establecer la conexión
        datos = new String[]{
                             txtServidor.getText().toString(),
                             txtPuerto.getText().toString(),
                             txtBaseDatos.getText().toString(),
                             txtUsuario.getText().toString(),
                             edPassword.getText().toString()
                            };

		/*Se almacenan los datos de la consulta en el componente ListView.*/
		listaAgenda.setAdapter(new AdaptadorLista(this,citasAgenda()));

		txtNombreAgenda.setText("Cliente >> " + proximaCita()[0] + " " + proximaCita()[1]);
		txtFechaAgenda.setText("Fecha de cita >> " + proximaCita()[2]);
		txtDescripcionAgenda.setText("Descripción >> " + proximaCita()[3]);
	}
	
	/*Método que permite consultar la tabla y mostrar todos los registros almacenados.*/
	public ArrayList<Agenda> citasAgenda()
	{
			/*ArrayList que almacenará los objetos Agenda por cada iteración.*/
            ArrayList<Agenda> registros = new ArrayList<Agenda>();
			ArrayList<Agenda> datosConexion = new ArrayList<Agenda>();

            datosConexion.add(new Agenda(datos[0],datos[1],datos[2],datos[3],datos[4]));
		try{
			/*Asignamos el driver a una variable de tipo String.*/
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
            registros = new com.example.mysqlfinal.TareaAsincronaConsultas().execute(datosConexion).get();
            txtTotalRegistros.append(String.valueOf(" " + registros.size()));

		}catch(Exception ex)
		{
			Toast.makeText(Citas.this, "Error al obtener resultados de las citas almacenadas: "
    				+ ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		return registros;
	}	

	/*Método que permite la reunión más próxima por el campo fecha.*/
	public String[] proximaCita()
	{
			String[] citaProxima = null;
		try{
			/*Asignamos el driver a una variable de tipo String.*/
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
			/*Establecemos la conexión con el Servidor MySQL indicándole como parámetros la url construida,
			la Base de Datos a la que vamos a conectarnos, y el usuario y contraseña de acceso al servidor.*/
			citaProxima = new com.example.mysqlfinal.TareaAsincronaProxima().execute(datos).get();
		}catch(Exception ex)
		{
			Toast.makeText(Citas.this, "Error al obtener resultados de la próxima cita: "
    				+ ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		return citaProxima;
	}
	
	/*Método que refrescará los datos de la pantalla con los cambios que se hayan producido en la base de datos.*/
	public void sincronizarDatos(View view)
	{
		onCreate(bundle);
	}

    /*Evento onClick que permitirá introducir un nuevo registro en la base de datos externa.*/
    public void nuevaCita(View view)
    {
        try{
            /*Asignamos el driver a una variable de tipo String.*/
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            //Se declara un Array de Strings, dónde se almacenan los datos para establecer la conexión e insertar un nuevo registro.
            String[] nuevaCita = new String[]{
                                                edNombre.getText().toString(),
                                                edApellidos.getText().toString(),
                                                edFecha.getText().toString(),
                                                edDescripcion.getText().toString(),
                                                txtServidor.getText().toString(),
                                                txtPuerto.getText().toString(),
                                                txtBaseDatos.getText().toString(),
                                                txtUsuario.getText().toString(),
                                                edPassword.getText().toString()
                                            };

            if(edNombre.getText().toString().equals("") || edApellidos.getText().toString().equals("") ||
                    edFecha.getText().toString().equals("") || edDescripcion.getText().toString().equals(""))
            {
                Toast.makeText(this,"Debe indicar todos los datos de la cita.", Toast.LENGTH_LONG).show();
            }else
            {
                new com.example.mysqlfinal.TareaAsincronaInsertar().execute(nuevaCita);
                edNombre.setText("");
                edApellidos.setText("");
                edFecha.setText("");
                edDescripcion.setText("");
                Toast.makeText(Citas.this, "Nuevo registro insertado", Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex)
        {
            Toast.makeText(Citas.this, "Error al insertar nueva cita: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
