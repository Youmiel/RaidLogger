package youmiel.tmcdebuglogger.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.MinecraftServer;
import youmiel.tmcdebuglogger.Util;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "startServer(Ljava/util/function/Function;)Lnet/minecraft/server/MinecraftServer;", at = @At("RETURN"))
    private static void cacheServerInstance(CallbackInfoReturnable<MinecraftServer> cir) {
        Util.setGlobalServer(cir.getReturnValue());
    }
}
