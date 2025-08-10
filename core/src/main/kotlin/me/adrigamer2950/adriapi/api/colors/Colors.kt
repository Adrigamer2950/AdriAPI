package me.adrigamer2950.adriapi.api.colors

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.ChatColor
import org.fusesource.jansi.Ansi

/**
 * Used to translate Minecraft Color Codes into Ansi colors or into ChatColor
 * You can also use a custom color code
 * system that's easier to remember than Bukkit one.
 */
object Colors {

    private val AMPERSAND_SERIALIZER = LegacyComponentSerializer.builder()
        .character('&')
        .hexColors()
        .build()

    @JvmStatic
    fun legacy(input: String): String {
        return ChatColor.translateAlternateColorCodes('&', input)
    }

    @JvmStatic
    fun legacyToComponent(input: String): Component {
        return AMPERSAND_SERIALIZER.deserialize(input)
    }

    @JvmStatic
    fun componentToLegacy(input: Component): String {
        return AMPERSAND_SERIALIZER.serialize(input)
    }

    @JvmStatic
    fun legacyToAnsi(input: String): String {
        return "${
            input
                .replace("&0", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).bold().toString())
                .replace("&1", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString())
                .replace("&2", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString())
                .replace("&3", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString())
                .replace("&4", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString())
                .replace("&5", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString())
                .replace("&6", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString())
                .replace("&7", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString())
                .replace("&8", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString())
                .replace("&9", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString())
                .replace("&a", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString())
                .replace("&b", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString())
                .replace("&c", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString())
                .replace("&d", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString())
                .replace("&e", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString())
                .replace("&f", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString())
                .replace("&k", Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString())
                .replace("&l", Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString())
                .replace("&m", Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString())
                .replace("&n", Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString())
                .replace("&o", Ansi.ansi().a(Ansi.Attribute.ITALIC).toString())
                .replace("&r", Ansi.ansi().a(Ansi.Attribute.RESET).toString())
        }${Ansi.ansi().reset()}"
    }

    @JvmStatic
    fun componentToAnsi(input: Component): String {
        return legacyToAnsi(
            LegacyComponentSerializer.legacyAmpersand().serialize(input)
        )
    }
}
