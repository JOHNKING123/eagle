package cc.msyt.eagle.core.db.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            BigDecimal value = new BigDecimal(jsonParser.getText());
            BigDecimal oneHundred = new BigDecimal(100);
            value = value.multiply(oneHundred);
            return value.intValue();
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
