import java.awt.*;

/**
 * Created by thompeth000 on 3/1/2017.
 */
public class Ship extends Entity {
    private boolean hasFired = false;
    private boolean spaceHeld = false;
    private boolean visible = true;
    private int bulletSpamTime;
    private int invulnTimer = 0;

    public Ship(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);

    }


    public void checkCollisions(int i) {
        for(int n = 0; n < getGame().getNextIndex(); n++){
            if(getGame().getHitbox(n).intersects(getBounds()) && getGame().getEntity(n) instanceof Bullet && !getGame().getEntity(n).isPlayerObject() && invulnTimer <= 0){

                getGame().getEntity(n).kill(i);
                Stats.decrementPlayerHealth();
                if(Stats.getPlayerHealth() <= 0){
                    Stats.decrementPlayerLives();
                    Stats.resetPlayerHealth();
                    setX(getGame().getWidth() / 2);
                    invulnTimer = 120;
                    if(Stats.getPlayerLives() < 0){
                        Stats.setIsGameover();
                    }
                }
            }
        }
    }

    public void paint(Graphics g){

        if(visible) {
            g.setColor(getColor());
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }


    public void update(int i) {

        if(getGame().isaPressed() && getDx() > -10){
            setDx(getDx() - 1);
        }
        else if(getGame().isdPressed() && getDx() < 10){
            setDx(getDx() + 1);
        }
        else{
            if(getDx() > 0){
                setDx(getDx() - 2);
            }
            else if(getDx() < 0){
                setDx(getDx() + 2);
            }
        }
        if(!checkWallCollision()){
            move();
        }

        if(getGame().isSpacePressed() && !hasFired && !getGame().getFirstSpacePress() && bulletSpamTime > 30 && !spaceHeld){
            getGame().addEntity(new Bullet(Color.GREEN, getX() + (getWidth()) / 2, getY(), 10, 10, -10, 0, getGame(),getGame().getNextIndex(), true));
            hasFired = true;
            bulletSpamTime = 0;
        }
        else if(!getGame().isSpacePressed() && hasFired){
            hasFired = false;

        }
        else if(getGame().isSpacePressed() && bulletSpamTime < 30){
            spaceHeld = true;
        }

        if(!getGame().isSpacePressed())
            spaceHeld = false;

        checkCollisions(i);

        bulletSpamTime++;

        if(invulnTimer > 0){
            invulnTimer--;
            toggleVisible();
        }
        else
            visible = true;

    }




    public boolean checkWallCollision(){
        if(getX() + getWidth() + getDx() >= getGame().getWidth() || getX() + getDx() <= 0){
            return true;
        }
        return false;
    }

    public boolean isPlayerObject(){
        return true;
    }

    public void toggleVisible(){
        if(visible){
            visible = false;
        }
        else
            visible = true;
    }
}
