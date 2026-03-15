package thatcuteone.disconnecttimer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class DisconnectTimerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigManager.HANDLER.load();
        ClientLifecycleEvents.CLIENT_STOPPING.register(_ -> ConfigManager.HANDLER.save());
    }
}