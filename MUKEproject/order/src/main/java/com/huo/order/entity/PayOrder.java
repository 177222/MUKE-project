package com.huo.order.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "pay_order") //支付订单信息表
public class PayOrder implements Serializable {
    private static final long serialVersionUID = 777308790778683330L;

    /**
     * 主键
     */
    @Id
    private Long id;
    private String order_no;//订单号
    private String user_id;//用户ID
    private String product_id;//商品唯一标识
    private String product_name;//产品名称
    private Double amount;//金额
    private Integer count;//商品数量
    private String currency;//货币类型，cny-人民币
    private String channel;//支付渠道
    private Integer status;//1-未支付 2-支付成功 3-支付失败 -1-订单失效
    private Integer channel_status;//渠道中的状态码值
    private Integer order_type;//类型 1-购买课程 2-充值
    private Integer source;//支付来源 1-app 2-h5 3-pc
    private String client_ip;//用户支付IP
    private String buy_id;//购买账号id
    private String out_trade_no;//外部支付渠道交易号
    private Date created_time;
    private Date updated_time;
    private Date pay_time;
    private String extra;//附加字段，KV json，例如:{"mobile":13020202,"success_url":123}
    private String goods_order_no;//商品订单编号
    private Integer platform;//支付所使用的平台
    private Integer wx_type;//微信类型
}