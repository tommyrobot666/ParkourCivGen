package lommie.parkourcivgen.chunk;

import com.mojang.serialization.MapCodec;
import lommie.parkourcivgen.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class ModChunkGenerators {
    static MapCodec<? extends ChunkGenerator> register(String name, MapCodec<? extends ChunkGenerator> codec){
        return Registry.register(BuiltInRegistries.CHUNK_GENERATOR, Identifier.fromNamespaceAndPath(Constants.MOD_ID,name), codec);
    }

    public static final MapCodec<ParkourLevelSource> PARKOUR = (MapCodec<ParkourLevelSource>) register("parkour",ParkourLevelSource.CODEC);

    public static void register(){}
}
