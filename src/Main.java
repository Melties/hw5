import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
        private static final Integer COUNT = 20;
        private static final LinkedList<BodyPart> BUFFER = new LinkedList<>();
        private static BodyPart bodyPart;
        private static int redRobots = 0;
        private static int blueRobots = 0;
        private static List<BodyPart> redBodyParts = new ArrayList<>();
        private static List<BodyPart> blueBodyParts = new ArrayList<>();

        private static void factory() throws InterruptedException {
            synchronized (BUFFER) {
                while (true) {
                    Utils.sleep(5000);
                    bodyPart = BodyPart.randomBodyPart();
                    Utils.print("produced: " + bodyPart);
                    BUFFER.add(bodyPart);
                    if (redRobots == COUNT || blueRobots == COUNT) {
                        Utils.print("War is over.");
                        return;
                    }
                }
            }
        }

        private static void redCountry() throws InterruptedException {
            synchronized (BUFFER) {
                while (true) {
                    while (BUFFER.size() == 0) {
                        BUFFER.wait();
                    }
                    Utils.sleep(5000);
                    if(!BUFFER.isEmpty()) {
                        bodyPart = BUFFER.getLast();
                        if (!redBodyParts.contains(bodyPart)) {
                            redBodyParts.add(bodyPart);
                            BUFFER.pollLast();
                            Utils.print("Red country consumed: " + bodyPart);
                        }
                    }
                    if(redBodyParts.size() == 6) {
                        redRobots++;
                        redBodyParts.clear();
                    }
                    if (BUFFER.isEmpty()) {
                        BUFFER.notifyAll();
                    }
                    if (redRobots == COUNT && blueRobots < COUNT) {
                        Utils.print("Red country WIN");
                        return;
                    }
                }
            }
        }
        private static void blueCountry() throws InterruptedException {
            synchronized (BUFFER) {
                while (true) {
                    while (BUFFER.size() == 0) {
                        BUFFER.wait();
                    }
                    Utils.sleep(5000);
                    if(!BUFFER.isEmpty()) {
                        bodyPart = BUFFER.getLast();
                        if(!blueBodyParts.contains(bodyPart)) {
                            blueBodyParts.add(bodyPart);
                            BUFFER.pollLast();
                            Utils.print("blue country consumed: " + bodyPart);
                        }
                    }
                    if(blueBodyParts.size() == 6) {
                        blueRobots++;
                        blueBodyParts.clear();
                    }
                    if (BUFFER.isEmpty()) {
                        BUFFER.notifyAll();
                    }
                    if (blueRobots == COUNT && redRobots < COUNT) {
                        Utils.print("blue country WIN");
                        return;
                    }
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {
            Runnable factoryTask = () -> Utils.call(Main::factory);
            Runnable redCountryTask = () -> Utils.call(Main::redCountry);
            Runnable blueCountryTask = () -> Utils.call(Main::blueCountry);

            Thread factory = new Thread(factoryTask, "Factory  ");
            Thread redCountry = new Thread(redCountryTask, "Red country  ");
            Thread blueCountry = new Thread(blueCountryTask, "Blue country  ");

            factory.start();
            redCountry.start();
            blueCountry.start();

            factory.join();
            redCountry.join();
            blueCountry.join();
        }
}
