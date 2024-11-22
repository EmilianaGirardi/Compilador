option casemap :none
include \masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib
.STACK 200h

.DATA
errorMsgOverflow db "Overflow: en suma!.", 10, 0 ;
errorMsgConversionNegativa db "Conversion invalida: no puede convertir un Single negativo a Entero!.", 10, 0 ;
errorMsgRestaNegativa db "Resta invalida: resultado negativo!.", 10, 0 ;
_cadena0_ DB "y menor a 50 => el RET fue x", 10, 0
_cadena1_ DB "y mayor a 50 => el RET fue x+z", 10, 0
_cadena2_ DB "ADENTRO DE FUN 3", 10, 0
_cadena3_ DB "ADENTRO DE FUN 2", 10, 0
_cadena4_ DB "ADENTRO DE FUN 1", 10, 0
y_global_funcion1_funcion2 DW 0 
z_global_funcion1_funcion2_funcion3 DD 0.0 
_cadena5_ DB "FUERA de funcion1", 10, 0
x_global DW 0 
y_global DW 0 
_cadena6_ DB "z>4", 10, 0
_cadena7_ DB "z<4", 10, 0
_float0_ DD 4.0
x_global_funcion1 DW 0 
@aux7 DW 0 
@aux6 DW 0 
@aux5 DW 0 
@aux4 DW 0 
@aux8 DW 0 
funcion1_global@param DW 0 
@aux3 DW 0 
@aux2 DD 0.0 
@aux1 DW 0 
funcion2_global_funcion1@param DW 0 

.CODE
  funcion1_global:
    MOV AX, funcion1_global@param
    MOV x_global_funcion1, AX
    invoke StdOut, addr _cadena4_

    funcion2_global_funcion1:
      MOV AX, funcion2_global_funcion1@param
      MOV y_global_funcion1_funcion2, AX

      invoke StdOut, addr _cadena3_

      funcion3_global_funcion1_funcion2:
        FST z_global_funcion1_funcion2_funcion3

        invoke StdOut, addr _cadena2_

        MOV CX, 0 
        FLD z_global_funcion1_funcion2_funcion3
        FLD _float0_
        FCOM ST(1)
        FSTSW AX
        SAHF
        SETG  CL
        MOV CH, 0 
        MOV @aux1, CX

        CMP @aux1, 0
        JE ET11

        invoke StdOut, addr _cadena6_

        MOV AX,8
        RET

        JMP ET14

        ET11:

        invoke StdOut, addr _cadena7_

        MOV AX,2
        RET

        ET14:

        MOV AX,0
      RET

      FILD y_global_funcion1_funcion2
      FST @aux2

      FLD @aux2
      CALL funcion3_global_funcion1_funcion2
      MOV @aux3, AX

      MOV AX, @aux3
      ADD AX, 2
      JC ??errorOverflow
      MOV @aux4, AX

      MOV AX,@aux4
    RET

    MOV AX, x_global_funcion1
    MOV funcion2_global_funcion1@param, AX
    CALL funcion2_global_funcion1
    MOV @aux5, AX

    MOV AX, @aux5
    ADD AX, x_global_funcion1
    JC ??errorOverflow
    MOV @aux6, AX

    MOV AX,@aux6
  RET

START:

  MOV AX, 1
  MOV x_global, AX

  MOV AX, x_global
  MOV funcion1_global@param, AX
  CALL funcion1_global
  MOV @aux7, AX

  MOV AX, @aux7
  MOV y_global, AX

  invoke StdOut, addr _cadena5_

  MOV CX, 0 
  MOV AX, y_global
  CMP AX, 50
  SETB CL
  MOV CH, 0 
  MOV @aux8, CX

  CMP @aux8, 0
  JE ET31

  invoke StdOut, addr _cadena0_

  JMP ET33

  ET31:

  invoke StdOut, addr _cadena1_

  ET33:

  JMP END_START

  ??errorOverflow:
    invoke StdOut, addr errorMsgOverflow
    JMP END_START

  ??errorConversionNegativo:
    invoke StdOut, addr errorMsgConversionNegativa
    JMP END_START

  ??errorRestaNegativa:
    invoke StdOut, addr errorMsgRestaNegativa

END_START: 
invoke ExitProcess, 0
END START
