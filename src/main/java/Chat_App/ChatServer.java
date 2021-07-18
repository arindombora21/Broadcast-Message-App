package Chat_App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import static java.lang.System.out;

public class ChatServer {
    Vector<String>users=new Vector<String>();
    Vector<HandleClient>clients=new Vector<HandleClient>();

    public void process() throws Exception{
        ServerSocket server=new ServerSocket(9999,10);//backlog – requested maximum length of the queue of incoming connections.
        out.println("Server Started :");
        while(true){
            Socket client= server.accept();
            HandleClient c=new HandleClient(client);
            clients.add(c);
        }
    }

    public void broadcast(String user,String message){
        //send message to all users
        for(HandleClient c : clients){
            if(!c.getUserName().equals(user)){
                c.sendMessage(user,message);
            }
        }
    }

    public static void main(String[] args) throws Exception {
       new ChatServer().process();
    }

    class HandleClient extends Thread{//We use Thread because The reason is simple, we don’t want only a single client to connect to server at a particular time but many clients simultaneously.
        // We want our architecture to support multiple clients at the same time.
        String name="";
        BufferedReader input;
        PrintWriter output;
        public HandleClient(Socket client) throws IOException {
            input=new BufferedReader(new InputStreamReader(client.getInputStream()));// if client is sending
            //output if client is receiving
            output=new PrintWriter(client.getOutputStream(),true);//A boolean; if true, the println, printf, or format methods will flush the output buffer
            name= input.readLine();// give input name of server user from pw.println(userName)
            users.add(name);// adding names of users of the server at the moment
            start();//inherited from Thread and Thread starts calls run method
        }
        public void sendMessage(String name,String msg){// this and getUserName only gets called for broadcast messages
         output.println(name+"-"+msg);
        }
        public String getUserName(){
           return name;//of class
        }
        public void run(){//whenever thread starts this will run.
           String line;
           try{
               while(true){
                   line=input.readLine();
                   if(line.equals("end")){
                       clients.remove(this);
                       users.remove(name);
                       break;
                   }
                   broadcast(name,line);//this runs always for normal messages
               }
           } catch (Exception e){
              out.println(e.getMessage());
           }
        }
    }
}
