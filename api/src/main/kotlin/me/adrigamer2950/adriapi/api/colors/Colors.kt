package me.adrigamer2950.adriapi.api.colors

import org.bukkit.ChatColor
import org.fusesource.jansi.Ansi
import org.jetbrains.annotations.ApiStatus

/**
 * Used to translate Minecraft Color Codes into Ansi colors or into ChatColor
 * You can also use a custom color code
 * system that's easier to remember than Bukkit one.
 */
object Colors {

    /**
     * Bukkit Color Codes
     */
    val mc_col: Array<String> = arrayOf(
        "&0", //Black
        "&1", //Dark Blue
        "&2", //Dark Green
        "&3", //Dark Aqua
        "&4", //Dark Red
        "&5", //Dark Purple
        "&6", //Gold
        "&7", //Gray
        "&8", //Dark Gray
        "&9", //Blue
        "&a", //Green
        "&b", //Aqua
        "&c", //Red
        "&d", //Light Purple
        "&e", //Yellow
        "&f", //White
        "&k", //Obfuscated
        "&l", //Bold
        "&m", //Strikethrough
        "&n", //Underline
        "&o", //Italic
        "&r", //Reset
    )

    /**
     * Custom Color Codes
     */
    val api_col: Array<String> = arrayOf(
        "<black>", //Black
        "<dark_blue>", //Dark Blue
        "<dark_green>", //Dark Green
        "<dark_aqua>", //Dark Aqua
        "<dark_red>", //Dark Red
        "<dark_purple>", //Dark Purple
        "<gold>", //Gold
        "<gray>", //Gray
        "<dark_gray>", //Dark Gray
        "<blue>", //Blue
        "<green>", //Green
        "<aqua>", //Aqua
        "<red>", //Red
        "<light_purple>", //Light Purple
        "<yellow>", //Yellow
        "<white>", //White
        "<obfuscated>", //Obfuscated
        "<bold>", //Bold
        "<strikethrough>", //Strikethrough
        "<underline>", //Underline
        "<italic>", //Italic
        "<reset>" //Reset
    )

    /**
     * Go to Colors#api_col to see how to write AdriAPI's color codes
     *
     * @param input The message
     * @return A colored message
     */
    @JvmStatic
    fun translateAPIColors(input: String): String {
        var message = input

        for (i in api_col.indices) {
            if (input.contains(api_col[i])) {
                val mc = mc_col[i]
                val c = api_col[i]
                message = message.replace(c, mc)
            }
        }

        return translateColors(message)
    }

    /**
     * Translates color codes
     *
     * @param input The message you want to colorize
     * @return Colorized message
     */
    @JvmStatic
    fun translateColors(input: String): String {
        @Suppress("DEPRECATION")
        return translateColors(input, '&', false)
    }

    /**
     * Translates color codes
     *
     * @param input     The message you want to colorize
     * @param s         Color character
     * @param toConsole If the message is being sent to console
     * @return Colorized message
     */
    @JvmStatic
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    @Deprecated("In favor of Colors#translateToAnsi(String) and Colors#translateToAnsi(String, char)")
    fun translateColors(input: String, s: Char, toConsole: Boolean): String {
        return if (toConsole) translateToAnsi(input, s) else ChatColor.translateAlternateColorCodes(s, input)
    }

    @JvmStatic
    fun translateToAnsi(input: String): String {
        return translateToAnsi(input, '&')
    }

    @JvmStatic
    fun translateToAnsi(input: String, s: Char): String {
        return StringBuilder().append(
            input.replace(s + "0", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString())
                .replace(s + "1", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString())
                .replace(s + "2", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString())
                .replace(s + "3", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString())
                .replace(s + "4", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString())
                .replace(s + "5", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString())
                .replace(s + "6", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString())
                .replace(s + "7", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString())
                .replace(s + "8", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString())
                .replace(s + "9", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString())
                .replace(s + "a", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString())
                .replace(s + "b", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString())
                .replace(s + "c", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString())
                .replace(s + "d", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString())
                .replace(s + "e", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString())
                .replace(s + "f", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString())
                .replace(s + "k", Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString())
                .replace(s + "l", Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString())
                .replace(s + "m", Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString())
                .replace(s + "n", Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString())
                .replace(s + "o", Ansi.ansi().a(Ansi.Attribute.ITALIC).toString())
                .replace(s + "r", Ansi.ansi().a(Ansi.Attribute.RESET).toString())
        )
            .append(Ansi.ansi().reset().toString())
            .toString()
    }
}
