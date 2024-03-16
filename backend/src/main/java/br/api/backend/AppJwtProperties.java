package br.api.backend;

import ch.qos.logback.core.util.Duration;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import java.time.temporal.TemporalAmount;
import javax.crypto.SecretKey;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app.jwt")
public class AppJwtProperties {

    private SecretKey key;
    private String issuer;
    private JWSAlgorithm algorithm;
    private Duration expiresIn;

    public void setAlgorithm(String algorithm) {
        this.algorithm = JWSAlgorithm.parse(algorithm);
    }

    public void setKey(String key) {
        var jwk = new OctetSequenceKey.Builder(key.getBytes())
                .algorithm(algorithm)
                .build();

        this.key = jwk.toSecretKey();
    }

}