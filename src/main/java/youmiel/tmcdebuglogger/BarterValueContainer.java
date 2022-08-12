package youmiel.tmcdebuglogger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class BarterValueContainer {
    public static PlayerEntity player = null;
    // public static BlockPos dropLocation = null;
    public static boolean isPiglinPos = false;

    public static void resetField(){
        player = null;
        // dropLocation = null;
        isPiglinPos = false;
    }
}
