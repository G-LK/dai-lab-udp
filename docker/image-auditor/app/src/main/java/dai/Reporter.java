package dai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;

public class Reporter implements Runnable {

    final static int TCP_PORT = 2205;

    @Override
    public void run() {
        System.out.println("Started Reporter !");
        ObjectMapper mapper = new ObjectMapper();

        try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        var in = new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream(), StandardCharsets.UTF_8));
                        var out = new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream(), StandardCharsets.UTF_8))) {

                    String report = "error";
                    Listener.dropInactiveMusicians();
                    report = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Listener.musicians.elements());

                    out.write(report + "\n");
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Error on accept or buffers: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Error with serverSocket: " + e);
        }
    }
}
