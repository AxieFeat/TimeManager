package net.arial.time;

import org.bukkit.configuration.file.FileConfiguration;

public class Time {

    public static FileConfiguration config;
    public static void install(FileConfiguration config) {
        if (config == null) {
            throw new NullPointerException("FileConfiguration can not be null!");
        }
        Time.config = config;
    }
}
