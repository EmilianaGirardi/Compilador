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
    	/*import Lex.Lex;*/
//#line 22 "Parser.java"




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
public final static short menos=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    3,    3,    3,    3,    3,   10,   10,   10,
   11,   11,   11,   11,    2,    2,    2,    2,   14,   16,
   18,   18,   17,   17,   17,   13,   13,   13,   13,   13,
   13,   13,   19,   19,   19,   12,    6,    6,   20,   20,
   20,   20,   20,   23,   23,   22,   22,   22,   22,   22,
   24,   24,   24,   24,   25,   21,   21,   21,    5,    5,
    5,    4,    4,    4,    4,    4,    4,   26,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   27,   27,
   27,   27,   27,   27,    9,    9,    9,    9,    7,    7,
    7,   15,   28,    8,    8,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    3,    4,    3,
    2,    3,    1,    2,    1,    1,    1,    1,    2,    2,
    1,    3,    1,    1,    1,   10,    9,    9,    9,    8,
    9,    8,    2,    1,    1,    5,    3,    6,    3,    3,
    4,    4,    1,    1,    3,    3,    3,    1,    4,    4,
    1,    4,    1,    1,    2,    1,    1,    1,    4,    7,
    3,    5,    4,    7,    6,    4,    6,    5,    9,    8,
    8,    8,    8,    4,    4,    4,    8,    5,    1,    1,
    1,    1,    1,    1,    4,    4,    3,    4,    4,    3,
    3,    6,    1,    2,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,   35,   33,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   25,   26,   27,   28,    0,    0,   31,    0,
    0,    0,    0,    0,    0,    0,    0,   66,   67,   68,
    0,   64,    0,   63,    0,   58,    0,    0,    0,  103,
    0,    0,    0,  104,    0,    0,    0,    4,    6,    0,
    0,    0,    0,   71,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  100,    0,  101,    0,    0,    0,    0,
   90,   91,   89,    0,    0,   92,   93,   94,    0,    0,
    0,    0,    0,    0,    0,   97,    0,    0,  105,   65,
    3,    8,   10,    0,    0,    2,    0,   69,    0,   32,
    0,   21,   18,    0,    0,   20,   99,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   57,    0,   56,   76,    0,    1,   98,   95,   96,    0,
    0,   45,    0,    0,    0,    0,    0,   22,   19,   62,
    0,    0,   86,    0,    0,    0,   51,   52,   84,   60,
   59,    0,   72,    0,    0,    0,   43,    0,    0,    0,
    0,    0,   88,    0,    0,   78,    0,   77,    0,  102,
    0,    0,    0,   70,   46,    0,    0,    0,    0,    0,
   74,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   42,    0,    0,    0,   40,   87,    0,   81,
   80,   82,   41,   38,    0,   37,   39,   79,   36,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   42,   19,   20,   21,   22,   36,
   72,   73,   23,   24,   25,   26,   27,   33,  144,  119,
   44,   45,   80,   46,   54,   47,   89,   51,
};
final static short yysindex[] = {                      -197,
  362,  439,    0,   76, -206,   85,  439,    2, -231, -173,
    0,    0,    0,  391,   -2,    7,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -224,  483,    0, -155,
   65,  220,   23, -218,   85,   58,  -18,    0,    0,    0,
   92,    0,  470,    0,   -6,    0, -196,  500,   74,    0,
   31,   38,   48,    0,   52,   88,   97,    0,    0,  -29,
   23,  112,  109,    0,   84,   77,   37,  -85,  -31,  139,
  129,  -82,  -78,    0,   85,    0,  220, -155,  353,   21,
    0,    0,    0, -174,  -66,    0,    0,    0, -155,  309,
  543,   47,  143,  160,  169,    0,  -35,  177,    0,    0,
    0,    0,    0,  166, -242,    0, -155,    0,  -22,    0,
 -155,    0,    0,  195,  -15,    0,    0,  134,  109,  393,
   78, -155, -155,  -48,  203,   -6,  215,   -6,   86,  222,
    0,  225,    0,    0, -159,    0,    0,    0,    0,  217,
   45,    0,   29,  258,   93, -155,  100,    0,    0,    0,
  387,  266,    0,  103,  109,  269,    0,    0,    0,    0,
    0,   61,    0,   54,   71,  301,    0,   81,  322,  109,
  305, -155,    0,   95, -155,    0, -155,    0,  104,    0,
  517,  102,  517,    0,    0,   89, -155,  125,  142,  159,
    0,  517,  110,  517,  517,  118,  342,  167,  356,  365,
  369,  122,    0, -116,  146,  149,    0,    0,  388,    0,
    0,    0,    0,    0,  162,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -99,  323,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  128,    0,    0,    0,  -41,    0,    0,    0,
    0,    0,    0,    0,  -16,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  415,  466,    0,    0,    0,
  145,    0,  165,    0,    0,    0,    0,    0,    0,    0,
  293,    0,    0,    0,    0,    0,    0,    0,  190,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  537,    0,    0,    0,    0,  418,    0,
    0,    0,    0,    0,    0,    9,    0,   34,    0,    0,
    0,    0,    0,    0,  194,    0,    0,    0,    0,    0,
    0,    0,  389,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   94,  447,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  219,
    0,    0,    0,    0,    0,    0,    0,    0,  245,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  111,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   24,  352,  506,    0,  534,    0,    0,    0,    0,  -62,
    0,  -55,    0,    0,    0,    0,  -24,  408,  299,  544,
    3,  -52,  -73,  124,    0,  -12,  -64,    0,
};
final static int YYTABLESIZE=814;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         61,
   61,   61,   61,   61,  120,   61,   65,   84,   31,   85,
  105,   88,   86,   87,  122,  142,  115,   61,   61,   61,
   61,   31,   74,   76,   53,   28,   53,   53,   53,  135,
   48,  126,  128,   29,   67,   91,   11,   12,   13,   69,
   90,   49,   53,   53,   53,   53,   50,    5,    6,   49,
   60,   49,   49,   49,    8,  152,   58,   70,   10,  156,
    1,  124,  117,   34,  123,   59,   68,   49,   49,   49,
   49,   92,    2,  140,   50,   35,   50,   50,   50,  118,
  143,  125,   52,   37,   53,  165,  174,   38,   39,   40,
   98,   32,   50,   50,   50,   50,   99,   41,  186,  179,
  188,  189,   37,  190,   77,   64,   38,   39,   40,  162,
  101,  100,  163,  198,   96,   31,  143,  108,  153,   84,
   84,   85,   85,  107,   41,  193,  159,  196,   84,  197,
   85,   78,  123,  169,  187,   84,  202,   85,  205,  206,
  171,    4,   84,  176,   85,   84,  102,   85,  215,    5,
    6,   84,   85,   85,  214,  103,    8,    9,    5,   70,
   10,  109,   11,   12,   13,  199,    5,    5,  123,   83,
  106,    5,  110,    5,    5,   69,    5,    5,  111,    5,
    5,    5,  200,    5,    6,  123,   30,  112,  113,  127,
    8,   37,  116,   70,   10,   38,   39,   40,   32,  201,
  137,  136,  123,   29,  192,  141,  195,  209,   81,  138,
  123,   82,   83,  131,  133,   61,   61,  204,   61,   61,
   61,   61,   61,   47,   61,   61,   61,   30,  104,   61,
   54,   61,   61,   54,   61,   61,  146,   61,   61,   61,
   53,   53,   61,   53,   53,   53,   53,   53,  139,   53,
   53,   53,   73,  148,   53,  149,   53,   53,  150,   53,
   53,  157,   53,   53,   53,   49,   49,   53,   49,   49,
   49,   49,   49,  158,   49,   49,   49,   48,  164,   49,
  160,   49,   49,  161,   49,   49,  167,   49,   49,   49,
   50,   50,   49,   50,   50,   50,   50,   50,  168,   50,
   50,   50,  142,   75,   50,  175,   50,   50,  177,   50,
   50,  180,   50,   50,   50,   37,   34,   50,  134,   38,
   39,   40,   37,   11,   12,   13,   38,   39,   40,   94,
   34,   37,  178,   29,   30,   38,   39,   40,   95,   75,
  181,  182,   37,   11,   12,   13,   38,   39,   40,   37,
  183,   85,   37,   38,   39,   40,   38,   39,   40,   85,
   85,   85,  184,  185,   85,   56,   85,   85,   83,   85,
   85,  194,   85,   85,   85,  191,   83,   83,   83,   56,
  203,   83,  208,   83,   83,   30,   83,   83,  207,   83,
   83,   83,  213,   30,   30,   84,  210,   85,   30,   56,
   30,   30,   29,   30,   30,  211,   30,   30,   30,  212,
   29,   29,   88,   86,   87,   29,  216,   29,   29,  217,
   29,   29,   47,   29,   29,   29,  172,  173,  218,   44,
   47,   47,  219,  151,   61,   47,  123,   47,   47,  166,
   47,   47,    0,   47,   47,   47,   88,   86,   87,    0,
    0,   73,   88,   86,   87,   11,   12,   13,   54,   73,
   73,   54,    0,    0,   73,    0,   73,   73,    0,   73,
   73,    0,   73,   73,   73,    0,   48,   54,   54,   54,
    0,   38,   39,   40,   48,   48,    0,   55,    0,   48,
   55,   48,   48,    0,   48,   48,    0,   48,   48,   48,
    0,    0,   75,    0,    0,    0,   55,   55,   55,    0,
   75,   75,   84,    0,   85,   75,    0,   75,   75,   57,
   75,   75,    0,   75,   75,   75,    0,    0,    0,   88,
   86,   87,    0,   57,   18,   18,    0,    0,    0,   71,
   18,    0,    0,   56,    0,    0,   56,   18,    0,   43,
   23,    0,    0,   57,    0,   56,    0,    0,   23,   23,
    0,   18,    0,   23,  130,   23,   37,   18,   23,   23,
   38,   39,   40,   63,   66,    0,    0,  114,   43,   43,
    7,   18,    0,    0,   79,    0,    0,    0,    7,    7,
    0,    0,   97,    7,    0,    7,    7,    0,    7,    7,
    0,    7,    7,    7,    0,   18,    0,    0,    0,   81,
   37,    0,   82,   83,   38,   39,   40,    0,   43,    4,
    0,    0,  121,    0,    0,    0,    0,    5,    6,    0,
    0,    7,  129,    0,    8,    9,    0,    0,   10,    0,
   11,   12,   13,   81,    0,    0,   82,   83,    4,   81,
  145,    0,   82,   83,  147,    0,    5,    6,    0,    0,
    0,   55,    0,    8,    9,  154,  155,   10,    0,   11,
   12,   13,    9,    0,   54,    0,    0,   54,   54,    0,
    9,    9,    0,    0,    0,    9,    0,    9,    9,  170,
    9,    9,    0,    9,    9,    9,    4,   57,    0,    0,
   57,    0,    0,   55,    5,    6,   55,   55,    0,   57,
    0,    8,    9,    0,   18,   10,   18,   11,   12,   13,
    0,    0,    0,   11,    0,   18,   81,   18,   18,   82,
   83,   11,   11,    0,    0,    0,   11,   18,   11,   11,
    4,   11,   11,    0,   11,   11,   11,    0,    5,    6,
    0,    0,    0,   62,    0,    8,    9,    4,    0,   10,
    0,   11,   12,   13,    0,    5,    6,    0,    0,    0,
   93,    0,    8,    9,    4,    0,   10,    0,   11,   12,
   13,    0,    5,    6,    0,    0,    0,    0,    0,    8,
    9,    0,   70,   10,   24,   11,   12,   13,  132,    0,
   37,    0,   24,   24,   38,   39,   40,   24,    0,   24,
    0,    0,   24,   24,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   78,   47,   31,   43,   40,   45,
   40,   60,   61,   62,   79,  258,   72,   59,   60,   61,
   62,   40,   35,   36,   41,    2,   43,   44,   45,   92,
    7,   84,   85,  258,   32,   42,  279,  280,  281,  258,
   47,   40,   59,   60,   61,   62,  278,  266,  267,   41,
  275,   43,   44,   45,  273,  120,   59,  276,  277,  124,
  258,   41,   75,  270,   44,   59,   44,   59,   60,   61,
   62,  268,  270,   98,   41,  282,   43,   44,   45,   77,
  105,  256,  256,  258,  258,   41,  151,  262,  263,  264,
   60,  123,   59,   60,   61,   62,   59,   40,  172,  162,
  174,  175,  258,  177,  123,   41,  262,  263,  264,  269,
   59,   64,  272,  187,   41,   40,  141,   41,   41,   43,
   43,   45,   45,   40,   40,  181,   41,  183,   43,   41,
   45,   40,   44,   41,   40,   43,  192,   45,  194,  195,
   41,  258,   43,   41,   45,   43,   59,   45,  204,  266,
  267,   43,   59,   45,  271,   59,  273,  274,  258,  276,
  277,  125,  279,  280,  281,   41,  266,  267,   44,   59,
   59,  271,  258,  273,  274,  258,  276,  277,   40,  279,
  280,  281,   41,  266,  267,   44,   59,   59,  271,  256,
  273,  258,  271,  276,  277,  262,  263,  264,  123,   41,
   41,   59,   44,   59,  181,   40,  183,   41,  257,   41,
   44,  260,  261,   90,   91,  257,  258,  194,  260,  261,
  262,  263,  264,   59,  266,  267,  268,  259,  258,  271,
   41,  273,  274,   44,  276,  277,  259,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,  284,  266,
  267,  268,   59,   59,  271,  271,  273,  274,  125,  276,
  277,   59,  279,  280,  281,  257,  258,  284,  260,  261,
  262,  263,  264,   59,  266,  267,  268,   59,   62,  271,
   59,  273,  274,   59,  276,  277,  258,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,   41,  266,
  267,  268,  258,   59,  271,   40,  273,  274,   40,  276,
  277,  258,  279,  280,  281,  258,  270,  284,  272,  262,
  263,  264,  258,  279,  280,  281,  262,  263,  264,  256,
  270,  258,  272,  258,  259,  262,  263,  264,  265,  282,
  270,   41,  258,  279,  280,  281,  262,  263,  264,  258,
  270,  258,  258,  262,  263,  264,  262,  263,  264,  266,
  267,  268,   41,   59,  271,   14,  273,  274,  258,  276,
  277,  270,  279,  280,  281,  272,  266,  267,  268,   28,
  271,  271,   41,  273,  274,  258,  276,  277,  271,  279,
  280,  281,  271,  266,  267,   43,   41,   45,  271,   48,
  273,  274,  258,  276,  277,   41,  279,  280,  281,   41,
  266,  267,   60,   61,   62,  271,  271,  273,  274,  271,
  276,  277,  258,  279,  280,  281,   40,   41,   41,   41,
  266,  267,  271,   41,   27,  271,   44,  273,  274,  141,
  276,  277,   -1,  279,  280,  281,   60,   61,   62,   -1,
   -1,  258,   60,   61,   62,  279,  280,  281,   41,  266,
  267,   44,   -1,   -1,  271,   -1,  273,  274,   -1,  276,
  277,   -1,  279,  280,  281,   -1,  258,   60,   61,   62,
   -1,  262,  263,  264,  266,  267,   -1,   41,   -1,  271,
   44,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
   -1,   -1,  258,   -1,   -1,   -1,   60,   61,   62,   -1,
  266,  267,   43,   -1,   45,  271,   -1,  273,  274,   14,
  276,  277,   -1,  279,  280,  281,   -1,   -1,   -1,   60,
   61,   62,   -1,   28,    1,    2,   -1,   -1,   -1,   34,
    7,   -1,   -1,  192,   -1,   -1,  195,   14,   -1,    6,
  258,   -1,   -1,   48,   -1,  204,   -1,   -1,  266,  267,
   -1,   28,   -1,  271,  256,  273,  258,   34,  276,  277,
  262,  263,  264,   30,   31,   -1,   -1,   72,   35,   36,
  258,   48,   -1,   -1,   41,   -1,   -1,   -1,  266,  267,
   -1,   -1,   49,  271,   -1,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,   -1,   72,   -1,   -1,   -1,  257,
  258,   -1,  260,  261,  262,  263,  264,   -1,   75,  258,
   -1,   -1,   79,   -1,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   89,   -1,  273,  274,   -1,   -1,  277,   -1,
  279,  280,  281,  257,   -1,   -1,  260,  261,  258,  257,
  107,   -1,  260,  261,  111,   -1,  266,  267,   -1,   -1,
   -1,  271,   -1,  273,  274,  122,  123,  277,   -1,  279,
  280,  281,  258,   -1,  257,   -1,   -1,  260,  261,   -1,
  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,  146,
  276,  277,   -1,  279,  280,  281,  258,  192,   -1,   -1,
  195,   -1,   -1,  257,  266,  267,  260,  261,   -1,  204,
   -1,  273,  274,   -1,  181,  277,  183,  279,  280,  281,
   -1,   -1,   -1,  258,   -1,  192,  257,  194,  195,  260,
  261,  266,  267,   -1,   -1,   -1,  271,  204,  273,  274,
  258,  276,  277,   -1,  279,  280,  281,   -1,  266,  267,
   -1,   -1,   -1,  271,   -1,  273,  274,  258,   -1,  277,
   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,  258,   -1,  277,   -1,  279,  280,
  281,   -1,  266,  267,   -1,   -1,   -1,   -1,   -1,  273,
  274,   -1,  276,  277,  258,  279,  280,  281,  256,   -1,
  258,   -1,  266,  267,  262,  263,  264,  271,   -1,  273,
   -1,   -1,  276,  277,
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
"TRIPLE","TIPO_UNSIGNED","TIPO_SINGLE","TIPO_OCTAL","UNTIL","menos","\") \"",
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
"asig : ID ASIGNACION exp_arit",
"asig : ID '{' constante '}' ASIGNACION exp_arit",
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
"factor : ID '{' constante '}'",
"factor : constante",
"factor : invocacion_fun",
"etiqueta : ID '@'",
"constante : SINGLE_CONSTANTE",
"constante : ENTERO_UNSIGNED",
"constante : OCTAL",
"invocacion_fun : ID '(' exp_arit ')'",
"invocacion_fun : ID '(' tipo '(' exp_arit ')' ')'",
"invocacion_fun : ID '(' ')'",
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
"salida : OUTF '(' MULTILINEA ')'",
"salida : OUTF '(' exp_arit \") \"",
"salida : OUTF '(' ')'",
"salida : OUTF '(' error ')'",
"repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL condicion",
"repeat_until : REPEAT UNTIL condicion",
"repeat_until : REPEAT bloque_sentencias_ejecutables condicion",
"def_triple : TYPEDEF tipo_compuesto '<' tipo '>' ID",
"tipo_compuesto : TRIPLE",
"goto : GOTO etiqueta",
"goto : GOTO error ';'",
};

