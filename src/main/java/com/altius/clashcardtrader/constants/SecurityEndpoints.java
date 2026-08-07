package com.altius.clashcardtrader.constants;

public final class SecurityEndpoints {
    public static final String API_BASE = "/v1/api";
    public static final String CARDS = API_BASE + "/cards";
    public static final String CLANS = API_BASE + "/clans/**";
    public static final String PLAYERS = API_BASE + "/players/**";
    public static final String AUTH = API_BASE + "/auth/**";
    public static final String HEALTH = "/actuator/health";

    private SecurityEndpoints() {}

    public static final String[] PUBLIC = {
        CARDS,
        CLANS,
        PLAYERS,
        AUTH,
        HEALTH
    };
}
