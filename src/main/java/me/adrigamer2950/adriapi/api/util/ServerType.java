package me.adrigamer2950.adriapi.api.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

@Getter
@AllArgsConstructor
public enum ServerType {

    FOLIA("Folia"),
    PAPER_FORK("Paper-Fork"),

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

                if (!Bukkit.getServer().getName().contains("Paper")) {
                    ServerType.PAPER_FORK.setName(Bukkit.getServer().getName());

                    return ServerType.PAPER_FORK;
                }

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
