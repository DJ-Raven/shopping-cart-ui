package cart;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TextPaneCenter extends JTextPane {

    public TextPaneCenter() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setFocusable(false);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setForeground(new Color(153, 153, 153));
        StyledDocument documentStyle = this.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
    }
}
