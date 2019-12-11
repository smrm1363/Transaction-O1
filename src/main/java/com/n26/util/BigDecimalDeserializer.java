package com.n26.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * This is a deserializer for BigDecimal Json
 */
public class BigDecimalDeserializer extends StdDeserializer<BigDecimal> {
    public BigDecimalDeserializer() {
        this(null);
    }
    public BigDecimalDeserializer(Class<?> vc) {
        super(vc);
    }
    @Override
    public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        BigDecimal input;
        try {
            input=new BigDecimal(node.asText());
        } catch (Exception e) {
            throw new DeserializeException("BigDecimal field deserialization failed");
        }
        return input;
    }
}