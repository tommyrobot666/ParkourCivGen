package lommie.parkourcivgen.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;

public class ParkourLevelGeneratorSettings {
    public static final Codec<ParkourLevelGeneratorSettings> CODEC = RecordCodecBuilder.create((i) ->
            i.group(
                    Biome.CODEC.fieldOf("biome").forGetter((s) -> s.biome),
                    BlockState.CODEC.fieldOf("block").forGetter((s) -> s.block),
                    Codec.INT.fieldOf("spacing").forGetter((s) -> s.spacing))
                    .apply(i, ParkourLevelGeneratorSettings::new));//.comapFlatMap(ParkourLevelGeneratorSettings::validateHeight, Function.identity()).stable();

    private final Holder<Biome> biome;
    // TODO add stacking layers
    private final BlockState block;
    private final int spacing;

    public ParkourLevelGeneratorSettings(Holder<Biome> biome, BlockState block, int spacing) {
        this.biome = biome;
        this.block = block;
        this.spacing = spacing;
    }

    private static DataResult<ParkourLevelGeneratorSettings> validateHeight(ParkourLevelGeneratorSettings settings){
        // TODO add heights of stacked layers when exists
        // list.stream().sum()
        return true ? DataResult.success(settings) : DataResult.error(() -> "Sum of layer heights is > " + DimensionType.Y_SIZE, settings);
    }

    public Holder<Biome> getBiome() {
        return biome;
    }

    public BlockState getBlock(){
        return block;
    }

    public int getSpacing(){
        return spacing+1;
    }
}
