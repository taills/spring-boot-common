package io.github.taills.common.security.jti;

import java.util.Date;

/**
 * @ClassName JtiBlackListService
 * @Description
 * @Author nil
 * @Date 2022/1/12 4:57 PM
 **/

public interface JtiService {
    /**
     * 撤销某个 token
     * @param jti
     */
    void revoke(String jti);

    /**
     * 检查某个token是否被撤销
     * @param jti
     * @return
     */
    boolean isRevoked(String jti);

    /**
     * 清理已过期的记录
     * @param expiredDate  过期时间
     */
    void cleanupExpiredJti(Date expiredDate);

}
