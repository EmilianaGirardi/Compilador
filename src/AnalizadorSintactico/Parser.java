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
    	import GeneradorCodigo.Terceto;

//#line 26 "Parser.java"




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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    3,    3,    3,    3,    3,   10,   10,   10,
   11,   11,   11,   11,    2,    2,    2,    2,   14,   16,
   18,   18,   17,   17,   17,   13,   13,   13,   13,   13,
   13,   13,   19,   19,   19,   12,    5,    5,    5,   20,
   20,   20,   20,   20,   22,   22,   21,   21,   21,   21,
   21,   23,   23,   23,   23,   23,   23,   23,   24,    6,
    6,   25,    8,    8,    9,    9,    9,    9,    4,    4,
    4,    4,    4,    4,   26,   27,   28,   28,   28,   28,
   28,   28,   28,   28,   28,   28,   29,   29,   29,   29,
   29,   29,    7,    7,    7,   30,   15,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    3,    4,    3,
    2,    3,    1,    2,    1,    1,    1,    1,    2,    2,
    1,    3,    1,    1,    1,   10,    9,    9,    9,    8,
    9,    8,    2,    1,    1,    5,    4,    7,    3,    3,
    3,    4,    4,    1,    1,    3,    3,    3,    1,    4,
    4,    1,    1,    1,    1,    1,    1,    2,    4,    3,
    3,    2,    2,    3,    4,    4,    3,    4,    3,    2,
    5,    4,    2,    3,    3,    1,    5,    9,    8,    8,
    8,    8,    4,    4,    8,    5,    1,    1,    1,    1,
    1,    1,    4,    3,    3,    1,    6,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,  106,    0,    0,    0,    0,    0,
   34,   35,   33,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   25,   26,   27,   28,    0,    0,    0,    0,
    0,   31,    0,    0,    0,    0,    0,   65,   66,   67,
    0,    0,   63,    0,    0,   59,   64,    0,    0,    0,
    0,    0,    0,   73,    0,    0,    0,    4,    6,    0,
    0,    0,   86,    0,   83,    0,    0,    0,    0,    0,
    0,   49,    0,    0,    0,    0,    0,    0,    0,   68,
   98,   99,   97,    0,    0,  100,  101,  102,    0,    0,
    0,   85,    0,    0,    0,   77,    0,    0,   74,   72,
    3,    8,   10,    0,    0,    0,    0,    0,    0,    0,
    0,   79,    0,   84,  104,    0,  105,    2,    0,   47,
   69,   32,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   57,    0,   58,    1,   78,   75,   76,
    0,    0,   45,    0,    0,    0,   21,   18,    0,    0,
   20,    0,  103,    0,    0,    0,    0,    0,    0,   52,
   53,   93,   60,   61,    0,    0,    0,   43,    0,    0,
   22,   19,   81,    0,    0,   96,    0,    0,   87,    0,
  107,    0,    0,    0,    0,   48,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   46,    0,    0,    0,
    0,    0,    0,   42,    0,    0,    0,   40,   95,    0,
   90,   89,   91,   41,   38,    0,   37,   39,   88,   36,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   43,   19,   20,   21,   22,   66,
  110,  111,   23,   24,   25,   26,   27,   36,  145,  123,
   45,   79,   46,   47,   54,   29,   67,   48,   89,   30,
};
final static short yysindex[] = {                      -208,
  506, -127,    0,    1,    0,   93, -127,  -18, -250, -173,
    0,    0,    0,  344,  -15,    7,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -249, -200,  -80, -212,
  523,    0,  294,   69, -179,   49,  -36,    0,    0,    0,
   96, -211,    0,  500,  -23,    0,    0, -166,  540,   82,
   48,   61,   71,    0,   78,   92,  119,    0,    0,  -33,
   49,  294,    0, -224,    0, -174,  -98,   93,  -40,  137,
   58,    0,  144,   -8,   77,  -64,  294,  500,   68,    0,
    0,    0,    0,  -43,  272,    0,    0,    0,  294,  275,
  284,    0,  145,  162,  164,    0,   30, -203,    0,    0,
    0,    0,    0,  169, -233,   58,  -24,  170,  152,  306,
  -54,    0,  -58,    0,    0,   93,    0,    0,  294,    0,
    0,    0,   58,  223,  294,  294,  377,  157,  -23,  167,
  -23,   51,  173,    0,  174,    0,    0,    0,    0,    0,
  177,   56,    0,  -29,  199,  294,    0,    0,  186,  -14,
    0,   -7,    0,   72,  146,  208,   85,   58,  218,    0,
    0,    0,    0,    0,   12,    3,  241,    0,   25,  132,
    0,    0,    0,  249,  294,    0,  103,  294,    0,  294,
    0,  557,   28,  557,  256,    0,   81,  294,  115,  125,
  135,  557,   36,  557,  557,   52,    0,  287,  141,  289,
  293,  300,   97,    0, -109,   98,  102,    0,    0,  301,
    0,    0,    0,    0,    0,  105,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  392,  418,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  138,  -30,    0,    0,    0,
    0,    0,    0,    0,   -5,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  442,  462,    0,    0,    0,
  155,    0,    0,    0,    0,  175,    0,    0,    0,    0,
  195,    0,    0,    0,    0,    0,    0,  154,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  220,    0,    0,  574,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  248,    0,    0,    0,    0,    0,   20,    0,
   45,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  302,    0,    0,    0,    0,  588,    0,
    0,  245,    0,    0,    0,    0,  104,  383,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  121,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -1,   -4,  539,    0,  564,    0,    0,    0,    0,  -22,
    0,  -50,    0,    0,    0,    0,  -31,  325,  211,  586,
   76,  -59,  109,  583,    0,    0,  297,  -48,  -55,    0,
};
final static int YYTABLESIZE=865;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   31,   42,   73,   34,   42,   49,  105,   69,   32,   56,
   62,   62,   62,   62,   62,   34,   62,  124,   90,  115,
  117,   50,  125,   91,  143,   60,   56,   51,   62,   62,
   62,   62,  120,  107,   84,   54,   85,   54,   54,   54,
   34,    5,    6,   58,   56,   11,   12,   13,    8,    1,
   80,  108,   10,   54,   54,   54,   54,   64,   62,  150,
   50,    2,   50,   50,   50,   59,  141,  153,  156,   68,
  140,  159,   84,  144,   85,   11,   12,   13,   50,   50,
   50,   50,   52,   75,   53,   51,   35,   51,   51,   51,
  152,  162,   76,   84,   63,   85,  166,  112,   35,  177,
   84,   92,   85,   51,   51,   51,   51,   98,  127,   72,
  144,  126,  174,   42,   84,  187,   85,  189,  190,   99,
  191,  198,   96,   35,  126,  179,   42,   84,  199,   85,
    4,  193,   41,  196,  100,   77,  101,   42,    5,    6,
   42,  203,  188,  206,  207,    8,    9,   42,    4,   10,
  102,   11,   12,   13,  216,  200,    5,    6,  126,  129,
  131,  215,   94,    8,    9,  201,  108,   10,  126,   11,
   12,   13,  185,  114,   84,  202,   85,  103,  126,   92,
  192,  210,  195,  119,  126,  175,  176,   56,   63,   64,
   56,   65,  205,  122,   55,  118,   30,   55,  134,  136,
   56,  121,  138,  137,  139,   88,   86,   87,  142,  146,
  147,   64,  128,   29,   37,  160,  151,   37,   38,   39,
   40,   38,   39,   40,  104,  161,   62,   62,  168,   62,
   62,  163,  164,   80,   33,   62,   62,   62,  165,  169,
   62,  116,   62,   62,  171,   62,   62,  178,   62,   62,
   62,   54,   54,   70,   54,   54,  172,  180,   32,   33,
   54,   54,   54,  155,  173,   54,  126,   54,   54,  181,
   54,   54,  182,   54,   54,   54,   50,   50,   71,   50,
   50,  183,   88,   86,   87,   50,   50,   50,   55,  186,
   50,   55,   50,   50,  184,   50,   50,  194,   50,   50,
   50,   51,   51,   82,   51,   51,  204,   55,   55,   55,
   51,   51,   51,  143,  197,   51,   42,   51,   51,   42,
   51,   51,  208,   51,   51,   51,   37,  209,   42,  211,
   38,   39,   40,  212,   11,   12,   13,   94,   42,   37,
  213,  219,   44,   38,   39,   40,   95,   11,   12,   13,
   37,   61,  167,   37,   38,   39,   40,   38,   39,   40,
   37,   94,  113,    0,   38,   39,   40,  214,  217,   94,
   94,   94,  218,    0,   94,  220,   94,   94,   92,   94,
   94,    0,   94,   94,   94,    0,   92,   92,   92,    0,
    0,   92,    0,   92,   92,   30,   92,   92,    0,   92,
   92,   92,   81,   30,   30,   82,   83,    0,   30,    0,
   30,   30,   29,   30,   30,    0,   30,   30,   30,    0,
   29,   29,    0,   56,    0,   29,   56,   29,   29,    0,
   29,   29,   80,   29,   29,   29,   88,   86,   87,    0,
   80,   80,   56,   56,   56,   80,    0,   80,   80,    0,
   80,   80,   70,   80,   80,   80,    0,    0,    0,    0,
   70,   70,    0,    0,    0,   70,    0,   70,   70,    0,
   70,   70,    0,   70,   70,   70,    0,   71,    0,   81,
    0,    0,   82,   83,    0,   71,   71,    0,    0,    0,
   71,    0,   71,   71,    0,   71,   71,    0,   71,   71,
   71,    0,   82,    0,   55,    0,    0,   55,   55,    0,
   82,   82,    0,    0,    0,   82,    0,   82,   82,    0,
   82,   82,    0,   82,   82,   82,    0,  130,    0,   37,
  133,    0,   37,   38,   39,   40,   38,   39,   40,  135,
    0,   37,   84,    0,   85,   38,   39,   40,    0,    0,
    0,   37,   57,    0,    0,   38,   39,   40,    0,   88,
   86,   87,    0,  107,   18,   18,    0,    0,    0,   57,
   18,    5,    6,    0,    0,    0,  148,   18,    8,    0,
    0,  108,   10,   28,   28,    0,    0,   57,    0,   28,
    0,   44,    0,    0,   18,    0,   28,    0,    0,    0,
    0,    4,  109,    0,    0,    0,    0,    0,    0,    5,
    6,    0,   18,   28,   55,    0,    8,    9,   71,   74,
   10,    0,   11,   12,   13,    0,   78,   18,    0,    0,
    0,   28,    0,   81,    0,   97,   82,   83,    0,   56,
    0,    0,   56,   56,    0,    0,   28,  106,  149,    5,
    0,    0,    0,   44,   44,    0,    0,    5,    5,    0,
    0,    0,    5,    0,    5,    5,    0,    5,    5,    0,
    5,    5,    5,   18,  132,    7,    0,    0,    0,    0,
    0,    0,    0,    7,    7,    0,    0,    0,    7,    0,
    7,    7,   28,    7,    7,    0,    7,    7,    7,    9,
    0,   44,    0,    0,  154,    0,    0,    9,    9,    0,
  157,  158,    9,    0,    9,    9,    0,    9,    9,   11,
    9,    9,    9,    0,    0,    0,    0,   11,   11,    0,
   57,  170,   11,   57,   11,   11,    0,   11,   11,    0,
   11,   11,   11,   57,    0,   18,    0,   18,    0,    0,
    0,    0,    0,    0,    0,   18,   81,   18,   18,   82,
   83,    0,    0,    4,   28,    0,   28,    0,   18,    0,
    0,    5,    6,    0,   28,    7,   28,   28,    8,    9,
    4,    0,   10,    0,   11,   12,   13,   28,    5,    6,
    0,    0,    0,   70,    0,    8,    9,    4,    0,   10,
    0,   11,   12,   13,    0,    5,    6,    0,    0,    0,
   93,    0,    8,    9,    4,    0,   10,    0,   11,   12,
   13,    0,    5,    6,    0,    0,    0,    0,    0,    8,
    9,   23,  108,   10,    0,   11,   12,   13,    0,   23,
   23,    0,    0,    0,   23,   24,   23,    0,    0,   23,
   23,    0,    0,   24,   24,    0,    0,    0,   24,    0,
   24,    0,    0,   24,   24,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    2,   45,   34,   40,   45,    7,   40,   30,  258,   14,
   41,   42,   43,   44,   45,   40,   47,   77,   42,   68,
   69,   40,   78,   47,  258,  275,   31,  278,   59,   60,
   61,   62,   41,  258,   43,   41,   45,   43,   44,   45,
   40,  266,  267,   59,   49,  279,  280,  281,  273,  258,
  262,  276,  277,   59,   60,   61,   62,  270,  259,  110,
   41,  270,   43,   44,   45,   59,   98,  116,  124,  282,
   41,  127,   43,  105,   45,  279,  280,  281,   59,   60,
   61,   62,  256,  263,  258,   41,  123,   43,   44,   45,
  113,   41,   44,   43,  269,   45,   41,  272,  123,  155,
   43,  268,   45,   59,   60,   61,   62,   60,   41,   41,
  142,   44,   41,   45,   43,  175,   45,  177,  178,   59,
  180,   41,   41,  123,   44,   41,   45,   43,  188,   45,
  258,  182,   40,  184,   64,   40,   59,   45,  266,  267,
   45,  192,   40,  194,  195,  273,  274,   45,  258,  277,
   59,  279,  280,  281,  205,   41,  266,  267,   44,   84,
   85,  271,   59,  273,  274,   41,  276,  277,   44,  279,
  280,  281,   41,  272,   43,   41,   45,   59,   44,   59,
  182,   41,  184,   40,   44,   40,   41,  192,  269,  270,
  195,  272,  194,  258,   41,   59,   59,   44,   90,   91,
  205,  125,   41,   59,   41,   60,   61,   62,   40,   40,
   59,  270,  256,   59,  258,   59,  271,  258,  262,  263,
  264,  262,  263,  264,  258,   59,  257,  258,  258,  260,
  261,   59,   59,   59,  259,  266,  267,  268,   62,   41,
  271,  282,  273,  274,   59,  276,  277,   40,  279,  280,
  281,  257,  258,   59,  260,  261,  271,   40,  258,  259,
  266,  267,  268,   41,  272,  271,   44,  273,  274,  258,
  276,  277,  270,  279,  280,  281,  257,  258,   59,  260,
  261,   41,   60,   61,   62,  266,  267,  268,   41,   41,
  271,   44,  273,  274,  270,  276,  277,  270,  279,  280,
  281,  257,  258,   59,  260,  261,  271,   60,   61,   62,
  266,  267,  268,  258,   59,  271,   45,  273,  274,   45,
  276,  277,  271,  279,  280,  281,  258,   41,   45,   41,
  262,  263,  264,   41,  279,  280,  281,  256,   45,  258,
   41,   41,   41,  262,  263,  264,  265,  279,  280,  281,
  258,   27,  142,  258,  262,  263,  264,  262,  263,  264,
  258,  258,   66,   -1,  262,  263,  264,  271,  271,  266,
  267,  268,  271,   -1,  271,  271,  273,  274,  258,  276,
  277,   -1,  279,  280,  281,   -1,  266,  267,  268,   -1,
   -1,  271,   -1,  273,  274,  258,  276,  277,   -1,  279,
  280,  281,  257,  266,  267,  260,  261,   -1,  271,   -1,
  273,  274,  258,  276,  277,   -1,  279,  280,  281,   -1,
  266,  267,   -1,   41,   -1,  271,   44,  273,  274,   -1,
  276,  277,  258,  279,  280,  281,   60,   61,   62,   -1,
  266,  267,   60,   61,   62,  271,   -1,  273,  274,   -1,
  276,  277,  258,  279,  280,  281,   -1,   -1,   -1,   -1,
  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,   -1,  258,   -1,  257,
   -1,   -1,  260,  261,   -1,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,   -1,  258,   -1,  257,   -1,   -1,  260,  261,   -1,
  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,   -1,  256,   -1,  258,
  256,   -1,  258,  262,  263,  264,  262,  263,  264,  256,
   -1,  258,   43,   -1,   45,  262,  263,  264,   -1,   -1,
   -1,  258,   14,   -1,   -1,  262,  263,  264,   -1,   60,
   61,   62,   -1,  258,    1,    2,   -1,   -1,   -1,   31,
    7,  266,  267,   -1,   -1,   -1,  271,   14,  273,   -1,
   -1,  276,  277,    1,    2,   -1,   -1,   49,   -1,    7,
   -1,    6,   -1,   -1,   31,   -1,   14,   -1,   -1,   -1,
   -1,  258,   64,   -1,   -1,   -1,   -1,   -1,   -1,  266,
  267,   -1,   49,   31,  271,   -1,  273,  274,   33,   34,
  277,   -1,  279,  280,  281,   -1,   41,   64,   -1,   -1,
   -1,   49,   -1,  257,   -1,   50,  260,  261,   -1,  257,
   -1,   -1,  260,  261,   -1,   -1,   64,   62,  110,  258,
   -1,   -1,   -1,   68,   69,   -1,   -1,  266,  267,   -1,
   -1,   -1,  271,   -1,  273,  274,   -1,  276,  277,   -1,
  279,  280,  281,  110,   89,  258,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,
  273,  274,  110,  276,  277,   -1,  279,  280,  281,  258,
   -1,  116,   -1,   -1,  119,   -1,   -1,  266,  267,   -1,
  125,  126,  271,   -1,  273,  274,   -1,  276,  277,  258,
  279,  280,  281,   -1,   -1,   -1,   -1,  266,  267,   -1,
  192,  146,  271,  195,  273,  274,   -1,  276,  277,   -1,
  279,  280,  281,  205,   -1,  182,   -1,  184,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  192,  257,  194,  195,  260,
  261,   -1,   -1,  258,  182,   -1,  184,   -1,  205,   -1,
   -1,  266,  267,   -1,  192,  270,  194,  195,  273,  274,
  258,   -1,  277,   -1,  279,  280,  281,  205,  266,  267,
   -1,   -1,   -1,  271,   -1,  273,  274,  258,   -1,  277,
   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,  258,   -1,  277,   -1,  279,  280,
  281,   -1,  266,  267,   -1,   -1,   -1,   -1,   -1,  273,
  274,  258,  276,  277,   -1,  279,  280,  281,   -1,  266,
  267,   -1,   -1,   -1,  271,  258,  273,   -1,   -1,  276,
  277,   -1,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,
  273,   -1,   -1,  276,  277,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=282;
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
"TRIPLE","TIPO_UNSIGNED","TIPO_SINGLE","TIPO_OCTAL","UNTIL",
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
"factor : invocacion_fun",
"factor : triple",
"factor : SINGLE_CONSTANTE",
"factor : ENTERO_UNSIGNED",
"factor : OCTAL",
"factor : '-' SINGLE_CONSTANTE",
"triple : ID '{' ENTERO_UNSIGNED '}'",
"asig : ID ASIGNACION exp_arit",
"asig : triple ASIGNACION exp_arit",
"etiqueta : ID '@'",
"goto : GOTO etiqueta",
"goto : GOTO error ';'",
"salida : OUTF '(' MULTILINEA ')'",
"salida : OUTF '(' exp_arit ')'",
"salida : OUTF '(' ')'",
"salida : OUTF '(' error ')'",
"sentencia_if : condicion_if bloque_sentencias_ejecutables END_IF",
"sentencia_if : condicion_if bloque_sentencias_ejecutables",
"sentencia_if : condicion_if bloque_sentencias_ejecutables condicion_else bloque_sentencias_ejecutables END_IF",
"sentencia_if : condicion_if bloque_sentencias_ejecutables condicion_else bloque_sentencias_ejecutables",
"sentencia_if : condicion_if END_IF",
"sentencia_if : condicion_if condicion_else END_IF",
"condicion_if : IF condicion THEN",
"condicion_else : ELSE",
"condicion : '(' exp_arit comparador exp_arit ')'",
"condicion : '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')'",
"condicion : '(' '(' lista_exp_arit comparador '(' lista_exp_arit ')' ')'",
"condicion : '(' '(' lista_exp_arit ')' comparador lista_exp_arit ')' ')'",
"condicion : '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')' ')'",
"condicion : '(' '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')'",
"condicion : exp_arit comparador exp_arit ')'",
"condicion : '(' exp_arit comparador exp_arit",
"condicion : '(' '(' lista_exp_arit ')' '(' lista_exp_arit ')' ')'",
"condicion : '(' '(' lista_exp_arit ')' ')'",
"comparador : MAYORIGUAL",
"comparador : MENORIGUAL",
"comparador : DISTINTO",
"comparador : '='",
"comparador : '>'",
"comparador : '<'",
"repeat_until : sentencia_repeat bloque_sentencias_ejecutables UNTIL condicion",
"repeat_until : sentencia_repeat UNTIL condicion",
"repeat_until : sentencia_repeat bloque_sentencias_ejecutables condicion",
"sentencia_repeat : REPEAT",
"def_triple : TYPEDEF TRIPLE '<' tipo '>' ID",
};

