package com.huo.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huo.order.entity.PayOrder;
import com.huo.order.entity.PayOrderRecord;
import com.huo.order.entity.UserCourseOrder;
import com.huo.order.mapper.OrderDao;
import com.huo.order.mapper.PayOrderDao;
import com.huo.order.mapper.PayOrderRecordDao;
import com.huo.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PayOrderDao payOrderDao;
    @Autowired
    private PayOrderRecordDao payOrderRecordDao;

    @Override
    public void saveOrder(String orderNo, String user_id, String course_id, String activity_course_id, String source_type) {
        UserCourseOrder userCourseOrder=new UserCourseOrder();
        userCourseOrder.setOrderNo(orderNo);
        userCourseOrder.setUserId(user_id);
        userCourseOrder.setCourseId(course_id);
        userCourseOrder.setActivityCourseId(Integer.parseInt(activity_course_id));
        userCourseOrder.setSourceType(source_type);
        userCourseOrder.setStatus(0);
        userCourseOrder.setIsDel(0);
        userCourseOrder.setCreateTime(new Date());
        userCourseOrder.setUpdateTime(new Date());
        orderDao.insert(userCourseOrder);

    }

    @Override
    public Integer updateOrder(String orderNo, int status) {
        UserCourseOrder order=new UserCourseOrder();
        order.setStatus(status);

        QueryWrapper q=new QueryWrapper();
        q.eq("order_no",orderNo);

        return orderDao.update(order,q);
    }

    @Override
    public Integer deleteOrder(String orderNo) {
        QueryWrapper q=new QueryWrapper<>();
        q.eq("order_no",orderNo);

        return orderDao.delete(q);
    }

    @Override
    public List<UserCourseOrder> getOKOrderCourseIds(Integer userId) {
        QueryWrapper q=new QueryWrapper<>();
        q.eq("user_id",userId);
        return orderDao.selectList(q);
    }

    @Override
    public void saveOrderInfo(PayOrder payOrder) {
        payOrderDao.insert(payOrder);
    }

    @Override
    public void saveOrderRecord(PayOrderRecord payOrderRecord) {
        payOrderRecordDao.insert(payOrderRecord);
    }
}
