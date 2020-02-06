package ds.core;

import ds.communication.BootstrapClient;
import ds.communication.MessageBroker;
import ds.utils.Log;
import ds.utils.PropertiesFile;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Node {
    private String ip;
    private int port;
    private String username;
    private String[] fileList;
    private int fileCount;
    private BootstrapClient bsClient;
    private MessageBroker messageBroker;
    private Log log;

    public Node(String username) {
        log = new Log();
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            this.ip = InetAddress.getByName("localhost").getHostAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Could not find the host address");
        }

        this.port = getFreeport();
        this.username = username;
        this.fileCount = randomInteger(3, 5);
        this.fileList = new String[this.fileCount];
        this.bsClient = new BootstrapClient(this.log);
        String[] allFiles = PropertiesFile.getNodeProperty("files").split(",");
        for (int i = 0; i < this.fileCount; i++) {
            while (true) {
                String temp = allFiles[randomInteger(0, allFiles.length - 1)];
                if (!Arrays.asList(this.fileList).contains(temp)) {
                    this.fileList[i] = temp;
                    break;
                }
            }

        }

        try {
            this.messageBroker = new MessageBroker(ip, port, this.log);
        } catch (SocketException e) {
            throw new RuntimeException("Message Broker creation failed");
        }
        messageBroker.start();
        log.writeLog("Gnode initiated on IP :" + ip + " and Port :" + port + " and username :" + username);
    }

    //a socket for TCP/IP connection
    public int getFreeport() {
        try {
            ServerSocket socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
//            System.out.println("created socket port: " + port);
            socket.close();
            return port;
        } catch (IOException e) {
            throw new RuntimeException("Could not find a free port. Process failed");
        }
    }

    public void leaveNetwork(){
        this.unregisterFromBSServer();
        this.messageBroker.leave();
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void registerToBSServer() {
        List<InetSocketAddress> neighbors = bsClient.register(this.ip, this.port, this.username);
        if (neighbors != null) {
            for (InetSocketAddress neighbor : neighbors) {
                String neighborAddress = neighbor.getAddress().toString().substring(1);
                int neighborPort = neighbor.getPort();
                this.messageBroker.getRoutingTable().addNeighbour(neighborAddress, neighborPort, "username");
                this.messageBroker.sendJoin(neighborAddress, neighborPort);
            }
        }
    }

    public void unregisterFromBSServer() {
        bsClient.unregister(this.ip, this.port, this.username);
    }

    public void echoBSServer() {
        bsClient.echo(this.ip, this.port, this.username);
    }

    //creates a random number between min and max (including min and max)
    private int randomInteger(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String[] getFileList() {
        return fileList;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void printFileList() {
        System.out.println("Files are: ");
        for (String file : this.fileList) {
            System.out.print(file + " ");
        }
    }

    public MessageBroker getMessageBroker() {
        return messageBroker;
    }

    public void printlog(){
        this.log.printLog();
    }

}
