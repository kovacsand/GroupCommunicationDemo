package clients;

import server.GroupMulticastServer;

import java.io.IOException;
import java.net.*;

public class GroupMulticastClientAvengers
{
    public static void main(String[] args) throws UnknownHostException
    {
        InetAddress address = InetAddress.getByName(GroupMulticastServer.INET_ADDRESS);

        //Create buffer of bytes
        byte[] buffer = new byte[1024];

        //Create MulticastSocket
        try (MulticastSocket socket = new MulticastSocket(GroupMulticastServer.PORT))
        {
            InetSocketAddress group = new InetSocketAddress(address, GroupMulticastServer.PORT);
            NetworkInterface networkInterface = NetworkInterface.getByName("avengers07");

            //Join the group
            socket.joinGroup(group, networkInterface);

            //Receive packets
            while (true)
            {
                //Create packet with data
                DatagramPacket messagePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(messagePacket);

                //Convert message from byte[] to string
                String message = new String(messagePacket.getData(), 0, messagePacket.getLength());
                System.out.printf("++Team Avengers received:\n\t%s\n", message);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
