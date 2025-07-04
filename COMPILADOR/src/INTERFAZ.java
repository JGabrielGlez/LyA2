
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class INTERFAZ extends javax.swing.JFrame {

    private File archivoActual = null;
    private UndoManager undoManager = new UndoManager();
    private int contadorClicks = 0;
    private File archivoCopiado = null;
    private File carpetaActual = null;
    private Timer resaltadoTimer;

    public INTERFAZ() {
        initComponents();
        configurarUndoRedo();
        jScrollPane4.setRowHeaderView(new TextLineNumber(JTAEditotText));
        
        JTAEditotText.getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) {
        SwingUtilities.invokeLater(() -> {
            String texto = JTAEditotText.getText();
            
            // Buscar la posición de "Terminar"
            int posicionTerminar = texto.lastIndexOf("Terminar");
            
            if (posicionTerminar != -1) {
                // Calcular donde termina la palabra "Terminar"
                int finTerminar = posicionTerminar + "Terminar".length();
                
                // Si hay texto después de "Terminar", eliminarlo
                if (texto.length() > finTerminar) {
                    JTAEditotText.setText(texto.substring(0, finTerminar));
                }
            }
        });
    }
    
    @Override
    public void removeUpdate(DocumentEvent e) {}
    
    @Override
    public void changedUpdate(DocumentEvent e) {}
});
    }
    
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPEditorTexto = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        JTAEditotText = new javax.swing.JTextPane();
        JPCarpeta = new javax.swing.JPanel();
        btnCambiarCarpeta = new javax.swing.JButton();
        JPAnalisis = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JPEditorTextAnalisis = new javax.swing.JEditorPane();
        JPConsola = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTAConsola = new javax.swing.JTextPane();
        JBMBarra = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        JCBNuevo = new javax.swing.JCheckBoxMenuItem();
        JCBAbrir = new javax.swing.JCheckBoxMenuItem();
        JCBGuardar = new javax.swing.JCheckBoxMenuItem();
        JCBAyuda = new javax.swing.JCheckBoxMenuItem();
        JMConfiguracion = new javax.swing.JMenu();
        JMCompilar = new javax.swing.JMenu();
        JMEjecutar = new javax.swing.JMenu();
        JMAnalisisLexico = new javax.swing.JMenu();
        JMSintactico = new javax.swing.JMenu();
        JMAnalisisSemantico = new javax.swing.JMenu();
        JMTraducirPrograma = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMPILADOR");

        JPEditorTexto.setBackground(new java.awt.Color(255, 255, 255));

        JTAEditotText.setBorder(javax.swing.BorderFactory.createTitledBorder("Editor de Texto"));
        JTAEditotText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JTAEditotTextKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(JTAEditotText);

        javax.swing.GroupLayout JPEditorTextoLayout = new javax.swing.GroupLayout(JPEditorTexto);
        JPEditorTexto.setLayout(JPEditorTextoLayout);
        JPEditorTextoLayout.setHorizontalGroup(
            JPEditorTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPEditorTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        JPEditorTextoLayout.setVerticalGroup(
            JPEditorTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPEditorTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addContainerGap())
        );

        JPCarpeta.setBackground(new java.awt.Color(255, 255, 255));
        JPCarpeta.setBorder(javax.swing.BorderFactory.createTitledBorder("PROGRAMAS"));
        JPCarpeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPCarpetaMouseClicked(evt);
            }
        });

        btnCambiarCarpeta.setText("Cambiar Carpeta");
        btnCambiarCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarCarpetaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPCarpetaLayout = new javax.swing.GroupLayout(JPCarpeta);
        JPCarpeta.setLayout(JPCarpetaLayout);
        JPCarpetaLayout.setHorizontalGroup(
            JPCarpetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPCarpetaLayout.createSequentialGroup()
                .addComponent(btnCambiarCarpeta)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        JPCarpetaLayout.setVerticalGroup(
            JPCarpetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPCarpetaLayout.createSequentialGroup()
                .addComponent(btnCambiarCarpeta)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        JPAnalisis.setBackground(new java.awt.Color(255, 255, 255));
        JPAnalisis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPAnalisisMouseClicked(evt);
            }
        });

        JPEditorTextAnalisis.setEditable(false);
        JPEditorTextAnalisis.setBorder(javax.swing.BorderFactory.createTitledBorder("ANALISIS"));
        JPEditorTextAnalisis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPEditorTextAnalisisMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(JPEditorTextAnalisis);

        javax.swing.GroupLayout JPAnalisisLayout = new javax.swing.GroupLayout(JPAnalisis);
        JPAnalisis.setLayout(JPAnalisisLayout);
        JPAnalisisLayout.setHorizontalGroup(
            JPAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPAnalisisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
        );
        JPAnalisisLayout.setVerticalGroup(
            JPAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPAnalisisLayout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        JPConsola.setBackground(new java.awt.Color(255, 255, 255));

        JTAConsola.setBorder(javax.swing.BorderFactory.createTitledBorder("CONSOLA"));
        JTAConsola.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTAConsolaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTAConsola);

        javax.swing.GroupLayout JPConsolaLayout = new javax.swing.GroupLayout(JPConsola);
        JPConsola.setLayout(JPConsolaLayout);
        JPConsolaLayout.setHorizontalGroup(
            JPConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPConsolaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPConsolaLayout.setVerticalGroup(
            JPConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPConsolaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("Archivo");

        JCBNuevo.setText("Nuevo");
        JCBNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(JCBNuevo);

        JCBAbrir.setText("Abrir");
        JCBAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBAbrirActionPerformed(evt);
            }
        });
        jMenu1.add(JCBAbrir);

        JCBGuardar.setText("Guardar");
        JCBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBGuardarActionPerformed(evt);
            }
        });
        jMenu1.add(JCBGuardar);

        JCBAyuda.setText("Ayuda");
        JCBAyuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JCBAyudaMouseClicked(evt);
            }
        });
        JCBAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCBAyudaActionPerformed(evt);
            }
        });
        jMenu1.add(JCBAyuda);

        JBMBarra.add(jMenu1);

        JMConfiguracion.setText("Configuracion");
        JBMBarra.add(JMConfiguracion);

        JMCompilar.setText("Compilar");
        JMCompilar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JMCompilarMouseClicked(evt);
            }
        });
        JBMBarra.add(JMCompilar);

        JMEjecutar.setText("Ejecutar");
        JMEjecutar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JMEjecutarMouseClicked(evt);
            }
        });
        JBMBarra.add(JMEjecutar);

        JMAnalisisLexico.setText("Analisis Lexico");
        JMAnalisisLexico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JMAnalisisLexicoMouseClicked(evt);
            }
        });
        JBMBarra.add(JMAnalisisLexico);

        JMSintactico.setText("Analisis Sintactico");
        JMSintactico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JMSintacticoMouseClicked(evt);
            }
        });
        JBMBarra.add(JMSintactico);

        JMAnalisisSemantico.setText("Analisis Semantico");
        JBMBarra.add(JMAnalisisSemantico);

        JMTraducirPrograma.setText("Traducir Programa");
        JBMBarra.add(JMTraducirPrograma);

        setJMenuBar(JBMBarra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JPCarpeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JPAnalisis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JPConsola, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JPEditorTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JPEditorTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JPCarpeta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JPAnalisis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JPConsola, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void JCBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBGuardarActionPerformed
        if (archivoActual == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar archivo como...");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                archivoActual = fileChooser.getSelectedFile();
                if (!archivoActual.getName().toLowerCase().endsWith(".txt")) {
                    archivoActual = new File(archivoActual.getAbsolutePath() + ".txt");
                }
                cargarArchivosEnPanel(archivoActual.getParentFile());
            } else {
                return; 
            }
        }
        try {
            Files.write(archivoActual.toPath(), JTAEditotText.getText().getBytes());
            JTAConsola.setText("Archivo guardado: " + archivoActual.getAbsolutePath());
            cargarArchivosEnPanel(archivoActual.getParentFile());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo");
        }
    }//GEN-LAST:event_JCBGuardarActionPerformed

    private void JCBAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBAyudaActionPerformed
        mostrarManual();
    }//GEN-LAST:event_JCBAyudaActionPerformed

    
    
    
    private void JCBNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBNuevoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona la carpeta para crear el nuevo archivo");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File carpeta = fileChooser.getSelectedFile();
            String nombreArchivo = JOptionPane.showInputDialog(this, "Nombre del archivo (sin .txt):");
            if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
                File nuevoArchivo = new File(carpeta, nombreArchivo + ".txt");
                try {
                    if (nuevoArchivo.createNewFile()) {
                        JTAEditotText.setText(""); 
                        archivoActual = nuevoArchivo;
                        cargarArchivosEnPanel(carpeta); 
                        JTAConsola.setText("Archivo creado: " + nuevoArchivo.getAbsolutePath());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al crear el archivo");
                }
            }
        }
    }//GEN-LAST:event_JCBNuevoActionPerformed

    private void JCBAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCBAbrirActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir archivo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            archivoActual = fileChooser.getSelectedFile();
            try {
                String contenido = new String(Files.readAllBytes(archivoActual.toPath()), StandardCharsets.UTF_8);
                JTAEditotText.setText(contenido);
                cargarArchivosEnPanel(archivoActual.getParentFile()); 
                JTAConsola.setText("Archivo cargado: " + archivoActual.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo");
            }
        }
    }//GEN-LAST:event_JCBAbrirActionPerformed

    private void JPEditorTextAnalisisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPEditorTextAnalisisMouseClicked
        mostrarContenidoAnalisis();
    }//GEN-LAST:event_JPEditorTextAnalisisMouseClicked

    private void JPAnalisisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPAnalisisMouseClicked
        mostrarContenidoAnalisis();
    }//GEN-LAST:event_JPAnalisisMouseClicked

    private void JPCarpetaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPCarpetaMouseClicked
        if (javax.swing.SwingUtilities.isRightMouseButton(evt)) {
            mostrarMenuPegarEnPanel(evt);
        }
    }//GEN-LAST:event_JPCarpetaMouseClicked

    private void btnCambiarCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarCarpetaActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una carpeta");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File carpetaSeleccionada = fileChooser.getSelectedFile();
            cargarArchivosEnPanel(carpetaSeleccionada); 
            archivoActual = null; 
            JTAEditotText.setText(""); 
        }
    }//GEN-LAST:event_btnCambiarCarpetaActionPerformed

    private void JTAConsolaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTAConsolaMouseClicked
        contadorClicks++;
        if (contadorClicks == 2) {
            mostrarContenidoConsola();
            contadorClicks = 0; 
        }
        Timer timer = new Timer(1000, e -> contadorClicks = 0);
        timer.setRepeats(false);
        timer.start();
    }//GEN-LAST:event_JTAConsolaMouseClicked

    private void JTAEditotTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTAEditotTextKeyReleased
        int codigo = evt.getKeyCode();
        if (codigo == KeyEvent.VK_SPACE || codigo == KeyEvent.VK_ENTER) {
        }
    }//GEN-LAST:event_JTAEditotTextKeyReleased

    private void JMAnalisisLexicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JMAnalisisLexicoMouseClicked
        String codigo = JTAEditotText.getText();
        List<Analizador.Token> tokens = Analizador.analizarLexico(codigo);
        StringBuilder resultado = new StringBuilder();
        int lineaActual = -1;
        for (Analizador.Token token : tokens) {
            if (token.linea != lineaActual) {
                lineaActual = token.linea;
                resultado.append("\n[Línea ").append(lineaActual).append("]\n");
            }
            resultado.append(token.tipo)
                    .append(": ")
                    .append(token.lexema)
                    .append(" (col ").append(token.columna).append(")\n");
        }
        JPEditorTextAnalisis.setText(resultado.toString());
        
    }//GEN-LAST:event_JMAnalisisLexicoMouseClicked

    private void JMSintacticoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JMSintacticoMouseClicked
        String codigo = JTAEditotText.getText();
        List<Analizador.Token> tokens = Analizador.analizarLexico(codigo);
        StringBuilder errores = new StringBuilder();
        boolean correcto = Analizador.analizarSintaxis(tokens, errores);
        if (correcto) {
            StringBuilder resultado = new StringBuilder();
            resultado.append("✅ Análisis sintáctico correcto.\n");
            resultado.append("Expresiones reconocidas por línea:\n");
            resultado.append(Analizador.obtenerPatronesPorLinea(tokens));
            JPEditorTextAnalisis.setText(resultado.toString());
            
           String simbolos = Analizador.getTablaSimbolosTexto();
            
            //mostrar la tabla de símbolos
            StringBuilder contenido = new StringBuilder();
            contenido.append("Tabla de símbolos generada:");
            
            if(simbolos.isEmpty()){
                contenido.append("No se encontraron símbolos");
            }
            
                   JTAConsola.setText(contenido.toString());
                  // System.out.println(Analizador.analizarSintaxis(tokens, errores));
                   
    } else {
            JPEditorTextAnalisis.setText("❌ Se encontraron errores en la sintaxis:\n" + errores.toString());
        }
    }//GEN-LAST:event_JMSintacticoMouseClicked

    private void JCBAyudaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JCBAyudaMouseClicked
        mostrarManual();
    }//GEN-LAST:event_JCBAyudaMouseClicked

    private void JMCompilarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JMCompilarMouseClicked
        String codigo = JTAEditotText.getText();
        Analizador compilador = new Analizador();
        
        List<Analizador.Token> tokens = compilador.analizarLexico(codigo);
        //Añadir otra validacion para la tabla de símbolos
    
        StringBuilder errores = new StringBuilder();
        
        boolean esValido = compilador.analizarSintaxis(tokens, errores);
        
        
        
        if (esValido) {
            JTAConsola.setText("✅ Compilación exitosa. No se encontraron errores.\nGuardando archivo...");
            if (archivoActual != null) {
                try (FileWriter writer = new FileWriter(archivoActual)) {
                    writer.write(codigo);
                    JTAConsola.setText(JTAConsola.getText() + "\nArchivo guardado exitosamente en: " + archivoActual.getAbsolutePath());
                } catch (IOException e) {
                    JTAConsola.setText(JTAConsola.getText() + "\nError al guardar el archivo: " + e.getMessage());
                }
            } else {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar archivo compilado");
                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    archivoActual = fileChooser.getSelectedFile();
                    try (FileWriter writer = new FileWriter(archivoActual)) {
                        writer.write(codigo);
                        JTAConsola.setText(JTAConsola.getText() + "\nArchivo guardado exitosamente en: " + archivoActual.getAbsolutePath());
                        cargarArchivosEnPanel(archivoActual.getParentFile());
                    } catch (IOException e) {
                        JTAConsola.setText(JTAConsola.getText() + "\nError al guardar el archivo: " + e.getMessage());
                    }
                } else {
                    JTAConsola.setText(JTAConsola.getText() + "\nCancelado por el usuario.");
                }
            }
        } else {
            String[] lineas = codigo.split("\n");
            String[] mensajes = errores.toString().split("\n");
            StringBuilder salida = new StringBuilder("❌ Se encontraron errores en la compilación:\n\n");
            for (String mensaje : mensajes) {
                Matcher matcher = Pattern.compile("en línea (\\d+)").matcher(mensaje);
                if (matcher.find()) {
                    int numLinea = Integer.parseInt(matcher.group(1));
                    String lineaCodigo = (numLinea <= lineas.length) ? lineas[numLinea - 1].trim() : "";
                    salida.append("L").append(numLinea).append(" | ").append(lineaCodigo).append("\n");
                    salida.append("    → ").append(mensaje).append("\n\n");
                } else {
                    salida.append(mensaje).append("\n");
                }
            }
            JTAConsola.setText(salida.toString());
        }
        StringBuilder resultado = new StringBuilder();
        int lineaActual = -1;
        resultado.append("Análisis léxico.\n");
        resultado.append("-------------------------------------------------------------\n");

        for (Analizador.Token token : tokens) {
            if (token.linea != lineaActual) {
                lineaActual = token.linea;
                
                resultado.append("\n[Línea ").append(lineaActual).append("]\n");
            }
            resultado.append(token.tipo)
                    .append(": ")
                    .append(token.lexema)
                    .append(" (col ").append(token.columna).append(")\n");
        }
        JPEditorTextAnalisis.setText(resultado.toString());
        if (esValido) {
            resultado.append("\n");
            resultado.append("\n");
            resultado.append("\n");
            resultado.append("-------------------------------------------------------------\n");
            resultado.append("\n");
            resultado.append("\n");
            resultado.append("✅ Análisis sintáctico correcto.\n");
            resultado.append("Expresiones reconocidas por línea:\n");
            resultado.append(Analizador.obtenerPatronesPorLinea(tokens));
           // JPEditorTextAnalisis.setText(resultado.toString());
            
            
            resultado.append("Tabla de símbolos resultannte\n");
            resultado.append("-------------------------------------------------------------\n");
            resultado.append(mostrarTablaSimbolos(codigo));
            
            //aquí muestra el árbol
            ProgramaNodo arbol = Analizador.parsearAST(codigo, errores);
            resultado.append("\n");
            resultado.append("Generación del árbol sintáctico.\n");
            resultado.append("-------------------------------------------------------------\n");

            resultado.append(arbol.mostrarArbol()+"\n");
           
            
            
            JPEditorTextAnalisis.setText(resultado.toString());

            
            
            
            
            System.out.println(arbol.mostrarArbol());
        } else {
            JPEditorTextAnalisis.setText("❌ Se encontraron errores en la sintaxis:\n" + errores.toString());
        }
        
        
        
        //falta poner la tabla de símbolos
    }//GEN-LAST:event_JMCompilarMouseClicked

    
    public static String mostrarTablaSimbolos(String codigo) {
    // Analizar el código para generar tokens
    List<Analizador.Token> tokens = Analizador.analizarLexico(codigo);
    
    // Obtener tabla de símbolos completa
    String tablaCompleta = Analizador.getTablaSimbolosTexto();
    
    StringBuilder resultado = new StringBuilder();
    resultado.append(tablaCompleta).append("\n\n");
    
    return resultado.toString();
}
    
    
    
    private void JMEjecutarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JMEjecutarMouseClicked
        String codigo = JTAEditotText.getText();
        List<Analizador.Token> tokens = Analizador.analizarLexico(codigo);
        StringBuilder errores = new StringBuilder();
        boolean esValido = Analizador.analizarSintaxis(tokens, errores);
        if (!esValido) {
            JTAConsola.setText("❌ No se puede ejecutar. Corrige los errores primero.\n" + errores);
            return;
        }
        List<String> instrucciones = new ArrayList<>();
        String[] lineas = codigo.split("\\n");
        for (String linea : lineas) {
            String limpia = linea.trim();
            if (!limpia.isEmpty() && !limpia.equals("iniciar") && !limpia.equals("fin")) {
                instrucciones.add(limpia);
            }
        }
        JDialog animDialog = new JDialog(this, "Simulación de Robot", true);
        animDialog.setSize(800, 600);
        animDialog.setLocationRelativeTo(this);
        PanelAnimacionRobot panelAnim = new PanelAnimacionRobot(tokens);
        animDialog.getContentPane().add(panelAnim);
        animDialog.setVisible(true);
    }//GEN-LAST:event_JMEjecutarMouseClicked

    private void mostrarManual() {
        String manual = """
        #📘 MANUAL DE MINI-ROBOT-CODE
        
        ## ▸ ESTRUCTURA BÁSICA:
        Todo programa debe iniciar con la palabra clave 'iniciar' y finalizar con 'Terminar' (con T mayúscula).
        Estas indican el inicio y el fin del código del robot.
        
        **Gramática:** `<programa> ::= "iniciar" <declaraciones> <instrucciones> "Terminar"`
        
        **Ejemplo:**
        
        iniciar
            ...declaraciones...
            ...instrucciones...
        Terminar
        
        
        ## ▸ DECLARACIONES DE VARIABLES:
        Antes de usar variables, deben declararse especificando su tipo.
        
        Variables numéricas:
        
        <identificador> tipo numero = <expresion>
        
        
        Sensores:
        
        <identificador> tipo Sensor = <puerto>
        
        
        Gramática:
        <declaracion_numero> ::= <identificador> "tipo" "numero" "=" <expresion>
        <declaracion_sensor> ::= <identificador> "tipo" "Sensor" "=" <numero>
        
        Ejemplos:
        
        velocidad tipo numero = 10
        contador tipo numero = 5 + 3
        sensor1 tipo Sensor = 1
        sensor_distancia tipo Sensor = 2
        
        
        ## ▸ EXPRESIONES ARITMÉTICAS:
        Puedes realizar cálculos matemáticos con precedencia de operadores.
        
        Operadores: +, -, *, /  
        Precedencia: () > *, / > +, -
        
        Gramática:
        <expresion> ::= <termino> | <expresion> ("+" | "-") <termino>
        <termino> ::= <factor> | <termino> ("*" | "/") <factor>
        <factor> ::= <numero> | <identificador> | "(" <expresion> ")" | <leer_sensor>
        
        Ejemplos:
        
        resultado tipo numero = (5 + 3) * 2
        distancia tipo numero = velocidad / 2
        calculo tipo numero = leer_sensor sensor1 + 10
        
        
        ## ▸ MOVIMIENTOS:
        Comandos que mueven o rotan al robot. Aceptan expresiones como parámetros.
        
        Comandos disponibles:
        mover_adelante <expresion>` → Avanza hacia adelante
        mover_atras <expresion>` → Retrocede  
        girar_izquierda <expresion>` → Gira a la izquierda (grados)
        girar_derecha <expresion>` → Gira a la derecha (grados)
        
        Gramática:
        <comando_movimiento> ::= ("mover_adelante" | "mover_atras" | "girar_izquierda" | "girar_derecha") <expresion>
        
        Ejemplos:
        
        mover_adelante 10
        mover_adelante velocidad
        girar_derecha velocidad * 2
        mover_atras leer_sensor sensor1
        
        
        ## ▸ ACTUADORES:
        Controlan salidas como LEDs. Requieren un identificador de color.
        
        Comandos disponibles:
        encender_led <identificador>` → Enciende el LED del color indicado
        apagar_led <identificador>` → Apaga el LED del color indicado
        
        Gramática:
       <comando_actuador> ::= ("encender_led" | "apagar_led") <identificador>
        
        Colores válidos: rojo, verde, azul, amarillo, blanco
        
        Ejemplos:
        
        encender_led rojo
        apagar_led verde
        encender_led amarillo
        
        
        ## ▸ TIEMPO:
        El comando 'esperar' pausa temporalmente el robot.
        
        Sintaxis: 
        esperar <expresion> <unidad>`
        <expresion>: cantidad de tiempo (puede ser un cálculo)
        <unidad>: segundos | milisegundos
        
        Gramática: 
        <comando_tiempo> ::= "esperar" <expresion> <unidad_tiempo>
        
        Ejemplos:
        
        esperar 2 segundos
        esperar 500 milisegundos
        esperar velocidad * 100 milisegundos
        esperar leer_sensor sensor1 segundos
        
        
        ## ▸ SENSORES:
        Leen valores del hardware del robot para usarse en cálculos y condiciones.
        
        Sintaxis: leer_sensor <identificador>
        
        Gramática: 
        <leer_sensor> ::= "leer_sensor" <identificador>
        
        Ejemplo:
        
        sensor_frontal tipo Sensor = 1
        si leer_sensor sensor_frontal > 20 entonces
            mover_adelante 5
        fin si
        
        
        ## ▸ ASIGNACIONES:
        Modifican el valor de variables ya declaradas.
        
        Sintaxis: `identificador> = <expresion>
        
        Gramática: 
        <asignacion> ::= <identificador> "=" <expresion>
        
        Ejemplos:
        
        contador = contador + 1
        velocidad = leer_sensor sensor1 * 2
        resultado = (a + b) / 2
        
        
        ## ▸ ESTRUCTURAS DE CONTROL:
        
        ### Condicionales (si-entonces):
        Ejecuta código solo si se cumple una condición.
        
        Sintaxis:
        
        si <condicion> entonces
            <instrucciones>
        fin si
        
        
        Gramática: <bloque_si> ::= "si" <condicion> "entonces" <instrucciones> "fin si"
        
        Operadores relacionales: <, >, <=, >=, ==, !=
        
        Gramática de condición: <condicion> ::= <expresion> <operador_relacional> <expresion>
        
        ### Bucles para:
        Repite código un número determinado de veces.
        
        Sintaxis:
        
        para <variable> = <inicio> hasta <fin> hacer
            <instrucciones>
        fin para
        
        
        Gramática: 
        <bloque_para> ::= "para" <identificador> "=" <expresion> "hasta" <expresion> "hacer" <instrucciones> "fin para"
        
        ### Bucles mientras:
        Repite código mientras se cumpla una condición.
        
        Sintaxis:
        
        mientras <condicion> hacer
            <instrucciones>
        fin mientras  
        
        
        Gramática: <bloque_mientras> ::= "mientras" <condicion> "hacer" <instrucciones> "fin mientras"
        
        Ejemplos de estructuras:
        
        si velocidad > 10 entonces
            encender_led verde
            mover_adelante velocidad / 2
        fin si
        
        para i = 1 hasta 5 hacer
            girar_derecha 72
            mover_adelante 10
        fin para
        
        mientras leer_sensor sensor1 < 50 hacer
            mover_adelante 5
            esperar 100 milisegundos
        fin mientras
        
        
        ## ▸ COMANDOS DE CONTROL DE FLUJO:
        romper → Sale del bucle actual
        detener → Detiene TODAS las operaciones
        
        Gramática: <comando_control> ::= "romper" | "detener"
        
        ## ▸ COMENTARIOS:
        Agregan documentación al código sin afectar la ejecución.
        
        Sintaxis: // comentario o # comentario
        
        Ejemplos
                         
        // Este es un comentario de línea
        velocidad tipo numero = 10  // Comentario al final de línea
        # También puedes usar numeral
        
        
        ## ▸ REGLAS IMPORTANTES:
        - Usa siempre 'iniciar' al principio y 'Terminar' al final del programa
        - Declara todas las variables antes de usarlas
        - Cada instrucción debe ir en una línea separada
        - Cierra correctamente todos los bloques: 'fin si', 'fin para', 'fin mientras'
        - Los nombres de variables deben empezar con letra o guión bajo
        - Las expresiones pueden anidarse con paréntesis para cambiar precedencia
        
        ## ▸ EJEMPLO COMPLETO:
        ```
        iniciar
            // Declaraciones
            velocidad tipo numero = 15
            sensor_frontal tipo Sensor = 1
            contador tipo numero = 0
            
            // Bucle principal
            para i = 1 hasta 3 hacer
                contador = contador + 1
                si leer_sensor sensor_frontal > 20 entonces
                    encender_led verde
                    mover_adelante velocidad
                    esperar 1 segundos
                fin si
                girar_derecha 120
            fin para
            
            // Finalización
            apagar_led verde
            detener
        Terminar
        
        
        ## ▸ GRAMÁTICA COMPLETA DEL LENGUAJE:
        
        <programa> ::= "iniciar" <declaraciones> <instrucciones> "Terminar"
        
        <declaraciones> ::= <declaracion> <declaraciones> | ε
        <declaracion> ::= <declaracion_numero> | <declaracion_sensor>
        <declaracion_numero> ::= <identificador> "tipo" "numero" "=" <expresion>
        <declaracion_sensor> ::= <identificador> "tipo" "Sensor" "=" <numero>
        
        <instrucciones> ::= <instruccion> <instrucciones> | ε
        <instruccion> ::= <comando_movimiento> | <comando_actuador> | <comando_tiempo> | 
                          <estructura_control> | <asignacion> | <comando_control>
        
        <comando_movimiento> ::= ("mover_adelante" | "mover_atras" | "girar_izquierda" | "girar_derecha") <expresion>
        <comando_actuador> ::= ("encender_led" | "apagar_led") <identificador>
        <comando_tiempo> ::= "esperar" <expresion> <unidad_tiempo>
        <comando_control> ::= "romper" | "detener"
        
        <estructura_control> ::= <bloque_si> | <bloque_para> | <bloque_mientras>
        <bloque_si> ::= "si" <condicion> "entonces" <instrucciones> "fin si"
        <bloque_para> ::= "para" <identificador> "=" <expresion> "hasta" <expresion> "hacer" <instrucciones> "fin para"
        <bloque_mientras> ::= "mientras" <condicion> "hacer" <instrucciones> "fin mientras"
        
        <condicion> ::= <expresion> <operador_relacional> <expresion>
        <operador_relacional> ::= "<" | ">" | "<=" | ">=" | "==" | "!="
        
        <asignacion> ::= <identificador> "=" <expresion>
        
        <expresion> ::= <termino> | <expresion> ("+" | "-") <termino>
        <termino> ::= <factor> | <termino> ("*" | "/") <factor>
        <factor> ::= <numero> | <identificador> | "(" <expresion> ")" | <leer_sensor>
        
        <leer_sensor> ::= "leer_sensor" <identificador>
        <unidad_tiempo> ::= "segundos" | "milisegundos"
        <numero> ::= [0-9]+(.[0-9]+)?                
        <identificador> ::= [a-zA-Z_][a-zA-Z_0-9]*
       
        
      
        
        Revisa tu código antes de ejecutar. Errores comunes:
        - Olvidar declarar variables antes de usarlas
        - No cerrar bloques con 'fin si', 'fin para', o 'fin mientras'  
        - Usar 'fin' en lugar de 'Terminar' al final del programa
        - Comandos sin parámetros o parámetros incorrectos
        - Paréntesis desbalanceados en expresiones
        
        Para soporte técnico: +52 334 410 54 27
        """;

        JTextArea area = new JTextArea(manual);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);
        JDialog dialogo = new JDialog(this, "Guía del Lenguaje MINI-ROBOT-CODE", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(this);
        dialogo.add(scroll);
        dialogo.setVisible(true);
    }

    private void cargarArchivosEnPanel(File carpeta) {
        JPCarpeta.removeAll();
        JPCarpeta.setLayout(new BoxLayout(JPCarpeta, BoxLayout.Y_AXIS));
        JPCarpeta.add(btnCambiarCarpeta);
        JPCarpeta.add(Box.createVerticalStrut(2));
        carpetaActual = carpeta;
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".txt"));
        if (archivos != null) {
            for (File archivo : archivos) {
                JLabel label = new JLabel(archivo.getName());
                label.setOpaque(true);
                label.setBackground(archivo.equals(archivoActual) ? Color.BLUE : Color.WHITE);
                label.setForeground(archivo.equals(archivoActual) ? Color.WHITE : Color.BLACK);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if (javax.swing.SwingUtilities.isRightMouseButton(evt)) {
                            mostrarMenuContextual(evt, archivo);
                        } else if (javax.swing.SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) {
                            try {
                                archivoActual = archivo;
                                String contenido = new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);
                                JTAEditotText.setText(contenido);
                                cargarArchivosEnPanel(carpeta); 
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Error al abrir el archivo");
                            }
                        }
                    }
                });
                JPCarpeta.add(label);
            }
        }
        JPCarpeta.revalidate();
        JPCarpeta.repaint();
    }

    private void configurarUndoRedo() {
        JTAEditotText.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        JTAEditotText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_Z) {
                    try {
                        if (undoManager.canUndo()) {
                            undoManager.undo();
                        }
                    } catch (CannotUndoException ex) {
                        System.out.println("No se puede deshacer: " + ex);
                    }
                }
            }
        });
    }

    private void mostrarContenidoAnalisis() {
        String contenido = JPEditorTextAnalisis.getText();
        JEditorPane area = new JEditorPane();
        area.setContentType("text/plain"); 
        area.setText(contenido);
        area.setEditable(false);
        area.setCaretPosition(0);  
        JScrollPane scroll = new JScrollPane(area);
        JDialog dialogo = new JDialog(this, "Vista del Panel de Análisis", true);
        dialogo.setSize(1100, 700);
        dialogo.setLocationRelativeTo(this);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.getContentPane().add(scroll);
        dialogo.setVisible(true);
    }

    private void mostrarContenidoConsola() {
        String contenido = JTAConsola.getText();
        JEditorPane area = new JEditorPane();
        area.setContentType("text/plain"); 
        area.setText(contenido);
        area.setEditable(false);
        area.setCaretPosition(0);  
        JScrollPane scroll = new JScrollPane(area);
        JDialog dialogo = new JDialog(this, "Vista del Panel de Análisis", true);
        dialogo.setSize(1100, 700);
        dialogo.setLocationRelativeTo(this);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.getContentPane().add(scroll);
        dialogo.setVisible(true);
    }

    private void mostrarMenuContextual(MouseEvent evt, File archivo) {
        javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem copiar = new javax.swing.JMenuItem("Copiar");
        copiar.addActionListener(e -> archivoCopiado = archivo);
        javax.swing.JMenuItem pegar = new javax.swing.JMenuItem("Pegar");
        pegar.addActionListener(e -> {
            if (archivoCopiado != null && archivoCopiado.exists()) {
                try {
                    File destino = new File(archivo.getParent(), archivoCopiado.getName());
                    if (destino.exists()) {
                        JOptionPane.showMessageDialog(this, "Ya existe un archivo con ese nombre");
                        return;
                    }
                    Files.copy(archivoCopiado.toPath(), destino.toPath());
                    cargarArchivosEnPanel(archivo.getParentFile());
                    JTAConsola.setText("Archivo pegado en: " + destino.getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al pegar el archivo");
                }
            }
        });
        javax.swing.JMenuItem duplicar = new javax.swing.JMenuItem("Duplicar");
        duplicar.addActionListener(e -> {
            try {
                File dir = archivo.getParentFile();
                String baseName = archivo.getName().replaceFirst("[.][^.]+$", "");
                String extension = archivo.getName().substring(archivo.getName().lastIndexOf('.'));
                int contador = 1;
                File nuevoArchivo;
                do {
                    nuevoArchivo = new File(dir, baseName + " -Copia" + (contador > 1 ? " (" + contador + ")" : "") + extension);
                    contador++;
                } while (nuevoArchivo.exists());
                Files.copy(archivo.toPath(), nuevoArchivo.toPath());
                cargarArchivosEnPanel(dir);
                JTAConsola.setText("Archivo duplicado como: " + nuevoArchivo.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al duplicar el archivo");
            }
        });
        javax.swing.JMenuItem propiedades = new javax.swing.JMenuItem("Propiedades");
        propiedades.addActionListener(e -> {
            String info = "Nombre: " + archivo.getName() + "\n"
                    + "Ruta: " + archivo.getAbsolutePath() + "\n"
                    + "Tamaño: " + archivo.length() + " bytes\n"
                    + "Última modificación: " + new java.util.Date(archivo.lastModified());
            JOptionPane.showMessageDialog(this, info, "Propiedades del archivo", JOptionPane.INFORMATION_MESSAGE);
        });
        javax.swing.JMenuItem eliminar = new javax.swing.JMenuItem("Eliminar");
        eliminar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas eliminar el archivo?\n" + archivo.getName(),
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (archivo.delete()) {
                    cargarArchivosEnPanel(archivo.getParentFile());
                    JTAConsola.setText("Archivo eliminado: " + archivo.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el archivo");
                }
            }
        });
        menu.add(copiar);
        menu.add(pegar);
        menu.add(duplicar);
        menu.add(eliminar);
        menu.addSeparator(); 
        menu.add(propiedades);
        menu.show(evt.getComponent(), evt.getX(), evt.getY());
    }

    private void mostrarMenuPegarEnPanel(MouseEvent evt) {
        if (archivoCopiado != null && carpetaActual != null && carpetaActual.isDirectory()) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem pegar = new JMenuItem("Pegar");
            pegar.addActionListener(e -> {
                try {
                    File destino = new File(carpetaActual, archivoCopiado.getName());
                    if (destino.exists()) {
                        String nombre = archivoCopiado.getName();
                        String base = nombre.replaceFirst("[.][^.]+$", "");
                        String ext = nombre.substring(nombre.lastIndexOf('.'));
                        int contador = 1;
                        File nuevo;
                        do {
                            nuevo = new File(carpetaActual, base + " -Copia" + (contador > 1 ? " (" + contador + ")" : "") + ext);
                            contador++;
                        } while (nuevo.exists());
                        destino = nuevo;
                    }
                    Files.copy(archivoCopiado.toPath(), destino.toPath());
                    cargarArchivosEnPanel(carpetaActual);
                    JTAConsola.setText("Archivo pegado: " + destino.getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al pegar el archivo");
                }
            });
            menu.add(pegar);
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(INTERFAZ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INTERFAZ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INTERFAZ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INTERFAZ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new INTERFAZ().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar JBMBarra;
    private javax.swing.JCheckBoxMenuItem JCBAbrir;
    private javax.swing.JCheckBoxMenuItem JCBAyuda;
    private javax.swing.JCheckBoxMenuItem JCBGuardar;
    private javax.swing.JCheckBoxMenuItem JCBNuevo;
    private javax.swing.JMenu JMAnalisisLexico;
    private javax.swing.JMenu JMAnalisisSemantico;
    private javax.swing.JMenu JMCompilar;
    private javax.swing.JMenu JMConfiguracion;
    private javax.swing.JMenu JMEjecutar;
    private javax.swing.JMenu JMSintactico;
    private javax.swing.JMenu JMTraducirPrograma;
    private javax.swing.JPanel JPAnalisis;
    private javax.swing.JPanel JPCarpeta;
    private javax.swing.JPanel JPConsola;
    private javax.swing.JEditorPane JPEditorTextAnalisis;
    private javax.swing.JPanel JPEditorTexto;
    private javax.swing.JTextPane JTAConsola;
    private javax.swing.JTextPane JTAEditotText;
    private javax.swing.JButton btnCambiarCarpeta;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
