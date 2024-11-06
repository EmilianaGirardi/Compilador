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
        package AnalizadorSintactico;
    	import java.io.*;
    	import AnalizadorLexico.Lexico;
    	import GeneradorCodigo.Generador;
    	import java.util.ArrayList;
    	import AnalizadorLexico.TablaSimbolos;
//#line 24 "Parser.java"




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
public final static short TIPO_UNSIGNED=279;
public final static short TIPO_SINGLE=280;
public final static short TIPO_OCTAL=281;
public final static short UNTIL=282;
public final static short UMINUS=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    3,    3,    3,    3,    3,   10,   10,   10,
   11,   11,   11,   11,    2,    2,    2,    2,   14,   16,
   18,   18,   17,   17,   17,   13,   13,   13,   13,   13,
   13,   13,   19,   19,   19,   12,    5,    5,    5,   20,
   20,   20,   20,   20,   22,   22,   21,   21,   21,   21,
   21,   23,   23,   23,   23,   25,    6,    6,   24,   24,
   24,   26,    8,    8,    9,    9,    9,    9,    4,    4,
    4,    4,    4,    4,   27,   27,   27,   27,   27,   27,
   27,   27,   27,   27,   27,   28,   28,   28,   28,   28,
   28,    7,    7,    7,   15,   29,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    3,    4,    3,
    2,    3,    1,    2,    1,    1,    1,    1,    2,    2,
    1,    3,    1,    1,    1,   10,    9,    9,    9,    8,
    9,    8,    2,    1,    1,    5,    4,    7,    3,    3,
    3,    4,    4,    1,    1,    3,    3,    3,    1,    4,
    4,    1,    1,    1,    1,    4,    3,    3,    1,    1,
    1,    2,    2,    3,    4,    4,    3,    4,    5,    4,
    7,    6,    4,    6,    5,    9,    8,    8,    8,    8,
    4,    4,    4,    8,    5,    1,    1,    1,    1,    1,
    1,    4,    3,    3,    6,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,   35,   33,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   25,   26,   27,   28,    0,    0,    0,   31,
    0,    0,    0,    0,    0,    0,    0,    0,   69,   70,
   71,    0,   64,    0,    0,   59,   63,   65,    0,    0,
    0,  106,    0,    0,    0,   73,    0,    0,    0,    4,
    6,    0,    0,    0,    0,    0,   49,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  103,    0,  104,    0,
    0,    0,   97,   98,   96,    0,    0,   99,  100,  101,
    0,    0,    0,    0,    0,    0,    0,   77,    0,    0,
   74,   72,    3,    8,   10,    0,    0,    0,    2,    0,
   47,   66,   32,    0,   21,   18,    0,    0,   20,  102,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,    0,   57,   83,    0,    1,   78,   75,
   76,    0,    0,   45,    0,    0,    0,    0,   22,   19,
    0,    0,   93,    0,    0,    0,   52,   53,   91,   61,
   60,    0,   79,    0,    0,    0,   43,    0,    0,    0,
    0,   95,    0,    0,   85,    0,   84,    0,  105,    0,
    0,    0,   48,   46,    0,    0,    0,    0,    0,   81,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   42,    0,    0,    0,   40,   94,    0,   88,   87,
   89,   41,   38,    0,   37,   39,   86,   36,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   43,   19,   20,   21,   22,   37,
   75,   76,   23,   24,   25,   26,   27,   34,  146,  121,
   45,   82,   46,   47,   48,   56,   49,   91,   53,
};
final static short yysindex[] = {                      -228,
  271,  436,    0,   76, -233,   85,  436,   -2, -227, -101,
    0,    0,    0,  500,    8,   14,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -251, -178,  517,    0,
 -155,   65, -174,   53, -139,   85,   58,  -24,    0,    0,
    0,   92,    0,  470,   -7,    0,    0,    0, -163,  534,
   74,    0,   57,   54,   72,    0,   81,  108,  113,    0,
    0,  -29,   53, -155,  143,  141,    0,  123,   18,   64,
  -44,  -31,  178,  172,  -66,  -37,    0,   85,    0, -155,
  353,   -5,    0,    0,    0, -198,  400,    0,    0,    0,
 -155,  437,  577,  -74,  195,  196,  215,    0,  -35, -150,
    0,    0,    0,    0,    0,  222, -224,  141,    0, -155,
    0,    0,    0, -155,    0,    0,  200,    3,    0,    0,
  141,  393,   39, -155, -155,  -48,  220,   -7,  225,   -7,
   59,  228,    0,  240,    0,    0, -184,    0,    0,    0,
    0,  244,   45,    0,   23,  268,   77,  104,    0,    0,
  387,  272,    0,  105,  141,  291,    0,    0,    0,    0,
    0,   47,    0,   75,   93,  310,    0,   96,  323,  313,
 -155,    0,   95, -155,    0, -155,    0,  117,    0,  551,
  106,  551,    0,    0,   70, -155,  130,  136,  144,    0,
  551,  112,  551,  551,  122,  356,  149,  359,  365,  369,
  146,    0, -115,  158,  159,    0,    0,  392,    0,    0,
    0,    0,    0,  169,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -98,  362,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  128,    0,    0,    0,  -41,    0,    0,
    0,    0,    0,    0,  -16,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  410,  483,    0,
    0,    0,  145,    0,    0,  165,    0,    0,    0,    0,
    0,    0,    0,  298,    0,    0,    0,    0,    0,    0,
  150,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  194,    0,    0,
    0,    0,    0,    0,    0,    0,  318,    0,    0,    0,
  418,    0,    0,    0,    0,    0,    0,    9,    0,   34,
    0,    0,    0,    0,    0,    0,  219,    0,    0,    0,
    0,    0,    0,    0,  402,    0,    0,    0,    0,    0,
    0,    0,    0,   94,  447,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  245,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  111,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   15,  406,   12,    0,  532,    0,    0,    0,    0,  -71,
    0,  -70,    0,    0,    0,    0,  -17,  423,  308,  541,
  255,  -32,  288,    0,  552,    0,   -4,  -50,    0,
};
final static int YYTABLESIZE=841;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         62,
   62,   62,   62,   62,  118,   62,   30,   86,   32,   87,
  107,   90,   88,   89,   68,   32,   29,   62,   62,   62,
   62,   50,  137,   62,   54,   59,   54,   54,   54,    1,
  124,   77,   79,  144,   93,  126,   35,   51,  125,   92,
   59,    2,   54,   54,   54,   54,   74,  122,   36,   50,
   52,   50,   50,   50,   11,   12,   13,  127,  111,   38,
   86,   59,   87,   39,   40,   41,   60,   50,   50,   50,
   50,  152,   61,  120,   51,  156,   51,   51,   51,  153,
   64,   86,  142,   87,  162,  165,  117,  163,   70,  145,
  178,   33,   51,   51,   51,   51,   71,   42,   33,  159,
  173,   86,   38,   87,   94,   67,   39,   40,   41,  192,
  196,  195,  101,  125,   98,   32,  100,  169,   72,   86,
  201,   87,  204,  205,   42,  145,    5,    6,   11,   12,
   13,   80,  214,    8,  186,  102,   73,   10,  185,  103,
  187,  188,    4,  189,  170,  175,   86,   86,   87,   87,
    5,    6,   92,  197,   54,  213,   55,    8,    9,    5,
   73,   10,  110,   11,   12,   13,  104,    5,    5,   90,
  198,  105,    5,  125,    5,    5,  199,    5,    5,  125,
    5,    5,    5,   86,  200,   87,   30,  125,  112,  208,
   55,   72,  125,   55,  191,   35,  194,  136,   33,    5,
    6,  109,   59,   29,  116,   59,    8,  203,   83,   73,
   10,   84,   85,  113,   59,   62,   62,  114,   62,   62,
   62,   62,   62,   67,   62,   62,   62,   31,  106,   62,
  115,   62,   62,  119,   62,   62,  139,   62,   62,   62,
   54,   54,   62,   54,   54,   54,   54,   54,  141,   54,
   54,   54,   68,  138,   54,  140,   54,   54,  149,   54,
   54,  143,   54,   54,   54,   50,   50,   54,   50,   50,
   50,   50,   50,  150,   50,   50,   50,   80,  157,   50,
  167,   50,   50,  158,   50,   50,  160,   50,   50,   50,
   51,   51,   50,   51,   51,   51,   51,   51,  161,   51,
   51,   51,  144,   82,   51,  164,   51,   51,  168,   51,
   51,  174,   51,   51,   51,   38,   35,   51,  177,   39,
   40,   41,   38,   11,   12,   13,   39,   40,   41,   96,
  176,   38,  179,   30,   31,   39,   40,   41,   97,   78,
  128,  130,   38,   11,   12,   13,   39,   40,   41,   38,
  181,   92,   38,   39,   40,   41,   39,   40,   41,   92,
   92,   92,  180,  183,   92,  182,   92,   92,   90,   92,
   92,  184,   92,   92,   92,  193,   90,   90,   90,  133,
  135,   90,  202,   90,   90,   30,   90,   90,  190,   90,
   90,   90,  206,   30,   30,   86,  207,   87,   30,  209,
   30,   30,   29,   30,   30,  210,   30,   30,   30,  211,
   29,   29,   90,   88,   89,   29,  212,   29,   29,   58,
   29,   29,   67,   29,   29,   29,  171,  172,  215,  216,
   67,   67,  217,  151,   58,   67,  125,   67,   67,  218,
   67,   67,   44,   67,   67,   67,   90,   88,   89,   63,
  166,   68,   90,   88,   89,   58,    0,    0,   55,   68,
   68,   55,    0,    0,   68,    0,   68,   68,    0,   68,
   68,    0,   68,   68,   68,    0,   80,   55,   55,   55,
    0,    0,    0,    0,   80,   80,    0,   56,    0,   80,
   56,   80,   80,    0,   80,   80,    0,   80,   80,   80,
    0,    0,   82,    0,    0,    0,   56,   56,   56,    0,
   82,   82,   86,    0,   87,   82,    0,   82,   82,    0,
   82,   82,    0,   82,   82,   82,    0,    0,    4,   90,
   88,   89,   18,   18,    0,    0,    5,    6,   18,    0,
    7,    0,    0,    8,    9,   18,   44,   10,    0,   11,
   12,   13,   28,   28,    0,   23,    0,    0,   28,    0,
   18,    0,    0,   23,   23,   28,   18,    0,   23,    0,
   23,   66,   69,   23,   23,   24,   44,   44,    0,    0,
   28,   18,   81,   24,   24,    0,   28,    0,   24,    0,
   24,   99,    0,   24,   24,    0,   58,    0,    0,   58,
    0,   28,    0,    0,  108,    0,   18,    0,   58,   83,
   38,    0,   84,   85,   39,   40,   41,    0,   44,    7,
    0,  123,    0,    0,    0,    0,   28,    7,    7,    0,
    0,  131,    7,    0,    7,    7,    0,    7,    7,    0,
    7,    7,    7,   83,    0,    0,   84,   85,    0,   83,
  147,    0,   84,   85,  148,  129,    0,   38,    0,    0,
    0,   39,   40,   41,  154,  155,    0,    9,    0,    0,
    0,    0,    0,    0,   55,    9,    9,   55,   55,    0,
    9,    0,    9,    9,    0,    9,    9,    0,    9,    9,
    9,    0,  132,    4,   38,    0,    0,    0,   39,   40,
   41,    5,    6,   56,    0,    0,   56,   56,    8,    9,
    0,   18,   10,   18,   11,   12,   13,    0,    0,    0,
    0,    0,   18,    0,   18,   18,   83,    0,    0,   84,
   85,   28,    0,   28,   18,    0,    0,    0,    0,    0,
   11,    0,   28,    0,   28,   28,    0,    0,   11,   11,
    0,    0,    0,   11,   28,   11,   11,    4,   11,   11,
    0,   11,   11,   11,    0,    5,    6,    0,    0,    0,
   57,    0,    8,    9,    4,    0,   10,    0,   11,   12,
   13,    0,    5,    6,    0,    0,    0,   65,    0,    8,
    9,    4,    0,   10,    0,   11,   12,   13,    0,    5,
    6,    0,    0,    0,   95,    0,    8,    9,    4,    0,
   10,    0,   11,   12,   13,    0,    5,    6,    0,    0,
    0,    0,    0,    8,    9,    0,   73,   10,    0,   11,
   12,   13,  134,    0,   38,    0,    0,    0,   39,   40,
   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   75,   47,  258,   43,   40,   45,
   40,   60,   61,   62,   32,   40,    2,   59,   60,   61,
   62,    7,   94,  275,   41,   14,   43,   44,   45,  258,
   81,   36,   37,  258,   42,   41,  270,   40,   44,   47,
   29,  270,   59,   60,   61,   62,   35,   80,  282,   41,
  278,   43,   44,   45,  279,  280,  281,  256,   41,  258,
   43,   50,   45,  262,  263,  264,   59,   59,   60,   61,
   62,  122,   59,   78,   41,  126,   43,   44,   45,   41,
  259,   43,  100,   45,  269,   41,   75,  272,  263,  107,
  162,  123,   59,   60,   61,   62,   44,   40,  123,   41,
  151,   43,  258,   45,  268,   41,  262,  263,  264,  180,
   41,  182,   59,   44,   41,   40,   60,   41,  258,   43,
  191,   45,  193,  194,   40,  143,  266,  267,  279,  280,
  281,   40,  203,  273,   40,   64,  276,  277,  171,   59,
  173,  174,  258,  176,   41,   41,   43,   43,   45,   45,
  266,  267,   59,  186,  256,  271,  258,  273,  274,  258,
  276,  277,   40,  279,  280,  281,   59,  266,  267,   59,
   41,   59,  271,   44,  273,  274,   41,  276,  277,   44,
  279,  280,  281,   43,   41,   45,   59,   44,  125,   41,
   41,  258,   44,   44,  180,  270,  182,  272,  123,  266,
  267,   59,  191,   59,  271,  194,  273,  193,  257,  276,
  277,  260,  261,  258,  203,  257,  258,   40,  260,  261,
  262,  263,  264,   59,  266,  267,  268,  259,  258,  271,
   59,  273,  274,  271,  276,  277,   41,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,  284,  266,
  267,  268,   59,   59,  271,   41,  273,  274,   59,  276,
  277,   40,  279,  280,  281,  257,  258,  284,  260,  261,
  262,  263,  264,  271,  266,  267,  268,   59,   59,  271,
  258,  273,  274,   59,  276,  277,   59,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,   59,  266,
  267,  268,  258,   59,  271,   62,  273,  274,   41,  276,
  277,   40,  279,  280,  281,  258,  270,  284,  272,  262,
  263,  264,  258,  279,  280,  281,  262,  263,  264,  256,
   40,  258,  258,  258,  259,  262,  263,  264,  265,  282,
   86,   87,  258,  279,  280,  281,  262,  263,  264,  258,
   41,  258,  258,  262,  263,  264,  262,  263,  264,  266,
  267,  268,  270,   41,  271,  270,  273,  274,  258,  276,
  277,   59,  279,  280,  281,  270,  266,  267,  268,   92,
   93,  271,  271,  273,  274,  258,  276,  277,  272,  279,
  280,  281,  271,  266,  267,   43,   41,   45,  271,   41,
  273,  274,  258,  276,  277,   41,  279,  280,  281,   41,
  266,  267,   60,   61,   62,  271,  271,  273,  274,   14,
  276,  277,  258,  279,  280,  281,   40,   41,  271,  271,
  266,  267,   41,   41,   29,  271,   44,  273,  274,  271,
  276,  277,   41,  279,  280,  281,   60,   61,   62,   27,
  143,  258,   60,   61,   62,   50,   -1,   -1,   41,  266,
  267,   44,   -1,   -1,  271,   -1,  273,  274,   -1,  276,
  277,   -1,  279,  280,  281,   -1,  258,   60,   61,   62,
   -1,   -1,   -1,   -1,  266,  267,   -1,   41,   -1,  271,
   44,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
   -1,   -1,  258,   -1,   -1,   -1,   60,   61,   62,   -1,
  266,  267,   43,   -1,   45,  271,   -1,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,   -1,   -1,  258,   60,
   61,   62,    1,    2,   -1,   -1,  266,  267,    7,   -1,
  270,   -1,   -1,  273,  274,   14,    6,  277,   -1,  279,
  280,  281,    1,    2,   -1,  258,   -1,   -1,    7,   -1,
   29,   -1,   -1,  266,  267,   14,   35,   -1,  271,   -1,
  273,   31,   32,  276,  277,  258,   36,   37,   -1,   -1,
   29,   50,   42,  266,  267,   -1,   35,   -1,  271,   -1,
  273,   51,   -1,  276,  277,   -1,  191,   -1,   -1,  194,
   -1,   50,   -1,   -1,   64,   -1,   75,   -1,  203,  257,
  258,   -1,  260,  261,  262,  263,  264,   -1,   78,  258,
   -1,   81,   -1,   -1,   -1,   -1,   75,  266,  267,   -1,
   -1,   91,  271,   -1,  273,  274,   -1,  276,  277,   -1,
  279,  280,  281,  257,   -1,   -1,  260,  261,   -1,  257,
  110,   -1,  260,  261,  114,  256,   -1,  258,   -1,   -1,
   -1,  262,  263,  264,  124,  125,   -1,  258,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  266,  267,  260,  261,   -1,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,   -1,  256,  258,  258,   -1,   -1,   -1,  262,  263,
  264,  266,  267,  257,   -1,   -1,  260,  261,  273,  274,
   -1,  180,  277,  182,  279,  280,  281,   -1,   -1,   -1,
   -1,   -1,  191,   -1,  193,  194,  257,   -1,   -1,  260,
  261,  180,   -1,  182,  203,   -1,   -1,   -1,   -1,   -1,
  258,   -1,  191,   -1,  193,  194,   -1,   -1,  266,  267,
   -1,   -1,   -1,  271,  203,  273,  274,  258,  276,  277,
   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,  258,   -1,  277,   -1,  279,  280,
  281,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,
  274,  258,   -1,  277,   -1,  279,  280,  281,   -1,  266,
  267,   -1,   -1,   -1,  271,   -1,  273,  274,  258,   -1,
  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,
   -1,   -1,   -1,  273,  274,   -1,  276,  277,   -1,  279,
  280,  281,  256,   -1,  258,   -1,   -1,   -1,  262,  263,
  264,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=284;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"MENORIGUAL","ID","ASIGNACION","DISTINTO",
