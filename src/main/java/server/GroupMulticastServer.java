package server;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GroupMulticastServer
{
    //Group multicast IP range: 224.0.0.0 (reserved) - 239.255.255.255

    public final static String INET_ADDRESS = "234.69.69.69";
    public final static int PORT = 1234;

    public static void main(String[] args) throws UnknownHostException
    {
        String[] technologies = {"gRPC", "REST", "RabbitMQ", "SignalR", "Blazor", "WebSocket"};

        //Getting the internet address to connect
        InetAddress address = InetAddress.getByName(INET_ADDRESS);

        //Open DatagramSocket for sending data
        try (DatagramSocket serverSocket = new DatagramSocket())
        {
            for (String technology : technologies)
            {
                //Construct messsage
                int group = getRandomGroup() + 1;
                String message = String.format("Group %d is using %s, very cool", group, technology);

                //Create packet to be sent
                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket messagePacket = new DatagramPacket(messageBytes, messageBytes.length, address, PORT);

                //Send the packet
                serverSocket.send(messagePacket);

                System.out.printf("--Server has sent a new packet:\n\t%s\n", message);
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private static int getRandomGroup()
    {
        return new Random().nextInt(10);
    }
}
