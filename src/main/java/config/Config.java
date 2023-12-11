package config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.load();
    public static String dbUrl = dotenv.get("DB_URL");
    public static String dbUser = dotenv.get("DB_USER");
    public static String dbPassword = dotenv.get("DB_PASSWORD");
    public static String accessToken = dotenv.get("ACCESS_TOKEN");
}
