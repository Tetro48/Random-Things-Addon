package net.random.things.mixin;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import btw.util.status.StatusEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiIngame;


@Mixin(GuiIngame.class)
public class GuiInGameMixin {
    @Shadow
    private Minecraft mc;

    @Inject(method = "Lnet/minecraft/src/GuiIngame;drawPenaltyText(II)V", at = @At("TAIL"))
    private void drawTimer(int iScreenX, int iScreenY, CallbackInfo cbi){
        if(!mc.thePlayer.isDead){
            FontRenderer fontRenderer = this.mc.fontRenderer;
            String textToShow = secToTime((int)(MinecraftServer.getServer().worldServers[0].getWorldTime() / 20));
            int stringWidth = fontRenderer.getStringWidth(textToShow);
            ArrayList<StatusEffect> activeStatuses = mc.thePlayer.getAllActiveStatusEffects();
            fontRenderer.drawStringWithShadow(textToShow, iScreenX - stringWidth, iScreenY-(10 * activeStatuses.size()), 0XFFFFFF);
        }
    }

    //https://stackoverflow.com/questions/6118922/convert-seconds-value-to-hours-minutes-seconds#:~:text=hours%20%3D%20totalSecs%20%2F%203600%3B%20minutes,%2C%20hours%2C%20minutes%2C%20seconds)%3B
    String secToTime(int sec) {
        int seconds = sec % 60;
        int minutes = sec / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            minutes %= 60;
            if( hours >= 24) {
                int days = hours / 24;
                return String.format("%d days %02d:%02d:%02d", days,hours%24, minutes, seconds);
            }
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("00:%02d:%02d", minutes, seconds);
    }
    /*
     * Duration duration = Duration.ofSeconds(seconds);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long remainingSeconds = duration.toSecondsPart();
        
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    */
    /*
     * if (!mc.thePlayer.isDead) {
            ArrayList<StatusEffect> activeStatuses = mc.thePlayer.getAllActiveStatusEffects();
    
            FontRenderer fontRenderer = this.mc.fontRenderer;
    
            for (int i = 0; i < activeStatuses.size(); i++) {
                String status = StringTranslate.getInstance().translateKey(activeStatuses.get(i).getUnlocalizedName());
                
                int stringWidth = fontRenderer.getStringWidth(status);
                int offset = i * 10;
                
                fontRenderer.drawStringWithShadow(status, screenX - stringWidth, screenY - offset, 0XFFFFFF);
            }
        }
    */
}
