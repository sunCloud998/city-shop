package com.yidian.shop.utils;

import com.yidian.shop.annotation.LogFieldInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @function:
 * @description: BigDecimalSerializer.java
 * @date: 2021/07/12
 * @author: sunfayun
 * @version: 1.0
 */
@Slf4j
public class CommonUtil {

    /**
     * 把以逗号分隔的ids字符串，转为list，因为用的比较频繁，所以抽一个工具类出来
     * String --> List<Integer>
     */
    public static List<Integer> covertIdStr(String ids) {
        if (StringUtils.isBlank(ids)) {
            //空的时候返回一个空的List，避免NPE异常
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(ids.replace("，", ",").replace("_", ",").replace("[", "").replace("]", ""), ","))
                .map(StringUtils::trim)//trim一下
                .filter(StringUtils::isNumeric)//过滤掉不能转成整数数的串
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * 上面那个函数的反函数，把idList转为字符串,逗号拼接的形式
     * <p>
     * List<Integer> --> String
     */
    public static String covertIdList2Str(Collection<Integer> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return "";
        }
        return StringUtils.join(idList, ",");
    }

    /**
     * 把以逗号分隔的ids字符串，转为list，因为用的比较频繁，所以抽一个工具类出来
     * <p>
     * String --> List<Long>
     */
    public static List<Long> covertIdStr2Long(String ids) {
        if (StringUtils.isBlank(ids)) {
            //空的时候返回一个空的List，避免NPE异常
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(ids.replace("，", ",").replace("_", ",").replace("[", "").replace("]", ""), ","))
                .map(StringUtils::trim)//trim一下
                .filter(StringUtils::isNumeric)//过滤掉不能转成整数数的串
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * 把以逗号分隔的ids字符串，转为list,数字StringList
     * <p>
     * String -->  List<String> 转出的String是数值类型的
     */
    public static List<String> covertNumberIdStr(String ids) {
        if (StringUtils.isBlank(ids)) {
            //空的时候返回一个空的List，避免NPE异常
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(ids.replace("，", ",").replace("_", ",").replace("[", "").replace("]", ""), ","))
                .map(StringUtils::trim)//trim一下
                .filter(StringUtils::isNumeric)//过滤掉不能转成整数数的串
                .map(String::new)
                .collect(Collectors.toList());
    }

    /**
     * 切割String为多个
     * <p>
     * String --> List<String>
     */
    public static List<String> convertStr2SListStr(String str) {
        if (StringUtils.isBlank(str)) {
            return new ArrayList<>();
        }
        return Arrays.stream(StringUtils.split(str.replace("，", ","), ","))
                .map(StringUtils::trim)//trim一下
                .map(String::toString).collect(Collectors.toList());
    }

    /**
     * 上面那个函数的虫子啊，把stringList转为字符串,逗号拼接的形式
     */
    public static String covertStringList2Str(Collection<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return "";
        }
        return StringUtils.join(idList, ",");
    }

