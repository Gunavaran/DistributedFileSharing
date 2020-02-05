package ds.utils;

import java.net.InetAddress;

public class Constants {

    public static final String REG_FORMAT = "REG %s %s %s";
    public static final String UNREG_FORMAT = "UNREG %s %s %s";
    public static final String ECHO_FORMAT = "ECHO %s %s %s";

    public static final String LEAVE_FORMAT = "LEAVE %s %s";
    public static final String LEAVEOK_FORMAT = "LEAVEOK %s";

    public static final String JOIN_FORMAT = "JOIN %s %s";
    public static final String JOINOK_FORMAT = "JOINOK %s";

    public static final String MSG_FORMAT = "%04d %s";
    public static final int BS_SERVER_PORT = 55555;
//    public static final String BS_SERVER_IP = "192.168.8.164";
    public static final String BS_SERVER_IP = "127.0.0.1";
    public static final int BSSERVER_TIMEOUT = 10000;

}
