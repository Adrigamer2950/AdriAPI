package me.adrigamer2950.adriapi.api.util;

import java.text.DecimalFormat;

public class DecimalUtil {

    public static double roundDecimals(double decimal, int maxDecimal) {
        StringBuilder pattern = new StringBuilder("#.");
        for(int i = 0 ; i < maxDecimal ; i++)
            pattern.append("#");

        DecimalFormat formatter = new DecimalFormat(pattern.toString());
        
        try {
            return Double.parseDouble(formatter.format(decimal));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
