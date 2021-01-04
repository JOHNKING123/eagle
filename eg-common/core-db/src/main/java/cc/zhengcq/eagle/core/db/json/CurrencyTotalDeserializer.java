package cc.zhengcq.eagle.core.db.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyTotalDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            BigDecimal value = new BigDecimal(jsonParser.getText());
            BigDecimal oneHundred = new BigDecimal(100);
            value = value.multiply(oneHundred);
            return value.longValue();
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
