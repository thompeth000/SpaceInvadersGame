import java.awt.*;

/**
 * Created by thompeth000 on 3/1/2017.
 */
public class Ship extends Entity {
    private boolean hasFired = false;
    private boolean spaceHeld = false;
    private int bulletSpamTime;
    public Ship(Color color, int x, int y, int width, int height, Game game, int index){
        super(color, x, y, width, height, game, index);

    }


    public void checkCollisions(int i) {

    }

    public void paint(Graphics g){
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(),getHeight());
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

        if(getGame().isSpacePressed() && !hasFired && !getGame().getFirstSpacePress() && bulletSpamTime > 60 && !spaceHeld){
            getGame().addEntity(new Bullet(Color.GREEN, getX() + (getWidth()) / 2, getY(), 10, 10, -10, 0, getGame(),getGame().getNextIndex(), true));
            hasFired = true;
            bulletSpamTime = 0;
        }
        else if(!getGame().isSpacePressed() && hasFired){
            hasFired = false;

        }
        else if(getGame().isSpacePressed() && bulletSpamTime < 60){
            spaceHeld = true;
        }

        if(!getGame().isSpacePressed())
            spaceHeld = false;

        bulletSpamTime++;
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
}
