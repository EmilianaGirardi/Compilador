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
   14,   16,   18,   18,   18,   17,   17,   17,   13,   13,
   13,   19,   19,   19,   20,   20,   12,   12,    6,    6,
   21,   21,   21,   21,   24,   24,   23,   23,   23,   23,
   25,   25,   25,   25,   26,   22,   22,   22,    5,    5,
    4,    4,    4,    4,    4,    4,   27,   27,   27,   27,
   27,   27,   27,   27,   27,   27,   28,   28,   28,   28,
   28,   28,    9,    9,    9,    9,    7,    7,    7,   15,
   29,    8,    8,
};
final static short yylen[] = {                            2,
    5,    4,    4,    3,    2,    2,    1,    2,    1,    3,
    2,    3,    2,    1,    1,    1,    1,    1,    1,    3,
    4,    3,    2,    3,    1,    2,    1,    1,    1,    1,
    2,    2,    1,    3,    2,    1,    1,    1,    9,    8,
    8,    2,    1,    1,    1,    2,    5,    5,    3,    6,
    3,    3,    2,    1,    1,    3,    3,    3,    1,    2,
    1,    4,    1,    1,    2,    1,    1,    1,    4,    7,
    5,    4,    7,    6,    4,    6,    5,    9,    8,    8,
    8,    8,    4,    4,    4,    8,    1,    1,    1,    1,
    1,    1,    4,    4,    3,    4,    4,    3,    3,    6,
    1,    2,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   37,   38,   36,    0,    0,    0,   14,   15,   16,   17,
   18,   19,   27,   28,   29,   30,    0,    0,    0,   33,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   66,
   67,   68,    0,   64,    0,   63,    0,   59,    0,    0,
    0,  101,    0,    0,    0,  102,    0,    0,    0,    6,
    8,    0,    0,    0,   35,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   98,    0,   99,   53,
   60,    0,    0,    0,    0,   88,   89,   87,    0,    0,
   90,   91,   92,    0,    0,    0,    0,    0,    0,    0,
   95,    0,    0,  103,   65,    3,   10,   12,    0,    0,
    2,    0,   69,    0,   34,    0,    0,   23,   20,    0,
    0,   22,   97,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,   57,   75,    0,    1,   96,
   93,   94,    0,    0,   44,    0,    0,    0,    0,    0,
    0,   24,   21,   62,    0,    0,   85,    0,    0,    0,
   83,    0,   71,    0,    0,    0,   42,    0,    0,    0,
    0,    0,    0,    0,    0,   77,    0,   76,    0,  100,
    0,    0,    0,   70,   48,   47,    0,    0,    0,    0,
    0,   73,    0,   45,    0,    0,    0,    0,    0,    0,
    0,    0,   46,   41,    0,   40,   86,    0,   80,   79,
   81,   39,   78,
};
final static short yydgoto[] = {                          3,
  193,   15,   16,   17,   44,   19,   20,   21,   22,   37,
   75,  194,   23,   24,   25,   26,   27,   34,  147,  195,
  125,   46,   47,   85,   48,   56,   49,   94,   53,
};
final static short yysindex[] = {                      -234,
  482,  618,    0,  -18, -241,  173,  618,   -7, -255, -219,
    0,    0,    0,  567,   -1,   17,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -218,  584, -171,    0,
  -87, -128,  -79,   38, -112,  173,  -40,  -54,  -24,    0,
    0,    0,  199,    0,  485,    0,   36,    0, -173,  601,
  -28,    0,   47,   58,   61,    0,  109,  139,  152,    0,
    0,  -37,   38,  153,    0,   94,  185,   53,   89,  -29,
  191,  -26,  192,  186,  638,  -27,    0,  173,    0,    0,
    0,  -79,  -87,  471,  -35,    0,    0,    0,  -61,  -61,
    0,    0,    0,  -87,  -76,  -76, -127,  188,  140,  212,
    0,  -41,  -74,    0,    0,    0,    0,    0,  216, -230,
    0,  -87,    0,   11,    0,  -87,  -87,    0,    0,  201,
    2,    0,    0,  146,   94,  118,   81,  -87,  -87,  526,
  -76,   36,   36,   97,    0,    0,    0, -205,    0,    0,
    0,    0,  214,  -31,    0,   26,  248,  174,  -87,  387,
  409,    0,    0,    0,   -6,  255,    0,  434,   94,  259,
    0, -114,    0,   42,   32,  267,    0,   43,  271,   94,
  265,  269,  -87,  226,  -87,    0,  -87,    0,   57,    0,
  523,   64,  523,    0,    0,    0,   70,  -87,   72,  116,
  122,    0,  523,    0,   60,  523,   66,  300,  148,  312,
  316,  317,    0,    0,   92,    0,    0,  319,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  366,  347,  374,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  167,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,    0,    0,   30,    0,    0,  370,
    0,    0,    0,    0,    0,    0,    0,  400,  426,    0,
    0,    0,  193,    0,    0,  220,    0,    0,    0,    0,
    0,    0,    0,  650,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  150,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  662,
    0,    0,    0,    0,  507,    0,    0,    0,    0,    0,
    0,   59,   88,    0,    0,    0,    0,  246,    0,    0,
    0,    0,    0,    0,    0,  335,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  115,  517,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  283,
    0,    0,    0,    0,    0,    0,    0,    0,  318,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  141,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   78,    3,   -3,    0,  635,    0,    0,    0,    0,  -78,
    0,  -23,    0,    0,    0,    0,  -17,  353,  240, -176,
  707,  -12,   83,  -65,  -30,    0,  -10,  -49,    0,
};
final static int YYTABLESIZE=939;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   61,   89,  110,   90,   80,  130,  197,   81,  129,  165,
   59,   76,  101,   32,   67,   32,   58,  126,  138,  205,
   69,   32,   52,    1,   59,   77,   79,  145,   35,   54,
   58,   74,   51,  173,  128,    2,   54,   29,   55,   30,
   36,   61,   61,   61,   61,   61,   59,   61,   11,   12,
   13,  121,   58,   93,   91,   92,   62,   60,   51,   61,
   61,   61,   61,  162,  135,  136,  163,  123,   81,  124,
   54,  120,   54,   54,   54,   61,  156,   96,   14,   28,
  160,   70,   95,  179,   50,  143,   65,   52,   54,   54,
   54,   54,  146,  113,   97,   89,   33,   90,   82,   51,
   81,   51,   51,   51,   33,  174,  103,  187,  189,  190,
  198,  191,  200,  129,   84,  129,  104,   51,   51,   51,
   51,  157,  199,   89,  105,   90,  146,   38,   52,   39,
   52,   52,   52,   40,   41,   42,   89,  161,   90,   89,
   82,   90,   35,   71,  137,   72,   52,   52,   52,   52,
   11,   12,   13,    5,    6,   35,  201,  178,  155,  129,
    8,  129,  202,   73,   10,  129,   32,  106,   38,  203,
   39,  132,  133,   84,   40,   41,   42,   93,   91,   92,
  140,   39,   40,   41,   42,   40,   41,   42,  208,   59,
   55,  129,   31,   55,  131,   58,   39,  107,   80,   82,
   40,   41,   42,   39,   11,   12,   13,   40,   41,   42,
  108,  111,   43,  114,  169,   38,   89,   39,   90,   49,
  109,   40,   41,   42,  112,   32,  145,   99,  115,   39,
  116,  117,   31,   40,   41,   42,  100,   29,   83,   30,
   31,   78,  142,  122,  118,   72,  139,   11,   12,   13,
   86,   31,  141,   87,   88,  144,   61,   61,   61,  152,
   61,   61,   61,   61,   61,  188,   61,   61,   61,  149,
  154,   61,  153,   61,   61,  164,   61,   61,   49,   61,
   61,   61,   50,  167,   61,   54,   54,   54,  168,   54,
   54,   54,   54,   54,  175,   54,   54,   54,  177,  180,
   54,  181,   54,   54,   72,   54,   54,  182,   54,   54,
   54,  184,  183,   54,   51,   51,   51,   74,   51,   51,
   51,   51,   51,  185,   51,   51,   51,  186,  192,   51,
  204,   51,   51,  196,   51,   51,  206,   51,   51,   51,
  207,   50,   51,   52,   52,   52,    7,   52,   52,   52,
   52,   52,  209,   52,   52,   52,  210,  211,   52,  213,
   52,   52,  212,   52,   52,    5,   52,   52,   52,    4,
   84,   52,   84,    9,   86,   43,   74,   87,   88,   63,
   84,   84,   84,  166,    0,   84,    0,   84,   84,    0,
   84,   84,    0,   84,   84,   84,   82,   39,   82,   11,
    0,   40,   41,   42,    0,    0,   82,   82,   82,    0,
    0,   82,    0,   82,   82,    0,   82,   82,    0,   82,
   82,   82,   32,    0,   32,   13,    0,  171,   38,   89,
   39,   90,   32,   32,   40,   41,   42,   32,    0,   32,
   32,    0,   32,   32,    0,   32,   32,   32,   31,  172,
   31,   89,    0,   90,   38,    0,   39,    0,   31,   31,
   40,   41,   42,   31,    0,   31,   31,    0,   31,   31,
    0,   31,   31,   31,  176,   49,   89,   49,   90,    0,
    0,   38,    0,   39,    0,   49,   49,   40,   41,   42,
   49,    0,   49,   49,    0,   49,   49,    0,   49,   49,
   49,   72,    0,   72,    0,    0,    0,    0,    0,    0,
    0,   72,   72,   89,    0,   90,   72,    0,   72,   72,
    0,   72,   72,    0,   72,   72,   72,   89,    0,   90,
   93,   91,   92,    0,    0,    0,    0,    0,   50,    0,
   50,    0,    0,    0,   93,   91,   92,   55,   50,   50,
   55,    0,    0,   50,    0,   50,   50,   56,   50,   50,
   56,   50,   50,   50,    0,    0,   55,   55,   55,    0,
    0,    0,    0,   74,    0,   74,   56,   56,   56,    0,
    0,    0,    0,   74,   74,   93,   91,   92,   74,    0,
   74,   74,    0,   74,   74,    0,   74,   74,   74,    0,
    0,    0,    7,    0,    7,    0,    0,    0,    0,    0,
    0,    0,    7,    7,    0,    0,    0,    7,    0,    7,
    7,    0,    7,    7,    0,    7,    7,    7,    0,    9,
    0,    9,    0,    0,    0,   18,   18,    0,    0,    9,
    9,   18,    0,    0,    9,    0,    9,    9,   18,    9,
    9,    0,    9,    9,    9,   11,    0,   11,    0,    0,
    0,    0,   18,    0,    0,   11,   11,    0,    0,   18,
   11,    0,   11,   11,    0,   11,   11,    0,   11,   11,
   11,   13,    0,   13,   18,    0,    0,    0,    0,    0,
    0,   13,   13,    0,    0,    0,   13,    0,   13,   13,
    0,   13,   13,    0,   13,   13,   13,    0,    0,   18,
    0,    0,   45,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   38,   86,   39,    0,
   87,   88,   40,   41,   42,    0,    0,   66,   68,    4,
    0,   86,   45,   45,   87,   88,    0,    5,    6,   84,
    0,    7,    0,    0,    8,    9,    0,  102,   10,    0,
   11,   12,   13,   55,    0,    0,   55,   55,    0,    0,
    0,    0,    0,   56,    0,    0,   56,   56,   71,    0,
    4,    0,   86,    0,   45,   87,   88,    0,    5,    6,
  127,    0,    0,    0,    0,    8,    9,    0,   73,   10,
  134,   11,   12,   13,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   18,    0,   18,  148,    0,
    0,    0,  150,  151,    4,    0,    0,   18,    0,    0,
   18,    0,    5,    6,  158,  159,    0,   57,    0,    8,
    9,    4,    0,   10,    0,   11,   12,   13,    0,    5,
    6,    0,    0,    0,   64,  170,    8,    9,    4,    0,
   10,    0,   11,   12,   13,    0,    5,    6,    0,    0,
    0,   98,    0,    8,    9,    4,    0,   10,    0,   11,
   12,   13,    0,    5,    6,    0,    0,    0,    0,    0,
    8,    9,    0,   71,   10,   72,   11,   12,   13,    0,
    0,    0,    0,    5,    6,   25,    0,   25,  119,    0,
    8,    0,    0,   73,   10,   25,   25,   26,    0,   26,
   25,    0,   25,    0,    0,   25,   25,   26,   26,    0,
    0,    0,   26,    0,   26,    0,    0,   26,   26,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   43,   40,   45,   59,   41,  183,   38,   44,   41,
   14,   35,   41,   40,   32,   40,   14,   83,   97,  196,
   33,   40,  278,  258,   28,   36,   37,  258,  270,    0,
   28,   35,   40,   40,   84,  270,  256,  256,  258,  258,
  282,   41,   42,   43,   44,   45,   50,   47,  279,  280,
  281,   75,   50,   60,   61,   62,  275,   59,    0,   59,
   60,   61,   62,  269,   95,   96,  272,   78,   99,   82,
   41,   75,   43,   44,   45,   59,  126,   42,    1,    2,
  130,   44,   47,  162,    7,  103,  258,    0,   59,   60,
   61,   62,  110,   41,  268,   43,  123,   45,  123,   41,
  131,   43,   44,   45,  123,  155,   60,  173,  174,  175,
   41,  177,   41,   44,    0,   44,   59,   59,   60,   61,
   62,   41,  188,   43,   64,   45,  144,  256,   41,  258,
   43,   44,   45,  262,  263,  264,   43,   41,   45,   43,
    0,   45,  270,  256,  272,  258,   59,   60,   61,   62,
  279,  280,  281,  266,  267,  270,   41,  272,   41,   44,
  273,   44,   41,  276,  277,   44,    0,   59,  256,  193,
  258,   89,   90,   59,  262,  263,  264,   60,   61,   62,
   41,  258,  262,  263,  264,  262,  263,  264,   41,  193,
   41,   44,    0,   44,  256,  193,  258,   59,   59,   59,
  262,  263,  264,  258,  279,  280,  281,  262,  263,  264,
   59,   59,   40,  125,   41,  256,   43,  258,   45,    0,
  258,  262,  263,  264,   40,   59,  258,  256,  258,  258,
   40,   40,  259,  262,  263,  264,  265,  256,   40,  258,
  259,  282,  284,  271,   59,    0,   59,  279,  280,  281,
  257,   59,   41,  260,  261,   40,  256,  257,  258,   59,
  260,  261,  262,  263,  264,   40,  266,  267,  268,  259,
  125,  271,  271,  273,  274,   62,  276,  277,   59,  279,
  280,  281,    0,  258,  284,  256,  257,  258,   41,  260,
  261,  262,  263,  264,   40,  266,  267,  268,   40,  258,
  271,  270,  273,  274,   59,  276,  277,   41,  279,  280,
  281,   41,  270,  284,  256,  257,  258,    0,  260,  261,
  262,  263,  264,   59,  266,  267,  268,   59,  272,  271,
  271,  273,  274,  270,  276,  277,  271,  279,  280,  281,
   41,   59,  284,  256,  257,  258,    0,  260,  261,  262,
  263,  264,   41,  266,  267,  268,   41,   41,  271,   41,
  273,  274,  271,  276,  277,    0,  279,  280,  281,    0,
  256,  284,  258,    0,  257,   41,   59,  260,  261,   27,
  266,  267,  268,  144,   -1,  271,   -1,  273,  274,   -1,
  276,  277,   -1,  279,  280,  281,  256,  258,  258,    0,
   -1,  262,  263,  264,   -1,   -1,  266,  267,  268,   -1,
   -1,  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,
  280,  281,  256,   -1,  258,    0,   -1,   41,  256,   43,
  258,   45,  266,  267,  262,  263,  264,  271,   -1,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,  256,   41,
  258,   43,   -1,   45,  256,   -1,  258,   -1,  266,  267,
  262,  263,  264,  271,   -1,  273,  274,   -1,  276,  277,
   -1,  279,  280,  281,   41,  256,   43,  258,   45,   -1,
   -1,  256,   -1,  258,   -1,  266,  267,  262,  263,  264,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,  256,   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  266,  267,   43,   -1,   45,  271,   -1,  273,  274,
   -1,  276,  277,   -1,  279,  280,  281,   43,   -1,   45,
   60,   61,   62,   -1,   -1,   -1,   -1,   -1,  256,   -1,
  258,   -1,   -1,   -1,   60,   61,   62,   41,  266,  267,
   44,   -1,   -1,  271,   -1,  273,  274,   41,  276,  277,
   44,  279,  280,  281,   -1,   -1,   60,   61,   62,   -1,
   -1,   -1,   -1,  256,   -1,  258,   60,   61,   62,   -1,
   -1,   -1,   -1,  266,  267,   60,   61,   62,  271,   -1,
  273,  274,   -1,  276,  277,   -1,  279,  280,  281,   -1,
   -1,   -1,  256,   -1,  258,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,
  274,   -1,  276,  277,   -1,  279,  280,  281,   -1,  256,
   -1,  258,   -1,   -1,   -1,    1,    2,   -1,   -1,  266,
  267,    7,   -1,   -1,  271,   -1,  273,  274,   14,  276,
  277,   -1,  279,  280,  281,  256,   -1,  258,   -1,   -1,
   -1,   -1,   28,   -1,   -1,  266,  267,   -1,   -1,   35,
  271,   -1,  273,  274,   -1,  276,  277,   -1,  279,  280,
  281,  256,   -1,  258,   50,   -1,   -1,   -1,   -1,   -1,
   -1,  266,  267,   -1,   -1,   -1,  271,   -1,  273,  274,
   -1,  276,  277,   -1,  279,  280,  281,   -1,   -1,   75,
   -1,   -1,    6,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,  258,   -1,
  260,  261,  262,  263,  264,   -1,   -1,   31,   32,  258,
   -1,  257,   36,   37,  260,  261,   -1,  266,  267,   43,
   -1,  270,   -1,   -1,  273,  274,   -1,   51,  277,   -1,
  279,  280,  281,  257,   -1,   -1,  260,  261,   -1,   -1,
   -1,   -1,   -1,  257,   -1,   -1,  260,  261,  256,   -1,
  258,   -1,  257,   -1,   78,  260,  261,   -1,  266,  267,
   84,   -1,   -1,   -1,   -1,  273,  274,   -1,  276,  277,
   94,  279,  280,  281,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  181,   -1,  183,  112,   -1,
   -1,   -1,  116,  117,  258,   -1,   -1,  193,   -1,   -1,
  196,   -1,  266,  267,  128,  129,   -1,  271,   -1,  273,
  274,  258,   -1,  277,   -1,  279,  280,  281,   -1,  266,
  267,   -1,   -1,   -1,  271,  149,  273,  274,  258,   -1,
  277,   -1,  279,  280,  281,   -1,  266,  267,   -1,   -1,
   -1,  271,   -1,  273,  274,  258,   -1,  277,   -1,  279,
  280,  281,   -1,  266,  267,   -1,   -1,   -1,   -1,   -1,
  273,  274,   -1,  256,  277,  258,  279,  280,  281,   -1,
   -1,   -1,   -1,  266,  267,  256,   -1,  258,  271,   -1,
  273,   -1,   -1,  276,  277,  266,  267,  256,   -1,  258,
  271,   -1,  273,   -1,   -1,  276,  277,  266,  267,   -1,
   -1,   -1,  271,   -1,  273,   -1,   -1,  276,  277,
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
"lista_var : error ID",
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
"retorno : error '(' exp_arit ')' ';'",
"asig : ID ASIGNACION exp_arit",
"asig : ID '{' constante '}' ASIGNACION exp_arit",
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
"factor : ID '{' constante '}'",
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