//#line 453 "gramatica.y"

private Lexico lexico;
private Generador generador;
private final Float infPositivo = 1.17549435e-38f;//(float) Math.pow(1.1754943, -38 )
private final Float supPositivo = 3.40282347e38f;//(float) Math.pow(3.40282347, 38)
private final Float infNegativo = -3.40282347e38f;//(float) Math.pow(-3.40282347, 38)
private final Float supNegativo = -1.17549435e-38f;//(float) Math.pow(-1.17549435, -38)

    public final static int T_UNSIGNED = 1;
    public final static int  T_SINGLE = 2;
    public final static int  T_OCTAL = 3;
    public final static int  TIPO_MULTILINEA = 4;
    public final static int  TIPO_DESCONOCIDO = 50;
    public final static int TIPO_TRIPLE_UNSIGNED = 5;
    public final static int TIPO_TRIPLE_SINGLE = 6;
    public final static int TIPO_TRIPLE_OCTAL = 7;

    public ArrayList<String> tiposUsuario;

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
    this.tiposUsuario = new ArrayList<>();
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


//#line 637 "Parser.java"
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
//#line 19 "gramatica.y"
{System.out.println("Se detecto: Programa");}
break;
case 2:
//#line 20 "gramatica.y"
{System.out.println("Error, Falta nombre de programa");}
break;
case 3:
//#line 21 "gramatica.y"
{System.out.println("Error de delimitador de programa ");}
break;
case 5:
//#line 25 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 7:
//#line 27 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 9:
//#line 29 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 11:
//#line 31 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 12:
//#line 37 "gramatica.y"
{System.out.println("Se detecto: Sentencia if ");}
break;
case 13:
//#line 38 "gramatica.y"
{System.out.println("Se detecto: Invocacion a funcion " + " en linea: " + lexico.getContadorLinea());}
break;
case 14:
//#line 39 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + " en linea: " + lexico.getContadorLinea());}
break;
case 15:
//#line 40 "gramatica.y"
{System.out.println("Se detecto: Ciclo repeat until ");}
break;
case 16:
//#line 41 "gramatica.y"
{System.out.println("Se detecto: Sentencia GOTO " + " en linea: " + lexico.getContadorLinea());}
break;
case 17:
//#line 42 "gramatica.y"
{System.out.println("Se detecto: Salida " + " en linea: " + lexico.getContadorLinea());}
break;
case 23:
//#line 52 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 24:
//#line 53 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 25:
//#line 61 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion ");}
break;
case 26:
//#line 62 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 27:
//#line 63 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 28:
//#line 64 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 29:
//#line 67 "gramatica.y"
{
        String[] lista = val_peek(0).sval.split(",");
        TablaSimbolos TS = lexico.getTablaSimbolos();
        Integer tipo;
        for (String var : lista){
            tipo = Integer.parseInt(val_peek(1).sval);
            TS.editarTipo(var, tipo);
        }
    }
