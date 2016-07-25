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
    // �̻�˽Կ��pkcs8��ʽ
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKNOc6m1mIYfmNHRJ5irSmoJQNw6GuhY/Lv9KX1Tv1HlUgrJ/GgaMwbEQpr4YHhfyxX4rDBtexx+TtKWNVdO7DBdpmQNlgEM/K4U4maVWOKa+6RG/M+pXkDtzm4h6dMKdDWIFFocPOeMyT5FaEl7//Oi2Ou7MCKvdaogvpNQeSSVAgMBAAECgYAM96nPjA/0doXYA1e1k8Rw7xMraaHBWW+YLd4hiYjjo+YD3U4hphLvMp5nV9mxNUoXEIv7vHXOKWlw52SM7kNq40J48KBdsWw9lAq/X6k3hxaqugDE9otuVHLIsFIWE10fEq+0Xr5RA7edIZUTSwv4dPBUup0q/jmhSNvteyoy8QJBANZ/Y6R3p8JJai5dwxABun1FJjRDsgTQWhd64LzvV3+7hZUc+KUYluN2BS+bmf9Y3MC29Bjn2VniyoJupdsEUDcCQQDC5288x9oW2qsxFB00PdAk4q1D8Nphb45LvopKRxsNate02LsSp/ee3YMDMch4joRwwOB3fVX1H/uiYoF5zROTAkEAxnDOZ38HpfgzkrfN2cYvmOtbX+jtDgUQBD77IfAvvA1plT1bT6oDFma+f3/uJc05VdUs2MnOTJlDjw38xTYlWwJAGrM9x+PQ+qSGIzkQLyHwq7zp447RO9vecH3vz/EhwZrLZhjyt8GO6JKL6hodznYnqpKhrXzcE7KH9FzuQ9NWRQJAEuXT0irjwnZBbMcloLt62Uz9qSyPtOyqqCwRj7+39IRbMEbntI3sTUc2EThOvCeQEXflWgtJDCdi0bd2VJfjFg==";
    // ֧������Կ
    public static final String RSA_PUBLIC = "";
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_CHECK_FLAG = 2;
    public static String getRequestString(AVObject doctor,String subject, String body, String price){
        String orderInfo=getOrderInfo(doctor,subject,body,price);
        String sign = sign(orderInfo);
        try {
            /**
             * �����sign ��URL����
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
        // ǩԼ���������ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // ǩԼ����֧�����˺�
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // �̻���վΨһ������
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo(doctor.getObjectId()) + "\"";

        // ��Ʒ����
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // ��Ʒ����
        orderInfo += "&body=" + "\"" + body + "\"";

        // ��Ʒ���
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // �������첽֪ͨҳ��·��
        orderInfo += "&notify_url=" + "\"" + "http://stg-hidoc.leanapp.cn/notify" + "\"";

        // ����ӿ����ƣ� �̶�ֵ
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // ֧�����ͣ� �̶�ֵ
        orderInfo += "&payment_type=\"1\"";

        // �������룬 �̶�ֵ
        orderInfo += "&_input_charset=\"utf-8\"";

        // ����δ����׵ĳ�ʱʱ��
        // Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
        // ȡֵ��Χ��1m��15d��
        // m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
        // �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
        orderInfo += "&return_url=\"m.alipay.com\"";

        // �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
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
