package thatcuteone.disconnecttimer

import com.google.gson.GsonBuilder
import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import dev.isxander.yacl3.api.controller.DoubleFieldControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.text.Text
import net.minecraft.util.Identifier



class DisconnectConfig {
    @JvmField
    @SerialEntry
    var modEnabled: Boolean = true
    @JvmField
    @SerialEntry
    var timer: Double = 3.0
    @JvmField
    @SerialEntry
    var applyToSingleplayer: Boolean = true


    fun createConfigBuilder(): YetAnotherConfigLib {
        return YetAnotherConfigLib.createBuilder()
            .title(Text.translatable("disconnect_timer.config.title"))
            .category(createMainCategory())
            .build()
    }
    private fun createMainCategory(): ConfigCategory {
        return ConfigCategory.createBuilder()
            .name(Text.translatable("disconnect_timer.config.title"))
            .tooltip(Text.literal("meow"))
            .option(
                        Option.createBuilder<Boolean?>()
                            .name(Text.translatable("disconnect_timer.config.enable_mod"))
                            .description(OptionDescription.of(Text.translatable("disconnect_timer.config.enable_mod.tooltip")))
                            .binding(
                                true,
                                { this.modEnabled },
                                { newVal: Boolean? -> this.modEnabled = newVal!! })
                            .controller { option: Option<Boolean?>? ->
                                TickBoxControllerBuilder.create(
                                    option
                                )
                            }.build()
            )
            .option (
                Option.createBuilder<Double>()
                    .name(Text.translatable("disconnect_timer.config.timer"))
                    .description(OptionDescription.of(Text.translatable("disconnect_timer.config.timer.tooltip")))
                    .binding(
                        3.0,
                        { this.timer},
                        { newVal: Double -> this.timer = newVal }
                    )
                    .controller {opt -> DoubleFieldControllerBuilder.create(opt)}
                    .build()
            )
            .option(
                Option.createBuilder<Boolean?>()
                    .name(Text.translatable("disconnect_timer.config.enable_singleplayer"))
                    .description(OptionDescription.of(Text.translatable("disconnect_timer.config.enable_singleplayer.tooltip")))
                    .binding(
                        true,
                        { this.applyToSingleplayer },
                        { newVal: Boolean? -> this.applyToSingleplayer = newVal!! })
                    .controller { option: Option<Boolean?>? ->
                        TickBoxControllerBuilder.create(
                            option
                        )
                    }.build()
            )
            .build()

    }

}

object ConfigManager {
    val HANDLER: ConfigClassHandler<DisconnectConfig> = ConfigClassHandler.createBuilder(DisconnectConfig::class.java)
        .id(Identifier.of("disconnecttimer", "disconnect_timer_config"))
        .serializer { config ->
            GsonConfigSerializerBuilder.create(config)
                .setPath(FabricLoader.getInstance().configDir.resolve ("disconnect_timer.json"))
                .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                .setJson5(false)
                .build()
        }
        .build()

    val config: DisconnectConfig get() = HANDLER.instance()
}

@JvmField
val config = ConfigManager.config