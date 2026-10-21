package com.example.practicalfive;

/**
 * Regular Java class (not an Activity, no layout) that validates and normalises the
 * municipality names typed by the user.
 */
public class TextProcessor {

    public static final String BEIJING = "Beijing";
    public static final String SHANGHAI = "Shanghai";
    public static final String TIANJIN = "Tianjin";
    public static final String CHONGQING = "Chongqing";

    private static final String[] SUPPORTED_MUNICIPALITIES = {BEIJING, SHANGHAI, TIANJIN, CHONGQING};

    /**
     * @return true if the text is null, empty or contains only whitespace
     */
    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * Validates a municipality name without being case-sensitive.
     *
     * @param municipality the text typed by the user (may be null)
     * @return the correctly capitalised name (for example "Beijing" for "BEIJING"),
     * or null when the input is null, empty, whitespace only or not a supported municipality
     */
    public static String processMunicipality(String municipality) {
        if (isBlank(municipality)) {
            return null;
        }
        String candidate = municipality.trim();
        for (String name : SUPPORTED_MUNICIPALITIES) {
            if (name.equalsIgnoreCase(candidate)) {
                return name;
            }
        }
        return null;
    }
}
