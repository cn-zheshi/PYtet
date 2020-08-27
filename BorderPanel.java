import javax.swing.*;
import java.awt.*;

public class BorderPanel extends JPanel{
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 20, 400);
    }
}