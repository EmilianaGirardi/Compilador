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
    1,    3,    3,    3,    3,    3,    3,    3,   11,   11,
   11,   12,   12,   12,   12,    2,    2,    2,    2,   15,
   17,   19,   19,   18,   18,   18,   14,   14,   14,   14,
   14,   14,   14,   20,   20,   20,   13,    5,    5,    5,
   21,   21,   21,   21,   21,   23,   23,   22,   22,   22,
   22,   22,   24,   24,   24,   24,   24,   24,   24,   25,
    6,    6,   10,    8,    8,    9,    9,    9,    9,    4,
    4,    4,    4,    4,    4,   26,   27,   28,   28,   28,
   28,   28,   28,   28,   28,   28,   28,   29,   29,   29,
   29,   29,   29,    7,    7,    7,   30,   16,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    1,    3,    4,
    3,    2,    3,    1,    2,    1,    1,    1,    1,    2,
    2,    1,    3,    1,    1,    1,   10,    9,    9,    9,
    8,    9,    8,    2,    1,    1,    5,    4,    7,    3,
    3,    3,    4,    4,    1,    1,    3,    3,    3,    1,
    4,    4,    1,    1,    1,    1,    1,    1,    2,    4,
    3,    3,    2,    3,    3,    4,    4,    3,    4,    3,
    2,    5,    4,    2,    3,    3,    1,    5,    9,    8,
    8,    8,    8,    4,    4,    8,    5,    1,    1,    1,
    1,    1,    1,    4,    3,    3,    1,    6,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,  107,    0,    0,    0,    0,    0,
   35,   36,   34,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   18,   26,   27,   28,   29,    0,    0,    0,
    0,    0,   32,    0,    0,    0,   73,    0,    0,   66,
   67,   68,    0,    0,   64,    0,    0,   60,   65,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    4,    6,
    0,    0,    0,   87,    0,   84,    0,    0,    0,    0,
    0,    0,   50,    0,    0,    0,    0,    0,    0,    0,
   69,   99,  100,   98,    0,    0,  101,  102,  103,    0,
    0,    0,   86,    0,    0,    0,   78,    0,    0,   75,
   74,    3,    8,   10,    0,    0,    0,    0,    0,    0,
    0,    0,   80,    0,   85,  105,    0,  106,    2,    0,
   48,   70,   33,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,    0,   59,    1,   79,   76,
   77,    0,    0,   46,    0,    0,    0,   22,   19,    0,
    0,   21,    0,  104,    0,    0,    0,    0,    0,    0,
   53,   54,   94,   61,   62,    0,    0,    0,   44,    0,
    0,   23,   20,   82,    0,    0,   97,    0,    0,   88,
    0,  108,    0,    0,    0,    0,   49,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,    0,    0,
    0,    0,    0,    0,   43,    0,    0,    0,   41,   96,
    0,   91,   90,   92,   42,   39,    0,   38,   40,   89,
   37,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   45,   19,   20,   21,   22,   23,
   67,  111,  112,   24,   25,   26,   27,   28,   38,  146,
  124,   47,   80,   48,   49,   30,   68,   50,   90,   31,
};
final static short yysindex[] = {                      -237,
  453,  573,    0,   94,    0,   93,  573,    1, -219, -240,
    0,    0,    0,  505,   42,   49,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -249, -183, -227,
 -247,  522,    0,  294,   69, -138,    0,  113,  -36,    0,
    0,    0,   96, -125,    0,  500,    4,    0,    0, -106,
  539,   82,  101,  109,  107,  117,  119,  124,    0,    0,
  -33,  113,  294,    0, -127,    0, -235,  -87,   93,  -40,
  129,  102,    0,  156,   17,   65,  -60,  294,  500,   25,
    0,    0,    0,    0,  -43,  272,    0,    0,    0,  294,
  275,  284,    0,  140,  159,  160,    0,   32, -196,    0,
    0,    0,    0,    0,  163, -231,  102,  -20,  164,  151,
  319,  -66,    0,  -61,    0,    0,   93,    0,    0,  294,
    0,    0,    0,  102,  223,  294,  294,  378,  152,    4,
  153,    4,   57,  157,    0,  167,    0,    0,    0,    0,
    0,  170,   56,    0,  -29,  192,  294,    0,    0,  176,
  -31,    0,  -27,    0,   83,  146,  208,  136,  102,  218,
    0,    0,    0,    0,    0,    7,  -11,  232,    0,   12,
  150,    0,    0,    0,  249,  294,    0,  103,  294,    0,
  294,    0,  556,   28,  556,  236,    0,   51,  294,   68,
   72,   77,  556,   36,  556,  556,   44,    0,  282,   91,
  287,  289,  293,   70,    0, -107,   71,   92,    0,    0,
  302,    0,    0,    0,    0,    0,   97,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  333,  379,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  138,  -30,    0,
    0,    0,    0,    0,    0,    0,   -5,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  416,  436,    0,    0,
    0,  155,    0,    0,    0,    0,  175,    0,    0,    0,
    0,  195,    0,    0,    0,    0,    0,    0,  112,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  220,    0,    0,  473,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  248,    0,    0,    0,    0,    0,   20,
    0,   45,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  323,    0,    0,    0,    0,  590,
    0,    0,  245,    0,    0,    0,    0,  104,  383,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  121,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
   -1,   -4,   64,    0,  552,    0,    0,    0,    0,    0,
  -23,    0,  -41,    0,    0,    0,    0,  -32,  341,  230,
  559,    8,  -59,  -39,  569,    0,  307,  -45,  -57,    0,
};
final static int YYTABLESIZE=867;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   32,   44,   74,   35,   44,   51,  106,   70,   33,   57,
   63,   63,   63,   63,   63,   54,   63,   55,  125,   35,
    1,  126,   65,  116,  118,   61,  144,   57,   63,   63,
   63,   63,    2,   64,   69,   55,  113,   55,   55,   55,
   52,   64,   65,   37,   66,   91,   57,   11,   12,   13,
   92,  135,  137,   55,   55,   55,   55,  121,   53,   85,
   51,   86,   51,   51,   51,  128,  142,  157,  127,  151,
  160,  154,  141,  145,   85,   63,   86,   58,   51,   51,
   51,   51,   11,   12,   13,   52,   36,   52,   52,   52,
  153,  199,  130,  132,  127,   58,  167,  163,  178,   85,
   59,   86,   36,   52,   52,   52,   52,   60,  201,   73,
  145,  127,  202,   44,   58,  127,  188,  203,  190,  191,
  127,  192,   97,  175,   76,   85,   44,   86,  110,  200,
  108,  211,   43,   35,  127,   78,   81,   44,    5,    6,
   44,  194,  189,  197,   85,    8,   86,   44,  109,   10,
    4,  204,   56,  207,  208,   56,   77,   37,    5,    6,
   99,   93,   95,  216,  217,    8,    9,  100,  109,   10,
  101,   11,   12,   13,  150,  102,  180,  103,   85,   93,
   86,  193,  104,  196,  115,  176,  177,  119,   57,  122,
  186,   57,   85,  206,   86,  120,   31,  123,  138,  139,
  140,   57,  143,  147,  152,   89,   87,   88,   65,  148,
  161,  162,  129,   30,   39,  164,   36,   39,   40,   41,
   42,   40,   41,   42,  105,  165,   63,   63,  169,   63,
   63,  166,  170,   81,  172,   63,   63,   63,   34,  173,
   63,  117,   63,   63,  174,   63,   63,  179,   63,   63,
   63,   55,   55,   71,   55,   55,   58,  181,  183,   58,
   55,   55,   55,  156,  182,   55,  127,   55,   55,   58,
   55,   55,  184,   55,   55,   55,   51,   51,   72,   51,
   51,  185,   89,   87,   88,   51,   51,   51,   56,  187,
   51,   56,   51,   51,  198,   51,   51,  195,   51,   51,
   51,   52,   52,   83,   52,   52,  205,   56,   56,   56,
   52,   52,   52,  144,  209,   52,   44,   52,   52,   44,
   52,   52,  210,   52,   52,   52,   39,  212,   44,  213,
   40,   41,   42,  214,   11,   12,   13,   95,   44,   39,
  215,  218,  220,   40,   41,   42,   96,   11,   12,   13,
   39,   33,   34,   39,   40,   41,   42,   40,   41,   42,
   39,   95,  219,   45,   40,   41,   42,  221,   62,   95,
   95,   95,  168,  114,   95,    0,   95,   95,   93,   95,
   95,    0,   95,   95,   95,    0,   93,   93,   93,    0,
    0,   93,    0,   93,   93,   31,   93,   93,    0,   93,
   93,   93,   82,   31,   31,   83,   84,    0,   31,    0,
   31,   31,   30,   31,   31,    0,   31,   31,   31,    0,
   30,   30,    0,   57,    0,   30,   57,   30,   30,    0,
   30,   30,   81,   30,   30,   30,    0,   89,   87,   88,
   81,   81,   57,   57,   57,   81,    0,   81,   81,    0,
   81,   81,   71,   81,   81,   81,    0,    0,    0,    0,
   71,   71,    0,    0,    0,   71,    0,   71,   71,    0,
   71,   71,    0,   71,   71,   71,    0,   72,    0,   82,
    0,    0,   83,   84,    0,   72,   72,    0,    0,    0,
   72,    0,   72,   72,    0,   72,   72,    0,   72,   72,
   72,    0,   83,    0,   56,    0,    0,   56,   56,    0,
   83,   83,    0,    0,    0,   83,    0,   83,   83,    0,
   83,   83,    0,   83,   83,   83,    0,  131,    0,   39,
  134,    0,   39,   40,   41,   42,   40,   41,   42,  136,
    0,   39,   85,    0,   86,   40,   41,   42,    0,    0,
    0,   39,   18,   18,    0,   40,   41,   42,   18,   89,
   87,   88,    0,    0,   46,   18,    0,    0,    0,   29,
   29,    0,    0,    0,    0,   29,  108,    0,    0,    0,
    0,    0,   29,   18,    5,    6,    0,    0,    0,  149,
    5,    8,   72,   75,  109,   10,    0,    0,    5,    5,
   29,   79,   18,    5,    0,    5,    5,    0,    5,    5,
   98,    5,    5,    5,    0,    0,   18,    0,    0,   29,
    0,  107,    0,    0,    0,    0,    0,   46,   46,    0,
    0,    0,    0,   29,   82,    0,    7,   83,   84,   57,
    0,    0,   57,   57,    7,    7,    0,    0,  133,    7,
    0,    7,    7,    0,    7,    7,    0,    7,    7,    7,
    0,    0,   18,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,    0,   46,    0,    0,  155,   29,
    0,    9,    9,    0,  158,  159,    9,    0,    9,    9,
    0,    9,    9,   11,    9,    9,    9,    0,    0,    0,
    0,   11,   11,    0,    0,  171,   11,    0,   11,   11,
    4,   11,   11,    0,   11,   11,   11,    0,    5,    6,
    0,    0,    7,    0,    0,    8,    9,    0,    0,   10,
   24,   11,   12,   13,   18,    0,   18,    0,   24,   24,
    0,    0,    0,   24,   18,   24,   18,   18,   24,   24,
    0,   29,    0,   29,    0,    0,   82,   18,    0,   83,
   84,   29,    4,   29,   29,    0,    0,    0,    0,    0,
    5,    6,    0,    0,   29,   56,    0,    8,    9,    4,
    0,   10,    0,   11,   12,   13,    0,    5,    6,    0,
    0,    0,   71,    0,    8,    9,    4,    0,   10,    0,
   11,   12,   13,    0,    5,    6,    0,    0,    0,   94,
    0,    8,    9,    4,    0,   10,    0,   11,   12,   13,
    0,    5,    6,    0,    0,    0,    0,    0,    8,    9,
    4,  109,   10,    0,   11,   12,   13,    0,    5,    6,
    0,    0,    0,    0,    0,    8,    9,   25,    0,   10,
    0,   11,   12,   13,    0,   25,   25,    0,    0,    0,
   25,    0,   25,    0,    0,   25,   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    2,   45,   35,   40,   45,    7,   40,   31,  258,   14,
   41,   42,   43,   44,   45,  256,   47,  258,   78,   40,
  258,   79,  270,   69,   70,  275,  258,   32,   59,   60,
   61,   62,  270,  269,  282,   41,  272,   43,   44,   45,
   40,  269,  270,   64,  272,   42,   51,  279,  280,  281,
   47,   91,   92,   59,   60,   61,   62,   41,  278,   43,
   41,   45,   43,   44,   45,   41,   99,  125,   44,  111,
  128,  117,   41,  106,   43,  259,   45,   14,   59,   60,
   61,   62,  279,  280,  281,   41,  123,   43,   44,   45,
  114,   41,   85,   86,   44,   32,   41,   41,  156,   43,
   59,   45,  123,   59,   60,   61,   62,   59,   41,   41,
  143,   44,   41,   45,   51,   44,  176,   41,  178,  179,
   44,  181,   41,   41,  263,   43,   45,   45,   65,  189,
  258,   41,   40,   40,   44,   40,  262,   45,  266,  267,
   45,  183,   40,  185,   43,  273,   45,   45,  276,  277,
  258,  193,   41,  195,  196,   44,   44,   64,  266,  267,
   60,  268,   59,  271,  206,  273,  274,   59,  276,  277,
   64,  279,  280,  281,  111,   59,   41,   59,   43,   59,
   45,  183,   59,  185,  272,   40,   41,   59,  193,  125,
   41,  196,   43,  195,   45,   40,   59,  258,   59,   41,
   41,  206,   40,   40,  271,   60,   61,   62,  270,   59,
   59,   59,  256,   59,  258,   59,  123,  258,  262,  263,
  264,  262,  263,  264,  258,   59,  257,  258,  258,  260,
  261,   62,   41,   59,   59,  266,  267,  268,  259,  271,
  271,  282,  273,  274,  272,  276,  277,   40,  279,  280,
  281,  257,  258,   59,  260,  261,  193,   40,  270,  196,
  266,  267,  268,   41,  258,  271,   44,  273,  274,  206,
  276,  277,   41,  279,  280,  281,  257,  258,   59,  260,
  261,  270,   60,   61,   62,  266,  267,  268,   41,   41,
  271,   44,  273,  274,   59,  276,  277,  270,  279,  280,
  281,  257,  258,   59,  260,  261,  271,   60,   61,   62,
  266,  267,  268,  258,  271,  271,   45,  273,  274,   45,
  276,  277,   41,  279,  280,  281,  258,   41,   45,   41,
  262,  263,  264,   41,  279,  280,  281,  256,   45,  258,
  271,  271,   41,  262,  263,  264,  265,  279,  280,  281,
  258,  258,  259,  258,  262,  263,  264,  262,  263,  264,
  258,  258,  271,   41,  262,  263,  264,  271,   28,  266,
  267,  268,  143,   67,  271,   -1,  273,  274,  258,  276,
  277,   -1,  279,  280,  281,   -1,  266,  267,  268,   -1,
   -1,  271,   -1,  273,  274,  258,  276,  277,   -1,  279,
  280,  281,  257,  266,  267,  260,  261,   -1,  271,   -1,
  273,  274,  258,  276,  277,   -1,  279,  280,  281,   -1,
  266,  267,   -1,   41,   -1,  271,   44,  273,  274,   -1,
  276,  277,  258,  279,  280,  281,   -1,   60,   61,   62,
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
   -1,  258,    1,    2,   -1,  262,  263,  264,    7,   60,
   61,   62,   -1,   -1,    6,   14,   -1,   -1,   -1,    1,
    2,   -1,   -1,   -1,   -1,    7,  258,   -1,   -1,   -1,
   -1,   -1,   14,   32,  266,  267,   -1,   -1,   -1,  271,
  258,  273,   34,   35,  276,  277,   -1,   -1,  266,  267,
   32,   43,   51,  271,   -1,  273,  274,   -1,  276,  277,
   52,  279,  280,  281,   -1,   -1,   65,   -1,   -1,   51,
   -1,   63,   -1,   -1,   -1,   -1,   -1,   69,   70,   -1,
   -1,   -1,   -1,   65,  257,   -1,  258,  260,  261,  257,
   -1,   -1,  260,  261,  266,  267,   -1,   -1,   90,  271,
   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
   -1,   -1,  111,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  258,   -1,  117,   -1,   -1,  120,  111,
   -1,  266,  267,   -1,  126,  127,  271,   -1,  273,  274,
   -1,  276,  277,  258,  279,  280,  281,   -1,   -1,   -1,
   -1,  266,  267,   -1,   -1,  147,  271,   -1,  273,  274,
  258,  276,  277,   -1,  279,  280,  281,   -1,  266,  267,
   -1,   -1,  270,   -1,   -1,  273,  274,   -1,   -1,  277,
  258,  279,  280,  281,  183,   -1,  185,   -1,  266,  267,
   -1,   -1,   -1,  271,  193,  273,  195,  196,  276,  277,
   -1,  183,   -1,  185,   -1,   -1,  257,  206,   -1,  260,
  261,  193,  258,  195,  196,   -1,   -1,   -1,   -1,   -1,
  266,  267,   -1,   -1,  206,  271,   -1,  273,  274,  258,
   -1,  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,
   -1,   -1,  271,   -1,  273,  274,  258,   -1,  277,   -1,
  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,  271,
   -1,  273,  274,  258,   -1,  277,   -1,  279,  280,  281,
   -1,  266,  267,   -1,   -1,   -1,   -1,   -1,  273,  274,
  258,  276,  277,   -1,  279,  280,  281,   -1,  266,  267,
   -1,   -1,   -1,   -1,   -1,  273,  274,  258,   -1,  277,
   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,   -1,   -1,  276,  277,
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
"ejecutable : etiqueta",
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
"goto : GOTO ID '@'",
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

