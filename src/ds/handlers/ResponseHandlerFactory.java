package ds.handlers;

import ds.communication.MessageBroker;
import ds.utils.Log;

import java.util.ArrayList;

public class ResponseHandlerFactory {

    public static AbstractResponseHandler getResponseHandler(String keyword,
                                                             MessageBroker messageBroker, Log log){
        switch (keyword){

            case "JOIN":
                AbstractResponseHandler joinHandler = JoinHandler.getInstance();
                joinHandler.init(messageBroker.getRoutingTable(),messageBroker.getChannelOut(), log);
                return joinHandler;

            case "JOINOK":
                AbstractResponseHandler joinOkHandler = JoinHandler.getInstance();
                joinOkHandler.init(messageBroker.getRoutingTable(),messageBroker.getChannelOut(), log);
                return joinOkHandler;

            case "SER":
//                AbstractResponseHandler searchQueryHandler = SearchQueryHandler.getInstance();
//                searchQueryHandler.init(messageBroker.getRoutingTable(),
//                        messageBroker.getChannelOut(),
//                        messageBroker.getTimeoutManager());
//                return searchQueryHandler;

            case "SEROK":
//                AbstractResponseHandler queryHitHandler = QueryHitHandler.getInstance();
//                queryHitHandler.init(messageBroker.getRoutingTable(),
//                        messageBroker.getChannelOut(),
//                        messageBroker.getTimeoutManager());
//                return queryHitHandler;

            case "LEAVE":
//                AbstractResponseHandler leaveHandler = PingHandler.getInstance();
//                leaveHandler.init(
//                        messageBroker.getRoutingTable(),
//                        messageBroker.getChannelOut(),
//                        messageBroker.getTimeoutManager()
//                );
//                return leaveHandler;
            default:
                System.out.println("Unknown keyword received in Response Handler : " + keyword);
                return null;
        }
    }
}
