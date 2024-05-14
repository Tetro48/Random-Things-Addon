package btw.random.things;

import java.util.Arrays;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import btw.AddonHandler;
import btw.BTWAddon;
import net.minecraft.src.GameSettings;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.Minecraft;
import net.minecraft.src.StatCollector;

public class RandomThingsAddon extends BTWAddon {
    private static RandomThingsAddon instance;

    public static KeyBinding third_person_key;
    public static KeyBinding first_person_key;
    public static KeyBinding backwards_facing_key;

    public static Boolean shouldShowDateTimer;
    public static Boolean shouldShowRealTimer;

    public RandomThingsAddon() {
        super();
    }

    public static RandomThingsAddon getInstance() {
        if (instance == null)
            instance = new RandomThingsAddon();
        return instance;
    }

    @Override
    public void preInitialize() {
        this.registerProperty("EnableMinecraftDateTimer", "True", "Set if the minecraft date should show up or not");
        this.registerProperty("EnableRealWorldTimer", "True", "Set if the real time timer should show up or not");
    }

    @Override
    public void handleConfigProperties(Map<String, String> propertyValues) {
        shouldShowDateTimer = Boolean.parseBoolean(propertyValues.get("EnableMinecraftDateTimer"));
        shouldShowRealTimer = Boolean.parseBoolean(propertyValues.get("EnableMinecraftDateTimer"));
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }

    public void initKeybind(){
        third_person_key = new KeyBinding(StatCollector.translateToLocal("key.randomthings.thirdperson"), Keyboard.KEY_X);
        first_person_key = new KeyBinding(StatCollector.translateToLocal("key.randomthings.firstperson"), Keyboard.KEY_Z);
        backwards_facing_key = new KeyBinding(StatCollector.translateToLocal("key.randomthings.backwardsfacing"), Keyboard.KEY_C);

        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        KeyBinding[] keyBindings = settings.keyBindings;
        keyBindings = Arrays.copyOf(keyBindings, keyBindings.length + 3);
        keyBindings[keyBindings.length - 3] = first_person_key;
        keyBindings[keyBindings.length - 2] = third_person_key;
        keyBindings[keyBindings.length - 1] = backwards_facing_key;
        settings.keyBindings = keyBindings;
    }

    /*@Override
    public void postSetup() {
        getInstance().initKeybind();
    }*/
}