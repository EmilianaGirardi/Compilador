package AnalizadorSintactico;//### This file created by BYACC 1.8(/Java extension  1.15)
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
    	import AnalizadorLexico.Lexico;

        import java.io.*;
    	/*import Lex.Lex;*/
//#line 20 "Parser.java"

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
public final static short UNSIGNED=279;
public final static short SINGLE=280;
public final static short TIPO_OCTAL=281;
public final static short UNTIL=282;
public final static short menos=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    3,    3,    3,    3,
   11,   12,   12,   12,   12,    2,    2,    2,    2,   14,
   16,   18,   18,   18,   17,   17,   17,   13,   13,   13,
   19,   19,   19,   20,   20,    7,    7,    6,   21,   21,
   21,   21,   23,   23,   22,   22,   22,   22,   24,   24,
   24,   26,   25,   25,   25,    5,    5,    4,    4,    4,
    4,    4,    4,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   27,   28,   28,   28,   28,   28,   28,   10,
   10,   10,   10,    8,    8,    8,   15,   29,    9,    9,
};
final static short yylen[] = {                            2,
    5,    4,    4,    3,    2,    2,    1,    2,    1,    3,
    2,    3,    2,    1,    1,    1,    1,    1,    1,    1,
    3,    2,    3,    1,    2,    1,    1,    1,    1,    2,
    2,    1,    3,    2,    1,    1,    1,    9,    8,    8,
    2,    1,    1,    1,    2,    4,    4,    3,    3,    3,
    2,    1,    1,    3,    3,    3,    1,    2,    1,    1,
    1,    2,    1,    1,    1,    4,    7,    5,    4,    7,
    6,    4,    6,    5,    9,    8,    8,    8,    8,    4,
    4,    4,    8,    1,    1,    1,    1,    1,    1,    4,
    4,    3,    4,    4,    3,    3,    6,    1,    2,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   36,   37,   35,    0,    0,    0,   14,   15,
   16,   17,   18,   19,   20,   26,   27,   28,   29,    0,
    0,    0,    0,   32,    0,    0,    0,    0,    0,    0,
    0,    0,   63,   64,   65,    0,   61,    0,    0,   57,
   60,    0,    0,    0,   98,    0,    0,    0,    0,   99,
    0,    0,    0,    6,    8,    0,    0,    0,    0,   34,
    0,    0,    0,    0,    0,    0,    0,   95,    0,   96,
   51,   58,    0,    0,    0,   85,   86,   84,    0,    0,
   87,   88,   89,    0,    0,    0,    0,    0,    0,    0,
   92,    0,    0,    0,  100,   62,    3,   10,   12,    0,
    0,    2,   47,    0,   66,   33,   22,   21,    0,   94,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,   55,   72,    0,    1,   93,   90,   91,    0,   46,
    0,   43,    0,    0,    0,   23,    0,    0,   82,    0,
    0,    0,   80,    0,   68,    0,    0,    0,   41,    0,
    0,    0,    0,    0,   74,    0,   73,    0,   97,    0,
    0,    0,   67,    0,    0,    0,    0,    0,   70,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   40,    0,   39,   83,    0,   77,   76,   78,   38,   75,
};
final static short yydgoto[] = {                          3,
  180,   17,   18,   19,   47,   21,   22,   23,   24,   25,
   40,   77,   26,   27,   28,   29,   30,   37,  144,  182,
  121,   49,   85,   50,   51,   60,   52,   94,   56,
};
final static short yysindex[] = {                      -247,
  507,  619,    0,  -25,  -18, -258,  168,  619,   13, -252,
   18, -192,    0,    0,    0,  537,   22,   35,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -239,
  563,  -93, -217,    0,  -93, -119,   33, -150,  168,  -40,
  -52,   55,    0,    0,    0,  199,    0,  597,  -14,    0,
    0, -161,  593,  -28,    0,   52,  -93,   54,   60,    0,
   69,   75,   76,    0,    0,  -37,   33,   87,   97,    0,
  110,  119,  111,  -94,  -34,  113,  645,    0,  168,    0,
    0,    0,  -93,  485,    8,    0,    0,    0,  -68,  -68,
    0,    0,    0,  -93,  -81,  -81,  -63,  121,  116,  144,
    0,  -41,   96,  172,    0,    0,    0,    0,    0,  146,
 -211,    0,    0,  -93,    0,    0,    0,    0,  133,    0,
  110,  143,  188,  -93,  -93,  475,  -81,  -14,  -14,  394,
    0,    0,    0, -232,    0,    0,    0,    0,  152,    0,
  -31,    0,  -39,  191,  409,    0,   -6,  205,    0,  434,
  110,  231,    0,  186,    0,    2,    6,  243,    0,   25,
  248,  -93,  226,  -93,    0,  -93,    0,   27,    0,  619,
   32,  619,    0,   41,  -93,   81,  157,  203,    0,  619,
    0,   37,  619,   42,  277,  212,  287,  288,  290,    0,
    0,   53,    0,    0,  293,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  337,  312,  341,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  167,    0,    0,    0,
    0,    1,    0,    0,    0,    0,    0,    0,   30,    0,
    0,    0,  347,    0,    0,    0,    0,    0,    0,    0,
    0,  370,  398,    0,    0,    0,  193,    0,    0,    0,
  220,    0,    0,    0,    0,  657,    0,    0,    0,    0,
    0,    0,    0,  229,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  669,    0,
  454,    0,    0,    0,    0,    0,    0,   59,   88,    0,
    0,    0,    0,  246,    0,    0,    0,    0,    0,    0,
    0,    0,  316,    0,    0,    0,    0,    0,    0,  115,
  511,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  283,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  424,    0,    0,    0,    0,    0,    0,    0,    0,  450,
    0,    0,    0,    0,  141,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   85,   -2,  120,    0,   19,    0,   -4,    0,    0,    0,
  -89,    0,    0,    0,    0,    0,  -27,  323,  217, -167,
  721,  -58,  -65,  -16,    0,    0,   -1,  -17,    0,
};
final static int YYTABLESIZE=946;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
   59,   89,  111,   90,  184,   36,   81,  134,   72,  157,
    1,   38,  101,   62,   32,  192,   33,  122,   34,   20,
   20,   36,    2,   39,   82,   55,   20,   96,   62,   52,
  128,  129,   95,  162,   20,   66,  154,   78,   80,  155,
   70,   59,   59,   59,   59,   59,  142,   59,  126,   20,
   62,  125,   54,   93,   91,   92,   20,   57,   49,   59,
   59,   59,   59,   58,  168,   59,  124,   13,   14,   15,
   52,   20,   52,   52,   52,  139,   74,  120,  131,  132,
   64,  185,   82,  143,  125,   16,   31,   50,   52,   52,
   52,   52,   53,   65,   36,   20,  174,  176,  177,   49,
  178,   49,   49,   49,  148,    4,   97,   75,  152,  186,
   82,  103,  105,  143,   81,    6,    7,   49,   49,   49,
   49,  187,    9,  106,  125,   11,   12,  107,   50,  163,
   50,   50,   50,  108,  109,   63,   41,  113,   42,   89,
   79,   90,   43,   44,   45,  112,   50,   50,   50,   50,
   63,  115,   89,   89,   90,   90,  136,   76,  114,   13,
   14,   15,   41,  116,   42,  181,   31,  181,   43,   44,
   45,  117,   63,   81,   81,  190,   42,   62,  181,  135,
   43,   44,   45,  147,  137,  141,  125,  127,   20,   42,
   20,  146,   30,   43,   44,   45,  119,  188,   20,   79,
  125,   20,   93,   91,   92,   42,   38,   46,  133,   43,
   44,   45,  140,  156,   89,   41,   90,   42,  159,   48,
  110,   43,   44,   45,   35,   31,  142,   99,  149,   42,
   89,  160,   90,   43,   44,   45,  100,   33,   83,   34,
   35,   79,  138,  189,  164,   69,  125,   13,   14,   15,
   86,   30,  195,   87,   88,  125,   59,   59,   59,  169,
   59,   59,   59,   59,   59,  175,   59,   59,   59,   53,
  166,   59,   53,   59,   59,  170,   59,   59,   48,   59,
   59,   59,   71,  171,   59,   52,   52,   52,  173,   52,
   52,   52,   52,   52,  172,   52,   52,   52,  179,   63,
   52,  183,   52,   52,   69,   52,   52,  191,   52,   52,
   52,    7,  193,   52,   49,   49,   49,  194,   49,   49,
   49,   49,   49,  199,   49,   49,   49,  196,  197,   49,
  198,   49,   49,  200,   49,   49,    5,   49,   49,   49,
    9,   71,   49,   50,   50,   50,    4,   50,   50,   50,
   50,   50,   67,   50,   50,   50,   42,  158,   50,    0,
   50,   50,    0,   50,   50,    0,   50,   50,   50,   11,
   81,   50,   81,   42,   13,   14,   15,   43,   44,   45,
   81,   81,   81,    0,    0,   81,    0,   81,   81,    0,
   81,   81,    0,   81,   81,   81,   79,   13,   79,   86,
    0,    0,   87,   88,    0,    0,   79,   79,   79,    0,
    0,   79,    0,   79,   79,    0,   79,   79,    0,   79,
   79,   79,   31,   41,   31,   42,    0,    0,    0,   43,
   44,   45,   31,   31,  153,    0,   89,   31,   90,   31,
   31,    0,   31,   31,    0,   31,   31,   31,   30,  161,
   30,   89,    0,   90,   41,   38,   42,  167,   30,   30,
   43,   44,   45,   30,    0,   30,   30,    0,   30,   30,
    0,   30,   30,   30,  165,   48,   89,   48,   90,    0,
    0,   41,   17,   42,    0,   48,   48,   43,   44,   45,
   48,    0,   48,   48,   53,   48,   48,   53,   48,   48,
   48,   69,    0,   69,    0,    0,    0,    0,   17,    0,
    0,   69,   69,   53,   53,   53,   69,    0,   69,   69,
    0,   69,   69,    0,   69,   69,   69,   89,    0,   90,
    0,    0,    0,    0,   93,   91,   92,    0,   71,    0,
   71,    0,    0,    0,   93,   91,   92,    0,   71,   71,
    0,   54,    0,   71,   54,   71,   71,    0,   71,   71,
    0,   71,   71,   71,    0,    0,    0,    7,    0,    7,
   54,   54,   54,    0,    0,    0,    0,    7,    7,    0,
    0,    0,    7,    0,    7,    7,    0,    7,    7,    0,
    7,    7,    7,    0,    0,    0,    9,    0,    9,    0,
    0,    0,    0,    0,    0,    0,    9,    9,    0,    0,
    0,    9,    0,    9,    9,    0,    9,    9,    0,    9,
    9,    9,    0,    0,    0,   11,    0,   11,    0,    0,
    0,    0,    0,    0,    0,   11,   11,    0,    0,   89,
   11,   90,   11,   11,    0,   11,   11,    0,   11,   11,
   11,    0,    0,   13,    0,   13,   93,   91,   92,    0,
    0,    0,    0,   13,   13,    0,    0,    0,   13,    0,
   13,   13,    0,   13,   13,    0,   13,   13,   13,   17,
    0,   17,    0,    0,    0,    0,    0,    0,    0,   17,
   17,    0,    0,    0,   44,    0,   17,   17,    0,   17,
   17,    0,   17,   17,   17,   17,    0,   17,    0,    0,
   53,    0,    0,   53,   53,   17,   17,    0,    0,    0,
   45,    0,   17,   17,    0,   17,   17,   48,   17,   17,
   17,   86,    0,    0,   87,   88,    0,    0,    0,    0,
   41,   86,   42,    0,   87,   88,   43,   44,   45,    0,
    0,    0,   69,    0,    0,   71,   73,    0,    0,   48,
   48,    0,    4,    0,    5,    0,   84,   54,    0,    0,
   54,   54,    6,    7,  102,    0,    8,  104,    0,    9,
   10,    0,   11,   12,    0,   13,   14,   15,    0,    0,
    0,    0,    4,    0,    5,    0,    0,    0,    0,   48,
    0,    0,    6,    7,  123,    0,    0,   61,    0,    9,
   10,    0,   11,   12,  130,   13,   14,   15,    4,    0,
    5,    0,    0,    0,    0,    0,    0,    0,    6,    7,
    0,    0,    0,   68,  145,    9,   10,    0,   11,   12,
    0,   13,   14,   15,  150,  151,    0,    0,    4,    0,
    5,    0,    0,   86,    0,    0,   87,   88,    6,    7,
    0,    0,    0,   98,    0,    9,   10,    0,   11,   12,
    0,   13,   14,   15,    4,    0,    5,    0,    0,    0,
    0,    0,    0,    0,    6,    7,    0,    0,    0,    0,
    0,    9,   10,    0,   11,   12,    0,   13,   14,   15,
    4,    0,   75,    0,    0,    0,    0,    0,    0,    0,
    6,    7,   24,    0,   24,  118,    0,    9,    0,    0,
   11,   12,   24,   24,   25,    0,   25,   24,    0,   24,
    0,    0,   24,   24,   25,   25,    0,    0,    0,   25,
    0,   25,    0,    0,   25,   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   43,   40,   45,  172,   40,   59,   97,   36,   41,
  258,  270,   41,   16,   40,  183,  256,   83,  258,    1,
    2,   40,  270,  282,   41,  278,    8,   42,   31,    0,
   89,   90,   47,   40,   16,  275,  269,   39,   40,  272,
  258,   41,   42,   43,   44,   45,  258,   47,   41,   31,
   53,   44,   40,   60,   61,   62,   38,   40,    0,   59,
   60,   61,   62,  256,  154,  258,   84,  279,  280,  281,
   41,   53,   43,   44,   45,  103,   44,   79,   95,   96,
   59,   41,   99,  111,   44,    1,    2,    0,   59,   60,
   61,   62,    8,   59,   40,   77,  162,  163,  164,   41,
  166,   43,   44,   45,  122,  256,  268,  258,  126,  175,
  127,   60,   59,  141,    0,  266,  267,   59,   60,   61,
   62,   41,  273,   64,   44,  276,  277,   59,   41,  147,
   43,   44,   45,   59,   59,   16,  256,   41,  258,   43,
    0,   45,  262,  263,  264,   59,   59,   60,   61,   62,
   31,   41,   43,   43,   45,   45,   41,   38,   40,  279,
  280,  281,  256,  258,  258,  170,    0,  172,  262,  263,
  264,   59,   53,   59,   59,  180,  258,  180,  183,   59,
  262,  263,  264,   41,   41,   40,   44,  256,  170,  258,
  172,   59,    0,  262,  263,  264,   77,   41,  180,   59,
   44,  183,   60,   61,   62,  258,  270,   40,  272,  262,
  263,  264,   41,   62,   43,  256,   45,  258,  258,    0,
  258,  262,  263,  264,  259,   59,  258,  256,   41,  258,
   43,   41,   45,  262,  263,  264,  265,  256,   40,  258,
  259,  282,  284,   41,   40,    0,   44,  279,  280,  281,
  257,   59,   41,  260,  261,   44,  256,  257,  258,  258,
  260,  261,  262,  263,  264,   40,  266,  267,  268,   41,
   40,  271,   44,  273,  274,  270,  276,  277,   59,  279,
  280,  281,    0,   41,  284,  256,  257,  258,   41,  260,
  261,  262,  263,  264,  270,  266,  267,  268,  272,  180,
  271,  270,  273,  274,   59,  276,  277,  271,  279,  280,
  281,    0,  271,  284,  256,  257,  258,   41,  260,  261,
  262,  263,  264,  271,  266,  267,  268,   41,   41,  271,
   41,  273,  274,   41,  276,  277,    0,  279,  280,  281,
    0,   59,  284,  256,  257,  258,    0,  260,  261,  262,
  263,  264,   30,  266,  267,  268,   41,  141,  271,   -1,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,    0,
  256,  284,  258,  258,  279,  280,  281,  262,  263,  264,
  266,  267,  268,   -1,   -1,  271,   -1,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,  256,    0,  258,  257,
   -1,   -1,  260,  261,   -1,   -1,  266,  267,  268,   -1,
   -1,  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,
  280,  281,  256,  256,  258,  258,   -1,   -1,   -1,  262,
  263,  264,  266,  267,   41,   -1,   43,  271,   45,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,  256,   41,
  258,   43,   -1,   45,  256,  270,  258,  272,  266,  267,
  262,  263,  264,  271,   -1,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,   41,  256,   43,  258,   45,   -1,
   -1,  256,   59,  258,   -1,  266,  267,  262,  263,  264,
  271,   -1,  273,  274,   41,  276,  277,   44,  279,  280,
  281,  256,   -1,  258,   -1,   -1,   -1,   -1,   59,   -1,
   -1,  266,  267,   60,   61,   62,  271,   -1,  273,  274,
   -1,  276,  277,   -1,  279,  280,  281,   43,   -1,   45,
   -1,   -1,   -1,   -1,   60,   61,   62,   -1,  256,   -1,
  258,   -1,   -1,   -1,   60,   61,   62,   -1,  266,  267,
   -1,   41,   -1,  271,   44,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,   -1,   -1,   -1,  256,   -1,  258,
   60,   61,   62,   -1,   -1,   -1,   -1,  266,  267,   -1,
   -1,   -1,  271,   -1,  273,  274,   -1,  276,  277,   -1,
  279,  280,  281,   -1,   -1,   -1,  256,   -1,  258,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  266,  267,   -1,   -1,
   -1,  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,
  280,  281,   -1,   -1,   -1,  256,   -1,  258,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  266,  267,   -1,   -1,   43,
  271,   45,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,   -1,   -1,  256,   -1,  258,   60,   61,   62,   -1,
   -1,   -1,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,  256,
   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  266,
  267,   -1,   -1,   -1,  271,   -1,  273,  274,   -1,  276,
  277,   -1,  279,  280,  281,  256,   -1,  258,   -1,   -1,
  257,   -1,   -1,  260,  261,  266,  267,   -1,   -1,   -1,
  271,   -1,  273,  274,   -1,  276,  277,    7,  279,  280,
  281,  257,   -1,   -1,  260,  261,   -1,   -1,   -1,   -1,
  256,  257,  258,   -1,  260,  261,  262,  263,  264,   -1,
   -1,   -1,   32,   -1,   -1,   35,   36,   -1,   -1,   39,
   40,   -1,  256,   -1,  258,   -1,   46,  257,   -1,   -1,
  260,  261,  266,  267,   54,   -1,  270,   57,   -1,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,   -1,   -1,
   -1,   -1,  256,   -1,  258,   -1,   -1,   -1,   -1,   79,
   -1,   -1,  266,  267,   84,   -1,   -1,  271,   -1,  273,
  274,   -1,  276,  277,   94,  279,  280,  281,  256,   -1,
  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  266,  267,
   -1,   -1,   -1,  271,  114,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,  124,  125,   -1,   -1,  256,   -1,
  258,   -1,   -1,  257,   -1,   -1,  260,  261,  266,  267,
   -1,   -1,   -1,  271,   -1,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,  256,   -1,  258,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  266,  267,   -1,   -1,   -1,   -1,
   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,  281,
  256,   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  266,  267,  256,   -1,  258,  271,   -1,  273,   -1,   -1,
  276,  277,  266,  267,  256,   -1,  258,  271,   -1,  273,
   -1,   -1,  276,  277,  266,  267,   -1,   -1,   -1,  271,
   -1,  273,   -1,   -1,  276,  277,
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
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"MENORIGUAL","ID","ASIGNACION","DISTINTO",
"MAYORIGUAL","SINGLE_CONSTANTE","ENTERO_UNSIGNED","OCTAL","MULTILINEA","REPEAT",
"IF","THEN","ELSE","BEGIN","END","END_IF","OUTF","TYPEDEF","FUN","RET","GOTO",
"TRIPLE","UNSIGNED","SINGLE","TIPO_OCTAL","UNTIL","menos","\") \"",
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
"ejecutable : retorno",
"ejecutable : repeat_until",
"ejecutable : goto",
"ejecutable : salida",
"bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END",
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
"lista_var : error ID",
"tipo : TIPO_OCTAL",
"tipo : UNSIGNED",
"tipo : SINGLE",
"declaracionFun : tipo FUN ID '(' parametro ')' BEGIN cuerpoFun END",
"declaracionFun : tipo FUN '(' parametro ')' BEGIN cuerpoFun END",
"declaracionFun : tipo FUN ID '(' ')' BEGIN cuerpoFun END",
"parametro : tipo ID",
"parametro : tipo",
"parametro : ID",
"cuerpoFun : retorno",
"cuerpoFun : conjunto_sentencias retorno",
"retorno : RET '(' exp_arit ')'",
"retorno : error '(' exp_arit ')'",
"asig : ID ASIGNACION exp_arit",
"exp_arit : exp_arit '+' termino",
"exp_arit : exp_arit '-' termino",
"exp_arit : error ';'",
"exp_arit : termino",
"lista_exp_arit : exp_arit",
"lista_exp_arit : lista_exp_arit ',' exp_arit",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"termino : error factor",
"factor : ID",
"factor : constante",
"factor : invocacion_fun",
"etiqueta : ID '@'",
"constante : SINGLE_CONSTANTE",
"constante : ENTERO_UNSIGNED",
"constante : OCTAL",
"invocacion_fun : ID '(' exp_arit ')'",
"invocacion_fun : ID '(' tipo '(' exp_arit ')' ')'",
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

