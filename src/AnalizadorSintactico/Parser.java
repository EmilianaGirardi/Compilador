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
    0,    0,    0,    0,    0,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    3,    3,    3,   10,
   10,   10,   11,   11,   11,   11,    2,    2,    2,    2,
   14,   16,   18,   18,   17,   17,   17,   13,   13,   13,
   19,   19,   19,   20,   20,   12,   12,    6,    6,   21,
   21,   21,   21,   21,   24,   24,   23,   23,   23,   23,
   23,   25,   25,   25,   25,   26,   22,   22,   22,    5,
    5,    5,    4,    4,    4,    4,    4,    4,   27,   27,
   27,   27,   27,   27,   27,   27,   27,   27,   27,   28,
   28,   28,   28,   28,   28,    9,    9,    9,    9,    7,
    7,    7,   15,   29,    8,    8,
};
final static short yylen[] = {                            2,
    5,    4,    4,    3,    2,    2,    1,    2,    1,    3,
    2,    3,    2,    1,    1,    1,    1,    1,    1,    3,
    4,    3,    2,    3,    1,    2,    1,    1,    1,    1,
    2,    2,    1,    3,    1,    1,    1,    9,    8,    8,
    2,    1,    1,    1,    2,    5,    2,    3,    6,    3,
    3,    4,    4,    1,    1,    3,    3,    3,    1,    4,
    4,    1,    4,    1,    1,    2,    1,    1,    1,    4,
    7,    3,    5,    4,    7,    6,    4,    6,    5,    9,
    8,    8,    8,    8,    4,    4,    4,    8,    5,    1,
    1,    1,    1,    1,    1,    4,    4,    3,    4,    4,
    3,    3,    6,    1,    2,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   36,   37,   35,    0,    0,    0,   14,   15,   16,   17,
   18,   19,   27,   28,   29,   30,    0,    0,   33,    0,
    0,    0,    0,    0,    0,    0,    0,   67,   68,   69,
    0,   65,    0,   64,    0,   59,    0,    0,    0,  104,
    0,    0,    0,  105,    0,    0,    0,    6,    8,    0,
    0,    0,    0,   72,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  101,    0,  102,    0,    0,    0,
    0,   91,   92,   90,    0,    0,   93,   94,   95,    0,
    0,    0,    0,    0,    0,    0,   98,    0,    0,  106,
   66,    3,   10,   12,    0,    0,    2,    0,   70,    0,
   34,   47,    0,   23,   20,    0,    0,   22,  100,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,    0,   57,   77,    0,    1,   99,   96,
   97,    0,    0,   43,    0,    0,    0,    0,    0,   24,
   21,   63,    0,    0,   87,    0,    0,    0,   52,   53,
   85,   61,   60,    0,   73,    0,    0,    0,   41,    0,
    0,    0,    0,    0,   89,    0,    0,   79,    0,   78,
    0,  103,    0,    0,    0,   71,   46,    0,    0,    0,
    0,    0,   75,    0,   44,    0,    0,    0,    0,    0,
    0,    0,    0,   45,   40,    0,   39,   88,    0,   82,
   81,   83,   38,   80,
};
final static short yydgoto[] = {                          3,
  194,   15,   16,   17,   42,   19,   20,   21,   22,   36,
   73,  195,   23,   24,   25,   26,   27,   33,  146,  196,
  121,   44,   45,   81,   46,   54,   47,   90,   51,
};
final static short yysindex[] = {                      -231,
 -102,  562,    0,  -18, -172,  140,  562,   10, -237, -157,
    0,    0,    0,  481,   11,   26,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -235,  514,    0,  226,
  -31,  -81,   98, -209,  140,  -40,  -26,    0,    0,    0,
  166,    0,  485,    0,   75,    0, -130,  538,  -28,    0,
  116,  132,   89,    0,  133,  144,  160,    0,    0,  -37,
   98,  170,   85,    0,  185,   38,  113,    2,  180,  -15,
  231,  207,  582,   13,    0,  140,    0,  -81,  226,  471,
  -20,    0,    0,    0,  -68,  -47,    0,    0,    0,  226,
  194,  219,  -25,  217,  248,  254,    0,  -41,  174,    0,
    0,    0,    0,    0,  259, -186,    0,  226,    0,   41,
    0,    0,  226,    0,    0,  243,   37,    0,    0,  187,
   85,  507,   82,  226,  226,  -53,  265,   75,  269,   75,
   94,  270,    0,  272,    0,    0, -126,    0,    0,    0,
    0,  251,   99,    0,   79,  293,  169,  226,  420,    0,
    0,    0,   -6,  301,    0,  462,   85,  313,    0,    0,
    0,    0,    0,  236,    0,  100,   90,  322,    0,   96,
  329,   85,  316,  226,    0,  173,  226,    0,  226,    0,
  104,    0, -122,  114, -122,    0,    0,  119,  226,  125,
  129,  161,    0, -122,    0,  122, -122,  130,  344,  212,
  346,  349,  364,    0,    0,  135,    0,    0,  369,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  411,  347,  374,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  167,    0,    0,    0,    1,    0,    0,    0,
    0,    0,    0,    0,   30,    0,    0,  413,    0,    0,
    0,    0,    0,    0,    0,  400,  426,    0,    0,    0,
  193,    0,  220,    0,    0,    0,    0,    0,    0,    0,
    0,  594,    0,    0,    0,    0,    0,    0,    0,  229,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  606,    0,    0,    0,    0,
  517,    0,    0,    0,    0,    0,    0,   59,    0,   88,
    0,    0,    0,    0,    0,    0,  246,    0,    0,    0,
    0,    0,    0,    0,  375,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  115,  546,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  283,    0,    0,    0,    0,    0,    0,    0,    0,
  318,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  141,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  105,    5,    3,    0,    4,    0,    0,    0,    0,  -78,
    0,   -8,    0,    0,    0,    0,  -19,  392,  284, -101,
  689,  -12,   76,  -63,   93,    0,   -7,  -44,    0,
};
final static int YYTABLESIZE=883;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   62,   85,  106,   86,   18,   18,   89,   87,   88,   64,
   18,   65,   97,   31,  137,  122,   57,   18,   56,   67,
  126,   31,   29,  125,   31,   74,    1,   75,   77,   54,
   57,   18,   56,  174,  175,  124,   72,   18,    2,   60,
   50,   62,   62,   62,   62,   62,   69,   62,   70,   49,
   57,   18,   56,   89,   87,   88,    5,    6,   50,   62,
   62,   62,   62,    8,  117,  120,   71,   10,  119,   58,
   54,  144,   54,   54,   54,  116,   18,  154,  109,  142,
   85,  158,   86,  198,   59,  181,  145,   51,   54,   54,
   54,   54,   11,   12,   13,  206,   78,   34,   52,   50,
   53,   50,   50,   50,   32,   14,   28,   32,  176,   35,
  188,   48,  190,  191,   86,  192,   92,   50,   50,   50,
   50,   91,  155,  145,   85,  200,   86,   85,   51,   86,
   51,   51,   51,   69,  161,    4,   85,   93,   86,  167,
   84,   68,  164,    5,    6,  165,   51,   51,   51,   51,
    8,    9,  101,   71,   10,    4,   11,   12,   13,  199,
  128,  130,  125,    5,    6,  201,   32,    7,  125,  202,
    8,    9,  125,   86,   10,   99,   11,   12,   13,   41,
   38,   39,   40,  133,  135,  204,   18,  127,   18,   37,
  100,  102,   31,   38,   39,   40,   57,   18,   56,   84,
   18,  203,  103,   82,  125,   79,   83,   84,  129,  171,
   37,   85,  189,   86,   38,   39,   40,   37,  104,   48,
  105,   38,   39,   40,  108,   32,   37,   95,  107,   37,
   38,   39,   40,   38,   39,   40,   96,  110,  112,   29,
   30,   76,  141,   30,   34,   74,  136,   11,   12,   13,
   82,   31,  209,   83,   84,  125,   62,   62,   62,  111,
   62,   62,   62,   62,   62,  114,   62,   62,   62,   55,
  113,   62,   55,   62,   62,  138,   62,   62,   48,   62,
   62,   62,   49,  118,   62,   54,   54,   54,  139,   54,
   54,   54,   54,   54,  140,   54,   54,   54,  143,  148,
   54,  150,   54,   54,   74,   54,   54,  151,   54,   54,
   54,  152,  166,   54,   50,   50,   50,   76,   50,   50,
   50,   50,   50,  159,   50,   50,   50,  160,  162,   50,
  163,   50,   50,  170,   50,   50,  169,   50,   50,   50,
  177,   49,   50,   51,   51,   51,    7,   51,   51,   51,
   51,   51,  179,   51,   51,   51,  144,  182,   51,  183,
   51,   51,  184,   51,   51,  185,   51,   51,   51,  186,
   86,   51,   86,    9,  187,  193,   76,   11,   12,   13,
   86,   86,   86,  197,  208,   86,  210,   86,   86,  211,
   86,   86,  205,   86,   86,   86,   84,   37,   84,   11,
  207,   38,   39,   40,  212,  213,   84,   84,   84,  214,
    5,   84,    4,   84,   84,   42,   84,   84,   61,   84,
   84,   84,   32,   37,   32,   13,  168,   38,   39,   40,
   37,    0,   32,   32,   38,   39,   40,   32,    0,   32,
   32,    0,   32,   32,    0,   32,   32,   32,   31,  132,
   31,   37,   11,   12,   13,   38,   39,   40,   31,   31,
  173,    0,   85,   31,   86,   31,   31,    0,   31,   31,
    0,   31,   31,   31,  134,   48,   37,   48,    0,    0,
   38,   39,   40,   37,    0,   48,   48,   38,   39,   40,
   48,    0,   48,   48,    0,   48,   48,    0,   48,   48,
   48,   74,  178,   74,   85,   34,   86,  180,    0,    0,
    0,   74,   74,   85,    0,   86,   74,    0,   74,   74,
    0,   74,   74,    0,   74,   74,   74,   85,    0,   86,
   89,   87,   88,    0,    0,    0,    0,    0,   49,    0,
   49,    0,    0,    0,   89,   87,   88,  153,   49,   49,
  125,    0,    0,   49,    0,   49,   49,   55,   49,   49,
   55,   49,   49,   49,    0,    0,   89,   87,   88,    0,
    0,    0,    0,   76,    0,   76,   55,   55,   55,    0,
    0,    0,    0,   76,   76,    0,   56,    0,   76,   56,
   76,   76,    0,   76,   76,    0,   76,   76,   76,    0,
    0,    0,    7,    0,    7,   56,   56,   56,    0,    0,
    0,    0,    7,    7,    0,    0,    0,    7,    0,    7,
    7,    0,    7,    7,    0,    7,    7,    7,    0,    9,
    0,    9,    0,    0,    0,    0,    0,    0,    0,    9,
    9,    0,    0,    0,    9,    0,    9,    9,    0,    9,
    9,    0,    9,    9,    9,   11,    0,   11,    0,    0,
    0,    0,    0,    0,    0,   11,   11,    0,    0,    0,
   11,    0,   11,   11,    0,   11,   11,    0,   11,   11,
   11,   13,    0,   13,    0,    0,    0,    0,    0,    0,
    0,   13,   13,    0,   43,    0,   13,    0,   13,   13,
    0,   13,   13,    0,   13,   13,   13,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   63,   66,
    0,    0,    0,   43,   43,    0,    0,   82,   37,   80,
   83,   84,   38,   39,   40,    0,    0,   98,    4,    0,
    0,   82,    0,    0,   83,   84,    5,    6,    0,    0,
    0,   55,    0,    8,    9,    0,    0,   10,    0,   11,
   12,   13,    0,   82,   43,    0,   83,   84,  123,    0,
    0,    4,    0,   55,    0,    0,   55,   55,  131,    5,
    6,    0,    0,    0,   62,    0,    8,    9,    0,    0,
   10,    0,   11,   12,   13,    4,  147,    0,    0,    0,
    0,  149,   56,    5,    6,   56,   56,    0,   94,    0,
    8,    9,  156,  157,   10,    0,   11,   12,   13,    4,
    0,    0,    0,    0,    0,    0,    0,    5,    6,    0,
    0,    0,    0,    0,    8,    9,  172,   69,   10,   70,
   11,   12,   13,    0,    0,    0,    0,    5,    6,   25,
    0,   25,  115,    0,    8,    0,    0,   71,   10,   25,
   25,   26,    0,   26,   25,    0,   25,    0,    0,   25,
   25,   26,   26,    0,    0,    0,   26,    0,   26,    0,
    0,   26,   26,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   43,   40,   45,    1,    2,   60,   61,   62,   41,
    7,   31,   41,   40,   93,   79,   14,   14,   14,   32,
   41,   40,  258,   44,   40,   34,  258,   35,   36,    0,
   28,   28,   28,   40,   41,   80,   34,   34,  270,  275,
  278,   41,   42,   43,   44,   45,  256,   47,  258,   40,
   48,   48,   48,   60,   61,   62,  266,  267,    0,   59,
   60,   61,   62,  273,   73,   78,  276,  277,   76,   59,
   41,  258,   43,   44,   45,   73,   73,  122,   41,   99,
   43,  126,   45,  185,   59,  164,  106,    0,   59,   60,
   61,   62,  279,  280,  281,  197,  123,  270,  256,   41,
  258,   43,   44,   45,  123,    1,    2,  123,  153,  282,
  174,    7,  176,  177,    0,  179,   42,   59,   60,   61,
   62,   47,   41,  143,   43,  189,   45,   43,   41,   45,
   43,   44,   45,  256,   41,  258,   43,  268,   45,   41,
    0,   44,  269,  266,  267,  272,   59,   60,   61,   62,
  273,  274,   64,  276,  277,  258,  279,  280,  281,   41,
   85,   86,   44,  266,  267,   41,    0,  270,   44,   41,
  273,  274,   44,   59,  277,   60,  279,  280,  281,   40,
  262,  263,  264,   91,   92,  194,  183,  256,  185,  258,
   59,   59,    0,  262,  263,  264,  194,  194,  194,   59,
  197,   41,   59,  257,   44,   40,  260,  261,  256,   41,
  258,   43,   40,   45,  262,  263,  264,  258,   59,    0,
  258,  262,  263,  264,   40,   59,  258,  256,   59,  258,
  262,  263,  264,  262,  263,  264,  265,  125,   59,  258,
  259,  282,  284,  259,  270,    0,  272,  279,  280,  281,
  257,   59,   41,  260,  261,   44,  256,  257,  258,  258,
  260,  261,  262,  263,  264,   59,  266,  267,  268,   41,
   40,  271,   44,  273,  274,   59,  276,  277,   59,  279,
  280,  281,    0,  271,  284,  256,  257,  258,   41,  260,
  261,  262,  263,  264,   41,  266,  267,  268,   40,  259,
  271,   59,  273,  274,   59,  276,  277,  271,  279,  280,
  281,  125,   62,  284,  256,  257,  258,    0,  260,  261,
  262,  263,  264,   59,  266,  267,  268,   59,   59,  271,
   59,  273,  274,   41,  276,  277,  258,  279,  280,  281,
   40,   59,  284,  256,  257,  258,    0,  260,  261,  262,
  263,  264,   40,  266,  267,  268,  258,  258,  271,  270,
  273,  274,   41,  276,  277,  270,  279,  280,  281,   41,
  256,  284,  258,    0,   59,  272,   59,  279,  280,  281,
  266,  267,  268,  270,   41,  271,   41,  273,  274,   41,
  276,  277,  271,  279,  280,  281,  256,  258,  258,    0,
  271,  262,  263,  264,   41,  271,  266,  267,  268,   41,
    0,  271,    0,  273,  274,   41,  276,  277,   27,  279,
  280,  281,  256,  258,  258,    0,  143,  262,  263,  264,
  258,   -1,  266,  267,  262,  263,  264,  271,   -1,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,  256,  256,
  258,  258,  279,  280,  281,  262,  263,  264,  266,  267,
   41,   -1,   43,  271,   45,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,  256,  256,  258,  258,   -1,   -1,
  262,  263,  264,  258,   -1,  266,  267,  262,  263,  264,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,  256,   41,  258,   43,  270,   45,  272,   -1,   -1,
   -1,  266,  267,   43,   -1,   45,  271,   -1,  273,  274,
   -1,  276,  277,   -1,  279,  280,  281,   43,   -1,   45,
   60,   61,   62,   -1,   -1,   -1,   -1,   -1,  256,   -1,
  258,   -1,   -1,   -1,   60,   61,   62,   41,  266,  267,
   44,   -1,   -1,  271,   -1,  273,  274,   41,  276,  277,
   44,  279,  280,  281,   -1,   -1,   60,   61,   62,   -1,
   -1,   -1,   -1,  256,   -1,  258,   60,   61,   62,   -1,
   -1,   -1,   -1,  266,  267,   -1,   41,   -1,  271,   44,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,   -1,
   -1,   -1,  256,   -1,  258,   60,   61,   62,   -1,   -1,
   -1,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,   -1,  256,
   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  266,
  267,   -1,   -1,   -1,  271,   -1,  273,  274,   -1,  276,
  277,   -1,  279,  280,  281,  256,   -1,  258,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,  256,   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  266,  267,   -1,    6,   -1,  271,   -1,  273,  274,
   -1,  276,  277,   -1,  279,  280,  281,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   30,   31,
   -1,   -1,   -1,   35,   36,   -1,   -1,  257,  258,   41,
  260,  261,  262,  263,  264,   -1,   -1,   49,  258,   -1,
   -1,  257,   -1,   -1,  260,  261,  266,  267,   -1,   -1,
   -1,  271,   -1,  273,  274,   -1,   -1,  277,   -1,  279,
  280,  281,   -1,  257,   76,   -1,  260,  261,   80,   -1,
   -1,  258,   -1,  257,   -1,   -1,  260,  261,   90,  266,
  267,   -1,   -1,   -1,  271,   -1,  273,  274,   -1,   -1,
  277,   -1,  279,  280,  281,  258,  108,   -1,   -1,   -1,
   -1,  113,  257,  266,  267,  260,  261,   -1,  271,   -1,
  273,  274,  124,  125,  277,   -1,  279,  280,  281,  258,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  266,  267,   -1,
   -1,   -1,   -1,   -1,  273,  274,  148,  256,  277,  258,
  279,  280,  281,   -1,   -1,   -1,   -1,  266,  267,  256,
   -1,  258,  271,   -1,  273,   -1,   -1,  276,  277,  266,
  267,  256,   -1,  258,  271,   -1,  273,   -1,   -1,  276,
  277,  266,  267,   -1,   -1,   -1,  271,   -1,  273,   -1,
   -1,  276,  277,
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
"programa : ID BEGIN conjunto_sentencias",
"programa : ID conjunto_sentencias",
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
"declaracionFun : tipo FUN ID '(' parametro ')' BEGIN cuerpoFun END",
"declaracionFun : tipo FUN '(' parametro ')' BEGIN cuerpoFun END",
"declaracionFun : tipo FUN ID '(' ')' BEGIN cuerpoFun END",
"parametro : tipo ID",
"parametro : tipo",
"parametro : ID",
"cuerpoFun : retorno",
"cuerpoFun : conjunto_sentencias retorno",
"retorno : RET '(' exp_arit ')' ';'",
"retorno : error ';'",
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

