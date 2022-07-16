package youmiel.raidlogger.mixin;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Raid.class)
public interface RaidAccessor {
    @Accessor("preRaidTicks")
    public int getPreRaidTicks();

    @Accessor("waveCount")
    public int getTotalWaveCount();

    @Accessor("ticksActive")
    public long getTicksActive();
}
