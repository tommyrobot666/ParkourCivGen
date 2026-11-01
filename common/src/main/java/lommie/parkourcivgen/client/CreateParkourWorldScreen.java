package lommie.parkourcivgen.client;

import lommie.parkourcivgen.chunk.ParkourLevelGeneratorSettings;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class CreateParkourWorldScreen extends Screen {
    private static final Component TITLE = Component.translatable("createWorld.customize.parkour.title");

    public CreateParkourWorldScreen(@NonNull CreateWorldScreen parent, Consumer<ParkourLevelGeneratorSettings> updateGeneratorSettings, ParkourLevelGeneratorSettings generatorSettings) {
        super(TITLE);
    }
}
