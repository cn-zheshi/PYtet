import javax.swing.*;
import java.awt.*;

public class AnotherPlayerPanel  extends JPanel{
    Board board=new Board();
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
                g.fillRect(8*x, 8*(y-24), 8, 8);
            }
        }
        g.setColor(Color.BLACK);
        for(int x=0;x<10;++x){
            g.drawLine(8*x, 0, 8*x, 160);
        }
        for(int y=0;y<20;++y){
            g.drawLine(0, 8*y, 80, 8*y);
        }
    }
    public void setBoard(String str){
        String[] arr=str.split(",");
        for(int i=0;i<arr.length;++i){
            if(arr[i].length()>=10){
                for(int j=0;j<arr[i].length();++j){
                    board.board[j][i+24]=(arr[i].charAt(j)-'0');
                }
            }
        }
    }
}