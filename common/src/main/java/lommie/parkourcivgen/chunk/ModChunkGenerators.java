package lommie.parkourcivgen.chunk;

import com.mojang.serialization.MapCodec;
import lommie.parkourcivgen.platform.Services;
import net.minecraft.world.level.chunk.ChunkGenerator;

import java.util.function.Supplier;

public class ModChunkGenerators {
    static <T extends ChunkGenerator> Supplier<MapCodec<T>> register(String name, MapCodec<T> codec){
        return Services.PLATFORM.registerChunkGenerator(name,codec);
    }

    public static final Supplier<MapCodec<ParkourLevelSource>> PARKOUR = register("parkour",ParkourLevelSource.CODEC);

    public static void register(){}
}
