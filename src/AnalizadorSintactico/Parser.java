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
    0,    1,    1,    1,    1,    3,    3,    3,    3,    3,
    3,    3,   12,   12,    4,    4,    2,    2,    2,    2,
   14,   16,   18,   18,   17,   17,   17,   13,   19,   20,
   20,    8,    7,   21,   21,   21,   23,   23,   22,   22,
   22,   24,   24,   24,   26,   25,   25,   25,   25,    6,
    6,    5,    5,   27,   27,   28,   28,   28,   28,   28,
   28,   11,   11,    9,   15,   29,   10,
};
final static short yylen[] = {                            2,
    5,    2,    2,    3,    3,    1,    1,    1,    1,    1,
    1,    1,    2,    3,    2,    3,    1,    1,    1,    1,
    2,    2,    1,    3,    1,    1,    1,    9,    2,    1,
    2,    4,    3,    3,    3,    1,    1,    3,    3,    3,
    1,    1,    1,    1,    2,    1,    1,    1,    2,    4,
    7,    5,    7,    5,    7,    1,    1,    1,    1,    1,
    1,    4,    4,    4,    6,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   26,   27,   25,    0,    0,    0,    6,    7,    8,    9,
   10,   11,   12,   17,   18,   19,   20,    0,   23,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   66,
    0,    0,    0,   67,    0,    0,    0,    0,    2,    3,
    0,    0,    0,   46,   47,   48,    0,   44,    0,    0,
   41,   43,    0,    0,    0,    0,   13,    0,    0,    0,
    0,    0,    0,    0,    0,   45,    1,    4,   15,    5,
    0,    0,   49,    0,    0,    0,    0,    0,   50,   24,
   14,   64,   57,   58,   56,   59,   60,   61,    0,    0,
    0,    0,   62,   63,    0,   32,   16,    0,    0,    0,
   39,   40,    0,    0,    0,    0,    0,   52,    0,    0,
    0,    0,   54,    0,    0,   65,   29,    0,   51,    0,
    0,   53,    0,   55,    0,    0,    0,    0,   28,
};
final static short yydgoto[] = {                          2,
   14,   15,   35,   48,   17,   58,   19,   20,   21,   22,
   23,   36,   24,   25,   26,   27,   28,   32,  121,  137,
   59,   60,   70,   61,   62,   44,   38,   99,   41,
};
final static short yysindex[] = {                      -206,
 -202,    0,  -92,  -24, -172,   44,   48, -186,   62, -162,
    0,    0,    0, -135,   47,   60,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -198,    0,   12,
  -18,   77,   -7, -118,   75, -132,   12, -121,    2,    0,
   96,   12,   93,    0,  101,  105,  106,   24,    0,    0,
  -91,   77,  128,    0,    0,    0,  -93,    0,   -9,   25,
    0,    0,  131,   33,  -85, -159,    0,   44,   20,    5,
 -172,  135,  -28, -127,   46,    0,    0,    0,    0,    0,
  118,  139,    0,   12,   12,   12,   12,   12,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   12,   12,
  -19, -199,    0,    0,  121,    0,    0, -127,   25,   25,
    0,    0,   79,   92,   -9,  146, -172,    0,  -68,  -67,
  151,  152,    0,   12,  -78,    0,    0,  -75,    0,   -9,
   56,    0,  -92,    0,  -92,    0,  -74,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  137,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  140,  -41,    0,    0,    0,    0,    0,  141,  -36,
    0,    0,    0,    0,    0,    0,    0,    0,   69,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -31,   -6,
    0,    0,    0,    0,   84,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   69,
    0,    0,    0,    0,    0,  -48,    0,  -14,    0,
};
final static short yygindex[] = {                         0,
   65,    8,   37,  167,    0,   45,    0,   28,    0,    0,
    0,  -27,    0,    0,    0,    0,    1,  174,    0,    0,
   27,  -23,   80,   43,    0,    0,  138,  102,    0,
};
final static int YYTABLESIZE=301;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         42,
   42,   42,   42,   42,   36,   42,   36,   36,   36,   34,
    9,   34,   34,   34,   84,   31,   85,   42,   42,   42,
   42,   46,   36,   36,   36,   36,   57,   34,   34,   34,
   34,   63,   31,   84,   35,   85,   35,   35,   35,   16,
   98,   96,   97,  102,    9,  101,   57,   18,  100,   18,
   47,    1,   35,   35,   35,   35,   57,   64,   18,   29,
  109,  110,   84,   69,   85,   73,   86,    3,   75,  117,
   47,   87,  118,   89,  105,   84,   51,   85,   18,   98,
   96,   97,   80,   37,   81,   33,  106,   39,   84,  125,
   85,   40,   18,    5,    6,   43,  134,   34,   33,  100,
    7,   42,   81,    9,   10,   49,    5,    6,  120,   37,
   18,   91,   37,    7,  113,   18,    9,   10,   50,  122,
   65,   84,    4,   85,   38,  114,  115,   38,  111,  112,
    5,    6,  123,   67,   84,   45,   85,    7,    8,   33,
    9,   10,   46,   11,   12,   13,   71,    5,    6,   68,
  130,   11,   12,   13,    7,   74,   76,    9,   10,   77,
  136,   18,  138,   78,   79,    4,   82,   31,   83,   16,
   88,   47,   90,    5,    6,  103,  107,   18,  108,   18,
    7,    8,  119,    9,   10,  124,   11,   12,   13,  126,
  127,  128,  129,  132,  133,   22,  139,  135,   21,   33,
   66,   52,  116,  131,    0,   92,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   42,    0,    0,   42,   42,
   36,    0,   30,   36,   36,   34,    0,    0,   34,   34,
    0,    0,    0,   29,   30,    0,    0,   93,    0,   53,
   94,   95,   42,   54,   55,   56,    0,   36,    0,    0,
   35,   30,   34,   35,   35,  104,   31,    0,    0,   53,
   11,   12,   13,   54,   55,   56,   72,    0,    0,   53,
    0,    0,    0,   54,   55,   56,   93,   35,    0,   94,
   95,   33,    0,    0,    0,    0,    0,    0,    0,    5,
    6,    0,    0,    0,    0,    0,    7,    0,    0,    9,
   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
   59,   43,   44,   45,   43,   40,   45,   59,   60,   61,
   62,   14,   59,   60,   61,   62,   45,   59,   60,   61,
   62,   31,   40,   43,   41,   45,   43,   44,   45,    3,
   60,   61,   62,   71,   59,   41,   45,    3,   44,    5,
   14,  258,   59,   60,   61,   62,   45,   31,   14,  258,
   84,   85,   43,   37,   45,   39,   42,  270,   42,  269,
   34,   47,  272,   41,   74,   43,  275,   45,   34,   60,
   61,   62,   59,   40,   48,  258,   41,   40,   43,  117,
   45,  278,   48,  266,  267,  258,   41,  270,  258,   44,
  273,   40,   66,  276,  277,   59,  266,  267,  108,   41,
   66,  271,   44,  273,   88,   71,  276,  277,   59,   41,
   44,   43,  258,   45,   41,   99,  100,   44,   86,   87,
  266,  267,   41,   59,   43,  271,   45,  273,  274,  258,
  276,  277,  135,  279,  280,  281,  268,  266,  267,  282,
  124,  279,  280,  281,  273,   60,   64,  276,  277,   59,
  133,  117,  135,   59,   59,  258,  258,   40,  262,  133,
   40,  135,  258,  266,  267,   41,   59,  133,   40,  135,
  273,  274,   62,  276,  277,   40,  279,  280,  281,  258,
  258,   41,   41,  272,  270,   59,  271,  133,   59,   59,
   34,   28,  101,  124,   -1,   68,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,  261,
  257,   -1,  271,  260,  261,  257,   -1,   -1,  260,  261,
   -1,   -1,   -1,  258,  259,   -1,   -1,  257,   -1,  258,
  260,  261,  284,  262,  263,  264,   -1,  284,   -1,   -1,
  257,  259,  284,  260,  261,  284,  271,   -1,   -1,  258,
  279,  280,  281,  262,  263,  264,  265,   -1,   -1,  258,
   -1,   -1,   -1,  262,  263,  264,  257,  284,   -1,  260,
  261,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  266,
  267,   -1,   -1,   -1,   -1,   -1,  273,   -1,   -1,  276,
  277,
};
}
final static short YYFINAL=2;
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
"conjunto_sentencias : declarativa ';'",
"conjunto_sentencias : ejecutable ';'",
"conjunto_sentencias : conjunto_sentencias declarativa ';'",
"conjunto_sentencias : conjunto_sentencias sentencias_ejecutables ';'",
"ejecutable : sentencia_if",
"ejecutable : invocacion_fun",
"ejecutable : asig",
"ejecutable : retorno",
"ejecutable : repeat_until",
"ejecutable : goto",
"ejecutable : salida",
"bloque_sentencias_ejecutables : ejecutable ';'",
"bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END",
"sentencias_ejecutables : ejecutable ';'",
"sentencias_ejecutables : sentencias_ejecutables ejecutable ';'",
"declarativa : declaracionFun",
"declarativa : declarvar",
"declarativa : def_triple",
"declarativa : declar_compuesto",
"declarvar : tipo lista_var",
"declar_compuesto : ID lista_var",
"lista_var : ID",
"lista_var : lista_var ',' ID",
"tipo : TIPO_OCTAL",
"tipo : UNSIGNED",
"tipo : SINGLE",
"declaracionFun : tipo FUN ID '(' parametro ')' BEGIN cuerpoFun END",
"parametro : tipo ID",
"cuerpoFun : retorno",
"cuerpoFun : conjunto_sentencias retorno",
"retorno : RET '(' exp_arit ')'",
"asig : ID ASIGNACION exp_arit",
"exp_arit : exp_arit '+' termino",
"exp_arit : exp_arit '-' termino",
"exp_arit : termino",
"lista_exp_arit : exp_arit",
"lista_exp_arit : lista_exp_arit ',' exp_arit",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : constante",
"factor : invocacion_fun",
"etiqueta : ID '@'",
"constante : SINGLE_CONSTANTE",
"constante : ENTERO_UNSIGNED",
"constante : OCTAL",
"constante : '-' SINGLE_CONSTANTE",
"invocacion_fun : ID '(' exp_arit ')'",
"invocacion_fun : ID '(' tipo '(' exp_arit ')' ')'",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables END_IF",
"sentencia_if : IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables END_IF",
"condicion : '(' exp_arit comparador exp_arit ')'",
"condicion : '(' lista_exp_arit ')' comparador '(' lista_exp_arit ')'",
"comparador : MAYORIGUAL",
"comparador : MENORIGUAL",
"comparador : DISTINTO",
"comparador : '='",
"comparador : '>'",
"comparador : '<'",
"salida : OUTF '(' MULTILINEA ')'",
"salida : OUTF '(' exp_arit \") \"",
"repeat_until : REPEAT bloque_sentencias_ejecutables UNTIL condicion",
"def_triple : TYPEDEF tipo_compuesto '<' tipo '>' ID",
"tipo_compuesto : TRIPLE",
"goto : GOTO etiqueta",
};

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
