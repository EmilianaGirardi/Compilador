//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
    	import java.io.*;
    	import AnalizadorLexico.Lexico;

private final Float infPositivo = (float) Math.pow(1.1754943, -38 );
private final Float supPositivo = (float) Math.pow(3.40282347, 38);
private final Float infNegativo = (float) Math.pow(-3.40282347, 38);
private final Float supNegativo = (float) Math.pow(-1.17549435, -38);


/*construccion del Lexico*/
String archivo = args[0];
private Lexico lexico = Lexico.getInstance(archivo);


private String truncarFueraRango(String cte, int linea) throws NumberFormatException{
   	/* Reemplazar 's' por 'e' para convertir a notación científica y parsear el float*/
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

//#line 64 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short MENORIGUAL=257;
public final static short ID=258;
public final static short ASIGNACION=259;
public final static short DISTINTO=260;
public final static short MAYORIGUAL=261;
public final static short SINGLE_CONSTANTE=262;
public final static short ENTERO_UNSIGNED=263;
public final static short OCTAL=264;
public final static short MULTILINEA=265;
public final static short REPEAT=266;
public final static short IF=267;
public final static short THEN=268;
public final static short ELSE=269;
public final static short BEGIN=270;
public final static short END=271;
public final static short END_IF=272;
public final static short OUTF=273;
public final static short TYPEDEF=274;
public final static short FUN=275;
public final static short RET=276;
public final static short GOTO=277;
public final static short TRIPLE=278;
public final static short UNSIGNED=279;
public final static short SINGLE=280;
public final static short TIPO_OCTAL=281;
public final static short UNTIL=282;
public final static short menos=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    3,    3,    3,    3,
   12,   12,   12,    4,    4,    4,    4,    2,    2,    2,
    2,   14,   16,   18,   18,   18,   17,   17,   17,   13,
   13,   13,   19,   19,   19,   20,   20,    8,    8,    7,
   21,   21,   21,   21,   21,   21,   23,   23,   22,   22,
   22,   22,   22,   22,   22,   22,   24,   24,   24,   26,
   25,   25,   25,   25,    6,    6,    5,    5,    5,    5,
    5,    5,   27,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   28,   28,   28,   28,   28,   28,   11,   11,
   11,   11,   11,    9,    9,    9,   15,   29,   10,   10,
};
final static short yylen[] = {                            2,
    5,    4,    4,    3,    2,    2,    1,    2,    1,    3,
    2,    3,    2,    1,    1,    1,    1,    1,    1,    1,
    2,    1,    3,    2,    1,    3,    2,    1,    1,    1,
    1,    2,    2,    1,    3,    2,    1,    1,    1,    9,
    8,    8,    2,    1,    1,    1,    2,    4,    3,    3,
    3,    3,    2,    2,    2,    1,    1,    3,    3,    3,
    2,    2,    2,    2,    2,    1,    1,    1,    1,    2,
    1,    1,    1,    2,    4,    7,    5,    4,    7,    6,
    4,    6,    5,    7,    4,    4,    6,    6,    6,    6,
    4,    6,    1,    1,    1,    1,    1,    1,    4,    4,
    3,    4,    2,    4,    3,    3,    6,    1,    2,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   38,   39,   37,    0,    0,    0,    0,   14,   15,
   16,   17,   18,   19,   20,   28,   29,   30,   31,    0,
    0,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   71,   72,   73,    0,    0,    0,    0,   69,    0,
    0,    0,   66,   68,    0,    0,    0,  103,  108,    0,
    0,    0,    0,  109,    0,    0,    0,    0,    0,    6,
    8,    0,    0,    0,    0,    0,    0,   36,    0,    0,
  105,   21,    0,  106,   74,    0,    0,   62,   63,   94,
   95,   93,    0,    0,   96,   97,   98,    0,    0,    0,
    0,   61,    0,    0,    0,    0,    0,  101,    0,    0,
    0,    0,  110,   70,   49,    3,   10,   24,   12,    0,
    0,    0,    2,    0,   75,   35,   23,  104,    0,    0,
    0,    0,    0,    0,   71,    0,    0,   59,   60,    0,
    0,   81,    0,    1,   99,  100,  102,    0,   48,   26,
    0,   45,    0,    0,    0,   91,    0,    0,    0,    0,
   85,    0,    0,   77,    0,    0,    0,   43,    0,    0,
   83,    0,    0,    0,    0,    0,    0,   82,    0,  107,
    0,    0,    0,   76,   92,    0,   89,   88,   87,   79,
    0,    0,    0,    0,    0,   84,    0,   42,    0,   41,
   40,
};
final static short yydgoto[] = {                          3,
  191,   17,   18,   69,   19,   49,   21,   22,   23,   24,
   25,   40,   26,   27,   28,   29,   30,   35,  154,  193,
  172,   51,   52,   53,   54,   64,   55,   99,   60,
};
final static short yysindex[] = {                      -234,
  802,  899,    0,  -37,  239,  900,  899,  -38, -263,  -19,
 -209,    0,    0,    0, 1066,  826,  -31,   -4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -253,
  843,    0, 1066,  919,  -44,  -34,  975,  900,    5,  889,
   -2,    0,    0,    0, -197, 1066,   28,   28,    0, 1243,
 1083,   10,    0,    0, -196,  860, 1001,    0,    0,   16,
 1066,   19,   34,    0, 1020,   24,   25,   32,  963,    0,
    0,  -20,  -44,   43, 1250,   70, 1101,    0, -147,  366,
    0,    0,  900,    0,    0, 1243,  566,    0,    0,    0,
    0,    0,  -12,  276,    0,    0,    0, 1083, 1066,   28,
   28,    0, 1066,  -53,  937,   69,   92,    0,  -29,   94,
 -163, 1143,    0,    0,    0,    0,    0,    0,    0,   78,
  103, -173,    0, 1066,    0,    0,    0,    0, 1202, 1083,
 1066,  -21,  119,  302,    0,  302, 1213,    0,    0, 1250,
  120,    0, -213,    0,    0,    0,    0,   80,    0,    0,
  189,    0,  -96,  122, 1226,    0, 1234, 1066, 1049, 1066,
    0, 1066,  950,    0,  -90, -101,  129,    0,  -97,  136,
    0, 1250,   86, 1066,   88,  110,  111,    0,  -91,    0,
  899,  -84,  899,    0,    0,  112,    0,    0,    0,    0,
  899,    0,  -83,  899,  -82,    0,    0,    0,  -76,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  187,  630,  647,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  677,    0,    0,    0,  508,    0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,  117,
  183,    0,    0,    0,    0,  196,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  702,  728,  206,    0,
    0,    0,  758,    0,  613,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  117,    0,    0,    0,    0,
    0,    0,   79,  105,    0,    0,    0,  432,    0,   27,
   53,    0,    0,    0,    0,    0,    0,    0,  117,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  778,
    0,    0,    0,    0,    0,    0,    0,    0,    0, 1258,
    0,    0,    0,  131,    0,  157,    0,    0,    0,  458,
    0,    0,  558,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  158,    0,    0,    0,  596,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  579,    0,    0,    0,    0,    0,    0,  533,    0,
    0,    0,    0,    0,    0,  483,    0,    0,    0,    0,
    0,  335,    0,    0,    0,    0,  402,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  102,   -6,   21,  166,    0, 1353,    0,  -47,    0,    0,
    0,  -88,    0,    0,    0,    0,  -30,  175,   58, -160,
 1328, 1306,   20, 1393,    0,    0,   42,  -75,    0,
};
final static int YYTABLESIZE=1547;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         79,
   67,   57,   34,   76,   32,   34,   97,   95,   96,   67,
  131,  133,   47,   93,   59,   94,  143,   48,  158,  122,
   61,   72,  195,    1,   67,   39,   64,   70,  141,   47,
   97,   95,   96,  199,   48,    2,   68,   34,   97,   95,
   96,   67,   67,   67,   67,   67,   62,   67,   63,   67,
  104,   68,   65,  103,   71,  163,  159,   68,  164,   67,
   67,   67,   67,   82,   85,   87,   64,   64,   64,   64,
   64,  105,   45,   64,  179,  111,   68,  113,   54,   81,
  148,   84,  116,  117,  152,   64,   64,   64,   64,  120,
  118,  153,   65,   65,   65,   65,   65,  114,  110,   65,
  120,  123,   16,   31,   55,   12,   13,   14,   56,  124,
  126,   65,   65,   65,   65,   12,   13,   14,   54,   54,
  153,   54,   54,   54,  128,   39,  185,  144,  187,  103,
   51,  103,  145,  192,  147,  192,  150,   54,   54,   54,
   54,  165,  151,  197,   55,   55,  192,   55,   55,   55,
  188,  189,  196,  103,  103,  103,   52,   57,  160,  162,
   57,  168,  169,   55,   55,   55,   55,  180,  181,  182,
   51,   51,  183,   51,   51,   51,  184,  173,  175,  176,
  190,  177,   56,   39,   67,  194,    5,  198,  200,   51,
   51,   51,   51,  186,  201,    4,   52,   52,   44,   52,
   52,   52,   80,   90,   73,   13,   91,   92,  167,    0,
    0,   68,    0,   78,    0,   52,   52,   52,   52,    0,
   32,   33,   56,   56,   33,   56,   56,   90,   41,  166,
   91,   92,   42,   43,   44,   90,    0,  121,   91,   92,
    0,   56,   56,   56,   56,   41,   58,    0,    0,   42,
   43,   44,    0,    0,  146,    0,    0,   67,   67,    0,
   67,   67,   67,   67,   67,    0,   67,   67,   67,   67,
    0,   67,   67,   67,   67,    0,   67,   67,   15,   67,
   67,   67,   67,   64,   67,   41,   64,   64,    0,   42,
   43,   44,   64,   64,   64,   64,    0,   64,   64,   64,
   64,    0,   64,   64,    0,   64,   64,   64,   64,   65,
   64,    0,   65,   65,    0,    0,    0,   47,   65,   65,
   65,   65,   48,   65,   65,   65,   65,    0,   65,   65,
    0,   65,   65,   65,   65,   54,   65,    0,   54,   54,
    0,    0,    0,  100,   54,   54,   54,   54,  101,   54,
   54,   54,   54,    0,   54,   54,    0,   54,   54,   54,
   54,   55,   54,    0,   55,   55,    0,    0,    0,    0,
   55,   55,   55,   55,   17,   55,   55,   55,   55,    0,
   55,   55,    0,   55,   55,   55,   55,   51,   55,    0,
   51,   51,    0,   17,    0,    0,   51,   51,   51,   51,
    0,   51,   51,   51,   51,   15,   51,   51,    0,   51,
   51,   51,   51,   52,   51,    0,   52,   52,    0,    0,
    0,    0,   52,   52,   52,   52,    0,   52,   52,   52,
   52,   53,   52,   52,    0,   52,   52,   52,   52,   56,
   52,   17,   56,   56,    0,    0,  152,    0,   56,   56,
   56,   56,    0,   56,   56,   56,   56,   58,   56,   56,
   17,   56,   56,   56,   56,    0,   56,   12,   13,   14,
    0,   53,   53,    0,   53,   53,   13,    0,    0,   13,
    0,    0,   90,    0,   13,   13,   13,    0,    0,    0,
   53,   53,   53,   53,    0,    0,   36,   58,   58,    0,
    0,   58,    0,    0,    5,    6,    0,   22,   37,    0,
    0,    8,    0,    0,   10,   11,   58,   58,   58,   58,
   38,    0,   90,    0,   90,    0,    0,   90,    0,   90,
    0,    0,   80,   41,    0,    0,    0,  135,   43,   44,
    0,   90,    0,    0,    0,    0,    0,   22,    0,   22,
    0,    0,   22,    0,   22,    0,    0,   78,    0,   41,
    0,    0,    0,   42,   43,   44,    0,    0,    0,    0,
    0,    0,   80,    0,   80,    0,    0,   80,   57,   80,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   80,   17,    0,    0,   86,    0,   78,    0,   78,
   17,   17,   78,    0,   78,   46,  132,   17,   17,  103,
   17,   17,   50,   17,   17,   17,   78,    0,   57,   57,
    0,    0,   57,   36,    0,   97,   95,   96,    0,    7,
    0,    5,    6,    0,    0,   86,  127,   57,    8,    0,
    0,   10,   11,    0,    0,    0,    9,    0,    0,    0,
    0,    0,   50,    0,   86,    0,    0,    0,    0,   17,
    0,    0,    0,    0,    0,    0,    0,   17,   17,    7,
    0,   50,   47,    0,   17,   17,   33,   17,   17,    0,
   17,   17,   17,    0,    0,    0,    9,    0,   53,    0,
    0,   53,   53,    0,    0,    0,    0,   53,   53,   53,
   53,   11,   53,   53,   53,   53,    0,   53,   53,    0,
   53,   53,   53,   53,   58,   53,   33,   58,   58,    0,
    0,    0,    0,   58,   58,   58,   58,   25,   58,   58,
   58,   58,    0,   58,   58,   33,   58,   58,   58,   58,
   90,   11,    0,    0,   90,   90,   90,    0,   90,   90,
   90,   90,    0,   90,   90,   90,   90,   32,   90,   90,
    0,   90,   90,   90,   90,   22,    0,   25,    0,   22,
   22,   22,    0,   22,   22,    0,   22,   27,   22,   22,
   22,   22,    0,   22,   22,    0,   22,   22,   22,   22,
   80,    0,    0,    0,   80,   80,   80,   32,   80,   80,
    0,   80,    0,   80,    0,   80,   80,    0,   80,   80,
    0,   80,   80,   80,   80,   78,   32,   27,    0,   78,
   78,   78,   90,   78,   78,   91,   92,    0,   78,    0,
   78,   78,    0,   78,   78,    0,   78,   78,   78,   78,
    0,   15,    0,    0,   57,   57,   57,   57,    0,   57,
   57,   57,   57,    0,   57,   57,    0,   57,   57,   57,
   57,   86,   86,   86,   86,   15,   86,   86,   86,   86,
    0,   86,   86,    0,   86,   86,   86,   86,   50,   50,
    0,   50,   15,   50,   50,   50,   50,    7,   50,   50,
    0,   50,   50,   50,   50,    7,    7,    0,    0,   15,
    7,    0,    7,    7,    9,    7,    7,    0,    7,    7,
    7,    0,    9,    9,    0,    0,    0,    9,    0,    9,
    9,    0,    9,    9,    0,    9,    9,    9,   46,    0,
   47,    0,    0,   45,    0,   48,    0,    0,   15,   46,
    0,   47,   33,   33,   45,    0,   48,   33,    0,   33,
   33,    0,   33,   33,    0,   33,   33,   33,    0,   11,
   47,    0,    0,   45,    0,   48,    0,   11,   11,    0,
    0,    0,   11,    0,   11,   11,   15,   11,   11,    0,
   11,   11,   11,    0,    0,   25,    0,    0,    0,   15,
    0,    0,    0,   25,   25,    0,    0,    0,   25,    0,
   25,   25,   15,   25,   25,    0,   25,   25,   25,    0,
    0,    0,    0,    0,   15,    0,    0,    0,    0,    0,
    0,  119,    0,   32,   32,    0,    0,    0,   32,    0,
   32,   32,    0,   32,   32,   27,   32,   32,   32,    0,
   46,  108,   47,   27,   27,   45,    0,   48,   27,    0,
   27,   27,    0,   27,   27,    0,   27,   27,   27,    4,
  115,   47,   93,    0,   94,    0,   48,    5,    6,    0,
    0,    7,    0,    0,    8,    9,    0,   10,   11,    0,
   12,   13,   14,    4,    0,    0,    0,    0,  174,    0,
   47,    5,    6,   45,    0,   48,   66,    0,    8,    9,
    4,   10,   11,    0,   12,   13,   14,   47,    5,    6,
   45,    0,   48,   74,    0,    8,    9,    4,   10,   11,
    0,   12,   13,   14,  100,    5,    6,   45,    0,  101,
  106,    0,    8,    9,    0,   10,   11,    0,   12,   13,
   14,  125,   47,   93,    0,   94,   41,   48,    0,    0,
   42,   43,   44,    0,    0,    0,    4,   41,    0,    0,
    0,   42,   43,   44,    5,    6,    0,    0,    0,    0,
   83,    8,    9,    0,   10,   11,   41,   12,   13,   14,
   42,   43,   44,  149,   47,   93,    0,   94,    0,   48,
    0,    0,    0,    0,   36,    0,    0,   12,   13,   14,
    0,    0,    5,    6,    0,    0,   37,   36,  142,    8,
    0,    0,   10,   11,    0,    5,    6,    0,    0,   37,
   36,  178,    8,    0,    0,   10,   11,    0,    5,    6,
    0,    0,   36,    0,    0,    8,    0,    0,   10,   11,
    5,    6,  156,   47,   93,    0,   94,    8,   48,    0,
   10,   11,    0,  161,   47,   93,    0,   94,   41,   48,
    0,    0,   42,   43,   44,  107,  170,   47,   93,    0,
   94,    0,   48,    0,  171,   47,   93,   41,   94,    0,
   48,   42,   43,   44,   47,   93,    0,   94,    0,   48,
    0,   47,   93,    0,   94,    0,   48,    0,   53,    0,
   53,   53,   97,   95,   96,    0,   41,    0,    0,    0,
   42,   43,   44,    0,    0,    0,    0,   53,   53,   53,
    0,    0,    0,   41,    0,    0,    0,   42,   43,   44,
    0,    0,    0,   50,    0,    0,    0,    0,    0,    0,
   41,    0,   65,    0,   42,   43,   44,    0,    0,    0,
    0,    0,    0,   20,   20,   98,    0,   20,   41,   20,
   75,   77,   42,   43,   44,   50,    0,   50,   20,    0,
   98,    0,    0,   86,    0,    0,    0,    0,    0,    0,
   98,    0,   98,   20,  109,    0,    0,    0,  112,   20,
    0,  130,    0,    0,    0,    0,    0,    0,  134,  136,
   41,    0,    0,    0,   42,   43,   44,    0,   20,    0,
   50,    0,    0,  129,   98,    0,    0,   98,    0,    0,
    0,   20,    0,    0,    0,    0,  137,    0,    0,    0,
  140,    0,   20,    0,   98,    0,    0,    0,    0,   88,
   89,    0,   98,  102,    0,   98,    0,    0,    0,    0,
    0,  155,    0,    0,    0,    0,    0,   20,  157,   41,
   98,    0,   98,   42,   43,   44,    0,    0,    0,    0,
   41,    0,    0,    0,   42,   43,   44,   98,    0,    0,
    0,    0,    0,   41,    0,    0,    0,   42,   43,   44,
  102,   41,  138,  139,    0,   42,   43,   44,    0,   90,
   41,    0,   91,   92,   42,   43,   44,   41,    0,    0,
    0,   42,   43,   44,   53,   20,    0,   53,   53,    0,
    0,    0,  102,    0,    0,    0,  102,    0,  102,    0,
    0,    0,    0,   20,    0,   20,    0,    0,    0,    0,
    0,    0,    0,   20,    0,    0,   20,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
    0,   40,   40,   34,  258,   40,   60,   61,   62,   16,
   86,   87,   42,   43,  278,   45,  105,   47,   40,   40,
   40,  275,  183,  258,   31,    5,    0,   59,  104,   42,
   60,   61,   62,  194,   47,  270,   16,   40,   60,   61,
   62,   41,   42,   43,   44,   45,  256,   47,  258,   56,
   41,   31,    0,   44,   59,  269,  132,   37,  272,   59,
   60,   61,   62,   59,  262,   46,   40,   41,   42,   43,
   44,  268,   45,   47,  163,   60,   56,   59,    0,   38,
  111,   40,   59,   59,  258,   59,   60,   61,   62,   69,
   59,  122,   40,   41,   42,   43,   44,   64,   57,   47,
   80,   59,    1,    2,    0,  279,  280,  281,    7,   40,
  258,   59,   60,   61,   62,  279,  280,  281,   40,   41,
  151,   43,   44,   45,   83,  105,   41,   59,   41,   44,
    0,   44,   41,  181,   41,  183,   59,   59,   60,   61,
   62,   62,   40,  191,   40,   41,  194,   43,   44,   45,
   41,   41,   41,   44,   44,   44,    0,   41,   40,   40,
   44,  258,   41,   59,   60,   61,   62,  258,  270,   41,
   40,   41,  270,   43,   44,   45,   41,  158,  159,  160,
  272,  162,    0,  163,  191,  270,    0,  271,  271,   59,
   60,   61,   62,  174,  271,    0,   40,   41,   41,   43,
   44,   45,   37,  257,   30,    0,  260,  261,  151,   -1,
   -1,  191,   -1,  258,   -1,   59,   60,   61,   62,   -1,
  258,  259,   40,   41,  259,   43,   44,  257,  258,   41,
  260,  261,  262,  263,  264,  257,   -1,  258,  260,  261,
   -1,   59,   60,   61,   62,  258,  285,   -1,   -1,  262,
  263,  264,   -1,   -1,  284,   -1,   -1,  257,  258,   -1,
  260,  261,  262,  263,  264,   -1,  266,  267,  268,  269,
   -1,  271,  272,  273,  274,   -1,  276,  277,   40,  279,
  280,  281,  282,  257,  284,  258,  260,  261,   -1,  262,
  263,  264,  266,  267,  268,  269,   -1,  271,  272,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,  282,  257,
  284,   -1,  260,  261,   -1,   -1,   -1,   42,  266,  267,
  268,  269,   47,  271,  272,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,  282,  257,  284,   -1,  260,  261,
   -1,   -1,   -1,   42,  266,  267,  268,  269,   47,  271,
  272,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
  282,  257,  284,   -1,  260,  261,   -1,   -1,   -1,   -1,
  266,  267,  268,  269,   40,  271,  272,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,  282,  257,  284,   -1,
  260,  261,   -1,   59,   -1,   -1,  266,  267,  268,  269,
   -1,  271,  272,  273,  274,   40,  276,  277,   -1,  279,
  280,  281,  282,  257,  284,   -1,  260,  261,   -1,   -1,
   -1,   -1,  266,  267,  268,  269,   -1,  271,  272,  273,
  274,    0,  276,  277,   -1,  279,  280,  281,  282,  257,
  284,   40,  260,  261,   -1,   -1,  258,   -1,  266,  267,
  268,  269,   -1,  271,  272,  273,  274,    0,  276,  277,
   59,  279,  280,  281,  282,   -1,  284,  279,  280,  281,
   -1,   40,   41,   -1,   43,   44,  271,   -1,   -1,  274,
   -1,   -1,    0,   -1,  279,  280,  281,   -1,   -1,   -1,
   59,   60,   61,   62,   -1,   -1,  258,   40,   41,   -1,
   -1,   44,   -1,   -1,  266,  267,   -1,    0,  270,   -1,
   -1,  273,   -1,   -1,  276,  277,   59,   60,   61,   62,
  282,   -1,   40,   -1,   42,   -1,   -1,   45,   -1,   47,
   -1,   -1,    0,  258,   -1,   -1,   -1,  262,  263,  264,
   -1,   59,   -1,   -1,   -1,   -1,   -1,   40,   -1,   42,
   -1,   -1,   45,   -1,   47,   -1,   -1,    0,   -1,  258,
   -1,   -1,   -1,  262,  263,  264,   -1,   -1,   -1,   -1,
   -1,   -1,   40,   -1,   42,   -1,   -1,   45,    0,   47,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   59,  258,   -1,   -1,    0,   -1,   40,   -1,   42,
  266,  267,   45,   -1,   47,  271,   41,  273,  274,   44,
  276,  277,    0,  279,  280,  281,   59,   -1,   40,   41,
   -1,   -1,   44,  258,   -1,   60,   61,   62,   -1,    0,
   -1,  266,  267,   -1,   -1,   40,  271,   59,  273,   -1,
   -1,  276,  277,   -1,   -1,   -1,    0,   -1,   -1,   -1,
   -1,   -1,   40,   -1,   59,   -1,   -1,   -1,   -1,  258,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  266,  267,   40,
   -1,   59,  271,   -1,  273,  274,    0,  276,  277,   -1,
  279,  280,  281,   -1,   -1,   -1,   40,   -1,  257,   -1,
   -1,  260,  261,   -1,   -1,   -1,   -1,  266,  267,  268,
  269,    0,  271,  272,  273,  274,   -1,  276,  277,   -1,
  279,  280,  281,  282,  257,  284,   40,  260,  261,   -1,
   -1,   -1,   -1,  266,  267,  268,  269,    0,  271,  272,
  273,  274,   -1,  276,  277,   59,  279,  280,  281,  282,
  258,   40,   -1,   -1,  262,  263,  264,   -1,  266,  267,
  268,  269,   -1,  271,  272,  273,  274,    0,  276,  277,
   -1,  279,  280,  281,  282,  258,   -1,   40,   -1,  262,
  263,  264,   -1,  266,  267,   -1,  269,    0,  271,  272,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,  282,
  258,   -1,   -1,   -1,  262,  263,  264,   40,  266,  267,
   -1,  269,   -1,  271,   -1,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,  282,  258,   59,   40,   -1,  262,
  263,  264,  257,  266,  267,  260,  261,   -1,  271,   -1,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,  282,
   -1,   40,   -1,   -1,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
  282,  266,  267,  268,  269,   40,  271,  272,  273,  274,
   -1,  276,  277,   -1,  279,  280,  281,  282,  266,  267,
   -1,  269,   40,  271,  272,  273,  274,  258,  276,  277,
   -1,  279,  280,  281,  282,  266,  267,   -1,   -1,   40,
  271,   -1,  273,  274,  258,  276,  277,   -1,  279,  280,
  281,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,   40,   -1,
   42,   -1,   -1,   45,   -1,   47,   -1,   -1,   40,   40,
   -1,   42,  266,  267,   45,   -1,   47,  271,   -1,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,   -1,  258,
   42,   -1,   -1,   45,   -1,   47,   -1,  266,  267,   -1,
   -1,   -1,  271,   -1,  273,  274,   40,  276,  277,   -1,
  279,  280,  281,   -1,   -1,  258,   -1,   -1,   -1,   40,
   -1,   -1,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,
  273,  274,   40,  276,  277,   -1,  279,  280,  281,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,
   -1,   59,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,
  273,  274,   -1,  276,  277,  258,  279,  280,  281,   -1,
   40,   41,   42,  266,  267,   45,   -1,   47,  271,   -1,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,  258,
   41,   42,   43,   -1,   45,   -1,   47,  266,  267,   -1,
   -1,  270,   -1,   -1,  273,  274,   -1,  276,  277,   -1,
  279,  280,  281,  258,   -1,   -1,   -1,   -1,   40,   -1,
   42,  266,  267,   45,   -1,   47,  271,   -1,  273,  274,
  258,  276,  277,   -1,  279,  280,  281,   42,  266,  267,
   45,   -1,   47,  271,   -1,  273,  274,  258,  276,  277,
   -1,  279,  280,  281,   42,  266,  267,   45,   -1,   47,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,   41,   42,   43,   -1,   45,  258,   47,   -1,   -1,
  262,  263,  264,   -1,   -1,   -1,  258,  258,   -1,   -1,
   -1,  262,  263,  264,  266,  267,   -1,   -1,   -1,   -1,
  282,  273,  274,   -1,  276,  277,  258,  279,  280,  281,
  262,  263,  264,   41,   42,   43,   -1,   45,   -1,   47,
   -1,   -1,   -1,   -1,  258,   -1,   -1,  279,  280,  281,
   -1,   -1,  266,  267,   -1,   -1,  270,  258,  272,  273,
   -1,   -1,  276,  277,   -1,  266,  267,   -1,   -1,  270,
  258,  272,  273,   -1,   -1,  276,  277,   -1,  266,  267,
   -1,   -1,  258,   -1,   -1,  273,   -1,   -1,  276,  277,
  266,  267,   41,   42,   43,   -1,   45,  273,   47,   -1,
  276,  277,   -1,   41,   42,   43,   -1,   45,  258,   47,
   -1,   -1,  262,  263,  264,  265,   41,   42,   43,   -1,
   45,   -1,   47,   -1,   41,   42,   43,  258,   45,   -1,
   47,  262,  263,  264,   42,   43,   -1,   45,   -1,   47,
   -1,   42,   43,   -1,   45,   -1,   47,   -1,   41,   -1,
   43,   44,   60,   61,   62,   -1,  258,   -1,   -1,   -1,
  262,  263,  264,   -1,   -1,   -1,   -1,   60,   61,   62,
   -1,   -1,   -1,  258,   -1,   -1,   -1,  262,  263,  264,
   -1,   -1,   -1,    6,   -1,   -1,   -1,   -1,   -1,   -1,
  258,   -1,   15,   -1,  262,  263,  264,   -1,   -1,   -1,
   -1,   -1,   -1,    1,    2,   50,   -1,    5,  258,    7,
   33,   34,  262,  263,  264,   38,   -1,   40,   16,   -1,
   65,   -1,   -1,   46,   -1,   -1,   -1,   -1,   -1,   -1,
   75,   -1,   77,   31,   57,   -1,   -1,   -1,   61,   37,
   -1,   86,   -1,   -1,   -1,   -1,   -1,   -1,   93,   94,
  258,   -1,   -1,   -1,  262,  263,  264,   -1,   56,   -1,
   83,   -1,   -1,   86,  109,   -1,   -1,  112,   -1,   -1,
   -1,   69,   -1,   -1,   -1,   -1,   99,   -1,   -1,   -1,
  103,   -1,   80,   -1,  129,   -1,   -1,   -1,   -1,   47,
   48,   -1,  137,   51,   -1,  140,   -1,   -1,   -1,   -1,
   -1,  124,   -1,   -1,   -1,   -1,   -1,  105,  131,  258,
  155,   -1,  157,  262,  263,  264,   -1,   -1,   -1,   -1,
  258,   -1,   -1,   -1,  262,  263,  264,  172,   -1,   -1,
   -1,   -1,   -1,  258,   -1,   -1,   -1,  262,  263,  264,
   98,  258,  100,  101,   -1,  262,  263,  264,   -1,  257,
  258,   -1,  260,  261,  262,  263,  264,  258,   -1,   -1,
   -1,  262,  263,  264,  257,  163,   -1,  260,  261,   -1,
   -1,   -1,  130,   -1,   -1,   -1,  134,   -1,  136,   -1,
   -1,   -1,   -1,  181,   -1,  183,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  191,   -1,   -1,  194,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=285;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"MENORIGUAL","ID","ASIGNACION","DISTINTO",
