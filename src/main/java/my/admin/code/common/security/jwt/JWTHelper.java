package my.admin.code.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

/**
 * Created by ace on 2017/9/10.
 */
public class JWTHelper {
    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();
    /**
     * 密钥加密token
     * @param priKeyPath
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(String acctName, String priKeyPath, int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(acctName)
                //.claim(JWTConstants.JWT_ACCT_ID,jwtInfo.getAcctId())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath))
                .compact();
        return compactJws;
    }

    /**
     * 密钥加密token
     * @param priKey
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(String acctName, byte priKey[], int expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(acctName)
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析token
     * @param token
     * @return
     * @throws Exception
     */
    public static String parserToken(String token, byte[] pubKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
        Claims body=claimsJws.getBody();
        return body.getSubject();
    }

}
