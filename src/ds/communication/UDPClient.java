package ds.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class UDPClient extends Thread{
    private final BlockingQueue<ChannelMessage> channelOut;
    private final DatagramSocket socket;
    private volatile boolean process = true;
    public UDPClient(BlockingQueue<ChannelMessage> channelOut, DatagramSocket socket) {
        this.channelOut = channelOut;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (process) {
            try {
                ChannelMessage message = channelOut.take();
//                ChannelMessage message = channelOut.poll(100, TimeUnit.MILLISECONDS);
                String address = message.getAddress();
                int port = message.getPort();
                String payload = message.getMessage();
                DatagramPacket packet = new DatagramPacket(
                        payload.getBytes(),
                        payload.length(),
                        InetAddress.getByName(address),
                        port
                );
                socket.send(packet);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
    public void stopProcessing() {
        this.process = false;
    }
}
