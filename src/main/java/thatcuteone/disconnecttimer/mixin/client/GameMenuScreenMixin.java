package thatcuteone.disconnecttimer.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static thatcuteone.disconnecttimer.DisconnectConfigKt.config;


@Mixin(PauseScreen.class)
public class GameMenuScreenMixin {
    private Double timer;
    private Component original_text;
    @Shadow
    @Nullable
    private Button disconnectButton;

	@Inject(at = @At("TAIL"), method = "createPauseMenu")
    private void handleExitButtonState(CallbackInfo ci) {
        if (!config.modEnabled) return;
        if (Minecraft.getInstance().isSingleplayer() && !config.applyToSingleplayer){
            return;
        }
        timer = config.timer;
        if (this.disconnectButton != null) {
            original_text = this.disconnectButton.getMessage();
            this.disconnectButton.active = false;
            this.disconnectButton.setMessage(
                    Component.literal(String.format("%s (%.1fs)",original_text.getString(), timer))
            );
        }
    }
    @Inject(method = "tick", at = @At("TAIL"))
    private void checkButtonState(CallbackInfo ci) {
        if (!config.modEnabled) return;
        if (Minecraft.getInstance().isSingleplayer() && !config.applyToSingleplayer){
            return;
        }
        timer -= 0.05d;
        if (this.disconnectButton != null && timer <= 0) {;
            this.disconnectButton.setMessage(original_text);
            this.disconnectButton.active = true;
        }
        else {
            assert this.disconnectButton != null;
            this.disconnectButton.setMessage(
                    Component.literal(String.format("%s (%.1fs)",original_text.getString(), timer))
            );
        }
    }
}