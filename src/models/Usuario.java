package models;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String correo;
    private String passwordHash;

    public Usuario(int idUsuario, String nombre, String correo, String passwordHash) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}