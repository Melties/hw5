public class Utils {
    interface InterruptedWrapped {
        void wrap() throws InterruptedException;
    }

    static void call(InterruptedWrapped wrapped) {
        try {
            wrapped.wrap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void print(String message) {
        System.out.println(Thread.currentThread().getName() + "   " + message);
    }

}