    /**
     * 拼接集合里面的字符
     */
    public static String convertCollection2String(Collection collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return StringUtils.EMPTY;
        }
        return StringUtils.join(collection, ",");
    }

    /**
     * 去除字符串首尾逗号，类似：,1,2,3,的格式，去除以后方法返回1,2,3
     *
     * @param source
     * @param element
     * @return
     */
    public static String trimFirstAndLastChar(String source, char element) {
        if (StringUtils.isBlank(source)) {
            return StringUtils.EMPTY;
        }
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do {
            int beginIndex = source.indexOf(element) == 0 ? 1 : 0;
            int endIndex = source.lastIndexOf(element) + 1 == source.length() ? source.lastIndexOf(element) : source.length();
            source = source.substring(beginIndex, endIndex);
            beginIndexFlag = (source.indexOf(element) == 0);
            endIndexFlag = (source.lastIndexOf(element) + 1 == source.length());
        } while (beginIndexFlag || endIndexFlag);
        return source;
    }

    /**
     * 获取字符串中满足正则的所有字符串
     *
     * @param content
     * @param reg
     * @return
     */
    public static List<String> getChecks(String content, String reg) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(reg)) {
            return new ArrayList<>();
        }
        Pattern r = Pattern.compile(reg);
        Matcher m = r.matcher(content);
        List<String> result = new ArrayList<>();
        while (m.find()) {
            result.add(m.group());
        }
        return result;
    }

    /**
     * 获取字符串中满足正则的所有字符串
     *
     * @param content
     * @param reg
     * @param group   需要捕获组的索引
     * @return
     */
    public static List<String> getChecks(String content, String reg, int group) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(reg)) {
            return new ArrayList<>();
        }
        Pattern r = Pattern.compile(reg);
        Matcher m = r.matcher(content);
        List<String> result = new ArrayList<>();
        while (m.find()) {
            if (m.groupCount() >= group) {
                result.add(m.group(group));
            }
        }
        return result;
    }

    /**
     * 获取字符串中满足正则的所有字符串
     *
     * @param content
     * @param reg
     * @return
     */
    public static boolean checkReg(String content, String reg) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(reg)) {
            return false;
        }
        Pattern r = Pattern.compile(reg);
        Matcher m = r.matcher(content);
        return m.find();
    }

    public static boolean hasChinese(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        return text.length() != text.getBytes(StandardCharsets.UTF_8).length;
    }

    private static boolean isEmojiCharacter(int codePoint) {
        return (codePoint >= 0x2600 && codePoint <= 0x27BF) // 杂项符号与符号字体
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x2000 && codePoint <= 0x200F)//
                || (codePoint >= 0x2028 && codePoint <= 0x202F)//
                || codePoint == 0x205F //
                || (codePoint >= 0x2065 && codePoint <= 0x206F)//
                /* 标点符号占用区域 */
                || (codePoint >= 0x2100 && codePoint <= 0x214F)// 字母符号
                || (codePoint >= 0x2300 && codePoint <= 0x23FF)// 各种技术符号
                || (codePoint >= 0x2B00 && codePoint <= 0x2BFF)// 箭头A
                || (codePoint >= 0x2900 && codePoint <= 0x297F)// 箭头B
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)// 中文符号
                || (codePoint >= 0xD800 && codePoint <= 0xDFFF)// 高低位替代符保留区域
                || (codePoint >= 0xE000 && codePoint <= 0xF8FF)// 私有保留区域
                || (codePoint >= 0xFE00 && codePoint <= 0xFE0F)// 变异选择器
                || codePoint >= 0x10000; // Plane在第二平面以上的，char都不可以存，全部都转
    }

    /**
     * 比较字符串集合是否包含某个字符串 ，不区分大小写
     *
     * @param list
     * @param compareStr
     * @return
     */
    public static boolean containIgnoreCase(List<String> list, String compareStr) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        for (String s : list) {
            if (StringUtils.equalsIgnoreCase(s, compareStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除重复数据，不同于removeAll的是重复数据只会删除第一个
     *
     * @param collection
     * @param remove
     * @param <E>
     * @return
     */
    public static <E> List<E> removeAllFirst(final Collection<E> collection, final Collection<?> remove) {
        final List<E> list = new ArrayList<>();
        Set<E> delete = new HashSet<>();
        for (final E obj : collection) {
            if (remove.contains(obj) && !delete.contains(obj)) {
                delete.add(obj);
                continue;
            }
            list.add(obj);
        }
        return list;
    }

    /**
     * 获取图片相对路径
     *
     * @param imgPath
     * @return
     */
    public static String getImgRelativePath(String imgPath) {
        if (StringUtils.isEmpty(imgPath)) {
            return "";
        }
        if (imgPath.contains("http")) {
            int i = imgPath.indexOf("/news");
            return StringUtils.substring(imgPath, i, imgPath.length());
        } else {
            return imgPath;
        }
    }

    /**
     * 将ip 地址转换成数字
     *
     * @param ipAddress 传入的ip地址
     * @return 转换成数字类型的ip地址
     */
    public static Long getIpConvertNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 判断某个对象是否在string里
     *
     * @param target
     * @param source
     * @return
     */
    public static boolean contains(String target, Object source) {
        Set<String> set = new HashSet<>(Arrays.asList(target.split(",")));
        return set.contains(String.valueOf(source));
    }


    /**
     * 对比2个对象之间的差异
     *
     * @param old    原始数据
     * @param update 变更后的数据
     * @param isAdd  用来确定是否是新增数据
     */
    public static String objCompare(Object old, Object update, boolean isAdd) {
        if (old == null || update == null || old.getClass() != update.getClass()) {
            return "";
        }
        List<Field> compareList = new ArrayList<>();
        // 有些类会继承
        Class<?> tempClass = old.getClass();
        // 当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null) {
            Field[] fields = tempClass.getDeclaredFields();
            // 找到所有需要对比的字段
            for (Field f : fields) {
                Annotation[] annotations = f.getAnnotations();
                for (Annotation an : annotations) {
                    if (an instanceof LogFieldInfo) {
                        compareList.add(f);
                    }
                }
            }
            // 得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        StringBuilder sb = new StringBuilder();
        for (Field f : compareList) {
            f.setAccessible(true);
            LogFieldInfo an = f.getAnnotation(LogFieldInfo.class);
            try {
                Object o1 = f.get(old);
                Object o2 = f.get(update);
                if (isAdd) {
                    if (o2 != null && ObjectUtils.notEqual(o2, "")) {
                        sb.append(an.fieldName()).append("(").append(o2).append(")\n");
                    }
                } else {
                    if (f.getType().equals(String.class)) {
                        // String类型特殊处理（将""替换成null）
                        if ("".equals(o1)) {
                            o1 = null;
                        }
                        if ("".equals(o2)) {
                            o2 = null;
                        }
                    } else if (f.getType().equals(Boolean.class)) {
                        // Boolean类型特殊处理（null改为false）
                        if (o1 == null) {
                            o1 = false;
                        }
                        if (o2 == null) {
                            o2 = false;
                        }
                    }
                    // 判断是否有差异 修改前后都为null; 修改前为null 修改后为 ""  的去掉
                    if ((null == o1 && "".equals(o2)) || (null == o1 && null == o2) || (null != o1 && null == o2)) {
                        continue;
                    }
                    // 其中一个为null 则必然有差异
                    boolean oneNull = o1 == null;
                    boolean hasDiff = false;
                    if (!oneNull) {
                        if (f.getType().equals(List.class)) {
                            hasDiff = listModifyContrast((List<?>) o1, (List<?>) o2);
                        } else if (f.getType().equals(BigDecimal.class)) {
                            hasDiff = ((BigDecimal) o1).compareTo((BigDecimal) o2) != 0;
                        } else {
                            hasDiff = !o1.equals(o2);
                        }
                    }
                    if (oneNull || hasDiff) {
                        if (an.special()) {
                            sb.append(an.fieldName()).append("进行了修改\n");
                        } else {
                            sb.append(an.fieldName()).append("(");
                            sb.append("修改前:").append(o1).append(" | ");
                            sb.append("修改后:").append(o2).append(")\n");
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                log.error(old.getClass().getName() + "属性对比失败", e);
            }
        }
        return sb.toString();
    }

    /**
     * List数据变更对比
     *
     * @param newList newList
     * @param oldList oldList
     * @return Boolean
     */
    public static Boolean listModifyContrast(List<?> newList, List<?> oldList) {
        boolean flag;
        if (newList != null && oldList != null) {
            if (newList.size() != oldList.size()) {
                flag = true;
            } else {
                flag = !newList.containsAll(oldList);
            }
        } else {
            flag = newList != null || oldList != null;
        }
        return flag;
    }
}
