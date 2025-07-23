import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class TextLineNumber extends JPanel {

    private final JEditorPane editorPane;
    private final FontMetrics fontMetrics;
    private final int padding = 5;

    public TextLineNumber(JEditorPane editorPane) {
        this.editorPane = editorPane;
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        setFont(font);
        fontMetrics = getFontMetrics(font);
        setPreferredSize(new Dimension(40, Integer.MAX_VALUE));
        setBackground(new Color(240, 240, 240));
        editorPane.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                repaint();
            }

            public void removeUpdate(DocumentEvent e) {
                repaint();
            }

            public void changedUpdate(DocumentEvent e) {
                repaint();
            }
        });
        editorPane.addCaretListener(e -> repaint());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle clip = g.getClipBounds();
        int startOffset = editorPane.viewToModel2D(new Point(0, clip.y));
        int endOffset = editorPane.viewToModel2D(new Point(0, clip.y + clip.height));
        Element root = editorPane.getDocument().getDefaultRootElement();
        int startLine = root.getElementIndex(startOffset);
        int endLine = root.getElementIndex(endOffset);
        for (int i = startLine; i <= endLine; i++) {
            String lineNumber = Integer.toString(i + 1);
            int y = getLineY(i);
            g.setColor(Color.GRAY);
            g.drawString(lineNumber, getWidth() - fontMetrics.stringWidth(lineNumber) - padding, y);
        }
    }

    private int getLineY(int line) {
        try {
            Rectangle r = editorPane.modelToView2D(editorPane.getDocument().getDefaultRootElement().getElement(line).getStartOffset()).getBounds();
            return r.y + r.height - 4;
        } catch (Exception e) {
            return 0;
        }
    }
}
