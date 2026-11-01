package lommie.parkourcivgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;

@EventBusSubscriber
public class RegisterEvents {
    public record DeferredChunkGenerator<T extends ChunkGenerator>(String name, MapCodec<T> codec){}
    public static ArrayList<DeferredChunkGenerator<?>> deferredChunkGenerators = new ArrayList<>();

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(
                Registries.CHUNK_GENERATOR,
                registry -> {
                    for (DeferredChunkGenerator<?> deferredChunkGenerator : deferredChunkGenerators) {
                        registry.register(Identifier.fromNamespaceAndPath(Constants.MOD_ID,deferredChunkGenerator.name),deferredChunkGenerator.codec);
                    }
                }
        );
    }
}
