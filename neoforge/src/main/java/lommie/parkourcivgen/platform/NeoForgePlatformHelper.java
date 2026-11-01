package lommie.parkourcivgen.platform;

import com.mojang.serialization.MapCodec;
import lommie.parkourcivgen.RegisterEvents;
import lommie.parkourcivgen.platform.services.IPlatformHelper;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.function.Supplier;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public <T extends ChunkGenerator> Supplier<MapCodec<T>> registerChunkGenerator(String name, MapCodec<T> codec) {
        RegisterEvents.deferredChunkGenerators.add(new RegisterEvents.DeferredChunkGenerator<T>(name,codec));
        return () -> codec;
    }
}