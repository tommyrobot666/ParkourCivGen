package lommie.parkourcivgen.platform;

import com.mojang.serialization.MapCodec;
import lommie.parkourcivgen.Constants;
import lommie.parkourcivgen.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T extends ChunkGenerator> Supplier<MapCodec<T>> registerChunkGenerator(String name, MapCodec<T> codec) {
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, Identifier.fromNamespaceAndPath(Constants.MOD_ID,name), codec);
        return () -> codec;
    }
}
