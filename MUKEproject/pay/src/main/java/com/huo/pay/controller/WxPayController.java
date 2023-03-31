package com.huo.pay.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.huo.pay.commons.PayConfig;
import com.jfinal.kit.HttpKit;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 微信支付相关控制
 */
@RestController
@RequestMapping("pay")
@CrossOrigin
public class WxPayController {

    @GetMapping("createCode")
    public Object createCode(String courseid,String coursename,String price) throws Exception {
        coursename = new String(coursename.getBytes("ISO-8859-1"),"UTF-8");
        Map<String,String> mm = new HashMap();
        mm.put("appid", PayConfig.appid);  // 公众账号ID
        mm.put("mch_id",PayConfig.partner);// 商户号
        mm.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        mm.put("body",coursename); //商品简单描述
        mm.put("out_trade_no",WXPayUtil.generateNonceStr()); //随机生成的商户订单号
        mm.put("total_fee",price); // 订单金额,订单总金额，单位为分，只能为整数
        mm.put("spbill_create_ip","127.0.0.1"); // 终端IP
        mm.put("notify_url",PayConfig.notifyurl); //通知地址
        mm.put("trade_type","NATIVE"); //交易类型
        //System.out.println("商户信息：" + mm) ;

        //2.生成数字签名，并发商户信息转换成xml格式
        String xml = WXPayUtil.generateSignedXml(mm, PayConfig.partnerKey);
        //System.out.println("商户的xml信息：" + xml);

        //3.将xml数据发送给为微信支付平台，从而生成订单
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        // 发送请求，并返回一个xml格式的字符串
        String result = HttpKit.post(url, xml);

        //4.微信支付平台返回xml格式数据，将其转换成map格式并返回给前端
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
        resultMap.put("orderId",mm.get("out_trade_no"));
        return resultMap;
    }


    @GetMapping("checkOrderStatus")
    public Object checkOrderStatus(String orderId) throws Exception {
        //1.编写商户信息
        Map<String,String> mm = new HashMap();
        mm.put("appid", PayConfig.appid);  // 公众账号ID
        mm.put("mch_id",PayConfig.partner);// 商户号
        mm.put("out_trade_no", orderId);//商户订单号
        mm.put("nonce_str",WXPayUtil.generateNonceStr()); //随机字符串
        //2.生成数字签名
        String xml = WXPayUtil.generateSignedXml(mm, PayConfig.partnerKey);
        //3.发送查询请求给微信支付平台
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        // 查询订单状态的开始时间点
        long beginTime = System.currentTimeMillis();
        // 不停的去微信支付平台询问是否支付成功
        while(true) {
            //4.对微信支付平台返回的查询结果进行处理
            String result = HttpKit.post(url, xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);

            //已经支付成功，不再询问
            if(resultMap.get("trade_state").equalsIgnoreCase("SUCCESS")){
                return resultMap;
            }

            //超过30秒未支付，停止询问
            if( System.currentTimeMillis()- beginTime > 30000 ){
                return resultMap;
            }
            Thread.sleep(3000); //每隔3秒，询问一次微信支付平台
        }
    }

    @GetMapping("wxCallback")
    public String wxCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = null;
        try (InputStream inputStream = request.getInputStream();
             ByteArrayOutputStream outputStream=new ByteArrayOutputStream()){
            byte buffer[] = new byte[1024];
            int len = 0;
            while( (len = inputStream.read(buffer)) != -1 ){
                outputStream.write(buffer,0,len);
            }
            result = new String( outputStream.toByteArray() );
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("回调失败！");
        }
        //System.out.println("回调参数 = " + result);
        return result;
    }

}
