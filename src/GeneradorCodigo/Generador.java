package GeneradorCodigo;

import java.util.ArrayList;

public class Generador {
    private ArrayList<Terceto> tercetos;

    public String addTerceto(String operador, String operando1, String operando2){
        Terceto terceto = new Terceto(operador, operando1, operando2);
        this.tercetos.add(terceto);
        return String.valueOf(this.tercetos.size()-1);
    }

    public Generador(){
        this.tercetos = new ArrayList<Terceto>();
    }
}
