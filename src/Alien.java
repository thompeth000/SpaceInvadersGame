import java.awt.*;

/**
 * Created by thompeth000 on 3/1/2017.
 */
public class Alien extends Entity {

    public Alien(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);
    }

    public void checkCollisions(int i) {
for(int n = 0; n < getGame().getNextIndex(); n++){
    if(getGame().getHitbox(n).intersects(getBounds()) && getGame().getEntity(n) instanceof Bullet && getGame().getEntity(n).isPlayerObject()){
        Stats.incrementScore();
        getGame().getEntity(n).kill(i);
        kill(i);
    }
}
    }

    public void update(int i) {
        int chanceOfFire = (int)(Math.random() * 10000);
        if(chanceOfFire > 9950){ //IT'S OVER 9000!!!!
            fireBulletAtPlayer();
        }

        setDx(Stats.getCollectiveDx());

        move();
        checkCollisions(i);
if(checkWallCollision()){
    Stats.setHasCollided(true);
}


    }



    public boolean checkWallCollision() {
        if(getX() + getWidth() + getDx() >= getGame().getWidth() || getX() + getDx() <= 0){
            return true;
        }
        return false;
    }

    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(), getY(), getWidth(),getHeight());
    }

    public void fireBulletAtPlayer(){
        getGame().addEntity(new Bullet(Color.RED, getX() + (getWidth()) / 2, getY(), 10, 10, 10 + Stats.getWaveNumber(), 0, getGame(),getGame().getNextIndex(), false));
    }

    public boolean isPlayerObject(){
        return false;
    }
}
