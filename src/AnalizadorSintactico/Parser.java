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
    4,    4,    4,    4,   27,   28,   29,   29,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   30,   30,   30,
   30,   30,   30,    7,    7,    7,   31,   15,   32,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    3,    4,    3,
    2,    3,    1,    2,    1,    1,    1,    1,    2,    2,
    1,    3,    1,    1,    1,   10,    9,    9,    9,    8,
    9,    8,    2,    1,    1,    5,    4,    7,    3,    3,
    3,    4,    4,    1,    1,    3,    3,    3,    1,    4,
    4,    1,    1,    1,    1,    4,    3,    3,    1,    1,
    1,    2,    2,    3,    4,    4,    3,    4,    3,    2,
    5,    4,    2,    3,    3,    1,    5,    9,    8,    8,
    8,    8,    4,    4,    4,    8,    5,    1,    1,    1,
    1,    1,    1,    4,    3,    3,    1,    6,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,  107,    0,    0,    0,    0,    0,
   34,   35,   33,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   25,   26,   27,   28,    0,    0,    0,    0,
    0,   31,    0,    0,    0,    0,    0,   69,   70,   71,
    0,   64,    0,    0,   59,   63,   65,    0,    0,    0,
  109,    0,    0,    0,   73,    0,    0,    0,    4,    6,
    0,    0,    0,   86,    0,   83,    0,    0,    0,    0,
    0,    0,   49,    0,    0,    0,    0,    0,    0,    0,
   99,  100,   98,    0,    0,  101,  102,  103,    0,    0,
    0,   85,    0,    0,    0,   77,    0,    0,   74,   72,
    3,    8,   10,    0,    0,    0,    0,    0,    0,    0,
    0,   79,    0,   84,  105,    0,  106,    2,    0,   47,
   66,   32,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,    0,   57,    1,   78,   75,
   76,    0,    0,   45,    0,    0,    0,   21,   18,    0,
    0,   20,    0,  104,    0,    0,    0,   95,    0,    0,
    0,   52,   53,   93,   61,   60,    0,    0,    0,   43,
    0,    0,   22,   19,   81,    0,    0,   97,    0,    0,
   87,    0,  108,    0,    0,    0,    0,   48,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   46,    0,
    0,    0,    0,    0,    0,   42,    0,    0,    0,   40,
   96,    0,   90,   89,   91,   41,   38,    0,   37,   39,
   88,   36,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   42,   19,   20,   21,   22,   67,
  110,  111,   23,   24,   25,   26,   27,   36,  146,  123,
   44,   80,   45,   46,   47,   55,   29,   68,   48,   89,
   30,   52,
};
final static short yysindex[] = {                      -235,
  505,  573,    0,   76,    0,   85,  573,  -33, -247, -104,
    0,    0,    0, -100,  -21,   21,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -224, -175, -196, -240,
  522,    0, -107,   65, -160,   44,  -23,    0,    0,    0,
   92,    0,  470,   25,    0,    0,    0, -159,  539,   74,
    0,   54,   81,   77,    0,   87,  105,  110,    0,    0,
  -29,   44, -107,    0, -156,    0, -164, -109,   85,   58,
  113,  211,    0,  138,   -4,   59,  -65, -107,  353,   83,
    0,    0,    0, -198,  307,    0,    0,    0, -107,  345,
  376,    0,  135,  161,  169,    0,  -35,  202,    0,    0,
    0,    0,    0,  166, -232,  211,  -31,  191,  155,  -76,
  -37,    0,  -11,    0,    0,   85,    0,    0, -107,    0,
    0,    0,  211,  393,   16, -107, -107,  -48,  178,   25,
  203,   25,   46,  215,    0,  220,    0,    0,    0,    0,
    0,  222,   45,    0,   23,  246, -107,    0,    0,  240,
   41,    0,   61,    0,  162,  387,  277,    0,  461,  211,
  291,    0,    0,    0,    0,    0,   84,   71,  310,    0,
   93,  498,    0,    0,    0,  325, -107,    0,   95, -107,
    0, -107,    0,  556,  102,  556,  305,    0,  104, -107,
  118,  142,  148,  556,  109,  556,  556,  122,    0,  335,
  167,  340,  342,  348,  126,    0,  271,  129,  139,    0,
    0,  365,    0,    0,    0,    0,    0,  146,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  306,  385,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  128,  -41,    0,    0,    0,
    0,    0,    0,  -16,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  410,  485,    0,    0,
    0,  145,    0,    0,    0,    0,  165,    0,    0,    0,
    0,  194,    0,    0,    0,    0,    0,    0,  174,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  219,    0,    0,  323,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  418,    0,    0,    0,    0,    0,    0,    9,
    0,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  379,    0,    0,    0,    0,  354,
    0,    0,  245,    0,    0,    0,    0,    0,   94,  447,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  111,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  449,  112,   -9,    0,  526,    0,    0,    0,    0,   -6,
    0,  -47,    0,    0,    0,    0,   -1,  402,  287,   49,
   38,  -46,   39,    0,  553,    0,    0,  366,  -54,  -43,
    0,    0,
};
final static int YYTABLESIZE=854;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         62,
   62,   62,   62,   62,   58,   62,   50,   84,   34,   85,
  105,   88,   86,   87,  115,  117,   34,   62,   62,   62,
   62,   58,    1,   70,   54,  144,   54,   54,   54,   65,
   51,  124,   74,   32,    2,  126,  120,   59,   84,   58,
   85,   69,   54,   54,   54,   54,   11,   12,   13,   50,
   61,   50,   50,   50,   43,  109,  158,  129,   84,   37,
   85,  154,  151,   38,   39,   40,   91,   50,   50,   50,
   50,   90,   64,   65,   51,   66,   51,   51,   51,   60,
  157,   72,   75,   63,  161,  168,  164,   77,   84,   79,
   85,   35,   51,   51,   51,   51,  142,   41,   97,   35,
  150,  107,   76,  145,   64,   73,  153,  112,   92,    5,
    6,  106,  179,   98,   96,   34,    8,   43,   43,  108,
   10,  130,  132,  128,   41,   57,  127,  125,  135,  137,
  189,   78,  191,  192,  190,  193,  195,  133,  198,   99,
  100,  145,   57,  201,  200,  101,  205,  127,  208,  209,
   37,   53,   94,   54,   38,   39,   40,    4,  202,  218,
   57,  127,  114,  102,   43,    5,    6,  155,  103,   92,
   56,  118,    8,    9,  159,  160,   10,  119,   11,   12,
   13,  107,  203,  121,   58,  127,   30,   58,  204,    5,
    6,  127,  122,  138,  149,  172,    8,   58,   35,  108,
   10,  139,  176,   29,   84,  143,   85,  212,   81,  140,
  127,   82,   83,  148,   55,   62,   62,   55,   62,   62,
   62,   62,   62,   80,   62,   62,   62,   33,  104,   62,
  147,   62,   62,  152,   62,   62,  162,   62,   62,   62,
   54,   54,   62,   54,   54,   54,   54,   54,  141,   54,
   54,   54,   67,   84,   54,   85,   54,   54,   65,   54,
   54,  163,   54,   54,   54,   50,   50,   54,   50,   50,
   50,   50,   50,  165,   50,   50,   50,   68,  166,   50,
  170,   50,   50,  167,   50,   50,  171,   50,   50,   50,
   51,   51,   50,   51,   51,   51,   51,   51,  173,   51,
   51,   51,  144,   82,   51,   57,   51,   51,   57,   51,
   51,  174,   51,   51,   51,   37,  180,   51,   57,   38,
   39,   40,   37,   11,   12,   13,   38,   39,   40,   94,
  182,   37,  175,   32,   33,   38,   39,   40,   95,  116,
  184,  183,   37,   11,   12,   13,   38,   39,   40,   37,
  185,   94,   37,   38,   39,   40,   38,   39,   40,   94,
   94,   94,  186,  199,   94,  188,   94,   94,   92,   94,
   94,  196,   94,   94,   94,  211,   92,   92,   92,  206,
  213,   92,  214,   92,   92,   30,   92,   92,  215,   92,
   92,   92,  210,   30,   30,   84,  216,   85,   30,  219,
   30,   30,   29,   30,   30,  221,   30,   30,   30,  220,
   29,   29,   88,   86,   87,   29,  222,   29,   29,   44,
   29,   29,   80,   29,   29,   29,  177,  178,   62,  169,
   80,   80,  113,  156,    0,   80,  127,   80,   80,    0,
   80,   80,    0,   80,   80,   80,   88,   86,   87,    0,
   31,   67,   88,   86,   87,   49,    0,    0,   55,   67,
   67,   55,    0,    0,   67,    0,   67,   67,    0,   67,
   67,    0,   67,   67,   67,    0,   68,   55,   55,   55,
   11,   12,   13,    0,   68,   68,    0,   56,    0,   68,
   56,   68,   68,    0,   68,   68,    0,   68,   68,   68,
    0,  181,   82,   84,    0,   85,   56,   56,   56,    0,
   82,   82,   84,    0,   85,   82,    0,   82,   82,    0,
   82,   82,    0,   82,   82,   82,   18,   18,    4,   88,
   86,   87,   18,    0,    0,    0,    5,    6,  187,   18,
   84,  217,   85,    8,    9,    0,  108,   10,    0,   11,
   12,   13,    0,   28,   28,    0,   18,    0,    0,   28,
    0,    0,  131,    5,   37,    0,   28,    0,   38,   39,
   40,    5,    5,    0,   18,    0,    5,    0,    5,    5,
   23,    5,    5,   28,    5,    5,    5,    0,   23,   23,
   18,    0,    0,   23,    0,   23,    0,    0,   23,   23,
  134,   28,   37,    0,    0,    0,   38,   39,   40,   81,
   37,   24,   82,   83,   38,   39,   40,   28,    0,   24,
   24,    0,    0,    0,   24,    0,   24,    0,    0,   24,
   24,  136,  194,   37,  197,   18,    0,   38,   39,   40,
    0,    0,    7,   81,  207,    0,   82,   83,    0,   81,
    7,    7,   82,   83,    0,    7,    0,    7,    7,    0,
    7,    7,   28,    7,    7,    7,    0,    9,    0,    0,
    0,    0,    0,    0,   55,    9,    9,   55,   55,    0,
    9,    0,    9,    9,    0,    9,    9,    0,    9,    9,
    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   56,    0,    0,   56,   56,    0,   18,
    0,   18,    0,    0,    0,    0,    0,    0,    0,   18,
    0,   18,   18,    0,    0,    0,   81,    0,    0,   82,
   83,    0,   18,    0,    0,    0,   28,    0,   28,    0,
    0,    0,   11,    0,    0,    0,   28,    0,   28,   28,
   11,   11,    0,    0,    0,   11,    0,   11,   11,   28,
   11,   11,    4,   11,   11,   11,    0,    0,    0,    0,
    5,    6,    0,    0,    7,    0,    0,    8,    9,    4,
    0,   10,    0,   11,   12,   13,    0,    5,    6,    0,
    0,    0,   71,    0,    8,    9,    4,    0,   10,    0,
   11,   12,   13,    0,    5,    6,    0,    0,    0,   93,
    0,    8,    9,    4,    0,   10,    0,   11,   12,   13,
    0,    5,    6,    0,    0,    0,    0,    0,    8,    9,
    4,  108,   10,    0,   11,   12,   13,    0,    5,    6,
    0,    0,    0,    0,    0,    8,    9,    0,    0,   10,
    0,   11,   12,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   14,   47,   40,   43,   40,   45,
   40,   60,   61,   62,   69,   70,   40,   59,   60,   61,
   62,   31,  258,   30,   41,  258,   43,   44,   45,  270,
  278,   78,   34,  258,  270,   79,   41,   59,   43,   49,
   45,  282,   59,   60,   61,   62,  279,  280,  281,   41,
  275,   43,   44,   45,    6,   65,   41,  256,   43,  258,
   45,  116,  110,  262,  263,  264,   42,   59,   60,   61,
   62,   47,  269,  270,   41,  272,   43,   44,   45,   59,
  124,   33,   34,  259,  128,   41,   41,   44,   43,   41,
   45,  123,   59,   60,   61,   62,   98,   40,   50,  123,
  110,  258,  263,  105,  269,   41,  113,  272,  268,  266,
  267,   63,  156,   60,   41,   40,  273,   69,   70,  276,
  277,   84,   85,   41,   40,   14,   44,   79,   90,   91,
  177,   40,  179,  180,   40,  182,  184,   89,  186,   59,
   64,  143,   31,  190,   41,   59,  194,   44,  196,  197,
  258,  256,   59,  258,  262,  263,  264,  258,   41,  207,
   49,   44,  272,   59,  116,  266,  267,  119,   59,   59,
  271,   59,  273,  274,  126,  127,  277,   40,  279,  280,
  281,  258,   41,  125,  194,   44,   59,  197,   41,  266,
  267,   44,  258,   59,  271,  147,  273,  207,  123,  276,
  277,   41,   41,   59,   43,   40,   45,   41,  257,   41,
   44,  260,  261,   59,   41,  257,  258,   44,  260,  261,
  262,  263,  264,   59,  266,  267,  268,  259,  258,  271,
   40,  273,  274,  271,  276,  277,   59,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,  284,  266,
  267,  268,   59,   43,  271,   45,  273,  274,  270,  276,
  277,   59,  279,  280,  281,  257,  258,  284,  260,  261,
  262,  263,  264,   59,  266,  267,  268,   59,   59,  271,
  258,  273,  274,   62,  276,  277,   41,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,   59,  266,
  267,  268,  258,   59,  271,  194,  273,  274,  197,  276,
  277,  271,  279,  280,  281,  258,   40,  284,  207,  262,
  263,  264,  258,  279,  280,  281,  262,  263,  264,  256,
   40,  258,  272,  258,  259,  262,  263,  264,  265,  282,
  270,  258,  258,  279,  280,  281,  262,  263,  264,  258,
   41,  258,  258,  262,  263,  264,  262,  263,  264,  266,
  267,  268,  270,   59,  271,   41,  273,  274,  258,  276,
  277,  270,  279,  280,  281,   41,  266,  267,  268,  271,
   41,  271,   41,  273,  274,  258,  276,  277,   41,  279,
  280,  281,  271,  266,  267,   43,  271,   45,  271,  271,
  273,  274,  258,  276,  277,   41,  279,  280,  281,  271,
  266,  267,   60,   61,   62,  271,  271,  273,  274,   41,
  276,  277,  258,  279,  280,  281,   40,   41,   27,  143,
  266,  267,   67,   41,   -1,  271,   44,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,   60,   61,   62,   -1,
    2,  258,   60,   61,   62,    7,   -1,   -1,   41,  266,
  267,   44,   -1,   -1,  271,   -1,  273,  274,   -1,  276,
  277,   -1,  279,  280,  281,   -1,  258,   60,   61,   62,
  279,  280,  281,   -1,  266,  267,   -1,   41,   -1,  271,
   44,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
   -1,   41,  258,   43,   -1,   45,   60,   61,   62,   -1,
  266,  267,   43,   -1,   45,  271,   -1,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,    1,    2,  258,   60,
   61,   62,    7,   -1,   -1,   -1,  266,  267,   41,   14,
   43,  271,   45,  273,  274,   -1,  276,  277,   -1,  279,
  280,  281,   -1,    1,    2,   -1,   31,   -1,   -1,    7,
   -1,   -1,  256,  258,  258,   -1,   14,   -1,  262,  263,
  264,  266,  267,   -1,   49,   -1,  271,   -1,  273,  274,
  258,  276,  277,   31,  279,  280,  281,   -1,  266,  267,
   65,   -1,   -1,  271,   -1,  273,   -1,   -1,  276,  277,
  256,   49,  258,   -1,   -1,   -1,  262,  263,  264,  257,
  258,  258,  260,  261,  262,  263,  264,   65,   -1,  266,
  267,   -1,   -1,   -1,  271,   -1,  273,   -1,   -1,  276,
  277,  256,  184,  258,  186,  110,   -1,  262,  263,  264,
   -1,   -1,  258,  257,  196,   -1,  260,  261,   -1,  257,
  266,  267,  260,  261,   -1,  271,   -1,  273,  274,   -1,
  276,  277,  110,  279,  280,  281,   -1,  258,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  266,  267,  260,  261,   -1,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,   -1,   -1,  260,  261,   -1,  184,
   -1,  186,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  194,
   -1,  196,  197,   -1,   -1,   -1,  257,   -1,   -1,  260,
  261,   -1,  207,   -1,   -1,   -1,  184,   -1,  186,   -1,
   -1,   -1,  258,   -1,   -1,   -1,  194,   -1,  196,  197,
  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,  207,
  276,  277,  258,  279,  280,  281,   -1,   -1,   -1,   -1,
  266,  267,   -1,   -1,  270,   -1,   -1,  273,  274,  258,
   -1,  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,
   -1,   -1,  271,   -1,  273,  274,  258,   -1,  277,   -1,
  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,  271,
   -1,  273,  274,  258,   -1,  277,   -1,  279,  280,  281,
   -1,  266,  267,   -1,   -1,   -1,   -1,   -1,  273,  274,
  258,  276,  277,   -1,  279,  280,  281,   -1,  266,  267,
   -1,   -1,   -1,   -1,   -1,  273,  274,   -1,   -1,  277,
   -1,  279,  280,  281,
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
"condicion : '(' exp_arit exp_arit ')'",
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
"def_triple : TYPEDEF tipo_compuesto '<' tipo '>' ID",
"tipo_compuesto : TRIPLE",
};

