import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements Runnable {
    private static Window window = null;
    private Boolean isRunning;
    private Scene currentScene;
    private final KL keyListener = new KL();
    private final ML mouseListener = new ML();

    private Window(int width, int height, String title){
        setSize(width,height);
        setTitle(title);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        isRunning = true;
        changeState(0);
    }

    public static Window getInstance(){
        if (Window.window == null){
            Window.window = new Window(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Constants.SCREEN_TITLE);
        }
        return Window.window;
    }

    public void close(){
        isRunning = false;
    }

    public void changeState(int newState){
        switch (newState){
            case 0:
                currentScene = new MenuScene(keyListener, mouseListener);
                break;
            case 1:
                currentScene = new GameScene(keyListener);
                break;
            default:
                System.out.println("Unknown scene");
                currentScene = null;
                break;
        }
    }

    private void update(double dt){
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        getGraphics().drawImage(dbImage, 0,0,this);
        currentScene.update(dt);
    }

    private void draw(Graphics g){
        currentScene.draw(g);
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        try {
            while(isRunning){
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;
                update(deltaTime);
            }
        } catch (Exception e){
            System.out.println("Oopses");
        }
        this.dispose();
    }
}
