package io.github.taills.common.security.jti;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SimpleJtiService
 * @Description
 * @Author nil
 * @Date 2022/1/12 5:14 PM
 **/

@Slf4j
public class SimpleJtiService implements JtiService {

    private Set<String> stringSet = new HashSet<>();

    /**
     * 撤销某个 token
     *
     * @param jti
     */
    @Override
    public void revoke(String jti) {
        stringSet.add(jti);
    }

    /**
     * 检查某个token是否被撤销
     *
     * @param jti
     * @return
     */
    @Override
    public boolean isRevoked(String jti) {
        return stringSet.contains(jti);
    }
}
