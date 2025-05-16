package utils;

public class IntegerUtil {
    public static Integer[] getListIntegerToValue(int endValue) {
        return getListIntegerToValue(1, endValue);
    }

    public static Integer[] getListIntegerToValue(int startValue, int endValue) {
        if (startValue > endValue) return new Integer[0];
        Integer[] values = new Integer[endValue - startValue + 1];
        for (int i = 0; i <= endValue - startValue; i++) {
            values[i] = startValue + i;
        }
        return values;
    }

    public static Integer[] getListIntegerToValueReverse(int startValue, int endValue) {
        if (startValue > endValue) return new Integer[0];
        Integer[] values = new Integer[endValue - startValue + 1];
        for (int i = 0; i <= endValue - startValue; i++) {
            values[i] = endValue - i;
        }
        return values;
    }
}
