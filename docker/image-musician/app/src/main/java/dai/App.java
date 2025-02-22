/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dai;

import java.util.UUID;

public class App {
    public static void main(String[] args) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();

        if (args.length == 0) {
            System.err.println("Error: you need to give the instrument name as the first argument");
            System.exit(1);
        }
        if (!Musician.soundsByInstrument.containsKey(args[0])) {
            System.err.println("Error: on the following instruments are valid: " + Musician.soundsByInstrument);
            System.exit(1);
        }

        System.out.println("Musician started " + uuid + "!");

        Musician musician = new Musician(uuid, args[0]);

        while (true) {
            Thread.sleep(1000);
            musician.play();
        }
    }
}