//#line 184 "gramatica.y"

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


//#line 623 "Parser.java"
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
case 4:
//#line 18 "gramatica.y"
{System.out.println("Error de delimitador de programa ");}
break;
case 5:
//#line 19 "gramatica.y"
{System.out.println("Error de delimitador de programa ");}
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
case 13:
//#line 28 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 14:
//#line 31 "gramatica.y"
{System.out.println("Se detecto: Sentencia if ");}
break;
case 15:
//#line 32 "gramatica.y"
{System.out.println("Se detecto: Invocacion a funcion " + "en linea: " + lexico.getContadorLinea());}
break;
case 16:
//#line 33 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + "en linea: " + lexico.getContadorLinea());}
break;
case 17:
//#line 34 "gramatica.y"
{System.out.println("Se detecto: Ciclo repeat until ");}
break;
case 18:
//#line 35 "gramatica.y"
{System.out.println("Se detecto: Sentencia GOTO " + "en linea: " + lexico.getContadorLinea());}
break;
case 19:
//#line 36 "gramatica.y"
{System.out.println("Se detecto: Salida " + "en linea: " + lexico.getContadorLinea());}
break;
case 25:
//#line 46 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 26:
//#line 47 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 27:
//#line 50 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion ");}
break;
case 28:
//#line 51 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 29:
//#line 52 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 30:
//#line 53 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 39:
//#line 68 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 40:
//#line 69 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 42:
//#line 74 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 43:
//#line 75 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 47:
//#line 83 "gramatica.y"
{System.out.println("Falta el retorno en funcion ");}
break;
case 50:
//#line 91 "gramatica.y"
{System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());}
break;
case 51:
//#line 92 "gramatica.y"
{System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());}
break;
case 52:
//#line 93 "gramatica.y"
{System.out.println("Error: Falta el término después de '+' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 53:
//#line 94 "gramatica.y"
{System.out.println("Error: Falta el término después de '-' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 57:
//#line 107 "gramatica.y"
{System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());}
break;
case 58:
//#line 108 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 60:
//#line 110 "gramatica.y"
{System.out.println("Error: Falta el factor después de '*' en expresion aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 111 "gramatica.y"
{System.out.println("Error: Falta el factor después de '/' en expresión aritmetica en línea: " + lexico.getContadorLinea());}
break;
case 62:
//#line 114 "gramatica.y"
{System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());}
break;
case 65:
//#line 117 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 67:
//#line 122 "gramatica.y"
{lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea()));}
break;
case 72:
//#line 131 "gramatica.y"
{System.out.println("Error de falta de parámetro en invocación a función en linea: " + lexico.getContadorLinea());}
break;
case 74:
//#line 135 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 75:
//#line 136 "gramatica.y"
{System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
break;
case 76:
//#line 137 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 77:
//#line 138 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 78:
//#line 139 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 143 "gramatica.y"
{System.out.println("Se detecto: comparación");}
break;
case 80:
//#line 144 "gramatica.y"
{System.out.println("Se detecto: comparación múltiple");}
break;
case 81:
//#line 145 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 82:
//#line 146 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 83:
//#line 147 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 84:
//#line 148 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 149 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 86:
//#line 150 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 87:
//#line 151 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
break;
case 88:
//#line 152 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 89:
//#line 153 "gramatica.y"
{System.out.println("Error, falta de lista de expresión aritmetica en comparación " + "en linea: " + lexico.getContadorLinea());}
break;
case 98:
//#line 166 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal " + "en linea: " + lexico.getContadorLinea());}
break;
case 99:
//#line 167 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 101:
//#line 171 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 102:
//#line 172 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 106:
//#line 181 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
//#line 1008 "Parser.java"
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