//#line 171 "gramatica.y"

private Lexico lexico;
private final Float infPositivo = (float) Math.pow(1.1754943, -38 );
private final Float supPositivo = (float) Math.pow(3.40282347, 38);
private final Float infNegativo = (float) Math.pow(-3.40282347, 38);
private final Float supNegativo = (float) Math.pow(-1.17549435, -38);
  public static void main(String[] args){
    if(args.length > 1) {
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


//#line 617 "Parser.java"
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
//#line 13 "gramatica.y"
{System.out.println("Se detecto: Programa");}
break;
case 2:
//#line 14 "gramatica.y"
{System.out.println("Error, Falta nombre de programa " + "en linea: " + lexico.getContadorLinea());}
break;
case 3:
//#line 15 "gramatica.y"
{System.out.println("Error de delimitador de programa " + "en linea: " + lexico.getContadorLinea());}
break;
case 4:
//#line 16 "gramatica.y"
{System.out.println("Error de delimitador de programa " + "en linea: " + lexico.getContadorLinea());}
break;
case 5:
//#line 17 "gramatica.y"
{System.out.println("Error de delimitador de programa " + "en linea: " + lexico.getContadorLinea());}
break;
case 7:
//#line 20 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 9:
//#line 22 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 11:
//#line 24 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 13:
//#line 26 "gramatica.y"
{System.out.println("Falta ; " + "en linea: " + lexico.getContadorLinea());}
break;
case 14:
//#line 29 "gramatica.y"
{System.out.println("Se detecto: Sentencia if" + "en linea: " + lexico.getContadorLinea());}
break;
case 15:
//#line 30 "gramatica.y"
{System.out.println("Se detecto: Invocacion a funcion " + "en linea: " + lexico.getContadorLinea());}
break;
case 16:
//#line 31 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + "en linea: " + lexico.getContadorLinea());}
break;
case 17:
//#line 32 "gramatica.y"
{System.out.println("Se detecto: Retorno " + "en linea: " + lexico.getContadorLinea());}
break;
case 18:
//#line 33 "gramatica.y"
{System.out.println("Se detecto: Ciclo repeat until " + "en linea: " + lexico.getContadorLinea());}
break;
case 19:
//#line 34 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + "en linea: " + lexico.getContadorLinea());}
break;
case 20:
//#line 35 "gramatica.y"
{System.out.println("Se detecto: Salida " + "en linea: " + lexico.getContadorLinea());}
break;
case 24:
//#line 43 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 25:
//#line 44 "gramatica.y"
{System.out.println("Falta ;");}
break;
case 26:
//#line 47 "gramatica.y"
{System.out.println("Se detecto: Declaracion de funcion " + "en linea: " + lexico.getContadorLinea());}
break;
case 27:
//#line 48 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable " + "en linea: " + lexico.getContadorLinea());}
break;
case 28:
//#line 49 "gramatica.y"
{System.out.println("Se detecto: Declaración de tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 29:
//#line 50 "gramatica.y"
{System.out.println("Se detecto: Declaración de variable tipo triple " + "en linea: " + lexico.getContadorLinea());}
break;
case 30:
//#line 52 "gramatica.y"
{System.out.println("Se detecto: Declaración de lista de variables " + "en linea: " + lexico.getContadorLinea());}
break;
case 34:
//#line 58 "gramatica.y"
{System.out.println("Error, falta ',' para diferenciar las variables");}
break;
case 39:
//#line 64 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 40:
//#line 65 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 42:
//#line 70 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 43:
//#line 71 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 47:
//#line 79 "gramatica.y"
{System.out.println("Falta, la palabra ret que indica retorno");}
break;
case 49:
//#line 86 "gramatica.y"
{System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());}
break;
case 50:
//#line 87 "gramatica.y"
{System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());}
break;
case 51:
//#line 88 "gramatica.y"
{System.out.println("Error en expresion aritmetica");}
break;
case 55:
//#line 99 "gramatica.y"
{System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());}
break;
case 56:
//#line 100 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 58:
//#line 102 "gramatica.y"
{System.out.println("Error en termino" + "en linea: " + lexico.getContadorLinea());}
break;
case 59:
//#line 105 "gramatica.y"
{System.out.println("Se detecto: Identificador " + val_peek(0).sval + "en linea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 107 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 68:
//#line 123 "gramatica.y"
{System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
break;
case 69:
//#line 124 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 70:
//#line 125 "gramatica.y"
{System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
break;
case 71:
//#line 126 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 72:
//#line 127 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 73:
//#line 128 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 76:
//#line 134 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 77:
//#line 135 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 78:
//#line 136 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 137 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 80:
//#line 138 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 81:
//#line 139 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 82:
//#line 140 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
break;
case 83:
//#line 141 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 92:
//#line 154 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal " + "en linea: " + lexico.getContadorLinea());}
break;
case 93:
//#line 155 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 95:
//#line 159 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 96:
//#line 160 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 100:
//#line 168 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
//#line 990 "Parser.java"
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
