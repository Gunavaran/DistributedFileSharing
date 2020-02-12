package ds.core;

import ds.communication.BootstrapClient;
import ds.communication.MessageBroker;
import ds.communication.ftp.FTPClient;
import ds.communication.ftp.FTPServer;
import ds.utils.Constants;
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
    private BootstrapClient bsClient;
    private MessageBroker messageBroker;
    private FileManager fileManager;
    private Log log;
    private FTPServer ftpServer;
    private SearchManager searchManager;
    private int ftpServerPort;

    public Node(String username) throws Exception {
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
        this.bsClient = new BootstrapClient(this.log);
//        this.fileManager = FileManager.getInstance(this.username, this.log);
        this.fileManager = new FileManager(this.username, this.log);
        this.ftpServerPort = this.getFreeport();
        this.ftpServer = new FTPServer(this.ftpServerPort, this.username);
        Thread t = new Thread(ftpServer);
        t.start();

        try {
            this.messageBroker = new MessageBroker(this.ip, this.port, this.ftpServerPort, this.log, this.fileManager);
        } catch (SocketException e) {
            throw new RuntimeException("Message Broker creation failed");
        }
        messageBroker.start();

        this.searchManager = new SearchManager(this.messageBroker);

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
//                this.messageBroker.getRoutingTable().addNeighbour(neighborAddress, neighborPort, "username");
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


    //destination address and FTPServerport
    public void getFile(int fileOption) {
        try {
            SearchResult fileDetail = this.searchManager.getFileDetails(fileOption);
            System.out.println("The file you requested is " + fileDetail.getFileName());
            FTPClient ftpClient = new FTPClient(fileDetail.getAddress(), fileDetail.getFtpPort(),
                    fileDetail.getFileName());

            System.out.println("Waiting for file download...");
            Thread.sleep(Constants.FILE_DOWNLOAD_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void printFileList() {
        this.fileManager.printFileList();
    }

    public MessageBroker getMessageBroker() {
        return messageBroker;
    }

    public void printlog(){
        this.log.printLog();
    }

    public int doSearch(String keyword){
        return this.searchManager.doSearch(keyword);
    }


}
