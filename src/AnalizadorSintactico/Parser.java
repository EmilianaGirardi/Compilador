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
   20,   20,   22,   22,   21,   21,   21,   23,   23,   23,
   23,   23,   23,   23,   24,    6,    6,   25,    8,    8,
    9,    9,    9,    9,    4,    4,    4,    4,    4,    4,
   26,   27,   28,   28,   28,   28,   28,   28,   28,   28,
   28,   28,   29,   29,   29,   29,   29,   29,    7,    7,
    7,   30,   15,   31,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    3,    4,    3,
    2,    3,    1,    2,    1,    1,    1,    1,    2,    2,
    1,    3,    1,    1,    1,   10,    9,    9,    9,    8,
    9,    8,    2,    1,    1,    5,    4,    7,    3,    3,
    3,    1,    1,    3,    3,    3,    1,    1,    1,    1,
    1,    1,    1,    2,    4,    3,    3,    2,    2,    3,
    4,    4,    3,    4,    3,    2,    5,    4,    2,    3,
    3,    1,    5,    9,    8,    8,    8,    8,    4,    4,
    8,    5,    1,    1,    1,    1,    1,    1,    4,    3,
    3,    1,    6,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,  102,    0,    0,    0,    0,    0,
   34,   35,   33,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   25,   26,   27,   28,    0,    0,    0,    0,
    0,   31,    0,    0,    0,    0,    0,   61,   62,   63,
    0,    0,   59,    0,    0,   57,   60,    0,    0,    0,
  104,    0,    0,    0,   69,    0,    0,    0,    4,    6,
    0,    0,    0,   82,    0,   79,    0,    0,    0,    0,
    0,    0,   49,    0,    0,    0,    0,    0,    0,    0,
   64,   94,   95,   93,    0,    0,   96,   97,   98,    0,
    0,    0,   81,    0,    0,    0,   73,    0,    0,   70,
   68,    3,    8,   10,    0,    0,    0,    0,    0,    0,
    0,    0,   75,    0,   80,  100,    0,  101,    2,    0,
   47,   65,   32,    0,    0,    0,    0,    0,    0,    0,
    0,   55,   56,    1,   74,   71,   72,    0,    0,   45,
    0,    0,    0,   21,   18,    0,    0,   20,    0,   99,
    0,    0,    0,    0,    0,    0,   89,    0,    0,    0,
   43,    0,    0,   22,   19,   77,    0,    0,   92,    0,
    0,   83,    0,  103,    0,    0,    0,    0,   48,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   46,
    0,    0,    0,    0,    0,    0,   42,    0,    0,    0,
   40,   91,    0,   86,   85,   87,   41,   38,    0,   37,
   39,   84,   36,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   43,   19,   20,   21,   22,   67,
  111,  112,   23,   24,   25,   26,   27,   36,  142,  124,
   45,   80,   46,   47,   55,   29,   68,   48,   90,   30,
   52,
};
final static short yysindex[] = {                      -218,
  -96,  291,    0,   -3,    0,   85,  291,   -4, -236, -195,
    0,    0,    0,  454,    9,   33,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -234, -204, -237, -217,
  471,    0,   14,   71, -150,   77,  -27,    0,    0,    0,
   95, -139,    0,   84,   -8,    0,    0, -140,  488,   74,
    0,   72,   80,   69,    0,   82,   94,  100,    0,    0,
  -24,   77,   14,    0,  536,    0, -181, -118,   85,   64,
  109,   34,    0,  127,   21,   50,  -82,   14,   84,   53,
    0,    0,    0,    0,   14,   14,    0,    0,    0,   14,
   14,   14,    0,  123,  145,  147,    0,  -38,  236,    0,
    0,    0,    0,    0,  153, -230,   34,  -28,  157,  143,
  263,  -67,    0,  -58,    0,    0,   85,    0,    0,   14,
    0,    0,    0,   34,  440,   14,   14,  -52,   -8,   -8,
   26,    0,    0,    0,    0,    0,    0,  152,  -30,    0,
  -48,  174,   14,    0,    0,  159,  -50,    0,  -43,    0,
   35,  139,  197,  146,   34,  208,    0,  -36,   -9,  223,
    0,   -2,  151,    0,    0,    0,  241,   14,    0,   98,
   14,    0,   14,    0,  505,   15,  505,  224,    0,   62,
   14,   70,  108,  128,  505,   17,  505,  505,   20,    0,
  254,  154,  258,  262,  268,   39,    0, -116,   41,   47,
    0,    0,  283,    0,    0,    0,    0,    0,   54,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  319,  339,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  131,  -41,    0,    0,    0,
    0,    0,    0,    0,  -14,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  369,  386,    0,    0,
    0,  148,    0,    0,    0,    0,  165,    0,    0,    0,
    0,  182,    0,    0,    0,    0,    0,    0,  162,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  199,    0,    0,  522,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  447,    0,    0,    0,    0,   13,   40,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  290,    0,    0,    0,    0,  534,    0,    0,  216,    0,
    0,    0,    0,   97,  462,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  114,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   36,  117,  403,    0,  497,    0,    0,    0,    0,  -16,
    0,  -51,    0,    0,    0,    0,  -17,  277,  201,  504,
  -60,  -63,   -5,  511,    0,    0,  275,  -47,  -35,    0,
    0,
};
final static int YYTABLESIZE=813;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         58,
   58,   58,   58,   58,   85,   58,   86,   89,   87,   88,
  159,   34,   34,   70,  125,  106,   74,   58,   58,   58,
   58,  116,  118,   32,  129,  130,   52,  140,   52,   52,
   52,   64,   65,   91,   66,   50,   34,   31,   92,    1,
   61,   51,   49,  126,   52,   52,   52,   52,   11,   12,
   13,    2,   65,   50,   63,   50,   50,   50,   42,  147,
   53,  121,   54,   85,   69,   86,  157,   59,   85,  150,
   86,   50,   50,   50,   50,  167,   85,   85,   86,   86,
   51,  138,   51,   51,   51,  132,  133,   64,  141,  153,
  113,   60,  156,  128,   35,   35,  127,  149,   51,   51,
   51,   51,  191,   41,  180,  127,  182,  183,   42,  184,
  193,   73,   76,  127,   97,   42,  170,  192,   42,   35,
   77,  141,   81,  186,   41,  189,   85,   93,   86,   42,
   57,   99,  101,  196,   78,  199,  200,  181,  100,   42,
  102,    4,   42,   89,   87,   88,  209,   57,  194,    5,
    6,  127,  103,  115,  208,   90,    8,    9,  104,  109,
   10,    4,   11,   12,   13,   57,  120,  119,  195,    5,
    6,  127,   88,    7,  122,  123,    8,    9,  168,  169,
   10,  134,   11,   12,   13,  135,  172,  136,   85,   30,
   86,  178,  139,   85,  203,   86,  143,  127,   89,   87,
   88,  144,   53,  148,   82,   53,   29,   83,   84,  161,
  185,   65,  188,  158,  162,   58,   58,  164,   58,   58,
  165,  174,  198,   76,   58,   58,   58,  140,  166,   58,
   33,   58,   58,  105,   58,   58,  171,   58,   58,   58,
   66,   58,   52,   52,  137,   52,   52,  173,   11,   12,
   13,   52,   52,   52,   32,   33,   52,   67,   52,   52,
  175,   52,   52,  176,   52,   52,   52,  177,   52,   50,
   50,   37,   50,   50,   78,   38,   39,   40,   50,   50,
   50,  179,  190,   50,  187,   50,   50,  197,   50,   50,
  201,   50,   50,   50,  202,   50,   51,   51,  204,   51,
   51,   57,  205,   62,   57,   51,   51,   51,  206,  207,
   51,  210,   51,   51,   57,   51,   51,  211,   51,   51,
   51,   37,   51,  212,  213,   38,   39,   40,   37,   95,
   44,   37,   38,   39,   40,   38,   39,   40,   96,  160,
   82,  114,   37,   83,   84,  117,   38,   39,   40,   11,
   12,   13,   37,    0,   90,   37,   38,   39,   40,   38,
   39,   40,   90,   90,   90,    0,    0,   90,    0,   90,
   90,   88,   90,   90,    0,   90,   90,   90,    0,   88,
   88,   88,    0,    0,   88,    0,   88,   88,   30,   88,
   88,    0,   88,   88,   88,   82,   30,   30,   83,   84,
    0,   30,    0,   30,   30,   29,   30,   30,    0,   30,
   30,   30,    0,   29,   29,    0,   58,    0,   29,    0,
   29,   29,   76,   29,   29,    0,   29,   29,   29,    0,
   76,   76,    0,   58,    0,   76,    0,   76,   76,   66,
   76,   76,    0,   76,   76,   76,    0,   66,   66,    0,
    0,   58,   66,    0,   66,   66,   67,   66,   66,    0,
   66,   66,   66,    0,   67,   67,    0,  110,    0,   67,
    0,   67,   67,   78,   67,   67,    0,   67,   67,   67,
  152,   78,   78,  127,    0,    0,   78,   53,   78,   78,
   53,   78,   78,    0,   78,   78,   78,   18,   18,   89,
   87,   88,   54,   18,    0,   54,   53,   53,   53,   44,
   18,   28,   28,  146,   11,   12,   13,   28,    0,    0,
  108,   54,   54,   54,   28,    0,    0,   18,    5,    6,
    0,    0,    0,  145,    0,    8,   72,   75,  109,   10,
    0,   28,    0,    0,   79,   18,    0,    0,    4,    0,
    0,    0,    0,   98,    0,    0,    5,    6,    0,   28,
    0,   18,    0,    8,    9,    0,  107,   10,    0,   11,
   12,   13,   44,   44,    0,   28,    5,    0,    0,    0,
    0,    0,    0,    0,    5,    5,    0,   58,    0,    5,
   58,    5,    5,  131,    5,    5,    7,    5,    5,    5,
   58,    0,    0,    0,    7,    7,    0,   18,    0,    7,
    0,    7,    7,    0,    7,    7,    0,    7,    7,    7,
   44,   28,    0,  151,    0,    0,    9,    0,    0,  154,
  155,    0,    0,    0,    9,    9,    0,    0,    0,    9,
    0,    9,    9,   11,    9,    9,  163,    9,    9,    9,
    0,   11,   11,    0,    0,    0,   11,    0,   11,   11,
    0,   11,   11,    0,   11,   11,   11,    0,    0,    0,
    0,   18,    0,   18,    0,    0,    0,    0,    0,    0,
    0,   18,    0,   18,   18,   28,    0,   28,    0,    0,
    0,    0,    0,    0,   18,   28,   82,   28,   28,   83,
   84,    0,    0,   53,    0,    0,   53,   53,   28,    0,
    0,    4,    0,    0,    0,    0,    0,    0,   54,    5,
    6,   54,   54,    0,   56,    0,    8,    9,    4,    0,
   10,    0,   11,   12,   13,    0,    5,    6,    0,    0,
    0,   71,    0,    8,    9,    4,    0,   10,    0,   11,
   12,   13,    0,    5,    6,    0,    0,    0,   94,    0,
    8,    9,    4,    0,   10,    0,   11,   12,   13,    0,
    5,    6,    0,    0,    0,    0,    0,    8,    9,   23,
  109,   10,    0,   11,   12,   13,    0,   23,   23,    0,
    0,   24,   23,  108,   23,    0,    0,   23,   23,   24,
   24,    5,    6,    0,   24,    0,   24,    0,    8,   24,
   24,  109,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   43,   47,   45,   60,   61,   62,
   41,   40,   40,   30,   78,   40,   34,   59,   60,   61,
   62,   69,   70,  258,   85,   86,   41,  258,   43,   44,
   45,  269,  270,   42,  272,   40,   40,    2,   47,  258,
  275,  278,    7,   79,   59,   60,   61,   62,  279,  280,
  281,  270,  270,   41,  259,   43,   44,   45,   45,  111,
  256,   41,  258,   43,  282,   45,   41,   59,   43,  117,
   45,   59,   60,   61,   62,   41,   43,   43,   45,   45,
   41,   99,   43,   44,   45,   91,   92,  269,  106,  125,
  272,   59,  128,   41,  123,  123,   44,  114,   59,   60,
   61,   62,   41,   40,  168,   44,  170,  171,   45,  173,
   41,   41,  263,   44,   41,   45,  152,  181,   45,  123,
   44,  139,  262,  175,   40,  177,   43,  268,   45,   45,
   14,   60,   64,  185,   40,  187,  188,   40,   59,   45,
   59,  258,   45,   60,   61,   62,  198,   31,   41,  266,
  267,   44,   59,  272,  271,   59,  273,  274,   59,  276,
  277,  258,  279,  280,  281,   49,   40,   59,   41,  266,
  267,   44,   59,  270,  125,  258,  273,  274,   40,   41,
  277,   59,  279,  280,  281,   41,   41,   41,   43,   59,
   45,   41,   40,   43,   41,   45,   40,   44,   60,   61,
   62,   59,   41,  271,  257,   44,   59,  260,  261,  258,
  175,  270,  177,   62,   41,  257,  258,   59,  260,  261,
  271,  258,  187,   59,  266,  267,  268,  258,  272,  271,
  259,  273,  274,  258,  276,  277,   40,  279,  280,  281,
   59,  283,  257,  258,  283,  260,  261,   40,  279,  280,
  281,  266,  267,  268,  258,  259,  271,   59,  273,  274,
  270,  276,  277,   41,  279,  280,  281,  270,  283,  257,
  258,  258,  260,  261,   59,  262,  263,  264,  266,  267,
  268,   41,   59,  271,  270,  273,  274,  271,  276,  277,
  271,  279,  280,  281,   41,  283,  257,  258,   41,  260,
  261,  185,   41,   27,  188,  266,  267,  268,   41,  271,
  271,  271,  273,  274,  198,  276,  277,  271,  279,  280,
  281,  258,  283,   41,  271,  262,  263,  264,  258,  256,
   41,  258,  262,  263,  264,  262,  263,  264,  265,  139,
  257,   67,  258,  260,  261,  282,  262,  263,  264,  279,
  280,  281,  258,   -1,  258,  258,  262,  263,  264,  262,
  263,  264,  266,  267,  268,   -1,   -1,  271,   -1,  273,
  274,  258,  276,  277,   -1,  279,  280,  281,   -1,  266,
  267,  268,   -1,   -1,  271,   -1,  273,  274,  258,  276,
  277,   -1,  279,  280,  281,  257,  266,  267,  260,  261,
   -1,  271,   -1,  273,  274,  258,  276,  277,   -1,  279,
  280,  281,   -1,  266,  267,   -1,   14,   -1,  271,   -1,
  273,  274,  258,  276,  277,   -1,  279,  280,  281,   -1,
  266,  267,   -1,   31,   -1,  271,   -1,  273,  274,  258,
  276,  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,
   -1,   49,  271,   -1,  273,  274,  258,  276,  277,   -1,
  279,  280,  281,   -1,  266,  267,   -1,   65,   -1,  271,
   -1,  273,  274,  258,  276,  277,   -1,  279,  280,  281,
   41,  266,  267,   44,   -1,   -1,  271,   41,  273,  274,
   44,  276,  277,   -1,  279,  280,  281,    1,    2,   60,
   61,   62,   41,    7,   -1,   44,   60,   61,   62,    6,
   14,    1,    2,  111,  279,  280,  281,    7,   -1,   -1,
  258,   60,   61,   62,   14,   -1,   -1,   31,  266,  267,
   -1,   -1,   -1,  271,   -1,  273,   33,   34,  276,  277,
   -1,   31,   -1,   -1,   41,   49,   -1,   -1,  258,   -1,
   -1,   -1,   -1,   50,   -1,   -1,  266,  267,   -1,   49,
   -1,   65,   -1,  273,  274,   -1,   63,  277,   -1,  279,
  280,  281,   69,   70,   -1,   65,  258,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  266,  267,   -1,  185,   -1,  271,
  188,  273,  274,   90,  276,  277,  258,  279,  280,  281,
  198,   -1,   -1,   -1,  266,  267,   -1,  111,   -1,  271,
   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
  117,  111,   -1,  120,   -1,   -1,  258,   -1,   -1,  126,
  127,   -1,   -1,   -1,  266,  267,   -1,   -1,   -1,  271,
   -1,  273,  274,  258,  276,  277,  143,  279,  280,  281,
   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,
   -1,  276,  277,   -1,  279,  280,  281,   -1,   -1,   -1,
   -1,  175,   -1,  177,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  185,   -1,  187,  188,  175,   -1,  177,   -1,   -1,
   -1,   -1,   -1,   -1,  198,  185,  257,  187,  188,  260,
  261,   -1,   -1,  257,   -1,   -1,  260,  261,  198,   -1,
   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,  257,  266,
  267,  260,  261,   -1,  271,   -1,  273,  274,  258,   -1,
  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,
   -1,  271,   -1,  273,  274,  258,   -1,  277,   -1,  279,
  280,  281,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,
  273,  274,  258,   -1,  277,   -1,  279,  280,  281,   -1,
  266,  267,   -1,   -1,   -1,   -1,   -1,  273,  274,  258,
  276,  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,
   -1,  258,  271,  258,  273,   -1,   -1,  276,  277,  266,
  267,  266,  267,   -1,  271,   -1,  273,   -1,  273,  276,
  277,  276,  277,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=283;
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
"TRIPLE","TIPO_UNSIGNED","TIPO_SINGLE","TIPO_OCTAL","UNTIL","\") \"",
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
"exp_arit : termino",
"lista_exp_arit : exp_arit",
"lista_exp_arit : lista_exp_arit ',' exp_arit",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
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

