package youmiel.raidlogger.mixin;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.raid.Raid;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Raid.class)
public interface RaidAccessor {
    @Accessor("preRaidTicks")
    public int getPreRaidTicks();

    @Accessor("waveCount")
    public int getTotalWaveCount();

    @Accessor("ticksActive")
    public long getTicksActive();

    @Invoker("isInRaidDistance")
    public Predicate<ServerPlayerEntity> publicInRaidDistancePredicate();
}
