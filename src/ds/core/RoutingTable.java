package ds.core;

import ds.core.Neighbour;
import ds.utils.Log;

import java.util.ArrayList;

public class RoutingTable {
    private ArrayList<Neighbour> neighbours;
    private String localAddress;            //used by other nodes to communicate with this node
    private int localPort;                  //used by other nodes to communicate with this node
    private Log log;

    public RoutingTable(String localAddress, int localPort, Log log) {
        this.localAddress = localAddress;
        this.localPort = localPort;
        this.neighbours = new ArrayList<Neighbour>();
        this.log = log;
    }

    public synchronized int addNeighbour(String address, int port, String username) {

        neighbours.add(new Neighbour(address, port, username));

        log.writeLog("Adding neighbour : " + address + ":" + port);
        return neighbours.size();
    }

    public synchronized int removeNeighbour(String address, int port) {
        Neighbour toRemove = null;
        for (Neighbour n: neighbours) {
            if (n.equals(address,port)) {
                toRemove = n;
            }
        }
        if (toRemove != null) {
            neighbours.remove(toRemove);
            log.writeLog("Neighbor successfully removed");
            System.out.println("Neighbor successfully removed");
            return neighbours.size();
        }
        log.writeLog("Neighbor to be removed not found");
        return 0;
    }

    public synchronized int getCount() {
        return neighbours.size();
    }

    public synchronized void print() {
        System.out.println("Total neighbours: " + neighbours.size());
        System.out.println("Address: " + localAddress + ":" + localPort);
        System.out.println("++++++++++++++++++++++++++");
        for (Neighbour n :neighbours) {
            System.out.println(
                    "Address: " + n.getIp()
                            + " Port: " + n.getPort()
            );
        }
    }

    public boolean isANeighbour(String address, int port) {
        for (Neighbour n: neighbours) {
            if (n.equals(address, port)) {
                return  true;
            }
        }
        return false;
    }

    public ArrayList<String> getOtherNeighbours(String address, int port) {
        ArrayList<String> list = new ArrayList<>();
        for (Neighbour n: neighbours) {
            if(!n.equals(address, port)) {
                list.add(n.toString());
            }
        }
        return list;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public int getLocalPort() {
        return localPort;
    }

    public ArrayList<Neighbour> getNeighbours() {
        return neighbours;
    }
}
