package ch.heig.cashflow.network;

public final class Config {

    protected final static String BASE_URL = "https://192.168.1.148:5000/api";

    protected final static String AUTH = BASE_URL + "/auth";
    protected final static String AUTH_SIGNIN =  AUTH + "/signin";
    protected final static String AUTH_SIGNUP =  AUTH + "/signup";

    protected final static String TRANSACTIONS = BASE_URL + "/transactions";
    protected final static String TRANSACTIONS_TYPE = TRANSACTIONS + "/type";

    protected final static String CATEGORIES = BASE_URL + "/categories";
    protected final static String CATEGORIES_TYPE = CATEGORIES + "/type";

}
