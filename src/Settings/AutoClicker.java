package Settings;

import java.io.IOException;

public class AutoClicker extends Abilities {
    private static int clickers = 0;

    AutoClicker(int amountOfAbilities, int width, String image, String name, String description, int price){
        try {
            super.setSettings(amountOfAbilities, width, image, name, description, price);
            createClicker();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double createClicker() {
        clickers++;
        return clickers;
    }
}