"MAYORIGUAL","SINGLE_CONSTANTE","ENTERO_UNSIGNED","OCTAL","MULTILINEA","REPEAT",
"IF","THEN","ELSE","BEGIN","END","END_IF","OUTF","TYPEDEF","FUN","RET","GOTO",
"TRIPLE","TIPO_UNSIGNED","TIPO_SINGLE","TIPO_OCTAL","UNTIL","UMINUS","\") \"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID BEGIN conjunto_sentencias END ';'",
"programa : BEGIN conjunto_sentencias END ';'",
"programa : ID conjunto_sentencias END ';'",
"conjunto_sentencias : declarativa ';'",
"conjunto_sentencias : declarativa",
"conjunto_sentencias : ejecutable ';'",
"conjunto_sentencias : ejecutable",
"conjunto_sentencias : conjunto_sentencias declarativa ';'",
"conjunto_sentencias : conjunto_sentencias declarativa",
"conjunto_sentencias : conjunto_sentencias ejecutable ';'",
"conjunto_sentencias : conjunto_sentencias ejecutable",
"ejecutable : sentencia_if",
"ejecutable : invocacion_fun",
"ejecutable : asig",
"ejecutable : repeat_until",
"ejecutable : goto",
"ejecutable : salida",
"bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END",
"bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables retorno END",
"bloque_sentencias_ejecutables : BEGIN retorno END",
"sentencias_ejecutables : ejecutable ';'",
"sentencias_ejecutables : sentencias_ejecutables ejecutable ';'",
"sentencias_ejecutables : ejecutable",
"sentencias_ejecutables : sentencias_ejecutables ejecutable",
"declarativa : declaracionFun",
"declarativa : declarvar",
"declarativa : def_triple",
"declarativa : declar_compuesto",
"declarvar : tipo lista_var",
"declar_compuesto : ID lista_var",
"lista_var : ID",
"lista_var : lista_var ',' ID",
"tipo : TIPO_OCTAL",
"tipo : TIPO_UNSIGNED",
"tipo : TIPO_SINGLE",
"declaracionFun : tipo FUN ID '(' parametro ')' BEGIN conjunto_sentencias retorno END",
"declaracionFun : tipo FUN ID '(' parametro ')' BEGIN retorno END",
"declaracionFun : tipo FUN ID '(' parametro ')' BEGIN conjunto_sentencias END",
"declaracionFun : tipo FUN '(' parametro ')' BEGIN conjunto_sentencias retorno END",
"declaracionFun : tipo FUN '(' parametro ')' BEGIN retorno END",
"declaracionFun : tipo FUN ID '(' ')' BEGIN conjunto_sentencias retorno END",
"declaracionFun : tipo FUN ID '(' ')' BEGIN retorno END",
"parametro : tipo ID",
"parametro : tipo",
"parametro : ID",
"retorno : RET '(' exp_arit ')' ';'",
"invocacion_fun : ID '(' exp_arit ')'",
"invocacion_fun : ID '(' tipo '(' exp_arit ')' ')'",
"invocacion_fun : ID '(' ')'",
"exp_arit : exp_arit '+' termino",
"exp_arit : exp_arit '-' termino",
"exp_arit : exp_arit '+' error ';'",
"exp_arit : exp_arit '-' error ';'",
"exp_arit : termino",
"lista_exp_arit : exp_arit",
"lista_exp_arit : lista_exp_arit ',' exp_arit",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"termino : termino '*' error ';'",
"termino : termino '/' error ';'",
"factor : ID",
"factor : constante",
"factor : invocacion_fun",
"factor : triple",
"triple : ID '{' ENTERO_UNSIGNED '}'",
"asig : ID ASIGNACION exp_arit",
"asig : triple ASIGNACION exp_arit",
"constante : SINGLE_CONSTANTE",
"constante : ENTERO_UNSIGNED",
"constante : OCTAL",
"etiqueta : ID '@'",
"goto : GOTO etiqueta",
"goto : GOTO error ';'",
"salida : OUTF '(' MULTILINEA ')'",
"salida : OUTF '(' exp_arit \") \"",
"salida : OUTF '(' ')'",
"salida : OUTF '(' error ')'",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables END_IF",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables END_IF",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables",
"sentencia_if : IF condicion THEN END_IF",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables ELSE END_IF",
"condicion : '(' exp_arit comparador exp_arit ')'",
"condicion : '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')'",
"condicion : '(' '(' lista_exp_arit comparador '(' lista_exp_arit ')' ')'",
"condicion : '(' '(' lista_exp_arit ')' comparador lista_exp_arit ')' ')'",
"condicion : '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')'",
"condicion : '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')'",
"condicion : exp_arit comparador exp_arit ')'",
"condicion : '(' exp_arit comparador exp_arit",
"condicion : '(' exp_arit exp_arit ')'",
"condicion : '(' '(' lista_exp_arit ')' '(' lista_exp_arit ')' ')'",
"condicion : '(' '(' lista_exp_arit ')' ')'",
"comparador : MAYORIGUAL",
"comparador : MENORIGUAL",
"comparador : DISTINTO",
"comparador : '='",
"comparador : '>'",
"comparador : '<'",
"repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL condicion",
"repeat_until : REPEAT UNTIL condicion",
"repeat_until : REPEAT bloque_sentencias_ejecutables condicion",
"def_triple : TYPEDEF tipo_compuesto '<' tipo '>' ID",
"tipo_compuesto : TRIPLE",
};

