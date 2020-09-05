import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
    HashMap map;
    public class ClientHandler implements Runnable{
        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSocket){
            try {
                sock=clientSocket;
                InputStreamReader isReader=new InputStreamReader(sock.getInputStream());
                reader=new BufferedReader(isReader);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }

        public void run(){
            String message;
            try {
                while((message=reader.readLine())!=null){
                    System.out.println("read:"+message);
                    tellOthers(message,sock.getPort());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        new Server().go();
    }

    public void go(){
        map=new HashMap<Integer,PrintWriter>();
        try {
            ServerSocket serverSock=new ServerSocket(3000);
            System.out.println("Listening");
            while(true){
                Socket clientSocket=serverSock.accept();
                PrintWriter writer=new PrintWriter(clientSocket.getOutputStream());
                map.put(clientSocket.getPort(), writer);

                Thread t=new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");         
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tellOthers(String message,int port){
        Set<Integer> set=map.keySet();
        for(Integer i:set){
            try {
                if(i!=port){
                    PrintWriter writer=(PrintWriter)map.get(i);
                    writer.println(message);
                    writer.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}