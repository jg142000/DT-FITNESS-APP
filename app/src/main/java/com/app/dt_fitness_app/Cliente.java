package com.app.dt_fitness_app;

public class Cliente {

    private String Nombre;
    private String Contraseña;
    private String Telefono;
    private String DNI;
    private String Correo;
    private String Direccion;
    private String Bono;


    public Cliente() {
    }

    public Cliente(String nombre, String contraseña, String telefono, String DNI, String correo, String direccion, String bono) {
        Nombre = nombre;
        Contraseña = contraseña;
        Telefono = telefono;
        this.DNI = DNI;
        Correo = correo;
        Direccion = direccion;
        Bono = bono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getBono() {
        return Bono;
    }

    public void setBono(String bono) {
        Bono = bono;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }
}