package me.adrigamer2950.adriapi.api.util

import org.bukkit.Bukkit

enum class ServerType(val serverName: String) {

    FOLIA("Folia"),

    PAPER("Paper"),

    BUKKIT("Bukkit");

    companion object {
        @JvmField
        val type: ServerType = getServerType() ?: throw IllegalStateException("Unknown server type")

        @JvmField
        val name: String = Bukkit.getServer().name

        @JvmStatic
        private fun getServerType(): ServerType? {
            return try {
                Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
                FOLIA
            } catch (_: ClassNotFoundException) {
                try {
                    Class.forName("io.papermc.paper.util.Tick")
                    PAPER
                } catch (_: ClassNotFoundException) {
                    try {
                        Class.forName("org.bukkit.Bukkit")
                        BUKKIT
                    } catch (_: ClassNotFoundException) {
                        null
                    }
                }
            }
        }
    }
}