//#line 316 "gramatica.y"

private Lexico lexico;
private Generador generador;
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
    generador = Generador.getInstance();
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


//#line 619 "Parser.java"
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
int yyparse() throws IOException {
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
case 1:
//#line 20 "gramatica.y"
{System.out.println("Se detecto: Programa");}
break;
case 2:
//#line 21 "gramatica.y"
{System.out.println("Error, Falta nombre de programa");}
break;
case 3:
//#line 22 "gramatica.y"
{System.out.println("Error de delimitador de programa ");}
break;
case 5:
//#line 26 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 7:
//#line 28 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 9:
//#line 30 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 11:
//#line 32 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 12:
//#line 38 "gramatica.y"
{System.out.println("Se detecto: Sentencia if ");}
break;
case 13:
//#line 39 "gramatica.y"
{System.out.println("Se detecto: Invocacion a funcion " + " en linea: " + lexico.getContadorLinea());}
break;
case 14:
//#line 40 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + " en linea: " + lexico.getContadorLinea());}
break;
case 15:
//#line 41 "gramatica.y"
{System.out.println("Se detecto: Ciclo repeat until ");}
break;
case 16:
//#line 42 "gramatica.y"
{System.out.println("Se detecto: Sentencia GOTO " + " en linea: " + lexico.getContadorLinea());}
break;
case 17:
//#line 43 "gramatica.y"
{System.out.println("Se detecto: Salida " + " en linea: " + lexico.getContadorLinea());}
break;
case 23:
//#line 53 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 24:
//#line 54 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 25:
//#line 62 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion ");}
break;
case 26:
//#line 63 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 27:
//#line 64 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 28:
//#line 65 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 38:
//#line 95 "gramatica.y"
{System.out.println("Error, falta retorno en funcion");}
break;
case 39:
//#line 96 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 40:
//#line 97 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 41:
//#line 98 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 42:
//#line 99 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 44:
//#line 104 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 45:
//#line 105 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 49:
//#line 114 "gramatica.y"
{System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
break;
case 50:
//#line 121 "gramatica.y"
{
                    yyval.sval = generador.addTerceto("+", val_peek(2).sval, val_peek(0).sval);
                    System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());
           }
break;
case 51:
//#line 126 "gramatica.y"
{
	                yyval.sval = generador.addTerceto("-", val_peek(2).sval, val_peek(0).sval);
	                System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());
	       }
