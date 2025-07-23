/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oswal
 */
// Nuevo archivo: GeneradorCodigoIntermedio.java
import java.util.ArrayList;
import java.util.List;

public class GeneradorCodigoIntermedio {
    private List<InstruccionTAC> codigo;
    private int contadorTemporales;
    private int contadorEtiquetas;

    public GeneradorCodigoIntermedio() {
        this.codigo = new ArrayList<>();
        this.contadorTemporales = 0;
        this.contadorEtiquetas = 0;
    }

    public String nuevaTemporal() {
        return "t" + (contadorTemporales++);
    }

    public String nuevaEtiqueta() {
        return "L" + (contadorEtiquetas++);
    }

    public void emitir(String operacion, String resultado, String arg1, String arg2) {
        codigo.add(new InstruccionTAC(operacion, resultado, arg1, arg2));
    }

    public void emitir(String operacion, String resultado, String arg1) {
        codigo.add(new InstruccionTAC(operacion, resultado, arg1));
    }
    
    public void emitir(String operacion, String arg1) {
        codigo.add(new InstruccionTAC(operacion, arg1));
    }
    public void emitir(String operacion) {
        codigo.add(new InstruccionTAC(operacion));
    }


    public List<InstruccionTAC> getCodigo() {
        return codigo;
    }
    
    public void agregarEtiqueta(String etiqueta) {
        emitir("ETIQUETA", etiqueta); // Una instrucción TAC especial para etiquetas
    }
    
  public void imprimirCuadruplosEnTabla() {
    System.out.println("\n--- Cuádruplos Generados (Tabla) ---");
    System.out.println("------------------------------------------------------------------");
    // Nombres de las columnas ajustados para reflejar los campos de tu InstruccionTAC
    System.out.printf("%-10s %-15s %-15s %-15s\n", "OPERACION", "ARG1", "ARG2", "RESULTADO");
    System.out.println("------------------------------------------------------------------");

    for (InstruccionTAC instruccion : codigo) { 
        String op = (instruccion.getOperacion() != null) ? instruccion.getOperacion() : "";
        String a1 = (instruccion.getArg1() != null) ? instruccion.getArg1() : "";
        String a2 = (instruccion.getArg2() != null) ? instruccion.getArg2() : "";
        String res = (instruccion.getResultado() != null) ? instruccion.getResultado() : "";

        System.out.printf("%-10s %-15s %-15s %-15s\n", 
                          op, 
                          a1, 
                          a2, 
                          res);
    }
    System.out.println("------------------------------------------------------------------");
}
  
  // En GeneradorCodigoIntermedio.java

public String obtenerCuadruplosEnFormatoTabla() { // Nuevo nombre del método
    StringBuilder tabla = new StringBuilder();
    tabla.append("\n--- Cuádruplos Generados (Tabla) ---\n");
    tabla.append("------------------------------------------------------------------\n");
    tabla.append(String.format("%-10s %-15s %-15s %-15s\n", "OPERACION", "ARG1", "ARG2", "RESULTADO"));
    tabla.append("------------------------------------------------------------------\n");

    for (InstruccionTAC instruccion : codigo) {
        String op = (instruccion.getOperacion() != null) ? instruccion.getOperacion() : "";
        String a1 = (instruccion.getArg1() != null) ? instruccion.getArg1() : "";
        String a2 = (instruccion.getArg2() != null) ? instruccion.getArg2() : "";
        String res = (instruccion.getResultado() != null) ? instruccion.getResultado() : "";

        tabla.append(String.format("%-10s %-15s %-15s %-15s\n", op, a1, a2, res));
    }
    tabla.append("------------------------------------------------------------------\n");
    return tabla.toString(); // Retorna la cadena completa de la tabla
}
  
}