//#line 683 "gramatica.y"

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
    //¿sEtiqueta seria uso?
    public final static int  TIPO_ETIQUETA = 8;
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


//#line 643 "Parser.java"
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
case 18:
//#line 43 "gramatica.y"
{System.out.println("Se detecto: Etiqueta " + " en linea: " + lexico.getContadorLinea());}
break;
case 24:
//#line 53 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 25:
//#line 54 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 26:
//#line 62 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion ");}
break;
case 27:
//#line 63 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 28:
//#line 64 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 29:
//#line 65 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 30:
//#line 68 "gramatica.y"
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
case 31:
//#line 79 "gramatica.y"
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
case 32:
//#line 113 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 33:
//#line 114 "gramatica.y"
{
	        yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	    }
break;
case 34:
//#line 119 "gramatica.y"
{yyval.sval = String.valueOf(T_OCTAL);}
break;
case 35:
//#line 120 "gramatica.y"
{yyval.sval = String.valueOf(T_UNSIGNED);}
break;
case 36:
//#line 121 "gramatica.y"
{yyval.sval = String.valueOf(T_SINGLE);}
break;
case 37:
//#line 129 "gramatica.y"
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
case 38:
//#line 138 "gramatica.y"
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
case 39:
//#line 148 "gramatica.y"
{System.out.println("Error, falta retorno en funcion");}
break;
case 40:
//#line 149 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 41:
//#line 150 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 42:
//#line 151 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 43:
//#line 152 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 44:
//#line 156 "gramatica.y"
{
                TablaSimbolos TS = lexico.getTablaSimbolos();
                TS.editarTipo(val_peek(0).sval, Integer.parseInt(val_peek(1).sval));
            }
