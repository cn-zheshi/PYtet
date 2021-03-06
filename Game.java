import javax.swing.*;
import java.awt.event.*;

public class Game {
    JFrame frame = new JFrame("Tetris");
    JButton soloButton = new JButton("Solo");
    JButton multiplayButton = new JButton("Multiplayer");
    Tetris t=new Tetris();
    public static void main(String[] args) {
        new Game().go();
    }
    public void go(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        soloButton.addActionListener(new SoloButton());
        multiplayButton.addActionListener(new MultiButton());
        soloButton.setSize(100, 50);
        multiplayButton.setSize(100,50);
        frame.add(soloButton);
        frame.add(multiplayButton);
        frame.setLayout(null);
        soloButton.setLocation(150, 100);
        multiplayButton.setLocation(150, 200);
        frame.setSize(420, 440);
        frame.setResizable(false);
        frame.setVisible(true);
        try{
            while(frame.isEnabled()){
                //我也不知道为啥注释掉下面这行代码就不能用，但是现在是能用的
                Thread.sleep(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("play");
        t.play();
    }
    //单人游戏
    public class SoloButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            t.playMode="Single";
            frame.setVisible(false);
            frame.setEnabled(false);
        }
    }
    //多人游戏
    public class MultiButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            t.playMode="Multiplayer";
            frame.setVisible(false);
            frame.setEnabled(false);
        }
    }
    //设置按钮（以后会写的）
    public class SettingButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            //由于太菜了还没想好怎么实现，先放着吧，大致思路是写入文件再由Tetris类去读取
        }
    }
}
