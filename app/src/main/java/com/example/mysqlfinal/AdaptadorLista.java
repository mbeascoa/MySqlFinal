package com.example.mysqlfinal;

//Aplicación Android que permite conectar con un servidor MySQL y mostrar
//los registros almacenados en un ListView, simulando una agenda de citas.
//academiaandroid.com
//
//by José Antonio Gázquez Rodríguez

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*Clase AdaptadorLista, que hereda de la clase base BaseAdapter, encargado de 
construir la vista de cada uno de los item que se mostrará en el componente ListView.*/
public class AdaptadorLista extends BaseAdapter{

	/*Se declaran las clases necesarias para posteriormente añadirlas como argumentos al constructor.*/
	private Context context;	
	private ArrayList<Agenda> listas;
	private LayoutInflater inflater;

	/*Constructor que será invocado desde el componente ListView (como argumento del método setAdapter())
	, y que recibe el contexto de la aplicación y los objetos almacenados en un ArrayList.*/
	public AdaptadorLista(Context context, ArrayList<Agenda> listas) 
	{	
		this.context = context;		
		this.listas = listas;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listas.size();
	}

	@Override
	public Object getItem(int position) {
		return listas.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View filas = null;
		if(convertView == null)
		{
			filas = inflater.inflate(R.layout.activity_adaptador_lista, parent, false);
		}else
		{
			filas = convertView;
		}

            /*Se asocian las variables de tipo TextView con sus controles a nivel de layout.*/
			ViewHolder.txtNombre = (TextView)filas.findViewById(R.id.txtNombre);
			ViewHolder.txtApellidos = (TextView)filas.findViewById(R.id.txtApellidos);
			ViewHolder.txtFecha = (TextView)filas.findViewById(R.id.txtFecha);

			/*Se asignan a cada control el índice de la columna a mostrar, a partir de los resultados
			de la consulta procesada. En cada ítem del control de selección ListView se mostrarán
			los datos de nombre,apellidos y fecha de reunión.*/
			ViewHolder.txtNombre.setText("Nombre y Apellidos: " + listas.get(position).getNombre());
			ViewHolder.txtApellidos.setText(listas.get(position).getApellidos());
			ViewHolder.txtFecha.setText("Fecha reunión: " + listas.get(position).getFecha());

		return filas;
	}

	static class ViewHolder {
		
		public static TextView txtNombre, txtApellidos, txtFecha;
	}
}


