import javax.swing.*;
import java.awt.*;

public class BorderPanel extends JPanel{
    public void paintComponent(Graphics g){
        g.setColor(Color.decode("000000"));
        g.fillRect(0, 0, 20, 400);
    }
}