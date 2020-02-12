package ds.communication.ftp;

import java.net.Socket;

public class FTPClient {

    //connect to the specified ip and port
    public FTPClient(String ipAddress, int port, String fileName) throws Exception {
        Socket serverSock = new Socket(ipAddress, port);

        System.out.println("Connecting...");
        Thread t = new Thread(new DataReceivingOperation(serverSock, fileName));
        t.start();
    }
}
