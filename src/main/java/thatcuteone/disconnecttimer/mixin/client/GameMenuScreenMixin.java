package thatcuteone.disconnecttimer.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static thatcuteone.disconnecttimer.DisconnectConfigKt.config;


@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin {
    private Double timer;
    private Text original_text;
    @Shadow
    @Nullable
    private ButtonWidget exitButton;

	@Inject(at = @At("TAIL"), method = "initWidgets")
    private void handleExitButtonState(CallbackInfo ci) {
        if (!config.modEnabled) return;
        if (MinecraftClient.getInstance().isInSingleplayer() && !config.applyToSingleplayer){
            return;
        }
        timer = config.timer;
        if (this.exitButton != null) {
            original_text = this.exitButton.getMessage();
            this.exitButton.active = false;
            this.exitButton.setMessage(
                    Text.literal(String.format("%s (%.1fs)",original_text.getString(), timer))
            );
        }
    }
    @Inject(method = "tick", at = @At("TAIL"))
    private void checkButtonState(CallbackInfo ci) {
        if (!config.modEnabled) return;
        if (MinecraftClient.getInstance().isInSingleplayer() && !config.applyToSingleplayer){
            return;
        }
        //timer -= 0.05d;
        if (this.exitButton != null && timer <= 0) {;
            this.exitButton.setMessage(original_text);
            this.exitButton.active = true;
        }
        else {
            assert this.exitButton != null;
            this.exitButton.setMessage(
                    Text.literal(String.format("%s (%.1fs)",original_text.getString(), timer))
            );
        }
    }
}