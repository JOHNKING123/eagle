package cc.msyt.eagle.core.db.base;


import cc.msyt.eagle.core.db.json.IdWorkerDeserializer;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@SuppressWarnings("serial")
@Data
public class BaseModel implements Serializable {


    @TableId(value = "idx", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long idx;

    private Long version;

    private Integer status;

    private Long createByMemberIdx;

    private Date createTime;


    private Long updateByMemberIdx;

    private Date updateTime;


    private String remark;

    @JsonIgnore
    public String getIdx_() {
        return idx == null ? "" : idx.toString();
    }

    public void preInsert(){
        this.setCreateTime(new Date());

        this.setUpdateTime(new Date());

        if(this.createByMemberIdx!= null && this.updateByMemberIdx == null ){
            this.setUpdateByMemberIdx(this.createByMemberIdx);
        }

        if (this.createByMemberIdx == null) {
            this.setCreateByMemberIdx(0L);
        }

        if (this.updateByMemberIdx == null) {
            this.setUpdateByMemberIdx(this.createByMemberIdx);
        }
    }

    public void preUpdate(){

        this.setUpdateTime(new Date());

        if (this.updateByMemberIdx == null) {
            this.setUpdateByMemberIdx(0L);
        }
    }
}
