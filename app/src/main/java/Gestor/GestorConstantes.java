package Gestor;

public class GestorConstantes {

    private static GestorConstantes mGestorConstantes;
    private String idioma = "ESP";

    private GestorConstantes() {

    }

    public static GestorConstantes getGestorConstantes() {
        if (mGestorConstantes == null) {
            mGestorConstantes = new GestorConstantes();
        }
        return mGestorConstantes;
    }
    public void setIdioma (String pIdioma) {
        this.idioma = pIdioma;
    }

    public String getIdioma () {
        return this.idioma;
    }


}
