package cc.zhengcq.eagle.core.db.base;


import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
public class BaseModel implements Serializable {

    /**
     * 主键id
     */
    @TableId(value = "idx", type = IdType.ASSIGN_UUID)
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = IdWorkerDeserializer.class)
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

    public void preInsert() {
        this.setCreateTime(new Date());

        this.setUpdateTime(new Date());

        if (this.createByMemberIdx != null && this.updateByMemberIdx == null) {
            this.setUpdateByMemberIdx(this.createByMemberIdx);
        }

        if (this.createByMemberIdx == null) {
            this.setCreateByMemberIdx(0L);
        }

        if (this.updateByMemberIdx == null) {
            this.setUpdateByMemberIdx(this.createByMemberIdx);
        }
    }

    public void preUpdate() {

        this.setUpdateTime(new Date());

        if (this.updateByMemberIdx == null) {
            this.setUpdateByMemberIdx(0L);
        }
    }
}
