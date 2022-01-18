package io.github.taills.jpa.converter;

import com.google.common.net.InetAddresses;
import io.github.taills.common.util.IpUtils;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName IPAddressConverter
 * @Description
 * @Author nil
 * @Date 2022/1/17 8:19 PM
 **/
@Slf4j
public class InetAddressConverter implements AttributeConverter<String, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(String inetAddress) {
        return IpUtils.inetAtoN(inetAddress);
    }

    @Override
    public String convertToEntityAttribute(byte[] s) {
        try {
            return IpUtils.inetNtoA(s);
        } catch (UnknownHostException e) {
            log.error("IP 解析失败 {}，将返回预设的 255.255.255.255", e.getLocalizedMessage());
        }
        return "255.255.255.255";
    }
}
