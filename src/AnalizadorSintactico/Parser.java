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
   11,   12,   12,   12,   12,   12,   12,   12,    2,    2,
    2,    2,   14,   15,   19,   19,   18,   18,   18,   20,
   20,   20,   20,   20,   17,   17,   17,   13,    5,    5,
    5,    5,   21,   21,   21,   21,   21,   23,   23,   22,
   22,   22,   22,   22,   24,   24,   24,   24,   24,   24,
   24,   25,    6,    6,   10,    8,    8,    9,    9,    9,
    4,    4,    4,    4,    4,    4,   26,   27,   28,   28,
   28,   28,   28,   28,   28,   28,   28,   28,   29,   29,
   29,   29,   29,   29,    7,    7,    7,   30,   16,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    1,    3,    4,
    3,    2,    3,    1,    2,    2,    1,    1,    1,    1,
    1,    1,    2,    2,    1,    3,    1,    1,    1,    7,
    6,    5,    6,    6,    5,    4,    4,    5,    4,    5,
    7,    3,    3,    3,    4,    4,    1,    1,    3,    3,
    3,    1,    4,    4,    1,    1,    1,    1,    1,    1,
    2,    4,    3,    3,    2,    3,    3,    4,    4,    3,
    3,    2,    5,    4,    2,    3,    3,    1,    5,    9,
    8,    8,    8,    8,    4,    4,    8,    5,    1,    1,
    1,    1,    1,    1,    4,    3,    3,    1,    6,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,  108,    0,    0,    0,    0,    0,
   38,   39,   37,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   18,   30,   32,   31,   29,    0,    0,    0,
    0,    0,    0,   35,    0,    0,    0,   75,    0,    0,
   68,   69,   70,    0,    0,   66,    0,    0,   62,   67,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    4,
    6,    0,    0,    0,    0,   88,    0,   85,    0,    0,
    0,    0,    0,    0,   52,    0,    0,    0,    0,    0,
    0,    0,   71,  100,  101,   99,    0,    0,  102,  103,
  104,    0,    0,    0,   87,    0,    0,   80,    0,    0,
   77,   76,    3,    8,   10,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   27,   28,    0,   81,    0,
   86,  106,    0,  107,    2,    0,    0,   49,   72,   36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   60,    0,   61,    1,   78,   79,    0,    0,    0,
    0,   47,    0,   46,   22,    0,   19,    0,    0,   21,
   26,    0,  105,    0,   50,    0,    0,    0,    0,    0,
   55,   56,   95,   63,   64,    0,    0,   42,    0,    0,
    0,   45,   23,   20,   83,    0,    0,   98,    0,    0,
   89,    0,  109,   44,    0,   43,   41,    0,   51,    0,
    0,    0,    0,    0,   40,   48,    0,    0,    0,    0,
    0,   97,    0,   92,   91,   93,   90,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   46,   19,   20,   21,   22,   23,
   69,  113,  110,   24,   25,   26,   27,   28,   63,   29,
  131,   48,   82,   49,   50,   31,   70,   51,   92,   32,
};
final static short yysindex[] = {                      -248,
  -82,  520,    0,   84,    0,   89,  520,  -21, -250, -205,
    0,    0,    0,  449,  -12,   35,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -240, -183, -159,
 -177, -245,  466,    0,  299,   69, -161,    0,   88,  -39,
    0,    0,    0,   96, -142,    0,  498,   -1,    0,    0,
 -126,  483,   76,   95,  100,   97,  110,  114,  123,    0,
    0,  -33,   88,  503,  299,    0,  503,    0, -194,  -86,
   89,  -40,  128,   23,    0,   99,    7,   71,  -57,  299,
  498,   67,    0,    0,    0,    0,  -43,  272,    0,    0,
    0,  299,  275,  284,    0,  135,  152,    0,   28, -100,
    0,    0,    0,    0,    0,  162, -100,  163, -109,  -67,
   23,  146,  318,  -63,  147,    0,    0,  -46,    0,  -56,
    0,    0,   89,    0,    0,  299,   31,    0,    0,    0,
   23,  223,  299,  299,  366,  157,   -1,  170,   -1,   85,
  173,    0,  174,    0,    0,    0,    0,  178,   56,  -23,
  299,    0,  -26,    0,    0,  -20,    0,  189,  -14,    0,
    0,  -13,    0,   90,    0,  149,  218,  102,   23,  225,
    0,    0,    0,    0,    0,    2,  229,    0,  -32,  232,
  109,    0,    0,    0,    0,  241,  299,    0,  106,  299,
    0,  299,    0,    0,  249,    0,    0,  236,    0,   68,
  299,   81,  112,  119,    0,    0,  257,  133,  266,  274,
  282,    0,  287,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  330,  370,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  141,  -30,
    0,    0,    0,    0,    0,    0,    0,   -5,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  415,  432,    0,
    0,    0,  158,    0,    0,    0,    0,    0,  175,    0,
    0,    0,    0,  195,    0,    0,    0,    0,    0,    0,
  134,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  220,  395,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  248,    0,    0,    0,    0,    0,   20,    0,   45,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  537,    0,    0,
    0,  245,    0,    0,    0,    0,    0,  107,  403,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  124,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   19,  -10,   10,    0,  565,    0,    0,    0,    0,    0,
  -24,    0,  -64,  263,  278,  279,    0,   -9,  351,    0,
  479,  -28,  -74,   44,  567,    0,  297,  -38,  -65,    0,
};
final static int YYTABLESIZE=814;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
   36,   45,  114,   58,   45,  132,  107,   72,  196,    1,
   65,   65,   65,   65,   65,  133,   65,   34,   53,   36,
   33,    2,   58,   59,   67,   52,   76,   54,   65,   65,
   65,   65,  122,  124,   62,   57,   71,   57,   57,   57,
   93,   58,   59,   38,  153,   94,   60,  128,  159,   87,
   55,   88,   56,   57,   57,   57,   57,  118,  137,  139,
   53,   59,   53,   53,   53,   87,  167,   88,  147,  170,
   87,  165,   88,   87,   66,   88,  112,  119,   53,   53,
   53,   53,  109,   37,  163,   54,   64,   54,   54,   54,
  148,   66,   67,   61,   68,  162,  178,  150,   58,   65,
  189,   78,   37,   54,   54,   54,   54,  135,  207,   75,
  134,  134,  200,   45,  202,  203,   98,  204,   59,   83,
   45,  209,  158,   36,  134,  173,  208,   87,   44,   88,
  186,   79,   87,   45,   88,   80,  142,  144,  126,  179,
   45,   95,  191,   45,   87,  201,   88,   38,    4,  198,
   45,   87,  210,   88,  100,  134,    5,    6,  101,  211,
  102,  152,  134,    8,    9,   96,  108,   10,  103,   11,
   12,   13,  104,  213,   58,    4,  134,   58,   11,   12,
   13,  105,   94,    5,    6,  121,  125,    7,  187,  188,
    8,    9,  146,  145,   10,  129,   11,   12,   13,   34,
  130,  149,  151,  154,  155,  161,   37,  160,   91,   89,
   90,   34,  136,   67,   40,  171,   33,   40,   41,   42,
   43,   41,   42,   43,  106,  195,   65,   65,  172,   65,
   65,  174,  175,   82,  180,   65,   65,   65,   35,  176,
   65,  123,   65,   65,  182,   65,   65,  183,   65,   65,
   65,   57,   57,   73,   57,   57,  184,  190,  185,  193,
   57,   57,   57,  166,  192,   57,  134,   57,   57,  194,
   57,   57,  197,   57,   57,   57,   53,   53,   74,   53,
   53,  199,   91,   89,   90,   53,   53,   53,   58,  205,
   53,   58,   53,   53,  206,   53,   53,  212,   53,   53,
   53,   54,   54,   84,   54,   54,  214,   58,   58,   58,
   54,   54,   54,  177,  215,   54,   45,   54,   54,   45,
   54,   54,  216,   54,   54,   54,   40,  217,   45,  115,
   41,   42,   43,   40,   11,   12,   13,   41,   42,   43,
   97,   34,   35,   45,  116,  117,   40,   11,   12,   13,
   41,   42,   43,   40,   39,    0,   40,   41,   42,   43,
   41,   42,   43,   40,   96,  120,    0,   41,   42,   43,
    0,    0,   96,   96,   96,    0,    0,   96,    0,   96,
   96,   94,   96,   96,    0,   96,   96,   96,    0,   94,
   94,   94,    0,    0,   94,    0,   94,   94,   34,   94,
   94,    0,   94,   94,   94,   84,   34,   34,   85,   86,
    0,   34,    0,   34,   34,   33,   34,   34,    0,   34,
   34,   34,    0,   33,   33,   91,   89,   90,   33,    0,
   33,   33,   82,   33,   33,    0,   33,   33,   33,    0,
   82,   82,    0,   59,    0,   82,   59,   82,   82,    0,
   82,   82,   73,   82,   82,   82,    0,    0,    0,    0,
   73,   73,   59,   59,   59,   73,    0,   73,   73,    0,
   73,   73,    0,   73,   73,   73,    0,   74,    0,   84,
    0,    0,   85,   86,   47,   74,   74,    0,    0,    0,
   74,    0,   74,   74,    0,   74,   74,    0,   74,   74,
   74,    0,   84,    0,   58,    0,    0,   58,   58,    0,
   84,   84,    0,   74,   77,   84,    0,   84,   84,    0,
   84,   84,   81,   84,   84,   84,    0,  138,    0,   40,
  141,   99,   40,   41,   42,   43,   41,   42,   43,  143,
   87,   40,   88,  111,    0,   41,   42,   43,    0,   47,
   47,    0,    0,    0,  127,    0,   40,   91,   89,   90,
   41,   42,   43,    0,    0,   18,   18,   30,   30,    0,
  140,   18,    0,   30,    0,  156,    0,    0,   18,    0,
   30,    0,    0,    5,    6,    0,    0,    5,  157,    0,
    8,    0,    0,  108,   10,    5,    5,   18,    0,   30,
    5,   47,    5,    5,  164,    5,    5,    0,    5,    5,
    5,  168,  169,    0,    0,    0,   18,    0,   30,    0,
    0,    0,   84,    0,    0,   85,   86,    7,   18,  181,
   30,   18,    0,   30,    0,    7,    7,    0,    0,    0,
    7,    0,    7,    7,    0,    7,    7,    0,    7,    7,
    7,    0,   24,    0,    0,    0,    0,    0,    0,   59,
   24,   24,   59,   59,    0,   24,    0,   24,    0,    0,
   24,   24,    9,   18,    0,   30,    0,   18,    0,   30,
    9,    9,    0,    0,    0,    9,    0,    9,    9,   11,
    9,    9,    0,    9,    9,    9,    0,   11,   11,    0,
    0,    0,   11,    0,   11,   11,    4,   11,   11,    0,
   11,   11,   11,    0,    5,    6,    0,    0,    0,   57,
    0,    8,    9,    4,    0,   10,    0,   11,   12,   13,
    0,    5,    6,    0,    0,    0,   73,    0,    8,    9,
    4,    0,   10,    0,   11,   12,   13,    0,    5,    6,
    0,    0,    0,   96,   84,    8,    9,   85,   86,   10,
    4,   11,   12,   13,    0,    0,    0,    0,    5,    6,
    0,    0,    0,    0,    0,    8,    9,    4,  108,   10,
    0,   11,   12,   13,    0,    5,    6,    0,    0,    0,
    0,    0,    8,    9,   25,    0,   10,    0,   11,   12,
   13,    0,   25,   25,    0,    0,    0,   25,    0,   25,
    0,    0,   25,   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   45,   67,   14,   45,   80,   40,   32,   41,  258,
   41,   42,   43,   44,   45,   81,   47,  258,   40,   40,
    2,  270,   33,   14,  270,    7,   36,  278,   59,   60,
   61,   62,   71,   72,  275,   41,  282,   43,   44,   45,
   42,   52,   33,   64,  109,   47,   59,   41,  113,   43,
  256,   45,  258,   59,   60,   61,   62,   67,   87,   88,
   41,   52,   43,   44,   45,   43,  132,   45,   41,  135,
   43,   41,   45,   43,  269,   45,   67,  272,   59,   60,
   61,   62,   64,  123,  123,   41,  270,   43,   44,   45,
  100,  269,  270,   59,  272,  120,   41,  107,  109,  259,
  166,  263,  123,   59,   60,   61,   62,   41,   41,   41,
   44,   44,  187,   45,  189,  190,   41,  192,  109,  262,
   45,   41,  113,   40,   44,   41,  201,   43,   40,   45,
   41,   44,   43,   45,   45,   40,   93,   94,   40,  149,
   45,  268,   41,   45,   43,   40,   45,   64,  258,   41,
   45,   43,   41,   45,   60,   44,  266,  267,   59,   41,
   64,  271,   44,  273,  274,   59,  276,  277,   59,  279,
  280,  281,   59,   41,   41,  258,   44,   44,  279,  280,
  281,   59,   59,  266,  267,  272,   59,  270,   40,   41,
  273,  274,   41,   59,  277,  125,  279,  280,  281,   59,
  258,   40,   40,  271,   59,   59,  123,  271,   60,   61,
   62,  258,  256,  270,  258,   59,   59,  258,  262,  263,
  264,  262,  263,  264,  258,  258,  257,  258,   59,  260,
  261,   59,   59,   59,  258,  266,  267,  268,  259,   62,
  271,  282,  273,  274,  271,  276,  277,   59,  279,  280,
  281,  257,  258,   59,  260,  261,  271,   40,  272,  258,
  266,  267,  268,   41,   40,  271,   44,  273,  274,   41,
  276,  277,   41,  279,  280,  281,  257,  258,   59,  260,
  261,   41,   60,   61,   62,  266,  267,  268,   41,   41,
  271,   44,  273,  274,   59,  276,  277,   41,  279,  280,
  281,  257,  258,   59,  260,  261,   41,   60,   61,   62,
  266,  267,  268,  258,   41,  271,   45,  273,  274,   45,
  276,  277,   41,  279,  280,  281,  258,   41,   45,   67,
  262,  263,  264,  258,  279,  280,  281,  262,  263,  264,
  265,  258,  259,   45,   67,   67,  258,  279,  280,  281,
  262,  263,  264,  258,    4,   -1,  258,  262,  263,  264,
  262,  263,  264,  258,  258,   69,   -1,  262,  263,  264,
   -1,   -1,  266,  267,  268,   -1,   -1,  271,   -1,  273,
  274,  258,  276,  277,   -1,  279,  280,  281,   -1,  266,
  267,  268,   -1,   -1,  271,   -1,  273,  274,  258,  276,
  277,   -1,  279,  280,  281,  257,  266,  267,  260,  261,
   -1,  271,   -1,  273,  274,  258,  276,  277,   -1,  279,
  280,  281,   -1,  266,  267,   60,   61,   62,  271,   -1,
  273,  274,  258,  276,  277,   -1,  279,  280,  281,   -1,
  266,  267,   -1,   41,   -1,  271,   44,  273,  274,   -1,
  276,  277,  258,  279,  280,  281,   -1,   -1,   -1,   -1,
  266,  267,   60,   61,   62,  271,   -1,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,   -1,  258,   -1,  257,
   -1,   -1,  260,  261,    6,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,   -1,  258,   -1,  257,   -1,   -1,  260,  261,   -1,
  266,  267,   -1,   35,   36,  271,   -1,  273,  274,   -1,
  276,  277,   44,  279,  280,  281,   -1,  256,   -1,  258,
  256,   53,  258,  262,  263,  264,  262,  263,  264,  256,
   43,  258,   45,   65,   -1,  262,  263,  264,   -1,   71,
   72,   -1,   -1,   -1,   76,   -1,  258,   60,   61,   62,
  262,  263,  264,   -1,   -1,    1,    2,    1,    2,   -1,
   92,    7,   -1,    7,   -1,  258,   -1,   -1,   14,   -1,
   14,   -1,   -1,  266,  267,   -1,   -1,  258,  271,   -1,
  273,   -1,   -1,  276,  277,  266,  267,   33,   -1,   33,
  271,  123,  273,  274,  126,  276,  277,   -1,  279,  280,
  281,  133,  134,   -1,   -1,   -1,   52,   -1,   52,   -1,
   -1,   -1,  257,   -1,   -1,  260,  261,  258,   64,  151,
   64,   67,   -1,   67,   -1,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  266,  267,  260,  261,   -1,  271,   -1,  273,   -1,   -1,
  276,  277,  258,  109,   -1,  109,   -1,  113,   -1,  113,
  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,  258,
  276,  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,
   -1,   -1,  271,   -1,  273,  274,  258,  276,  277,   -1,
  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,  271,
   -1,  273,  274,  258,   -1,  277,   -1,  279,  280,  281,
   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,
  258,   -1,  277,   -1,  279,  280,  281,   -1,  266,  267,
   -1,   -1,   -1,  271,  257,  273,  274,  260,  261,  277,
  258,  279,  280,  281,   -1,   -1,   -1,   -1,  266,  267,
   -1,   -1,   -1,   -1,   -1,  273,  274,  258,  276,  277,
   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,
   -1,   -1,  273,  274,  258,   -1,  277,   -1,  279,  280,
  281,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,
   -1,   -1,  276,  277,
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
"sentencias_ejecutables : declarvar ';'",
"sentencias_ejecutables : declar_compuesto",
"sentencias_ejecutables : def_triple",
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
"encabezadoFun : tipo FUN ID '(' tipo ID ')'",
"encabezadoFun : tipo FUN '(' tipo ID ')'",
"encabezadoFun : tipo FUN ID '(' ')'",
"encabezadoFun : tipo FUN ID '(' tipo ')'",
"encabezadoFun : tipo FUN ID '(' ID ')'",
"declaracionFun : encabezadoFun BEGIN conjunto_sentencias retorno END",
"declaracionFun : encabezadoFun BEGIN retorno END",
"declaracionFun : encabezadoFun BEGIN conjunto_sentencias END",
"retorno : RET '(' exp_arit ')' ';'",
"invocacion_fun : ID '(' exp_arit ')'",
"invocacion_fun : ID '(' tipo exp_arit ')'",
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

