package com.yidian.shop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dev.
 */
public class RegexUtils {

    public static final String REGEX_EMAIL = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";

    public static final String REGEX_EMAIL_QQ = "\\w+@[qQ][qQ]\\.[a-z]+(\\.[a-z]+)?";

    public static final String REGEX_ID = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";

    /**
     * 这个是手机的正则
     */
    public static final String REGEX_MOBILE = "(\\+\\d+)?1[3456789]\\d{9}$";

    /**
     * 这个是座机的正则
     */
    public static final String REGEX_PHONE = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";

    public static final String REGEX_DIGITAL = "\\-?[1-9]\\d+";

    public static final String REGEX_DECIMAL = "\\-?[1-9]\\d+(\\.\\d+)?";

    public static final String REGEX_BLANK = "\\s+";

    public static final String REGEX_CHINESE = "^[\u4E00-\u9FA5]+$";

    public static final String REGEX_CHINESE_NUMBER_CHAR = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";

    /**
     * 校验是否是金额，且只保留2位小数
     */
    public static final String REGEX_MONEY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";

    /**
     * 是否是全数字
     */
    public static final String REGEX_NUM = "^[0-9]*$";

    /**
     * 包含小数
     */
    public static final String REGEX_DOUBLE = "^[0-9]*$";

    /**
     * 仅包含 字母 和 数字
     */
    public static final String LETTER_OR_NUMBER = "^[a-z0-9A-Z]+$";

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，
     *              xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 验证qqEmail
     *
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，
     *              xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkQqEmail(String email) {
        return Pattern.matches(REGEX_EMAIL_QQ, email);
    }


    /**
     * 是否是全数字
     *
     * @param number
     * @return
     */
    public static boolean checkNumber(String number) {
        return Pattern.matches(REGEX_NUM, number);
    }

    /**
     * 校验包含字母 或  数字
     *
     * @param str
     * @return
     */
    public static boolean checkLetterOrNumber(String str) {
        return Pattern.matches(LETTER_OR_NUMBER, str);
    }

    /**
     * 检查金额，且只能保2位小数
     *
     * @param str
     * @return
     */
    public static boolean isMoney(String str) {
        return Pattern.matches(REGEX_MONEY, str);
    }

    /**
     * 判断字符串是否表示数值（包括整数和小数）
     *
     * @param res
     * @return
     */
    public static boolean isNumeric(String res) {
        try {
            double db = Double.parseDouble(res);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        return Pattern.matches(REGEX_ID, idCard);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>
     *               移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *               </p>
     *               <p>
     *               联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *               </p>
     *               <p>
     *               电信的号段：133、153、180（未启用）、189
     *               </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p>
     *              <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
     *              的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
     *              </p>
     *              <p>
     *              <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。
     *              </p>
     *              <p>
     *              <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
     *              </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        return Pattern.matches(REGEX_PHONE, phone);
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        return Pattern.matches(REGEX_DIGITAL, digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        return Pattern.matches(REGEX_DECIMAL, decimals);
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        return Pattern.matches(REGEX_BLANK, blankSpace);
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 验证中文、数组、字母
     *
     * @param param
     * @return
     */
    public static boolean checkChineseOrNumberOrChar(String param) {
        return Pattern.matches(REGEX_CHINESE_NUMBER_CHAR, param);
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex, birthday);
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
     *            http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * <pre>
     * 获取网址 URL 的一级域名
     * http://www.zuidaima.com/share/1550463379442688.htm ->> zuidaima.com
     * </pre>
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        String regx = "(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)";
        Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        // 获取完整的域名
        //        // Pattern
        //        // p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)",
        //        // Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        return matcher.group();
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }

    /*
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     */

    /**
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

}
