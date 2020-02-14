package ds.handlers;

import ds.communication.ChannelMessage;
import ds.core.FileManager;
import ds.core.Neighbour;
import ds.core.RoutingTable;
import ds.utils.Constants;
import ds.utils.Log;
import ds.utils.StringEncoderDecoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

public class SearchHandler implements AbstractRequestHandler, AbstractResponseHandler {

    private Log log;
    private BlockingQueue<ChannelMessage> channelOut;
    private RoutingTable routingTable;
    private FileManager fileManager;

    public SearchHandler(RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut, Log log, FileManager fileManager) {
        this.fileManager = fileManager;
        this.routingTable = routingTable;
        this.channelOut = channelOut;
        this.log = log;
    }

    @Override
    public void sendRequest(ChannelMessage message) {
        try {
            channelOut.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doSearch(String keyword) {

        String searchMessage = String.format(Constants.SEARCH_FORMAT,
                this.routingTable.getLocalAddress(),
                this.routingTable.getLocalPort(),
                StringEncoderDecoder.encode(keyword),
                Constants.HOP_COUNT);

        String rawMessage = String.format(Constants.MSG_FORMAT, searchMessage.length() + 5, searchMessage);

        ChannelMessage initialMessage = new ChannelMessage(
                this.routingTable.getLocalAddress(),
                this.routingTable.getLocalPort(),
                rawMessage);

        this.handleResponse(initialMessage);
    }

    @Override
    public void handleResponse(ChannelMessage message) {
//        log.writeLog("Received SER : " + message.getMessage() + " from: " + message.getAddress() + " port: " + message.getPort());

        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");

        String length = stringToken.nextToken();
        String keyword = stringToken.nextToken();
        String address = stringToken.nextToken().trim();
        int port = Integer.parseInt(stringToken.nextToken().trim());
        String fileName = StringEncoderDecoder.decode(stringToken.nextToken().trim());
        int hops = Integer.parseInt(stringToken.nextToken().trim());


        //searching for the file in the current node
        Set<String> resultSet = fileManager.searchForFile(fileName);
        int fileNamesCount = resultSet.size();

        if (fileNamesCount != 0) {

            if (hops == Constants.HOP_COUNT) {
                System.out.println("File already available in the node");
            }
            StringBuilder fileNamesString = new StringBuilder();

            Iterator<String> itr = resultSet.iterator();

            while (itr.hasNext()) {
                fileNamesString.append(StringEncoderDecoder.encode(itr.next()) + " ");
            }

            String searchOKMessage = String.format(Constants.SEARCHOK_FORMAT,
                    fileNamesCount,
                    routingTable.getLocalAddress(),
                    routingTable.getFtpServerPort(),
                    Constants.HOP_COUNT - hops,
                    fileNamesString.toString());

            searchOKMessage = String.format(Constants.MSG_FORMAT, searchOKMessage.length() + 5, searchOKMessage);

            //destination address and port
            ChannelMessage okMessage = new ChannelMessage(address, port, searchOKMessage);
            this.log.incrementSEARCHOK_SENT_COUNT();
            this.sendRequest(okMessage);

        } else if (hops > 0) {
            ArrayList<Neighbour> neighbours = this.routingTable.getNeighbours();

            for (Neighbour neighbour : neighbours) {

                //skip sending search query to the same node again
                if (neighbour.getIp().equals(message.getAddress())
                        && neighbour.getClientPort() == message.getPort()) {
                    continue;
                }

                if(neighbour.getIp().equals(address) && neighbour.getPort() == port){
                    continue;
                }

                String payload = String.format(Constants.SEARCH_FORMAT,
                        address,
                        port,
                        StringEncoderDecoder.encode(fileName),
                        hops - 1);

                String rawMessage = String.format(Constants.MSG_FORMAT, payload.length() + 5, payload);

                ChannelMessage queryMessage = new ChannelMessage(neighbour.getIp(),
                        neighbour.getPort(),
                        rawMessage);
                this.log.incrementQUERY_FORWARDED_COUNT();
                this.sendRequest(queryMessage);
            }
        }


    }

}
