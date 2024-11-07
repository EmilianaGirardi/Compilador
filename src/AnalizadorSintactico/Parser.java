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
   30,   30,   30,    7,    7,    7,   15,   31,
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
    1,    1,    1,    4,    3,    3,    6,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,   35,   33,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   25,   26,   27,   28,    0,    0,    0,    0,
   31,    0,    0,    0,    0,    0,    0,    0,    0,   69,
   70,   71,    0,   64,    0,    0,   59,   63,   65,    0,
    0,    0,  108,    0,    0,    0,   73,    0,    0,    0,
    4,    6,    0,    0,    0,   86,   83,    0,    0,    0,
    0,   49,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  105,    0,  106,    0,    0,    0,   99,  100,   98,
    0,    0,  101,  102,  103,    0,    0,    0,   85,    0,
    0,    0,   77,    0,    0,   74,   72,    3,    8,   10,
    0,    0,    0,   79,    0,   84,    2,    0,   47,   66,
   32,    0,   21,   18,    0,    0,   20,  104,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   58,    0,   57,    1,   78,   75,   76,    0,    0,   45,
    0,    0,    0,    0,    0,   22,   19,    0,    0,   95,
    0,    0,    0,   52,   53,   93,   61,   60,    0,    0,
    0,   43,    0,   81,    0,    0,    0,   97,    0,    0,
   87,    0,  107,    0,    0,    0,   48,   46,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   42,    0,    0,    0,   40,   96,
    0,   90,   89,   91,   41,   38,    0,   37,   39,   88,
   36,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   44,   19,   20,   21,   22,   38,
   80,   81,   23,   24,   25,   26,   27,   35,  152,  129,
   46,   87,   47,   48,   49,   57,   29,   69,   50,   96,
   54,
};
final static short yysindex[] = {                      -234,
  385,  502,    0,   76, -198,   85,  502,  -17, -245,  -79,
    0,    0,    0,  403,    6,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -243, -168, -118,  468,
    0, -115,   65, -150,   73,  -66,   85,   58,   -9,    0,
    0,    0,   92,    0,  470,   -5,    0,    0,    0, -144,
  485,   74,    0,   78,   86,   80,    0,   96,  102,  126,
    0,    0,  -29,   73, -115,    0,    0, -214,  -94,  127,
  211,    0,  149,   40,   70,  -50,  -31,  197,  200,  519,
    3,    0,   85,    0, -115,  353,   22,    0,    0,    0,
  -82,  342,    0,    0,    0, -115,  430,  559,    0,  203,
  238,  240,    0,  -35, -240,    0,    0,    0,    0,    0,
  244, -232,  211,    0,   17,    0,    0, -115,    0,    0,
    0, -115,    0,    0,  247,   28,    0,    0,  211,  393,
  119, -115, -115,  -48,  250,   -5,  253,   -5,  521,  258,
    0,  260,    0,    0,    0,    0,    0,  271,   45,    0,
   83,  290,   79,  524,  532,    0,    0,  387,  302,    0,
  554,  211,  323,    0,    0,    0,    0,    0,  106,  110,
  325,    0,  113,    0,  331,  330, -115,    0,   95, -115,
    0, -115,    0, -140,  123, -140,    0,    0,   46, -115,
   56,   64,  162, -140,  129, -140, -140,  135,  356,  174,
  369,  376,  379,  158,    0, -108,  159,  164,    0,    0,
  392,    0,    0,    0,    0,    0,  169,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -83,  269,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  128,    0,    0,    0,  -41,    0,
    0,    0,    0,    0,    0,  -16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  313,  354,
    0,    0,    0,  145,    0,    0,    0,  165,    0,    0,
  194,    0,    0,    0,    0,    0,    0,    0,  531,    0,
    0,    0,    0,    0,    0,  190,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  219,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  543,    0,    0,    0,  418,    0,
    0,    0,    0,    0,    0,    9,    0,   34,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  402,    0,  245,    0,    0,    0,    0,    0,    0,    0,
   94,  447,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  111,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  374,    8,  523,    0,  503,    0,    0,    0,    0,  -12,
    0,  -75,    0,    0,    0,    0,   18,  423,  307,   24,
  -18,  -78,   31,    0,  527,    0,    0,  383,   -3,  -70,
    0,
};
final static int YYTABLESIZE=823;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         62,
   62,   62,   62,   62,  126,   62,  130,   91,   33,   92,
  112,   95,   93,   94,   31,  132,   68,   62,   62,   62,
   62,   59,   52,    1,   54,  150,   54,   54,   54,   45,
   33,   63,   53,   82,   84,    2,   98,   59,   11,   12,
   13,   97,   54,   54,   54,   54,   11,   12,   13,   50,
   73,   50,   50,   50,   66,   71,   74,  114,   59,  159,
   45,   45,  134,  163,   61,  133,   86,   50,   50,   50,
   50,   36,  136,  138,   51,  104,   51,   51,   51,  128,
  119,   62,   91,   37,   92,  170,  199,  179,  113,  133,
   65,   34,   51,   51,   51,   51,  201,   43,  189,  133,
  191,  192,  153,  193,  202,   72,   45,  133,  195,  131,
  198,  200,   75,   34,  103,   33,   76,    4,  204,  139,
  207,  208,  148,   99,   43,    5,    6,  141,  143,  151,
  217,   85,    8,    9,  190,   78,   10,  105,   11,   12,
   13,  154,   39,  107,  106,  155,   40,   41,   42,    4,
   66,   36,   94,   67,  108,  161,  162,    5,    6,  160,
  109,   91,  216,   92,    8,    9,  151,   78,   10,   92,
   11,   12,   13,  135,    5,   39,   55,  116,   56,   40,
   41,   42,    5,    5,  110,  117,   30,    5,  118,    5,
    5,   77,    5,    5,  120,    5,    5,    5,   34,    5,
    6,   59,  203,   29,   59,  133,    8,  121,   88,   78,
   10,   89,   90,   59,  211,   62,   62,  133,   62,   62,
   62,   62,   62,   80,   62,   62,   62,   32,  111,   62,
   55,   62,   62,   55,   62,   62,  122,   62,   62,   62,
   54,   54,   62,   54,   54,   54,   54,   54,  147,   54,
   54,   54,   67,   91,   54,   92,   54,   54,  123,   54,
   54,  144,   54,   54,   54,   50,   50,   54,   50,   50,
   50,   50,   50,  127,   50,   50,   50,   68,  145,   50,
  146,   50,   50,  149,   50,   50,   36,   50,   50,   50,
   51,   51,   50,   51,   51,   51,   51,   51,  157,   51,
   51,   51,  150,   82,   51,  156,   51,   51,  164,   51,
   51,  165,   51,   51,   51,   39,  167,   51,  168,   40,
   41,   42,   39,   11,   12,   13,   40,   41,   42,  101,
  173,   39,  169,   31,   32,   40,   41,   42,  102,   83,
  172,  180,   39,   11,   12,   13,   40,   41,   42,   39,
  174,   94,   39,   40,   41,   42,   40,   41,   42,   94,
   94,   94,  182,  183,   94,  185,   94,   94,   92,   94,
   94,  187,   94,   94,   94,   30,   92,   92,   92,  184,
   51,   92,  186,   92,   92,   30,   92,   92,  188,   92,
   92,   92,  196,   30,   30,   91,  210,   92,   30,  205,
   30,   30,   29,   30,   30,  209,   30,   30,   30,  212,
   29,   29,   95,   93,   94,   29,  213,   29,   29,  214,
   29,   29,   80,   29,   29,   29,  177,  178,  215,  218,
   80,   80,  220,  158,  219,   80,  133,   80,   80,  221,
   80,   80,   44,   80,   80,   80,   95,   93,   94,   64,
  115,   67,   95,   93,   94,  171,    0,    0,   55,   67,
   67,   55,    0,    0,   67,    0,   67,   67,    0,   67,
   67,    0,   67,   67,   67,    0,   68,   55,   55,   55,
    0,    0,    0,    0,   68,   68,    0,   56,    0,   68,
   56,   68,   68,    0,   68,   68,    0,   68,   68,   68,
    0,    0,   82,   18,   18,    0,   56,   56,   56,   18,
   82,   82,   91,    0,   92,   82,   18,   82,   82,    0,
   82,   82,    0,   82,   82,   82,    7,   28,   28,   95,
   93,   94,   18,   28,    7,    7,   60,    0,   18,    7,
   28,    7,    7,    0,    7,    7,    0,    7,    7,    7,
    0,    0,   60,   18,    0,    0,   28,  194,   79,  197,
    0,  166,   28,   91,  175,   92,   91,    0,   92,  206,
    9,    0,  176,   60,   91,    0,   92,   28,    9,    9,
    0,    0,   18,    9,    0,    9,    9,    0,    9,    9,
    0,    9,    9,    9,  181,    0,   91,  137,   92,   39,
    0,    0,  125,   40,   41,   42,   28,    0,    0,   88,
   39,   11,   89,   90,   40,   41,   42,    0,    0,   11,
   11,    0,    0,    0,   11,    0,   11,   11,    0,   11,
   11,    0,   11,   11,   11,    0,    0,    0,    0,    0,
    0,    0,    4,   88,    0,    0,   89,   90,    0,   88,
    5,    6,   89,   90,    7,    0,    0,    8,    9,    0,
    4,   10,    0,   11,   12,   13,    0,    0,    5,    6,
    0,    0,    0,   58,   55,    8,    9,   55,   55,   10,
    0,   11,   12,   13,    0,  140,   18,   39,   18,    0,
    0,   40,   41,   42,    0,    0,   18,    0,   18,   18,
    0,    0,    0,   56,    0,    0,   56,   56,   18,    0,
   28,    0,   28,    0,    0,    0,   60,    0,    0,   60,
   28,    0,   28,   28,    0,    4,   88,    0,   60,   89,
   90,    0,   28,    5,    6,    0,    0,    0,   70,    0,
    8,    9,    4,    0,   10,    0,   11,   12,   13,    0,
    5,    6,    0,    0,    0,  100,    0,    8,    9,    4,
    0,   10,    0,   11,   12,   13,    0,    5,    6,    0,
    0,    0,    0,    0,    8,    9,   77,    0,   10,    0,
   11,   12,   13,    0,    5,    6,    0,    0,   23,  124,
    0,    8,    0,    0,   78,   10,   23,   23,    0,    0,
   24,   23,    0,   23,    0,    0,   23,   23,   24,   24,
    0,    0,    0,   24,  142,   24,   39,    0,   24,   24,
   40,   41,   42,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   80,   47,   85,   43,   40,   45,
   40,   60,   61,   62,  258,   86,   29,   59,   60,   61,
   62,   14,   40,  258,   41,  258,   43,   44,   45,    6,
   40,  275,  278,   37,   38,  270,   42,   30,  279,  280,
  281,   47,   59,   60,   61,   62,  279,  280,  281,   41,
   33,   43,   44,   45,  269,   32,   33,  272,   51,  130,
   37,   38,   41,  134,   59,   44,   43,   59,   60,   61,
   62,  270,   91,   92,   41,   52,   43,   44,   45,   83,
   41,   59,   43,  282,   45,   41,   41,  158,   65,   44,
  259,  123,   59,   60,   61,   62,   41,   40,  177,   44,
  179,  180,  115,  182,   41,   41,   83,   44,  184,   86,
  186,  190,  263,  123,   41,   40,   44,  258,  194,   96,
  196,  197,  105,  268,   40,  266,  267,   97,   98,  112,
  206,   40,  273,  274,   40,  276,  277,   60,  279,  280,
  281,  118,  258,   64,   59,  122,  262,  263,  264,  258,
  269,  270,   59,  272,   59,  132,  133,  266,  267,   41,
   59,   43,  271,   45,  273,  274,  149,  276,  277,   59,
  279,  280,  281,  256,  258,  258,  256,  272,  258,  262,
  263,  264,  266,  267,   59,   59,   59,  271,   40,  273,
  274,  258,  276,  277,  125,  279,  280,  281,  123,  266,
  267,  194,   41,   59,  197,   44,  273,  258,  257,  276,
  277,  260,  261,  206,   41,  257,  258,   44,  260,  261,
  262,  263,  264,   59,  266,  267,  268,  259,  258,  271,
   41,  273,  274,   44,  276,  277,   40,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,  284,  266,
  267,  268,   59,   43,  271,   45,  273,  274,   59,  276,
  277,   59,  279,  280,  281,  257,  258,  284,  260,  261,
  262,  263,  264,  271,  266,  267,  268,   59,   41,  271,
   41,  273,  274,   40,  276,  277,  270,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,  271,  266,
  267,  268,  258,   59,  271,   59,  273,  274,   59,  276,
  277,   59,  279,  280,  281,  258,   59,  284,   59,  262,
  263,  264,  258,  279,  280,  281,  262,  263,  264,  256,
   41,  258,   62,  258,  259,  262,  263,  264,  265,  282,
  258,   40,  258,  279,  280,  281,  262,  263,  264,  258,
  272,  258,  258,  262,  263,  264,  262,  263,  264,  266,
  267,  268,   40,  258,  271,   41,  273,  274,  258,  276,
  277,   41,  279,  280,  281,    2,  266,  267,  268,  270,
    7,  271,  270,  273,  274,  258,  276,  277,   59,  279,
  280,  281,  270,  266,  267,   43,   41,   45,  271,  271,
  273,  274,  258,  276,  277,  271,  279,  280,  281,   41,
  266,  267,   60,   61,   62,  271,   41,  273,  274,   41,
  276,  277,  258,  279,  280,  281,   40,   41,  271,  271,
  266,  267,   41,   41,  271,  271,   44,  273,  274,  271,
  276,  277,   41,  279,  280,  281,   60,   61,   62,   27,
   68,  258,   60,   61,   62,  149,   -1,   -1,   41,  266,
  267,   44,   -1,   -1,  271,   -1,  273,  274,   -1,  276,
  277,   -1,  279,  280,  281,   -1,  258,   60,   61,   62,
   -1,   -1,   -1,   -1,  266,  267,   -1,   41,   -1,  271,
   44,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
   -1,   -1,  258,    1,    2,   -1,   60,   61,   62,    7,
  266,  267,   43,   -1,   45,  271,   14,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,  258,    1,    2,   60,
   61,   62,   30,    7,  266,  267,   14,   -1,   36,  271,
   14,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
   -1,   -1,   30,   51,   -1,   -1,   30,  184,   36,  186,
   -1,   41,   36,   43,   41,   45,   43,   -1,   45,  196,
  258,   -1,   41,   51,   43,   -1,   45,   51,  266,  267,
   -1,   -1,   80,  271,   -1,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,   41,   -1,   43,  256,   45,  258,
   -1,   -1,   80,  262,  263,  264,   80,   -1,   -1,  257,
  258,  258,  260,  261,  262,  263,  264,   -1,   -1,  266,
  267,   -1,   -1,   -1,  271,   -1,  273,  274,   -1,  276,
  277,   -1,  279,  280,  281,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  258,  257,   -1,   -1,  260,  261,   -1,  257,
  266,  267,  260,  261,  270,   -1,   -1,  273,  274,   -1,
  258,  277,   -1,  279,  280,  281,   -1,   -1,  266,  267,
   -1,   -1,   -1,  271,  257,  273,  274,  260,  261,  277,
   -1,  279,  280,  281,   -1,  256,  184,  258,  186,   -1,
   -1,  262,  263,  264,   -1,   -1,  194,   -1,  196,  197,
   -1,   -1,   -1,  257,   -1,   -1,  260,  261,  206,   -1,
  184,   -1,  186,   -1,   -1,   -1,  194,   -1,   -1,  197,
  194,   -1,  196,  197,   -1,  258,  257,   -1,  206,  260,
  261,   -1,  206,  266,  267,   -1,   -1,   -1,  271,   -1,
  273,  274,  258,   -1,  277,   -1,  279,  280,  281,   -1,
  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,  258,
   -1,  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,
   -1,   -1,   -1,   -1,  273,  274,  258,   -1,  277,   -1,
  279,  280,  281,   -1,  266,  267,   -1,   -1,  258,  271,
   -1,  273,   -1,   -1,  276,  277,  266,  267,   -1,   -1,
  258,  271,   -1,  273,   -1,   -1,  276,  277,  266,  267,
   -1,   -1,   -1,  271,  256,  273,  258,   -1,  276,  277,
  262,  263,  264,
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
"repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL condicion",
"repeat_until : REPEAT UNTIL condicion",
"repeat_until : REPEAT bloque_sentencias_ejecutables condicion",
"def_triple : TYPEDEF tipo_compuesto '<' tipo '>' ID",
"tipo_compuesto : TRIPLE",
};

//#line 408 "gramatica.y"

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


//#line 624 "Parser.java"
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
case 105:
//#line 391 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 106:
//#line 392 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
//#line 1232 "Parser.java"
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
