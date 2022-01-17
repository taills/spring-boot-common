package io.github.taills.jpa.converter;

import com.google.common.net.InetAddresses;

import javax.persistence.AttributeConverter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName IPAddressConverter
 * @Description
 * @Author nil
 * @Date 2022/1/17 8:19 PM
 **/
public class InetAddressConverter implements AttributeConverter<String, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(String inetAddress) {
        return InetAddresses.forString(inetAddress).getAddress();
    }

    @Override
    public String convertToEntityAttribute(byte[] s) {
        try {
            return InetAddress.getByAddress(s).getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
