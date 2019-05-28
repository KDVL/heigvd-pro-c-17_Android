package ch.heig.cashflow.utils;

public class Currency {

    public static final String format(long decimal) {
        // TODO : afficher toutjours 2 zero
        // TODO : Separateur de milliers
        return String.format("%s.%s CHF", decimal / 100, Math.abs(decimal % 100));
    }

    public long getLongValue(String value) {
        float f = Float.valueOf(value);
        long l = (long) (f * 100);
        return l;
    }

    public float getFloatValue(long value) {
        return (float) value / 100;
    }
}
