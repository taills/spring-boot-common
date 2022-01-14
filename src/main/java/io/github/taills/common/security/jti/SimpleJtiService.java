package io.github.taills.common.security.jti;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName SimpleJtiService
 * @Description
 * @Author nil
 * @Date 2022/1/12 5:14 PM
 **/

@Slf4j
public class SimpleJtiService implements JtiService {

    private final Map<String, Date> map = new HashMap<>();

    /**
     * 撤销某个 token
     *
     * @param jti
     */
    @Override
    public void revoke(String jti) {
        map.put(jti, new Date());
    }

    /**
     * 检查某个token是否被撤销
     *
     * @param jti
     * @return
     */
    @Override
    public boolean isRevoked(String jti) {
        return map.containsKey(jti);
    }

    /**
     * 清理已过期的记录
     *
     * @param expiredDate 过期时间
     */
    @Override
    public void cleanupExpiredJti(Date expiredDate) {
        synchronized (this) {
            Map<String, Date> newMap = map.entrySet().stream().filter(x -> x.getValue().after(expiredDate))
                    .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
            this.map.clear();
            this.map.putAll(newMap);
        }
    }


}
