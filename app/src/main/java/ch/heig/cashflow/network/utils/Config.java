/**
 * Network config
 *
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.network.utils;

public final class Config {
    public final static String BASE_URL = "https://192.168.1.148:5000/api";

    public final static String AUTH = BASE_URL + "/auth";
    public final static String AUTH_SIGNIN =  AUTH + "/signin";
    public final static String AUTH_SIGNUP =  AUTH + "/signup";
    public final static String AUTH_VERIF =  AUTH + "/validate";

    public final static String TRANSACTIONS = BASE_URL + "/transactions";
    public final static String TRANSACTIONS_TYPE = TRANSACTIONS + "/type/";
    public final static String TRANSACTIONS_TYPE_EXPENSE = TRANSACTIONS + "/type/EXPENSE";
    public final static String TRANSACTIONS_TYPE_INCOME = TRANSACTIONS + "/type/INCOME";
    public final static String TRANSACTION = TRANSACTIONS + "/";

    public final static String CATEGORIES = BASE_URL + "/categories";
    public final static String CATEGORIES_TYPE = CATEGORIES + "/type/";
    public final static String CATEGORY = CATEGORIES + "/";

    public final static String DASHBOARD = BASE_URL + "/dashboard";

}