import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class GameScene extends Scene{
    static Rect background, foreground;
    KL keyListener;
    Snake snake;
    Food food;

    public GameScene(KL keyListener){
        this.keyListener = keyListener;
        background = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        foreground = new Rect(24, 48, Constants.GRID * 31, Constants.GRID * 22);
        snake = new Snake(1, 48, 48 + 24, 24, 24);
        food = new Food(foreground, snake, 12, 12, Color.GREEN);
    }

    @Override
    public void update(double dt) {
        if (keyListener.isKeyPressed(KeyEvent.VK_UP) || keyListener.isKeyPressed(KeyEvent.VK_W)){
            snake.changeDirection(Direction.UP);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_DOWN) || keyListener.isKeyPressed(KeyEvent.VK_S)){
            snake.changeDirection(Direction.DOWN);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_RIGHT) || keyListener.isKeyPressed(KeyEvent.VK_D)){
            snake.changeDirection(Direction.RIGHT);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_LEFT) || keyListener.isKeyPressed(KeyEvent.VK_A)){
            snake.changeDirection(Direction.LEFT);
        }
        if (!food.isSpawned){
            food.spawn();
        }

        snake.update(dt);
        food.update(dt);

    }

    public static Rect getForeground(){
        return foreground;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Double(background.x, background.y, background.width, background.height));
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(foreground.x, foreground.y, foreground.width, foreground.height));
        snake.draw(g2);
        food.draw(g2);
    }
}
