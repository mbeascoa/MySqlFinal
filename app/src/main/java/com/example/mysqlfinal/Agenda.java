package com.example.mysqlfinal;

//Aplicación Android que permite conectar con un servidor MySQL y mostrar
//los registros almacenados en un ListView, simulando una agenda de citas.
//academiaandroid.com
//
//by José Antonio Gázquez Rodríguez

public class Agenda {

    private String nombre;
	private String apellidos;
	private String fecha;

    private String usuario;
    private String pass;
    private String baseDatos;
    private String puerto;
    private String ip;

    public Agenda(String nombre, String apellidos, String fecha)
    {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha = fecha;
    }

    public Agenda(String ip, String puerto, String baseDatos, String usuario, String pass)
    {
        this.ip = ip;
        this.puerto = puerto;
        this.baseDatos = baseDatos;
        this.usuario = usuario;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