//#line 388 "gramatica.y"

private Lexico lexico;
private Generador generador;
private final Float infPositivo = 1.17549435e-38f;
private final Float supPositivo = 3.40282347e38f;
private final Float infNegativo = -3.40282347e38f;
private final Float supNegativo = -1.17549435e-38f;

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


//#line 614 "Parser.java"
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
case 38:
//#line 94 "gramatica.y"
{System.out.println("Error, falta retorno en funcion");}
break;
case 39:
//#line 95 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 40:
//#line 96 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 41:
//#line 97 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 42:
//#line 98 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 44:
//#line 103 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 45:
//#line 104 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 46:
//#line 108 "gramatica.y"
{
            yyval.sval = generador.addTerceto("RETORNO", val_peek(2).sval, null);
        }
break;
case 47:
//#line 113 "gramatica.y"
{
    yyval.sval = generador.addTerceto("INVOCACION", val_peek(3).sval, val_peek(1).sval);
    }
break;
case 49:
//#line 117 "gramatica.y"
{System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
break;
case 50:
//#line 124 "gramatica.y"
{
                    yyval.sval = generador.addTerceto("+", val_peek(2).sval, val_peek(0).sval);
                    System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());
           }
break;
case 51:
//#line 129 "gramatica.y"
{
	                yyval.sval = generador.addTerceto("-", val_peek(2).sval, val_peek(0).sval);
	                System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());
	       }
