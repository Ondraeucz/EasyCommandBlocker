package org.ondraeu.easycommandblocker.Util;

import org.ondraeu.easycommandblocker.CommandBlocker;
import org.bukkit.ChatColor;

public class Util {
    public Util() {
    }

    public static String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', CommandBlocker.getInstance().getConfig().getString("Prefix") + " " + CommandBlocker.getInstance().getConfig().getString(path));
    }
}