break;
case 30:
//#line 78 "gramatica.y"
{
        if (tiposUsuario.contains(val_peek(1).sval)){
            TablaSimbolos TS = lexico.getTablaSimbolos();
            Integer t = TS.getTipo(val_peek(1).sval);
                switch(t){
                    case(TIPO_TRIPLE_UNSIGNED):
                        t = T_UNSIGNED;
                        break;
                    case(TIPO_TRIPLE_SINGLE):
                        t = T_SINGLE;
                        break;
                    case(TIPO_TRIPLE_OCTAL):
                         t = T_OCTAL;
                         break;
                }
            String[] lista = val_peek(0).sval.split(",");
            for (String var : lista){
                for(int i=1; i<=3; i++){
                    String token = var+'{'+i+'}';
                    ArrayList<Integer> atributos = new ArrayList<Integer>();
                    atributos.add(258);
                    atributos.add(t);
                    TS.agregarToken(token, atributos);
                }
            }
        }
    }
break;
case 31:
//#line 112 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 32:
//#line 113 "gramatica.y"
{
	        yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	    }
break;
case 33:
//#line 118 "gramatica.y"
{yyval.sval = String.valueOf(T_OCTAL);}
break;
case 34:
//#line 119 "gramatica.y"
{yyval.sval = String.valueOf(T_UNSIGNED);}
break;
case 35:
//#line 120 "gramatica.y"
{yyval.sval = String.valueOf(T_SINGLE);}
break;
case 36:
//#line 128 "gramatica.y"
{
                TablaSimbolos TS = lexico.getTablaSimbolos();
                Integer tipo = Integer.parseInt(val_peek(9).sval);
                TS.editarTipo(val_peek(7).sval, tipo);
                Integer tipoRetorno = generador.getTerceto(Integer.parseInt(val_peek(1).sval)).getTipo();
                if (tipo != tipoRetorno){
                    System.out.println("Error: tipo de retorno invalido en funcion: " + val_peek(7).sval);
                }
     }
