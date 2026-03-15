package thatcuteone.disconnecttimer;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DoubleFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public class DisconnectConfig {
    @SerialEntry
    public boolean modEnabled = true;

    @SerialEntry
    public double timer = 3.0;

    @SerialEntry
    public boolean applyToSingleplayer = true;

    public YetAnotherConfigLib createConfigBuilder() {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("disconnect_timer.config.title"))
                .category(createMainCategory())
                .build();
    }

    private ConfigCategory createMainCategory() {
        return ConfigCategory.createBuilder()
                .name(Component.translatable("disconnect_timer.config.title"))
                .tooltip(Component.literal(""))
                .option(
                        Option.<Boolean>createBuilder()
                                .name(Component.translatable("disconnect_timer.config.enable_mod"))
                                .description(OptionDescription.of(Component.translatable("disconnect_timer.config.enable_mod.tooltip")))
                                .binding(
                                        true,
                                        () -> this.modEnabled,
                                        newVal -> this.modEnabled = Objects.requireNonNull(newVal)
                                )
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                )
                .option(
                        Option.<Double>createBuilder()
                                .name(Component.translatable("disconnect_timer.config.timer"))
                                .description(OptionDescription.of(Component.translatable("disconnect_timer.config.timer.tooltip")))
                                .binding(
                                        3.0,
                                        () -> this.timer,
                                        newVal -> this.timer = newVal
                                )
                                .controller(DoubleFieldControllerBuilder::create)
                                .build()
                )
                .option(
                        Option.<Boolean>createBuilder()
                                .name(Component.translatable("disconnect_timer.config.enable_singleplayer"))
                                .description(OptionDescription.of(Component.translatable("disconnect_timer.config.enable_singleplayer.tooltip")))
                                .binding(
                                        true,
                                        () -> this.applyToSingleplayer,
                                        newVal -> this.applyToSingleplayer = Objects.requireNonNull(newVal)
                                )
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                )
                .build();
    }
}



// public final class DisconnectTimerConfig {
//     private DisconnectTimerConfig() {
//     }

//     public static final DisconnectConfig config = ConfigManager.getConfig();
// }