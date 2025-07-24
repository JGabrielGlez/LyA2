.MODEL SMALL
.STACK 100h

.DATA
    const_33 DW 33
    const_2 DW 2
    const_3 DW 3
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

; +, t3, 2, 3
    MOV AX, const_2
    ADD AX, const_3
    MOV temp_t3, AX

; DECL_NUMERO, numero2, t3
    MOV numero2, ax

; DECL_SENSOR, sen, 33
    MOV sen, 33

    MOV AH, 4Ch
    INT 21h
END
