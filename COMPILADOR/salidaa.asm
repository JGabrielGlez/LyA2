.MODEL SMALL
.STACK

.DATA
    num DW ?
    distancia DW ?

    ; --- CONSTANTES SIMBÓLICAS PARA PROCEDIMIENTOS ---
    VERDE_VAL EQU 1
    ROJO_VAL EQU 2
    SEGUNDOS_VAL EQU 1
    MILISEGUNDOS_VAL EQU 2

.CODE
    MOV AX, @DATA
    MOV DS, AX
    MOV ES, AX

; *, t0, 5.0, 5.0
    MOV AX, 5
    MOV BX, 5
    MUL BX
    MOV t0, AX

; +, t1, 25.0, t0
    MOV AX, 25
    ADD AX, t0
    MOV t1, AX

; DECL_NUMERO, num, t1
    ; Declaración de variable 't1' (definida en .DATA)

; DECL_SENSOR, sensor, 170
    ; Declaración de variable '170' (definida en .DATA)

; DECL_NUMERO, distancia, 15.0
    ; Declaración de variable '15.0' (definida en .DATA)


    MOV AH, 4Ch
    INT 21h

; --- DEFINICIONES DE PROCEDIMIENTOS DE COMANDOS DE ALTO NIVEL ---

ENCENDER_LED_PROC PROC
    ; ENTRADA: AX contiene el valor del LED (VERDE_VAL, ROJO_VAL, etc.)
    ; AQUI DEBES IMPLEMENTAR LA LÓGICA ENSAMBLADORA REAL PARA ENCENDER EL LED.
    NOP ; No Operation (Placeholder)
    RET
ENCENDER_LED_PROC ENDP

APAGAR_LED_PROC PROC
    ; ENTRADA: AX contiene el valor del LED a apagar (VERDE_VAL, ROJO_VAL, etc.)
    ; AQUI DEBES IMPLEMENTAR LA LÓGICA ENSAMBLADORA REAL PARA APAGAR EL LED.
    NOP ; No Operation (Placeholder)
    RET
APAGAR_LED_PROC ENDP

ESPERAR_PROC PROC
    ; ENTRADA: AX contiene la cantidad de tiempo (ej. 3)
    ;          BX contiene la unidad de tiempo (ej. SEGUNDOS_VAL)
    ; AQUI DEBES IMPLEMENTAR LA LÓGICA ENSAMBLADORA REAL PARA ESPERAR EL TIEMPO INDICADO.
    NOP ; No Operation (Placeholder)
    RET
ESPERAR_PROC ENDP

GIRAR_IZQUIERDA_PROC PROC
    ; ENTRADA: AX contiene el ángulo en grados (ej. 45)
    ; AQUI DEBES IMPLEMENTAR LA LÓGICA ENSAMBLADORA REAL PARA GIRAR A LA IZQUIERDA.
    NOP ; No Operation (Placeholder)
    RET
GIRAR_IZQUIERDA_PROC ENDP

GIRAR_DERECHA_PROC PROC
    ; ENTRADA: AX contiene el ángulo en grados (ej. 45)
    ; AQUI DEBES IMPLEMENTAR LA LÓGICA ENSAMBLADORA REAL PARA GIRAR A LA DERECHA.
    NOP ; No Operation (Placeholder)
    RET
GIRAR_DERECHA_PROC ENDP

MOVER_ADELANTE_PROC PROC
    ; ENTRADA: AX contiene la distancia (ej. 5 unidades)
    ; AQUI DEBES IMPLEMENTAR LA LÓGICA ENSAMBLADORA REAL PARA MOVER ADELANTE.
    NOP ; No Operation (Placeholder)
    RET
MOVER_ADELANTE_PROC ENDP

MOVER_ATRAS_PROC PROC
    ; ENTRADA: AX contiene la distancia (ej. 8 unidades)
    ; AQUI DEBES IMPLEMENTAR LA LÓGICA ENSAMBLADORA REAL PARA MOVER ATRAS.
    NOP ; No Operation (Placeholder)
    RET
MOVER_ATRAS_PROC ENDP

GET_SENSOR_PROC PROC
    ; ENTRADA: DX contiene el ID del sensor (ej. 170)
    ; SALIDA:  AX debe contener el valor leído del sensor
    ; AQUI DEBES IMPLEMENTAR LA LÓGICA ENSAMBLADORA REAL PARA LEER EL SENSOR.
    NOP ; No Operation (Placeholder)
    RET
GET_SENSOR_PROC ENDP

END
