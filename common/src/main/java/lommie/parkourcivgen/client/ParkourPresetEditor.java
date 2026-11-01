package lommie.parkourcivgen.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.PresetEditor;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import org.jspecify.annotations.NonNull;

public class ParkourPresetEditor implements PresetEditor {
    @Override
    public @NonNull Screen createEditScreen(@NonNull CreateWorldScreen parent, @NonNull WorldCreationContext context) {
        return parent;
    }
}
