package lommie.parkourcivgen.mixin;

import lommie.parkourcivgen.worldpreset.ModWorldPresets;
import net.minecraft.client.gui.screens.worldselection.PresetEditor;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(WorldCreationUiState.class)
public class WorldCreationUiStateMixin {
    @Redirect(method = {
            "Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState;getPresetEditor()Lnet/minecraft/client/gui/screens/worldselection/PresetEditor;",
            "Lnet/minecraft/client/gui/screens/worldselection/WorldCreationUiState;updatePresetLists()V"
    },
            at= @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/worldselection/PresetEditor;EDITORS:Ljava/util/Map;"))
    private Map<Optional<ResourceKey<WorldPreset>>, PresetEditor> addParkourGenEditor(){
        HashMap<Optional<ResourceKey<WorldPreset>>, PresetEditor> editors = new HashMap<>(PresetEditor.EDITORS);
        for (ModWorldPresets.BootstrappedPreset preset : ModWorldPresets.deferredBootstrappedPresets) {
            if (preset.presetEditor() != null){
                editors.put(Optional.of(preset.preset()), preset.presetEditor());
            }
        }
        return editors;
    }
}
