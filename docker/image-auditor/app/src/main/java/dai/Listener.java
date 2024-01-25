package dai;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

class Sound {
    public String uuid;
    public String sound;

    public Sound() {
    }
}

public class Listener implements Runnable {
    final static String IPADDRESS = "239.255.22.5";
    final static int UDP_PORT = 9904;
    final static int BUFFER_SIZE = 100; // 100 is way above datagram length of ~66 chars
    InetSocketAddress group_address = new InetSocketAddress(IPADDRESS, UDP_PORT);
    NetworkInterface netif;

    final MulticastSocket socket;

    public Listener() throws IOException {
        socket = new MulticastSocket(UDP_PORT);
    }

    @Override
    public void run() {
        System.out.println("Started Listener");

        try (socket) {
            socket.joinGroup(group_address, netif);

            while (true) {

                byte[] buffer = new byte[BUFFER_SIZE];
                var packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0,
                        packet.getLength(), StandardCharsets.UTF_8);
                System.out.println("Received message: " + message +
                        " from " + packet.getAddress() +
                        ", port " + packet.getPort());

                ObjectMapper mapper = new ObjectMapper();
                Sound sound = mapper.readValue(message, Sound.class);

                if (Musician.musicians.containsKey(sound.uuid)) {
                    Musician.musicians.get(sound.uuid).lastActivity = System.currentTimeMillis();
                    System.out.println("update lastActivity" + sound);
                } else {
                    Musician.musicians.put(sound.uuid, new Musician(sound.uuid,
                            Musician.instrumentsBySound.get(sound.sound), System.currentTimeMillis()));
                    System.out.println("put new musician " + sound);

                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                socket.leaveGroup(group_address, netif);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

}
