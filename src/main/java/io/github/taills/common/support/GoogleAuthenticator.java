package io.github.taills.common.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author 参考 https://www.iteye.com/blog/awtqty-zhang-1986275
 */
@Slf4j
public class GoogleAuthenticator {

    private static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

    /**
     * 随机数种子前缀
     */
    private static final String SEED_PREFIX = "9OWKysNgWtgAOidiKO5JN37WsvGbZ7SoCTHgnlQ0lY";

    /**
     * 密钥长度，长度 40 生成的 String length 为 64
     */
    private static final int SECRET_SIZE = 40;

    /**
     * 宽限窗口期，为了避免服务器时间与验证器存在误差而造成验证失败。
     * 窗口期设置为0时，某一时刻内，只有 1 个有效的code
     * 窗口期设置为1时，某一时刻内，有 3 个有效的code
     * 窗口期设置为2时，某一时刻内，有 5 个有效的code
     * 以此类推，也就是当前时间点的前N个 + 后 N 个 code，都有效
     */
    private static final int WINDOW_SIZE = 1;

    private static Base32 base32 = new Base32();

    private static SecureRandom secureRandom;

    static {
        try {
            secureRandom = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
            secureRandom.setSeed(String.format("%s-%d", SEED_PREFIX, System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            log.error("随机数生成器初始化失败。{} ", e);
        }
    }

    /**
     * 产生一个密钥
     *
     * @return
     */
    public static String generateSecretKey() {
        byte[] buffer = secureRandom.generateSeed(SECRET_SIZE);
        return new String(base32.encode(buffer));
    }

    /**
     * 生成一个验证器的 URL
     *
     * @param issuer
     * @param username
     * @param secret
     * @return
     */
    public static String makeUrl(String issuer, String username, String secret) {
        return String.format("otpauth://totp/%s?secret=%s&issuer=%s", username, secret, issuer);
    }

    /**
     * 校验
     *
     * @param secret 密钥
     * @param code   动态码
     * @return
     */
    public static boolean verifyCode(String secret, long code) {
        byte[] decodedKey = base32.decode(secret);
        // convert unix msec time into a 30 second "window"
        // this is per the TOTP spec (see the RFC for details)
        long t = (System.currentTimeMillis() / 1000L) / 30L;
        // Window is used to check codes generated in the near past.
        // You can use this value to tune how far you're willing to go.
        for (int i = -WINDOW_SIZE; i <= WINDOW_SIZE; ++i) {
            long hash;
            try {
                hash = verifyCode(decodedKey, t + i);
            } catch (Exception e) {
                // Yes, this is bad form - but
                // the exceptions thrown would be rare and a static configuration problem
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
                //return false;
            }
            if (hash == code) {
                return true;
            }
        }
        // The validation code is invalid.
        return false;
    }

    private static int verifyCode(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }

    private GoogleAuthenticator() {
    }
}
