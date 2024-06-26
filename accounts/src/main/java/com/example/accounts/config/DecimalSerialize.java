package com.example.accounts.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalSerialize extends StdSerializer<BigDecimal> {

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public DecimalSerialize() {
        super(BigDecimal.class);
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(decimalFormat.format(value));
    }
}