break;
case 37:
//#line 137 "gramatica.y"
{
	            TablaSimbolos TS = lexico.getTablaSimbolos();
                Integer tipo = Integer.parseInt(val_peek(8).sval);
                TS.editarTipo(val_peek(6).sval, tipo);
                Integer tipoRetorno = generador.getTerceto(Integer.parseInt(val_peek(1).sval)).getTipo();
                if (tipo != tipoRetorno){
                     System.out.println("Error: tipo de retorno invalido en funcion: " + val_peek(6).sval);
                }
	}
break;
case 38:
//#line 147 "gramatica.y"
{System.out.println("Error, falta retorno en funcion");}
break;
case 39:
//#line 148 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 40:
//#line 149 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 41:
//#line 150 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 42:
//#line 151 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 44:
//#line 156 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 45:
//#line 157 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 46:
//#line 161 "gramatica.y"
{
            yyval.sval = generador.addTerceto("RETORNO", val_peek(2).sval, null);
            Integer tipo = generador.getTerceto(Integer.parseInt(val_peek(2).sval)).getTipo();
            generador.getTerceto(Integer.parseInt(yyval.sval)).setTipo(tipo);
        }
break;
case 47:
//#line 168 "gramatica.y"
{
    yyval.sval = generador.addTerceto("INVOCACION", val_peek(3).sval, val_peek(1).sval);
    }
