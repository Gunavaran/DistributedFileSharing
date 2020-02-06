package ds.handlers;

import ds.communication.ChannelMessage;
import ds.core.RoutingTable;
import ds.utils.Log;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public interface AbstractMessageHandler {
    void init(RoutingTable routingTable, BlockingQueue<ChannelMessage> channelOut, Log log);
}