//#line 1197 "gramatica.y"

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
    public final static int  TIPO_SALTO = 9;
    public final static int  TIPO_FUNCION = 10;

    public final static int  TIPO_DESCONOCIDO = 50;
    public final static int TIPO_TRIPLE_UNSIGNED = 5;
    public final static int TIPO_TRIPLE_SINGLE = 6;
    public final static int TIPO_TRIPLE_OCTAL = 7;
    public final static int TIPO_RETORNO = 11;

    public final static int NOMBRE_VAR = 101;
    public final static int NOMBRE_FUN = 102;
    public final static int NOMBRE_TIPO = 103;
    public final static int NOMBRE_PARAMETRO = 104;
    public final static int NOMBRE_ETIQUETA = 105;
    public final static int NOMBRE_PROGRAMA = 106;

    public ArrayList<String> tiposUsuario;

public int yylex() throws IOException {
    int token = lexico.yylex();
    this.yylval = lexico.getYylval();
    return token;
}

public void yyerror(String mensaje) {
    System.out.println("Error: " + mensaje);
}

public Parser(String archivo, String salida) throws IOException {
    lexico = Lexico.getInstance(archivo);
    generador = Generador.getInstance();
    generador.setSalida(salida);
    this.tiposUsuario = new ArrayList<>();

}