break;
case 45:
//#line 160 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 46:
//#line 161 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 47:
//#line 165 "gramatica.y"
{
            yyval.sval = generador.addTerceto("RETORNO", val_peek(2).sval, null);
            Integer tipo = generador.getTerceto(Integer.parseInt(val_peek(2).sval)).getTipo();
            generador.getTerceto(Integer.parseInt(yyval.sval)).setTipo(tipo);
        }
break;
case 48:
//#line 172 "gramatica.y"
{
        /*TODO verificar que el uso de ID sea nombre de función.*/
        /*TODO verificar que el tipo del parametro formal sea igual al del parametro real.*/
        /*podriamos poner en el nombre déla funcion en la TS como atributo el tipo del parametro que recibe,
        entonces aca lo verificamos. */
        yyval.sval = generador.addTerceto("INVOCACION", val_peek(3).sval, val_peek(1).sval);
        TablaSimbolos TS = lexico.getTablaSimbolos();
        Integer tipo = TS.getTipo(val_peek(3).sval);
        generador.getTerceto(Integer.parseInt(yyval.sval)).setTipo(tipo);
    }
break;
case 50:
//#line 183 "gramatica.y"
{System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
break;
case 51:
//#line 190 "gramatica.y"
{
                    /*verificación de tipos*/
                    Integer tipoExp, tipoTermino;
                    TablaSimbolos TS = lexico.getTablaSimbolos();
                    if (TS.estaToken(val_peek(2).sval)){
                        /*exp es un token*/
                        tipoExp = TS.getTipo(val_peek(2).sval);
                    }
                    else {
                        /*exp es un terceto*/
                        tipoExp = generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).getTipo();
                    }
                    if (TS.estaToken(val_peek(0).sval)){
                        /*termino es un token*/
                        tipoTermino = TS.getTipo(val_peek(0).sval);
                    }
                    else{
                        /*termino es un terceto*/
                        tipoTermino = generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).getTipo();
                    }
                    if(tipoExp != tipoTermino){
                        System.out.println("Incompatibilidad de tipos en suma, en linea " + lexico.getContadorLinea());
                    }

                    yyval.sval = generador.addTerceto("+", val_peek(2).sval, val_peek(0).sval);
                    generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoExp);
                    System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());
           }
