package cart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class Button extends JButton {

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    private boolean hover;
    private boolean press;
    private int borderSize = 2;

    public Button() {
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setContentAreaFilled(false);
        setBackground(new Color(60, 60, 60));
        setForeground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                press = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (hover || press) {
            g2.setColor(new Color(30, 30, 30));
        } else {
            g2.setColor(getBackground());
        }
        int width = getWidth();
        int height = getHeight();
        Shape shape = new RoundRectangle2D.Double(borderSize, borderSize, width - borderSize * 2, height - borderSize * 2, 5, 5);
        g2.fill(shape);
        if (press) {
            Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, 5, 5));
            area.subtract(new Area(new RoundRectangle2D.Double(1, 1, width - 2, height - 2, 5, 5)));
            g2.fill(area);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
