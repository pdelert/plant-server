package pl.plant.server;

import io.smallrye.jwt.KeyUtils;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.Set;

@ApplicationScoped
public class JwtTokenService {

    public String generateToken(JwtClaimsBuilder jwtClaimsBuilder) {
        PrivateKey privateKey = this.readPrivateKey("/test-private-key.pem");
        return jwtClaimsBuilder.jws().keyId("/test-private-key.pem").sign(privateKey);
    }

    public String createToken(String username, Integer tokenExpiration, Set<String> groups) {
        JwtClaimsBuilder claims = this.createClaims(username, tokenExpiration, groups);
        return this.generateToken(claims);
    }

    public JwtClaimsBuilder createClaims(String username, Integer expiration, Set<String> groups) {
        long currentTimeInSecs = (long) ((int) (System.currentTimeMillis() / 1000L));
        long exp = (long) ((int) (System.currentTimeMillis() / 1000L) + expiration);
        JwtClaimsBuilder claims = Jwt.claims();
        claims.issuer("https://quarkus.io/using-jwt-rbac");
        claims.issuedAt(currentTimeInSecs);
        claims.upn(username);
        claims.claim(Claims.auth_time.name(), currentTimeInSecs);
        claims.expiresAt(exp);
        claims.groups(groups);
        return claims;
    }

    private PrivateKey readPrivateKey(String pemResName) {
        try {
            InputStream contentIS = JwtTokenService.class.getResourceAsStream(pemResName);

            PrivateKey var5;
            try {
                byte[] tmp = new byte[4096];
                int length = contentIS.read(tmp);
                var5 = KeyUtils.decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
            } catch (Throwable var7) {
                if (contentIS != null) {
                    try {
                        contentIS.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (contentIS != null) {
                contentIS.close();
            }

            return var5;
        } catch (Exception var8) {
            throw new RuntimeException("Can not read private key.");
        }
    }
}