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
   24,   24,   24,   24,   24,   24,   25,   21,   21,    5,
    5,    5,    4,    4,    4,    4,    4,    4,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   27,
   27,   27,   27,   27,   27,    9,    9,    9,    9,    7,
    7,    7,   15,   28,    8,    8,
};
final static short yylen[] = {                            2,
    5,    4,    4,    2,    1,    2,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    1,    3,    4,    3,
    2,    3,    1,    2,    1,    1,    1,    1,    2,    2,
    1,    3,    1,    1,    1,   10,    9,    9,    9,    8,
    9,    8,    2,    1,    1,    5,    3,    6,    3,    3,
    4,    4,    1,    1,    3,    3,    3,    1,    4,    4,
    1,    4,    1,    1,    2,    1,    2,    1,    1,    4,
    7,    3,    5,    4,    7,    6,    4,    6,    5,    9,
    8,    8,    8,    8,    4,    4,    4,    8,    5,    1,
    1,    1,    1,    1,    1,    4,    4,    3,    4,    4,
    3,    3,    6,    1,    2,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,   35,   33,    0,    0,    0,   12,   13,   14,   15,
   16,   17,   25,   26,   27,   28,    0,    0,   31,    0,
    0,    0,    0,    0,    0,    0,    0,   64,   68,   69,
    0,    0,   66,    0,   63,    0,   58,    0,    0,    0,
  104,    0,    0,    0,  105,    0,    0,    0,    4,    6,
    0,    0,    0,    0,   72,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  101,    0,  102,    0,   65,    0,
    0,    0,   91,   92,   90,    0,    0,   93,   94,   95,
    0,    0,    0,    0,    0,    0,    0,   98,    0,    0,
  106,   67,    3,    8,   10,    0,    0,    2,    0,   70,
    0,   32,    0,   21,   18,    0,    0,   20,  100,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   57,    0,   56,   77,    0,    1,   99,
   96,   97,    0,    0,   45,    0,    0,    0,    0,    0,
   22,   19,   62,    0,    0,   64,   87,    0,    0,    0,
   51,   52,   85,   60,   59,    0,   73,    0,    0,    0,
   43,    0,    0,    0,    0,    0,   89,    0,    0,   79,
    0,   78,    0,  103,    0,    0,    0,   71,   46,    0,
    0,    0,    0,    0,   75,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   42,    0,    0,    0,
   40,   88,    0,   82,   81,   83,   41,   38,    0,   37,
   39,   80,   36,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   43,   19,   20,   21,   22,   36,
   73,   74,   23,   24,   25,   26,   27,   33,  147,  121,
   45,   46,   82,   47,   55,   48,   91,   52,
};
final static short yysindex[] = {                      -223,
  439,  361,    0,  340, -233,   85,  361,   -8, -205, -135,
    0,    0,    0,  493,   21,   25,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -227,  526,    0,  296,
   65, -222,   53,  481,   85,   58,  -33,    0,    0,    0,
 -161,   92,    0,  495,    0,   -9,    0, -160,  543,   74,
    0,   52,   91,   69,    0,   96,  104,  108,    0,    0,
  -29,   53,  113,   84,    0,   77,   17,   19, -104,  -31,
  146,  149,  317,  -60,    0,   85,    0, -222,    0,  296,
  353,   47,    0,    0,    0,  272,  286,    0,    0,    0,
  296,  289,  306,  -76,  155,  154,  174,    0,  -35,  -90,
    0,    0,    0,    0,    0,  178, -224,    0,  296,    0,
  -28,    0,  296,    0,    0,  175,  -34,    0,    0,  129,
   84,  393,  318,   18,  296,  296,  -48,  197,   -9,  200,
   -9,   42,  203,    0,  215,    0,    0, -141,    0,    0,
    0,    0,  217,   45,    0,   23,  243,   59,  296,   64,
    0,    0,    0,  387,  247,    0,    0,   79,   84,  259,
    0,    0,    0,    0,    0,   63,    0,   48,   39,  271,
    0,   49,  301,   84,  305,  296,    0,   95,  296,    0,
  296,    0,  100,    0,  560,  106,  560,    0,    0,   97,
  296,   98,  105,  130,    0,  560,  110,  560,  560,  112,
  325,  136,  348,  352,  356,  135,    0, -115,  139,  158,
    0,    0,  359,    0,    0,    0,    0,    0,  159,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -98,  -74,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  128,    0,    0,    0,  -41,    0,    0,    0,
    0,    0,    0,    0,    0,  -16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  422,  457,    0,    0,
    0,  145,    0,  165,    0,    0,    0,    0,    0,    0,
    0,  391,    0,    0,    0,    0,    0,    0,    0,    0,
  144,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  577,    0,    0,    0,    0,
  418,    0,    0,    0,    0,    0,    0,    0,    9,    0,
   34,    0,    0,    0,    0,    0,    0,  194,    0,    0,
    0,    0,    0,    0,    0,  376,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   94,  447,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  219,    0,    0,    0,    0,    0,    0,
    0,    0,  245,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  111,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  474,    2,  572,    0,  590,    0,    0,    0,    0,  -77,
    0,  -51,    0,    0,    0,    0,  -26,  406,  276,  601,
    4,  -47,  -65,  -27,    0,  -12,  -55,    0,
};
final static int YYTABLESIZE=854;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         61,
   61,   61,   61,   61,   66,   61,   31,   86,   31,   87,
  107,   90,   88,   89,  122,   57,  138,   61,   61,   61,
   61,  117,   75,   77,   53,  125,   53,   53,   53,   57,
   29,   50,   93,  145,    1,   68,   34,   92,  129,  131,
   39,   40,   53,   53,   53,   53,    2,   61,   35,   49,
   57,   49,   49,   49,   11,   12,   13,  110,  157,   86,
   86,   87,   87,  119,  134,  136,  155,   49,   49,   49,
   49,  160,   51,  143,   50,  131,   50,   50,   50,   59,
  146,  120,  163,   60,   86,  169,   87,  127,  183,   78,
  126,   32,   50,   50,   50,   50,   69,   42,  178,  173,
   79,   86,   41,   87,  175,   65,   86,   94,   87,   41,
  190,  100,  192,  193,   98,  194,  109,  146,   41,  180,
   53,   86,   54,   87,   42,  202,   86,  166,   87,   41,
  167,   80,  102,  197,  191,  200,   41,  201,  203,   41,
  126,  126,    4,  111,  206,  204,  209,  210,  126,  101,
    5,    6,   86,  112,  103,  218,  219,    8,    9,    5,
   71,   10,  104,   11,   12,   13,  105,    5,    5,   84,
  205,  108,    5,  126,    5,    5,  213,    5,    5,  126,
    5,    5,    5,    7,   54,  113,   30,   54,   11,   12,
   13,    7,    7,   34,  140,  137,    7,   57,    7,    7,
   57,    7,    7,   29,    7,    7,    7,  114,   83,   57,
  118,   84,   85,  139,  141,   61,   61,  144,   61,   61,
   61,   61,   61,   47,   61,   61,   61,   30,  106,   61,
  149,   61,   61,  151,   61,   61,  152,   61,   61,   61,
   53,   53,   61,   53,   53,   53,   53,   53,  142,   53,
   53,   53,   74,  153,   53,  161,   53,   53,  162,   53,
   53,  164,   53,   53,   53,   49,   49,   53,   49,   49,
   49,   49,   49,  165,   49,   49,   49,   48,  168,   49,
  171,   49,   49,  172,   49,   49,  179,   49,   49,   49,
   50,   50,   49,   50,   50,   50,   50,   50,  181,   50,
   50,   50,  145,   76,   50,  184,   50,   50,  185,   50,
   50,  186,   50,   50,   50,   37,   41,   50,  187,   38,
   39,   40,   37,   11,   12,   13,   38,   39,   40,   96,
   41,   37,   34,   41,  182,   38,   39,   40,   97,   76,
   41,  188,   37,   11,   12,   13,   38,   39,   40,   37,
   41,   86,   37,   38,   39,   40,   38,   39,   40,   86,
   86,   86,   41,  189,   86,  212,   86,   86,   84,   86,
   86,  195,   86,   86,   86,  198,   84,   84,   84,   31,
  207,   84,  211,   84,   84,   30,   84,   84,  214,   84,
   84,   84,  215,   30,   30,   86,  216,  123,   30,  222,
   30,   30,   29,   30,   30,  217,   30,   30,   30,  220,
   29,   29,   90,   88,   89,   29,   44,   29,   29,  170,
   29,   29,   47,   29,   29,   29,  176,  177,  221,  223,
   47,   47,   62,  154,    0,   47,  126,   47,   47,    0,
   47,   47,    0,   47,   47,   47,   90,   88,   89,    0,
    0,   74,   90,   88,   89,    0,    0,    0,   54,   74,
   74,   54,   32,    0,   74,    0,   74,   74,    0,   74,
   74,    0,   74,   74,   74,   28,   48,   54,   54,   54,
   49,    0,    0,    0,   48,   48,    0,   55,    0,   48,
   55,   48,   48,    0,   48,   48,    0,   48,   48,   48,
    0,    0,   76,    0,    0,    0,   55,   55,   55,    0,
   76,   76,    0,    0,    0,   76,    0,   76,   76,    0,
   76,   76,    0,   76,   76,   76,    0,  128,    0,   37,
    0,    0,    0,   38,   39,   40,    0,   86,    0,   87,
    0,  130,    0,   37,  133,    0,   37,   38,   39,   40,
   38,   39,   40,   37,   90,   88,   89,   38,   39,   40,
    0,  135,    0,   37,    0,    0,    0,   38,   39,   40,
    0,    0,    0,  130,   70,   37,    0,    0,    0,  156,
   39,   40,    5,    6,    0,   58,    0,  115,    0,    8,
   18,   18,   71,   10,    0,    0,   18,   29,   30,   58,
    0,    0,    0,   18,    0,   72,   44,    0,    0,   83,
   37,    0,   84,   85,   38,   39,   40,   18,    4,    0,
   58,    0,    0,   18,    0,    0,    5,    6,    0,    0,
   64,   67,    0,    8,    9,   44,   44,   10,   18,   11,
   12,   13,   81,   83,  116,    0,   84,   85,   23,   83,
   99,    0,   84,   85,    0,    0,   23,   23,  196,    0,
  199,   23,   18,   23,    0,    0,   23,   23,    0,    0,
    0,  208,    0,    0,   54,    0,   44,   54,   54,    9,
    0,  124,    0,    0,    0,    0,    0,    9,    9,    0,
    0,  132,    9,    0,    9,    9,    4,    9,    9,    0,
    9,    9,    9,   55,    5,    6,   55,   55,    7,  148,
    0,    8,    9,  150,   11,   10,    0,   11,   12,   13,
    0,    0,   11,   11,    0,  158,  159,   11,    0,   11,
   11,    0,   11,   11,    0,   11,   11,   11,   70,    0,
    0,    0,    0,    0,    0,    0,    5,    6,    0,  174,
    4,   83,    0,    8,   84,   85,   71,   10,    5,    6,
    0,    0,    0,   56,    0,    8,    9,   58,    0,   10,
   58,   11,   12,   13,   18,    0,   18,    0,    0,   58,
    0,    0,    0,    4,    0,   18,    0,   18,   18,    0,
    0,    5,    6,    0,    0,    0,   63,   18,    8,    9,
    4,    0,   10,    0,   11,   12,   13,    0,    5,    6,
    0,    0,    0,   95,    0,    8,    9,    4,    0,   10,
    0,   11,   12,   13,    0,    5,    6,    0,    0,    0,
    0,    0,    8,    9,   24,   71,   10,    0,   11,   12,
   13,    0,   24,   24,    0,    0,    0,   24,    0,   24,
    0,    0,   24,   24,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   31,   47,   40,   43,   40,   45,
   40,   60,   61,   62,   80,   14,   94,   59,   60,   61,
   62,   73,   35,   36,   41,   81,   43,   44,   45,   28,
  258,   40,   42,  258,  258,   32,  270,   47,   86,   87,
  263,  264,   59,   60,   61,   62,  270,  275,  282,   41,
   49,   43,   44,   45,  279,  280,  281,   41,   41,   43,
   43,   45,   45,   76,   92,   93,  122,   59,   60,   61,
   62,  127,  278,  100,   41,  123,   43,   44,   45,   59,
  107,   78,   41,   59,   43,   41,   45,   41,  166,  123,
   44,  123,   59,   60,   61,   62,   44,   40,  154,   41,
  262,   43,   45,   45,   41,   41,   43,  268,   45,   45,
  176,   60,  178,  179,   41,  181,   40,  144,   45,   41,
  256,   43,  258,   45,   40,  191,   43,  269,   45,   45,
  272,   40,   64,  185,   40,  187,   45,   41,   41,   45,
   44,   44,  258,  125,  196,   41,  198,  199,   44,   59,
  266,  267,   59,  258,   59,  271,  208,  273,  274,  258,
  276,  277,   59,  279,  280,  281,   59,  266,  267,   59,
   41,   59,  271,   44,  273,  274,   41,  276,  277,   44,
  279,  280,  281,  258,   41,   40,   59,   44,  279,  280,
  281,  266,  267,  270,   41,  272,  271,  196,  273,  274,
  199,  276,  277,   59,  279,  280,  281,   59,  257,  208,
  271,  260,  261,   59,   41,  257,  258,   40,  260,  261,
  262,  263,  264,   59,  266,  267,  268,  259,  258,  271,
  259,  273,  274,   59,  276,  277,  271,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,  284,  266,
  267,  268,   59,  125,  271,   59,  273,  274,   59,  276,
  277,   59,  279,  280,  281,  257,  258,  284,  260,  261,
  262,  263,  264,   59,  266,  267,  268,   59,   62,  271,
  258,  273,  274,   41,  276,  277,   40,  279,  280,  281,
  257,  258,  284,  260,  261,  262,  263,  264,   40,  266,
  267,  268,  258,   59,  271,  258,  273,  274,  270,  276,
  277,   41,  279,  280,  281,  258,   45,  284,  270,  262,
  263,  264,  258,  279,  280,  281,  262,  263,  264,  256,
   45,  258,  270,   45,  272,  262,  263,  264,  265,  282,
   45,   41,  258,  279,  280,  281,  262,  263,  264,  258,
   45,  258,  258,  262,  263,  264,  262,  263,  264,  266,
  267,  268,   45,   59,  271,   41,  273,  274,  258,  276,
  277,  272,  279,  280,  281,  270,  266,  267,  268,   40,
  271,  271,  271,  273,  274,  258,  276,  277,   41,  279,
  280,  281,   41,  266,  267,   43,   41,   45,  271,   41,
  273,  274,  258,  276,  277,  271,  279,  280,  281,  271,
  266,  267,   60,   61,   62,  271,   41,  273,  274,  144,
  276,  277,  258,  279,  280,  281,   40,   41,  271,  271,
  266,  267,   27,   41,   -1,  271,   44,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,   60,   61,   62,   -1,
   -1,  258,   60,   61,   62,   -1,   -1,   -1,   41,  266,
  267,   44,  123,   -1,  271,   -1,  273,  274,   -1,  276,
  277,   -1,  279,  280,  281,    2,  258,   60,   61,   62,
    7,   -1,   -1,   -1,  266,  267,   -1,   41,   -1,  271,
   44,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
   -1,   -1,  258,   -1,   -1,   -1,   60,   61,   62,   -1,
  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,   -1,  256,   -1,  258,
   -1,   -1,   -1,  262,  263,  264,   -1,   43,   -1,   45,
   -1,  256,   -1,  258,  256,   -1,  258,  262,  263,  264,
  262,  263,  264,  258,   60,   61,   62,  262,  263,  264,
   -1,  256,   -1,  258,   -1,   -1,   -1,  262,  263,  264,
   -1,   -1,   -1,  256,  258,  258,   -1,   -1,   -1,  262,
  263,  264,  266,  267,   -1,   14,   -1,  271,   -1,  273,
    1,    2,  276,  277,   -1,   -1,    7,  258,  259,   28,
   -1,   -1,   -1,   14,   -1,   34,    6,   -1,   -1,  257,
  258,   -1,  260,  261,  262,  263,  264,   28,  258,   -1,
   49,   -1,   -1,   34,   -1,   -1,  266,  267,   -1,   -1,
   30,   31,   -1,  273,  274,   35,   36,  277,   49,  279,
  280,  281,   42,  257,   73,   -1,  260,  261,  258,  257,
   50,   -1,  260,  261,   -1,   -1,  266,  267,  185,   -1,
  187,  271,   73,  273,   -1,   -1,  276,  277,   -1,   -1,
   -1,  198,   -1,   -1,  257,   -1,   76,  260,  261,  258,
   -1,   81,   -1,   -1,   -1,   -1,   -1,  266,  267,   -1,
   -1,   91,  271,   -1,  273,  274,  258,  276,  277,   -1,
  279,  280,  281,  257,  266,  267,  260,  261,  270,  109,
   -1,  273,  274,  113,  258,  277,   -1,  279,  280,  281,
   -1,   -1,  266,  267,   -1,  125,  126,  271,   -1,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,  258,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  266,  267,   -1,  149,
  258,  257,   -1,  273,  260,  261,  276,  277,  266,  267,
   -1,   -1,   -1,  271,   -1,  273,  274,  196,   -1,  277,
  199,  279,  280,  281,  185,   -1,  187,   -1,   -1,  208,
   -1,   -1,   -1,  258,   -1,  196,   -1,  198,  199,   -1,
   -1,  266,  267,   -1,   -1,   -1,  271,  208,  273,  274,
  258,   -1,  277,   -1,  279,  280,  281,   -1,  266,  267,
   -1,   -1,   -1,  271,   -1,  273,  274,  258,   -1,  277,
   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,   -1,
   -1,   -1,  273,  274,  258,  276,  277,   -1,  279,  280,
  281,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,
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
"factor : SINGLE_CONSTANTE",
"factor : '-' SINGLE_CONSTANTE",
"factor : invocacion_fun",
"etiqueta : ID '@'",
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

