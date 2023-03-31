package com.huo.order.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class SmsService {

    @Value("${ali.sms.signName}")
    private String signName;
    @Value("${ali.sms.templateCode}")
    private String templateCode;
    @Value("${ali.sms.assessKeyId}")
    private String accessKeyId;
    @Value("${ali.sms.assessKeySecret}")
    private String assessKeySecret;

    public void sendSms(String phoneNumber,String courseName) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, assessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);

        request.putQueryParameter("TemplateParam", "{\"phone\":\"" + phoneNumber + "\",\"courseName\":\""+courseName+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            /*String jsonStr = response.getData();
            System.out.println("jsonStr = " + jsonStr);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
