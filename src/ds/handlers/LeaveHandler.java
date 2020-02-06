package ds.handlers;

import ds.communication.ChannelMessage;
import ds.core.Neighbour;
import ds.core.RoutingTable;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import ds.utils.Constants;
import ds.utils.Log;

public class LeaveHandler implements AbstractRequestHandler, AbstractResponseHandler{

    private RoutingTable routingTable;
    private BlockingQueue<ChannelMessage> channelOut;
    private static LeaveHandler leaveHandler;
    private Log log;

    public synchronized static LeaveHandler getInstance() {
        if (leaveHandler == null){
            leaveHandler = new LeaveHandler();
        }
        return leaveHandler;
    }

    @Override
    public void init(
            RoutingTable routingTable,
            BlockingQueue<ChannelMessage> channelOut, Log log) {
        this.routingTable = routingTable;
        this.channelOut = channelOut;
        this.log = log;
    }

    public void sendLeave () {
        String payload = String.format(Constants.LEAVE_FORMAT,
                this.routingTable.getLocalAddress(),
                this.routingTable.getLocalPort());
        String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5,payload);
        ArrayList<Neighbour> neighbours = routingTable.getNeighbours();
        for (Neighbour n: neighbours) {
            ChannelMessage message = new ChannelMessage(n.getIp(), n.getPort(),rawMessage);
            sendRequest(message);
        }

    }

    public void sendLeaveOK(){

    }

    @Override
    public void handleResponse(ChannelMessage message) {
//        log.writeLog("Handling LEAVE : " + message.getMessage()
//                + " from: " + message.getAddress()
//                + " port: " + message.getPort());
//
//        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");
//
//        String length = stringToken.nextToken().trim();
//        String keyword = stringToken.nextToken().trim();
////        log.writeLog(keyword);
//        if (keyword.equals("JOIN")) {
//            log.writeLog("inside join");
//            String address = stringToken.nextToken().trim();
//            int port = Integer.parseInt(stringToken.nextToken().trim());
//            if (routingTable.isANeighbour(address, port)) {
//                log.writeLog("Already a neighbor " + routingTable.getLocalPort() + " " + port);
////                System.out.println("Already a neighbor " + routingTable.getLocalPort() + " " + port);
//                this.sendJoinOK(address, port);
//            } else {
//                log.writeLog("New neighbor");
//                routingTable.addNeighbour(address, port, "username");
//                this.sendJoinOK(address, port);
//            }
//
//        } else if (keyword.equals("JOINOK")) {
////            log.writeLog("inside joinok");
//            int value = Integer.parseInt(stringToken.nextToken().trim());
//            int port = Integer.parseInt(stringToken.nextToken().trim());
//
//            if (value == 0) {
//                log.writeLog("JOIN successful");
//            } else if (value == 9999) {
//                log.writeLog("JOIN failed");
//                routingTable.removeNeighbour(message.getAddress(), port);
//            }
//
//        } else {
//            log.writeLog("you messed up");
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
