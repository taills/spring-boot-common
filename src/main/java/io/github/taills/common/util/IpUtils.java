package io.github.taills.common.util;

import com.google.common.net.InetAddresses;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName IpUtils
 * @Description
 * @Author nil
 * @Date 2022/1/18 5:46 PM
 **/
public class IpUtils {
    /**
     * IP 文本转字节数组
     * @param ip
     * @return
     */
    public static byte[] inetAtoN(String ip) {
        return InetAddresses.forString(ip).getAddress();
    }

    /**
     * 字节数组转IP文本
     * @param ip
     * @return
     * @throws UnknownHostException
     */
    public static String inetNtoA(byte[] ip) throws UnknownHostException {
        return InetAddress.getByAddress(ip).getHostAddress();
    }
}