//#line 417 "gramatica.y"

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


//#line 631 "Parser.java"
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
//#line 22 "gramatica.y"
{System.out.println("Se detecto: Programa");}
break;
case 2:
//#line 23 "gramatica.y"
{System.out.println("Error, Falta nombre de programa");}
break;
case 3:
//#line 24 "gramatica.y"
{System.out.println("Error de delimitador de programa ");}
break;
case 5:
//#line 28 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 7:
//#line 30 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 9:
//#line 32 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 11:
//#line 34 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 12:
//#line 40 "gramatica.y"
{System.out.println("Se detecto: Sentencia if ");}
break;
case 13:
//#line 41 "gramatica.y"
{System.out.println("Se detecto: Invocacion a funcion " + " en linea: " + lexico.getContadorLinea());}
break;
case 14:
//#line 42 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + " en linea: " + lexico.getContadorLinea());}
break;
case 15:
//#line 43 "gramatica.y"
{System.out.println("Se detecto: Ciclo repeat until ");}
break;
case 16:
//#line 44 "gramatica.y"
{System.out.println("Se detecto: Sentencia GOTO " + " en linea: " + lexico.getContadorLinea());}
break;
case 17:
//#line 45 "gramatica.y"
{System.out.println("Se detecto: Salida " + " en linea: " + lexico.getContadorLinea());}
break;
case 23:
//#line 55 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 24:
//#line 56 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 25:
//#line 64 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion ");}
break;
case 26:
//#line 65 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 27:
//#line 66 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 28:
//#line 67 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 38:
//#line 97 "gramatica.y"
{System.out.println("Error, falta retorno en funcion");}
break;
case 39:
//#line 98 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 40:
//#line 99 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 41:
//#line 100 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 42:
//#line 101 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 44:
//#line 106 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 45:
//#line 107 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 46:
//#line 111 "gramatica.y"
{
            yyval.sval = generador.addTerceto("RETORNO", val_peek(2).sval, null);
        }
