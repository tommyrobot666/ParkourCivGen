package lommie.parkourcivgen.mixin;

import lommie.parkourcivgen.worldpreset.ModWorldPresets;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldPresets.Bootstrap.class)
public abstract class WorldPresetsBootstrapMixin {

    @Shadow
    protected abstract void registerCustomOverworldPreset(ResourceKey<WorldPreset> debug, LevelStem overworld);

    @Shadow
    protected abstract LevelStem makeOverworld(ChunkGenerator generator);

    @Shadow
    @Final
    private HolderGetter<Biome> biomes;

    @Inject(
            method = "Lnet/minecraft/world/level/levelgen/presets/WorldPresets$Bootstrap;bootstrap()V",
            at = @At("TAIL"))
    void addMoreWorldPresets(CallbackInfo ci){
        Holder.Reference<Biome> plains = this.biomes.getOrThrow(Biomes.PLAINS);
        for (ModWorldPresets.BootstrappedPreset bootstrappedPreset : ModWorldPresets.ADD_TO_BOOTSTRAP_MIXIN) {
            this.registerCustomOverworldPreset(bootstrappedPreset.preset(),
                    this.makeOverworld(bootstrappedPreset.generator().apply(plains)));
        }
    }
}
