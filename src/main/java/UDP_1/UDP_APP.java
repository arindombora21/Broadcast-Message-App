package UDP_1;

import java.io.IOException;
import java.net.*;

public class UDP_APP {
    public static void main(String[] args) throws IOException {
       //QOTD= Quote of the day
        //uses port 17(UDP)
        String hostName="djxmmx.net";
        int port=17;
        InetAddress inetAddress=InetAddress.getByName(hostName);
        DatagramSocket datagramSocket=new DatagramSocket();

        byte[] buffer=new byte[512];
        DatagramPacket dRequest=new DatagramPacket(buffer, buffer.length,inetAddress,port);
        datagramSocket.send(dRequest);// server not required because hostName is an already created server

        //response
        DatagramPacket dResponse=new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(dResponse);

        String response=new String(buffer,0, dResponse.getLength());//Constructs a new String by decoding the specified subarray of bytes using the platform's default charset.
        // The length of the new String is a function of the charset, and hence may not be equal to the length of the subarray.
        System.out.println(response);
    }
}