break;
case 47:
//#line 116 "gramatica.y"
{
    yyval.sval = generador.addTerceto("INVOCACION", val_peek(3).sval, val_peek(1).sval);
    }
break;
case 49:
//#line 120 "gramatica.y"
{System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
break;
case 50:
//#line 127 "gramatica.y"
{
                    yyval.sval = generador.addTerceto("+", val_peek(2).sval, val_peek(0).sval);
                    System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());
           }
break;
case 51:
//#line 132 "gramatica.y"
{
	                yyval.sval = generador.addTerceto("-", val_peek(2).sval, val_peek(0).sval);
	                System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());
	       }
break;
case 52:
//#line 166 "gramatica.y"
{
                    System.out.println("Error: Falta el término después de '+' en expresion aritmetica en línea: " + lexico.getContadorLinea());
           }
break;
case 53:
//#line 170 "gramatica.y"
{
                    System.out.println("Error: Falta el término después de '-' en expresión aritmetica en línea: " + lexico.getContadorLinea());
           }
break;
case 55:
//#line 183 "gramatica.y"
{
        yyval.sval = val_peek(0).sval;
    }
break;
case 56:
//#line 186 "gramatica.y"
{
	    yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	}
break;
case 57:
//#line 191 "gramatica.y"
{
        yyval.sval = generador.addTerceto("*", val_peek(2).sval, val_peek(0).sval);
        System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());
    }
