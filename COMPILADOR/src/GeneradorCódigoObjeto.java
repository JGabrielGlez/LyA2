import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorCódigoObjeto {

    private List<InstruccionTAC> codigoIntermedio;
    private List<Analizador.EntradaTablaSimbolos> tablaSimbolos;
    private StringBuilder codigoEnsamblador;
    private int contadorRegistrosTemporales;
    private List<String> registrosDisponibles;
    private Map<String, String> registroActualParaTemporal;
    private Map<String, String> mapeoTemporales;
    private Set<String> constantesEnteras;
    private Map<String, String> valoresTemporales;
    private static final int SCALE_FACTOR = 10; 

    public GeneradorCódigoObjeto(List<InstruccionTAC> codigoIntermedio, List<Analizador.EntradaTablaSimbolos> tablaSimbolos) {
        this.codigoIntermedio = codigoIntermedio;
        this.tablaSimbolos = tablaSimbolos;
        this.codigoEnsamblador = new StringBuilder();
        this.contadorRegistrosTemporales = 0;
        this.registrosDisponibles = new ArrayList<>(List.of("AX", "BX", "CX", "DX"));
        this.registroActualParaTemporal = new HashMap<>();
        this.mapeoTemporales = new HashMap<>();
        this.constantesEnteras = new HashSet<>();
        this.valoresTemporales = new HashMap<>();
    }

    private String getRegistroTemporal() {
        if (contadorRegistrosTemporales < registrosDisponibles.size()) {
            return registrosDisponibles.get(contadorRegistrosTemporales++);
        }
        return "AX"; // Fallback
    }

    private void liberarRegistro(String registro) {
        if (registrosDisponibles.contains(registro)) return;
        registrosDisponibles.add(registro);
        contadorRegistrosTemporales = Math.max(0, contadorRegistrosTemporales - 1);
    }

    public String generarCodigo() {
        recolectarConstantesYMapearTemporales();
        
        codigoEnsamblador.append(".MODEL SMALL\n");
        codigoEnsamblador.append(".STACK 100h\n\n");

        generarSegmentoDatos();

        codigoEnsamblador.append(".CODE\n");
        codigoEnsamblador.append("    MOV AX, @DATA\n");
        codigoEnsamblador.append("    MOV DS, AX\n");

        for (InstruccionTAC instruccion : codigoIntermedio) {
            traducirInstruccion(instruccion);
        }

        codigoEnsamblador.append("\n    MOV AH, 4Ch\n"); // DOS exit
        codigoEnsamblador.append("    INT 21h\n");
        codigoEnsamblador.append("END\n");

        return codigoEnsamblador.toString();
    }

    private void recolectarConstantesYMapearTemporales() {
        for (InstruccionTAC instr : codigoIntermedio) {
            if (esConstanteEntera(instr.getArg1())) constantesEnteras.add(instr.getArg1());
            if (esConstanteEntera(instr.getArg2())) constantesEnteras.add(instr.getArg2());
        }
        
        for (int i = 0; i < codigoIntermedio.size(); i++) {
            InstruccionTAC instr = codigoIntermedio.get(i);
            if ((instr.getOperacion().equals("+") || instr.getOperacion().equals("*")) 
                && instr.getResultado() != null && instr.getResultado().startsWith("t")) {
                String temporal = instr.getResultado();
                for (int j = i + 1; j < codigoIntermedio.size(); j++) {
                    InstruccionTAC siguienteInstr = codigoIntermedio.get(j);
                    if ((siguienteInstr.getOperacion().equals("DECL_NUMERO") || 
                         siguienteInstr.getOperacion().equals("DECL_SENSOR")) &&
                        temporal.equals(siguienteInstr.getArg2())) {
                        mapeoTemporales.put(temporal, siguienteInstr.getArg1());
                        break;
                    }
                }
                if (!mapeoTemporales.containsKey(temporal)) {
                    valoresTemporales.put(temporal, "temp_" + temporal);
                }
            }
        }
    }

    private boolean esConstanteEntera(String valor) {
        if (valor == null) return false;
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String generarNombreConstante(String valor) {
        return "const_" + valor;
    }

    private void generarSegmentoDatos() {
        codigoEnsamblador.append(".DATA\n");
        for (String constante : constantesEnteras) {
            String nombreConstante = generarNombreConstante(constante);
            codigoEnsamblador.append("    ").append(nombreConstante).append(" DW ").append(constante).append("\n");
        }
        for (Analizador.EntradaTablaSimbolos entrada : tablaSimbolos) {
            codigoEnsamblador.append("    ").append(entrada.getNombre()).append(" DW ?\n");
        }
        for (String temp : valoresTemporales.values()) {
            codigoEnsamblador.append("    ").append(temp).append(" DW ?\n");
        }
        codigoEnsamblador.append("\n");
    }

    private void traducirInstruccion(InstruccionTAC instr) {
        codigoEnsamblador.append("; ").append(instr.getOperacion());
        if (instr.getResultado() != null) codigoEnsamblador.append(", ").append(instr.getResultado());
        if (instr.getArg1() != null) codigoEnsamblador.append(", ").append(instr.getArg1());
        if (instr.getArg2() != null) codigoEnsamblador.append(", ").append(instr.getArg2());
        codigoEnsamblador.append("\n");

        switch (instr.getOperacion()) {
            case "DECL_NUMERO":
                if (instr.getArg2() != null) {
                    String valor = obtenerValorORegistro(instr.getArg2());
                    String variable = instr.getArg1();
                    codigoEnsamblador.append("    MOV AX, ").append(valor).append("\n");
                    codigoEnsamblador.append("    MOV ").append(variable).append (", AX\n");
                }
                break;

            case "DECL_SENSOR":
                if (instr.getArg2() != null) {
                    String valor = obtenerValorORegistro(instr.getArg2());
                    String sensor = instr.getArg1();
                    codigoEnsamblador.append("    MOV AX, ").append(valor).append("\n");
                    codigoEnsamblador.append("    MOV ").append(sensor).append (", AX\n");
                }
                break;

            case "ASIGNAR":
                String fuente = obtenerValorORegistro(instr.getArg1());
                String destino = instr.getResultado();
                codigoEnsamblador.append("    MOV AX, ").append(fuente).append("\n");
                codigoEnsamblador.append("    MOV ").append(destino).append (", AX\n");
                break;

            case "+":
                String op1Add = obtenerValorORegistro(instr.getArg1());
                String op2Add = obtenerValorORegistro(instr.getArg2());
                String resAdd = obtenerValorORegistro(instr.getResultado());
                codigoEnsamblador.append("    MOV AX, ").append(op1Add).append("\n");
                codigoEnsamblador.append("    ADD AX, ").append(op2Add).append("\n");
                codigoEnsamblador.append("    MOV ").append(resAdd).append (", AX\n");
                break;

            case "*":
                String op1Mul = obtenerValorORegistro(instr.getArg1());
                String op2Mul = obtenerValorORegistro(instr.getArg2());
                String resMul = obtenerValorORegistro(instr.getResultado());
                codigoEnsamblador.append("    MOV AX, ").append(op1Mul).append("\n");
                codigoEnsamblador.append("    IMUL ").append(op2Mul).append("\n");
                codigoEnsamblador.append("    MOV ").append(resMul).append (", AX\n");
                break;

            case "MENORQUE":
                String op1Comp = obtenerValorORegistro(instr.getArg1());
                String op2Comp = obtenerValorORegistro(instr.getArg2());
                String resComp = obtenerValorORegistro(instr.getResultado());
                codigoEnsamblador.append("    MOV AX, ").append(op1Comp).append("\n");
                codigoEnsamblador.append("    CMP AX, ").append(op2Comp).append("\n");
                codigoEnsamblador.append("    MOV ").append(resComp).append (", 0\n");
                codigoEnsamblador.append("    JB LESS_").append(resComp).append("\n");
                codigoEnsamblador.append("    MOV ").append(resComp).append (", 1\n");
                codigoEnsamblador.append("LESS_").append(resComp).append (":\n");
                break;

            case "JUMP_IF_FALSE":
                String cond = obtenerValorORegistro(instr.getArg1());
                String labelFalse = instr.getArg2();
                codigoEnsamblador.append("    CMP ").append(cond).append (", 0\n");
                codigoEnsamblador.append("    JE ").append(labelFalse).append("\n");
                break;

            case "JUMP":
                codigoEnsamblador.append("    JMP ").append(instr.getArg1()).append("\n");
                break;

            case "ETIQUETA":
                codigoEnsamblador.append(instr.getArg1()).append(":\n");
                break;

            case "ENCENDER_LED":
                codigoEnsamblador.append("    MOV DX, OFFSET ").append(instr.getArg1()).append("\n");
                codigoEnsamblador.append("    CALL LED_ON\n");
                break;

            case "ESPERAR":
                codigoEnsamblador.append("    MOV CX, ").append(obtenerValorORegistro(instr.getArg1())).append("\n");
                codigoEnsamblador.append("    CALL DELAY\n");
                break;

            case "MOVER_ADELANTE":
                codigoEnsamblador.append("    MOV CX, ").append(obtenerValorORegistro(instr.getArg1())).append("\n");
                codigoEnsamblador.append("    CALL MOVE_FORWARD\n");
                break;

            default:
                codigoEnsamblador.append("    ; ERROR: Operación TAC no reconocida: ").append(instr.getOperacion()).append("\n");
                break;
        }
        codigoEnsamblador.append("\n");
    }

    private String obtenerValorORegistro(String operando) {
        if (operando == null) return "";
        if (constantesEnteras.contains(operando)) return generarNombreConstante(operando);
        String variableMapeada = mapeoTemporales.get(operando);
        if (variableMapeada != null) return variableMapeada;
        if (valoresTemporales.containsKey(operando)) return valoresTemporales.get(operando);
        return operando;
    }

    public void guardarEnsambladorEnArchivo(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(codigoEnsamblador.toString());
            System.out.println("Código ensamblador generado y guardado en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo ensamblador: " + e.getMessage());
        }
    }
}