package thatcuteone.disconnecttimer;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import static thatcuteone.disconnecttimer.DisconnectConfigKt.config;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> config.createConfigBuilder().generateScreen(parentScreen);
    }
}