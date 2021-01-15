package cc.zhengcq.eagle.core.db.entity;


import cc.zhengcq.eagle.core.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.ibatis.session.RowBounds;

import java.util.LinkedList;
import java.util.List;

public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {

    private static final long serialVersionUID = 1L;

    public Page() {
		/* 注意，传入翻页参数 */
    }

    public Page(int current, int size) {
        super(current, size);
    }

    public Page(int current, int size, String orderByField) {
        super(current, size);
        List<OrderItem> orderItems = new LinkedList<>();
        if (StringUtils.isNotEmpty(orderByField)) {
            orderByField = getCorrectOrderByForDB(orderByField);
            String[] tmpStrs = orderByField.split(",");
            for (String tmpStr : tmpStrs) {
                String[] tmpOrderStrs = tmpStr.split("[ ]+");
                OrderItem orderItem = new OrderItem();
                orderItem.setColumn(tmpOrderStrs[0]);
                if (tmpOrderStrs.length > 1 && (tmpOrderStrs[1].contains("desc")
                        || tmpOrderStrs[1].contains("DESC"))) {
                    orderItem.setAsc(false);
                } else {
                    orderItem.setAsc(true);
                }
                orderItems.add(orderItem);
            }
        }
        this.setOrders(orderItems);
    }

    public static String getCorrectOrderByForDB(String orderBy) {
        if (StringUtils.isBlank(orderBy)) {
            return null;
        } else {
            orderBy = orderBy.trim();
            if (orderBy.contains(" ASC")) {
                orderBy = orderBy.replace(" ASC", " asc");
            } else if (orderBy.contains(" DESC")) {
                orderBy = orderBy.replace(" DESC", " desc");
            }

            String[] data = null;
            if (orderBy.contains("TimeCn")) {
                orderBy = orderBy.split("Cn")[0];
            } else if (orderBy.contains("TimeEn")) {
                orderBy = orderBy.split("En")[0];
            }

            StringBuffer buffer = new StringBuffer();
            char[] ch = orderBy.toCharArray();
            String separator = "-";

            for (int i = 0; i < ch.length; ++i) {
                if (ch[i] >= 'A' && ch[i] <= 'Z') {
                    buffer.append(ch[i]);
                    if (i < ch.length - 2) {
                        buffer.append(separator);
                    }
                }
            }

            if (buffer.length() <= 0) {
                return orderBy;
            } else {
                String[] strs = buffer.toString().split(separator);
                if (null != strs && strs.length > 0) {
                    String line = "_";
                    String[] var7 = strs;
                    int var8 = strs.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        String str = var7[var9];
                        orderBy = orderBy.replace(str, line + str.toLowerCase());
                    }

                    return orderBy;
                } else {
                    return orderBy;
                }
            }
        }
    }


    public void setCurrent(int current) {
        super.setCurrent(current);
//        this.restRowBounds();
    }
    public void setSize(int size) {
        super.setSize(size);
//        this.restRowBounds();
    }

    public RowBounds getRowBounds() {
        RowBounds rowBounds = new RowBounds();
        rowBounds.setLimit(Long.valueOf(this.getSize()).intValue());
        rowBounds.setOffset(offsetCurrent(Long.valueOf(current).intValue(), Long.valueOf(size).intValue()));
        return rowBounds;
    }

    private static int offsetCurrent(int current, int size) {
        return current > 0 ? (current - 1) * size : 0;
    }
//    private void restRowBounds(){
//        super.setMaxLimit(this.getSize());
//        super.setO(offsetCurrent(this.getCurrent(), this.getSize()));
//    }

}
