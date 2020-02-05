package ds.handlers;

import ds.communication.ChannelMessage;

public interface AbstractResponseHandler extends AbstractMessageHandler {
    void handleResponse(ChannelMessage message);
}
