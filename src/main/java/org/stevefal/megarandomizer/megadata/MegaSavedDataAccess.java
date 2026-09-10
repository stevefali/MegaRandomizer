package org.stevefal.megarandomizer.megadata;

import net.minecraft.server.MinecraftServer;

public class MegaSavedDataAccess {

    private static MegaSavedData megaSavedData;

    public static void initializeMegaSavedData(MinecraftServer server) {
        megaSavedData = MegaSavedData.get(server);
    }

    public static MegaSavedData getMegaSavedData() {
        return megaSavedData;
    }

}
