package ru.javainside.oauth.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OAuthResponse {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("expires_in")
    private Integer expiresIn;
    @SerializedName("scope")
    private String scope;
    private List<Authority> authorities;
    private String error;
    @SerializedName("error_description")
    private String errorDescription;
}
