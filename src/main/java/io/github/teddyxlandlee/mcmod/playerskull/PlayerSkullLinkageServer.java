package io.github.teddyxlandlee.mcmod.playerskull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.SERVER)
public interface PlayerSkullLinkageServer {
    void initServer();

    static void load() {
        FabricLoader.getInstance().getEntrypoints("player-skull.server", PlayerSkullLinkageServer.class)
                .forEach(PlayerSkullLinkageServer::initServer);
    }
}
