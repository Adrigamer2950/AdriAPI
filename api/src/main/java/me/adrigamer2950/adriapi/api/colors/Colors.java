package me.adrigamer2950.adriapi.api.colors;

import org.bukkit.ChatColor;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.ApiStatus;

/**
 * Used to translate Minecraft Color Codes into Ansi colors or into {@link ChatColor}.
 * You can also use a custom color code
 * system that's easier to remember than Bukkit one.
 */
public class Colors {

    /**
     * Bukkit Color Codes
     */
    public static final String[] mc_col = new String[] {
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
    };

    /**
     * Custom Color Codes
     */
    public static final String[] api_col = new String[] {
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
    };

    /**
     * Go to {@link Colors#api_col} to see how to write AdriAPI's color codes
     *
     * @param input The message
     * @return A colored message
     */
    public static String translateAPIColors(String input) {
        for (int i = 0; i < api_col.length; i++) {
            if (input.contains(api_col[i])) {
                String mc = mc_col[i];
                String c = api_col[i];
                input = input.replaceAll(c, mc);
            }
        }
        return translateColors(input);
    }

    /**
     * Translates color codes
     *
     * @param input The message you want to colorize
     * @return Colorized message
     */
    public static String translateColors(String input) {
        return translateColors(input, '&', false);
    }

    /**
     * Translates color codes
     *
     * @param input     The message you want to colorize
     * @param s         Color character
     * @param toConsole If the message is being sent to console
     * @return Colorized message
     * @deprecated In favor of {@link Colors#translateToAnsi(String)} and {@link Colors#translateToAnsi(String, char)}
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    @Deprecated(forRemoval = true)
    public static String translateColors(String input, char s, boolean toConsole) {
        return toConsole ? translateToAnsi(input, s) : ChatColor.translateAlternateColorCodes(s, input);
    }

    public static String translateToAnsi(String input) {
        return translateToAnsi(input, '&');
    }

    public static String translateToAnsi(String input, char s) {
        return input.replaceAll(s + "0", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString())
                .replaceAll(s + "1", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString())
                .replaceAll(s + "2", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString())
                .replaceAll(s + "3", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString())
                .replaceAll(s + "4", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString())
                .replaceAll(s + "5", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString())
                .replaceAll(s + "6", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString())
                .replaceAll(s + "7", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString())
                .replaceAll(s + "8", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString())
                .replaceAll(s + "9", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString())
                .replaceAll(s + "a", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString())
                .replaceAll(s + "b", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString())
                .replaceAll(s + "c", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString())
                .replaceAll(s + "d", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString())
                .replaceAll(s + "e", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString())
                .replaceAll(s + "f", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString())
                .replaceAll(s + "k", Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString())
                .replaceAll(s + "l", Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString())
                .replaceAll(s + "m", Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString())
                .replaceAll(s + "n", Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString())
                .replaceAll(s + "o", Ansi.ansi().a(Ansi.Attribute.ITALIC).toString())
                .replaceAll(s + "r", Ansi.ansi().a(Ansi.Attribute.RESET).toString())
                + Ansi.ansi().reset().toString();
    }
}
