import java.awt.*;

public class Food {
    public Rect background;
    public Snake snake;
    public int width, height;
    public Color color;
    public Rect rect;
    public int padding;
    public boolean isSpawned = false;

    public Food(Rect background, Snake snake, int width, int height, Color color){
        this.background = background;
        this.snake = snake;
        this.width = width;
        this.height = height;
        this.color = color;
        this.rect = new Rect(0,0, width, height);
        padding = (int)((Constants.GRID - this.width) / 2.0);
    }

    public void spawn(){
        do{
            double randX = (int)(Math.random() * (int) (background.width / Constants.GRID)) * Constants.GRID + background.x;
            double randY = (int)(Math.random() * (int) (background.height / Constants.GRID)) * Constants.GRID + background.y;
            this.rect.x = randX;
            this.rect.y = randY;
        }while(snake.intersectingWithRect(this.rect));
        isSpawned = true;
    }

    public void update(double dt){
        if (snake.intersectingWithRect(rect)){
            snake.grow();
            this.rect.x = -100;
            this.rect.y = -100;
            isSpawned = false;
        }
    }

    public void draw(Graphics2D g2){
        g2.setColor(color);
        g2.fillRect((int)rect.x + padding, (int)rect.y + padding, width, height);
    }
}
