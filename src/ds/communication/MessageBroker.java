package ds.communication;

import ds.core.RoutingTable;
import ds.handlers.AbstractResponseHandler;
import ds.handlers.JoinHandler;
import ds.handlers.LeaveHandler;
import ds.handlers.ResponseHandlerFactory;
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
    private Log log;


    public MessageBroker(String address, int port, Log log) throws SocketException {//local server port
        this.log = log;
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

        this.routingTable = new RoutingTable(address, port, log);

        this.joinHandler = JoinHandler.getInstance();
        this.joinHandler.init(this.routingTable, this.channelOut, this.log);

        this.leaveHandler = LeaveHandler.getInstance();
        this.leaveHandler.init(this.routingTable,this.channelOut,this.log);


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
                            = ResponseHandlerFactory.getResponseHandler(message.getMessage().split(" ")[1], this,log);

                    if (abstractResponseHandler != null) {
                        abstractResponseHandler.handleResponse(message);
                    }

                }
//                timeoutManager.checkForTimeout();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
}
