option casemap :none
include \masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib
.STACK 200h

.DATA
errorMsgOverflow db "Overflow: en suma!.", 10, 0 ;
errorMsgConversionNegativa db "Conversion invalida: no puede convertir un Single negativo a Entero!.", 10, 0 ;
errorMsgRestaNegativa db "Resta invalida: resultado negativo!.", 10, 0 ;
i_global_funcion DW 0 
i_global_funcion_funcion2 DW 0 
z_global DD 0.0 
x_global DW 0 
y_global DW 0 
_float0_ DD 2.0
l_global_funcion DW 0 
@aux7 DW 0 
@aux6 DW 0 
@aux5 DW 0 
@aux4 DW 0 
@aux9 DW 0 
@aux8 DW 0 
@aux3 DW 0 
@aux2 DW 0 
@aux1 DW 0 
funcion2_global_funcion@param DW 0 
funcion_global@param DW 0 
@aux11 DW 0 
@aux12 DW 0 
@aux13 DW 0 
@aux14 DW 0 
@aux10 DW 0 
@aux15 DW 0 
@aux16 DW 0 

.CODE
  funcion_global:
    MOV AX, funcion_global@param
    MOV l_global_funcion, AX
    funcion2_global_funcion:
      MOV AX, funcion2_global_funcion@param
      MOV i_global_funcion_funcion2, AX

      MOV AX, i_global_funcion_funcion2
      MOV BX, 2
      MUL BX 
      MOV @aux3, AX

      MOV AX, l_global_funcion
      ADD AX, @aux3
      JC ??errorOverflow
      MOV @aux4, AX

      MOV AX,@aux4
    RET

    ET11:

    MOV CX, 0 
    MOV AX, l_global_funcion
    CMP AX, 80
    SETBE CL
    MOV CH, 0 
    MOV @aux5, CX

    CMP @aux5, 0
    JE ET32

    MOV AX, l_global_funcion
    MOV funcion2_global_funcion@param, AX
    CALL funcion2_global_funcion
    MOV @aux6, AX

    MOV AX, @aux6
    MOV l_global_funcion, AX

    MOV CX, 0 
    MOV AX, l_global_funcion
    CMP AX, 80
    SETB CL
    MOV CH, 0 
    MOV @aux7, CX

    CMP @aux7, 0
    JE ET24

    ET18:

    MOV AX, i_global_funcion
    ADD AX, 1
    JC ??errorOverflow
    MOV @aux8, AX

    MOV AX, @aux8
    MOV i_global_funcion, AX

    MOV CX, 0 
    MOV  AX, i_global_funcion
    CMP AX, 7
    SETA CL
    MOV CH, 0 
    MOV @aux9, CX

    CMP @aux9, 0
    JE ET18

    JMP ET30

    ET24:

    MOV CX, 0 
    MOV  AX, l_global_funcion
    CMP AX, 80
    SETA CL
    MOV CH, 0 
    MOV @aux10, CX

    CMP @aux10, 0
    JE ET29

    MOV AX, l_global_funcion
    ADD AX, 5
    JC ??errorOverflow
    MOV @aux11, AX

    MOV AX, @aux11
    MOV l_global_funcion, AX

    ET29:

    ET30:

    JMP ET34

    ET32:

    MOV AX,l_global_funcion
    RET

    ET34:

    MOV CX, 0 
    MOV AX, l_global_funcion
    CMP AX, 100
    SETAE CL
    MOV CH, 0 
    MOV @aux12, CX

    CMP @aux12, 0
    JE ET11

    MOV AX,l_global_funcion
  RET

START:
  FLD _float0_
  FST z_global

  reinicio@_global:

  MOV AX, x_global
  ADD AX, 5
  JC ??errorOverflow
  MOV @aux1, AX

  MOV AX, @aux1
  MOV x_global, AX

  MOV AX, y_global
  ADD AX, 2
  JC ??errorOverflow
  MOV @aux2, AX

  MOV AX, @aux2
  MOV y_global, AX


  MOV AX, x_global
  ADD AX, y_global
  JC ??errorOverflow
  MOV @aux13, AX

  MOV AX, x_global
  MOV funcion_global@param, AX
  CALL funcion_global
  MOV @aux14, AX

  MOV AX, @aux13
  ADD AX, @aux14
  JC ??errorOverflow
  MOV @aux15, AX

  MOV AX, @aux15
  MOV x_global, AX

  MOV CX, 0 
  MOV AX, x_global
  CMP AX, 150
  SETB CL
  MOV CH, 0 
  MOV @aux16, CX

  CMP @aux16, 0
  JE ET45

  JMP reinicio@_global

  ET45:

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
