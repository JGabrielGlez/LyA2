import java.util.HashMap;
import java.util.Map;

public class GeneradorCodigoObjeto {
    private GeneradorCodigoIntermedio generadorTAC;
    private StringBuilder codigoASM;
    private Map<String, String> variablesDeclaradas;
    private Map<String, String> temporalesUsadas;
    private int contadorVariables;
    
    public GeneradorCodigoObjeto(GeneradorCodigoIntermedio generador) {
        this.generadorTAC = generador;
        this.codigoASM = new StringBuilder();
        this.variablesDeclaradas = new HashMap<>();
        this.temporalesUsadas = new HashMap<>();
        this.contadorVariables = 0;
    }
    
    public String generarCodigoASM() {
        inicializarCodigo();
        procesarCuadruplos();
        finalizarCodigo();
        return codigoASM.toString();
    }
    
    private void inicializarCodigo() {
        codigoASM.append(";*****************\n");
        codigoASM.append(";*****************\n\n");
        codigoASM.append(".model small\n");
        codigoASM.append(".stack\n");
        codigoASM.append(".data\n");
        
        // Primera pasada: declarar todas las variables y temporales necesarias
        for (InstruccionTAC instruccion : generadorTAC.getCodigo()) {
            declararVariablesSiEsNecesario(instruccion);
        }
        
        codigoASM.append("\n; ======== seccion de macros =============\n\n");
        generarMacrosBasicos();
        codigoASM.append("\n;==============================================\n");
        codigoASM.append(".code\n");
        codigoASM.append("mov ax, @data\n");
        codigoASM.append("mov ds, ax\n\n");
    }
    
    private void declararVariablesSiEsNecesario(InstruccionTAC instruccion) {
        // Declarar variables según el tipo de instrucción
        switch (instruccion.getOperacion()) {
            case "DECL_NUMERO":
                if (!variablesDeclaradas.containsKey(instruccion.getResultado())) {
                    codigoASM.append("    ").append(instruccion.getResultado())
                            .append(" dw 0\n");
                    variablesDeclaradas.put(instruccion.getResultado(), "dw");
                }
                break;
                
            case "DECL_SENSOR":
                if (!variablesDeclaradas.containsKey(instruccion.getResultado())) {
                    codigoASM.append("    ").append(instruccion.getResultado())
                            .append(" dw ").append(instruccion.getArg1()).append("\n");
                    variablesDeclaradas.put(instruccion.getResultado(), "dw");
                }
                break;
        }
        
        // Declarar temporales usadas
        declararTemporal(instruccion.getResultado());
        declararTemporal(instruccion.getArg1());
        declararTemporal(instruccion.getArg2());
    }
    
    private void declararTemporal(String variable) {
        if (variable != null && variable.startsWith("t") && 
            !temporalesUsadas.containsKey(variable) && 
            !esNumerico(variable)) {
            codigoASM.append("    ").append(variable).append(" dw 0\n");
            temporalesUsadas.put(variable, "dw");
        }
    }
    
    private void generarMacrosBasicos() {
        // Macro para imprimir número
        codigoASM.append("imprimir_numero macro var\n");
        codigoASM.append("    mov ax, var\n");
        codigoASM.append("    add ax, 30h    ; convertir a ASCII\n");
        codigoASM.append("    mov dl, al\n");
        codigoASM.append("    mov ah, 2\n");
        codigoASM.append("    int 21h\n");
        codigoASM.append("imprimir_numero endm\n\n");
        
        // Macro para esperar
        codigoASM.append("esperar_tiempo macro\n");
        codigoASM.append("    mov cx, 0FFFFh\n");
        codigoASM.append("bucle_espera:\n");
        codigoASM.append("    dec cx\n");
        codigoASM.append("    jnz bucle_espera\n");
        codigoASM.append("esperar_tiempo endm\n\n");
    }
    
    private void procesarCuadruplos() {
        for (InstruccionTAC instruccion : generadorTAC.getCodigo()) {
            generarCodigoParaInstruccion(instruccion);
        }
    }
    
