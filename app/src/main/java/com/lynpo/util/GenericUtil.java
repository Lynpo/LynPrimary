package com.lynpo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by fujw on 2017/1/5.
 *
 * 参考 {@link android.text.TextUtils#join(CharSequence, Iterable)}
 * TextUtils.join() 方法给 list 中的 id 成员添加符号分割并返回结果
 * return ids string like : "1,2,3,4"
 */

public class GenericUtil {

    public static <T> String join(String delimiter, ArrayList<T> list) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (T bean : list) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            try {
                Field field = bean.getClass().getField("id");
                sb.append(String.valueOf(field.getLong(bean)));
            } catch (NoSuchFieldException ignored) {
                return sb.toString();
            } catch (IllegalAccessException ignored) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

//    public static String joinComMessage(String delimiter, ArrayList<CommonRspMsg.Message> list) {
//        StringBuilder sb = new StringBuilder();
//        boolean firstTime = true;
//        for (CommonRspMsg.Message bean : list) {
//            if (firstTime) {
//                firstTime = false;
//            } else {
//                sb.append(delimiter);
//            }
//            sb.append(String.valueOf(bean.tagid));
//        }
//        return sb.toString();
//    }
//
//    public static String joinDrug(String delimiter, ArrayList<DrugBean> list) {
//        StringBuilder sb = new StringBuilder();
//        boolean firstTime = true;
//        for (DrugBean bean : list) {
//            if (firstTime) {
//                firstTime = false;
//            } else {
//                sb.append(delimiter);
//            }
//            sb.append(String.valueOf(bean.id));
//        }
//        return sb.toString();
//    }
//
//    public static String joinDisease(String delimiter, ArrayList<DiseaseBean> list) {
//        StringBuilder sb = new StringBuilder();
//        boolean firstTime = true;
//        for (DiseaseBean bean : list) {
//            if (firstTime) {
//                firstTime = false;
//            } else {
//                sb.append(delimiter);
//            }
//            sb.append(String.valueOf(bean.id));
//        }
//        return sb.toString();
//    }
//
//    public static String joinGuide(String delimiter, ArrayList<GuideItem> list) {
//        StringBuilder sb = new StringBuilder();
//        boolean firstTime = true;
//        for (GuideItem bean : list) {
//            if (firstTime) {
//                firstTime = false;
//            } else {
//                sb.append(delimiter);
//            }
//            sb.append(String.valueOf(bean.id));
//        }
//        return sb.toString();
//    }
//
//    public static String joinFavor(String delimiter, ArrayList<FavoriteBean> list) {
//        StringBuilder sb = new StringBuilder();
//        boolean firstTime = true;
//        for (FavoriteBean bean : list) {
//            if (firstTime) {
//                firstTime = false;
//            } else {
//                sb.append(delimiter);
//            }
//            sb.append(String.valueOf(bean.id));
//        }
//        return sb.toString();
//    }
//
//    public static String joinNews(String delimiter, ArrayList<NewsItem> list) {
//        StringBuilder sb = new StringBuilder();
//        boolean firstTime = true;
//        for (NewsItem bean : list) {
//            if (firstTime) {
//                firstTime = false;
//            } else {
//                sb.append(delimiter);
//            }
//            sb.append(String.valueOf(bean.id));
//        }
//        return sb.toString();
//    }
}
