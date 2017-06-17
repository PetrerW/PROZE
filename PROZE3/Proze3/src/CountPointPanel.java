/*
 * 
 */
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

// TODO: Auto-generated Javadoc
/**
 * Created by Daniel on 2017-04-02.
 */
public class CountPointPanel extends JPanel {
    
    /** The points. */
    private int points;
    
    /** The Constant numberCount. */
    private static final int numberCount = 6;
    
    /** The format. */
    private DecimalFormat format;

    /** The size. */
    private int size;

    /**
     * Instantiates a new count point panel.
     *
     * @param size the size
     */
    public CountPointPanel( int size) {

        this.size = size;
        this.format = new DecimalFormat("######");
        this.format.setMaximumIntegerDigits(numberCount);
        this.format.setMinimumIntegerDigits(numberCount);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        String string = format.format(points);
        int w = this.getWidth();
        int h = this.getHeight();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.BOLD, size));
        FontMetrics fm = g.getFontMetrics();
        int fw = fm.stringWidth(string);
        int fh = fm.getAscent();
        g.drawString(string, w / 2 - fw / 2, h / 2 + fh / 2);
    }

    /**
     * Sets the score.
     *
     * @param score the new score
     */
    public void setScore(int score) {
        points = score;
        repaint();
    }
}
