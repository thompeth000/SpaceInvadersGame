/**
 * Created by thompeth000 on 3/3/2017.
 */
public class Stats {
    static double collectiveDx = 1;
    static double collectiveDy = 0;
    static boolean hasCollided = false;
    static boolean isPlay, isPause, isMenu, isGameover, textFlicker;
    static int score = 0;
    static int waveNumber = 0;
    static int playerHealth = 16;
    static int playerLives = 3;


    public static double getCollectiveDx(){
        return collectiveDx;
    }

    public static void resetPlayerHealth(){
        playerHealth = 16;
    }

    public static void resetPlayerLives(){
        playerLives = 3;
    }

    public static void reverseCollectiveDx(){
        collectiveDx *= -1;

    }

    public static void decrementPlayerLives(){
        playerLives--;
    }

    public static void decrementPlayerHealth(){
        playerHealth--;
    }

    public static int getPlayerHealth(){
        return playerHealth;
    }

    public static boolean getTextFlicker(){
        return textFlicker;
    }

    public static void toggleTextFlicker(){
        if(textFlicker){
            textFlicker = false;
        }
        else
            textFlicker = true;
    }

    public static int getPlayerLives(){
        return playerLives;
    }

    public static void updateCollectiveDX(){
        collectiveDx = 1;
    }

    public static void resetCollectiveDX(){
        collectiveDx = 4;
    }

    public static boolean isIsPlay() {
        return isPlay;
    }

    public static boolean isIsPause() {
        return isPause;
    }

    public static boolean isIsMenu() {
        return isMenu;
    }

    public static boolean isIsGameover(){
        return isGameover;
    }

    public static void incrementScore(){
        score += 250;
    }

    public static void resetScore(){
        score = 0;
    }

    public static void incrementWaveNumber(){
        waveNumber++;
    }

    public static void resetWaveNumber(){
        waveNumber = 0;
    }

    public static int getWaveNumber(){
        return waveNumber;
    }

    public static void setIsPlay(){
        isPlay = true;
        isPause = false;
        isMenu = false;
        isGameover = false;
    }

    public static void setisPause(){
        isPause = true;
        isMenu = false;
        isPlay = false;
        isGameover = false;
    }

    public static void setisMenu(){
        isMenu = true;
        isPause = false;
        isPlay = false;
        isGameover = false;
    }

    public static void setIsGameover(){
        isGameover = true;
        isPause = false;
        isPlay = false;
        isMenu = false;
    }


    public static int getScore(){
        return score;
    }

    public static void setHasCollided(boolean a){
        hasCollided = a;
    }

    public static boolean isHasCollided(){
        return hasCollided;
    }
}
