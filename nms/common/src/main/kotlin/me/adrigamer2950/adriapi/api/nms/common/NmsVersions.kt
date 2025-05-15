package me.adrigamer2950.adriapi.api.nms.common

import org.bukkit.Bukkit

enum class NmsVersions(vararg val versions: String) {

    V1_17_R1("1.17", "1.17.1"),
    V1_18_R1("1.18", "1.18.1"),
    V1_18_R2("1.18.2"),
    V1_19_R1("1.19", "1.19.1", "1.19.2"),
    V1_19_R2("1.19.3"),
    V1_19_R3("1.19.4"),
    V1_20_R1("1.20", "1.20.1"),
    V1_20_R2("1.20.2"),
    V1_20_R3("1.20.3", "1.20.4"),
    V1_20_R4("1.20.5", "1.20.6"),
    V1_21_R1("1.21", "1.21.1"),
    V1_21_R2("1.21.3"),
    V1_21_R3("1.21.4"),
    V1_21_R4("1.21.5");

    companion object {
        private lateinit var current: NmsVersions

        @JvmStatic
        fun getCurrent(): NmsVersions {
            return if (!::current.isInitialized) {
                entries.firstOrNull {
                    it.versions.contains(Bukkit.getMinecraftVersion())
                } ?: throw IllegalStateException("Nms version not found")
            } else {
                current
            }
        }
    }
}