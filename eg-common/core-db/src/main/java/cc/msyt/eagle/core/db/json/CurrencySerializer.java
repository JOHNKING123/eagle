package cc.msyt.eagle.core.db.json;

import cc.msyt.eagle.core.common.utils.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class CurrencySerializer  extends ToStringSerializer {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        try{
            if(value == null){
                gen.writeString("0.00");
            }else{
                BigDecimal valTmp = BigDecimal.ZERO;
                if(value instanceof Integer){
                    Integer val = (Integer)value;
                    valTmp = new BigDecimal(val);
                }else if(value instanceof Long){
                    Long val = (Long)value;
                    valTmp = new BigDecimal(val);
                }

                BigDecimal oneHundred = new BigDecimal(100);
                valTmp = valTmp.divide(oneHundred,2,BigDecimal.ROUND_HALF_UP);
                gen.writeString(valTmp.toString());
            }
        }catch (Exception ex){
            gen.writeString("");
        }
    }
}