break;
case 49:
//#line 172 "gramatica.y"
{System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
break;
case 50:
//#line 179 "gramatica.y"
{
                    yyval.sval = generador.addTerceto("+", val_peek(2).sval, val_peek(0).sval);
                    System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());
           }
break;
case 51:
//#line 184 "gramatica.y"
{
	                yyval.sval = generador.addTerceto("-", val_peek(2).sval, val_peek(0).sval);
	                System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());
	       }
break;
case 52:
//#line 189 "gramatica.y"
{
                    System.out.println("Error: Falta el término después de '+' en expresion aritmetica en línea: " + lexico.getContadorLinea());
           }
break;
case 53:
//#line 193 "gramatica.y"
{
                    System.out.println("Error: Falta el término después de '-' en expresión aritmetica en línea: " + lexico.getContadorLinea());
           }
break;
case 55:
//#line 202 "gramatica.y"
{
        			yyval.sval = val_peek(0).sval;
    			}
break;
case 56:
//#line 205 "gramatica.y"
{
	    yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	}
break;
case 57:
//#line 210 "gramatica.y"
{
       			 yyval.sval = generador.addTerceto("*", val_peek(2).sval, val_peek(0).sval);
       			 System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());
   		 }
break;
case 58:
//#line 214 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("/", val_peek(2).sval, val_peek(0).sval);
        	System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());
		}
