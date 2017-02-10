package com.lynpo.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by fujw on 2016/12/20.
 * <p>
 * 用于 Android API level 不同而需要访问不同方法的工具类
 */

public class AndroidAPIUtil {

    static boolean getApplicationInfo(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.getPackageManager().getApplicationInfo(
                        packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES);
                return true;
            } else {
                context.getPackageManager().getApplicationInfo(
                        packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static Spanned fromHtml(String text) {
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(text);
        }
        return spanned;
    }

    public static void setStatusBarColor(Window window, int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(window.getContext(), colorResId));
        }
    }

    public static long getBlockSize(File file) {
        StatFs stat = new StatFs(file.getPath());
        long blockSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
        return blockSize;
    }

    public static long getAvailableBlocks(File file) {
        StatFs stat = new StatFs(file.getPath());
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isContextValid(Context context) {
        return !(context instanceof Activity) ||
                Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 ||
                !((Activity) context).isDestroyed();
    }
}
