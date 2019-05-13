package ch.heig.cashflow.models;

public class Currency {

    public static final String format(long decimal) {
        // TODO : Conversion decimal
        return String.format("%s.%s CHF", decimal / 100, Math.abs(decimal % 100));
    }
}
