package ds.handlers;

import ds.communication.ChannelMessage;
import ds.core.Neighbour;
import ds.core.RoutingTable;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import ds.utils.Constants;
import ds.utils.Log;

public class LeaveHandler implements AbstractRequestHandler, AbstractResponseHandler {

    private RoutingTable routingTable;
    private BlockingQueue<ChannelMessage> channelOut;
    //    private static LeaveHandler leaveHandler;
    private Log log;

    public LeaveHandler(RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut, Log log) {
        this.routingTable = routingTable;
        this.channelOut = channelOut;
        this.log = log;

    }

//    public static synchronized LeaveHandler getInstance() {
//        if (leaveHandler == null) {
//            leaveHandler = new LeaveHandler();
//        }
//        return leaveHandler;
//    }

//    @Override
//    public void init(
//            RoutingTable routingTable,
//            BlockingQueue<ChannelMessage> channelOut, Log log) {
//        this.routingTable = routingTable;
//        this.channelOut = channelOut;
//        this.log = log;
//    }

    public void sendLeave() {
//        log.writeLog("LEAVE msg sent2");
        String payload = String.format(Constants.LEAVE_FORMAT,
                this.routingTable.getLocalAddress(),
                this.routingTable.getLocalPort());
        String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5, payload);
        ArrayList<Neighbour> neighbours = routingTable.getNeighbours();
        for (Neighbour n : neighbours) {
            ChannelMessage message = new ChannelMessage(n.getIp(), n.getPort(), rawMessage);
            sendRequest(message);
        }

        log.writeLog("LEAVE msg sent");
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    //address and port of the destination node
    public void sendLeaveOK(String address, int port, int value) {
        String leaveOkMsg = String.format(Constants.LEAVEOK_FORMAT, value, this.routingTable.getLocalPort());
        leaveOkMsg = String.format(Constants.MSG_FORMAT, leaveOkMsg.length() + 5, leaveOkMsg);
        ChannelMessage okMessage = new ChannelMessage(address, port, leaveOkMsg);
        this.sendRequest(okMessage);
        log.writeLog("LEAVEOK sent to " + address + " " + port);
    }

    @Override
    public void handleResponse(ChannelMessage message) {
        log.writeLog("Handling LEAVE : " + message.getMessage()
                + " from: " + message.getAddress()
                + " port: " + message.getPort());

        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");

        String length = stringToken.nextToken().trim();
        String keyword = stringToken.nextToken().trim();
////        log.writeLog(keyword);
        if (keyword.equals("LEAVE")) {
            String address = stringToken.nextToken().trim();
            int port = Integer.parseInt(stringToken.nextToken().trim());
            if (routingTable.isANeighbour(address, port)) {
                log.writeLog("Neighbor exist");
////                System.out.println("Already a neighbor " + routingTable.getLocalPort() + " " + port);
                this.routingTable.removeNeighbour(address, port);
                this.sendLeaveOK(address, port, 0);
            } else {
                log.writeLog("Neighbor does not exist");
                this.sendLeaveOK(address, port, 9999);
            }
//
        } else if (keyword.equals("LEAVEOK")) {
////            log.writeLog("inside joinok");
            int value = Integer.parseInt(stringToken.nextToken().trim());
            int port = Integer.parseInt(stringToken.nextToken().trim());
//
            if (value == 0) {
                log.writeLog("LEAVE successful");
            } else if (value == 9999) {
                log.writeLog("LEAVE failed");
//                routingTable.removeNeighbour(message.getAddress(), port);
            }
//
        } else {
            log.writeLog("you messed up");
        }

//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void sendRequest(ChannelMessage message) {
        try {
            channelOut.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
