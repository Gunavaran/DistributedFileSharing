package ds;

import ds.core.Node;
import ds.utils.Constants;

import java.util.*;

public class PerformanceAnalyzer {
    public static void main(String args[]) throws Exception {

//        ArrayList<Node> nodes = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            nodes.add(new Node("150196" + i));
//            nodes.get(i).registerToBSServer();
//        }

        for (int m = 0; m < 3; m++) {
//            ArrayList<Integer> nodeDegree = new ArrayList<>();
//            ArrayList<Integer> hops = new ArrayList<>();
//            ArrayList<Long> latency = new ArrayList<>();
//            Node selectedNode;
//            ArrayList<Integer> QUERY_RECEIVED_COUNT = new ArrayList<>();
//            ArrayList<Integer> QUERY_FORWARDED_COUNT = new ArrayList<>();
//            ArrayList<Integer> SEARCHOK_SENT_COUNT = new ArrayList<>();
//            ArrayList<Integer> totalMessages = new ArrayList<>();
//            int[] tempArray;
//            if (m == 0) {
//                tempArray = new int[]{0, 5, 9};
//            } else if (m == 1) {
//                tempArray = new int[]{0, 4, 8};
//                nodes.get(3).leaveNetwork();
//                nodes.remove(3);
//                for (int i = 0; i < nodes.size(); i++) {
//                    selectedNode = nodes.get(i);
//                    selectedNode.resetLogValues();
//                }
//            } else {
//                tempArray = new int[]{0, 4, 7};
//                nodes.get(6).leaveNetwork();
//                nodes.remove(6);
//                for (int i = 0; i < nodes.size(); i++) {
//                    selectedNode = nodes.get(i);
//                    selectedNode.resetLogValues();
//                }
//            }
//            System.out.println("node count: " + nodes.size());
//
//
//            for (int i : tempArray) {
//                selectedNode = nodes.get(i);
//                for (String query : Constants.QUERY_LIST) {
//                    selectedNode.doSearch(query);
//                }
//            }
//
//            for (int i : tempArray) {
//                selectedNode = nodes.get(i);
//                latency.addAll(selectedNode.getLatency());
//                hops.addAll(selectedNode.getHops());
//            }
//
//            for (int j = 0; j < nodes.size(); j++) {
//                nodeDegree.add(nodes.get(j).getNodeDegree());
//            }
//
//            for (int j = 0; j < nodes.size(); j++) {
//                selectedNode = nodes.get(j);
//                QUERY_FORWARDED_COUNT.add(selectedNode.getQueryForwardedCount());
//                QUERY_RECEIVED_COUNT.add(selectedNode.getQueryMessagesReceived());
//                SEARCHOK_SENT_COUNT.add(selectedNode.getSearchOkSentCount());
//                totalMessages.add(QUERY_FORWARDED_COUNT.get(j) + QUERY_RECEIVED_COUNT.get(j) + SEARCHOK_SENT_COUNT.get(j));
//            }

//            System.out.println("latency array: " + latency);
//            System.out.println("hops array: " + hops);
//            System.out.println("node degree: " + nodeDegree);
//            System.out.println("received: " + QUERY_RECEIVED_COUNT);
//            System.out.println("forwarded: " + QUERY_FORWARDED_COUNT);
//            System.out.println("ok sent: " + SEARCHOK_SENT_COUNT);
//            System.out.println("total" + totalMessages);

            ArrayList<Integer> hops = new ArrayList<>();
            hops.add(10);
            hops.add(20);
            hops.add(5);
            System.out.println("Hops:");
            System.out.println("    max: " + Collections.max(hops));
            System.out.println("    min: " + Collections.min(hops));
            System.out.println("    mean: " + mean(hops));

            System.out.println("\n");

        }

    }
    public static int sum (List<Integer> a){
        if (a.size() > 0) {
            int sum = 0;

            for (Integer i : a) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }

    public static int sum2 (List<Long> a){
        if (a.size() > 0) {
            int sum = 0;

            for (Long i : a) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }
    public static double mean (List<Integer> a){
        int sum = sum(a);
        double mean = 0;
        mean = sum / (a.size() * 1.0);
        return mean;
    }

    public static double mean2 (List<Long> a){
        int sum = sum2(a);
        double mean = 0;
        mean = sum / (a.size() * 1.0);
        return mean;
    }
}
