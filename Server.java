import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
    HashMap<Integer,PrintWriter> map;
    HashMap<Integer,Integer> enemyMap;
    int count=0;
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
                    tellAnother(message,sock.getPort());
                }
            } catch (Exception e) {
                if(enemyMap.containsKey(sock.getPort())){
                    tellAnother("Lose", sock.getPort());
                    map.remove(enemyMap.get(sock.getPort()));
                    enemyMap.remove(enemyMap.get(sock.getPort()));
                    --count;
                }
                if(map.containsKey(sock.getPort())){
                    map.remove(sock.getPort());
                    enemyMap.remove(sock.getPort());
                    --count;
                }
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        new Server().go();
    }

    public void go(){
        map=new HashMap<Integer,PrintWriter>();
        enemyMap=new HashMap<Integer,Integer>();
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
                ++count;
                if(count%2==0){
                    Set<Integer> set=map.keySet();
                    for(Integer i:set){
                        if(!enemyMap.keySet().contains(i)&&i!=clientSocket.getPort()){
                            enemyMap.put(i,clientSocket.getPort());
                            enemyMap.put(clientSocket.getPort(),i);
                            break;
                        }
                    }
                    tellAnother("Another Player", enemyMap.get(clientSocket.getPort()));
                    tellAnother("Another Player", clientSocket.getPort());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tellAnother(String message,int port){
        try {
            PrintWriter writer=map.get(enemyMap.get(port));
            writer.println(message);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}