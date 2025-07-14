

import java.util.List;
// import java.util.ArrayList; // No necesario si Analizador.getTablaSimbolosCompleta() ya es estático y devuelve la lista completa.

public class CompiladorConsola {

    public static void main(String[] args) {

        String codigoFuenteDePrueba = "iniciar \n" +
            "num tipo numero = 25+5*5\n" +
            "sensor tipo Sensor = 170\n" +
             "distancia tipo numero = 15\n" +
                "si distancia < 20 entonces\n" +
"        encender_led verde\n" +
"        esperar 3 segundos\n" +
"        apagar_led rojo\n" +
"        girar_izquierda 45\n" +
"        mover_adelante 5\n" +
"        girar_derecha 45\n" +
"mover_adelante 8\n" +
"        apagar_led verde\n" +
"        romper\n" +
"    fin si\n"+
                
            "Terminar"; 

        System.out.println("--- Iniciando Proceso de Compilación ---");
        System.out.println("Código Fuente:\n" + codigoFuenteDePrueba + "\n");

        try {
            
            List<Analizador.Token> tokens = Analizador.analizarLexico(codigoFuenteDePrueba);
            
            List<Analizador.EntradaTablaSimbolos> tablaSimbolos = Analizador.getTablaSimbolosCompleta();

 
            AnalizadorSintactico analizadorSintactico = 
                new AnalizadorSintactico(tokens, tablaSimbolos);
            
            
            ProgramaNodo programaAST = analizadorSintactico.parsear(tokens, tablaSimbolos); // Tu método parsear() recibe tokens y tablaSimbolos

            if (analizadorSintactico.getErrores().length() > 0) {
                System.err.println("Errores durante la compilación:\n");
                System.err.println(analizadorSintactico.getErrores().toString());
            } else if (programaAST != null) {
                System.out.println("Análisis sintáctico y generación de código intermedio completados con éxito.\n");
                
                
                analizadorSintactico.getGeneradorCodigo().imprimirCuadruplosEnTabla();
                
            } else {
                System.err.println("Error desconocido: No se pudo construir el AST ni generar cuádruplos.");
            }

        } catch (Exception e) {
            System.err.println("¡Ocurrió un error inesperado durante la compilación!");
            e.printStackTrace(); // Imprime el stack trace para depuración detallada
        }

        System.out.println("\n--- Proceso de Compilación Finalizado ---");
    }
}