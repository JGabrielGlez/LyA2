import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Nodo base para declaraciones
 * Según gramática: <declaracion> ::= <declaracion_numero> | <declaracion_sensor>
 */
abstract class DeclaracionNodo  extends NodoAST{
    protected String identificador;
    
    public DeclaracionNodo(String identificador, int linea, int columna) {
        super(linea, columna);
        this.identificador = identificador;
    }
    
    public String getIdentificador() { return identificador; }
}


//============================================== clase del 30 de junio
//agregar los metodos al lenguaje
/**
 * Para: <declaracion_metodo> ::= "iniciar_metodo" <identificador> "("  <parametros>  ")"<declaraciones> <instrucciones>  "fin_metodo"
 */
class DeclaracionMetodoNodo extends DeclaracionNodo{
    private Set<ParametroNodo> parametros; //Valeria
    private List<DeclaracionNodo> declaraciones;
    private List<NodoAST> instrucciones;

    public DeclaracionMetodoNodo(String identificador, int linea, int columna) {
        super(identificador, linea, columna);
        this.parametros = new HashSet<>();
        this.instrucciones = new ArrayList<>();
        this.declaraciones = new ArrayList<>();
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
    public void agregarInstruccion(NodoAST instruccion) { instrucciones.add(instruccion); }
    public void agregarDeclaracion(DeclaracionNodo declaracion) { declaraciones.add(declaracion); }
    
    /**
     * 
     * @param parametro
     * @return falso cuando no se puede agregar el parámetro debido a que su identificador ya fue usado
     * 
     */
    public boolean agregarParametro(ParametroNodo parametro) { 
       
        for (ParametroNodo p : parametros) {
        if (p.getIdentificador().equals(parametro.getIdentificador())) {
            return false; // Ya existe
        }
    }
    // Si no existe, agregar el objeto completo
    parametros.add(parametro);
    return true;
    }



    @Override
    public String toString() {
return "";        
//throw new UnsupportedOperationException("Not supported yet."); 
    }

    public Set<ParametroNodo> getParametros() {
        return parametros;
    }

    public void setParametros(Set<ParametroNodo> parametros) {
        this.parametros = parametros;
    }

    

    
    

    public List<DeclaracionNodo> getDeclaraciones() {
        return declaraciones;
    }

    public void setDeclaraciones(List<DeclaracionNodo> declaraciones) {
        this.declaraciones = declaraciones;
    }

    public List<NodoAST> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<NodoAST> instrucciones) {
        this.instrucciones = instrucciones;
 }
}
/**
 * <usar_metodo>::= <identificador> "(" (parametros | ε) ")"
 */
class MetodoNodo extends NodoAST{
    private String identificador;
    private Set<ParametroNodo> parametros; //Valeria

    public MetodoNodo(String identificador, Set<ParametroNodo> parametros) {
        this.identificador = identificador;
        this.parametros = parametros;
    }

    public MetodoNodo(String identificador, Set<ParametroNodo> parametros, int linea, int columna) {
        super(linea, columna);
        this.identificador = identificador;
        this.parametros = parametros;
    }
    
