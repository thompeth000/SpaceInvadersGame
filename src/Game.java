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
    boolean firstSpacePress = true;
    int alienWidth = 720;
    int j;
    int gameTime;
    ArrayList<Entity> entities;
    public static void main(String args[]){
        Stats.setisMenu();
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

                if(e.getKeyCode() == KeyEvent.VK_SPACE && Stats.isIsMenu() || Stats.isIsGameover()){
                    if(Stats.isIsGameover()){
                        resetGame();
                    }

                    Stats.setIsPlay();


                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    spacePressed = false;
                    firstSpacePress = false;
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
    entities.add(new Ship(Color.BLUE, getWidth()/2, getHeight() - 30, 15, 15, this, 0) );
    spawnAliens();

}



public int getGameTime(){
    return gameTime;
}

public void run(){
    timer = new Timer(1000/60, this);
    timer.start();
}

public boolean getFirstSpacePress(){
    return firstSpacePress;
}



public int getNumAliens(){
    int alienCount = 0;
    for(Entity a: entities){
        if(a instanceof Alien)
            alienCount++;
    }

    if(alienCount == 0)
        spawnAliens();

    return alienCount;
}

public void paint(Graphics g){
    super.paint(g);
    if(Stats.isIsPlay()) {
        g.setFont(new Font("Serif", Font.BOLD, 16));
        for (Entity obj : entities) {
            obj.paint(g);
        }
        g.setColor(Color.WHITE);
        printSimpleString(("Score: " + Stats.getScore()), getWidth(), -150, 20, g);
        printSimpleString(("Wave: " + Stats.getWaveNumber()), getWidth(), -50, 20, g);
        printSimpleString("Health: " + Stats.getPlayerHealth(), getWidth(), 50, 20, g);
        printSimpleString("Lives: " + Stats.getPlayerLives(), getWidth(), 150, 20, g);
    }
    else if(Stats.isIsMenu()){
        g.setColor(Color.GREEN);
        g.setFont(new Font("Lucida Console", Font.BOLD, 64));
        printSimpleString(("SPACE INVADERS"), getWidth(), 0, 200, g);
        if(Stats.getTextFlicker()){
            printSimpleString(("INSERT COIN"), getWidth(), 0, 400, g);
        }
        g.setFont(new Font("Lucida Console", Font.BOLD, 16));
        printSimpleString(("or alternatively, press space."), getWidth(), 0, 430, g);
    }
    else if(Stats.isIsGameover()){
        g.setColor(Color.GREEN);
        g.setFont(new Font("Lucida Console", Font.BOLD, 64));

            printSimpleString(("GAME OVER"), getWidth(), 0, 200, g);

            if(Stats.getTextFlicker()) {
                printSimpleString("FINAL SCORE: " + Stats.getScore(), getWidth(), 0, 400, g);
            }

        g.setFont(new Font("Lucida Console", Font.BOLD, 32));
        printSimpleString("PRESS SPACE TO PLAY AGAIN!", getWidth(), 0, 600, g);

    }
}

    @Override
    public void actionPerformed(ActionEvent e) {
    gameTime++;

    if(gameTime == Integer.MAX_VALUE)
        gameTime = 0;


if(Stats.isIsPlay()) {
    for (j = 0; j < getNextIndex(); j++) {
        entities.get(j).update(j);
    }

    if (Stats.isHasCollided()) {
        alienCollision();

    }

    getNumAliens();
}
else{
    if(gameTime % 30 == 0)
        Stats.toggleTextFlicker();
}
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
    Stats.updateCollectiveDX();
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

    public void resetGame(){
        while(entities.size() > 1) {
            removeEntity(1);
        }

        Stats.resetScore();
        Stats.resetWaveNumber();
        Stats.resetPlayerLives();
        Stats.resetPlayerHealth();

        entities.get(0).setX(getWidth() / 2);
    }

    public Rectangle getHitbox(int i){
        return entities.get(i).getBounds();
    }

    private void printSimpleString(String s, int width, int xPos, int yPos, Graphics g2d){
        int stringLen = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width / 2 - stringLen / 2;
        g2d.drawString(s, start + xPos, yPos);
    }




}
