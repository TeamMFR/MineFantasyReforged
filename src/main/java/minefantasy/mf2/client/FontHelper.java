package minefantasy.mf2.client;

public final class FontHelper {

    public static boolean isFormatColour(char colour) {
        return colour >= 48 && colour <= 57 || colour >= 97 && colour <= 102 || colour >= 65 && colour <= 70;
    }

    public static boolean isFormatSpecial(char chars) {
        return chars >= 107 && chars <= 111 || chars >= 75 && chars <= 79 || chars == 114 || chars == 82;
    }

    public static String getFormatFromString(String string) {
        String s1 = "";
        int i = -1;
        int j = string.length();

        while ((i = string.indexOf(167, i + 1)) != -1) {
            if (i < j - 1) {
                char c0 = string.charAt(i + 1);

                if (isFormatColour(c0))
                    s1 = "\u00a7" + c0;
                else if (isFormatSpecial(c0))
                    s1 = s1 + "\u00a7" + c0;
            }
        }

        return s1;
    }

}
