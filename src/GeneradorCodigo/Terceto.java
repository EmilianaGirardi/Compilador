package GeneradorCodigo;

public class Terceto {
    private String operador, operando1, operando2;

    public Terceto(String operador, String operando1, String operando2){
        this.operador = operador;
        this.operando1=operando1;
        this.operando2=operando2;
    }

    public void setPrimerParametro (String operador) {
        this.operador = operador;
    }

    public void setSegundoParamtero(String operando1) {
        this.operando1 = operando1;
    }

    public void setTercerPArametro(String operando2) {
        this.operando2 = operando2;
    }

    @Override
    public String toString() {
        return "(" + operador +", " + operando1 + ", " + operando2 + ")";
    }
}