break;
case 52:
//#line 219 "gramatica.y"
{
                    /*verificación de tipos*/
                    Integer tipoExp, tipoTermino;
                    TablaSimbolos TS = lexico.getTablaSimbolos();
                    if (TS.estaToken(val_peek(2).sval)){
                        /*exp es un token*/
                        tipoExp = TS.getTipo(val_peek(2).sval);
                    }
                    else {
                        /*exp es un terceto*/
                        tipoExp = generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).getTipo();
                    }
                    if (TS.estaToken(val_peek(0).sval)){
                        /*termino es un token*/
                        tipoTermino = TS.getTipo(val_peek(0).sval);
                    }
                    else{
                        /*termino es un terceto*/
                        tipoTermino = generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).getTipo();
                    }
                    if(tipoExp != tipoTermino){
                        System.out.println("Incompatibilidad de tipos en resta, en linea " + lexico.getContadorLinea());
                    }

	                yyval.sval = generador.addTerceto("-", val_peek(2).sval, val_peek(0).sval);
	                generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoExp);
	                System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());
	       }
break;
case 53:
//#line 248 "gramatica.y"
{
                    System.out.println("Error: Falta el término después de '+' en expresion aritmetica en línea: " + lexico.getContadorLinea());
           }
break;
case 54:
//#line 252 "gramatica.y"
{
                    System.out.println("Error: Falta el término después de '-' en expresión aritmetica en línea: " + lexico.getContadorLinea());
           }
