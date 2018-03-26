package ru.javainside.oauth;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class Config {
    public static final String LOGIN_PERMISSION = "oauth2.login";

    private String baseUrl;
    private String clientId;
    private String clientSecret;
    private String grantPath;
    private String revokePath;
    private String language;

    Config(FileConfiguration configuration) {
        baseUrl = configuration.getString("oauth2.base-url");
        clientId = configuration.getString("oauth2.client-id");
        clientSecret = configuration.getString("oauth2.client-secret");
        grantPath = configuration.getString("oauth2.grant-path");
        revokePath = configuration.getString("oauth2.revoke-path");
        language = configuration.getString("language");
    }
}
