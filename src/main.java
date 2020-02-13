import ds.core.Node;
import ds.utils.StringEncoderDecoder;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class main {
    public static void main(String args[]) throws Exception {
        String nodeNumber, command;
        boolean loop = true;

//        StringBuilder fileNamesString = new StringBuilder();
//        fileNamesString.append(StringEncoderDecoder.encode("hello") + " ");
//        fileNamesString.append(StringEncoderDecoder.encode("world") + " ");
//        fileNamesString.append(StringEncoderDecoder.encode("love"));
//
//        StringTokenizer stringToken = new StringTokenizer(fileNamesString.toString(), " ");
//        String fileName = StringEncoderDecoder.decode(stringToken.nextToken());
//        System.out.println(fileName);
//        System.out.println(fileNamesString.toString());

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
            6 - search
         */
        while (loop) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("new node/select node: ");
            String input = myObj.nextLine();
            if(input.toLowerCase().equals("n")){
                nodes.add(new Node("150196" + nodes.size()));
                nodes.get(nodes.size() - 1).registerToBSServer();
            } else if (!input.equals("")){
                System.out.println("Enter Command: ");
                command = myObj.nextLine();
                Node selectedNode = nodes.get(Integer.parseInt(input));
                switch (command) {
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
                    case "6":
                        System.out.println("\nEnter your search query below : ");
                        String searchQuery = myObj.nextLine();

                        if (searchQuery != null && !searchQuery.equals("")) {
                            int results = selectedNode.doSearch(searchQuery);
                            if (results != 0){
                                System.out.println("\nPlease choose the file you need to download : ");
                                String fileOption = myObj.nextLine();
                                if(!fileOption.equals("cancel")){
                                    int option = Integer.parseInt(fileOption);
                                    selectedNode.getFile(option);
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("invalid command");
                }
            }



        }
    }
}