break;
case 55:
//#line 256 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 56:
//#line 261 "gramatica.y"
{
        			yyval.sval = val_peek(0).sval;
    			}
break;
case 57:
//#line 264 "gramatica.y"
{
	    yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	}
break;
case 58:
//#line 269 "gramatica.y"
{
                    /*verificación de tipos*/
                    Integer tipoTermino, tipoFactor;
                    TablaSimbolos TS = lexico.getTablaSimbolos();
                    if (TS.estaToken(val_peek(2).sval)){
                        /*termino es un token*/
                        tipoTermino = TS.getTipo(val_peek(2).sval);
                    }
                    else {
                        /*termino es un terceto*/
                        tipoTermino = generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).getTipo();
                    }
                    if (TS.estaToken(val_peek(0).sval)){
                        /*factor es un token*/
                        tipoFactor = TS.getTipo(val_peek(0).sval);
                    }
                    else{
                        /*factor es un terceto*/
                        tipoFactor = generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).getTipo();
                    }
                    if(tipoFactor != tipoTermino){
                        System.out.println("Incompatibilidad de tipos en multiplicación, en linea " + lexico.getContadorLinea());
                    }

       			 yyval.sval = generador.addTerceto("*", val_peek(2).sval, val_peek(0).sval);
       			 generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoFactor);
       			 System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());
   		 }
