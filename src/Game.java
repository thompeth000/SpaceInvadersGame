import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Created by thompeth000 on 3/1/2017.
 */
public class Game extends JPanel implements ActionListener {
    static Timer timer;
    boolean aPressed, dPressed, spacePressed;
    int alienWidth = 720;
    int j;
    ArrayList<Entity> entities;
    public static void main(String args[]){
        Game game = new Game();
        game.init();
        game.run();

    }

    public Game(){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Space Invaders!");
        setPreferredSize(new Dimension(1024,768));
        setBackground(Color.black);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
             spacePressed = true;
            }

                if(e.getKeyCode() == KeyEvent.VK_D){
                    dPressed = true;
                }

                if(e.getKeyCode() == KeyEvent.VK_A){
                    aPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    spacePressed = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_D){
                    dPressed = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_A){
                    aPressed = false;
                }

            }
        });
    }

public void init(){
    entities = new ArrayList<Entity>();
    entities.add(new Ship(Color.BLUE, getWidth()/2, getHeight() - 50, 30, 30, this, 0) );
    spawnAliens();

}

public void run(){
    timer = new Timer(1000/60, this);
    timer.start();
}

public void paint(Graphics g){
    super.paint(g);
    g.setFont(new Font("Serif", Font.BOLD, 16));
    for(Entity obj : entities){
        obj.paint(g);
    }
    g.setColor(Color.WHITE);
    printSimpleString(("Score: " + Stats.getScore()), getWidth(), 50, 20, g);
    printSimpleString(("Wave: " + Stats.getWaveNumber()), getWidth(), -50, 20, g);
}

    @Override
    public void actionPerformed(ActionEvent e) {
        entities.get(0).update(7);
        for(j = 1; j < getNextIndex(); j++){
            entities.get(j).update(j);
        }

        if(Stats.isHasCollided()) {
            alienCollision();

        }

        checkNoAliens();

        repaint();
    }

    public void addEntity(Entity ent){
        entities.add(ent);
    }

    public Entity getEntity(int index){
        return entities.get(index);
    }

    public void removeEntity(int index){
        entities.remove(index);
        for(int i = index; i < entities.size(); i++) {
            entities.get(i).decrementIndex();

        }




    }

    public int getNextIndex(){
        return entities.size();
    }

    public void decrementControlVariable(){
        j--;
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public boolean isdPressed() {
        return dPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public void spawnAliens() {
        for (int n = 0; n < 6; n++){
            for (int i = 0; i < 15; i++) {
                entities.add(new Alien(Color.RED, i * (alienWidth / 15) + ((getWidth() - alienWidth) / 2), 50 + (n * 40), 30, 30, this, getNextIndex()));
            }
    }
    Stats.incrementWaveNumber();
    }

    public void alienCollision(){
        Stats.reverseCollectiveDx();
        Stats.setHasCollided(false);
        for(int i = 1; i < entities.size(); i++) {
            if(entities.get(i) instanceof Alien){
                entities.get(i).setY(entities.get(i).getY() + 10);
            }

        }
    }

    public Rectangle getHitbox(int i){
        return entities.get(i).getBounds();
    }

    private void printSimpleString(String s, int width, int xPos, int yPos, Graphics g2d){
        int stringLen = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width / 2 - stringLen / 2;
        g2d.drawString(s, start + xPos, yPos);
    }

    public void checkNoAliens(){
        boolean alien = false;
        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i) instanceof Alien){
                alien = true;
                break;
            }
        }

        if(!alien){
            spawnAliens();
        }
    }
}
