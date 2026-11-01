package lommie.parkourcivgen.client;

import lommie.parkourcivgen.chunk.ParkourLevelGeneratorSettings;
import lommie.parkourcivgen.chunk.ParkourLevelSource;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.PresetEditor;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class ParkourPresetEditor implements PresetEditor {
    @Override
    public @NonNull Screen createEditScreen(@NonNull CreateWorldScreen parent, @NonNull WorldCreationContext context) {
        Consumer<ParkourLevelGeneratorSettings> updateGeneratorSettings = (parkourWorldSettings) -> parent.getUiState().updateDimensions(parkourWorldConfigurator(parkourWorldSettings));
        ChunkGenerator overworld = context.selectedDimensions().overworld();
        Holder.Reference<Biome> plains = Holder.Reference.createStandAlone(VanillaRegistries.createLookup().lookupOrThrow(Registries.BIOME),Biomes.PLAINS);
        //Holder.Reference<Biome> plains = context.worldgenRegistries().getLayer(RegistryLayer.WORLDGEN).get(Registries.BIOME).orElseThrow().value().get(Biomes.PLAINS).orElseThrow();
        ParkourLevelGeneratorSettings generatorSettings = overworld instanceof ParkourLevelSource ? ((ParkourLevelSource) overworld).getSettings() : ParkourLevelGeneratorSettings.getDefault(plains);
        return new CreateParkourWorldScreen(parent,updateGeneratorSettings,generatorSettings);
    }

    private static WorldCreationContext.DimensionsUpdater parkourWorldConfigurator(ParkourLevelGeneratorSettings parkourWorldSettings) {
        return (registryAccess, dimensions) -> {
            // TODO add settings for each dimension
            // new WorldDimensions(modified_dims+unmodified_dims, parkour_settings+original_settings)

            ParkourLevelSource generator = new ParkourLevelSource(parkourWorldSettings);
            return dimensions.replaceOverworldGenerator(registryAccess,generator);
        };
    }
}
