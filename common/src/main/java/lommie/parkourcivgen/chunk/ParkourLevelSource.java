package lommie.parkourcivgen.chunk;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ParkourLevelSource extends ChunkGenerator {
    public static final MapCodec<ParkourLevelSource> CODEC = RecordCodecBuilder.mapCodec((i) ->
            i.group(ParkourLevelGeneratorSettings.CODEC.fieldOf("settings").forGetter(ParkourLevelSource::settings))
                    .apply(i, i.stable(ParkourLevelSource::new))
    );

    private ParkourLevelGeneratorSettings settings() {
        return this.settings;
    }

    private final ParkourLevelGeneratorSettings settings;

    public ParkourLevelSource(ParkourLevelGeneratorSettings settings) {
        super(new FixedBiomeSource(settings.getBiome()));
        this.settings = settings;
    }

    public static ParkourLevelSource getDefault(Holder<Biome> plains) {
        return new ParkourLevelSource(ParkourLevelGeneratorSettings.getDefault(plains));
    }

    @Override
    protected @NonNull MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long l, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess) {}

    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {}

    @Override
    public void spawnOriginalMobs(WorldGenRegion worldGenRegion) {}

    @Override
    public int getGenDepth() {
        return 384;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {
        for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
                BlockPos pos = new BlockPos(x,0,z);
                BlockPos globalPos = chunkAccess.getPos().getBlockAt(pos.getX(),pos.getY(),pos.getZ());

                if (globalPos.getX() % settings.getSpacing() == 0 && globalPos.getZ() % settings.getSpacing() == 0) {
                    chunkAccess.setBlockState(pos, settings.getBlock());
                }
            }
        }

        return CompletableFuture.completedFuture(chunkAccess);
    }

    @Override
    public int getSeaLevel() {
        return -63;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return levelHeightAccessor.getMinY() + 1;
    }

    @Override
    public @NonNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return new NoiseColumn(levelHeightAccessor.getMinY(),
                x%settings.getSpacing()==0&&z%settings.getSpacing()==0 ? new BlockState[]{settings.getBlock()} : new BlockState[]{});
    }

    @Override
    public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {
        list.add("Jump for the beef!");
    }

    public ParkourLevelGeneratorSettings getSettings() {
        return settings;
    }
}
