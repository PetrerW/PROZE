import javax.swing.*;
import java.awt.*;

/**
 * @author Daniel
 * @version 2017-04-02.
 */
public class TextPanel extends JPanel {
    private String text;
    private int  size;

    public TextPanel(String text, int size) {
        this.text = text;
        this.size = size;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        int w = this.getWidth();
        int h = this.getHeight();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.BOLD, size));
        FontMetrics fm = g.getFontMetrics();
        int fw = fm.stringWidth(text);
        int fh = fm.getAscent();
        g.drawString(text, w / 2 - fw / 2, h / 2 + fh / 2);
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }


}