break;
case 58:
//#line 195 "gramatica.y"
{
        yyval.sval = generador.addTerceto("/", val_peek(2).sval, val_peek(0).sval);
        System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());
	}
break;
case 59:
//#line 199 "gramatica.y"
{
	    yyval.sval = val_peek(0).sval;
	}
break;
case 60:
//#line 203 "gramatica.y"
{System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 204 "gramatica.y"
{System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 62:
//#line 207 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
            System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());
        }
break;
case 63:
//#line 211 "gramatica.y"
{
	    yyval.sval = val_peek(0).sval;
	}
break;
case 64:
//#line 214 "gramatica.y"
{System.out.println("Se detecto: Invocación a función " + "en linea: " + lexico.getContadorLinea());}
break;
case 66:
//#line 218 "gramatica.y"
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
//#line 246 "gramatica.y"
{
            yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
        }
break;
case 68:
//#line 249 "gramatica.y"
{
        yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
    }
break;
case 69:
//#line 254 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
            lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea()));
        }
break;
case 70:
//#line 258 "gramatica.y"
{
        yyval.sval = val_peek(0).sval;
    }
break;
case 71:
//#line 261 "gramatica.y"
{
        yyval.sval = val_peek(0).sval;
    }
break;
case 72:
//#line 266 "gramatica.y"
{
    yyval.sval = val_peek(1).sval;
    }
