package plugins.tobisch.com.network.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Utils {
    public static String chat(String s){

        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String formatMoney(double amount){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        return decimalFormat.format(amount);
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    public static boolean isPlayerInMultiverseWorld(Player player, String worldName) {
        Bukkit.getConsoleSender().sendMessage(player.getWorld().getName());
        return player.getWorld().getName().equalsIgnoreCase(worldName);
    }

    private static final String[] ROMAN_NUMERALS = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };

    private static final int[] VALUES = {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };

    public static String toRoman(int num) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < VALUES.length; i++) {
            while (num >= VALUES[i]) {
                num -= VALUES[i];
                result.append(ROMAN_NUMERALS[i]);
            }
        }
        return result.toString();
    }
}
