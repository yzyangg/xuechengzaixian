package com.xuecheng.base.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The type Local date time config.
 */
@Configuration
public class LocalDateTimeConfig {

    /**
     * Local date time serializer local date time serializer.
     *
     * @return the local date time serializer
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Local date time deserializer local date time deserializer.
     *
     * @return the local date time deserializer
     */
    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * Jackson 2 object mapper builder customizer jackson 2 object mapper builder customizer.
     *
     * @return the jackson 2 object mapper builder customizer
     */

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
        };
    }

}