//#line 182 "gramatica.y"

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


//#line 608 "Parser.java"
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
//#line 15 "gramatica.y"
{System.out.println("Se detecto: Programa");}
break;
case 2:
//#line 16 "gramatica.y"
{System.out.println("Error, Falta nombre de programa");}
break;
case 3:
//#line 17 "gramatica.y"
{System.out.println("Error de delimitador de programa ");}
break;
case 5:
//#line 20 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 7:
//#line 22 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 9:
//#line 24 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 11:
//#line 26 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 12:
//#line 29 "gramatica.y"
{System.out.println("Se detecto: Sentencia if ");}
break;
case 13:
//#line 30 "gramatica.y"
{System.out.println("Se detecto: Invocacion a funcion " + "en linea: " + lexico.getContadorLinea());}
break;
case 14:
//#line 31 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + "en linea: " + lexico.getContadorLinea());}
break;
case 15:
//#line 32 "gramatica.y"
{System.out.println("Se detecto: Ciclo repeat until ");}
break;
case 16:
//#line 33 "gramatica.y"
{System.out.println("Se detecto: Sentencia GOTO " + "en linea: " + lexico.getContadorLinea());}
break;
case 17:
//#line 34 "gramatica.y"
{System.out.println("Se detecto: Salida " + "en linea: " + lexico.getContadorLinea());}
break;
case 23:
//#line 44 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 24:
//#line 45 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 25:
//#line 48 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion ");}
break;
case 26:
//#line 49 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 27:
//#line 50 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 28:
//#line 51 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 38:
//#line 67 "gramatica.y"
{System.out.println("Error, falta retorno en funcion");}
break;
case 39:
//#line 68 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 40:
//#line 69 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 41:
//#line 70 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 42:
//#line 71 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 44:
//#line 76 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 45:
//#line 77 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 49:
//#line 89 "gramatica.y"
{System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());}
break;
case 50:
//#line 90 "gramatica.y"
{System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());}
break;
case 51:
//#line 91 "gramatica.y"
{System.out.println("Error: Falta el término después de '+' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 52:
//#line 92 "gramatica.y"
{System.out.println("Error: Falta el término después de '-' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 56:
//#line 105 "gramatica.y"
{System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());}
break;
case 57:
//#line 106 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 59:
//#line 108 "gramatica.y"
{System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 60:
//#line 109 "gramatica.y"
{System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 112 "gramatica.y"
{System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());}
break;
case 64:
//#line 115 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 66:
//#line 120 "gramatica.y"
{lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea()));}
break;
case 71:
//#line 129 "gramatica.y"
{System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
break;
case 73:
//#line 133 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 74:
//#line 134 "gramatica.y"
{System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
break;
case 75:
//#line 135 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 76:
//#line 136 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 77:
//#line 137 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 78:
//#line 141 "gramatica.y"
{System.out.println("Se detecto: comparación");}
break;
case 79:
//#line 142 "gramatica.y"
{System.out.println("Se detecto: comparación múltiple");}
break;
case 80:
//#line 143 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 81:
//#line 144 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 82:
//#line 145 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 83:
//#line 146 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 84:
//#line 147 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 148 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 86:
//#line 149 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
break;
case 87:
//#line 150 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 88:
//#line 151 "gramatica.y"
{System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
break;
case 97:
//#line 164 "gramatica.y"
{System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
break;
case 98:
//#line 165 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 100:
//#line 169 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 101:
//#line 170 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 105:
//#line 179 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
//#line 993 "Parser.java"
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
