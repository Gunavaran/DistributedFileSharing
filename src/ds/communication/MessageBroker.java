package ds.communication;

import ds.core.FileManager;
import ds.core.RoutingTable;
import ds.handlers.*;
import ds.utils.Log;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MessageBroker extends Thread {

    private volatile boolean process = true;

    private UDPServer server;
    private UDPClient client;

    private BlockingQueue<ChannelMessage> channelIn;
    private BlockingQueue<ChannelMessage> channelOut;

    private RoutingTable routingTable;
    private LeaveHandler leaveHandler;
    private JoinHandler joinHandler;
    private SearchHandler searchHandler;
    private QueryHitHandler queryHitHandler;
    private Log log;
    private FileManager fileManager;

    public MessageBroker(String address, int port, int ftpServerPort, Log log, FileManager fileManager) throws SocketException {//local server port
        this.log = log;
        this.fileManager = fileManager;
        channelIn = new LinkedBlockingQueue<ChannelMessage>();
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException("Socket creation failed");
        }
        this.server = new UDPServer(channelIn, socket);

        channelOut = new LinkedBlockingQueue<ChannelMessage>();
        this.client = new UDPClient(channelOut, new DatagramSocket());

        this.routingTable = new RoutingTable(address, port, ftpServerPort, log);

//        this.joinHandler = JoinHandler.getInstance();
//        this.joinHandler.init(this.routingTable, this.channelOut, this.log);
        this.joinHandler = new JoinHandler(this.routingTable, this.channelOut, this.log);

//        this.leaveHandler = LeaveHandler.getInstance();
//        this.leaveHandler.init(this.routingTable,this.channelOut,this.log);
        this.leaveHandler = new LeaveHandler(this.routingTable,this.channelOut,this.log);

//        this.searchHandler = SearchHandler.getInstance();
//        this.searchHandler.init(this.routingTable,this.channelOut, this.log);
        this.searchHandler = new SearchHandler(this.routingTable,this.channelOut, this.log, this.fileManager);

        this.queryHitHandler = new QueryHitHandler(this.routingTable, this.channelOut, this.log);

    }

    @Override
    public void run() {
        this.server.start();
        this.client.start();
        this.process();
    }

    public void process() {
        while (process) {
            try {
                ChannelMessage message = channelIn.poll(100, TimeUnit.MILLISECONDS);
//                ChannelMessage message = channelIn.take();
                if (message != null) {
                    log.writeLog("Received Message: " + message.getMessage()
                            + " from: " + message.getAddress()
                            + " port: " + message.getPort());

                    AbstractResponseHandler abstractResponseHandler
                            = this.getResponseHandler(message.getMessage().split(" ")[1]);

                    if (abstractResponseHandler != null) {
                        abstractResponseHandler.handleResponse(message);
                    }

                }
//                timeoutManager.checkForTimeout();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }
    }

    public void stopProcessing() {
        this.process = false;
        server.stopProcessing();
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public BlockingQueue<ChannelMessage> getChannelIn() {
        return channelIn;
    }

    public BlockingQueue<ChannelMessage> getChannelOut() {
        return channelOut;
    }

    public void sendJoin(String address, int port){
        this.joinHandler.sendJoin(address,port);
    }

    public void leave(){
        this.leaveHandler.sendLeave();
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void doSearch(String keyword){
        this.searchHandler.doSearch(keyword);
    }

    public AbstractResponseHandler getResponseHandler(String keyword) {
        switch (keyword) {

            case "JOIN":
//                AbstractResponseHandler joinHandler = JoinHandler.getInstance();
//                joinHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
                return joinHandler;

            case "JOINOK":
//                AbstractResponseHandler joinOkHandler = JoinHandler.getInstance();
//                joinOkHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
//                return joinOkHandler;
                return joinHandler;
            case "LEAVE":
//                AbstractResponseHandler leaveHandler = LeaveHandler.getInstance();
//                leaveHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
                return leaveHandler;

            case "LEAVEOK":
//                AbstractResponseHandler leaveOkHandler = LeaveHandler.getInstance();
//                leaveOkHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
//                return leaveOkHandler;
                return leaveHandler;
            case "SER":
//                AbstractResponseHandler searchHandler = SearchHandler.getInstance();
//                searchHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
                return searchHandler;

            case "SEROK":
//                AbstractResponseHandler queryHitHandler = QueryHitHandler.getInstance();
//                queryHitHandler.init(messageBroker.getRoutingTable(),
//                        messageBroker.getChannelOut(), log);
                return queryHitHandler;


            default:
                System.out.println("Unknown keyword received in Response Handler : " + keyword);
                return null;
        }
    }

    public LeaveHandler getLeaveHandler() {
        return leaveHandler;
    }

    public JoinHandler getJoinHandler() {
        return joinHandler;
    }

    public SearchHandler getSearchHandler() {
        return searchHandler;
    }

    public QueryHitHandler getQueryHitHandler() {
        return queryHitHandler;
    }
}
