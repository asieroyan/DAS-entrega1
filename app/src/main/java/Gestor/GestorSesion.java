package Gestor;

public class GestorSesion {
    private static GestorSesion mGestorSesion;
    private String email;

    private GestorSesion(){}

    public static GestorSesion getGestorSesion() {
        if (mGestorSesion == null) {
            mGestorSesion = new GestorSesion();
        }
        return mGestorSesion;
    }

    public void setEmail (String pEmail) {
        this.email = pEmail;
    }

    public String getEmail () {
        return this.email;
    }
}
