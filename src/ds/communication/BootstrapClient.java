package ds.communication;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import ds.utils.Constants;
import ds.utils.Log;


public class BootstrapClient {
    private DatagramSocket socket;
    private String BSServerIPAddress;
    private int BSServerPort;
    private Log log;

    public BootstrapClient(Log log) {
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.BSServerIPAddress = Constants.BS_SERVER_IP;
        this.BSServerPort = Constants.BS_SERVER_PORT;
        this.log = log;
    }

    public List<InetSocketAddress> register(String ipAddress, int port, String username) {
        String regMessage = String.format(Constants.REG_FORMAT, ipAddress, port, username);
        regMessage = String.format(Constants.MSG_FORMAT, regMessage.length() + 5, regMessage);
        return processBSResponse(sendOrReceive(regMessage));
    }

    public void unregister(String ipAddress, int port, String username){
        String unregMessage = String.format(Constants.UNREG_FORMAT, ipAddress, port, username);
        unregMessage = String.format(Constants.MSG_FORMAT, unregMessage.length() + 5, unregMessage);
        processBSUnregisterResponse(sendOrReceive(unregMessage));
    }

    public void echo(String ipAddress, int port, String username){
        String echoMessage = String.format(Constants.ECHO_FORMAT, ipAddress, port, username);
        echoMessage = String.format(Constants.MSG_FORMAT, echoMessage.length() + 5, echoMessage);
        sendOrReceive(echoMessage);
    }

    private List<InetSocketAddress> processBSResponse(String response) {
        StringTokenizer stringToken = new StringTokenizer(response, " ");
//        System.out.println(response);
        String length = stringToken.nextToken();

        String status = stringToken.nextToken();

        if (!"REGOK".equals(status)) {
            throw new IllegalStateException("REGOK" + " not received");
        }

        int nodesCount = Integer.parseInt(stringToken.nextToken());

        List<InetSocketAddress> neighborNodes = null;

        switch (nodesCount) {
            case 0:
                System.out.println("Success. This is the first node");
                neighborNodes = new ArrayList<>();
                break;

            case 1:
                System.out.println("Success. Number of neighbor nodes = 1");

                neighborNodes = new ArrayList<>();

                while (stringToken.hasMoreTokens()) {
                    neighborNodes.add(new InetSocketAddress(stringToken.nextToken(),
                            Integer.parseInt(stringToken.nextToken())));
                }
                break;

            case 2:
                System.out.println("Success. Number of neighbor nodes = 2");

                neighborNodes = new ArrayList<>();

                while (stringToken.hasMoreTokens()) {
                    neighborNodes.add(new InetSocketAddress(stringToken.nextToken(),
                            Integer.parseInt(stringToken.nextToken())));
                }
                break;

            case 9999:
                System.out.println("Failed, there is some error in the command");
                break;
            case 9998:
                System.out.println("Failed, already registered to you, unregisterFromBSServer first");
                break;
            case 9997:
                System.out.println("Failed, registered to another user, try a different IP and port");
                break;
            case 9996:
                System.out.println("Failed, can’t register. BS full");
                break;
            default:
                throw new IllegalStateException("Invalid status code");
        }

        return neighborNodes;
    }

    private boolean processBSUnregisterResponse(String response){

        StringTokenizer stringTokenizer = new StringTokenizer(response, " ");

        String length = stringTokenizer.nextToken();
        String status = stringTokenizer.nextToken();

        if (!"UNROK".equals(status)) {
            throw new IllegalStateException("UNROK not received");
        }

        int code = Integer.parseInt(stringTokenizer.nextToken());

        switch (code) {
            case 0:
                log.writeLog("Successfully unregistered from BSServer");
                return true;

            case 9999:
                System.out.println("Error while un-registering. " +
                        "IP and port may not be in the registry or command is incorrect");
            default:
                return false;
        }
    }

    public String sendOrReceive(String message){
        DatagramPacket sendPacket = null;
        try {
            sendPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(BSServerIPAddress), BSServerPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            socket.setSoTimeout(Constants.BSSERVER_TIMEOUT);
            socket.send(sendPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Sending register message to BS Server failed");
        }

        byte[] buff = new byte[65536];
        DatagramPacket receivedPacket = new DatagramPacket(buff, buff.length);
        try {
            socket.receive(receivedPacket);
        } catch (IOException e) {
            System.out.println("Could not receive packet from the BS Server");
//            return "NULL";
        }

//        System.out.println(receivedPacket.getAddress().getHostAddress());
        log.writeLog(new String (buff, 0 , buff.length));
        return new String(receivedPacket.getData(), 0, receivedPacket.getLength());

    }

    public String getBSServerIPAddress() {
        return BSServerIPAddress;
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}

