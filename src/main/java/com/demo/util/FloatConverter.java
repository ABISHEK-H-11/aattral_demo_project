package com.demo.util;

public class FloatConverter {

    public static float convertRegistersToFloat(int high, int low) {
        int combined = (high << 16) | (low & 0xFFFF);
        return Float.intBitsToFloat(combined);
    }
}