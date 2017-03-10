import java.awt.*;

/**
 * Created by thompeth000 on 3/1/2017.
 */


public class Bullet extends Entity{
    private boolean playerBullet;
    public Bullet(Color color, int x, int y, int width, int height, double dy, double dx, Game game, int index, boolean player) {
        super(color, x, y, width, height, game, index);
        playerBullet = player;
        setDx(dx);
        setDy(dy);

    }

    public void update(int i){
    move();
    if(checkWallCollision()){
        kill(getIndex());
    }

    }

    public boolean checkWallCollision(){
        if(getY() + getDy() + getHeight() > getGame().getHeight() || getY() + getDy() < 0){
            return true;
        }
        return false;
    }

    public void checkCollisions(int i){

    }

public boolean isPlayerObject(){
        return playerBullet;
}

    public void paint(Graphics g){
        g.setColor(getColor());
        g.fillOval(getX(), getY(), getWidth(),getHeight());
    }


    }

