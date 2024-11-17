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
   17,   19,   19,   18,   18,   18,   20,   20,   20,   20,
   20,   14,   14,   14,   13,    5,    5,    5,    5,   21,
   21,   21,   21,   21,   23,   23,   22,   22,   22,   22,
   22,   24,   24,   24,   24,   24,   24,   24,   25,    6,
    6,   10,    8,    8,    9,    9,    9,    9,    4,    4,
    4,    4,    4,    4,   26,   27,   28,   28,   28,   28,
   28,   28,   28,   28,   28,   28,   29,   29,   29,   29,
   29,   29,    7,    7,    7,   30,   16,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    1,    3,    4,
    3,    2,    3,    1,    2,    1,    1,    1,    1,    2,
    2,    1,    3,    1,    1,    1,    7,    6,    5,    6,
    6,    5,    4,    4,    5,    4,    5,    7,    3,    3,
    3,    4,    4,    1,    1,    3,    3,    3,    1,    4,
    4,    1,    1,    1,    1,    1,    1,    2,    4,    3,
    3,    2,    3,    3,    4,    4,    3,    4,    3,    2,
    5,    4,    2,    3,    3,    1,    5,    9,    8,    8,
    8,    8,    4,    4,    8,    5,    1,    1,    1,    1,
    1,    1,    4,    3,    3,    1,    6,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,  106,    0,    0,    0,    0,    0,
   35,   36,   34,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   18,   26,   27,   28,   29,    0,    0,    0,
    0,    0,    0,   32,    0,    0,    0,   72,    0,    0,
   65,   66,   67,    0,    0,   63,    0,    0,   59,   64,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    4,
    6,    0,    0,    0,    0,   86,    0,   83,    0,    0,
    0,    0,    0,    0,   49,    0,    0,    0,    0,    0,
    0,    0,   68,   98,   99,   97,    0,    0,  100,  101,
  102,    0,    0,    0,   85,    0,    0,    0,   77,    0,
    0,   74,   73,    3,    8,   10,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   79,    0,   84,  104,
    0,  105,    2,    0,    0,   46,   69,   33,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   57,
    0,   58,    1,   78,   75,   76,    0,    0,    0,    0,
   44,    0,   43,   22,   19,    0,    0,   21,    0,  103,
    0,   47,    0,    0,    0,    0,    0,   52,   53,   93,
   60,   61,    0,    0,   39,    0,    0,    0,   42,   23,
   20,   81,    0,    0,   96,    0,    0,   87,    0,  107,
   41,    0,   40,   38,    0,   48,    0,    0,    0,    0,
    0,   37,   45,    0,    0,    0,    0,    0,   95,    0,
   90,   89,   91,   88,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   46,   19,   20,   21,   22,   23,
   69,  115,  111,   24,   25,   26,   27,   28,   39,   29,
  129,   48,   82,   49,   50,   31,   70,   51,   92,   32,
};
final static short yysindex[] = {                      -223,
  -84,  529,    0,  -39,    0,   93,  529,   19, -268, -121,
    0,    0,    0,  461,    7,   12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -234, -187, -168,
  -78, -222,  478,    0,  -29,   69, -142,    0,   51,  -22,
    0,    0,    0,   96, -137,    0,  503,  -14,    0,    0,
 -115,  495,   82,   97,  113,  115,  118,  125,  126,    0,
    0,  -33,   51,  512,  -29,    0,  315,    0, -266, -105,
   93,  -40,  141,   99,    0,  103,   31,   79,  -45,  -29,
  503,   -7,    0,    0,    0,    0,  275,  278,    0,    0,
    0,  -29,  289,  298,    0,  155,  165,  175,    0,   72,
  -69,    0,    0,    0,    0,    0,  177,  -69,  199, -111,
  -31,   99,  -38,  186,  546,  -23,    0,  -16,    0,    0,
   93,    0,    0,  -29,   75,    0,    0,    0,   99,  397,
  -29,  -29,  364,  200,  -14,  201,  -14,   89,  205,    0,
  206,    0,    0,    0,    0,    0,  196,   56,    9,  -29,
    0,   -1,    0,    0,    0,  214,    8,    0,   11,    0,
  130,    0,  147,  244,  135,   99,  245,    0,    0,    0,
    0,    0,   32,  251,    0,  -32,  254,  158,    0,    0,
    0,    0,  257,  -29,    0,  106,  -29,    0,  -29,    0,
    0,  263,    0,    0,  230,    0,   34,  -29,   68,   78,
  108,    0,    0,  267,  117,  268,  269,  274,    0,  276,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  331,  389,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  139,  -30,
    0,    0,    0,    0,    0,    0,    0,   -5,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  420,  444,    0,
    0,    0,  156,    0,    0,    0,    0,    0,  173,    0,
    0,    0,    0,  198,    0,    0,    0,    0,    0,    0,
  161,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  223,    0,  558,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  422,    0,
    0,    0,    0,    0,   20,    0,   45,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  572,    0,    0,  248,    0,
    0,    0,    0,    0,  105,  447,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  122,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   60,  -10,   35,    0,   44,    0,    0,    0,    0,    0,
  -24,    0,  -17,    0,    0,    0,    0,   -9,  300,    0,
  514,  -44,  -58,  -41,  570,    0,  260,  -52,  -60,    0,
};
final static int YYTABLESIZE=849;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
   36,   36,   66,   58,   45,  117,  108,   72,  193,   54,
   62,   62,   62,   62,   62,   45,   62,   36,  120,  122,
  131,  130,   58,   34,   38,   38,   76,   93,   62,   62,
   62,   62,   94,  133,    1,   54,  132,   54,   54,   54,
   62,   58,  135,  137,   18,   18,    2,   67,   59,  116,
   18,  140,  142,   54,   54,   54,   54,   18,   53,   71,
   50,   33,   50,   50,   50,   60,   52,   59,  160,  164,
   61,  126,  167,   87,  204,   88,   18,  132,   50,   50,
   50,   50,   64,   37,   37,   51,   59,   51,   51,   51,
   65,  147,  152,  159,   79,   18,  175,  157,  149,   58,
   37,  114,  186,   51,   51,   51,   51,   18,  206,   75,
   18,  132,  146,   45,   87,  162,   88,   87,  207,   88,
   78,  132,   99,  110,   83,  197,   45,  199,  200,  170,
  201,   87,   44,   88,   55,   80,   56,   45,  176,  205,
   45,   87,  124,   88,   59,  198,    4,   45,  208,  156,
   45,  132,   95,   18,    5,    6,  101,  210,   18,  151,
  132,    8,    9,   94,  109,   10,  119,   11,   12,   13,
  183,  102,   87,    4,   88,  188,  104,   87,  103,   88,
   92,    5,    6,  105,  106,    7,  184,  185,    8,    9,
   66,   67,   10,   68,   11,   12,   13,   31,  195,  123,
   87,   55,   88,  127,   55,  144,   91,   89,   90,   11,
   12,   13,  128,  143,   30,  145,  148,   40,   34,   35,
   35,   41,   42,   43,  107,  192,   62,   62,   40,   62,
   62,   80,   41,   42,   43,   62,   62,   62,  150,  153,
   62,  121,   62,   62,  154,   62,   62,  158,   62,   62,
   62,   54,   54,   67,   54,   54,   70,  173,  168,  169,
   54,   54,   54,  171,  172,   54,  177,   54,   54,  179,
   54,   54,  180,   54,   54,   54,   50,   50,  181,   50,
   50,   71,  182,  187,  189,   50,   50,   50,  203,  190,
   50,  191,   50,   50,  194,   50,   50,  196,   50,   50,
   50,   51,   51,  202,   51,   51,   82,  209,  211,  212,
   51,   51,   51,  174,  213,   51,  214,   51,   51,   45,
   51,   51,   45,   51,   51,   51,   40,   63,  118,    0,
   41,   42,   43,   45,   11,   12,   13,   97,    0,   40,
    0,    0,   45,   41,   42,   43,   98,   11,   12,   13,
   40,    0,    0,   40,   41,   42,   43,   41,   42,   43,
   40,    0,   94,   40,   41,   42,   43,   41,   42,   43,
   94,   94,   94,    0,    0,   94,    0,   94,   94,   92,
   94,   94,    0,   94,   94,   94,    0,   92,   92,   92,
    0,    0,   92,    0,   92,   92,   31,   92,   92,    0,
   92,   92,   92,   84,   31,   31,   85,   86,    0,   31,
    0,   31,   31,   30,   31,   31,    0,   31,   31,   31,
    0,   30,   30,   91,   89,   90,   30,    0,   30,   30,
   80,   30,   30,    0,   30,   30,   30,  163,   80,   80,
  132,    0,    0,   80,    0,   80,   80,    0,   80,   80,
    0,   80,   80,   80,    0,   70,   91,   89,   90,    0,
    0,    0,   55,   70,   70,   55,    0,    0,   70,    0,
   70,   70,    0,   70,   70,    0,   70,   70,   70,    0,
   71,   55,   55,   55,    0,    0,    0,   56,   71,   71,
   56,    0,    0,   71,    0,   71,   71,    0,   71,   71,
    0,   71,   71,   71,    0,   82,   56,   56,   56,    0,
    0,    0,    0,   82,   82,    0,    0,    0,   82,   47,
   82,   82,    0,   82,   82,    0,   82,   82,   82,    0,
  134,    0,   40,  136,    0,   40,   41,   42,   43,   41,
   42,   43,    0,    0,  139,   87,   40,   88,   74,   77,
   41,   42,   43,  141,    0,   40,    0,   81,    0,   41,
   42,   43,   91,   89,   90,    0,  100,    0,    0,    0,
   30,   30,  113,    0,    0,    0,   30,    0,  112,    0,
    5,    6,    0,   30,   47,   47,    0,    8,    5,  125,
  109,   10,    0,    0,    0,    0,    5,    5,    0,    0,
    0,    5,   30,    5,    5,  138,    5,    5,    0,    5,
    5,    5,    0,    0,    0,    0,    0,    0,    0,    0,
   84,   30,    0,   85,   86,    0,    0,    0,    0,    0,
    0,    0,    0,   30,   47,    0,   30,  161,    0,    0,
    0,    0,    0,    0,  165,  166,    7,    0,    0,    0,
    0,    0,    0,   84,    7,    7,   85,   86,    0,    7,
    0,    7,    7,  178,    7,    7,    0,    7,    7,    7,
    0,    0,    0,    0,    0,    0,    0,    9,   55,   30,
    0,   55,   55,    0,   30,    9,    9,    0,    0,    0,
    9,    0,    9,    9,    0,    9,    9,    0,    9,    9,
    9,   11,    0,   56,    0,    0,   56,   56,    0,   11,
   11,    0,    0,    0,   11,    0,   11,   11,    4,   11,
   11,    0,   11,   11,   11,    0,    5,    6,    0,    0,
    0,   57,    0,    8,    9,    4,    0,   10,    0,   11,
   12,   13,    0,    5,    6,    0,    0,    0,   73,    0,
    8,    9,    4,    0,   10,    0,   11,   12,   13,   84,
    5,    6,   85,   86,    0,   96,    0,    8,    9,    4,
    0,   10,    0,   11,   12,   13,    0,    5,    6,    0,
    0,    0,    0,    0,    8,    9,    4,  109,   10,    0,
   11,   12,   13,    0,    5,    6,    0,    0,    0,    0,
    0,    8,    9,  113,    0,   10,    0,   11,   12,   13,
    0,    5,    6,    0,    0,   24,  155,    0,    8,    0,
    0,  109,   10,   24,   24,    0,    0,    0,   24,   25,
   24,    0,    0,   24,   24,    0,    0,   25,   25,    0,
    0,    0,   25,    0,   25,    0,    0,   25,   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   40,  269,   14,   45,  272,   40,   32,   41,  278,
   41,   42,   43,   44,   45,   45,   47,   40,   71,   72,
   81,   80,   33,  258,   64,   64,   36,   42,   59,   60,
   61,   62,   47,   41,  258,   41,   44,   43,   44,   45,
  275,   52,   87,   88,    1,    2,  270,  270,   14,   67,
    7,   93,   94,   59,   60,   61,   62,   14,   40,  282,
   41,    2,   43,   44,   45,   59,    7,   33,  121,  130,
   59,   41,  133,   43,   41,   45,   33,   44,   59,   60,
   61,   62,  270,  123,  123,   41,   52,   43,   44,   45,
  259,  101,  110,  118,   44,   52,   41,  115,  108,  110,
  123,   67,  163,   59,   60,   61,   62,   64,   41,   41,
   67,   44,   41,   45,   43,   41,   45,   43,   41,   45,
  263,   44,   41,   64,  262,  184,   45,  186,  187,   41,
  189,   43,   40,   45,  256,   40,  258,   45,  148,  198,
   45,   43,   40,   45,  110,   40,  258,   45,   41,  115,
   45,   44,  268,  110,  266,  267,   60,   41,  115,  271,
   44,  273,  274,   59,  276,  277,  272,  279,  280,  281,
   41,   59,   43,  258,   45,   41,   59,   43,   64,   45,
   59,  266,  267,   59,   59,  270,   40,   41,  273,  274,
  269,  270,  277,  272,  279,  280,  281,   59,   41,   59,
   43,   41,   45,  125,   44,   41,   60,   61,   62,  279,
  280,  281,  258,   59,   59,   41,   40,  258,  258,  259,
  259,  262,  263,  264,  258,  258,  257,  258,  258,  260,
  261,   59,  262,  263,  264,  266,  267,  268,   40,  271,
  271,  282,  273,  274,   59,  276,  277,  271,  279,  280,
  281,  257,  258,  270,  260,  261,   59,   62,   59,   59,
  266,  267,  268,   59,   59,  271,  258,  273,  274,  271,
  276,  277,   59,  279,  280,  281,  257,  258,  271,  260,
  261,   59,  272,   40,   40,  266,  267,  268,   59,  258,
  271,   41,  273,  274,   41,  276,  277,   41,  279,  280,
  281,  257,  258,   41,  260,  261,   59,   41,   41,   41,
  266,  267,  268,  258,   41,  271,   41,  273,  274,   45,
  276,  277,   45,  279,  280,  281,  258,   28,   69,   -1,
  262,  263,  264,   45,  279,  280,  281,  256,   -1,  258,
   -1,   -1,   45,  262,  263,  264,  265,  279,  280,  281,
  258,   -1,   -1,  258,  262,  263,  264,  262,  263,  264,
  258,   -1,  258,  258,  262,  263,  264,  262,  263,  264,
  266,  267,  268,   -1,   -1,  271,   -1,  273,  274,  258,
  276,  277,   -1,  279,  280,  281,   -1,  266,  267,  268,
   -1,   -1,  271,   -1,  273,  274,  258,  276,  277,   -1,
  279,  280,  281,  257,  266,  267,  260,  261,   -1,  271,
   -1,  273,  274,  258,  276,  277,   -1,  279,  280,  281,
   -1,  266,  267,   60,   61,   62,  271,   -1,  273,  274,
  258,  276,  277,   -1,  279,  280,  281,   41,  266,  267,
   44,   -1,   -1,  271,   -1,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,   -1,  258,   60,   61,   62,   -1,
   -1,   -1,   41,  266,  267,   44,   -1,   -1,  271,   -1,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,   -1,
  258,   60,   61,   62,   -1,   -1,   -1,   41,  266,  267,
   44,   -1,   -1,  271,   -1,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,   -1,  258,   60,   61,   62,   -1,
   -1,   -1,   -1,  266,  267,   -1,   -1,   -1,  271,    6,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,   -1,
  256,   -1,  258,  256,   -1,  258,  262,  263,  264,  262,
  263,  264,   -1,   -1,  256,   43,  258,   45,   35,   36,
  262,  263,  264,  256,   -1,  258,   -1,   44,   -1,  262,
  263,  264,   60,   61,   62,   -1,   53,   -1,   -1,   -1,
    1,    2,  258,   -1,   -1,   -1,    7,   -1,   65,   -1,
  266,  267,   -1,   14,   71,   72,   -1,  273,  258,   76,
  276,  277,   -1,   -1,   -1,   -1,  266,  267,   -1,   -1,
   -1,  271,   33,  273,  274,   92,  276,  277,   -1,  279,
  280,  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,   52,   -1,  260,  261,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   64,  121,   -1,   67,  124,   -1,   -1,
   -1,   -1,   -1,   -1,  131,  132,  258,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  266,  267,  260,  261,   -1,  271,
   -1,  273,  274,  150,  276,  277,   -1,  279,  280,  281,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,  257,  110,
   -1,  260,  261,   -1,  115,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,  258,   -1,  257,   -1,   -1,  260,  261,   -1,  266,
  267,   -1,   -1,   -1,  271,   -1,  273,  274,  258,  276,
  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,
   -1,  271,   -1,  273,  274,  258,   -1,  277,   -1,  279,
  280,  281,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,
  273,  274,  258,   -1,  277,   -1,  279,  280,  281,  257,
  266,  267,  260,  261,   -1,  271,   -1,  273,  274,  258,
   -1,  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,
   -1,   -1,   -1,   -1,  273,  274,  258,  276,  277,   -1,
  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,   -1,
   -1,  273,  274,  258,   -1,  277,   -1,  279,  280,  281,
   -1,  266,  267,   -1,   -1,  258,  271,   -1,  273,   -1,
   -1,  276,  277,  266,  267,   -1,   -1,   -1,  271,  258,
  273,   -1,   -1,  276,  277,   -1,   -1,  266,  267,   -1,
   -1,   -1,  271,   -1,  273,   -1,   -1,  276,  277,
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

