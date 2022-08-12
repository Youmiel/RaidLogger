package youmiel.tmcdebuglogger.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import youmiel.tmcdebuglogger.BarterValueContainer;
import youmiel.tmcdebuglogger.TMCLoggerMod;
import youmiel.tmcdebuglogger.Util;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(method = "dropBarteredItem(Lnet/minecraft/entity/mob/PiglinEntity;Ljava/util/List;)V", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void setNoPlayerInBarter(PiglinEntity piglin, List<ItemStack> items, CallbackInfo ci){
        BarterValueContainer.resetField();
        BarterValueContainer.player = null;
    }

    @Inject(method = "dropBarteredItem(Lnet/minecraft/entity/mob/PiglinEntity;Lnet/minecraft/entity/player/PlayerEntity;Ljava/util/List;)V", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void setPlayerInBarter(PiglinEntity piglin, PlayerEntity player, List<ItemStack> items, CallbackInfo ci){
        BarterValueContainer.resetField();
        BarterValueContainer.player = player;
    }

    @Inject(method = "findGround", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void markReturnPositionFlag(PiglinEntity piglin, CallbackInfoReturnable<Vec3d> cir, Vec3d vec3d){
        BarterValueContainer.isPiglinPos = (vec3d == null);
    }

    @Inject(method = "drop", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void logDropInfo(PiglinEntity piglin, List<ItemStack> items, Vec3d pos, CallbackInfo ci){//getString()? asString()?
        StringBuilder locStringBuilder = new StringBuilder(" to ");
        if (BarterValueContainer.player != null){
            locStringBuilder.append("§9Player[").append(BarterValueContainer.player.getName().asString()).append("] §ftowards ");
        }else if (BarterValueContainer.isPiglinPos){
            locStringBuilder.append("§epiglin itself §ftowards ");
        }else{
            locStringBuilder.append("§cposition §f");
        }
        TMCLoggerMod.globalInfo("Piglin["
                + (piglin.getCustomName() == null ? String.valueOf(piglin.getId()) : piglin.getCustomName().asString())
                + "] dropped " + Util.list2Str(items) + locStringBuilder.toString() + pos.toString());
    }
}
