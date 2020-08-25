import javax.swing.*;
import java.awt.*;
import java.util.*;

public class NextPanel extends JPanel{
    ArrayList next;
    int nextCount;
    NextPanel(ArrayList arrayList,int count){
        next=arrayList;
        nextCount=count;
    }
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,80,400);
        for(int i=0;i<nextCount;++i){
            Blocks block=(Blocks)next.get(i);
            int[][] temp=Block2Array.m.get(block)[0];
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
                        g.fillRect(20*x, 20*y+i*80, 20, 20);
                    }
                }
            }
        }
    }
}