//#line 181 "gramatica.y"

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


//#line 620 "Parser.java"
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
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 7:
//#line 22 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 9:
//#line 24 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 11:
//#line 26 "gramatica.y"
{System.out.println("Falta ; " + "antes de la linea: " + lexico.getContadorLinea());}
break;
case 12:
//#line 29 "gramatica.y"
{System.out.println("Se detecto: Sentencia if ");}
break;
case 13:
//#line 30 "gramatica.y"
{System.out.println("Se detecto: Invocacion a funcion " + " en linea: " + lexico.getContadorLinea());}
break;
case 14:
//#line 31 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + " en linea: " + lexico.getContadorLinea());}
break;
case 15:
//#line 32 "gramatica.y"
{System.out.println("Se detecto: Ciclo repeat until ");}
break;
case 16:
//#line 33 "gramatica.y"
{System.out.println("Se detecto: Sentencia GOTO " + " en linea: " + lexico.getContadorLinea());}
break;
case 17:
//#line 34 "gramatica.y"
{System.out.println("Se detecto: Salida " + " en linea: " + lexico.getContadorLinea());}
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
//#line 103 "gramatica.y"
{System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());}
break;
case 57:
//#line 104 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 59:
//#line 106 "gramatica.y"
{System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 60:
//#line 107 "gramatica.y"
{System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 110 "gramatica.y"
{System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());}
break;
case 64:
//#line 113 "gramatica.y"
{lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea()));}
break;
case 65:
//#line 114 "gramatica.y"
{lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, truncarFueraRango(val_peek(0).sval+val_peek(1).sval, lexico.getContadorLinea()));}
break;
case 66:
//#line 115 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 72:
//#line 128 "gramatica.y"
{System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
break;
case 74:
//#line 132 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 75:
//#line 133 "gramatica.y"
{System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
break;
case 76:
//#line 134 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 77:
//#line 135 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 78:
//#line 136 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 140 "gramatica.y"
{System.out.println("Se detecto: comparación");}
break;
case 80:
//#line 141 "gramatica.y"
{System.out.println("Se detecto: comparación múltiple");}
break;
case 81:
//#line 142 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 82:
//#line 143 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 83:
//#line 144 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 84:
//#line 145 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 146 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 86:
//#line 147 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 87:
//#line 148 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
break;
case 88:
//#line 149 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 89:
//#line 150 "gramatica.y"
{System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
break;
case 98:
//#line 163 "gramatica.y"
{System.out.println("Error, falta parametro " + "en linea: " + lexico.getContadorLinea());}
break;
case 99:
//#line 164 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 101:
//#line 168 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 102:
//#line 169 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 106:
//#line 178 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
//#line 1009 "Parser.java"
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
