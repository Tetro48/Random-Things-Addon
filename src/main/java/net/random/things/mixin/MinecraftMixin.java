package net.random.things.mixin;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GameSettings;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    private GameSettings gameSettings;

    @Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 13)) //at = @At(value = "INVOKE", target = "if")
    private int redirectF5Call() {
        int actualKeyPressed = Keyboard.getEventKey();
        if(actualKeyPressed == Keyboard.KEY_Z){
            gameSettings.thirdPersonView = 0;
        }
        if(actualKeyPressed == Keyboard.KEY_X){
            gameSettings.thirdPersonView = 1;
        }
        if(actualKeyPressed == Keyboard.KEY_C){
            gameSettings.thirdPersonView = 2;
        }
        if (actualKeyPressed == Keyboard.KEY_F5){
            System.out.println("pressed f5?");
            return 0;
        }
        return actualKeyPressed;
    }
}
