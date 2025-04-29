package net.random.things.mixin;

import btw.random.things.RandomThingsAddon;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(method = "damageItem", at = @At("HEAD"))
	public void playSoundOnDurabilityWaste(int par1, EntityLivingBase par2EntityLivingBase, CallbackInfo ci) {
		if (par1 > 1 && RandomThingsAddon.warnDurabilityWaste) {
			par2EntityLivingBase.playSound("btw:item.chisel.wood", 1f, 0.6f);
		}
	}
}
