package cart;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Cart extends JComponent {

    private Animator animator;
    private float animate;
    private boolean show;
    private CartLayer cartLayer;
    private ModelCart model = new ModelCart("Title",
            "0.00$",
            "Description",
            new ImageIcon(getClass().getResource("/cart/p1.png")));

    public Cart() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        cartLayer = new CartLayer();
        add(new JLayer(cartLayer, new CartLayerUI()));
        initAnimator();
    }

    private void initAnimator() {
        animator = new Animator(300, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (show) {
                    animate = fraction;
                } else {
                    animate = 1f - fraction;
                }
                repaint();
            }
        });
        animator.setResolution(0);
    }

    private void start(boolean show) {
        if (animator.isRunning()) {
            float f = animator.getTimingFraction();
            animator.stop();
            animator.setStartFraction(1f - f);
            animator.setStartDelay(0);
        } else {
            animator.setStartFraction(0f);
            if (show) {
                animator.setStartDelay(200);
            } else {
                animator.setStartDelay(0);
            }
        }
        this.show = show;
        animator.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
        g2.dispose();
        super.paintComponent(g);
    }

    private class CartLayerUI extends LayerUI<CartLayer> {

        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            if (c instanceof JLayer) {
                ((JLayer) c).setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
            }
        }

        @Override
        public void uninstallUI(JComponent jc) {
            if (jc instanceof JLayer) {
                ((JLayer) jc).setLayerEventMask(0);
            }
            super.uninstallUI(jc);
        }

        @Override
        public void paint(Graphics grphcs, JComponent jc) {
            super.paint(grphcs, jc);
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int width = getWidth();
            int height = getHeight();
            g2.setColor(getBackground());
            float alpha = 1f - animate;
            if (alpha > 1) {
                alpha = 1;
            } else if (alpha < 0) {
                alpha = 0;
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(new RoundRectangle2D.Double(0, 0, width, height, 10, 10));
            int size = Math.min(width, height);
            Dimension rec = getAutoSize(getModel().getImage(), (int) (size * 0.8f));
            int x = (width - rec.width) / 2;
            int y = (height - rec.height) / 2;
            float rotate = animate * 90f;
            g2.rotate(Math.toRadians(rotate), width / 2f, height / 2f);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2.drawImage(toImage(getModel().getImage()), (int) (x - (animate * 100f)), y, rec.width, rec.height, null);
            g2.dispose();
        }

        @Override
        protected void processMouseEvent(MouseEvent e, JLayer<? extends CartLayer> l) {
            Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), l.getView());
            if (l.getView().contains(point)) {
                if (!show) {
                    start(true);
                }
            } else {
                if (show) {
                    start(false);
                }
            }
        }

        private Dimension getAutoSize(Icon image, int size) {
            int w = size;
            int h = size;
            int iw = image.getIconWidth();
            int ih = image.getIconHeight();
            double xScale = (double) w / iw;
            double yScale = (double) h / ih;
            double scale = Math.min(xScale, yScale);
            int width = (int) (scale * iw);
            int height = (int) (scale * ih);
            if (width < 1) {
                width = 1;
            }
            if (height < 1) {
                height = 1;
            }
            return new Dimension(width, height);
        }

        private Image toImage(Icon icon) {
            return ((ImageIcon) icon).getImage();
        }
    }

    public ModelCart getModel() {
        return model;
    }

    public void setModel(ModelCart model) {
        this.model = model;
        cartLayer.setModel(model);
    }
}