//#line 176 "gramatica.y"

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


//#line 630 "Parser.java"
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
case 8:
//#line 23 "gramatica.y"
{System.out.println("Se detecto el conjunto de sentencias");}
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
{System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
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
{System.out.println("Se detecto: Ciclo repeat until " + "en linea: " + lexico.getContadorLinea());}
break;
case 18:
//#line 35 "gramatica.y"
{System.out.println("Se detecto: Asignacion " + "en linea: " + lexico.getContadorLinea());}
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
{System.out.println("Se detecto: Declaracion de funcion " + "en linea: " + lexico.getContadorLinea());}
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
case 35:
//#line 60 "gramatica.y"
{System.out.println("Error, falta ',' para diferenciar las variables");}
break;
case 40:
//#line 66 "gramatica.y"
{System.out.println("Error, Falta nombre de funcion");}
break;
case 41:
//#line 67 "gramatica.y"
{System.out.println("Error, Falta parametro de funcion");}
break;
case 43:
//#line 72 "gramatica.y"
{System.out.println("Error, falta nombre del parametro formal");}
break;
case 44:
//#line 73 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal");}
break;
case 48:
//#line 81 "gramatica.y"
{System.out.println("Falta, la palabra ret que indica retorno");}
break;
case 51:
//#line 89 "gramatica.y"
{System.out.println("Se detecto: Suma " + "en linea: " + lexico.getContadorLinea());}
break;
case 52:
//#line 90 "gramatica.y"
{System.out.println("Se detecto: Resta " + "en linea: " + lexico.getContadorLinea());}
break;
case 53:
//#line 91 "gramatica.y"
{System.out.println("Error en expresion aritmetica");}
break;
case 57:
//#line 102 "gramatica.y"
{System.out.println("Se detecto: Multiplicación " + "en linea: " + lexico.getContadorLinea());}
break;
case 58:
//#line 103 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 60:
//#line 105 "gramatica.y"
{System.out.println("Error en termino" + "en linea: " + lexico.getContadorLinea());}
break;
case 61:
//#line 108 "gramatica.y"
{System.out.println("Se detecto: Identificador " + val_peek(0).sval + " en linea: " + lexico.getContadorLinea());}
break;
case 64:
//#line 111 "gramatica.y"
{System.out.println("Se detecto: División " + "en linea: " + lexico.getContadorLinea());}
break;
case 66:
//#line 116 "gramatica.y"
{lexico.getTablaSimbolos().editarLexema(val_peek(0).sval, truncarFueraRango(val_peek(0).sval, lexico.getContadorLinea()));}
break;
case 72:
//#line 128 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 73:
//#line 129 "gramatica.y"
{System.out.println("Se detecto: Sentencia if " + "en linea: " + lexico.getContadorLinea());}
break;
case 74:
//#line 130 "gramatica.y"
{System.out.println("Error, Falta END_IF de cierre " + "en linea: " + lexico.getContadorLinea());}
break;
case 75:
//#line 131 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque then " + "en linea: " + lexico.getContadorLinea());}
break;
case 76:
//#line 132 "gramatica.y"
{System.out.println("Error, Falta de contenido en el bloque else " + "en linea: " + lexico.getContadorLinea());}
break;
case 79:
//#line 138 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 80:
//#line 139 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 81:
//#line 140 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 82:
//#line 141 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 83:
//#line 142 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 84:
//#line 143 "gramatica.y"
{System.out.println("Error, falta de parentesis en la condicion " + "en linea: " + lexico.getContadorLinea());}
break;
case 85:
//#line 144 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea() );}
break;
case 86:
//#line 145 "gramatica.y"
{System.out.println("Error, falta de comparador " + "en linea: " + lexico.getContadorLinea());}
break;
case 95:
//#line 158 "gramatica.y"
{System.out.println("Error, falta tipo del parametro formal " + "en linea: " + lexico.getContadorLinea());}
break;
case 96:
//#line 159 "gramatica.y"
{System.out.println("Error, parametro invalido " + "en linea: " + lexico.getContadorLinea());}
break;
case 98:
//#line 163 "gramatica.y"
{System.out.println("Error, falta cuerpo en la iteracion " + "en linea: " + lexico.getContadorLinea());}
break;
case 99:
//#line 164 "gramatica.y"
{System.out.println("Error, falta de until en la iteracion repeat" + "en linea: " + lexico.getContadorLinea());}
break;
case 103:
//#line 173 "gramatica.y"
{System.out.println("Error, falta de etiqueta en la sentencia GOTO" + "en linea: " + lexico.getContadorLinea());}
break;
//#line 999 "Parser.java"
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
