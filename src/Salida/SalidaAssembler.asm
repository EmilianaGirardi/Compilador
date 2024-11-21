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
_cadena2_ DB "FUERA de funcion1", 10, 0
x_global DW 0 
y_global DW 0 
x_global_funcion1 DW 0 
@aux3 DW 0 
@aux2 DW 0 
@aux1 DW 0 

.CODE
  funcion1_global:
    PUSH EBP
    MOV EBP, ESP
    MOV AX, [EBP+8]
    MOV x_global_funcion1, AX
    MOV AX, 10
    ADD AX, x_global_funcion1
    JC ??errorOverflow
    MOV @aux1, AX

    MOV AX,@aux1
    MOV ESP, EBP
    POP EBP
  RET

  MOV AX, 1
  MOV x_global, AX

  PUSH x_global
  CALL funcion1_global
  MOV @aux2, AX

  MOV AX, @aux2
  MOV y_global, AX

  invoke StdOut, addr _cadena2_

  MOV CX, 0 
  MOV AX, y_global
  CMP AX, 50
  SETB CL
  MOV CH, 0 
  MOV @aux3, CX

  CMP @aux3, 0
  JE ET11

  invoke StdOut, addr _cadena0_

  JMP ET13

ET11:

  invoke StdOut, addr _cadena1_

ET13:

START:

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
