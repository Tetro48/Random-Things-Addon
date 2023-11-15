package net.random.things;

import btw.community.randomthings.RandomThingsAddon;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class RandomThingsPreLaunchInitializer implements PreLaunchEntrypoint {
    /*
     * Runs the PreLaunch entrypoint to register BTW-Addon.
     * Don't initialize anything else here, use
     * the method Initialize() in the Addon.
     */
    @Override
    public void onPreLaunch() {
        RandomThingsAddon.getInstance();
    }
}