break;
case 53:
//#line 148 "gramatica.y"
{
        yyval.sval = val_peek(0).sval;
    }
break;
case 54:
//#line 151 "gramatica.y"
{
	    yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	}
break;
case 55:
//#line 156 "gramatica.y"
{
        yyval.sval = generador.addTerceto("*", val_peek(2).sval, val_peek(0).sval);
        System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());
    }
break;
case 56:
//#line 160 "gramatica.y"
{
        yyval.sval = generador.addTerceto("/", val_peek(2).sval, val_peek(0).sval);
        System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());
	}
break;
case 57:
//#line 164 "gramatica.y"
{
	    yyval.sval = val_peek(0).sval;
	}
break;
case 58:
//#line 173 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
            System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());
        }
break;
case 59:
//#line 181 "gramatica.y"
{System.out.println("Se detecto: Invocación a función " + "en linea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 183 "gramatica.y"
{
                yyval.sval = truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea());
                lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, yyval.sval);
            }
break;
case 62:
//#line 187 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 63:
//#line 190 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 64:
//#line 193 "gramatica.y"
{
        yyval.sval = truncarFueraRango("-"+val_peek(0).sval, lexico.getContadorLinea());
        lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 65:
//#line 200 "gramatica.y"
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
case 66:
//#line 228 "gramatica.y"
{
            yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
        }
break;
case 67:
//#line 231 "gramatica.y"
{
        yyval.sval = generador.addTerceto(":=", val_peek(2).sval, val_peek(0).sval);
    }
break;
case 68:
//#line 237 "gramatica.y"
{
    yyval.sval = val_peek(1).sval;
    }
break;
case 69:
//#line 242 "gramatica.y"
{
        yyval.sval = generador.addTerceto("GOTO", val_peek(0).sval, null);
       }
break;
case 70:
//#line 245 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
case 71:
//#line 248 "gramatica.y"
{
        yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 72:
//#line 252 "gramatica.y"
{
        yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 73:
//#line 256 "gramatica.y"
{System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
break;
case 74:
//#line 257 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 75:
//#line 264 "gramatica.y"
{
							int pos = Integer.parseInt(generador.obtenerElementoPila().split("T")[1]);
							generador.eliminarPila();
							Terceto t = generador.getTerceto(pos);
							String label = "ET"+generador.getSizeTercetos();

							t.setTercerParametro(label);
							yyval.sval=generador.addTerceto(label, null, null);
			}
break;
case 76:
//#line 274 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 77:
//#line 275 "gramatica.y"
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
case 78:
//#line 285 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 286 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 80:
//#line 287 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 81:
//#line 290 "gramatica.y"
{
							yyval.sval = generador.addTerceto("BF", val_peek(1).sval, null);
							generador.agregarPila(yyval.sval);
				}
break;
case 82:
//#line 296 "gramatica.y"
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
case 83:
//#line 312 "gramatica.y"
{
        yyval.sval = generador.addTerceto(val_peek(2).sval, val_peek(3).sval, val_peek(1).sval);
        System.out.println("Se detecto: comparación");}
break;
case 84:
//#line 316 "gramatica.y"
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
case 85:
//#line 341 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 86:
//#line 342 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 87:
//#line 343 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 88:
//#line 344 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 89:
//#line 345 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 90:
//#line 346 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 91:
//#line 348 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 92:
//#line 349 "gramatica.y"
{System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
break;
case 93:
//#line 353 "gramatica.y"
{yyval.sval = ">=";}
break;
case 94:
//#line 354 "gramatica.y"
{yyval.sval = "<=";}
break;
case 95:
//#line 355 "gramatica.y"
{yyval.sval = "!=";}
break;
case 96:
//#line 356 "gramatica.y"
{yyval.sval = "=";}
break;
case 97:
//#line 357 "gramatica.y"
{yyval.sval = ">";}
break;
case 98:
//#line 358 "gramatica.y"
{yyval.sval = "<";}
break;
case 99:
//#line 361 "gramatica.y"
{yyval.sval = generador.addTerceto("BT", val_peek(0).sval, generador.obtenerElementoPila());
    generador.eliminarPila();
}
break;
case 100:
//#line 365 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 101:
//#line 366 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 102:
//#line 370 "gramatica.y"
{
    yyval.sval = generador.addTerceto("ET" + generador.getSizeTercetos(), null, null);
    generador.agregarPila('E' + yyval.sval);
}
break;
//#line 1212 "Parser.java"
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
