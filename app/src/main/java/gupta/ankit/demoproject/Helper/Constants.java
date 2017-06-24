package gupta.ankit.demoproject.Helper;


public class Constants {


    public static final class DATABASE {

        public static final String DB_NAME = "deals";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "deal";

        public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;

        public static final String GET_DEALS_QUERY = "SELECT * FROM " + TABLE_NAME;

        public static final String PRODUCT_ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String IMAGE_URL = "image";
        public static final String PHOTO = "photo";


        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + PRODUCT_ID + " INTEGER PRIMARY KEY not null," +
                TITLE + " TEXT not null," +
                DESCRIPTION + " TEXT not null," +
                IMAGE_URL + " TEXT not null," +
                PHOTO + " blob not null)";
    }

    public static final class REFERENCE {
        public static final String DEAL = Config.PACKAGE_NAME + "deal";
    }

    public static final class Config {
        public static final String PACKAGE_NAME = "gupta.ankit.demoproject";
    }
}
