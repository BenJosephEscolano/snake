

public class Main {
    public static void main(String[] args) {
        Window window = Window.getInstance();
        Thread thread = new Thread(window);
        thread.start();
    }
}