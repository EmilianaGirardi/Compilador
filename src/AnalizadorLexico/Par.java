package AnalizadorLexico;

import AnalizadorLexico.AccionesSemanticas.AccionSemantica;

public class Par {


    private int estado;
    private AccionSemantica as;

    //
    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setAs(AccionSemantica as) {
        this.as = as;
    }

    public AccionSemantica getAs() {
        return as;
    }

    public int getEstado() {
        return estado;
    }
}
