package net.random.things.mixin;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import btw.community.randomthings.RandomThingsAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GameSettings;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    private GameSettings gameSettings;

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
