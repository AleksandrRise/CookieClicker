package LaunchGame;

import Settings.Game;

public class Main {
    public static void main(String[] args) {
        try {
            new Game();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}