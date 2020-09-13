import javax.swing.*;
import java.awt.*;

public class BorderPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 20, 400);
    }
}