    private void generarCodigoParaInstruccion(InstruccionTAC instruccion) {
        String op = instruccion.getOperacion();
        String resultado = instruccion.getResultado();
        String arg1 = instruccion.getArg1();
        String arg2 = instruccion.getArg2();
        
        codigoASM.append("    ; ").append(op);
        if (resultado != null) codigoASM.append(" ").append(resultado);
        if (arg1 != null) codigoASM.append(" ").append(arg1);
        if (arg2 != null) codigoASM.append(" ").append(arg2);
        codigoASM.append("\n");
        
        switch (op) {
            case "DECL_NUMERO":
                if (arg1 != null) {
                    moverValorAVariable(arg1, resultado);
                }
                break;
                
            case "ASIGNAR":
                moverValorAVariable(arg1, resultado);
                break;
                
            case "+":
            case "ADD":
                realizarOperacionAritmetica("add", resultado, arg1, arg2);
                break;
                
            case "-":
                realizarOperacionAritmetica("sub", resultado, arg1, arg2);
                break;
                
            case "*":
                realizarMultiplicacion(resultado, arg1, arg2);
                break;
                
            case "/":
                realizarDivision(resultado, arg1, arg2);
                break;
                
            case "MENORQUE":
            case "<":
                realizarComparacion("jl", resultado, arg1, arg2);
                break;
                
            case "MAYORQUE":
            case ">":
                realizarComparacion("jg", resultado, arg1, arg2);
                break;
                
            case "IGUALQUE":
            case "==":
                realizarComparacion("je", resultado, arg1, arg2);
                break;
                
            case "MENORIG":
            case "<=":
                realizarComparacion("jle", resultado, arg1, arg2);
                break;
                
            case "C_GE":
            case ">=":
                realizarComparacion("jge", resultado, arg1, arg2);
                break;
                
            case "ETIQUETA":
                codigoASM.append(arg1).append(":\n");
                break;
                
            case "JUMP":
                codigoASM.append("    jmp ").append(arg1).append("\n");
                break;
                
            case "MOVER_ADELANTE":
                codigoASM.append("    ; Comando: mover adelante\n");
                codigoASM.append("    mov ax, ").append(arg1).append("\n");
                codigoASM.append("    ; Aquí iría la lógica específica del robot\n");
                break;
                
            case "MOVER_ATRAS":
                codigoASM.append("    ; Comando: mover atrás\n");
                codigoASM.append("    mov ax, ").append(arg1).append("\n");
                break;
                
            case "GIRAR_IZQUIERDA":
            case "GIRAR_DERECHA":
                codigoASM.append("    ; Comando: ").append(op.toLowerCase()).append("\n");
                codigoASM.append("    mov ax, ").append(arg1).append("\n");
                break;
                
            case "ENCENDER_LED":
            case "APAGAR_LED":
                codigoASM.append("    ; ").append(op).append(" ").append(arg1).append("\n");
                break;
                
            case "ESPERAR":
                codigoASM.append("    esperar_tiempo\n");
                break;
                
            case "LEER_SENSOR":
                codigoASM.append("    ; Leer sensor ").append(arg1).append("\n");
                codigoASM.append("    mov ax, ").append(arg1).append("\n");
                codigoASM.append("    mov ").append(resultado).append(", ax\n");
                break;
                
            case "RETORNO":
                codigoASM.append("    ; Fin del método\n");
                break;
                
            default:
                codigoASM.append("    ; Operación no implementada: ").append(op).append("\n");
                break;
        }
        codigoASM.append("\n");
    }
    
    private void moverValorAVariable(String origen, String destino) {
        if (esNumerico(origen)) {
            codigoASM.append("    mov ax, ").append(origen).append("\n");
        } else {
            codigoASM.append("    mov ax, ").append(origen).append("\n");
        }
        codigoASM.append("    mov ").append(destino).append(", ax\n");
    }
    
