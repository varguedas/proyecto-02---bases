package models;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String correo;
    private String passwordHash;
    private String rol;

    public Usuario(
        int idUsuario,
        String nombre,
        String correo,
        String passwordHash,
        String rol
    ) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }
}