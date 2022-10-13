package digitalcity.demeyert.overstockmanager.model.entity;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    ENGLISH("English"),
    FRENCH("French"),
    GERMAN("German"),
    SPANISH("Spanish"),
    ITALIAN("Italian"),
    SCHINESE("S-Chinese"),
    JAPANESE("Japanese"),
    PORTUGUESE("Portuguese"),
    RUSSIAN("Russian"),
    KOREAN("Korean"),
    TCHINESE("T-Chinese");

    private final String label;

    Language(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Language fromLabel(String label){

        return switch (label) {
            case "English" -> ENGLISH;
            case "French" -> FRENCH;
            case "German" -> GERMAN;
            case "Spanish" -> SPANISH;
            case "Italian" -> ITALIAN;
            case "S-Chinese" -> SCHINESE;
            case "Japanese" -> JAPANESE;
            case "Portuguese" -> PORTUGUESE;
            case "Russian" -> RUSSIAN;
            case "Korean" -> KOREAN;
            case "T-Chinese" -> TCHINESE;
            default -> null;
        };
    }



}