break;
case 73:
//#line 271 "gramatica.y"
{
        yyval.sval = generador.addTerceto("GOTO", val_peek(0).sval, null);
       }
break;
case 74:
//#line 274 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
case 75:
//#line 277 "gramatica.y"
{
        yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 76:
//#line 281 "gramatica.y"
{
        yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 77:
//#line 285 "gramatica.y"
{System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
break;
case 78:
//#line 286 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 293 "gramatica.y"
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
//#line 303 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 81:
//#line 304 "gramatica.y"
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
//#line 314 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 83:
//#line 315 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 84:
//#line 316 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 319 "gramatica.y"
{
							yyval.sval = generador.addTerceto("BF", val_peek(1).sval, null);
							generador.agregarPila(yyval.sval);
				}
break;
case 86:
//#line 325 "gramatica.y"
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
//#line 341 "gramatica.y"
{
        yyval.sval = generador.addTerceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);
        System.out.println("Se detecto: comparación");}
break;
case 88:
//#line 345 "gramatica.y"
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
//#line 370 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 90:
//#line 371 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 91:
//#line 372 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 92:
//#line 373 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 93:
//#line 374 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 94:
//#line 375 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 95:
//#line 376 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
break;
case 96:
//#line 377 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 97:
//#line 378 "gramatica.y"
{System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
break;
case 98:
//#line 382 "gramatica.y"
{yyval.sval = ">=";}
break;
case 99:
//#line 383 "gramatica.y"
{yyval.sval = "<=";}
break;
case 100:
//#line 384 "gramatica.y"
{yyval.sval = "!=";}
break;
case 101:
//#line 385 "gramatica.y"
{yyval.sval = "=";}
break;
case 102:
//#line 386 "gramatica.y"
{yyval.sval = ">";}
break;
case 103:
//#line 387 "gramatica.y"
{yyval.sval = "<";}
break;
case 104:
//#line 390 "gramatica.y"
{yyval.sval = generador.addTerceto("BT", val_peek(0).sval, generador.obtenerElementoPila());
    generador.eliminarPila();
}
break;
case 105:
//#line 394 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 106:
//#line 395 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 107:
//#line 399 "gramatica.y"
{
    yyval.sval = generador.addTerceto("ET" + generador.getSizeTercetos(), null, null);
    generador.agregarPila('E' + yyval.sval);
}
break;
//#line 1252 "Parser.java"
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
