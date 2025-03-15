package me.adrigamer2950.adriapi.api.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("SpellCheckingInspection")
@Getter
@AllArgsConstructor
public enum ServerType {

    FOLIA("Folia"),

    PAPER("Paper"),

    BUKKIT("Bukkit");

    @Getter
    private static final ServerType type = ServerType.getServerType();

    @SuppressWarnings("NonFinalFieldInEnum")
    @Setter(AccessLevel.PRIVATE)
    private String name;

    private static ServerType getServerType() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");

            return ServerType.FOLIA;
        } catch (ClassNotFoundException ex1) {
            try {
                Class.forName("io.papermc.paper.util.Tick");

                return ServerType.PAPER;
            } catch (ClassNotFoundException ex2) {
                try {
                    Class.forName("org.bukkit.Bukkit");

                    return ServerType.BUKKIT;
                } catch (ClassNotFoundException ex3) {
                    return null;
                }
            }
        }
    }
}
