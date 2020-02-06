package ds.handlers;

import ds.communication.ChannelMessage;
import ds.core.RoutingTable;
import ds.utils.Constants;
import ds.utils.Log;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class JoinHandler implements AbstractRequestHandler, AbstractResponseHandler {

    private static JoinHandler joinHandler;
    private BlockingQueue<ChannelMessage> channelOut;
    private RoutingTable routingTable;
    private boolean initiated = false;
    private Log log;

    private JoinHandler() {
        this.initiated = true;
    }

    public synchronized static JoinHandler getInstance() {

        if (joinHandler == null) {
            joinHandler = new JoinHandler();
        }
        return joinHandler;
    }

    @Override
    public void sendRequest(ChannelMessage message) {
        try {
            channelOut.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendJoin(String address, int port) {

        String joinMsg = String.format(Constants.JOIN_FORMAT, this.routingTable.getLocalAddress(), this.routingTable.getLocalPort());
        joinMsg = String.format(Constants.MSG_FORMAT, joinMsg.length() + 5, joinMsg);

        ChannelMessage message = new ChannelMessage(address, port, joinMsg);

        this.sendRequest(message);
        log.writeLog("JOIN message sent");

    }

    public void sendJoinOK(String address, int port) {
        String joinOkMsg = String.format(Constants.JOINOK_FORMAT, "0", routingTable.getLocalPort());
        joinOkMsg = String.format(Constants.MSG_FORMAT, joinOkMsg.length() + 5, joinOkMsg);
        ChannelMessage okMessage = new ChannelMessage(address, port, joinOkMsg);
        this.sendRequest(okMessage);
        log.writeLog("JOINOK sent to " + address + " " + port);
    }

    @Override
    public void init(
            RoutingTable routingTable,
            BlockingQueue<ChannelMessage> channelOut, Log log) {
        this.routingTable = routingTable;
        this.channelOut = channelOut;
        this.log = log;
    }

    @Override
    public void handleResponse(ChannelMessage message) {
        log.writeLog("Handling Join : " + message.getMessage()
                + " from: " + message.getAddress()
                + " port: " + message.getPort());

        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");

        String length = stringToken.nextToken().trim();
        String keyword = stringToken.nextToken().trim();
//        log.writeLog(keyword);
        if (keyword.equals("JOIN")) {
            log.writeLog("inside join");
            String address = stringToken.nextToken().trim();
            int port = Integer.parseInt(stringToken.nextToken().trim());
            if (routingTable.isANeighbour(address, port)) {
                log.writeLog("Already a neighbor " + routingTable.getLocalPort() + " " + port);
//                System.out.println("Already a neighbor " + routingTable.getLocalPort() + " " + port);
                this.sendJoinOK(address, port);
            } else {
                log.writeLog("New neighbor");
                routingTable.addNeighbour(address, port, "username");
                this.sendJoinOK(address, port);
            }

        } else if (keyword.equals("JOINOK")) {
//            log.writeLog("inside joinok");
            int value = Integer.parseInt(stringToken.nextToken().trim());
            int port = Integer.parseInt(stringToken.nextToken().trim());

            if (value == 0) {
                    log.writeLog("JOIN successful");
            } else if (value == 9999) {
                    log.writeLog("JOIN failed");
                routingTable.removeNeighbour(message.getAddress(), port);
            }

        } else {
            log.writeLog("you messed up");
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
