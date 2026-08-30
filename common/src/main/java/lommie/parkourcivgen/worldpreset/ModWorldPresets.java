package lommie.parkourcivgen.worldpreset;

import lommie.parkourcivgen.Constants;
import lommie.parkourcivgen.chunk.ParkourLevelGeneratorSettings;
import lommie.parkourcivgen.chunk.ParkourLevelSource;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.presets.WorldPreset;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModWorldPresets {
    public record BootstrappedPreset(ResourceKey<WorldPreset> preset, Function<Holder.Reference<Biome>,ChunkGenerator> generator) {}

    // use this.registerCustomOverworldPreset(p,g);
    public static List<BootstrappedPreset> ADD_TO_BOOTSTRAP_MIXIN = new ArrayList<>();

    // biome reference is plains
    static ResourceKey<WorldPreset> register(String name, Function<Holder.Reference<Biome>,ChunkGenerator> generator){
        ResourceKey<WorldPreset> preset = ResourceKey.create(Registries.WORLD_PRESET, Identifier.fromNamespaceAndPath(Constants.MOD_ID,name));
        ADD_TO_BOOTSTRAP_MIXIN.add(new BootstrappedPreset(preset,generator));
        return preset;
    }

    public static final ResourceKey<WorldPreset> PARKOUR = register("parkour",
            (plains) -> new ParkourLevelSource(new ParkourLevelGeneratorSettings(
                    plains,
                    Blocks.GRASS_BLOCK.defaultBlockState(),
                    1
            )));


    public static void register(){}
}