private String truncarFueraRango(String cte, int linea) throws NumberFormatException{
   	// Reemplazar 's' por 'e' para convertir a notación científica y parsear el float
       cte = cte.replace('s', 'e');
       Float result = Float.parseFloat(cte);

       if(result>0.0f) {
	        if (infPositivo > result) {
	        	System.out.println("\u001B[33m"+"Warning: constante fuera de rango. Linea: "+ linea+"\u001B[0m");
	            String nuevaCte = infPositivo.toString();
	            return nuevaCte;

	        }else if(supPositivo < result) {
	        	System.out.println("\u001B[33m"+"Warning: constante fuera de rango. Linea: "+ linea+"\u001B[0m");
	            String nuevaCte = supPositivo.toString();
	            return nuevaCte;
	        }
       }else {
       	if(infNegativo > result) {
       		System.out.println("\u001B[33m"+"Warning: constante fuera de rango. Linea: "+ linea+"\u001B[0m");
	            String nuevaCte = infNegativo.toString();
	            return nuevaCte;
	        }else if(supNegativo < result) {
	        	System.out.println("\u001B[33m"+"Warning: constante fuera de rango. Linea: "+ linea+"\u001B[0m");
	            String nuevaCte = supNegativo.toString();
	            return nuevaCte;
	        }
       }

       return cte;
   }

private String mappeoTipo(Integer tipo){
	if(tipo!=null) {
	
		switch(tipo){
			case T_UNSIGNED:
				return "UNSIGNED";
			case T_SINGLE:
				return "SINGLE";
			case T_OCTAL:
				return "OCTAL";
			case TIPO_MULTILINEA:
				return "MULTILINEA";
			case TIPO_TRIPLE_UNSIGNED:
				return "TRIPLE_UNSIGNED";
			case TIPO_TRIPLE_SINGLE:
				return "TRIPLE_SINGLE";
			case TIPO_TRIPLE_OCTAL:
				return "TRIPLE_OCTAL";
			case TIPO_ETIQUETA:
				return "ETIQUETA";
			case TIPO_SALTO:
				return "SALTO";
			case TIPO_FUNCION:
				return "FUNCION";
			case 11:
				return "AUXILIAR";
		}
	}
	
	return "";
}

