package moviles.hotel.data;

import android.provider.BaseColumns;

public class HuespedContract {
    public static abstract class HuespedEntry implements BaseColumns {
        public static final String TABLE_NAME = "huesped";
        public static final String col_usuario = "user";
        public static final String col_password = "password";
        public static final String col_nombre = "name";
        public static final String col_email = "email";
    }
}
