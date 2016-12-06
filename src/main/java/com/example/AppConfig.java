package com.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

/**
 * Created by sali on 12/5/16.
 */
@Configuration
public class AppConfig
{
    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        return getJsonConverter2();
    }

    /**
     * Produces a JSON converter with application-wide configuration options
     *
     * @return  JSON converter
     */
    public static Jackson2ObjectMapperBuilder getJsonConverter2()
    {
        Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();

        mapperBuilder.failOnUnknownProperties(false);
        mapperBuilder.indentOutput(true);
        mapperBuilder.dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        mapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);

        return mapperBuilder;
    }

}
