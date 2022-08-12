package youmiel.tmcdebuglogger.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.village.raid.Raid;
import youmiel.tmcdebuglogger.LoggerValueContainer;
import youmiel.tmcdebuglogger.TMCLoggerMod;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Raid.class)
public class RaidMixin {

    @Inject(method = "preCalculateRavagerSpawnLocation(I)Ljava/util/Optional;", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void logPreCalcInfo(int proximity, CallbackInfoReturnable<Optional<BlockPos>> cir) {
        Raid targetRaid = (Raid) (Object) this;
        RaidAccessor targetRaidAccessor = (RaidAccessor) (Raid) (Object) this;
        TMCLoggerMod.info("[" + targetRaid.getRaidId() + ", " + (0 - targetRaidAccessor.getPreRaidTicks()) + "] "
                         + "preCalculateRavagerSpawnLocation(proximity = " + proximity + ")");
        TMCLoggerMod.increaseIndent();
    }

    @Inject(method = "preCalculateRavagerSpawnLocation(I)Ljava/util/Optional;", at = @At("RETURN"))
    private void decreasePrecalcIndent(CallbackInfoReturnable<Optional<BlockPos>> cir) {
        TMCLoggerMod.decreaseIndent();
    }

    // @ModifyVariable(method = "preCalculateRavagerSpawnLocation(I)Ljava/util/Optional;", at = @At("INVOKE"), ordinal = 1)
    // private int storePreCalcCounter(int i) {
    //     LoggerValueContainer.INT_MAP.put("counter", i);
    //     return i;
    // }

    @Inject(method = "preCalculateRavagerSpawnLocation(I)Ljava/util/Optional;", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/Raid;getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void logLocationCall(int proximity, CallbackInfoReturnable<Optional<BlockPos>> cir, int i) {
        // int i = LoggerValueContainer.INT_MAP.get("counter");
        TMCLoggerMod.info("[i = " + i + "] getRavagerSpawnLocation(proximity = " + proximity + ", tries = 1)");
    }

    // @ModifyVariable(method = "getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;", at = @At("HEAD"), ordinal = 0)
    // private int storeSpawnLocationProximity(int proximity) {
    //     LoggerValueContainer.INT_MAP.put("proximity", proximity);
    //     return proximity;
    // }

    // @ModifyVariable(method = "getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;", at = @At("HEAD"), ordinal = 1)
    // private int storeSpawnLocationTries(int tries) {
    //     LoggerValueContainer.INT_MAP.put("tries", tries);
    //     return tries;
    // }

    // @ModifyVariable(method = "getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;", at = @At("STORE"), ordinal = 2)
    // private int logSpawnLocationMultiplier(int radiusMultiplier) {
    //     int proximity = LoggerValueContainer.INT_MAP.get("proximity");
    //     int tries = LoggerValueContainer.INT_MAP.get("tries");
    //     Raid targetRaid = (Raid) (Object) this;
    //     RaidAccessor targetRaidAccessor = (RaidAccessor) (Raid) (Object) this;
    //     RaidLoggerMod.info("[" + targetRaid.getRaidId() + ", " + targetRaidAccessor.getTicksActive() + "] "
    //                      + "getRavagerSpawnLocation(proximity = " + proximity + ", tries = " + tries + ") "
    //                      + "radiusMultiplier = " + radiusMultiplier);
    //     RaidLoggerMod.increaseIndent();
    //     return radiusMultiplier;
    // }

    @Inject(method = "getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void logSpawnLocationCall(int proximity, int tries, CallbackInfoReturnable<BlockPos> cir){
        Raid targetRaid = (Raid) (Object) this;
        RaidAccessor targetRaidAccessor = (RaidAccessor) (Raid) (Object) this;
        TMCLoggerMod.info("[" + targetRaid.getRaidId() + ", " + targetRaidAccessor.getTicksActive() + "] "
                         + "getRavagerSpawnLocation(proximity = " + proximity + ", tries = " + tries + ") "
                         + "radiusMultiplier = " + (2 - proximity));
        TMCLoggerMod.increaseIndent();
    }
    
    // @ModifyVariable(method = "getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;", at = @At("INVOKE"), print = true)
    // @ModifyVariable(method = "getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;", at = @At("INVOKE"), ordinal = 6, print = true)
    // private int storeGetLocationCounter(int attempt) {
    //     LoggerValueContainer.INT_LIST.add(attempt);
    //     // LoggerValueContainer.INT_MAP.put("attempt", attempt);
    //     // RaidLoggerMod.info("[attempt = " + attempt + "]");
    //     return attempt;
    // }

    @Inject(method = "getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void storeGetLocationCounter(int proximity, int tries, CallbackInfoReturnable<BlockPos> cir, int i, Mutable mutable, int j) {
        LoggerValueContainer.INT_LIST.add(j);
    }

    @Inject(method = "getRavagerSpawnLocation(II)Lnet/minecraft/util/math/BlockPos;", at = @At("RETURN"))
    private void logReturnBlockPos(CallbackInfoReturnable<BlockPos> cir){
        StringBuilder builder = new StringBuilder();
        LoggerValueContainer.INT_LIST.forEach(e -> builder.append(e).append(','));
        TMCLoggerMod.info(LoggerValueContainer.INT_LIST.size() + " attempts = " + builder.toString());
        LoggerValueContainer.INT_LIST.clear();
        BlockPos spawnPos = cir.getReturnValue();
        TMCLoggerMod.info("spawnLocation = " + (spawnPos == null ? "null" : spawnPos.toShortString()));
        if (spawnPos != null){
            Raid targetRaid = (Raid) (Object) this;
            RaidAccessor targetRaidAccessor = (RaidAccessor) (Raid) (Object) this;
            List<ServerPlayerEntity> playerList = ((ServerWorld) targetRaid.getWorld()).getPlayers(targetRaidAccessor.publicInRaidDistancePredicate());
            playerList.forEach(p -> p.sendMessage(new LiteralText("spawnLocation = " + spawnPos.toShortString()), false));
        }
        TMCLoggerMod.decreaseIndent();
    }

    @Inject(method = "spawnNextWave(Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void logWaveSpawnCall(BlockPos pos, CallbackInfo ci) {
        Raid targetRaid = (Raid) (Object) this;
        RaidAccessor targetRaidAccessor = (RaidAccessor) (Raid) (Object) this;
        TMCLoggerMod.getLogger()
                .info("[" + targetRaid.getRaidId() + ", " + targetRaidAccessor.getTicksActive() + "] " 
                    + "WAVE (" + (targetRaid.getGroupsSpawned() + 1) + "/" + targetRaidAccessor.getTotalWaveCount()
                    + ") has spawned at " + pos.toShortString());
    }

    @Inject(
        method = "tick()V", 
        at = @At("RETURN"),
        slice = @Slice(
            from = @At(
                value = "INVOKE", 
                target = "Lnet/minecraft/village/raid/Raid;getRaiderCount()I"
            ), 
            to = @At(
                value = "INVOKE", 
                target = "Lnet/minecraft/village/raid/Raid;canSpawnRaiders()Z"
            )
        )
    )
    private void logPreRaidTickReset(CallbackInfo ci){
        Raid targetRaid = (Raid) (Object) this;
        RaidAccessor targetRaidAccessor = (RaidAccessor) (Raid) (Object) this;
        TMCLoggerMod.getLogger().info("[" + targetRaid.getRaidId() + ", " + targetRaidAccessor.getTicksActive() + "] "
                                     + "preRaidTicks reset to " + targetRaidAccessor.getPreRaidTicks());
    }

}
