%{
        package AnalizadorSintactico;
    	import java.io.*;
    	import AnalizadorLexico.Lexico;
    	//import Lex.Lex;
%}

%token MENORIGUAL ID ASIGNACION DISTINTO MAYORIGUAL SINGLE_CONSTANTE ENTERO_UNSIGNED OCTAL MULTILINEA REPEAT IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET GOTO TRIPLE OCTAL TIPO_UNSIGNED TIPO_SINGLE TIPO_OCTAL UNTIL

%left '+' '-'
%left '/' '*'
%right menos

%%

/*---Programa---*/

programa : ID BEGIN conjunto_sentencias END ';' {System.out.println("Se detecto: Programa");}
      | BEGIN conjunto_sentencias END ';' {System.out.println("Error, Falta nombre de programa");}
      | ID conjunto_sentencias END ';' {System.out.println("Error de delimitador de programa ");}
      ;
      
conjunto_sentencias : declarativa ';'
			| declarativa {System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
			| ejecutable ';'
			| ejecutable {System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
			| conjunto_sentencias declarativa ';'
			| conjunto_sentencias declarativa {System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
			| conjunto_sentencias ejecutable ';'
			| conjunto_sentencias ejecutable {System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
			;
/*------*/

/*---EJECUTABLE---*/

ejecutable : sentencia_if {System.out.println("Se detecto: Sentencia if ");}
    |invocacion_fun  {System.out.println("Se detecto: Invocacion a funcion " + " en linea: " + lexico.getContadorLinea());}
    |asig  {System.out.println("Se detecto: Asignacion " + " en linea: " + lexico.getContadorLinea());}
    | repeat_until {System.out.println("Se detecto: Ciclo repeat until ");}
    | goto {System.out.println("Se detecto: Sentencia GOTO " + " en linea: " + lexico.getContadorLinea());}
    | salida {System.out.println("Se detecto: Salida " + " en linea: " + lexico.getContadorLinea());}
    ;

bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END
                                | BEGIN sentencias_ejecutables retorno END
                                | BEGIN retorno END
								;

sentencias_ejecutables : ejecutable ';'
 	| sentencias_ejecutables ejecutable ';'
 	| ejecutable {System.out.println("Falta ;");}
 	| sentencias_ejecutables ejecutable {System.out.println("Falta ;");}
	;


/*------*/

/*---DECLARATIVA---*/

declarativa : declaracionFun {System.out.println("Se detecto: Declaracion de funcion ");}
	| declarvar {System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
	| def_triple {System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
	| declar_compuesto {System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
	;

declarvar : tipo lista_var
    ;

declar_compuesto : ID lista_var 
	;

declaracionFun : tipo FUN ID '(' parametro ')' BEGIN conjunto_sentencias retorno END
	|  tipo FUN ID '(' parametro ')' BEGIN retorno END
	| tipo FUN ID '(' parametro ')' BEGIN conjunto_sentencias END {System.out.println("Error, falta retorno en funcion");}
	| tipo FUN  '(' parametro ')' BEGIN conjunto_sentencias retorno END {System.out.println("Error, Falta nombre de funcion");}
	| tipo FUN  '(' parametro ')' BEGIN retorno END {System.out.println("Error, Falta nombre de funcion");}
	|tipo FUN ID '(' ')' BEGIN conjunto_sentencias retorno END {System.out.println("Error, Falta parametro de funcion");}
	|tipo FUN ID '(' ')' BEGIN retorno END {System.out.println("Error, Falta parametro de funcion");}
	;
		
/*------*/

/*---VARIABLES---*/

lista_var : ID
	| lista_var ',' ID
	;

tipo : TIPO_OCTAL 
	| TIPO_UNSIGNED 
	| TIPO_SINGLE  
	;


/*------*/

/*---FUNCION---*/

declaracionFun : tipo FUN ID '(' parametro ')' BEGIN conjunto_sentencias retorno END
	|  tipo FUN ID '(' parametro ')' BEGIN retorno END
	| tipo FUN ID '(' parametro ')' BEGIN conjunto_sentencias END {System.out.println("Error, falta retorno en funcion");}
	| tipo FUN  '(' parametro ')' BEGIN conjunto_sentencias retorno END {System.out.println("Error, Falta nombre de funcion");}
	| tipo FUN  '(' parametro ')' BEGIN retorno END {System.out.println("Error, Falta nombre de funcion");}
	|tipo FUN ID '(' ')' BEGIN conjunto_sentencias retorno END {System.out.println("Error, Falta parametro de funcion");}
	|tipo FUN ID '(' ')' BEGIN retorno END {System.out.println("Error, Falta parametro de funcion");}
	;


parametro : tipo ID
	| tipo {System.out.println("Error, falta nombre del parametro formal");}
	| ID {System.out.println("Error, falta tipo del parametro formal");}
	;


retorno : RET '(' exp_arit ')' ';'
	;

invocacion_fun : ID '(' exp_arit ')'
	| ID '(' tipo '(' exp_arit ')' ')'  //para evitar conflictos si que hay una operación (suma, resta, etc)
	| ID '(' ')' {System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
	;

/*------*/

/*---EXPRESION ARITMETICA---*/

exp_arit : exp_arit '+' termino {System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());}
	| exp_arit '-' termino {System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());}
	| exp_arit '+' error ';' {System.out.println("Error: Falta el término después de '+' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
    | exp_arit '-' error ';'  {System.out.println("Error: Falta el término después de '-' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
	| termino
	;

lista_exp_arit : exp_arit
	| lista_exp_arit ',' exp_arit
	;

termino : termino '*' factor {System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());}
	| termino '/' factor {System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
	| factor
	| termino '*' error ';' {System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
    | termino '/' error ';'  {System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
	;

factor : ID {System.out.println("Se detecto: Identificador " + $1.sval + " en linea: " + lexico.getContadorLinea());}
    | ID '{' constante '}'
	| constante
	| invocacion_fun {System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
	;

/*------*/

/*---ASIGNACION ; ETIQUETA ; CONSTANTE ; SALIDA---*/

asig : ID ASIGNACION exp_arit {
    $$.sval = generador.addTerceto(":=", $1.sval, $2.sval);
    }

    | ID '{' constante '}' ASIGNACION exp_arit
 ;	

constante : SINGLE_CONSTANTE {lexico.getTablaSimbolos().editarLexema($1.sval, truncarFueraRango($1.sval, lexico.getContadorLinea()));}
    |ENTERO_UNSIGNED
    |OCTAL
    /* |   '-' SINGLE_CONSTANTE    %prec menos {lexico.getTablaSimbolos().editarLexema($2.sval, truncarFueraRango($2.sval+$1.sval, , lexico.getContadorLinea()));} */
	;

etiqueta : ID '@'
	;

goto : GOTO etiqueta
	| GOTO error ';' {System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
	;

salida : OUTF '(' MULTILINEA ')' | OUTF '(' exp_arit ') '
    |OUTF '(' ')'  {System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
    |OUTF '(' error ')' {System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
;

/*------*/

/*---CONDICIONALES---*/

sentencia_if : IF  condicion  THEN bloque_sentencias_ejecutables END_IF
	| IF  condicion  THEN bloque_sentencias_ejecutables {System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
	| IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables END_IF {System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
	| IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables {System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
	| IF  condicion  THEN END_IF {System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
	|IF condicion THEN bloque_sentencias_ejecutables ELSE END_IF {System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
	;


condicion : '(' exp_arit comparador exp_arit ')' {System.out.println("Se detecto: comparación");}
	| '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')' {System.out.println("Se detecto: comparación múltiple");}
    | '(' '(' lista_exp_arit  comparador '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
    | '(' '(' lista_exp_arit ')' comparador lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
    | '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
    | '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	| exp_arit comparador exp_arit ')' {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	| '(' exp_arit comparador exp_arit  {System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
	|'(' exp_arit exp_arit ')' {System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
	| '(' '(' lista_exp_arit ')' '(' lista_exp_arit ')' ')' {System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
	| '(' '(' lista_exp_arit ')'  ')' {System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
	;


comparador : MAYORIGUAL
	| MENORIGUAL
	| DISTINTO
	| '='
	| '>'
	| '<'
	;

repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL  condicion
	|REPEAT UNTIL condicion {System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
	| REPEAT bloque_sentencias_ejecutables condicion {System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
 	;	
/*-----*/

/*---TIPO COMPUESTO---*/

def_triple : TYPEDEF tipo_compuesto '<' tipo '>'  ID
	;

tipo_compuesto : TRIPLE
	;

/*-----*/		

/*--CODIGO---*/
%%

private Lexico lexico;
private final Float infPositivo = (float) Math.pow(1.1754943, -38 );
private final Float supPositivo = (float) Math.pow(3.40282347, 38);
private final Float infNegativo = (float) Math.pow(-3.40282347, 38);
private final Float supNegativo = (float) Math.pow(-1.17549435, -38);

public int yylex() throws IOException {
    int token = lexico.yylex();
    this.yylval = lexico.getYylval();
    return token;
}

public void yyerror(String mensaje) {
    System.out.println("Error: " + mensaje);
}

public Parser(String archivo) throws IOException {
    lexico = Lexico.getInstance(archivo);
}

private String truncarFueraRango(String cte, int linea) throws NumberFormatException{
   	// Reemplazar 's' por 'e' para convertir a notación científica y parsear el float
       cte = cte.replace('s', 'e');
       Float result = Float.parseFloat(cte);

       if(result>0.0f) {
	        if (infPositivo > result) {
	        	System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
	            String nuevaCte = infPositivo.toString().replace('E', 's');
	            return nuevaCte;

	        }else if(supPositivo < result) {
	        	System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
	            String nuevaCte = supPositivo.toString().replace('E', 's');
	            return nuevaCte;
	        }
       }else {
       	if(infNegativo > result) {
       		System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
	            String nuevaCte = infNegativo.toString().replace('E', 's');
	            return nuevaCte;
	        }else if(supNegativo < result) {
	        	System.out.println("Warning: constante fuera de rango. Linea: "+ linea);
	            String nuevaCte = supNegativo.toString().replace('E', 's');
	            return nuevaCte;
	        }
       }

       cte = cte.replace('e', 's');
       return cte;
   }

public static void main(String[] args) throws IOException {
    if(args.length > 0) {
          String archivo = args[0];
          try {
            Parser parser = new Parser(archivo);
            parser.run();
          }
          catch (IOException excepcion){
            excepcion.printStackTrace();
          }
        }
        else {
          System.out.println("Se debe ingresar un archivo a compilar");
        }
}


