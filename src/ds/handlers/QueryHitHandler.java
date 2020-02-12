package ds.handlers;

import ds.communication.ChannelMessage;
import ds.core.RoutingTable;
import ds.core.SearchResult;
import ds.utils.Constants;
import ds.utils.Log;
import ds.utils.StringEncoderDecoder;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

public class QueryHitHandler implements AbstractResponseHandler {

    private RoutingTable routingTable;
    private BlockingQueue<ChannelMessage> channelOut;
//    private static QueryHitHandler queryHitHandler;
    private Map<String, SearchResult> searchResutls;
    private Log log;

    public QueryHitHandler(RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut, Log log) {
        this.routingTable = routingTable;
        this.channelOut = channelOut;
        this.log = log;
    }

//    public static synchronized QueryHitHandler getInstance() {
//        if (queryHitHandler == null) {
//            queryHitHandler = new QueryHitHandler();
//        }
//        return queryHitHandler;
//    }

    @Override
    public void handleResponse(ChannelMessage message) {
//        log.writeLog("Received SEROK : " + message.getMessage()
//                + " from: " + message.getAddress()
//                + " port: " + message.getPort());

        StringTokenizer stringToken = new StringTokenizer(message.getMessage(), " ");

        String length = stringToken.nextToken();
        String keyword = stringToken.nextToken();
        int filesCount = Integer.parseInt(stringToken.nextToken());
        String address = stringToken.nextToken().trim();
        int ftpPort = Integer.parseInt(stringToken.nextToken().trim());

        String addressKey = String.format(Constants.ADDRESS_KEY_FORMAT, address, ftpPort);

        int hops = Integer.parseInt(stringToken.nextToken());

        while(filesCount > 0){

            String fileName = StringEncoderDecoder.decode(stringToken.nextToken());

            if (this.searchResutls != null){
                if(!this.searchResutls.containsKey(addressKey + fileName)){
                    this.searchResutls.put(addressKey + fileName,
                            new SearchResult(fileName, address, ftpPort, hops));

                }
            }

            filesCount--;
        }

    }

//    @Override
//    public void init(RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut, Log log) {
//        this.routingTable = routingTable;
//        this.channelOut = channelOut;
//        this.log = log;
//    }

    public void setSearchResutls(Map<String, SearchResult> searchResutls) {
        this.searchResutls = searchResutls;
    }
}
