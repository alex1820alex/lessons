import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JFrame;
import java.io.IOException;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main extends JComponent implements KeyListener, ActionListener {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        BufferedImage image = ImageIO.read(new File("image.png"));
        //BilinearInterpolation item = new BilinearInterpolation();
        BicubicInterpolation item = new BicubicInterpolation();
        image=item.bicubicResize(image,image.getWidth()/2,image.getHeight()/2);
        ImageIO.write(image, "png", new File("resized_image.png"));
        BufferedImage finalImage = image;
        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(finalImage, 0, 0, null);
            }
        };
        frame.setSize( image.getWidth(),image.getHeight());
        frame.add(pane);
        frame.setVisible(true);




    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}