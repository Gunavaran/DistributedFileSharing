package ds.utils;

import java.net.InetAddress;

public class Constants {

    public static final String REG_FORMAT = "REG %s %s %s";
    public static final String UNREG_FORMAT = "UNREG %s %s %s";
    public static final String ECHO_FORMAT = "ECHO %s %s %s";

    public static final String LEAVE_FORMAT = "LEAVE %s %s";
    public static final String LEAVEOK_FORMAT = "LEAVEOK %s %s";

    public static final String JOIN_FORMAT = "JOIN %s %s";
    public static final String JOINOK_FORMAT = "JOINOK %s %s";

    public static final String SEARCH_FORMAT = "SER %s %s %s %s";
    public static final String SEARCHOK_FORMAT = "SEROK %s %s %s %s %s";

    public static final String MSG_FORMAT = "%04d %s";
    public static final int BS_SERVER_PORT = 55555;
//    public static final String BS_SERVER_IP = "192.168.8.164";

    public static final String ENCODE_CLASS = "UTF-8";

    public static final String BS_SERVER_IP = "127.0.0.1";
    public static final int BSSERVER_TIMEOUT = 10000;

    public static final int HOP_COUNT = 5;
    public static final int FILE_DOWNLOAD_TIMEOUT = 2000;

    public static final String ADDRESS_KEY_FORMAT = "%s:%s";

    public static final int SEARCH_TIMEOUT = 3000;

    public static final String[] QUERY_LIST = {"Twilight", "Jack", "American Idol", "Happy Feet",
            "Twilight saga", "Happy Feet", "Happy Feet", "Feet", "Happy Feet", "Twilight", "Windows",
            "Happy Feet", "Mission Impossible", "Twilight", "Windows 8", "The", "Happy", "Windows 8",
            "Happy Feet", "Super Mario", "Jack and Jill", "Happy Feet", "Impossible", "Happy Feet",
            "Turn Up The Music", "Adventures of Tintin", "Twilight saga", "Happy Feet", "Super Mario",
            "American Pickers", "Microsoft Office 2010", "Twilight", "Modern Family", "Jack and Jill", "Jill",
            "Glee", "The Vampire Diarie", "King Arthur", "Jack and Jill", "King Arthur", "Windows XP", "Harry Potter",
            "Feet", "Kung Fu Panda", "Lady Gaga", "Gaga", "Happy Feet", "Twilight", "Hacking", "King"};

    public static final String[] QUERY_LIST_TEST = {"Twilight", "Jack", "American Idol"};

}