break;
case 59:
//#line 218 "gramatica.y"
{
	    	yyval.sval = val_peek(0).sval;
		}
break;
case 60:
//#line 222 "gramatica.y"
{System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 223 "gramatica.y"
{System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 62:
//#line 227 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
            System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());
        }
break;
case 63:
//#line 231 "gramatica.y"
{System.out.println("Se detecto: Invocación a función " + "en linea: " + lexico.getContadorLinea());}
break;
case 64:
//#line 232 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 65:
//#line 233 "gramatica.y"
{
		 	yyval.sval = truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea());
            lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 66:
//#line 237 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 67:
//#line 240 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 68:
//#line 243 "gramatica.y"
{
        	yyval.sval = truncarFueraRango("-"+val_peek(0).sval, lexico.getContadorLinea());
        	lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 69:
//#line 250 "gramatica.y"
{
    String token = val_peek(3).sval+'{'+val_peek(1).sval+'}';
    TablaSimbolos TS = lexico.getTablaSimbolos();
    if (TS.estaToken(token)){
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
case 70:
//#line 273 "gramatica.y"
{
            yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
        }
break;
case 71:
//#line 276 "gramatica.y"
{
        yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
    }
break;
case 72:
//#line 282 "gramatica.y"
{
    			yyval.sval = val_peek(1).sval;
    		}
break;
case 73:
//#line 287 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("GOTO", val_peek(0).sval, null);
       }
break;
case 74:
//#line 290 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
case 75:
//#line 293 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 76:
//#line 297 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 77:
//#line 301 "gramatica.y"
{System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
break;
case 78:
//#line 302 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 309 "gramatica.y"
{
							int pos = Integer.parseInt(generador.obtenerElementoPila().split("T")[1]);
							generador.eliminarPila();
							Terceto t = generador.getTerceto(pos);
							String label = "ET"+generador.getSizeTercetos();

							t.setTercerParametro(label);
							yyval.sval=generador.addTerceto(label, null, null);
			}
break;
case 80:
//#line 319 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 81:
//#line 320 "gramatica.y"
{
		System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());
		int pos = Integer.parseInt(generador.obtenerElementoPila().split("T")[1]);
		generador.eliminarPila();
		Terceto t = generador.getTerceto(pos);
		String label = "ET"+generador.getSizeTercetos();
		t.setTercerParametro(label);
		
		yyval.sval=generador.addTerceto(label, null, null);
	 }
break;
case 82:
//#line 330 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 83:
//#line 331 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 84:
//#line 332 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 335 "gramatica.y"
{
							yyval.sval = generador.addTerceto("BF", val_peek(1).sval, null);
							generador.agregarPila(yyval.sval);
				}
break;
case 86:
//#line 341 "gramatica.y"
{
							int pos = Integer.parseInt(generador.obtenerElementoPila().split("T")[1]);
							generador.eliminarPila();
							Terceto t = generador.getTerceto(pos);
							int tam = generador.getSizeTercetos()+1;
							String label = "ET"+tam;
							
							t.setTercerParametro(label);
							yyval.sval = generador.addTerceto("BI", null, null);
							generador.agregarPila(yyval.sval);

							generador.addTerceto(label, null, null);
				  }
break;
case 87:
//#line 357 "gramatica.y"
{
        		yyval.sval = generador.addTerceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);
        		System.out.println("Se detecto: comparación");
        	}
break;
case 88:
//#line 362 "gramatica.y"
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

	                yyval.sval = generador.addTerceto("BF", yyval.sval, null);
	                /*generador.addPila($$.sval);*/
	            }

	        }
		    System.out.println("Se detecto: comparación múltiple");
		  }
