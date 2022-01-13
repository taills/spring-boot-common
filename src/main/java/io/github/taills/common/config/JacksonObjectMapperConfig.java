package io.github.taills.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName JsonConfig
 * @Description
 * @Author nil
 * @Date 2021/10/24 1:31 上午
 **/

@Configuration
public class JacksonObjectMapperConfig {
    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        //针对于JDK新时间类。序列化时带有T的问题，自定义格式化字符串
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        SimpleModule bigIntegerModule = new SimpleModule();
        //序列化将BigInteger转String类型
        bigIntegerModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        SimpleModule bigDecimalModule = new SimpleModule();
        //序列化将BigDecimal转String类型
        bigDecimalModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);

        return builder.createXmlMapper(false)
                .modulesToInstall(javaTimeModule)
                .modulesToInstall(bigIntegerModule)
                .modulesToInstall(bigDecimalModule)
                .failOnEmptyBeans(false)
                //针对于Date类型，文本格式化
                .simpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .build();
    }
}
