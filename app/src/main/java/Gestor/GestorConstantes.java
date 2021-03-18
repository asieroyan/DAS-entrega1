package Gestor;

public class GestorConstantes {

    private static GestorConstantes mGestorConstantes;
    private boolean sesion;
    private boolean datosCargados = false;

    private GestorConstantes() {

    }

    public static GestorConstantes getGestorConstantes() {
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

    public boolean getDatosCargados(){
        return this.datosCargados;
    }

    public void cambiarDatosCargados(){
        this.datosCargados = !this.datosCargados;
    }
}