break;
case 89:
//#line 387 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 90:
//#line 388 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 91:
//#line 389 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 92:
//#line 390 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 93:
//#line 391 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 94:
//#line 392 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 95:
//#line 394 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 96:
//#line 395 "gramatica.y"
{System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
break;
case 97:
//#line 399 "gramatica.y"
{yyval.sval = ">=";}
break;
case 98:
//#line 400 "gramatica.y"
{yyval.sval = "<=";}
break;
case 99:
//#line 401 "gramatica.y"
{yyval.sval = "!=";}
break;
case 100:
//#line 402 "gramatica.y"
{yyval.sval = "=";}
break;
case 101:
//#line 403 "gramatica.y"
{yyval.sval = ">";}
break;
case 102:
//#line 404 "gramatica.y"
{yyval.sval = "<";}
break;
case 103:
//#line 407 "gramatica.y"
{
					yyval.sval = generador.addTerceto("BT", val_peek(0).sval, generador.obtenerElementoPila());
    				generador.eliminarPila();
				}
break;
case 104:
//#line 412 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 105:
//#line 413 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 106:
//#line 417 "gramatica.y"
{
				    yyval.sval = generador.addTerceto("ET" + generador.getSizeTercetos(), null, null);
				    generador.agregarPila('E' + yyval.sval);
				}
break;
case 107:
//#line 426 "gramatica.y"
{
    Integer t = Integer.parseInt(val_peek(2).sval);
    switch(t){
        case(T_UNSIGNED):
            lexico.getTablaSimbolos().editarTipo(val_peek(0).sval, TIPO_TRIPLE_UNSIGNED);
            break;
        case(T_SINGLE):
            lexico.getTablaSimbolos().editarTipo(val_peek(0).sval, TIPO_TRIPLE_SINGLE);
            break;

        case(T_OCTAL):
             lexico.getTablaSimbolos().editarTipo(val_peek(0).sval, TIPO_TRIPLE_OCTAL);
             break;

        /*case(default):
            System.out.printl("Se intentó definir un tipo Triple de un tipo invalido en linea: " + lexico.getContadorLinea());
            break;
         */
    }
        this.tiposUsuario.add(val_peek(0).sval);
    }
break;
//#line 1370 "Parser.java"
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