break;
case 52:
//#line 141 "gramatica.y"
{
                    System.out.println("Error: Falta el término después de '+' en expresion aritmetica en línea: " + lexico.getContadorLinea());
           }
break;
case 53:
//#line 145 "gramatica.y"
{
                    System.out.println("Error: Falta el término después de '-' en expresión aritmetica en línea: " + lexico.getContadorLinea());
           }
break;
case 55:
//#line 157 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 158 "gramatica.y"
{
	yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);}
break;
case 57:
//#line 162 "gramatica.y"
{
        yyval.sval = generador.addTerceto("*", val_peek(2).sval, val_peek(0).sval);
        System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());}
break;
case 58:
//#line 165 "gramatica.y"
{
	yyval.sval = generador.addTerceto("/", val_peek(2).sval, val_peek(0).sval);
	System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 59:
//#line 168 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 60:
//#line 169 "gramatica.y"
{System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 170 "gramatica.y"
{System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 62:
//#line 173 "gramatica.y"
{
    yyval.sval = val_peek(0).sval;
    System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());}
break;
case 63:
//#line 176 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 64:
//#line 177 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 66:
//#line 181 "gramatica.y"
{
    String token = val_peek(3).sval+'{'+val_peek(1).sval+'}';
    /*verificar que constante sea 1, 2 o 3*/
    if (val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2") || val_peek(1).sval.equals("3")){
        ArrayList<Integer> atributos = new ArrayList<Integer>();
        atributos.add(258);
        atributos.add(5);
        TablaSimbolos TS = lexico.getTablaSimbolos();
        TS.agregarToken(token, atributos);
        yyval.sval = token;
    }
    else {
        System.out.println("Intento de acceso a una posición de triple inexistente en linea " + lexico.getContadorLinea());
        yyval.sval = token;
        /*en este punto, los tercetos se generan igual,
        aunque se intente acceder a un valor invalido del tercerto.
        El generador de assembler deberia volver a verificar si la constante es 1, 2 o 3
        y solo generar codigo en ese caso, de lo contrario lanzar error.
        */
    }
}
break;
case 67:
//#line 209 "gramatica.y"
{
    yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
    }
break;
case 68:
//#line 212 "gramatica.y"
{
    yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
    }
break;
case 69:
//#line 217 "gramatica.y"
{
    yyval.sval = val_peek(0).sval;
    lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea()));}
