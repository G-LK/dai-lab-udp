package dai;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static java.nio.charset.StandardCharsets.*;

public class Musician {
    final static String IPADDR = "239.255.22.5";
    final static int UDP_PORT = 9904;
    final String uuid;
    final String sound;

    // Static constant sounds list
    static final Map<String, String> soundsByInstrument = new ConcurrentHashMap<>();
    static {
        soundsByInstrument.put("piano", "ti-ta-ti");
        soundsByInstrument.put("trumpet", "pouet");
        soundsByInstrument.put("flute", "trulu");
        soundsByInstrument.put("violin", "gzi-gzi");
        soundsByInstrument.put("drum", "boum-boum");
    }

    Musician(String uuid, String instrument) {
        this.uuid = uuid;
        this.sound = soundsByInstrument.get(instrument);
    }

    record Sound(String uuid, String sound) {
    }

    void play() {
        try (DatagramSocket socket = new DatagramSocket()) {

            Sound soundEvent = new Sound(uuid, sound);

            ObjectWriter ow = new ObjectMapper().writer();
            String json = ow.writeValueAsString(soundEvent);

            byte[] payload = json.getBytes(UTF_8);
            var dest_address = new InetSocketAddress(IPADDR, UDP_PORT);
            var packet = new DatagramPacket(payload, payload.length, dest_address);
            socket.send(packet);
            System.out.println("Played " + sound + " by " + uuid);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