//#line 1159 "gramatica.y"

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
    public final static int  TIPO_DESCONOCIDO = 50;
    public final static int TIPO_TRIPLE_UNSIGNED = 5;
    public final static int TIPO_TRIPLE_SINGLE = 6;
    public final static int TIPO_TRIPLE_OCTAL = 7;

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


//#line 646 "Parser.java"
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
{System.out.println("Error: Falta ; " + "antes de la linea: " + lexico.getContadorLinea()); generador.setError();}
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
case 24:
//#line 55 "gramatica.y"
{System.err.println("Error: Falta ;"); generador.setError();}
break;
case 25:
//#line 56 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 26:
//#line 64 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion ");}
break;
case 27:
//#line 65 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 28:
//#line 66 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 29:
//#line 67 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 30:
//#line 70 "gramatica.y"
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
case 31:
//#line 87 "gramatica.y"
{
        /*hay que ver que el ID sea alcanzable por el ambito actual.*/
        TablaSimbolos TS = lexico.getTablaSimbolos();
        String id = TS.buscarVariable(val_peek(1).sval);
        if (id == null){
            System.err.println("Error: variable no declarad. Linea: " + lexico.getContadorLinea());
            generador.setError();
        }
        else{
            if (tiposUsuario.contains(val_peek(1).sval)){
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
case 32:
//#line 135 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 33:
//#line 136 "gramatica.y"
{
	        yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	    }
break;
case 34:
//#line 141 "gramatica.y"
{yyval.sval = String.valueOf(T_OCTAL);}
break;
case 35:
//#line 142 "gramatica.y"
{yyval.sval = String.valueOf(T_UNSIGNED);}
break;
case 36:
//#line 143 "gramatica.y"
{yyval.sval = String.valueOf(T_SINGLE);}
break;
case 37:
//#line 151 "gramatica.y"
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
                TS.editarLexema(val_peek(1).sval, val_peek(1).sval + TS.getAmbitos());

                yyval.sval = generador.addTerceto(funcion,null,null);
                generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
                generador.putEtiqueta(funcion);
              }
break;
case 38:
//#line 171 "gramatica.y"
{System.err.println("Error: Falta nombre de funcion"); generador.setError();}
break;
case 39:
//#line 172 "gramatica.y"
{System.err.println("Error: Falta parametro de funcion"); generador.setError();}
break;
case 40:
//#line 173 "gramatica.y"
{System.err.println("Error: falta nombre del parametro formal"); generador.setError();}
break;
case 41:
//#line 174 "gramatica.y"
{System.err.println("Error: falta tipo del parametro formal"); generador.setError();}
break;
case 42:
//#line 177 "gramatica.y"
{
                /*verificar el tipo de retorno*/
                TablaSimbolos TS = lexico.getTablaSimbolos();
                String lexemaFun = TS.getUltimoAmbito(); /*obtengo el lexema de la funcion*/
                Integer tipoFun = TS.getTipo(lexemaFun); /*obtengo el tipo de la funcion*/
                Integer tipoRetorno = generador.getTerceto(Integer.parseInt(val_peek(1).sval.replaceAll("\\D", ""))).getTipo();
                if (tipoFun != tipoRetorno){
                    System.err.println("Error: tipo de retorno invalido en funcion: " + lexemaFun);
                    generador.setError();
                }

                /*desapilar el ambito de la funcion*/
                TS.eliminarAmbito();
     }
break;
case 43:
//#line 192 "gramatica.y"
{
	            /*verificar tipo retorno*/
	             TablaSimbolos TS = lexico.getTablaSimbolos();
                 String lexemaFun = TS.getUltimoAmbito(); /*obtengo el lexema de la funcion*/
                 Integer tipoFun = TS.getTipo(lexemaFun); /*obtengo el tipo de la funcion*/

                Integer tipoRetorno = generador.getTerceto(Integer.parseInt(val_peek(1).sval.replaceAll("\\D", ""))).getTipo();
                if (tipoFun != tipoRetorno){
                     System.err.println("Error: tipo de retorno invalido en funcion: " + lexemaFun);
                     generador.setError();
                }
                /*desapilar el ambito de la funcion*/
                TS.eliminarAmbito();

	}
break;
case 44:
//#line 208 "gramatica.y"
{System.err.println("Error: falta retorno en funcion"); generador.setError();}
break;
case 45:
//#line 213 "gramatica.y"
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
case 46:
//#line 239 "gramatica.y"
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
        if(val_peek(3).sval == TS.getUltimoAmbito())
            System.err.println("Error no se admiten funciones recursivas");
    }
break;
case 47:
//#line 287 "gramatica.y"
{
        /*verificar que el uso de ID sea nombre de función.*/
        TablaSimbolos TS = lexico.getTablaSimbolos();
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

            if (tipoParam != tipoCast){
                System.err.println("Error: tipo de parametro incompatible. Linea: " + lexico.getContadorLinea());
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
                	System.err.println("Error: conversion invalida. Linea: " + lexico.getContadorLinea());
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
        if(val_peek(4).sval == TS.getUltimoAmbito())
                    System.err.println("Error no se admiten funciones recursivas");
    }
break;
case 48:
//#line 345 "gramatica.y"
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
            Integer tipoParam = TS.getTipoParam(id);
            Integer tipoCast = Integer.parseInt(val_peek(4).sval);

            if (tipoParam != tipoCast){
                System.err.println("Error: tipo de parametro incompatible. Linea: " + lexico.getContadorLinea());
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
        if(val_peek(6).sval == TS.getUltimoAmbito())
                    System.err.println("Error no se admiten funciones recursivas");
    }
break;
case 49:
//#line 399 "gramatica.y"
{System.err.println("Error: falta de parámetro en invocación a función. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 50:
//#line 406 "gramatica.y"
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
                        System.err.println("Error: Incompatibilidad de tipos en suma. Linea " + lexico.getContadorLinea());
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
case 51:
//#line 463 "gramatica.y"
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
                        System.err.println("Error: Incompatibilidad de tipos en resta. Linea " + lexico.getContadorLinea());
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
case 52:
//#line 520 "gramatica.y"
{
                    System.err.println("Error: Falta el término después de '+' en expresion aritmetica. Línea: " + lexico.getContadorLinea());
                    generador.setError();
           }
break;
case 53:
//#line 525 "gramatica.y"
{
                    System.err.println("Error: Falta el término después de '-' en expresión aritmetica. Línea: " + lexico.getContadorLinea());
                    generador.setError();
           }
break;
case 54:
//#line 530 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 55:
//#line 535 "gramatica.y"
{
        			yyval.sval = val_peek(0).sval;
    			}
break;
case 56:
//#line 538 "gramatica.y"
{
	    yyval.sval = val_peek(2).sval.concat(",").concat(val_peek(0).sval);
	}
break;
case 57:
//#line 543 "gramatica.y"
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
                        System.err.println("Error: Incompatibilidad de tipos en multiplicacion. Linea " + lexico.getContadorLinea());
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
case 58:
//#line 598 "gramatica.y"
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
                        System.err.println("Error: Incompatibilidad de tipos en division. Linea " + lexico.getContadorLinea());
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
case 59:
//#line 652 "gramatica.y"
{
	    	yyval.sval = val_peek(0).sval;
		}
break;
case 60:
//#line 656 "gramatica.y"
{System.err.println("Error: Falta el factor después de '*' en expresion aritmetica. Línea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 61:
//#line 657 "gramatica.y"
{System.err.println("Error: Falta el factor después de '/' en expresión aritmetica. Línea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 62:
//#line 660 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
            System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());
            }
break;
case 63:
//#line 664 "gramatica.y"
{System.out.println("Se detecto: Invocación a función " + "en linea: " + lexico.getContadorLinea());}
break;
case 64:
//#line 665 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 65:
//#line 666 "gramatica.y"
{
		 	yyval.sval = truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea());
		 	TablaSimbolos TS = lexico.getTablaSimbolos();
            TS.editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 66:
//#line 671 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 67:
//#line 674 "gramatica.y"
{
            yyval.sval = val_peek(0).sval;
        }
break;
case 68:
//#line 677 "gramatica.y"
{
        	yyval.sval = truncarFueraRango("-"+val_peek(0).sval, lexico.getContadorLinea());
        	TablaSimbolos TS = lexico.getTablaSimbolos();
        	TS.editarLexema(val_peek(0).sval, yyval.sval);
        }
break;
case 69:
//#line 685 "gramatica.y"
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
case 70:
//#line 710 "gramatica.y"
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
                     System.err.println("Error: tipos invalidos en asignación. Linea: " +lexico.getContadorLinea());
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
case 71:
//#line 753 "gramatica.y"
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
                      System.err.println("Error: tipos invalidos en asignación. Linea: " +lexico.getContadorLinea());
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
case 72:
//#line 798 "gramatica.y"
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
case 73:
//#line 815 "gramatica.y"
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
case 74:
//#line 828 "gramatica.y"
{System.err.println("Error: falta de etiqueta en la sentencia GOTO" + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 75:
//#line 831 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 76:
//#line 835 "gramatica.y"
{
        	yyval.sval = generador.addTerceto("SALIDA", val_peek(1).sval, null);
        }
break;
case 77:
//#line 839 "gramatica.y"
{System.err.println("Error: falta parametro " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 78:
//#line 840 "gramatica.y"
{System.err.println("Error: parametro invalido " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 79:
//#line 847 "gramatica.y"
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
case 80:
//#line 858 "gramatica.y"
{System.err.println("Error: Falta END_IF de cierre " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 81:
//#line 859 "gramatica.y"
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
case 82:
//#line 870 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 83:
//#line 871 "gramatica.y"
{System.err.println("Error: Falta de contenido en el bloque then " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 84:
//#line 872 "gramatica.y"
{System.err.println("Error: Falta de contenido en el bloque else " + ". Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 85:
//#line 875 "gramatica.y"
{
							yyval.sval = generador.addTerceto("BF", val_peek(1).sval, null);
							generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);
							generador.agregarPila(yyval.sval);
				}
break;
case 86:
//#line 882 "gramatica.y"
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

							generador.addTerceto(label, null, null);
							generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
				  }
break;
case 87:
//#line 900 "gramatica.y"
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
                    			System.err.println("Error: comparación entre dos expresiones de tipos diferentes. Linea: "+lexico.getContadorLinea());
                    			generador.setError();
                    		}

                    		String operando1, operando2;

                    		if (primer_exp_arit == "Terceto") operando1 = val_peek(3).sval;
                            else operando1 = primer_exp_arit;
                            if (segunda_exp_arit == "Terceto") operando2 = val_peek(1).sval;
                            else operando2 = segunda_exp_arit;

                    		yyval.sval = generador.addTerceto(val_peek(2).sval, operando1, operando2);

                    		System.out.println("Se detecto: comparación");

                    	}
break;
case 88:
//#line 959 "gramatica.y"
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
case 89:
//#line 1085 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 90:
//#line 1086 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 91:
//#line 1087 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 92:
//#line 1088 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 93:
//#line 1089 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 94:
//#line 1090 "gramatica.y"
{System.err.println("Error: falta de parentesis en la condicion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 95:
//#line 1092 "gramatica.y"
{System.err.println("Error: falta de comparador. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 96:
//#line 1093 "gramatica.y"
{System.err.println("Error, falta de lista de expresión aritmetica en comparación. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 97:
//#line 1097 "gramatica.y"
{yyval.sval = ">=";}
break;
case 98:
//#line 1098 "gramatica.y"
{yyval.sval = "<=";}
break;
case 99:
//#line 1099 "gramatica.y"
{yyval.sval = "!=";}
break;
case 100:
//#line 1100 "gramatica.y"
{yyval.sval = "=";}
break;
case 101:
//#line 1101 "gramatica.y"
{yyval.sval = ">";}
break;
case 102:
//#line 1102 "gramatica.y"
{yyval.sval = "<";}
break;
case 103:
//#line 1105 "gramatica.y"
{
					int pos = Integer.parseInt(generador.obtenerElementoPila().replaceAll("\\D", ""));

					yyval.sval = generador.addTerceto("BT", val_peek(0).sval, generador.getTerceto(pos).getOperador());
					generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_SALTO);
    				generador.eliminarPila();
				}
break;
case 104:
//#line 1113 "gramatica.y"
{System.err.println("Error: falta cuerpo en la iteracion. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 105:
//#line 1114 "gramatica.y"
{System.err.println("Error: falta de until en la iteracion repeat. Linea: " + lexico.getContadorLinea()); generador.setError();}
break;
case 106:
//#line 1118 "gramatica.y"
{
				    yyval.sval = generador.addTerceto("ET" + generador.getSizeTercetos(), null, null);
				    generador.getTerceto(Integer.parseInt(yyval.sval.replaceAll("\\D", ""))).setTipo(TIPO_ETIQUETA);
				    generador.agregarPila(yyval.sval);
				}
break;
case 107:
//#line 1128 "gramatica.y"
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
//#line 2094 "Parser.java"
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
