package net.random.things.mixin;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import btw.random.things.RandomThingsAddon;
import btw.util.status.StatusEffect;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Material;
import net.minecraft.src.Minecraft;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.WorldClient;

@Mixin(GuiIngame.class)
public class GuiInGameMixin {
    @Shadow
    private Minecraft mc;

    private int amountRendered = 0;
    private int layers = 1;

    @Inject(method = "Lnet/minecraft/src/GuiIngame;drawPenaltyText(II)V", at = @At("TAIL"))
    private void drawTimer(int iScreenX, int iScreenY, CallbackInfo cbi){
        if(!mc.thePlayer.isDead){
            WorldClient theWorld = Minecraft.getMinecraft().theWorld;
            amountRendered = 0;
            if(RandomThingsAddon.timerAlignment == "hotbar" && (this.mc.thePlayer.isInsideOfMaterial(Material.water) || mc.thePlayer.getAir() < 300)){
                amountRendered++;
            }
            if(RandomThingsAddon.shouldShowRealTimer && RandomThingsAddon.shouldShowDateTimer) {
                layers = 2;
            }
            else {
                layers = 1;
            }
            FontRenderer fontRenderer = this.mc.fontRenderer;
            String textToShow = secToTime((int)(theWorld.getTotalWorldTime() / 20));
            int stringWidth = fontRenderer.getStringWidth(textToShow);
            ArrayList<StatusEffect> activeStatuses = mc.thePlayer.getAllActiveStatusEffects();
            
            if(RandomThingsAddon.shouldShowRealTimer){
                renderText(textToShow, stringWidth, iScreenX, iScreenY, fontRenderer, activeStatuses);
            }
            long worldTime = theWorld.getWorldTime();
            textToShow = getTimeType(theWorld) + (((int)Math.ceil(worldTime/24000))+1);
            stringWidth = fontRenderer.getStringWidth(textToShow);
            if(RandomThingsAddon.shouldShowDateTimer){
                renderText(textToShow, stringWidth, iScreenX, iScreenY, fontRenderer, activeStatuses);
            }
        }
    }

    String getTimeType(WorldClient world)
    {
        if (this.mc.thePlayer.dimension != 0) {
            long worldTime = world.getWorldTime();
            if (worldTime % 24000 < 8000 && worldTime % 24000 > 4000) {
                return "Day ";
            }
            else {
                return "§k???§r ";
            }
        }
        else if (world.isDaytime()) {
            return "Day ";
        }
        else {
            return "Night ";
        }
    }

    private void renderText(String text, int stringWidth, int iScreenX, int iScreenY, FontRenderer fontRenderer, ArrayList<StatusEffect> activeStatuses){
        ScaledResolution scaledResolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        boolean isDebugEnabled = this.mc.gameSettings.showDebugInfo;
        int initial_top_y = 2 - 10 * amountRendered + 10 * layers - 10;
        int bottom_y = scaledResolution.getScaledHeight() - 10 * amountRendered - 12;
        if (isDebugEnabled && !RandomThingsAddon.timerAlignment.equals("top"))
        {
            initial_top_y = bottom_y;
        }
        int x, y;
        switch (RandomThingsAddon.timerAlignment) {
            case "topleft" -> {
                x = 2;
                y = initial_top_y;
            }
            case "topright" -> {
                x = scaledResolution.getScaledWidth() - stringWidth - 2;
                y = initial_top_y;
            }
            case "top" -> {
                x = (scaledResolution.getScaledWidth() - stringWidth) / 2;
                y = initial_top_y;
            }
            case "bottomleft" -> {
                x = 2;
                y = bottom_y;
            }
            case "bottomright" -> {
                x = scaledResolution.getScaledWidth() - stringWidth - 2;
                y = bottom_y;
            }
            default -> {
                x = iScreenX - stringWidth;
                y = iScreenY-(10 * (activeStatuses.size()+amountRendered));
            }
        }
        fontRenderer.drawStringWithShadow(text, x, y, 0XFFFFFF);
        amountRendered++;
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
                return String.format("%d:%02d:%02d:%02d", days,hours%24, minutes, seconds);
            }
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("%d:%02d", minutes, seconds);
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
