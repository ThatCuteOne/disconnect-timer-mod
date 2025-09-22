package thatcuteone.disconnecttimer

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents


object DisconnectTimerClient : ClientModInitializer {
	override fun onInitializeClient() {
        ConfigManager.HANDLER.load()
        ClientLifecycleEvents.CLIENT_STOPPING.register {
            ConfigManager.HANDLER.save()
        }
	}
}