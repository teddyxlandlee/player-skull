package io.github.teddyxlandlee.mcmod.playerskull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public interface PlayerSkullLinkageClient {
    void initClient();

    static void load() {
        FabricLoader.getInstance().getEntrypoints("player-skull.client", PlayerSkullLinkageClient.class)
                .forEach(PlayerSkullLinkageClient::initClient);
    }
}
