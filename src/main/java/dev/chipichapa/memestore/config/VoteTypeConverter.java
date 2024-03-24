package dev.chipichapa.memestore.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dev.chipichapa.memestore.domain.enumerated.VoteType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;

@Slf4j
public class VoteTypeConverter implements Converter<Integer, VoteType> {
    @Override
    @SneakyThrows
    public VoteType convert(Integer integer) {
        return VoteType.valueOf(integer);
    }
}
