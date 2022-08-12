package youmiel.tmcdebuglogger.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import youmiel.tmcdebuglogger.TMCLoggerMod;

@Mixin(LookTargetUtil.class)
public class LookTargetUtilMixin {
    @Inject(method = "give", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void logDropVelocity(LivingEntity entity, ItemStack stack, Vec3d targetLocation, CallbackInfo ci, double d, ItemEntity itemEntity, float f, Vec3d vec3d){
        if (entity instanceof PiglinEntity){
            TMCLoggerMod.globalInfo("Dropped item[" + stack.toString() + "] velocity: " + itemEntity.getVelocity().toString());
        }
    }
}
