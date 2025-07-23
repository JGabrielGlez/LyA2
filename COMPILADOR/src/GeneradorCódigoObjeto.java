// Archivo: GeneradorCodigoEnsamblador8086.java (ACTUALIZADO CON DECL_ Y MENORQUE)
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorCódigoObjeto {

    private List<InstruccionTAC> codigoIntermedio;
    private List<Analizador.EntradaTablaSimbolos> tablaSimbolos;
    private StringBuilder codigoEnsamblador;
    private int contadorRegistrosTemporales; 

    private Map<String, String> registroActualParaTemporal;

    public GeneradorCódigoObjeto(List<InstruccionTAC> codigoIntermedio, List<Analizador.EntradaTablaSimbolos> tablaSimbolos) {
        this.codigoIntermedio = codigoIntermedio;
        this.tablaSimbolos = tablaSimbolos;
        this.codigoEnsamblador = new StringBuilder();
        this.contadorRegistrosTemporales = 0;
        this.registroActualParaTemporal = new HashMap<>();
    }

    private String getRegistroTemporal() {
        String[] registros = {"AX", "BX", "CX", "DX"}; // Registros básicos de 16 bits
        if (contadorRegistrosTemporales < registros.length) {
            return registros[contadorRegistrosTemporales++];
        }
        return "AX"; 
    }

    public String generarCodigo() {
        codigoEnsamblador.append(".MODEL SMALL\n");
        codigoEnsamblador.append(".STACK \n\n");

        // Segmento de datos (.DATA)
        generarSegmentoDatos();

        // Segmento de código (.CODE)
        codigoEnsamblador.append(".CODE\n");


        // Inicializar DS (segmento de datos)
        codigoEnsamblador.append("    MOV AX, @DATA\n");
        codigoEnsamblador.append("    MOV DS, AX\n\n");

        // Generar instrucciones para cada cuádruplo
        for (InstruccionTAC instruccion : codigoIntermedio) {
            traducirInstruccion(instruccion);
        }

        // Finalización del programa
        codigoEnsamblador.append("\n    MOV AX, 4C00h\n"); // Función DOS para terminar
        codigoEnsamblador.append("    INT 21h\n\n");  
        codigoEnsamblador.append("END");
      

        return codigoEnsamblador.toString();
    }

    private void generarSegmentoDatos() {
        codigoEnsamblador.append(".DATA\n");
        for (Analizador.EntradaTablaSimbolos entrada : tablaSimbolos) {
           
            if ("numero".equals(entrada.getTipo())) { 
                codigoEnsamblador.append("    ").append(entrada.getNombre()).append(" DW ?\n");
            }
     
        }
        codigoEnsamblador.append("\n");
    }

    private void traducirInstruccion(InstruccionTAC instr) {
        codigoEnsamblador.append("; ").append(instr.getOperacion()); // Comentario para depuración
        if (instr.getResultado() != null) codigoEnsamblador.append(", ").append(instr.getResultado());
        if (instr.getArg1() != null) codigoEnsamblador.append(", ").append(instr.getArg1());
        if (instr.getArg2() != null) codigoEnsamblador.append(", ").append(instr.getArg2());
        codigoEnsamblador.append("\n"); // Fin del comentario de la línea de TAC

        switch (instr.getOperacion()) {
            case "DECL_NUMERO":
            case "DECL_SENSOR":
           
                codigoEnsamblador.append("    ; Declaración de variable, manejada en .DATA\n");
                break;

            case "ASIGNAR":
                String fuenteAsignar = obtenerValorORegistro(instr.getArg1());
                String destinoAsignar = instr.getResultado();
                codigoEnsamblador.append("    MOV ").append(destinoAsignar).append(", ").append(fuenteAsignar).append("\n");
                break;

            case "+":
                String op1Add = obtenerValorORegistro(instr.getArg1());
                String op2Add = obtenerValorORegistro(instr.getArg2());
                String resAdd = instr.getResultado();

                codigoEnsamblador.append("    MOV AX, ").append(op1Add).append("\n");
                codigoEnsamblador.append("    ADD AX, ").append(op2Add).append("\n");
                if (!resAdd.startsWith("t")) { // Si no es un temporal
                    codigoEnsamblador.append("    MOV ").append(resAdd).append(", AX\n");
                }
                break;

            case "*":
                String op1Mul = obtenerValorORegistro(instr.getArg1());
                String op2Mul = obtenerValorORegistro(instr.getArg2());
                String resMul = instr.getResultado();

                codigoEnsamblador.append("    MOV AX, ").append(op1Mul).append("\n");
                codigoEnsamblador.append("    MOV BX, ").append(op2Mul).append("\n");
                codigoEnsamblador.append("    MUL BX\n"); // AX = AX * BX
                if (!resMul.startsWith("t")) { // Si no es un temporal
                     codigoEnsamblador.append("    MOV ").append(resMul).append(", AX\n");
                }
                break;
            
            case "MENORQUE":
            case "LT":
                String op1Comp = obtenerValorORegistro(instr.getArg1());
                String op2Comp = obtenerValorORegistro(instr.getArg2());
             
                codigoEnsamblador.append("    MOV AX, ").append(op1Comp).append("\n");
                codigoEnsamblador.append("    CMP AX, ").append(op2Comp).append("\n");
                break;

            case "SI": 
                
                String etiquetaDestinoSI = instr.getArg1(); 
                codigoEnsamblador.append("    JGE ").append(etiquetaDestinoSI).append("\n"); 
                break;
            
            case "SINO": 
               
                String etiquetaSino = instr.getArg1();
                if (etiquetaSino != null) {
                    codigoEnsamblador.append(etiquetaSino).append(":\n");
                } else {
                    codigoEnsamblador.append("    ; ERROR: SINO sin etiqueta de destino.\n");
                }
                break;

            case "ETIQUETA":
                codigoEnsamblador.append(instr.getArg1()).append(":\n"); 
                break;

            case "ENCENDER_LED":
                codigoEnsamblador.append("    call ENCENDER_LED ").append(instr.getArg1()).append("\n");
                break;
            case "APAGAR_LED":
                codigoEnsamblador.append("    call APAGAR_LED ").append(instr.getArg1()).append("\n");
                break;
            case "ESPERAR":
                codigoEnsamblador.append("    call ESPERAR ").append(instr.getArg1()).append(" ").append(instr.getArg2()).append("\n");
                break;
            case "GIRAR_IZQUIERDA":
            case "GIRAR_DERECHA":
            case "MOVER_ADELANTE":
            case "MOVER_ATRAS":
                codigoEnsamblador.append("    ; Comando: ").append(instr.getOperacion()).append(" ").append(instr.getArg1()).append("\n");
                break;
            case "ROMPER": 
               
                if (instr.getArg1() != null) {
                    codigoEnsamblador.append("    JMP ").append(instr.getArg1()).append("\n");
                } else {
                    codigoEnsamblador.append("    ; ERROR: ROMPER sin etiqueta de destino en TAC. No se generó JMP.\n");
                }
                break;
            
            case "GET_SENSOR": 
          
                String sensorName = instr.getArg1(); // Nombre del sensor
                String resultTemp = instr.getResultado(); 
                codigoEnsamblador.append("    ; Obtener valor del sensor '").append(sensorName).append("' en ").append(resultTemp).append("\n");
             
                break;

            default:
                codigoEnsamblador.append("    ; ERROR: Operación TAC no reconocida: ").append(instr.getOperacion()).append("\n");
                break;
        }
        codigoEnsamblador.append("\n"); // Línea en blanco para legibilidad
    }

    private String obtenerValorORegistro(String operando) {
        if (operando == null) {
            return "";
        }
        try {
            Integer.parseInt(operando);
            return operando; // Es un número entero literal (ej. "10")
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(operando);
              
                return operando; 
            } catch (NumberFormatException e2) {
                return operando; 
            }
        }
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