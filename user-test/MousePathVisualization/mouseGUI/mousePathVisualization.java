package mouseGUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class mousePathVisualization {

    Robot robot;
    JLabel label;
    GeneralPath gp;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    mousePathVisualization() throws AWTException {
        robot = new Robot();

        label = new JLabel();
        gp = new GeneralPath();
        Point p = MouseInfo.getPointerInfo().getLocation();
        gp.moveTo(p.x, p.y);
        drawLatestMouseMovement();
        ActionListener al = new ActionListener() {

            Point lastPoint;

            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                if (!p.equals(lastPoint)) {
                    gp.lineTo(p.x, p.y);
                    drawLatestMouseMovement();
                }
                lastPoint = p;
            }
        };
        Timer timer = new Timer(40, al);
        timer.start();
    }

    public void drawLatestMouseMovement() {
        BufferedImage biOrig = robot.createScreenCapture(
                new Rectangle(0, 0, d.width, d.height));
        BufferedImage small = new BufferedImage(
                biOrig.getWidth() / 4,
                biOrig.getHeight() / 4,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = small.createGraphics();
        g.scale(.25, .25);
        g.drawImage(biOrig, 0, 0, label);

        g.setStroke(new BasicStroke(8));
        g.setColor(Color.RED);
        g.draw(gp);
        g.dispose();

        label.setIcon(new ImageIcon(small));
    }

    public JComponent getUI() {
        return label;
    }

    public static void main(String[] args) throws Exception {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                JPanel ui = new JPanel(new BorderLayout(2, 2));
                ui.setBorder(new EmptyBorder(4, 4, 4, 4));

                try {
                    mousePathVisualization mmos = new mousePathVisualization();
                    ui.add(mmos.getUI());
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }

                JFrame f = new JFrame("Track Mouse On Screen");
                // quick hack to end the frame and timer
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setContentPane(ui);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}