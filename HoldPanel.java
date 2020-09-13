import javax.swing.*;
import java.awt.*;

public class HoldPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    Blocks block;
    HoldPanel(Blocks b){
        block=b;
    }
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,80,80);
        int[][] temp;
        if(block!=null){
            temp=Block2Array.m.get(block)[0];
        }
        else{
            return;
        }
        if(block.equals(Blocks.I)){
            g.setColor(Color.decode(ColorsOfBlocks.color_I));
        }
        if(block.equals(Blocks.L)){
            g.setColor(Color.decode(ColorsOfBlocks.color_L));
        }
        if(block.equals(Blocks.J)){
            g.setColor(Color.decode(ColorsOfBlocks.color_J));
        }
        if(block.equals(Blocks.O)){
            g.setColor(Color.decode(ColorsOfBlocks.color_O));
        }
        if(block.equals(Blocks.S)){
            g.setColor(Color.decode(ColorsOfBlocks.color_S));
        }
        if(block.equals(Blocks.Z)){
            g.setColor(Color.decode(ColorsOfBlocks.color_Z));
        }
        if(block.equals(Blocks.T)){
            g.setColor(Color.decode(ColorsOfBlocks.color_T));
        }
        for(int x=0;x<temp.length;++x){
            for(int y=0;y<temp.length;++y){
                if(temp[y][x]!=0){
                    g.fillRect(20*x, 20*y, 20, 20);
                }
            }
        }
    }
}