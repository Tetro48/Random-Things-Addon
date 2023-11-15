package btw.community.randomthings;

import btw.AddonHandler;
import btw.BTWAddon;

public class RandomThingsAddon extends BTWAddon {
    private static RandomThingsAddon instance;
    private RandomThingsAddon() {
        super("Random Things", "0.1.0", "Ex");
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }

    public static RandomThingsAddon getInstance() {
        if (instance == null)
            instance = new RandomThingsAddon();
        return instance;
    }
}
