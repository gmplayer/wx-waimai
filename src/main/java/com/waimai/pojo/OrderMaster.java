package com.waimai.pojo;

import com.waimai.enums.OrderStatusEnum;
import com.waimai.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate  //自动更新updateTime
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    @Column(name="buyer_openid")
    private String buyerOpenId;
    private BigDecimal orderAmount;
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    private Integer payStatus = PayStatusEnum.WAIT_PAY.getCode();

    private Date createTime;
    private Date updateTime;

   // @Transient //可以使用该注解将该字段在对应数据库表持久化的时候忽略，因为表中没有该属性，这样使用从设计上讲不太合理，可以使用dto层来进行转换
    //private List<OrderDetail> detailList;

}
