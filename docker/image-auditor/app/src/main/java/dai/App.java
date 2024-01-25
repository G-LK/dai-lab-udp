/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dai;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println("Starting listener and reporter");

        try {
            Thread listenThread = new Thread(new Listener());
            Thread reportThread = new Thread(new Reporter());

            listenThread.start();
            reportThread.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
