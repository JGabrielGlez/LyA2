.MODEL SMALL
.STACK 100h

.DATA
    const_33 DW 33
    const_2 DW 2
    const_3 DW 3
    const_10000 DW 10000
    const_10 DW 10
    numero1 DW ?
    numeros DW ?
    numero2 DW ?
    sen DW ?
    temp_t4 DW ?
    temp_t5 DW ?
    temp_t0 DW ?
    temp_t1 DW ?
    temp_t2 DW ?
    temp_t3 DW ?

.CODE
    MOV AX, @DATA
    MOV DS, AX
; *, t0, 2, 2
    MOV AX, const_2
    IMUL const_2
    MOV temp_t0, AX

; +, t1, 2, 2
    MOV AX, const_2
    ADD AX, const_2
    MOV temp_t1, AX

; +, t2, t1, t0
    MOV AX, temp_t1
    ADD AX, temp_t0
    MOV temp_t2, AX

; DECL_NUMERO, numero1, t2
    MOV numero1, ax

; /, t3, 10000, 10
    MOV AX, const_10000
    IDIV const_10
    MOV temp_t3, AX

; /, t4, t3, 2
    MOV AX, temp_t3
    IDIV const_2
    MOV temp_t4, AX

; DECL_NUMERO, numeros, t4
    MOV numeros, ax

; -, t5, 2, 3
    MOV AX, const_2
    SUB AX, const_3
    MOV temp_t5, AX

; DECL_NUMERO, numero2, t5
    MOV numero2, ax

; DECL_SENSOR, sen, 33
    MOV sen, 33

    MOV AH, 4Ch
    INT 21h
END
