package cc.zhengcq.eagle.core.db.json;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cc.zhengcq.eagle.core.common.utils.StringUtils;

import java.io.IOException;

public class LongToBooleanSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        try{
            if(value == null){
                gen.writeBoolean(false);
            } else {
                if(!StringUtils.isZero(value)){
                    gen.writeBoolean(true);
                } else {
                    gen.writeBoolean(false);
                }
            }
        }catch (Exception ex){
            gen.writeBoolean(false);
        }
    }
}
