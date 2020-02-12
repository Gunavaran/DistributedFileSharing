package ds.handlers;

import ds.communication.ChannelMessage;

public interface AbstractResponseHandler {
    void handleResponse(ChannelMessage message);
}
