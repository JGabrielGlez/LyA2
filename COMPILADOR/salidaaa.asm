.MODEL SMALL
.STACK 100h

.DATA
    const_33 DW 33
    const_200 DW 200
    const_300 DW 300
    numero1 DW ?
    numero2 DW ?
    sen DW ?
    temp_t0 DW ?
    temp_t1 DW ?
    temp_t2 DW ?
    temp_t3 DW ?

.CODE
    MOV AX, @DATA
    MOV DS, AX
; *, t0, 200, 200
    MOV AX, const_200
    IMUL const_200
    MOV temp_t0, AX

; +, t1, 200, 200
    MOV AX, const_200
    ADD AX, const_200
    MOV temp_t1, AX

; +, t2, t1, t0
    MOV AX, temp_t1
    ADD AX, temp_t0
    MOV temp_t2, AX

; DECL_NUMERO, numero1, t2

; +, t3, 200, 300
    MOV AX, const_200
    ADD AX, const_300
    MOV temp_t3, AX

; DECL_NUMERO, numero2, t3

; DECL_SENSOR, sen, 33


    MOV AH, 4Ch
    INT 21h
END
