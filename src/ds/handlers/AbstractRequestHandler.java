package ds.handlers;

import ds.communication.ChannelMessage;

public interface AbstractRequestHandler{
    void sendRequest(ChannelMessage message);

}
