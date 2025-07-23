.MODEL SMALL
.STACK 

.DATA
    numoers DW ?

.CODE
    MOV AX, @DATA
    MOV DS, AX

; DECL_SENSOR, sensor1, 3
    ; Declaración de variable, manejada en .DATA

; +, t0, 3.0, 3.0
    MOV AX, 3.0
    ADD AX, 3.0

; +, t1, t0, 3.0
    MOV AX, t0
    ADD AX, 3.0

; +, t2, t1, 3.0
    MOV AX, t1
    ADD AX, 3.0

; DECL_NUMERO, numoers, t2
    ; Declaración de variable, manejada en .DATA

; ENCENDER_LED, verde
    call ENCENDER_LED verde

; APAGAR_LED, verde
    call APAGAR_LED verde


    MOV AX, 4C00h
    INT 21h

END