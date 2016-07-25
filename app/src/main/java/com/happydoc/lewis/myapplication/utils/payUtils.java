package com.happydoc.lewis.myapplication.utils;

import com.avos.avoscloud.AVObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by LewisMS on 2/21/2016.
 */
public class payUtils {
    private static String SELLER="liaomf-88@163.com";
    private static String PARTNER="2088121081076080";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKNOc6m1mIYfmNHRJ5irSmoJQNw6GuhY/Lv9KX1Tv1HlUgrJ/GgaMwbEQpr4YHhfyxX4rDBtexx+TtKWNVdO7DBdpmQNlgEM/K4U4maVWOKa+6RG/M+pXkDtzm4h6dMKdDWIFFocPOeMyT5FaEl7//Oi2Ou7MCKvdaogvpNQeSSVAgMBAAECgYAM96nPjA/0doXYA1e1k8Rw7xMraaHBWW+YLd4hiYjjo+YD3U4hphLvMp5nV9mxNUoXEIv7vHXOKWlw52SM7kNq40J48KBdsWw9lAq/X6k3hxaqugDE9otuVHLIsFIWE10fEq+0Xr5RA7edIZUTSwv4dPBUup0q/jmhSNvteyoy8QJBANZ/Y6R3p8JJai5dwxABun1FJjRDsgTQWhd64LzvV3+7hZUc+KUYluN2BS+bmf9Y3MC29Bjn2VniyoJupdsEUDcCQQDC5288x9oW2qsxFB00PdAk4q1D8Nphb45LvopKRxsNate02LsSp/ee3YMDMch4joRwwOB3fVX1H/uiYoF5zROTAkEAxnDOZ38HpfgzkrfN2cYvmOtbX+jtDgUQBD77IfAvvA1plT1bT6oDFma+f3/uJc05VdUs2MnOTJlDjw38xTYlWwJAGrM9x+PQ+qSGIzkQLyHwq7zp447RO9vecH3vz/EhwZrLZhjyt8GO6JKL6hodznYnqpKhrXzcE7KH9FzuQ9NWRQJAEuXT0irjwnZBbMcloLt62Uz9qSyPtOyqqCwRj7+39IRbMEbntI3sTUc2EThOvCeQEXflWgtJDCdi0bd2VJfjFg==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_CHECK_FLAG = 2;
    public static String getRequestString(AVObject doctor,String subject, String body, String price){
        String orderInfo=getOrderInfo(doctor,subject,body,price);
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        return payInfo;
    }
    public static String getOutTradeNo(String string) {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        key=string+key;
        return key;
    }
    private static String getOrderInfo(AVObject doctor,String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo(doctor.getObjectId()) + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://stg-hidoc.leanapp.cn/notify" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    private static String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }
    private static String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
