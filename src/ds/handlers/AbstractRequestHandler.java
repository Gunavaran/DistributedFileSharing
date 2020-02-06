package ds.handlers;

import ds.communication.ChannelMessage;

public interface AbstractRequestHandler extends AbstractMessageHandler {
    void sendRequest(ChannelMessage message);

}
