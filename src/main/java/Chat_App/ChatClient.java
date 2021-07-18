package Chat_App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import static java.lang.System.out;

public class ChatClient extends JFrame implements ActionListener {
    String userName;
    PrintWriter pw;
    BufferedReader br;
    JTextArea ta_msg;
    JTextField tfInput;
    JButton btnSend,btnExit;
    Socket client;

    public ChatClient(String userName,String serverName) throws IOException {
        super(userName);//name displayed in frame
        this.userName=userName;
        client =new Socket(serverName,9999);//both clients are connected to the same socket this is server request
        br=new BufferedReader(new InputStreamReader(client.getInputStream()));
        pw=new PrintWriter(client.getOutputStream(),true);// just a stream is created contains nothing
        pw.println(userName);// this username will be retrieved by input.readLine() method in server
        buildInterface();
        new MessageThread().start();
    }
    public void buildInterface(){
        btnSend=new JButton("Send");
        btnExit=new JButton("Exit");
        ta_msg=new JTextArea();
        ta_msg.setRows(10);
        ta_msg.setColumns(50);
        ta_msg.setEditable(false);
        tfInput=new JTextField(50);
        JScrollPane sp= new JScrollPane(ta_msg,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel bp=new JPanel(new FlowLayout());
        bp.add(tfInput);
        bp.add(btnSend);
        bp.add(btnExit);
        add(bp,"South");
        btnSend.addActionListener(this);
        btnExit.addActionListener(this);
        setSize(550,300);
        setVisible(true);
        pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==btnExit){
           pw.println("end");
           System.exit(0);
       }else{
           pw.println(tfInput.getText());
           ta_msg.append(userName+"-"+tfInput.getText()+"\n");
           tfInput.setText("");
       }
    }

    public static void main(String[] args) {
        String name=JOptionPane.showInputDialog(null,"Enter your name:","Username",JOptionPane.PLAIN_MESSAGE);
        String server="localhost";
        try{
           new ChatClient(name,server);

        }catch(Exception e){
           out.println("Error:"+e.getMessage());
        }
    }
    class MessageThread extends Thread{
        public void run(){
            String line;
            try{
                while(true){
                    line=br.readLine();// constantly checks if smthng is there in the input stream or not....evrytime client presses send input stream
                    //is filled by the other users output stream...since thread is always running
                    ta_msg.append(line+"\n");
                }
            }catch(Exception e){
               e.printStackTrace();
            }
        }
    }
}
