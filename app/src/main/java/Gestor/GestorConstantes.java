package Gestor;

public class GestorConstantes {

    private static GestorConstantes mGestorConstantes;
    private boolean sesion;

    private GestorConstantes() {

    }

    public GestorConstantes getGestorConstantes() {
        if (mGestorConstantes == null) {
            mGestorConstantes = new GestorConstantes();
        }
        return mGestorConstantes;
    }
    public boolean getSesion() {
        return this.sesion;
    }

    public void setSesion(boolean param) {
        this.sesion = param;
    }
}
