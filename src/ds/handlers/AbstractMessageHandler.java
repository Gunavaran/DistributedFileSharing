package ds.handlers;

import ds.communication.ChannelMessage;
import ds.core.RoutingTable;

import java.util.concurrent.BlockingQueue;

public interface AbstractMessageHandler {
    void init (RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut);
}
