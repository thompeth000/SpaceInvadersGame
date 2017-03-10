/**
 * Created by thompeth000 on 3/8/2017.
 */
import java.awt.*;
public class BlastBullet extends Entity {

    public BlastBullet(Color color, int x, int y, int width, int height, double dy, double dx, Game game, int index) {
        super(color, x, y, width, height, game, index);

        setDx(dx);
        setDy(dy);

    }
    public void checkCollisions(){

    }

    public boolean isPlayer(){
        return true;
    }

    public void checkCollisions(int i){

    }

    public void update(int i){

    }

    public boolean isPlayerObject(){
        return true;
    }

    public boolean checkWallCollision(){
        return false;
    }

    public void paint(Graphics g){

    }


}