break;
case 59:
//#line 297 "gramatica.y"
{
                    /*verificación de tipos*/
                    Integer tipoTermino, tipoFactor;
                    TablaSimbolos TS = lexico.getTablaSimbolos();
                    if (TS.estaToken(val_peek(2).sval)){
                        /*termino es un token*/
                        tipoTermino = TS.getTipo(val_peek(2).sval);
                    }
                    else {
                        /*termino es un terceto*/
                        tipoTermino = generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).getTipo();
                    }
                    if (TS.estaToken(val_peek(0).sval)){
                        /*factor es un token*/
                        tipoFactor = TS.getTipo(val_peek(0).sval);
                    }
                    else{
                        /*factor es un terceto*/
                        tipoFactor = generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).getTipo();
                    }
                    if(tipoFactor != tipoTermino){
                        System.out.println("Incompatibilidad de tipos en división, en linea " + lexico.getContadorLinea());
                    }
        	yyval.sval = generador.addTerceto("/", val_peek(2).sval, val_peek(0).sval);
        	generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoFactor);
        	System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());
		}
break;
case 60:
//#line 324 "gramatica.y"
{
	    	yyval.sval = val_peek(0).sval;
		}
break;
case 61:
//#line 328 "gramatica.y"
{System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 62:
//#line 329 "gramatica.y"
{System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 63:
//#line 332 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
            System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());
        }
break;
case 64:
//#line 336 "gramatica.y"
{System.out.println("Se detecto: Invocación a función " + "en linea: " + lexico.getContadorLinea());}
break;
case 65:
//#line 337 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 66:
//#line 338 "gramatica.y"
{
		 	yyval.sval = truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea());
            lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 67:
//#line 342 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 68:
//#line 345 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 69:
//#line 348 "gramatica.y"
{
        	yyval.sval = truncarFueraRango("-"+val_peek(0).sval, lexico.getContadorLinea());
        	lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 70:
//#line 355 "gramatica.y"
{
    String token = val_peek(3).sval+'{'+val_peek(1).sval+'}';
    TablaSimbolos TS = lexico.getTablaSimbolos();
    if (TS.estaToken(token)){
        yyval.sval = token;
    }
    else {
        System.out.println("Variable inexistenet o Intento de acceso a una posición de triple inexistente en linea " + lexico.getContadorLinea());
        yyval.sval = token;
        /*en este punto, los tercetos se generan igual,
        aunque se intente acceder a un valor invalido del tercerto.
        El generador de assembler deberia volver a verificar si la constante es 1, 2 o 3
        y solo generar codigo en ese caso, de lo contrario lanzar error.
        */
    }
}
break;
case 71:
//#line 378 "gramatica.y"
{
            yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
            
            Integer t_exp_arit;

            if (val_peek(0).sval.matches("\\[T\\d+\\]")) {
            	int pos = Integer.parseInt(val_peek(0).sval.replaceAll("\\D", ""));
    			t_exp_arit = generador.getTerceto(pos).getTipo();
			} else {
    			t_exp_arit = lexico.getTablaSimbolos().getTipo(val_peek(0).sval);;
			}

            
            Integer t_id = lexico.getTablaSimbolos().getTipo(val_peek(2).sval);

            if(t_id==null){
            	System.out.println("Error: variable "+val_peek(2).sval+" no declarada. Linea: "+lexico.getContadorLinea());
            	/*generador.getTerceto(Integer.parseInt($$.sval)).setTipo(t_id); ponerle tipo al terceto sería raro, porque si tiene un error no se sabe*/
            }else if(t_id!=t_exp_arit){
            	System.out.println("Error: se intenta asignar un "+t_exp_arit+" a una variable "+t_id+" en la linea "+lexico.getContadorLinea());
            	/*generador.getTerceto(Integer.parseInt($$.sval)).setTipo(t_id); ponerle tipo al terceto sería raro, porque si tiene un error no se sabe*/
            }else{
				generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(t_id);
            }
        }
break;
case 72:
//#line 404 "gramatica.y"
{
        yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);

        Integer t_exp_arit;
        if (val_peek(0).sval.matches("\\[T\\d+\\]")) {
        	int pos = Integer.parseInt(val_peek(0).sval.replaceAll("\\D", ""));
    		t_exp_arit = generador.getTerceto(pos).getTipo();
		} else {
    		t_exp_arit = lexico.getTablaSimbolos().getTipo(val_peek(0).sval);;
		}
        
        Integer t_triple = lexico.getTablaSimbolos().getTipo(val_peek(2).sval);

        if(t_triple==null){
            	System.out.println("Error: variable "+val_peek(2).sval+" no existente. Linea: "+lexico.getContadorLinea());
            	/*generador.getTerceto(Integer.parseInt($$.sval)).setTipo(t_id); ponerle tipo al terceto sería raro, porque si tiene un error no se sabe*/
        }else if(t_triple!=t_exp_arit){
            System.out.println("Error: se intenta asignar un "+t_exp_arit+" a una variable "+t_triple+" en la linea "+lexico.getContadorLinea());
            /*generador.getTerceto(Integer.parseInt($$.sval)).setTipo(t_id); ponerle tipo al terceto sería raro, porque si tiene un error no se sabe*/
        }else{
			generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(t_triple);
        }
    }
