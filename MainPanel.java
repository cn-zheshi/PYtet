import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{
    Board board;
    Blocks nowBlock;
    int block_x,block_y;
    int blockIndex;
    int shadow_y;
    MainPanel(Board b,Blocks block,int x,int y,int index,int sy){
        board=b;
        nowBlock=block;
        block_x=x;
        block_y=y;
        blockIndex=index;
        shadow_y=sy;
    }
    public void paintComponent(Graphics g){
        for(int x=0;x<10;++x){
            for(int y=24;y<44;++y){
                int i=board.board[x][y];
                switch(i){
                    case 1:
                        g.setColor(Color.decode(ColorsOfBlocks.color_I));
                        break;
                    case 2:
                        g.setColor(Color.decode(ColorsOfBlocks.color_L));
                        break;
                    case 3:
                        g.setColor(Color.decode(ColorsOfBlocks.color_J));
                        break;
                    case 4:
                        g.setColor(Color.decode(ColorsOfBlocks.color_O));
                        break;
                    case 5:
                        g.setColor(Color.decode(ColorsOfBlocks.color_S));
                        break;
                    case 6:
                        g.setColor(Color.decode(ColorsOfBlocks.color_Z));
                        break;
                    case 7:
                        g.setColor(Color.decode(ColorsOfBlocks.color_T));
                        break;
                    case 0:
                        g.setColor(Color.BLACK);
                        break;
                }
                g.fillRect(20*x, 20*(y-24), 20, 20);
            }
        }
        int[][] temp=Block2Array.m.get(nowBlock)[blockIndex];
        g.setColor(Color.DARK_GRAY);
        for(int x=0;x<temp.length;++x){
            for(int y=0;y<temp.length;++y){
                if(temp[y][x]!=0){
                    g.fillRect(20*(x+block_x), 20*(y+shadow_y-24), 20, 20);
                }
            }
        }
        if(nowBlock.equals(Blocks.I)){
            g.setColor(Color.decode(ColorsOfBlocks.color_I));
        }
        if(nowBlock.equals(Blocks.L)){
            g.setColor(Color.decode(ColorsOfBlocks.color_L));
        }
        if(nowBlock.equals(Blocks.J)){
            g.setColor(Color.decode(ColorsOfBlocks.color_J));
        }
        if(nowBlock.equals(Blocks.O)){
            g.setColor(Color.decode(ColorsOfBlocks.color_O));
        }
        if(nowBlock.equals(Blocks.S)){
            g.setColor(Color.decode(ColorsOfBlocks.color_S));
        }
        if(nowBlock.equals(Blocks.Z)){
            g.setColor(Color.decode(ColorsOfBlocks.color_Z));
        }
        if(nowBlock.equals(Blocks.T)){
            g.setColor(Color.decode(ColorsOfBlocks.color_T));
        }
        for(int x=0;x<temp.length;++x){
            for(int y=0;y<temp.length;++y){
                if(temp[y][x]!=0){
                    g.fillRect(20*(x+block_x), 20*(y+block_y-24), 20, 20);
                }
            }
        }
        g.setColor(Color.BLACK);
        for(int x=0;x<10;++x){
            g.drawLine(20*x, 0, 20*x, 400);
        }
        for(int y=0;y<20;++y){
            g.drawLine(0, 20*y, 200, 20*y);
        }
    }
}