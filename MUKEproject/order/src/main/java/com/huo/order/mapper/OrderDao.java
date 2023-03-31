package com.huo.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huo.order.entity.UserCourseOrder;
import org.springframework.stereotype.Service;


@Service
public interface OrderDao extends BaseMapper<UserCourseOrder> {
}
