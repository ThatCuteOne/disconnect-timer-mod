package thatcuteone.disconnecttimer;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import com.google.gson.GsonBuilder;

public final class ConfigManager {
    private ConfigManager() {
    }

    public static final ConfigClassHandler<DisconnectConfig> HANDLER =
            ConfigClassHandler.createBuilder(DisconnectConfig.class)
                    .id(Identifier.fromNamespaceAndPath("disconnecttimer", "disconnect_timer_config"))
                    .serializer(config ->
                            GsonConfigSerializerBuilder.create(config)
                                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("disconnect_timer.json"))
                                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                                    .setJson5(false)
                                    .build()
                    )
                    .build();

    public static DisconnectConfig getConfig() {
        return HANDLER.instance();
    }
}