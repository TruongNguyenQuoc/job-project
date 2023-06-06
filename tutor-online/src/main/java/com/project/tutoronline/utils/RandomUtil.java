package com.project.tutoronline.utils;

public class RandomUtil {

    public static final String digits = "0123456789";

    public static String generateId(int n) {
        StringBuilder s = new StringBuilder(n);
        int y;
        for (y = 0; y < n; y++) {
            int index = (int) (digits.length() * Math.random());
            s.append(digits.charAt(index));
        }
        return s.toString();
    }

}
