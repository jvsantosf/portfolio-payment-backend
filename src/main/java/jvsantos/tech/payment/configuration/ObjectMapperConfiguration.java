package jvsantos.tech.payment.configuration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;

public class ObjectMapperConfiguration {

    @Bean
    @Primary
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configOverride(BigDecimal.class)
                .setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.STRING));

        return objectMapper;
    }

}