break;
case 73:
//#line 430 "gramatica.y"
{
    			String etq = val_peek(1).sval+"@";
    			if(!generador.isEtiqueta(etq)){
    				yyval.sval = generador.addTerceto("ET"+val_peek(1).sval+"@",null,null);

    				generador.putEtiqueta(etq, Integer.parseInt(yyval.sval.replaceAll("\\D", "")));

    				generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
    			}else{
    				System.out.println("Error: la etiqueta "+etq+" ya existe. Linea: "+lexico.getContadorLinea());
    			}
    		}
break;
case 74:
//#line 444 "gramatica.y"
{
			String etq = val_peek(1).sval+"@";
			if(generador.isEtiqueta(etq)){
				yyval.sval = generador.addTerceto("BI", etq, generador.posicionEtiqueta(etq));	
			}else{
				yyval.sval = generador.addTerceto("BI", etq, null);

				generador.addGoto(Integer.parseInt(yyval.sval.replaceAll("\\D", "")), etq);/*.setTipo(TIPO_SALTO); ¿Le agregamos un tipo a los saltos?*/
			}
        	
       }
break;
case 75:
//#line 455 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
case 76:
//#line 458 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 77:
//#line 462 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 78:
//#line 466 "gramatica.y"
{System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 467 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 80:
//#line 474 "gramatica.y"
{
							int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
							generador.eliminarPila();
							Terceto t = generador.getTerceto(pos);
							String label = "ET"+generador.getSizeTercetos();

							t.setTercerParametro(label);
							yyval.sval=generador.addTerceto(label, null, null);
			}
break;
case 81:
//#line 484 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 82:
//#line 485 "gramatica.y"
{
		System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());
		int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
		generador.eliminarPila();
		Terceto t = generador.getTerceto(pos);
		String label = "ET"+generador.getSizeTercetos();
		t.setTercerParametro(label);
		
		yyval.sval=generador.addTerceto(label, null, null);
	 }
break;
case 83:
//#line 495 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 84:
//#line 496 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 497 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 86:
//#line 500 "gramatica.y"
{
							yyval.sval = generador.addTerceto("BF", val_peek(1).sval, null);
							generador.agregarPila(yyval.sval);
				}
break;
case 87:
//#line 506 "gramatica.y"
{
							int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
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
case 88:
//#line 522 "gramatica.y"
{

        		yyval.sval = generador.addTerceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);
        		System.out.println("Se detecto: comparación");

        		int pos;
        		Integer t_primer_exp_arit;

        		if (val_peek(3).sval.matches("\\[T\\d+\\]")) {
        			pos = Integer.parseInt(val_peek(3).sval.replaceAll("\\D", ""));
    				t_primer_exp_arit = generador.getTerceto(pos).getTipo();
				} else {
		    		t_primer_exp_arit = lexico.getTablaSimbolos().getTipo(val_peek(3).sval);;
				}

        		Integer t_segunda_exp_arit;
        		if (val_peek(1).sval.matches("\\[T\\d+\\]")) {
        			pos = Integer.parseInt(val_peek(1).sval.replaceAll("\\D", ""));
    				t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
				} else {
		    		t_segunda_exp_arit = lexico.getTablaSimbolos().getTipo(val_peek(1).sval);;
				}

        		if(t_primer_exp_arit != t_segunda_exp_arit){
        			System.out.println("Error: comparación entre dos expresiones de tipos diferentes. Linea: "+lexico.getContadorLinea());
        		}
        	}