public static void main(String[] args) throws IOException {
    if(args.length == 2) {
          String archivo = args[0];
          String salida = args[1];
          try {
            Parser parser = new Parser(archivo, salida);
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


//#line 673 "Parser.java"
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
{
        lexico.getTablaSimbolos().agregarUso(val_peek(4).sval, NOMBRE_PROGRAMA);
        System.out.println("Se detecto: Programa");}
break;
case 2:
//#line 22 "gramatica.y"
{System.err.println("Error: Falta nombre de programa"); generador.setError();}
break;
case 3:
//#line 23 "gramatica.y"
{System.err.println("Error: Falta delimitador de programa ");generador.setError();}
break;
case 5:
//#line 27 "gramatica.y"
{System.err.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 7:
//#line 29 "gramatica.y"
{System.err.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea());generador.setError();}
break;
case 9:
//#line 31 "gramatica.y"
{System.err.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 11:
//#line 33 "gramatica.y"
{System.err.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 12:
//#line 39 "gramatica.y"
{System.out.println("Se detecto: Sentencia if ");}
break;
case 13:
//#line 40 "gramatica.y"
{System.out.println("Se detecto: Invocacion a funcion " + " en linea: " + lexico.getContadorLinea());}
break;
case 14:
//#line 41 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + " en linea: " + lexico.getContadorLinea());}
break;
case 15:
//#line 42 "gramatica.y"
{System.out.println("Se detecto: Ciclo repeat until ");}
break;
case 16:
//#line 43 "gramatica.y"
{System.out.println("Se detecto: Sentencia GOTO " + " en linea: " + lexico.getContadorLinea());}
break;
case 17:
//#line 44 "gramatica.y"
{System.out.println("Se detecto: Salida " + " en linea: " + lexico.getContadorLinea());}
break;
case 18:
//#line 45 "gramatica.y"
{System.out.println("Se detecto: Etiqueta " + " en linea: " + lexico.getContadorLinea());}
break;
case 20:
//#line 49 "gramatica.y"
{/*verificar el tipo de retorno*/
                                                                                            TablaSimbolos TS = lexico.getTablaSimbolos();
                                                                                            String lexemaFun = TS.getUltimoAmbito(); /*obtengo el lexema de la funcion*/
                                                                                            Integer tipoFun = TS.getTipo(lexemaFun); /*obtengo el tipo de la funcion*/
                                                                                            Integer tipoRetorno = generador.getTerceto(Integer.parseInt(val_peek(1).sval.replaceAll("\\D", ""))).getTipo();
                                                                                            if (tipoFun != tipoRetorno){
                                                                                                System.err.println("Error: tipo de retorno invalido en funcion: " + lexemaFun);
                                                                                                generador.setError();
                                                                                            }}
break;
case 21:
//#line 58 "gramatica.y"
{/*verificar el tipo de retorno*/
                                                                     TablaSimbolos TS = lexico.getTablaSimbolos();
                                                                     String lexemaFun = TS.getUltimoAmbito(); /*obtengo el lexema de la funcion*/
                                                                     Integer tipoFun = TS.getTipo(lexemaFun); /*obtengo el tipo de la funcion*/
                                                                     Integer tipoRetorno = generador.getTerceto(Integer.parseInt(val_peek(1).sval.replaceAll("\\D", ""))).getTipo();
                                                                     if (tipoFun != tipoRetorno){
                                                                         System.err.println("Error: tipo de retorno invalido en funcion: " + lexemaFun);
                                                                         generador.setError();
                                                                     }}
break;
case 24:
//#line 71 "gramatica.y"
{System.err.println("Error: Falta ;"); generador.setError();}
break;
case 25:
//#line 72 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 29:
//#line 83 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion ");}
break;
case 30:
//#line 84 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 31:
//#line 85 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 32:
//#line 86 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 33:
//#line 89 "gramatica.y"
{
        String[] lista = val_peek(0).sval.split(",");
        TablaSimbolos TS = lexico.getTablaSimbolos();
        Integer tipo = Integer.parseInt(val_peek(1).sval);
        String ambito = TS.getAmbitos();
        for (String var : lista){
            TS.editarTipo(var, tipo);
            TS.agregarUso(var, NOMBRE_VAR);
            if (TS.estaToken(var + ambito)){
                System.err.println("Error: ya existe la variable " + var + " en el ambito " + ambito + ". Linea " + lexico.getContadorLinea());
                generador.setError();
            }
            TS.editarLexema(var, var + ambito);
        }
    }
break;
case 34:
//#line 106 "gramatica.y"
{
        /*hay que ver que el ID sea alcanzable por el ambito actual.*/
        TablaSimbolos TS = lexico.getTablaSimbolos();
        String id = TS.buscarVariable(val_peek(1).sval);
        if (id == null){
            System.err.println("Error: variable no declarad. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else{
            if (tiposUsuario.contains(id)){
                Integer t = TS.getTipo(id);
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
                String ambito = TS.getAmbitos();
                for (String var : lista){
                    for(int i=1; i<=3; i++){
                        String token = var+'{'+i+'}';
                        ArrayList<Integer> atributos = new ArrayList<Integer>();
                        atributos.add(258);
                        atributos.add(t);
                        atributos.add(NOMBRE_VAR);
                        if (TS.estaToken(token + ambito)){
                           System.err.println("Error: ya existe la variable " + var + " en el ambito " + ambito + ". Linea " + lexico.getContadorLinea());
                           generador.setError();
                        }
                        TS.agregarToken(token + ambito, atributos);
                    }
                }
            }
        }
    }
break;
case 35:
//#line 154 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 36:
//#line 155 "gramatica.y"
{
	        yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	    }
break;
case 37:
//#line 160 "gramatica.y"
{yyval.sval = String.valueOf(T_OCTAL);}
break;
case 38:
//#line 161 "gramatica.y"
{yyval.sval = String.valueOf(T_UNSIGNED);}
break;
case 39:
//#line 162 "gramatica.y"
{yyval.sval = String.valueOf(T_SINGLE);}
break;
case 40:
//#line 170 "gramatica.y"
{
                TablaSimbolos TS = lexico.getTablaSimbolos();

                String funcion = TS.nameMangling(val_peek(4).sval);
                TS.editarLexema(val_peek(4).sval, funcion);

                Integer tipo = Integer.parseInt(val_peek(6).sval);
                TS.editarTipo(funcion, tipo);
                TS.agregarUso(funcion, NOMBRE_FUN);
                Integer tipoParam = Integer.parseInt(val_peek(2).sval);
                TS.editarTipo(val_peek(1).sval, tipoParam);
                TS.agregarUso(val_peek(1).sval, NOMBRE_PARAMETRO);
                TS.agregarTipoParam(funcion, tipoParam);
                TS.addAmbitos(val_peek(4).sval);
                
                String parametro =  val_peek(1).sval + TS.getAmbitos();
                TS.editarLexema(val_peek(1).sval,parametro);

                yyval.sval = generador.addTerceto(funcion,parametro,null);
                generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_FUNCION);
                generador.putEtiqueta(funcion);
              }
break;
case 41:
//#line 192 "gramatica.y"
{System.err.println("Error: Falta nombre de funcion"); generador.setError();}
break;
case 42:
//#line 193 "gramatica.y"
{System.err.println("Error: Falta parametro de funcion"); generador.setError();}
break;
case 43:
//#line 194 "gramatica.y"
{System.err.println("Error: falta nombre del parametro formal"); generador.setError();}
break;
case 44:
//#line 195 "gramatica.y"
{System.err.println("Error: falta tipo del parametro formal"); generador.setError();}
break;
case 45:
//#line 198 "gramatica.y"
{
                /*verificar el tipo de retorno*/
                TablaSimbolos TS = lexico.getTablaSimbolos();
                String lexemaFun = TS.getUltimoAmbito(); /*obtengo el lexema de la funcion*/
                Integer tipoFun = TS.getTipo(lexemaFun); /*obtengo el tipo de la funcion*/
                Terceto ret = generador.getTerceto(Integer.parseInt(val_peek(1).sval.replaceAll("\\D", "")));
                Integer tipoRetorno = ret.getTipo();
                if (tipoFun != tipoRetorno){
                    System.err.println("Error: tipo de retorno invalido en funcion: " + lexemaFun);
                    generador.setError();
                }
                ret.setTipo(TIPO_RETORNO);
                /*desapilar el ambito de la funcion*/
                TS.eliminarAmbito();
     }
break;
case 46:
//#line 213 "gramatica.y"
{
	            /*verificar tipo retorno*/
	             TablaSimbolos TS = lexico.getTablaSimbolos();
                 String lexemaFun = TS.getUltimoAmbito(); /*obtengo el lexema de la funcion*/
                 Integer tipoFun = TS.getTipo(lexemaFun); /*obtengo el tipo de la funcion*/

                 Terceto ret = generador.getTerceto(Integer.parseInt(val_peek(1).sval.replaceAll("\\D", "")));
                 Integer tipoRetorno = ret.getTipo();
                if (tipoFun != tipoRetorno){
                     System.err.println("Error: tipo de retorno invalido en funcion: " + lexemaFun);
                     generador.setError();
                }
                /*desapilar el ambito de la funcion*/
                ret.setTipo(TIPO_RETORNO);
                TS.eliminarAmbito();

	}
break;
case 47:
//#line 229 "gramatica.y"
{System.err.println("Error: falta retorno en funcion"); generador.setError();}
break;
case 48:
//#line 234 "gramatica.y"
{
            Integer tipo = null;
            TablaSimbolos TS = lexico.getTablaSimbolos();
            String expresion = TS.buscarVariable(val_peek(2).sval);
            if(expresion == null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
                yyval.sval = generador.addTerceto("RETORNO",expresion , null);
            }else{
				switch (expresion){
                	case "Terceto" :
                        tipo = generador.getTerceto(Integer.parseInt(val_peek(2).sval.replaceAll("\\D", ""))).getTipo();
                        yyval.sval = generador.addTerceto("RETORNO",val_peek(2).sval , null);
                        generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipo);
                        break;
                	default: /*la exp es una variable o una cte*/
                        tipo = TS.getTipo(expresion);
                        yyval.sval = generador.addTerceto("RETORNO",expresion , null);
                        generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipo);
                        break;
            	}
            }
            
        }
break;
case 49:
//#line 260 "gramatica.y"
{
        /*verificar que el uso de ID sea nombre de función.*/
        TablaSimbolos TS = lexico.getTablaSimbolos();

        String id = TS.buscarVariable(val_peek(3).sval);
        if (id == null || TS.getUso(id) != NOMBRE_FUN){
            System.err.println("Error: funcion no declarada. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else{ /*es una funcion alcanzable*/
            /*verificar que el tipo del parametro formal sea igual al del parametro real.*/
            /* tenemos en la tabla de simbolos, para el lexema ID (nombre de la funcion) el tipo de su parametro,
            entonces lo comparamos con el tipo de exp_arit.
            */
             if(id.equals(TS.getUltimoAmbito())) {
                                    System.err.println("Error no se admiten funciones recursivas");
                                    generador.setError();
             }
            Integer tipoExp = null;
            String expresion = TS.buscarVariable(val_peek(1).sval);
            if(expresion == null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
            }else{
            	switch (expresion){
	                case "Terceto" :
	                        tipoExp = generador.getTerceto(Integer.parseInt(val_peek(1).sval.replaceAll("\\D", ""))).getTipo();
	                        break;
	                default: /*la exp es una variable o una cte*/
	                        tipoExp = TS.getTipo(expresion);
	                        break;
            	}
            }
            

            if (tipoExp != TS.getTipoParam(id)){
                System.err.println("Error: Invocación a una función con un parametro incorrecto. Linea: " + lexico.getContadorLinea());
                generador.setError();
            }
            /* TODO implementar logica de etiquetas para llamar a la funcion*/
            String operando1;
            if (expresion == "Terceto") operando1 = val_peek(3).sval;
            else operando1 = expresion;

            yyval.sval = generador.addTerceto("CALL", id, operando1);
            Integer tipo = TS.getTipo(id);
            generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipo);
        }

    }
break;
case 50:
//#line 311 "gramatica.y"
{
        /*verificar que el uso de ID sea nombre de función.*/
        TablaSimbolos TS = lexico.getTablaSimbolos();
        if(val_peek(4).sval.equals(TS.getUltimoAmbito())) {
                            System.err.println("Error no se admiten funciones recursivas");
            generador.setError();
        }
        String id = TS.buscarVariable(val_peek(4).sval);
        if (id == null || TS.getUso(id) != NOMBRE_FUN){
            System.err.println("Error: funcion no declarada. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else { /*es una función alcanzable*/
            /*verificar que el tipo del parametro formal sea igual al del parametro real.*/
            /* tenemos en la tabla de simbolos, para el lexema ID (nombre de la funcion) el tipo de su parametro,
            entonces lo comparamos con el tipo de exp_arit.
            */
            Integer tipoParam = TS.getTipoParam(id);
            Integer tipoCast = Integer.parseInt(val_peek(2).sval);

             if(id.equals(TS.getUltimoAmbito())) {
                                    System.err.println("Error no se admiten funciones recursivas");
                                    generador.setError();
                                }

            if (tipoParam != tipoCast){
                System.err.println("Error: tipo de parametro incompatible. Se esperaba un parametro del tipo "+this.mappeoTipo(tipoParam)+". Linea: " + lexico.getContadorLinea());
                generador.setError();
            }
            else{
                Integer tipoExp = null;
                String expresion = TS.buscarVariable(val_peek(1).sval);
                if(expresion == null){
                	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                	generador.setError();
                }else{
					switch (expresion){
                    	case "Terceto" :
                            tipoExp = generador.getTerceto(Integer.parseInt(val_peek(1).sval.replaceAll("\\D", ""))).getTipo();
                            break;
                    	default: /*la exp es una variable o una cte*/
                            tipoExp = TS.getTipo(expresion);
                            break;
                	}
                }
                
                /*crear terceto de conversion*/
                String conversion = generador.getConversion(tipoExp, tipoCast);
                if (conversion == null) {
                	System.err.println("Error: conversion invalida. No se puede convertir"+this.mappeoTipo(tipoExp)+ " a "+this.mappeoTipo(tipoCast) +" .Linea: " + lexico.getContadorLinea());
                	generador.setError();
                }

                String operando1;
                if (expresion == "Terceto") operando1 = val_peek(4).sval;
                else operando1 = expresion;

                String terceto = generador.addTerceto(conversion, operando1, null);
                yyval.sval = generador.addTerceto("CALL", id, terceto);
                Integer tipo = TS.getTipo(id);
                generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipo);

            }
        }

    }
break;
case 51:
//#line 377 "gramatica.y"
{
        /*verificar que el uso de ID sea nombre de función.*/
        TablaSimbolos TS = lexico.getTablaSimbolos();

        String id = TS.buscarVariable(val_peek(6).sval);
        if (id == null || TS.getUso(id) != NOMBRE_FUN){
            System.err.println("Error: funcion no declarada. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else { /*es una función alcanzable*/
            /*verificar que el tipo del parametro formal sea igual al del parametro real.*/
            /* tenemos en la tabla de simbolos, para el lexema ID (nombre de la funcion) el tipo de su parametro,
            entonces lo comparamos con el tipo de exp_arit.
            */
            if(id.equals(TS.getUltimoAmbito())) {
                        System.err.println("Error no se admiten funciones recursivas");
                        generador.setError();
                    }
            Integer tipoParam = TS.getTipoParam(id);
            Integer tipoCast = Integer.parseInt(val_peek(4).sval);

            if (tipoParam != tipoCast){
                System.err.println("Error: tipo de parametro incompatible. Se esperaba un parametro del tipo "+this.mappeoTipo(tipoParam)+". Linea: " + lexico.getContadorLinea());
                generador.setError();
            }
            else{
                Integer tipoExp = null;
                String expresion = TS.buscarVariable(val_peek(2).sval);
                if(expresion == null){
                	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                	generador.setError();
                }else{
                	switch (expresion){
                    	case "Terceto" :
                            tipoExp = generador.getTerceto(Integer.parseInt(val_peek(2).sval.replaceAll("\\D", ""))).getTipo();
                            break;
                    	default: /*la exp es una variable o una cte*/
                            tipoExp = TS.getTipo(expresion);
                            break;
                	}
                }
                
                /*crear terceto de conversion*/
                String conversion = generador.getConversion(tipoExp, tipoCast);
                if (conversion == null) {System.out.println("Error de conversion en linea: " + lexico.getContadorLinea());}
                String operando1;
                if (expresion == "Terceto") operando1 = val_peek(6).sval;
                else operando1 = expresion;

                String terceto = generador.addTerceto(conversion, operando1, null);

                yyval.sval = generador.addTerceto("CALL", id, terceto);
                Integer tipo = TS.getTipo(id);
                generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipo);
            }
        }

    }
break;
case 52:
//#line 435 "gramatica.y"
{System.err.println("Error: falta de parámetro en invocación a función. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 53:
//#line 442 "gramatica.y"
{
                    /*verificación de tipos*/

                    Integer tipoExp = null;
                    Integer tipoTermino = null;
                    TablaSimbolos TS = lexico.getTablaSimbolos();

                    String expresion = TS.buscarVariable(val_peek(2).sval);
                    if(expresion == null){
						System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
						generador.setError();
                    }else{
                    	switch (expresion){
                        	case "Terceto":
                                tipoExp = generador.getTerceto(Integer.parseInt(val_peek(2).sval.replaceAll("\\D", ""))).getTipo();
                                break;
                        	default: /*es una var valida*/
                                tipoExp = TS.getTipo(expresion);
                                break;
                    	}
                    }
                    

                    String termino = TS.buscarVariable(val_peek(0).sval);
                    if(termino == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else{
                    	switch (termino){
                        	case "Terceto":
                                tipoTermino = generador.getTerceto(Integer.parseInt(val_peek(0).sval.replaceAll("\\D", ""))).getTipo();
                                break;
                        	default: /*es una var valida*/
                                tipoTermino = TS.getTipo(termino);
                                break;
                    	}
                    }

                    

                    if(tipoExp != tipoTermino){
                        System.err.println("Error: Incompatibilidad de tipos en suma. No se puede operar entre "+this.mappeoTipo(tipoExp)+" y "+this.mappeoTipo(tipoTermino)+". Linea " + lexico.getContadorLinea());
                        generador.setError();
                    }
                    else{
                        String operando1, operando2;
                        if (expresion == "Terceto") operando1 = val_peek(2).sval;
                        else operando1 = expresion;
                        if (termino == "Terceto") operando2 = val_peek(0).sval;
                        else operando2 = termino;

                        yyval.sval = generador.addTerceto("+", operando1, operando2);
                        generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoExp);
                        System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());
                    }
           }
break;
case 54:
//#line 499 "gramatica.y"
{
                    /*verificación de tipos*/

                    Integer tipoExp = null;
                    Integer tipoTermino = null;
                    TablaSimbolos TS = lexico.getTablaSimbolos();

                    String expresion = TS.buscarVariable(val_peek(2).sval);
                    if(expresion == null){
						System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
						generador.setError();
                    }else{
                    	switch (expresion){
                        	case "Terceto":
                                tipoExp = generador.getTerceto(Integer.parseInt(val_peek(2).sval.replaceAll("\\D", ""))).getTipo();
                                break;
                        	default: /*es una var valida*/
                                tipoExp = TS.getTipo(expresion);
                                break;
                    	}	
                    }
                    

                    String termino = TS.buscarVariable(val_peek(0).sval);

                    if(termino == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else{
						switch (termino){
	                        case "Terceto":
	                                tipoTermino = generador.getTerceto(Integer.parseInt(val_peek(0).sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: /*es una var valida*/
	                                tipoTermino = TS.getTipo(termino);
	                                break;
	                    }
                    }
                    

                    if(tipoExp != tipoTermino){
                        System.err.println("Error: Incompatibilidad de tipos en resta. No se puede operar entre "+this.mappeoTipo(tipoExp)+" y "+this.mappeoTipo(tipoTermino)+". Linea " + lexico.getContadorLinea());
                        generador.setError();
                    }
                    else{
                        String operando1, operando2;
                        if (expresion == "Terceto") operando1 = val_peek(2).sval;
                        else operando1 = expresion;
                        if (termino == "Terceto") operando2 = val_peek(0).sval;
                        else operando2 = termino;

                        yyval.sval = generador.addTerceto("-", operando1, operando2);
                        generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoExp);
                        System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());
                    }
           }
break;
case 55:
//#line 556 "gramatica.y"
{
                    System.err.println("Error: Falta el término después de '+' en expresion aritmetica. Línea: " + lexico.getContadorLinea());
                    generador.setError();
           }
break;
case 56:
//#line 561 "gramatica.y"
{
                    System.err.println("Error: Falta el término después de '-' en expresión aritmetica. Línea: " + lexico.getContadorLinea());
                    generador.setError();
           }
break;
case 57:
//#line 566 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 58:
//#line 571 "gramatica.y"
{
        			yyval.sval = val_peek(0).sval;
    			}
break;
case 59:
//#line 574 "gramatica.y"
{
	    yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	}
break;
case 60:
//#line 579 "gramatica.y"
{
                    /*verificación de tipos*/

                    Integer tipoFactor = null;
                    Integer tipoTermino = null;
                    TablaSimbolos TS = lexico.getTablaSimbolos();

                    String factor = TS.buscarVariable(val_peek(0).sval);
                    if(factor == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else{
	                    switch (factor){
	                        case "Terceto":
	                                tipoFactor = generador.getTerceto(Integer.parseInt(val_peek(0).sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: /*es una var valida*/
	                            tipoFactor = TS.getTipo(factor);
	                            break;
	                    }	
                    }
                    

                    String termino = TS.buscarVariable(val_peek(2).sval);
                    if(termino == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else {
	                    switch (termino){
	                        case "Terceto":
	                                tipoTermino = generador.getTerceto(Integer.parseInt(val_peek(2).sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: /*es una var valida*/
	                                tipoTermino = TS.getTipo(termino);
	                                break;
	                    }	
                    }

                    if(tipoFactor != tipoTermino){
                        System.err.println("Error: Incompatibilidad de tipos en multiplicacion. No se puede operar entre "+this.mappeoTipo(tipoFactor)+" y "+this.mappeoTipo(tipoTermino)+". Linea " + lexico.getContadorLinea());
                        generador.setError();
                    }
                    else{
                        String operando1, operando2;
                        if (termino == "Terceto") operando1 = val_peek(2).sval;
                        else operando1 = termino;
                        if (factor == "Terceto") operando2 = val_peek(0).sval;
                        else operando2 = factor;

                        yyval.sval = generador.addTerceto("*", operando1, operando2);
                        generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoTermino);
                        System.out.println("Se detecto: Multiplicacion " + "en linea: " + lexico.getContadorLinea());
                    }
           }
break;
case 61:
//#line 634 "gramatica.y"
{
                    /*verificación de tipos*/

                    Integer tipoFactor = null;
                    Integer tipoTermino = null;
                    TablaSimbolos TS = lexico.getTablaSimbolos();

                    String factor = TS.buscarVariable(val_peek(0).sval);
                    if(factor == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else{
	                    switch (factor){
	                        case "Terceto":
	                                tipoFactor = generador.getTerceto(Integer.parseInt(val_peek(0).sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: /*es una var valida*/
	                            tipoFactor = TS.getTipo(factor);
	                            break;
	                    }	
                    }
                    

                    String termino = TS.buscarVariable(val_peek(2).sval);
                    if(termino == null){
                    	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
                    	generador.setError();
                    }else {
	                    switch (termino){
	                        case "Terceto":
	                                tipoTermino = generador.getTerceto(Integer.parseInt(val_peek(2).sval.replaceAll("\\D", ""))).getTipo();
	                                break;
	                        default: /*es una var valida*/
	                                tipoTermino = TS.getTipo(termino);
	                                break;
	                    }	
                    }

                    if(tipoFactor != tipoTermino){
                        System.err.println("Error: Incompatibilidad de tipos en division. No se puede operar entre "+this.mappeoTipo(tipoFactor)+" y "+this.mappeoTipo(tipoTermino)+". Linea " + lexico.getContadorLinea());
                        generador.setError();
                    }
                    else{
                        String operando1, operando2;
                        if (termino == "Terceto") operando1 = val_peek(2).sval;
                        else operando1 = termino;
                        if (factor == "Terceto") operando2 = val_peek(0).sval;
                        else operando2 = factor;

                        yyval.sval = generador.addTerceto("/", operando1, operando2);
                        generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoTermino);
                        System.out.println("Se detecto: Division " + "en linea: " + lexico.getContadorLinea());
                    }
           }
break;
case 62:
//#line 688 "gramatica.y"
{
	    	yyval.sval = val_peek(0).sval;
		}
break;
case 63:
//#line 692 "gramatica.y"
{System.err.println("Error: Falta el factor después de '*' en expresion aritmetica. Línea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 64:
//#line 693 "gramatica.y"
{System.err.println("Error: Falta el factor después de '/' en expresión aritmetica. Línea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 65:
//#line 696 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
            System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());
            }
break;
case 66:
//#line 700 "gramatica.y"
{System.out.println("Se detecto: Invocación a función " + "en linea: " + lexico.getContadorLinea());}
break;
case 67:
//#line 701 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 68:
//#line 702 "gramatica.y"
{
		 	yyval.sval = truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea());
		 	TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 69:
//#line 707 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 70:
//#line 710 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 71:
//#line 713 "gramatica.y"
{
        	yyval.sval = truncarFueraRango("-"+val_peek(0).sval, lexico.getContadorLinea());
        	TablaSimbolos TS = lexico.getTablaSimbolos();
        	TS.editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 72:
//#line 721 "gramatica.y"
{
    String token = val_peek(3).sval+'{'+val_peek(1).sval+'}';
    TablaSimbolos TS = lexico.getTablaSimbolos();
    String var = TS.buscarVariable(token);
    if (var != null){
        yyval.sval = token;
    }
    else {
        System.err.println("Error: Variable inexistenet o Intento de acceso a una posición de triple inexistente. Linea " + lexico.getContadorLinea());
        generador.setError();
        yyval.sval = token;
        /*en este punto, los tercetos se generan igual,
        aunque se intente acceder a un valor invalido del tercerto.
        El generador de assembler deberia volver a verificar si la constante es 1, 2 o 3
        y solo generar codigo en ese caso, de lo contrario lanzar error.
        */
    }
}
break;
case 73:
//#line 746 "gramatica.y"
{
            Integer tipoExp = null;
            Integer tipoID = null;
            TablaSimbolos TS = lexico.getTablaSimbolos();
            String expresion = TS.buscarVariable(val_peek(0).sval);
            if(expresion==null){
				System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
				generador.setError();
            }else{
				switch (expresion){
                    case "Terceto":
                            tipoExp = generador.getTerceto(Integer.parseInt(val_peek(0).sval.replaceAll("\\D", ""))).getTipo();
                            break;
                    default: /*es una var valida*/
                            tipoExp = TS.getTipo(expresion);
                            break;
                }
            }
                    

            String id = TS.buscarVariable(val_peek(2).sval);
            if(id == null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
            }else{
            	/*es una var valida*/
                tipoID = TS.getTipo(id);

                if (tipoID != tipoExp){
                     System.err.println("Error: tipos invalidos en asignación. No se puede operar entre "+this.mappeoTipo(tipoExp)+" y "+this.mappeoTipo(tipoID)+". Linea: " +lexico.getContadorLinea());
                     generador.setError();
                }else{
                   String operando2;
                   if (expresion == "Terceto") operando2 = val_peek(0).sval;
                   else operando2 = expresion;
                   yyval.sval = generador.addTerceto(":=", id, operando2);
                   generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoID);
               }
            }
                

        }
break;
case 74:
//#line 789 "gramatica.y"
{
            Integer tipoExp = null;
            Integer tipoID = null;
            TablaSimbolos TS = lexico.getTablaSimbolos();
            String expresion = TS.buscarVariable(val_peek(0).sval);
            if(expresion==null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
            }else{
            	switch (expresion){
                    case "Terceto":
                            tipoExp = generador.getTerceto(Integer.parseInt(val_peek(0).sval.replaceAll("\\D", ""))).getTipo();
                                break;
                    default: /*es una var valida*/
                            tipoExp = TS.getTipo(expresion);
                            break;
                }
            }
                    

            String id = TS.buscarVariable(val_peek(2).sval);
            if(id == null){
            	System.err.println("Error: variable no declarada. Linea: " + lexico.getContadorLinea());
            	generador.setError();
            }else{
                tipoID = TS.getTipo(id);

                if (tipoID != tipoExp){
                      System.err.println("Error: tipos invalidos en asignación. No se puede operar entre "+this.mappeoTipo(tipoExp)+" y "+this.mappeoTipo(tipoID)+". Linea: " +lexico.getContadorLinea());
                 }
                else{
                    String operando2;
                    if (expresion == "Terceto") operando2 = val_peek(0).sval;
                    else operando2 = expresion;
                    yyval.sval = generador.addTerceto(":=", id, operando2);
                    generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(tipoID);
                }
                
            }
                

        }
break;
case 75:
//#line 834 "gramatica.y"
{
                lexico.getTablaSimbolos().agregarUso(val_peek(0).sval, NOMBRE_ETIQUETA);
                TablaSimbolos TS = lexico.getTablaSimbolos();
    			String etq = val_peek(1).sval+"@"+TS.getAmbitos();
    			if(!generador.isEtiqueta(etq)){
    				yyval.sval = generador.addTerceto(etq,null,null);

    				generador.putEtiqueta(etq);

    				generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);

    			}else{
    				System.err.println("Error: la etiqueta "+etq+" ya existente. Linea: "+lexico.getContadorLinea());
    			}
    		}
break;
case 76:
//#line 851 "gramatica.y"
{
            TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.agregarUso(val_peek(1).sval, NOMBRE_ETIQUETA);
			String etq = val_peek(1).sval+"@"+TS.getAmbitos();
			yyval.sval = generador.addTerceto("BI", null , etq);
			generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);

			if(!generador.isEtiqueta(etq)){
		    	generador.addGoto(Integer.parseInt(yyval.sval.replaceAll("\\D", "")), etq);
		    }

		    
       }
break;
case 77:
//#line 864 "gramatica.y"
{System.err.println("Error: falta de etiqueta en la sentencia GOTO" + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 78:
//#line 867 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 79:
//#line 871 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 80:
//#line 875 "gramatica.y"
{System.err.println("Error: falta parametro " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 81:
//#line 883 "gramatica.y"
{
							int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
							generador.eliminarPila();
							Terceto t = generador.getTerceto(pos);
							String label = "ET"+generador.getSizeTercetos();

							t.setTercerParametro(label);
							yyval.sval=generador.addTerceto(label, null, null);
							generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
			}
break;
case 82:
//#line 894 "gramatica.y"
{System.err.println("Error: Falta END_IF de cierre " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 83:
//#line 895 "gramatica.y"
{
		System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());
		int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
		generador.eliminarPila();
		Terceto t = generador.getTerceto(pos);
		String label = "ET"+generador.getSizeTercetos();
		t.setTercerParametro(label);
		
		yyval.sval=generador.addTerceto(label, null, null);
		generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
	 }
break;
case 84:
//#line 906 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 907 "gramatica.y"
{System.err.println("Error: Falta de contenido en el bloque then " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 86:
//#line 908 "gramatica.y"
{System.err.println("Error: Falta de contenido en el bloque else " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 87:
//#line 911 "gramatica.y"
{
							yyval.sval = generador.addTerceto("BF", val_peek(1).sval, null);
							generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);
							generador.agregarPila(yyval.sval);
				}
break;
case 88:
//#line 918 "gramatica.y"
{
							int pos =Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));
							generador.eliminarPila();
							Terceto t = generador.getTerceto(pos);
							int tam = generador.getSizeTercetos()+1;
							String label = "ET"+tam;
							
							t.setTercerParametro(label);
							yyval.sval = generador.addTerceto("BI", null, null);
							generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);
							generador.agregarPila(yyval.sval);

							String posT = generador.addTerceto(label, null, null);
							generador.getTerceto(Integer.parseInt(posT.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
				  }
break;
case 89:
//#line 936 "gramatica.y"
{

                    		TablaSimbolos TS = lexico.getTablaSimbolos();

                    		String primer_exp_arit = TS.buscarVariable(val_peek(3).sval);
                    		Integer t_primer_exp_arit = null;
                    		Integer pos;

                    		if(primer_exp_arit == null){
                    			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                    			generador.setError();
                    		}else{
                    			switch(primer_exp_arit){
                    				case "Terceto":
                    					pos = Integer.parseInt(val_peek(3).sval.replaceAll("\\D", ""));
                						t_primer_exp_arit = generador.getTerceto(pos).getTipo();
                    					break;
                    				default:
                    					t_primer_exp_arit = TS.getTipo(primer_exp_arit);
                    					break;
                    			}
                    		}

            				String segunda_exp_arit = TS.buscarVariable(val_peek(1).sval);
                    		Integer t_segunda_exp_arit = null;

                    		if(segunda_exp_arit == null){
                    			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                    			generador.setError();
                    		}else{
                    			switch(segunda_exp_arit){
                    				case "Terceto":
                    					pos = Integer.parseInt(val_peek(1).sval.replaceAll("\\D", ""));
                						t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
                    					break;
                    				default:
                    					t_segunda_exp_arit = TS.getTipo(segunda_exp_arit);
                    					break;
                    			}
                    		}

                    		if(t_primer_exp_arit != t_segunda_exp_arit){
                    			System.err.println("Error: comparación entre dos expresiones de tipos diferentes. No se puede comparar entre "+this.mappeoTipo(t_primer_exp_arit)+" y "+this.mappeoTipo(t_segunda_exp_arit)+". Linea: "+lexico.getContadorLinea());
                    			generador.setError();
                    		}

                    		String operando1, operando2;

                    		if (primer_exp_arit == "Terceto") operando1 = val_peek(3).sval;
                            else operando1 = primer_exp_arit;
                            if (segunda_exp_arit == "Terceto") operando2 = val_peek(1).sval;
                            else operando2 = segunda_exp_arit;

                    		yyval.sval = generador.addTerceto(val_peek(2).sval, operando1, operando2);
                            generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(t_primer_exp_arit);
                    		System.out.println("Se detecto: comparación");

                    	}
break;
case 90:
//#line 995 "gramatica.y"
{

          	        String[] lista1 = val_peek(6).sval.split(",");
          	        String[] lista2 = val_peek(2).sval.split(",");
          	        if (lista1.length != lista2.length){
          	            System.err.println("Error: Los tamaños de las listas en la condicion no coinciden. Linea: " + lexico.getContadorLinea());
          	            generador.setError();
          	        }else{

          	        	boolean error_comparacion = false;

          	        	TablaSimbolos TS = lexico.getTablaSimbolos();

                  		String primer_exp_arit = TS.buscarVariable(lista1[0]);
          	        	String segunda_exp_arit = TS.buscarVariable(lista2[0]);

          	            Integer t_primer_exp_arit = null;
                  		Integer t_segunda_exp_arit = null;
          	            int pos;

          				String operando1, operando2;

                  		if(primer_exp_arit == null){
                  			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                  			generador.setError();
                  		}else{
                  			switch(primer_exp_arit){
                  				case "Terceto":
                  					pos = Integer.parseInt(val_peek(7).sval.replaceAll("\\D", ""));
              						t_primer_exp_arit = generador.getTerceto(pos).getTipo();
                  					break;
                  				default:
                  					t_primer_exp_arit = TS.getTipo(primer_exp_arit);
                  					break;
                  			}
                  		}



                  		if(segunda_exp_arit == null){
                  			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                  			generador.setError();
                  		}else{
                  			switch(segunda_exp_arit){
                  				case "Terceto":
                  					pos = Integer.parseInt(val_peek(5).sval.replaceAll("\\D", ""));
              						t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
                  					break;
                  				default:
                  					t_segunda_exp_arit = TS.getTipo(segunda_exp_arit);
                  					break;
                  			}
                  		}

          				if(t_primer_exp_arit!=t_segunda_exp_arit) error_comparacion=true;


                  		if (primer_exp_arit == "Terceto") operando1 = lista1[0];
                          else operando1 = primer_exp_arit;
                          if (segunda_exp_arit == "Terceto") operando2 = lista2[0];
                          else operando2 = segunda_exp_arit;

                  		yyval.sval = generador.addTerceto(val_peek(4).sval, operando1, operando2);
                        generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(t_primer_exp_arit);


          	            if(lista1.length!=1){
          	                String auxTerceto;

          	                for (int i = 1; i<lista1.length; i++){

          	                    primer_exp_arit = TS.buscarVariable(lista1[i]);
          	                    segunda_exp_arit = TS.buscarVariable(lista2[i]);


          	                    if(primer_exp_arit == null){
                  					System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
                  					generador.setError();
          		        		}else{
          		        			switch(primer_exp_arit){
          		        				case "Terceto":
          		        					pos = Integer.parseInt(lista1[i].replaceAll("\\D", ""));
          		    						t_primer_exp_arit = generador.getTerceto(pos).getTipo();
          		        					break;
          		        				default:
          		        					t_primer_exp_arit = TS.getTipo(primer_exp_arit);
          		        					break;
          		        			}
                  				}


          		        		if(segunda_exp_arit == null){
          		        			System.err.println("Error: variable no declarada. Linea "+lexico.getContadorLinea());
          		        			generador.setError();
          		        		}else{
          		        			switch(segunda_exp_arit){
          		        				case "Terceto":
          		        					pos = Integer.parseInt(lista2[i].replaceAll("\\D", ""));
          		    						t_segunda_exp_arit = generador.getTerceto(pos).getTipo();
          		        					break;
          		        				default:
          		        					t_segunda_exp_arit = TS.getTipo(segunda_exp_arit);
          		        					break;
          		        			}
          		        		}

          						if(t_primer_exp_arit!=t_segunda_exp_arit) error_comparacion=true;

          						if (primer_exp_arit == "Terceto") operando1 = lista1[i];
                          		else operando1 = primer_exp_arit;
                          		if (segunda_exp_arit == "Terceto") operando2 = lista2[i];
                          		else operando2 = segunda_exp_arit;

          						auxTerceto= generador.addTerceto(val_peek(4).sval, operando1, operando2);
          	                    yyval.sval =generador.addTerceto("AND", yyval.sval, auxTerceto);
                                generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(t_primer_exp_arit);
          	                }

          	                if(error_comparacion){
          	                	System.err.println("Error: comparación entre dos expresiones de tipos diferentes. Linea: "+lexico.getContadorLinea());
          	                	generador.setError();
          	                }
          	            }

          	        }
          		    System.out.println("Se detecto: comparación múltiple");
          		  }
break;
case 91:
//#line 1123 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 92:
//#line 1124 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 93:
//#line 1125 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 94:
//#line 1126 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 95:
//#line 1127 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 96:
//#line 1128 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 97:
//#line 1130 "gramatica.y"
{System.err.println("Error: falta de comparador. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 98:
//#line 1131 "gramatica.y"
{System.err.println("Error, falta de lista de expresión aritmetica en comparación. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 99:
//#line 1135 "gramatica.y"
{yyval.sval = ">=";}
break;
case 100:
//#line 1136 "gramatica.y"
{yyval.sval = "<=";}
break;
case 101:
//#line 1137 "gramatica.y"
{yyval.sval = "!=";}
break;
case 102:
//#line 1138 "gramatica.y"
{yyval.sval = "=";}
break;
case 103:
//#line 1139 "gramatica.y"
{yyval.sval = ">";}
break;
case 104:
//#line 1140 "gramatica.y"
{yyval.sval = "<";}
break;
case 105:
//#line 1143 "gramatica.y"
{
					int pos = Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));

					yyval.sval = generador.addTerceto("BF", val_peek(0).sval, generador.getTerceto(pos).getOperador());
					generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);
    				generador.eliminarPila();
				}
break;
case 106:
//#line 1151 "gramatica.y"
{System.err.println("Error: falta cuerpo en la iteracion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 107:
//#line 1152 "gramatica.y"
{System.err.println("Error: falta de until en la iteracion repeat. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 108:
//#line 1156 "gramatica.y"
{
				    yyval.sval = generador.addTerceto("ET" + generador.getSizeTercetos(), null, null);
				    generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
				    generador.agregarPila(yyval.sval);
				}
break;
case 109:
//#line 1166 "gramatica.y"
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
        TablaSimbolos TS = lexico.getTablaSimbolos();
        String amb = TS.getAmbitos();
        this.tiposUsuario.add(val_peek(0).sval + amb);
        TS.agregarUso(val_peek(0).sval, NOMBRE_TIPO);
        TS.editarLexema(val_peek(0).sval, val_peek(0).sval + amb);
    }
break;
//#line 2160 "Parser.java"
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
  generador.generarCodigoMaquina();
  lexico.getTablaSimbolos().imprimirTabla();
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