    private void realizarOperacionAritmetica(String operacion, String resultado, String arg1, String arg2) {
        // Cargar primer operando en AX
        if (esNumerico(arg1)) {
            codigoASM.append("    mov ax, ").append(arg1).append("\n");
        } else {
            codigoASM.append("    mov ax, ").append(arg1).append("\n");
        }
        
        // Realizar operación con segundo operando
        if (esNumerico(arg2)) {
            codigoASM.append("    mov bx, ").append(arg2).append("\n");
            codigoASM.append("    ").append(operacion).append(" ax, bx\n");
        } else {
            codigoASM.append("    ").append(operacion).append(" ax, ").append(arg2).append("\n");
        }
        
        // Guardar resultado
        codigoASM.append("    mov ").append(resultado).append(", ax\n");
    }
    
    private void realizarMultiplicacion(String resultado, String arg1, String arg2) {
        // Cargar primer operando en AX
        if (esNumerico(arg1)) {
            codigoASM.append("    mov ax, ").append(arg1).append("\n");
        } else {
            codigoASM.append("    mov ax, ").append(arg1).append("\n");
        }
        
        // Cargar segundo operando en BX y multiplicar
        if (esNumerico(arg2)) {
            codigoASM.append("    mov bx, ").append(arg2).append("\n");
        } else {
            codigoASM.append("    mov bx, ").append(arg2).append("\n");
        }
        
        codigoASM.append("    mul bx\n");
        codigoASM.append("    mov ").append(resultado).append(", ax\n");
    }
    
    private void realizarDivision(String resultado, String arg1, String arg2) {
        // Limpiar DX para división
        codigoASM.append("    xor dx, dx\n");
        
        // Cargar dividendo en AX
        if (esNumerico(arg1)) {
            codigoASM.append("    mov ax, ").append(arg1).append("\n");
        } else {
            codigoASM.append("    mov ax, ").append(arg1).append("\n");
        }
        
        // Cargar divisor en BX y dividir
        if (esNumerico(arg2)) {
            codigoASM.append("    mov bx, ").append(arg2).append("\n");
        } else {
            codigoASM.append("    mov bx, ").append(arg2).append("\n");
        }
        
        codigoASM.append("    div bx\n");
        codigoASM.append("    mov ").append(resultado).append(", ax\n");
    }
    
    private void realizarComparacion(String saltoCondicional, String resultado, String arg1, String arg2) {
        // Cargar primer operando en AX
        if (esNumerico(arg1)) {
            codigoASM.append("    mov ax, ").append(arg1).append("\n");
        } else {
            codigoASM.append("    mov ax, ").append(arg1).append("\n");
        }
        
        // Comparar con segundo operando
        if (esNumerico(arg2)) {
            codigoASM.append("    mov bx, ").append(arg2).append("\n");
            codigoASM.append("    cmp ax, bx\n");
        } else {
            codigoASM.append("    cmp ax, ").append(arg2).append("\n");
        }
        
        // Establecer resultado basado en la comparación
        String etiquetaVerdadero = "comp_true_" + (contadorVariables++);
        String etiquetaFin = "comp_end_" + contadorVariables;
        
        codigoASM.append("    ").append(saltoCondicional).append(" ").append(etiquetaVerdadero).append("\n");
        codigoASM.append("    mov ").append(resultado).append(", 0    ; falso\n");
        codigoASM.append("    jmp ").append(etiquetaFin).append("\n");
        codigoASM.append(etiquetaVerdadero).append(":\n");
        codigoASM.append("    mov ").append(resultado).append(", 1    ; verdadero\n");
        codigoASM.append(etiquetaFin).append(":\n");
    }
    
    private boolean esNumerico(String str) {
        if (str == null) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void finalizarCodigo() {
        codigoASM.append("\n");
        codigoASM.append("salir: mov ax, 4c00h\n");
        codigoASM.append("       int 21h\n");
        codigoASM.append("end\n");
    }
}