    public MetodoNodo(String identificador,  int linea, int columna) {
        super(linea, columna);
        this.identificador = identificador;
        this.parametros = new HashSet<>();
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Set<ParametroNodo> getParametros() {
        return parametros;
    }

    public void setParametros(Set<ParametroNodo> parametros) {
        this.parametros = parametros;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    

    @Override
    public String toString() {
        return "MetodoNodo{" + "identificador=" + identificador + ", parametros=" + parametros + '}';
    }
    
    

}

/**
 * Para: <parametrounico> :: = <identificador> ("tipo numero"| "tipo Sensor")
 */

class ParametroNodo extends NodoAST{
    private String identificador, tipo;

    public ParametroNodo(String identificador, String tipo) {
        this.identificador = identificador;
        this.tipo = tipo;
    }

    public ParametroNodo(String identificador, String tipo, int linea, int columna) {
        super(linea, columna);
        this.identificador = identificador;
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
return "";        
    }
    
    
    
}
//es un nodo Declaracion, que se manejará con el método de parseo correspondiente. 


/**
 * Para: <parametros> :: = <parametrounico> (ε |"," <parametros>  )
 */

//==============================================

/**
 * Para: <declaracion_numero> ::= <identificador> "tipo" "numero" "=" <expresion>
 * Ejemplo: x tipo numero = 5 + 3
 */
class DeclaracionNumeroNodo extends DeclaracionNodo {
    private ExpresionNodo valorInicial;
    
    public DeclaracionNumeroNodo(String identificador, ExpresionNodo valorInicial, int linea, int columna) {
        super(identificador, linea, columna);
        this.valorInicial = valorInicial;
    }
    
    @Override
    public String toString() {
        return "DeclaracionNumero(" + identificador + " = " + valorInicial + ")";
    }
    
    @Override
    public List<NodoAST> getHijos() {
        List<NodoAST> hijos = new ArrayList<>();
        if (valorInicial != null) hijos.add(valorInicial);
        return hijos;
    }
    
    public ExpresionNodo getValorInicial() { return valorInicial; }
}

/**
 * Para: <declaracion_sensor> ::= <identificador> "tipo" "Sensor" "=" <numero>
 * Ejemplo: sensor1 tipo Sensor = 1
 */

class DeclaracionSensorNodo extends DeclaracionNodo {
    private int puerto;
    
    public DeclaracionSensorNodo(String identificador, int puerto, int linea, int columna) {
        super(identificador, linea, columna);
        this.puerto = puerto;
    }
    
    @Override
    public String toString() {
        return "DeclaracionSensor(" + identificador + " puerto=" + puerto + ")";
    }
    
    public int getPuerto() { return puerto; }
}


// NODOS DE EXPRESIONES (Para cálculos matemáticos)


/**
 * Nodo base para expresiones matemáticas
 * Según gramática: <expresion> ::= <termino> | <expresion> "+" <termino> | <expresion> "-" <termino>
 */
abstract class ExpresionNodo extends NodoAST {
    public ExpresionNodo(int linea, int columna) {
        super(linea, columna);
    }
    
    // Método para evaluar la expresión (útil para análisis semántico)
    public abstract double evaluar() throws Exception;
}

/**
 * Para expresiones aritméticas de N términos con mismo nivel de precedencia
 * Ejemplos: a + b + c - d, x * y / z * w
 */
class ExpresionAritmeticaNodo extends ExpresionNodo {
    private List<ExpresionNodo> operandos;
    private List<String> operadores;
    
    public ExpresionAritmeticaNodo(int linea, int columna) {
        super(linea, columna);
        this.operandos = new ArrayList<>();
        this.operadores = new ArrayList<>();
    }
    
    // Constructor para el caso simple (binario)
    public ExpresionAritmeticaNodo(ExpresionNodo izquierda, String operador, 
                                  ExpresionNodo derecha, int linea, int columna) {
        this(linea, columna);
        this.operandos.add(izquierda);
        this.operandos.add(derecha);
        this.operadores.add(operador);
    }
    
    public void agregarOperando(ExpresionNodo operando) {
        operandos.add(operando);
    }
    
    public void agregarOperador(String operador) {
        operadores.add(operador);
    }
    
    @Override
    public String toString() {
        if (operandos.isEmpty()) return "ExpresionVacia";
        if (operandos.size() == 1) return operandos.get(0).toString();
        
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < operandos.size(); i++) {
            sb.append(operandos.get(i));
            if (i < operadores.size()) {
                sb.append(" ").append(operadores.get(i)).append(" ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public List<NodoAST> getHijos() {
        return new ArrayList<>(operandos);
    }
    
    @Override
    public double evaluar() throws Exception {
        if (operandos.isEmpty()) {
            throw new Exception("Expresión vacía");
        }
        
        if (operandos.size() == 1) {
            return operandos.get(0).evaluar();
        }
        
        // Evaluar de izquierda a derecha (asociatividad izquierda)
        double resultado = operandos.get(0).evaluar();
        
        for (int i = 0; i < operadores.size(); i++) {
            double operando = operandos.get(i + 1).evaluar();
            String operador = operadores.get(i);
            
            switch (operador) {
                case "+": resultado += operando; break;
                case "-": resultado -= operando; break;
                case "*": resultado *= operando; break;
                case "/": 
                    if (operando == 0) throw new Exception("División por cero");
                    resultado /= operando; 
                    break;
                default: throw new Exception("Operador desconocido: " + operador);
            }
        }
        
        return resultado;
    }
    
    public List<ExpresionNodo> getOperandos() { return operandos; }
    public List<String> getOperadores() { return operadores; }
    public int getNumeroTerminos() { return operandos.size(); }
}

/**
 * Para números literales: 5, 3.14, etc.
 */
class NumeroNodo extends ExpresionNodo {
    private double valor;
    
    public NumeroNodo(double valor, int linea, int columna) {
        super(linea, columna);
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        // Mostrar enteros sin decimales
        if (valor == (int)valor) {
            return String.valueOf((int)valor);
        }
        return String.valueOf(valor);
    }
    
    @Override
    public double evaluar() {
        return valor;
    }
    
    public double getValor() { return valor; }
}

/**
 * Para referencias a variables: x, contador, etc.
 */
class IdentificadorNodo extends ExpresionNodo {
    private String nombre;
    
    public IdentificadorNodo(String nombre, int linea, int columna) {
        super(linea, columna);
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
    
    @Override
    public double evaluar() throws Exception {
        // En el futuro, esto consultará la tabla de símbolos
        throw new Exception("No se puede evaluar variable '" + nombre + "' sin tabla de símbolos");
    }
    
    public String getNombre() { return nombre; }
}

/**
 * Para: <leer_sensor> ::= "leer_sensor" <identificador>
 * Ejemplo: leer_sensor sensor1
 */
class LeerSensorNodo extends ExpresionNodo {
    private String nombreSensor;
    
    public LeerSensorNodo(String nombreSensor, int linea, int columna) {
        super(linea, columna);
        this.nombreSensor = nombreSensor;
    }
    
    @Override
    public String toString() {
        return "leer_sensor(" + nombreSensor + ")";
    }
    
    @Override
    public double evaluar() throws Exception {
        // En el futuro, esto leerá el sensor real
        throw new Exception("No se puede evaluar sensor '" + nombreSensor + "' sin hardware");
    }
    
    public String getNombreSensor() { return nombreSensor; }
}


// NODOS DE ESTRUCTURAS DE CONTROL


/**
 * Para: <bloque_si> ::= "si" <condicion> "entonces" <instrucciones> "fin si"
 * Ejemplo: si x > 5 entonces mover_adelante 10 fin si
 */
class SiNodo extends NodoAST {
    private CondicionNodo condicion;
    private List<NodoAST> instrucciones;
    
    public SiNodo(CondicionNodo condicion, int linea, int columna) {
        super(linea, columna);
        this.condicion = condicion;
        this.instrucciones = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Si(" + condicion + ")";
    }
    
    @Override
    public List<NodoAST> getHijos() {
        List<NodoAST> hijos = new ArrayList<>();
        hijos.add(condicion);
        hijos.addAll(instrucciones);
        return hijos;
    }
    
    public CondicionNodo getCondicion() { return condicion; }
    public List<NodoAST> getInstrucciones() { return instrucciones; }
    public void agregarInstruccion(NodoAST instruccion) { instrucciones.add(instruccion); }
}

/**
 * Para: <bloque_para> ::= "para" <identificador> "=" <expresion> "hasta" <expresion> "hacer" <instrucciones> "fin para"
 * Ejemplo: para i = 1 hasta 10 hacer mover_adelante 5 fin para
 */
class ParaNodo extends NodoAST {
    private String variable;
    private ExpresionNodo inicio;
    private ExpresionNodo fin;
    private List<NodoAST> instrucciones;
    
    public ParaNodo(String variable, ExpresionNodo inicio, ExpresionNodo fin, int linea, int columna) {
        super(linea, columna);
        this.variable = variable;
        this.inicio = inicio;
        this.fin = fin;
        this.instrucciones = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Para(" + variable + " = " + inicio + " hasta " + fin + ")";
    }
    
    @Override
    public List<NodoAST> getHijos() {
        List<NodoAST> hijos = new ArrayList<>();
        hijos.add(inicio);
        hijos.add(fin);
        hijos.addAll(instrucciones);
        return hijos;
    }
    
    public String getVariable() { return variable; }
    public ExpresionNodo getInicio() { return inicio; }
    public ExpresionNodo getFin() { return fin; }
    public List<NodoAST> getInstrucciones() { return instrucciones; }
    public void agregarInstruccion(NodoAST instruccion) { instrucciones.add(instruccion); }
}

/**
 * Para: <bloque_mientras> ::= "mientras" <condicion> "hacer" <instrucciones> "fin mientras"
 * Ejemplo: mientras x < 10 hacer x = x + 1 fin mientras
 */
class MientrasNodo extends NodoAST {
    private CondicionNodo condicion;
    private List<NodoAST> instrucciones;
    
    public MientrasNodo(CondicionNodo condicion, int linea, int columna) {
        super(linea, columna);
        this.condicion = condicion;
        this.instrucciones = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Mientras(" + condicion + ")";
    }
    
    @Override
    public List<NodoAST> getHijos() {
        List<NodoAST> hijos = new ArrayList<>();
        hijos.add(condicion);
        hijos.addAll(instrucciones);
        return hijos;
    }
    
    public CondicionNodo getCondicion() { return condicion; }
    public List<NodoAST> getInstrucciones() { return instrucciones; }
    public void agregarInstruccion(NodoAST instruccion) { instrucciones.add(instruccion); }
}

/**
 * Para: <condicion> ::= <expresion> <operador_relacional> <expresion>
 * Ejemplo: x > 5, contador <= 10
 */
class CondicionNodo extends NodoAST {
    private ExpresionNodo izquierda;
    private String operador; // >, <, >=, <=, ==, !=
    private ExpresionNodo derecha;
    
    public CondicionNodo(ExpresionNodo izquierda, String operador, 
                        ExpresionNodo derecha, int linea, int columna) {
        super(linea, columna);
        this.izquierda = izquierda;
        this.operador = operador;
        this.derecha = derecha;
    }
    
    @Override
    public String toString() {
        return izquierda + " " + operador + " " + derecha;
    }
    
    @Override
    public List<NodoAST> getHijos() {
        List<NodoAST> hijos = new ArrayList<>();
        hijos.add(izquierda);
        hijos.add(derecha);
        return hijos;
    }
    
    public ExpresionNodo getIzquierda() { return izquierda; }
    public String getOperador() { return operador; }
    public ExpresionNodo getDerecha() { return derecha; }
}


// NODOS DE COMANDOS


/**
 * Para: <comando_movimiento> ::= ("mover_adelante" | "mover_atras" | "girar_izquierda" | "girar_derecha") <expresion>
 * Ejemplo: mover_adelante 10, girar_derecha x+5
 */
class ComandoMovimientoNodo extends NodoAST {
    private String comando; // mover_adelante, mover_atras, girar_izquierda, girar_derecha
    private ExpresionNodo parametro;
    
    public ComandoMovimientoNodo(String comando, ExpresionNodo parametro, int linea, int columna) {
        super(linea, columna);
        this.comando = comando;
        this.parametro = parametro;
    }
    
    @Override
    public String toString() {
        return comando + "(" + parametro + ")";
    }
    
    @Override
    public List<NodoAST> getHijos() {
        List<NodoAST> hijos = new ArrayList<>();
        hijos.add(parametro);
        return hijos;
    }
    
    public String getComando() { return comando; }
    public ExpresionNodo getParametro() { return parametro; }
}

/**
 * Para: <comando_actuador> ::= ("encender_led" | "apagar_led") <identificador>
 * Ejemplo: encender_led rojo, apagar_led verde
 */
class ComandoActuadorNodo extends NodoAST {
    private String comando; // encender_led, apagar_led
    private String color;
    
    public ComandoActuadorNodo(String comando, String color, int linea, int columna) {
        super(linea, columna);
        this.comando = comando;
        this.color = color;
    }
    
    @Override
    public String toString() {
        return comando + "(" + color + ")";
    }
    
    public String getComando() { return comando; }
    public String getColor() { return color; }
}

/**
 * Para: <comando_tiempo> ::= "esperar" <expresion> <unidad_tiempo>
 * Ejemplo: esperar 5 segundos, esperar x*2 milisegundos
 */
class ComandoTiempoNodo extends NodoAST {
    private ExpresionNodo duracion;
    private String unidad; // segundos, milisegundos
    
    public ComandoTiempoNodo(ExpresionNodo duracion, String unidad, int linea, int columna) {
        super(linea, columna);
        this.duracion = duracion;
        this.unidad = unidad;
    }
    
    @Override
    public String toString() {
        return "esperar(" + duracion + " " + unidad + ")";
    }
    
    @Override
    public List<NodoAST> getHijos() {
        List<NodoAST> hijos = new ArrayList<>();
        hijos.add(duracion);
        return hijos;
    }
    
    public ExpresionNodo getDuracion() { return duracion; }
    public String getUnidad() { return unidad; }
}

/**
 * Para: <asignacion> ::= <identificador> "=" <expresion>
 * Ejemplo: x = 5, contador = contador + 1
 */
class AsignacionNodo extends NodoAST {
    private String variable;
    private ExpresionNodo valor;
    
    public AsignacionNodo(String variable, ExpresionNodo valor, int linea, int columna) {
        super(linea, columna);
        this.variable = variable;
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        return variable + " = " + valor;
    }
    
    @Override
    public List<NodoAST> getHijos() {
        List<NodoAST> hijos = new ArrayList<>();
        hijos.add(valor);
        return hijos;
    }
    
    public String getVariable() { return variable; }
    public ExpresionNodo getValor() { return valor; }
}

/**
 * Para comandos de control de flujo: "romper", "detener"
 * Ejemplo: romper, detener
 */
class ComandoControlNodo extends NodoAST {
    private String comando; // romper, detener
    
    public ComandoControlNodo(String comando, int linea, int columna) {
        super(linea, columna);
        this.comando = comando;
    }
    
    @Override
    public String toString() {
        return comando;
    }
    
    public String getComando() { return comando; }
}