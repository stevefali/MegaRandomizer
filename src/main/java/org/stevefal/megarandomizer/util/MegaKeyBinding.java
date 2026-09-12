package org.stevefal.megarandomizer.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class MegaKeyBinding {

    public static final String KEY_CATEGORY_MEGA = "key.category.mega_randomizer";
    public static final String KEY_OPEN_TRACKER = "key.mega_randomizer.open_tracker";

    public static final KeyMapping TRACKER_KEY = new KeyMapping(
            KEY_OPEN_TRACKER,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            KEY_CATEGORY_MEGA
    );

}