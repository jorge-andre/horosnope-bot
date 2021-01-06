public enum Signs {
    AQUARIUS("aquarius"),
    PISCES("pisces"),
    ARIES("aries"),
    TAURUS("taurus"),
    GEMINI("gemini"),
    CANCER("cancer"),
    LEO("leo"),
    VIRGO("virgo"),
    LIBRA("libra"),
    SCORPIO("scorpio"),
    SAGITTARIUS("sagittarius"),
    CAPRICORN("capricorn");

    private final String label;

    Signs(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
