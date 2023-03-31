package com.huo.order.service;


import com.huo.order.entity.PayOrder;
import com.huo.order.entity.PayOrderRecord;
import com.huo.order.entity.UserCourseOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    /**
     * 生成订单
     * @param orderNo 订单编号
     * @param user_id 用户编号
     * @param course_id 课程编号
     * @param activity_course_id 活动课程编号
     * @param source_type 订单来源类型
     */
    void saveOrder(String orderNo, String user_id, String course_id, String activity_course_id, String source_type);
    /**
     * 修改订单状态
     * @param orderNo 订单编号
     * @param status 订单状态 0已创建 10已支付 20已完成 30已取消 40已过期
     * @return 受影响的行数
     */
    Integer updateOrder(String orderNo, int status);

    /**
     * 删除订单
     * @param orderNo 订单编号
     * @return 受影响的行数
     */
    Integer deleteOrder(@Param("orderNo") String orderNo);

    /**
     * 查询登录用户的全部订单
     * @param userId 用户编号
     * @return 所有订单
     */
    List<UserCourseOrder> getOKOrderCourseIds(Integer userId);

    /**
     * 保存订单信息
     * @param payOrder
     */
    void saveOrderInfo(PayOrder payOrder);

    /**
     * 保存订单记录信息
     * @param payOrderRecord
     */
    void saveOrderRecord(PayOrderRecord payOrderRecord);


}
