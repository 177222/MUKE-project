package com.huo.course.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
//复用其他模块的功能，远程调用
@Component
@FeignClient(name = "MK-order",path = "order")
public interface OrderRemoteService {
    @GetMapping("getOKOrderCourseIds")
    List<Object> getOKOrderCourseIds( @RequestParam(value = "userid") Integer userid );
}
