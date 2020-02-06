import ds.core.Node;

import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String args[]) {
        String nodeNumber, command;
        boolean loop = true;
//        ds.core.Node node1 = new ds.core.Node("23", 43, "guna");
//        int fileCount = node1.getFileCount();
//        System.out.println(fileCount);
//
//        for(int i = 0; i< fileCount; i++){
//            System.out.println(node1.getFileList()[i]);
//        }

        ArrayList<Node> nodes = new ArrayList<>();

        nodes.add(new Node("150196" + 0));
        nodes.get(0).registerToBSServer();

        /*
        commands:
            0 - new node
            1 - print files
            2 - print routing table
            3 - print log
            4 - exit
            5 - leave network
         */
        while (loop) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Select Node: ");
            nodeNumber = myObj.nextLine();  // Read user input
            System.out.println("Enter Command: ");
            command = myObj.nextLine();  // Read user input
            Node selectedNode = nodes.get(Integer.parseInt(nodeNumber));
            switch (command) {
                case "0":
                    nodes.add(new Node("150196" + nodes.size()));
                    nodes.get(nodes.size()-1).registerToBSServer();
                    break;
                case "1":
                    selectedNode.printFileList();
                    break;
                case "2":
                    selectedNode.getMessageBroker().getRoutingTable().print();
                    break;
                case "3":
                    selectedNode.printlog();
                    break;
                case "4":
                    loop = false;
                    break;
                case "5":
                    selectedNode.leaveNetwork();
                    break;
            }
        }


    }
}