break;
case 89:
//#line 550 "gramatica.y"
{

	        String[] lista1 = val_peek(6).sval.split(",");
	        String[] lista2 = val_peek(2).sval.split(",");
	        if (lista1.length != lista2.length){
	            System.out.println("Los tamaños de las listas en la condicion no coinciden en linea: " + lexico.getContadorLinea());
	        }else{
	        	yyval.sval = generador.addTerceto(val_peek(4).sval, lista1[0], lista2[0]);

	        	boolean error_comparacion = false;
	        	int pos;
	            Integer t_primer_exp_arit;

	        	if (lista1[0].matches("\\[T\\d+\\]")) {
	        		pos = Integer.parseInt(lista1[0].replaceAll("\\D", ""));
	    			t_primer_exp_arit = generador.getTerceto(pos).getTipo();
				} else {
			    	t_primer_exp_arit = lexico.getTablaSimbolos().getTipo(lista1[0]);;
				}

	        	Integer t_segunda_exp_arit;
	        	if (lista2[0].matches("\\[T\\d+\\]")) {
	        		pos = Integer.parseInt(lista2[0].replaceAll("\\D", ""));
	    			t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
				} else {
			    	t_segunda_exp_arit = lexico.getTablaSimbolos().getTipo(lista2[0]);;
				}

				if(t_primer_exp_arit!=t_segunda_exp_arit) error_comparacion=true;


	            if(lista1.length!=1){
	                String auxTerceto;

	                for (int i = 1; i<lista1.length; i++){
	                    auxTerceto= generador.addTerceto(val_peek(4).sval, lista1[i], lista2[i]);
	                    yyval.sval =generador.addTerceto("AND", yyval.sval, auxTerceto);
	                    
	                    if (lista1[i].matches("\\[T\\d+\\]")) {
	        				pos = Integer.parseInt(lista1[i].replaceAll("\\D", ""));
	    					t_primer_exp_arit = generador.getTerceto(pos).getTipo();
						} else {
			    			t_primer_exp_arit = lexico.getTablaSimbolos().getTipo(lista1[i]);;
						}

			        	if (lista2[i].matches("\\[T\\d+\\]")) {
			        		pos = Integer.parseInt(lista2[i].replaceAll("\\D", ""));
			    			t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
						} else {
					    	t_segunda_exp_arit = lexico.getTablaSimbolos().getTipo(lista2[i]);;
						}

						if(t_primer_exp_arit!=t_segunda_exp_arit) error_comparacion=true;
	                }

	                if(error_comparacion){
	                	System.out.println("Error: comparación entre dos expresiones de tipos diferentes. Linea: "+lexico.getContadorLinea());
	                }

	                /*$$.sval = generador.addTerceto("BF", $$.sval, null);*/
	                /*generador.addPila($$.sval);*/
	            }

	        }
		    System.out.println("Se detecto: comparación múltiple");
		  }
break;
case 90:
//#line 617 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 91:
//#line 618 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 92:
//#line 619 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 93:
//#line 620 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 94:
//#line 621 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 95:
//#line 622 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 96:
//#line 624 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 97:
//#line 625 "gramatica.y"
{System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
break;
case 98:
//#line 629 "gramatica.y"
{yyval.sval = ">=";}
break;
case 99:
//#line 630 "gramatica.y"
{yyval.sval = "<=";}
break;
case 100:
//#line 631 "gramatica.y"
{yyval.sval = "!=";}
break;
case 101:
//#line 632 "gramatica.y"
{yyval.sval = "=";}
break;
case 102:
//#line 633 "gramatica.y"
{yyval.sval = ">";}
break;
case 103:
//#line 634 "gramatica.y"
{yyval.sval = "<";}
break;
case 104:
//#line 637 "gramatica.y"
{
					yyval.sval = generador.addTerceto("BT", val_peek(0).sval, generador.obtenerElementoPila());
    				generador.eliminarPila();
				}
break;
case 105:
//#line 642 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 106:
//#line 643 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 107:
//#line 647 "gramatica.y"
{
				    yyval.sval = generador.addTerceto("ET" + generador.getSizeTercetos(), null, null);
				    generador.agregarPila('E' + yyval.sval);
				}
break;
case 108:
//#line 656 "gramatica.y"
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
//#line 1617 "Parser.java"
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
