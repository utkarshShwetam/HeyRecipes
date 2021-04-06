package com.android.heyrecipes.Constants;

public class ConstantsValues {
    public static int TIMEOUT = 3000;
    public static final int CONNECTION_TIMEOUT=10;
    public static final int READ_TIMEOUT=2;
    public static final int WRITE_TIMEOUT=2;
    public static final int RECIPE_REFRESH_TIME= 60*60*12*30;

    public static String API_URL = "https://recipesapi.herokuapp.com/";

    public static String [] CATEGORIES_TYPE={"Breakfast","Chicken","Pasta",
            "Noodles","Barbeque","Cake","Grilled"};

    public static String [] CATEGORIES_TYPE_IMAGES={"breakfast","chicken","pasta",
            "noodles","barbeque","cake","grilled"};
}
