import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class Client {
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;
    AnotherPlayerPanel anotherPlayerPanel;
    ArrayList<Integer> rubbishLines;

    Client(AnotherPlayerPanel panel,ArrayList<Integer> list){
        anotherPlayerPanel=panel;
        rubbishLines=list;
    }
    public void go(){
        setUpNetworking();
        Thread readerThread=new Thread(new IncomingReader());
        readerThread.start();
    }

    public void setUpNetworking(){
        try{
            sock=new Socket("127.0.0.1", 3000);
            InputStreamReader streamReader=new InputStreamReader(sock.getInputStream());
            reader=new BufferedReader(streamReader);
            writer=new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public class IncomingReader implements Runnable{
        public void run(){
            String message;
            try {
                while((message=reader.readLine())!=null){
                    anotherPlayerPanel.setBoard(message);
                    anotherPlayerPanel.repaint();
                    int l=message.length();
                    int rubblisLine=(message.charAt(l-2)-'0')*10+message.charAt(l-1)-'0';
                    if(rubblisLine!=0){
                        rubbishLines.add(rubblisLine);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}