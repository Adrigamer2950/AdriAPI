package me.adrigamer2950.adriapi.api.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;

@SuppressWarnings("DeprecatedIsStillUsed")
@Getter
@AllArgsConstructor
public enum ServerType {

    FOLIA("Folia"),
    PAPER_FORK("Paper-Fork"),

    PAPER("Paper"),

    BUKKIT("Bukkit"),

    /**
     * @deprecated I won't be making a Velocity/Bungee API in the near future
     */
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "2.3.0")
    VELOCITY("Velocity"),

    /**
     * @deprecated I won't be making a Velocity/Bungee API in the near future
     */
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "2.3.0")
    BUNGEE("BungeeCord");

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
                    try {
                        Class.forName("com.velocitypowered.api.proxy.ProxyServer");

                        return ServerType.VELOCITY;
                    } catch (ClassNotFoundException ex4) {
                        try {
                            Class.forName("net.md_5.bungee.api.ProxyServer");

                            return ServerType.BUNGEE;
                        } catch (ClassNotFoundException ex5) {
                            return null;
                        }
                    }
                }
            }
        }
    }
}
