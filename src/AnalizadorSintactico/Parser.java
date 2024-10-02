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
    	/*import Lex.Lex;    */
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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    4,    4,    4,    4,    4,
    4,    4,   12,   12,    3,    3,    2,    2,    2,    2,
   14,   16,   18,   18,   17,   17,   17,   13,   19,   20,
   20,    8,    7,   21,   21,   21,   23,   23,   22,   22,
   22,   24,   24,   24,   26,   25,   25,   25,   25,    6,
    6,    5,    5,   28,   28,   29,   29,   29,   29,   29,
   29,   11,   11,    9,   15,   30,   10,   27,
};
final static short yylen[] = {                            2,
    5,    2,    1,    3,    3,    1,    1,    1,    1,    1,
    1,    1,    2,    3,    2,    3,    1,    1,    1,    1,
    2,    2,    1,    3,    1,    1,    1,    9,    2,    1,
    2,    4,    3,    3,    3,    1,    1,    3,    3,    3,
    1,    1,    1,    1,    2,    1,    1,    1,    1,    4,
    7,    5,    7,    5,    7,    1,    1,    1,    1,    1,
    1,    4,    4,    4,    6,    1,    2,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   26,   27,   25,    0,    0,    0,    0,    6,    7,    8,
    9,   10,   11,   12,   17,   18,   19,   20,    0,   23,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   66,    0,    0,    0,   67,    0,    0,    0,    2,    0,
   15,    0,    0,    0,   46,   47,   48,    0,   44,    0,
    0,   41,   43,   49,    0,    0,    0,    0,   13,    0,
    0,    0,    0,    0,    0,    0,    0,   45,    1,    4,
    5,   16,    0,    0,    0,    0,    0,    0,    0,   50,
   24,   14,   64,   57,   58,   56,   59,   60,   61,    0,
    0,    0,    0,   62,   63,    0,   32,    0,   68,    0,
    0,   39,   40,    0,    0,    0,    0,    0,   52,    0,
    0,    0,    0,   54,    0,    0,   65,   29,    0,   51,
    0,    0,   53,    0,   55,    0,    0,    0,    0,   28,
};
final static short yydgoto[] = {                          2,
   14,   15,   16,   17,   18,   59,   20,   21,   22,   23,
   24,   37,   25,   26,   27,   28,   29,   33,  122,  138,
   60,   61,   72,   62,   63,   45,   64,   39,  100,   42,
};
final static short yysindex[] = {                      -197,
 -206,    0, -145,  -13, -128,   37,   38, -184,   64, -125,
    0,    0,    0, -174,   84, -106,   96,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -208,    0,
   11,  -23,   97,   -8, -106,  107, -135,   11,  -94,    1,
    0,  117,   11,  115,    0,  122,  123,  124,    0,  125,
    0,  -73,   97,  146,    0,    0,    0,  -75,    0,   -2,
   26,    0,    0,    0,  148,   45,  -69, -108,    0,   37,
   20,    4, -128,  149,  -28, -170,   46,    0,    0,    0,
    0,    0,  151,  133,   11,   11,   11,   11,   11,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   11,
   11,   10, -132,    0,    0,  131,    0, -170,    0,   26,
   26,    0,    0,   73,   82,   -2,  154, -128,    0,  -63,
  -62,  156,  157,    0,   11,  -72,    0,    0,  -71,    0,
   -2,   54,    0, -145,    0, -145,    0,  -70,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -205,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  143,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  144,  -41,    0,    0,    0,    0,    0,  145,
  -36,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  110,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -31,
   -7,    0,    0,    0,    0,  112,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  110,    0,    0,    0,    0,    0,  -48,    0,  -43,    0,
};
final static short yygindex[] = {                         0,
   71,   21,  171,   28,    0,   44,    0,  -10,    0,    0,
    0,  -33,    0,    0,    0,    0,    7,  178,    0,    0,
   19,   87,   83,   88,    0,    0,    0,  139,  108,    0,
};
final static int YYTABLESIZE=281;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         42,
   42,   42,   42,   42,   36,   42,   36,   36,   36,   34,
    9,   34,   34,   34,   85,    9,   86,   42,   42,   42,
   42,   58,   36,   36,   36,   36,   32,   34,   34,   34,
   34,   32,   36,   35,   47,   35,   35,   35,   65,  103,
   85,   48,   86,   50,  102,   58,   19,  101,   19,   30,
   66,   35,   35,   35,   35,   58,   71,   19,   75,   19,
    1,   77,   85,    3,   86,    3,   52,   87,    3,   99,
   97,   98,   88,    3,    3,    3,   38,   40,   19,   99,
   97,   98,  106,    4,  126,   90,  107,   85,   85,   86,
   86,    5,    6,   41,  135,   50,   46,  101,    7,    8,
   36,    9,   10,   43,   11,   12,   13,  114,   11,   12,
   13,   19,    4,  123,  121,   85,   19,   86,  115,  116,
    5,    6,  124,  137,   85,  139,   86,    7,    8,   34,
    9,   10,   44,   11,   12,   13,  118,    5,    6,  119,
   67,   35,   49,  131,    7,   36,   70,    9,   10,   34,
   37,   34,   38,   37,   51,   38,   47,    5,    6,    5,
    6,   19,   92,   48,    7,   69,    7,    9,   10,    9,
   10,  110,  111,   73,  112,  113,   76,   19,   78,   19,
   79,   80,   81,   82,   83,   32,   84,   89,   91,  104,
  108,  109,  120,  125,  127,  128,  129,  130,  134,  133,
  140,   22,   21,   33,  136,   68,   53,  132,   93,  117,
    0,    0,    0,    0,    0,   42,    0,    0,   42,   42,
   36,    0,   30,   36,   36,   34,    0,   31,   34,   34,
    0,    0,    0,    0,   54,    0,    0,    0,   55,   56,
   57,   42,    0,    0,   30,   31,   36,    0,    0,   35,
   31,   34,   35,   35,  105,   11,   12,   13,   54,    0,
    0,    0,   55,   56,   57,   74,   94,    0,   54,   95,
   96,    0,   55,   56,   57,   35,   94,    0,    0,   95,
   96,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
   59,   43,   44,   45,   43,   59,   45,   59,   60,   61,
   62,   45,   59,   60,   61,   62,   40,   59,   60,   61,
   62,   40,    5,   41,   14,   43,   44,   45,   32,   73,
   43,   14,   45,   16,   41,   45,    3,   44,    5,  258,
   32,   59,   60,   61,   62,   45,   38,   14,   40,   16,
  258,   43,   43,  270,   45,  271,  275,   42,  274,   60,
   61,   62,   47,  279,  280,  281,   40,   40,   35,   60,
   61,   62,   76,  258,  118,   41,   41,   43,   43,   45,
   45,  266,  267,  278,   41,   68,  271,   44,  273,  274,
   73,  276,  277,   40,  279,  280,  281,   89,  279,  280,
  281,   68,  258,   41,  108,   43,   73,   45,  100,  101,
  266,  267,   41,  134,   43,  136,   45,  273,  274,  258,
  276,  277,  258,  279,  280,  281,  269,  266,  267,  272,
   44,  270,   59,  125,  273,  118,  282,  276,  277,  258,
   41,  258,   41,   44,   59,   44,  136,  266,  267,  266,
  267,  118,  271,  136,  273,   59,  273,  276,  277,  276,
  277,   85,   86,  268,   87,   88,   60,  134,   64,  136,
   59,   59,   59,   59,  258,   40,  262,   40,  258,   41,
   40,   59,   62,   40,  258,  258,   41,   41,  270,  272,
  271,   59,   59,   59,  134,   35,   29,  125,   70,  102,
   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,  261,
  257,   -1,  271,  260,  261,  257,   -1,  271,  260,  261,
   -1,   -1,   -1,   -1,  258,   -1,   -1,   -1,  262,  263,
  264,  283,   -1,   -1,  258,  259,  283,   -1,   -1,  257,
  259,  283,  260,  261,  283,  279,  280,  281,  258,   -1,
   -1,   -1,  262,  263,  264,  265,  257,   -1,  258,  260,
  261,   -1,  262,  263,  264,  283,  257,   -1,   -1,  260,
  261,
};
}
final static short YYFINAL=2;
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
"TRIPLE","UNSIGNED","SINGLE","TIPO_OCTAL","UNTIL","\") \"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID BEGIN conjunto_sentencias END ';'",
"conjunto_sentencias : declarativa ';'",
"conjunto_sentencias : sentencias_ejecutables",
"conjunto_sentencias : conjunto_sentencias declarativa ';'",
"conjunto_sentencias : conjunto_sentencias ejecutable ';'",
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
"constante : constante_negativa",
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
"constante_negativa : '-' SINGLE_CONSTANTE ';'",
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
