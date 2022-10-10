package digitalcity.demeyert.overstockmanager.model.entity;

public enum Language {
    ENGLISH("English"),
    FRENCH("French"),
    GERMAN("German"),
    SPANISH("Spanish"),
    ITALIAN("Italian"),
    SCHINESE("S-CHINESE"),
    JAPANESE("Japanese"),
    PORTUGUESE("Portuguese"),
    RUSSIAN("Russian"),
    KOREAN("Korean"),
    TCHINESE("T-CHINESE");

    public final String label;

    private Language(String label) {
        this.label = label;
    }

}
