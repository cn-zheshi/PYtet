import java.util.*;
import java.util.concurrent.TransferQueue;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.time.Clock;

public class Tetris {
    Board board;//面板
    ArrayList next;//next块的序列
    Blocks nowBlock;//当前块
    Blocks holdBlock;//hold块
    int x,y,index;//xy坐标，index代表方向
    int shadow_y;
    long softDropTime;//按压时间
    int dropBlockTimer;//落块计时器
    int lockBlockTimer;//锁定计时器
    boolean lose;
    int nextCount=5;//能看多少个next
    int holdCount=0;//一个块被放下前只能hold一次
    int x0=3,y0=23;//初始xy坐标
    int interval=60; 
    JFrame frame=new JFrame("Tetris");
    MoveHandler kbHandler=new MoveHandler();
    SpinHandler spHandler=new SpinHandler();
    MainPanel mainPanel;//游戏主画面，用来画board
    HoldPanel holdPanel;//hold面板，用来画hold的块
    NextPanel nextPanel;//next面板，用来画next序列
    int lockTime=1000;//落下时的锁定时间
    Tetris(){
        reset();
    }

    public class MoveHandler implements KeyListener {
        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            System.out.println(e.getKeyCode());
            if(!lose){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_A:
                        if(board.canBePutted(nowBlock, x-1, y, index)){
                            --x;
                            paintChanges();
                        }
                    break;
                    case KeyEvent.VK_D:
                        if(board.canBePutted(nowBlock, x+1, y, index)){
                            ++x;
                            paintChanges();
                        }
                    break;
                    case KeyEvent.VK_W:
                        y=shadow_y;
                        changeBlock();
                        paintChanges();
                    break;
                    case KeyEvent.VK_S:
                        if(softDropTime==0){
                            ++softDropTime;
                        }
                    break;
                    case KeyEvent.VK_R:
                        reset();
                        paintChanges();
                    break;
                    case KeyEvent.VK_SHIFT:
                        if(holdCount==0){
                            if(holdBlock==null){
                                holdBlock=nowBlock;
                                nowBlock=(Blocks)next.get(0);
                                next.remove(0);
                                if(next.size()<=nextCount){
                                    creatBlocks();
                                }
                            }
                            else{
                                Blocks tempBlock=nowBlock;
                                nowBlock=holdBlock;
                                holdBlock=tempBlock;
                            }
                            x=x0;
                            y=y0;
                            index=0;
                            ++holdCount;
                            paintChanges();
                        }
                    break;
                }
            }
            else if(e.getKeyCode()==KeyEvent.VK_R){
                reset();
                paintChanges();
            }
        }

        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            if(e.getKeyCode()==KeyEvent.VK_S){
                softDropTime=0;
            }

        }
    }
    public class SpinHandler implements KeyListener{

        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            int[][] temp;
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(nowBlock.equals(Blocks.I)){
                        temp=SRS.iBlock[index][(index+3)%4];
                    }
                    else{
                        temp=SRS.otherBlocks[index][(index+3)%4];
                    }
                    for(int i=0;i<5;++i){
                        if(board.canBePutted(nowBlock,x+temp[i][0],y+temp[i][1],(index+3)%4)){
                            index=(index+3)%4;
                            x+=temp[i][0];
                            y+=temp[i][1];
                            paintChanges();
                            break;
                        }
                    }
                break;
                case KeyEvent.VK_RIGHT:
                    if(nowBlock.equals(Blocks.I)){
                        temp=SRS.iBlock[index][(index+1)%4];
                    }
                    else{
                        temp=SRS.otherBlocks[index][(index+1)%4];
                    }
                    for(int i=0;i<5;++i){
                        if(board.canBePutted(nowBlock,x+temp[i][0],y+temp[i][1],(index+1)%4)){
                            index=(index+1)%4;
                            x+=temp[i][0];
                            y+=temp[i][1];
                            paintChanges();
                            break;
                        }
                    }
                break;
            }

        }

        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }
        
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
        setGUI();
        while(!lose){
            if(dropBlockTimer>=interval){
                dropBlockTimer=0;
                mainPart();
            }
            if(softDropTime!=0){
                ++softDropTime;
                if((softDropTime-1)%6==0&&softDropTime!=0){
                    dropBlockTimer=0;
                    mainPart();
                }
            }
            else{
                ++dropBlockTimer;
            }
            if(board.canBePutted(nowBlock, x, y+1, index)){
                lockBlockTimer=0;
            }
            else{
                ++lockBlockTimer;
            }
            try{
                Thread.sleep((long)(1000/60));
            }catch(Exception e){}
        }
    }
    //画图函数
    public void paintChanges(){
        if(!lose){
            shadow();
            mainPanel.board=board;
            mainPanel.nowBlock=nowBlock;
            mainPanel.block_x=x;
            mainPanel.block_y=y;
            mainPanel.shadow_y=shadow_y;
            mainPanel.blockIndex=index;
            holdPanel.block=holdBlock;
            nextPanel.next=next;
            mainPanel.repaint();
            nextPanel.repaint();
            holdPanel.repaint();
        }
    }
    //换成下一块
    public void changeBlock(){
        board.addBlock(nowBlock, x, y, index);
        nowBlock=(Blocks)next.get(0);
        next.remove(0);
        if(next.size()<=nextCount){
            creatBlocks();
        }
        x=x0;
        y=y0;
        holdCount=0;
        index=0;
        if((!board.canBePutted(nowBlock, x, y, index))){
            lose=true;
        }
    }
    //处理影子
    public void shadow(){
        shadow_y=y;
        while(board.canBePutted(nowBlock, x, shadow_y+1, index)){
            ++shadow_y;
        }
    }
    //主要移动逻辑
    public void mainPart(){
        if(board.canBePutted(nowBlock, x, y+1, index)){
            y++;
        }
        else{
            if(lockBlockTimer*1000/60>=lockTime){
                changeBlock();
            }
        }
        paintChanges();
    }
    //GUI
    public void setGUI(){
        mainPanel=new MainPanel(board,nowBlock,x,y,index,shadow_y);
        holdPanel=new HoldPanel(holdBlock);
        nextPanel=new NextPanel(next,nextCount);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(kbHandler);
        frame.addKeyListener(spHandler);
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
    }
    //重置游戏
    public void reset(){
        board = new Board();
        next=new ArrayList<Blocks>();
        creatBlocks();
        nowBlock=(Blocks)next.get(0);
        holdBlock=null;
        next.remove(0);
        x=x0;
        y=y0;
        index=0;
        shadow();
        softDropTime=0;
        dropBlockTimer=0;
        lockBlockTimer=0;
        lose=false;
    }

    public static void main(String[] args){
        new Tetris().play();
    }
}