"MAYORIGUAL","SINGLE_CONSTANTE","ENTERO_UNSIGNED","OCTAL","MULTILINEA","REPEAT",
"IF","THEN","ELSE","BEGIN","END","END_IF","OUTF","TYPEDEF","FUN","RET","GOTO",
"TRIPLE","UNSIGNED","SINGLE","TIPO_OCTAL","UNTIL","menos","\") \"",
"\"(asignacion)\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID BEGIN conjunto_sentencias END ';'",
"programa : BEGIN conjunto_sentencias END ';'",
"programa : ID conjunto_sentencias END ';'",
"programa : ID BEGIN conjunto_sentencias",
"programa : ID conjunto_sentencias",
"conjunto_sentencias : declarativa ';'",
"conjunto_sentencias : declarativa",
"conjunto_sentencias : ejecutable ';'",
"conjunto_sentencias : ejecutable",
"conjunto_sentencias : conjunto_sentencias declarativa ';'",
"conjunto_sentencias : conjunto_sentencias declarativa",
"conjunto_sentencias : conjunto_sentencias sentencias_ejecutables ';'",
"conjunto_sentencias : conjunto_sentencias sentencias_ejecutables",
"ejecutable : sentencia_if",
"ejecutable : invocacion_fun",
"ejecutable : asig",
"ejecutable : retorno",
"ejecutable : repeat_until",
"ejecutable : goto",
"ejecutable : salida",
"bloque_sentencias_ejecutables : ejecutable ';'",
"bloque_sentencias_ejecutables : ejecutable",
"bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END",
"sentencias_ejecutables : ejecutable ';'",
"sentencias_ejecutables : ejecutable",
"sentencias_ejecutables : sentencias_ejecutables ejecutable ';'",
"sentencias_ejecutables : sentencias_ejecutables ejecutable",
"declarativa : declaracionFun",
"declarativa : declarvar",
"declarativa : def_triple",
"declarativa : declar_compuesto",
"declarvar : tipo lista_var",
"declar_compuesto : ID lista_var",
"lista_var : ID",
"lista_var : lista_var ',' ID",
"lista_var : lista_var ID",
"tipo : TIPO_OCTAL",
"tipo : UNSIGNED",
"tipo : SINGLE",
"declaracionFun : tipo FUN ID '(' parametro ')' BEGIN cuerpoFun END",
"declaracionFun : tipo FUN '(' parametro ')' BEGIN cuerpoFun END",
"declaracionFun : tipo FUN ID '(' ')' BEGIN cuerpoFun END",
"parametro : tipo ID",
"parametro : tipo",
"parametro : ID",
"cuerpoFun : retorno",
"cuerpoFun : conjunto_sentencias retorno",
"retorno : RET '(' exp_arit ')'",
"retorno : '(' exp_arit ')'",
"asig : ID ASIGNACION exp_arit",
"exp_arit : exp_arit '+' termino",
"exp_arit : exp_arit '-' termino",
"exp_arit : exp_arit termino",
"exp_arit : exp_arit '+'",
"exp_arit : exp_arit '-'",
"exp_arit : termino",
"lista_exp_arit : exp_arit",
"lista_exp_arit : lista_exp_arit ',' exp_arit",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : termino factor",
"termino : '*' factor",
"termino : '/' factor",
"termino : termino '*'",
"termino : termino '/'",
"termino : factor",
"factor : ID",
"factor : constante",
"factor : invocacion_fun",
"etiqueta : ID '@'",
"constante : SINGLE_CONSTANTE",
"constante : ENTERO_UNSIGNED",
"constante : OCTAL",
"constante : '-' SINGLE_CONSTANTE",
"invocacion_fun : ID '(' exp_arit ')'",
"invocacion_fun : ID '(' tipo '(' exp_arit ')' ')'",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables END_IF",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables END_IF",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables",
"sentencia_if : IF condicion THEN END_IF",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables ELSE END_IF",
"condicion : '(' exp_arit comparador exp_arit ')'",
"condicion : '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')'",
"condicion : exp_arit comparador exp_arit ')'",
"condicion : '(' exp_arit comparador exp_arit",
"condicion : lista_exp_arit ')' comparador '(' lista_exp_arit ')'",
"condicion : '(' lista_exp_arit comparador '(' lista_exp_arit ')'",
"condicion : '(' lista_exp_arit ')' comparador lista_exp_arit ')'",
"condicion : '(' lista_exp_arit ')' comparador '(' lista_exp_arit",
"condicion : '(' exp_arit exp_arit ')'",
"condicion : '(' lista_exp_arit ')' '(' lista_exp_arit ')'",
"comparador : MAYORIGUAL",
"comparador : MENORIGUAL",
"comparador : DISTINTO",
"comparador : '='",
"comparador : '>'",
"comparador : '<'",
"salida : OUTF '(' MULTILINEA ')'",
"salida : OUTF '(' exp_arit \") \"",
"salida : OUTF '(' ')'",
"salida : OUTF '(' condicion ')'",
"salida : OUTF \"(asignacion)\"",
"repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL condicion",
"repeat_until : REPEAT UNTIL condicion",
"repeat_until : REPEAT bloque_sentencias_ejecutables condicion",
"def_triple : TYPEDEF tipo_compuesto '<' tipo '>' ID",
"tipo_compuesto : TRIPLE",
"goto : GOTO etiqueta",
"goto : GOTO error ';'",
};

