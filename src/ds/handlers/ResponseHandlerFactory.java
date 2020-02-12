//package ds.handlers;
//
//import ds.communication.MessageBroker;
//import ds.utils.Log;
//
//import java.util.ArrayList;
//
//public class ResponseHandlerFactory {
//
//    public static AbstractResponseHandler getResponseHandler(String keyword,
//                                                             MessageBroker messageBroker, Log log) {
//        switch (keyword) {
//
//            case "JOIN":
//                AbstractResponseHandler joinHandler = JoinHandler.getInstance();
//                joinHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
//                return joinHandler;
//
//            case "JOINOK":
//                AbstractResponseHandler joinOkHandler = JoinHandler.getInstance();
//                joinOkHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
//                return joinOkHandler;
//
//            case "LEAVE":
//                AbstractResponseHandler leaveHandler = LeaveHandler.getInstance();
//                leaveHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
//                return leaveHandler;
//
//            case "LEAVEOK":
//                AbstractResponseHandler leaveOkHandler = LeaveHandler.getInstance();
//                leaveOkHandler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
//                return leaveOkHandler;
//
//            case "SER":
//                AbstractResponseHandler searchHanler = SearchHandler.getInstance();
//                searchHanler.init(messageBroker.getRoutingTable(), messageBroker.getChannelOut(), log);
//                return searchHanler;
//
//            case "SEROK":
//                AbstractResponseHandler queryHitHandler = QueryHitHandler.getInstance();
//                queryHitHandler.init(messageBroker.getRoutingTable(),
//                        messageBroker.getChannelOut(), log);
//                return queryHitHandler;
//
//
//            default:
//                System.out.println("Unknown keyword received in Response Handler : " + keyword);
//                return null;
//        }
//    }
//}
