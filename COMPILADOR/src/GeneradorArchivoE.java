
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class GeneradorArchivoE {
    
    private static final String RUTA_BASE = obtenerRutaProyecto() + File.separator + "src" + File.separator + "arbolE";
    private static final String PREFIJO_ARCHIVO = "E";
    private static final String EXTENSION = ".asm";
    
    private static String obtenerRutaProyecto() {
        // Obtener el directorio de trabajo actual (donde está el proyecto)
        String rutaProyecto = System.getProperty("user.dir");
        System.out.println("Ruta del proyecto detectada: " + rutaProyecto);
        return rutaProyecto;
    }
    
    public static String generarArchivoE(String contenido) {
        
        // Obtener el siguiente número del contador
        int contador = obtenerSiguienteContador();
        
        // Crear el nombre del archivo
        String nombreArchivo = PREFIJO_ARCHIVO + contador + EXTENSION;
        String rutaCompleta = RUTA_BASE + File.separator + nombreArchivo;
        
        try {
            Path path = Paths.get(rutaCompleta);
            Files.write(path, contenido.getBytes());
            System.out.println("Archivo creado exitosamente: " + nombreArchivo);
            System.out.println("Ruta completa: " + rutaCompleta);
            return nombreArchivo;
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
            return null;
        }
    }
    
     public static String generarArchivo(String contenido) {
        
        // Obtener el siguiente número del contador
        int contador = obtenerSiguienteContador();
        
        // Crear el nombre del archivo
        String nombreArchivo = "objeto" + contador + ".asm";
        String rutaCompleta = RUTA_BASE + File.separator + nombreArchivo;
        
        try {
            Path path = Paths.get(rutaCompleta);
            Files.write(path, contenido.getBytes());
            System.out.println("Archivo creado exitosamente: " + nombreArchivo);
            System.out.println("Ruta completa: " + rutaCompleta);
            return nombreArchivo;
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
            return null;
        }
    }
    
    
    private static int obtenerSiguienteContador() {
        File directorio = new File(RUTA_BASE);
        File[] archivos = directorio.listFiles();
        
        if (archivos == null || archivos.length == 0) {
            return 1; // Si no hay archivos, empezar con 1
        }
        
        int mayorNumero = 0;
        
        // Buscar archivos que coincidan con el patrón E[numero].asm
        for (File archivo : archivos) {
            if (archivo.isFile() && archivo.getName().startsWith(PREFIJO_ARCHIVO) && 
                archivo.getName().endsWith(EXTENSION)) {
                
                String nombreArchivo = archivo.getName();
                // Extraer el número entre "E" y ".asm"
                String numeroStr = nombreArchivo.substring(
                    PREFIJO_ARCHIVO.length(), 
                    nombreArchivo.length() - EXTENSION.length()
                );
                
                try {
                    int numero = Integer.parseInt(numeroStr);
                    if (numero > mayorNumero) {
                        mayorNumero = numero;
                    }
                } catch (NumberFormatException e) {
                    // Ignorar archivos que no tengan número válido
                }
            }
        }
        
        return mayorNumero + 1;
    }
    
    public static int obtenerUltimoContador() {
        int siguiente = obtenerSiguienteContador();
        return siguiente > 1 ? siguiente - 1 : 0;
    }
    
}