//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 55 "gramatica.y"
{System.out.println(“Error, Falta nombre de programa”);}
break;
case 3:
//#line 56 "gramatica.y"
{System.out.println(“Error de delimitador de programa”);}
break;
case 4:
//#line 57 "gramatica.y"
{System.out.println(“Error de delimitador de programa”);}
break;
case 5:
//#line 58 "gramatica.y"
{System.out.println(“Error de delimitador de programa”);}
break;
case 7:
//#line 61 "gramatica.y"
{System.out.println(“Falta ;”);}
break;
case 9:
//#line 63 "gramatica.y"
{System.out.println(“Falta ;”);}
break;
case 11:
//#line 65 "gramatica.y"
{System.out.println(“Falta ;”);}
break;
case 13:
//#line 67 "gramatica.y"
{System.out.println(“Falta ;”);}
break;
case 22:
//#line 78 "gramatica.y"
{System.out.println(“Falta ;”);}
break;
case 25:
//#line 82 "gramatica.y"
{System.out.println(“Falta ;”);}
break;
case 27:
//#line 84 "gramatica.y"
{System.out.println(“Falta ;”);}
break;
case 36:
//#line 96 "gramatica.y"
{System.out.println(“Error, falta ',' para diferenciar las variables);}
break;
case 41:
//#line 101 "gramatica.y"
{System.out.println(“Error, Falta nombre de funcion”);}
break;
case 42:
//#line 102 "gramatica.y"
{System.out.println(“Error, Falta parametro de funcion”);}
break;
case 44:
//#line 105 "gramatica.y"
{System.out.println(“Error, falta nombre del parametro formal”);}
break;
case 45:
//#line 106 "gramatica.y"
{System.out.println(“Error, falta tipo del parametro formal”);}
break;
case 49:
//#line 112 "gramatica.y"
{System.out.println(“Falta, la palabra ret que indica retorno”);}
break;
case 53:
//#line 118 "gramatica.y"
{System.out.printl(“Error, falta de operador”);}
break;
case 54:
//#line 119 "gramatica.y"
{System.out.printl(“Error, falta de operando”);}
break;
case 55:
//#line 120 "gramatica.y"
{System.out.printl(“Error, falta de operando”);}
break;
case 61:
//#line 129 "gramatica.y"
{System.out.printl(“Error, falta de operador”);}
break;
case 62:
//#line 130 "gramatica.y"
{System.out.printl(“Error, falta de operando”);}
break;
case 63:
//#line 131 "gramatica.y"
{System.out.printl(“Error, falta de operando”);}
break;
case 64:
//#line 132 "gramatica.y"
{System.out.printl(“Error, falta de operando”);}
break;
case 65:
//#line 133 "gramatica.y"
{System.out.printl(“Error, falta de operando”);}
break;
case 71:
//#line 142 "gramatica.y"
{String nuevoToken = truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea());
lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, nuevoToken);}
break;
case 78:
//#line 152 "gramatica.y"
{System.out.println(“Error, Falta END_IF de cierre”);}
break;
case 80:
//#line 154 "gramatica.y"
{System.out.println(“Error, Falta END_IF de cierre”);}
break;
case 81:
//#line 155 "gramatica.y"
{System.out.println(“Error, Falta de contenido en el bloque then”);}
break;
case 82:
//#line 156 "gramatica.y"
{System.out.println(“Error, Falta de contenido en el bloque else”);}
break;
case 85:
//#line 161 "gramatica.y"
{System.out.println(“Error, falta de parentesis en la condicion”);}
break;
case 86:
//#line 162 "gramatica.y"
{System.out.println(“Error, falta de parentesis en la condicion”);}
break;
case 87:
//#line 163 "gramatica.y"
{System.out.println(“Error, falta de parentesis en la condicion”);}
break;
case 88:
//#line 164 "gramatica.y"
{System.out.println(“Error, falta de parentesis en la condicion”);}
break;
case 89:
//#line 165 "gramatica.y"
{System.out.println(“Error, falta de parentesis en la condicion”);}
break;
case 90:
//#line 166 "gramatica.y"
{System.out.println(“Error, falta de parentesis en la condicion”);}
break;
case 91:
//#line 167 "gramatica.y"
{System.out.println(“Error, falta de comparador2);}
break;
case 92:
//#line 168 "gramatica.y"
{System.out.println(“Error, falta de comparador2);}
break;
case 101:
//#line 180 "gramatica.y"
{System.out.println(“Error, falta tipo del parametro formal”);}
break;
case 102:
//#line 181 "gramatica.y"
{System.out.println(“Error, parametro invalido”);}
break;
case 103:
//#line 182 "gramatica.y"
{System.out.println(“Error, parametro invalido”);}
break;
case 105:
//#line 185 "gramatica.y"
{System.out.println(“Error, falta cuerpo en la iteracion”);}
break;
case 106:
//#line 186 "gramatica.y"
{System.out.println(“Error, falta de until en la iteracion repeat”);}
break;
case 110:
//#line 192 "gramatica.y"
{System.out.println(“Error, falta de etiqueta en la sentencia GOTO”);}
break;
//#line 1053 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
