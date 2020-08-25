import java.util.*;
import java.util.concurrent.TransferQueue;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

public class Tetris {
    Board board;
    ArrayList next;
    Blocks nowBlock;
    Blocks holdBlock;
    int x,y,index;
    int nextCount=5;
    int holdCount=0;
    int x0=3,y0=23;
    Double speed=((double)1)/60; 
    MainPanel mainPanel;
    HoldPanel holdPanel;
    NextPanel nextPanel;
    Tetris(){
        board = new Board();
        next=new ArrayList<Blocks>();
        creatBlocks();
        nowBlock=(Blocks)next.get(0);
        holdBlock=null;
        next.remove(0);
        x=3;
        y=23;
        index=0;
    }

    public class KeyboardHandler implements KeyListener {
        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            System.out.println(e.getKeyCode());
            switch(e.getKeyCode()){
                case KeyEvent.VK_A:
                    break;
                case KeyEvent.VK_D:
                    break;
                case KeyEvent.VK_W:
                    break;
                case KeyEvent.VK_S:
                    break;
                case KeyEvent.VK_SHIFT:
                    if(holdBlock==null){
                        holdBlock=nowBlock;
                        nowBlock=(Blocks)next.get(0);
                        next.remove(0);
                    }
                    else{
                        Blocks tempBlock=nowBlock;
                        nowBlock=holdBlock;
                        holdBlock=tempBlock;
                    }
                    x=x0;
                    y=y0;
                    mainPanel.board=board;
                    mainPanel.nowBlock=nowBlock;
                    mainPanel.block_x=x;
                    mainPanel.block_y=y;
                    holdPanel.block=holdBlock;
                    nextPanel.next=next;
                    mainPanel.repaint();
                    nextPanel.repaint();
                    holdPanel.repaint();
                    break;
                case KeyEvent.VK_LEFT:
                    break;
                case KeyEvent.VK_RIGHT:
                    break;
            }

        }

        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }
    }
    public static void main(String[] args){
        new Tetris().play();
    }
    //生成7bag的块
    public void creatBlocks(){
        Blocks[] bag={Blocks.I,Blocks.L,Blocks.J,Blocks.O,Blocks.S,Blocks.Z,Blocks.T};
        Random random=new Random();
        while(true){
            int r=random.nextInt(7);
            if(bag[r]!=null){
                next.add(bag[r]);
                bag[r]=null;
            }
            for(r=0;r<7;++r){
                if(bag[r]!=null){
                    break;
                }
            }
            if(r==7){
                break;
            }
        }
    }
    //开始游戏
    public void play(){
        //TODO
        JFrame frame=new JFrame("Tetris");
        mainPanel=new MainPanel(board,nowBlock,x,y,index);
        holdPanel=new HoldPanel(holdBlock);
        nextPanel=new NextPanel(next,nextCount);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyboardHandler());
        mainPanel.setSize(200, 400);
        holdPanel.setSize(80,80);
        nextPanel.setSize(80,400);
        JPanel borderPanel0=new BorderPanel();
        borderPanel0.setSize(20, 400);
        JPanel borderPanel1=new BorderPanel();
        borderPanel1.setSize(20, 400);
        frame.add(holdPanel);
        frame.add(mainPanel);
        frame.add(nextPanel);
        frame.add(borderPanel0);
        frame.add(borderPanel1);
        frame.setLayout(null);
        mainPanel.setLocation(100,0);
        holdPanel.setLocation(0,0);
        nextPanel.setLocation(320,0);
        borderPanel0.setLocation(80, 0);
        borderPanel1.setLocation(300,0);
        frame.setSize(420, 440);
        frame.setResizable(false);
        frame.setVisible(true);
        while(true){
            try{
                int sleepTime=(int)(1000*60*speed);
                Thread.currentThread().sleep(sleepTime);
            }catch(Exception e){}
            if(board.canBePutted(nowBlock, x, y+1, index)){
                y++;
            }
            else{
                board.addBlock(nowBlock, x, y, index);
                nowBlock=(Blocks)next.get(0);
                next.remove(0);
                if(next.size()<=5)
                creatBlocks();
                x=x0;
                y=y0;
                if(!board.canBePutted(nowBlock, x, y, index)){
                    break;
                }
            }
            mainPanel.board=board;
            mainPanel.nowBlock=nowBlock;
            mainPanel.block_x=x;
            mainPanel.block_y=y;
            holdPanel.block=holdBlock;
            nextPanel.next=next;
            mainPanel.repaint();
            nextPanel.repaint();
            holdPanel.repaint();
        }
    }
}