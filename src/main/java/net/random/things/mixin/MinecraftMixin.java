package net.random.things.mixin;

import com.prupe.mcpatcher.Config;
import net.minecraft.src.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import btw.random.things.RandomThingsAddon;
//import net.minecraft.client.Minecraft;
import net.minecraft.src.Minecraft;
import net.minecraft.src.GameSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public GameSettings gameSettings;

    @Shadow
    private static Minecraft theMinecraft;

    @Final
    @Shadow
    public File mcDataDir;

    @Inject(method = "startGame", at = @At("HEAD"))
    private void preStartGame(CallbackInfo cbi){
        extractKeybinds();
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    private void postStartGame(CallbackInfo cbi){
        RandomThingsAddon.getInstance().initKeybind();
    }

    // this feels disgusting to yank out decompiled minecraft source just to get keybinds
    private void extractKeybinds() {
        File optionsFile = Config.getOptionsTxt(mcDataDir, "options.txt");

        try {
            if (!optionsFile.exists()) {
                return;
            }
            BufferedReader var1 = new BufferedReader(new FileReader(optionsFile));
            String var2 = "";

            while ((var2 = var1.readLine()) != null) {
                try {
                    String[] var3 = var2.split(":");
                    if (var3[0].startsWith("key_key.randomthings.")) {
                        theMinecraft.getLogAgent().logInfo("Trying option: " + var2  + " and value "+ Integer.parseInt(var3[1]));
                        RandomThingsAddon.keybindMap.put(var3[0].substring(4), Integer.parseInt(var3[1]));
                    }
                } catch (Exception var51) {
                    theMinecraft.getLogAgent().logWarning("Skipping bad option: " + var2);
                }
            }
            var1.close();
        } catch (Exception var6) {
            theMinecraft.getLogAgent().logWarning("Failed to load options");
            var6.printStackTrace();
        }
    }

    //public static boolean f5Change

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 13))
    private int redirectF5Call() {
        /*if(RandomThingsAddon.f5enabled){
            if(RandomThingsAddon.first_person_key.pressed){
                gameSettings.thirdPersonView = 0;
            }
            if(RandomThingsAddon.third_person_key.pressed){
                gameSettings.thirdPersonView = 1;
            }
            if(RandomThingsAddon.backwards_facing_key.pressed){
                gameSettings.thirdPersonView = 2;
            }
            if (actualKeyPressed == Keyboard.KEY_F5 && !RandomThingsAddon.f5Vanillaenabled){
                System.out.println("pressed f5?");
                return 0;
            }
        }
        return actualKeyPressed;*/
        int actualKeyPressed = Keyboard.getEventKey();
        if(RandomThingsAddon.first_person_key.pressed){
            gameSettings.thirdPersonView = 0;
        }
        if(RandomThingsAddon.third_person_key.pressed){
            gameSettings.thirdPersonView = 1;
        }
        if(RandomThingsAddon.backwards_facing_key.pressed){
            gameSettings.thirdPersonView = 2;
        }
        return actualKeyPressed;
    }
}