break;
case 70:
//#line 220 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 71:
//#line 221 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 73:
//#line 227 "gramatica.y"
{yyval.sval = generador.addTerceto("GOTO", val_peek(0).sval, null);}
break;
case 74:
//#line 228 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
case 77:
//#line 232 "gramatica.y"
{System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
break;
case 78:
//#line 233 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 240 "gramatica.y"
{

}
break;
case 80:
//#line 243 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 81:
//#line 244 "gramatica.y"
{System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
break;
case 82:
//#line 245 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 83:
//#line 246 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 84:
//#line 247 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 251 "gramatica.y"
{
        yyval.sval = generador.addTerceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);
        yyval.sval = generador.addTerceto("BF", yyval.sval, null);
        System.out.println("Se detecto: comparación");}
break;
case 86:
//#line 256 "gramatica.y"
{
        String[] lista1 = val_peek(6).sval.split(",");
        String[] lista2 = val_peek(2).sval.split(",");
        if (lista1.length != lista2.length){
            System.out.println("Los tamaños de las listas en la condicion no coinciden en linea: " + lexico.getContadorLinea());
        }else{
            if(lista1.length==1){
                yyval.sval = generador.addTerceto(val_peek(4).sval, lista1[0], lista2[0]);
            }else{
                yyval.sval= generador.addTerceto(val_peek(4).sval, lista1[0], lista2[0]);
                String auxTerceto;

                for (int i = 1; i<lista1.length; i++){
                    auxTerceto= generador.addTerceto(val_peek(4).sval, lista1[i], lista2[i]);
                    yyval.sval =generador.addTerceto("AND", yyval.sval, auxTerceto);
                }
            }

        }
	    System.out.println("Se detecto: comparación múltiple");
	  }
break;
case 87:
//#line 278 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 88:
//#line 279 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 89:
//#line 280 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 90:
//#line 281 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 91:
//#line 282 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 92:
//#line 283 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 93:
//#line 284 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
break;
case 94:
//#line 285 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 95:
//#line 286 "gramatica.y"
{System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
break;
case 103:
//#line 299 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 104:
//#line 300 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
//#line 1116 "Parser.java"
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
public void run() throws IOException {
  yyparse();
  generador.imprimirTercetos();
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
