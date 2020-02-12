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

}
