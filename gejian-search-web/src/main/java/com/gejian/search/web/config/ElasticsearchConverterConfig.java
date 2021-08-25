package com.gejian.search.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：lijianghuai
 * @date ：2021-08-25 11:34
 * @description：
 */

@Configuration
public class ElasticsearchConverterConfig extends ElasticsearchConfigurationSupport {
    @Bean
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        List<Converter> converters = new ArrayList<>();
        converters.add(LongToLocalDateTimeConverter.INSTANCE);
        return new ElasticsearchCustomConversions(converters);
    }
    /**
     * 查询时 Long 转 LocalDateTime
     *
     * @ReadingConverter 保证仅在查询时使用
     */
    @ReadingConverter
    enum LongToLocalDateTimeConverter implements Converter<Integer, LocalDateTime> {
        /**
         *
         */
        INSTANCE;
        @Override
        public LocalDateTime convert(Integer seconds) {
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneOffset.ofHours(8));
        }
    }
}
