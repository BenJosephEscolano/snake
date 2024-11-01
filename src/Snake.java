import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Snake {
    public Rect[] body = new Rect[100];
    public double bodyWidth, bodyHeight;

    public int size;
    public int tail = 0;
    public int head = 0;

    public Direction direction = Direction.RIGHT;

    public double ogWaitBetweenUpdates = 0.08f;
    public double waitTimeLeft = ogWaitBetweenUpdates;
    private boolean isAlive = true;

    public Snake(int size, double startX, double startY, double bodyWidth, double bodyHeight){
        this.size = size;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;

        for (int i = 0; i <= size; i++){
            Rect bodyPiece = new Rect(startX + i * bodyWidth, startY, bodyWidth, bodyHeight);
            body[i] = bodyPiece;
            head++;
        }
        head--;
    }


    public void changeDirection(Direction newDirection){
        switch (newDirection){
            case UP:
                if (direction != Direction.DOWN){
                    direction = Direction.UP;
                }
                break;
            case DOWN:
                if (direction != Direction.UP){
                    direction = Direction.DOWN;
                }
                break;
            case LEFT:
                if (direction != Direction.RIGHT){
                    direction = Direction.LEFT;
                }
                break;
            case RIGHT:
                if (direction != Direction.LEFT){
                    direction = Direction.RIGHT;
                }
                break;
        }
    }

    public void update(double dt){
        if (waitTimeLeft > 0){
            waitTimeLeft -= dt;
            return;
        }
        if (intersectingWithSelf() || isOUtOfBounds(GameScene.getForeground())){
            Window.getInstance().changeState(0);
            isAlive = false;
        }
        waitTimeLeft = ogWaitBetweenUpdates;

        if (isAlive){
            double newX = 0;
            double newY = 0;
            if (direction == Direction.RIGHT){
                newX = body[head].x + bodyWidth;
                newY = body[head].y;
            } else if (direction == Direction.LEFT){
                newX = body[head].x - bodyWidth;
                newY = body[head].y;
            } else if (direction == Direction.DOWN){
                newX = body[head].x;
                newY = body[head].y + bodyHeight;
            } else if (direction == Direction.UP){
                newX = body[head].x;
                newY = body[head].y - bodyHeight;
            }

            body[(head + 1) % body.length] = body[tail];
            body[tail] = null;

            head = (head + 1) % body.length;
            tail = (tail + 1) % body.length;

            body[head].x = newX;
            body[head].y = newY;
            //System.out.println("X: " + body[head].x + " | Y: " + body[head].y );
        }
    }

    public boolean intersectingWithSelf(){
        return intersectingWithRect(body[head]);
    }

    public boolean intersectingWithRect(Rect rect){
        for (int i = tail; i != head ; i = (i+1) % body.length){
            if (intersecting(rect, body[i])){
                return true;
            }
        }
        return false;
    }

    public void grow(){
        int newTail = (tail - 1) % body.length;
        double newX = 0, newY = 0;
        if (size > 2){
            newX = body[tail].x + (body[tail].x - body[(tail + 1) % body.length].x);
            newY = body[tail].y + (body[tail].y - body[(tail + 1) % body.length].y);
            //body[newTail] = new Rect(body[tail].x + (body[tail].x - body[(tail + 1) % body.length].x), body[tail].y + (body[tail].y - body[(tail + 1) % body.length].y), bodyWidth, bodyHeight);
        } else {
            if (direction == Direction.RIGHT){
                newX = body[head].x - bodyWidth;
                newY = body[head].y;
            } else if (direction == Direction.LEFT){
                newX = body[head].x + bodyWidth;
                newY = body[head].y;
            } else if (direction == Direction.DOWN){
                newX = body[head].x;
                newY = body[head].y - bodyHeight;
            } else if (direction == Direction.UP){
                newX = body[head].x;
                newY = body[head].y + bodyHeight;
            }
        }
        body[newTail] = new Rect(newX, newY, bodyWidth, bodyHeight);
        tail = newTail;
        size++;
    }

    public boolean intersecting(Rect r1, Rect r2){
        return (r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width &&
                r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height);
    }

    public boolean isOUtOfBounds(Rect foreground){
        return(body[head].x < foreground.x || body[head].x + body[head].width > foreground.x + foreground.width ||
                body[head].y < foreground.y || body[head].y + body[head].height > foreground.y + foreground.height);
    }

    public void draw(Graphics2D g2){
        for (int i = tail; i != head; i = (i + 1) % body.length){
            Rect piece = body[i];
            double subWidth = (piece.width - 6.0) / 2.0;
            double subHeight = (piece.height - 6.0) / 2.0;
            g2.setColor(Color.BLACK);
            g2.fill(new Rectangle2D.Double(piece.x + 2.0, piece.y + 2.0, subWidth, subHeight));
            g2.fill(new Rectangle2D.Double(piece.x + 4.0 + subWidth, piece.y + 2.0, subWidth, subHeight));
            g2.fill(new Rectangle2D.Double(piece.x + 2.0, piece.y + 4.0 + subHeight, subWidth, subHeight));
            g2.fill(new Rectangle2D.Double(piece.x + 4.0 + subWidth, piece.y + 4.0 + subHeight, subWidth, subHeight));
        }
    }
}
