package Modelo;

public class Anuncio {

    // Atributos del anuncio
    private int codigo;
    private String foto;
    private String titulo;
    private String descripcion;
    private String contacto;
    private String emailAnunciante;

    // Constructora del anuncio
    public Anuncio(int pCodigo, String foto, String pTitulo, String pDescripcion, String pContacto, String pEmailAnunciante) {
        this.codigo = pCodigo;
        this.foto = foto;
        this.titulo = pTitulo;
        this.descripcion = pDescripcion;
        this.contacto = pContacto;
        this.emailAnunciante = pEmailAnunciante;
    }

    // Getters para todos los elementos del anuncio
    public int getCodigo(){
        return this.codigo;
    }

    public String getFoto(){
        return this.foto;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public String getContacto(){
        return this.contacto;
    }

    public String getEmailAnunciante(){
        return this.emailAnunciante;
    }
}
