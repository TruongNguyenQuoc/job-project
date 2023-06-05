package com.project.tutoronline.utils;

public class RandomUtil {

    public static final String alphas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String digits = "0123456789";

    public static String generateId(long text) {
        String format = "%1$06d";
        return String.format(format, text);
    }
}
