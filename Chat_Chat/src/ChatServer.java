import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public Chat_Server_Frame chat_server_frame;
    public ChatServer(){
        try{
            ServerSocket serverSocket=new ServerSocket(Port.SERVER_PORT);
            chat_server_frame=new Chat_Server_Frame();
            Server_IP_Info server_ip_info=getServerIP();
            loadServerInfo(server_ip_info);
            while(true){
                Socket socket=serverSocket.accept();
                ServerHandler serverHandler=new ServerHandler(socket,chat_server_frame);
                serverHandler.start();
                System.out.println(socket+"CONNECTED");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //获取服务器的主机名和IP地址,返回服务器IP等信息
    public Server_IP_Info getServerIP() {
        Server_IP_Info sib = null;
        try {
            InetAddress serverAddress = InetAddress.getLocalHost();
            byte[] ipAddress = serverAddress.getAddress();
            sib = new Server_IP_Info();
            sib.setIp(serverAddress.getHostAddress());
            sib.setHostName(serverAddress.getHostName());
            sib.setPort(Port.SERVER_PORT);

            System.out.println("Server IP is:" + (ipAddress[0] & 0xff) + "."
                    + (ipAddress[1] & 0xff) + "." + (ipAddress[2] & 0xff) + "."
                    + (ipAddress[3] & 0xff));
        } catch (Exception e) {
            System.out.println("CouLd not get Server IP." + e);
        }
        return sib;
    }

    public void loadServerInfo(Server_IP_Info serverInfo) {
        chat_server_frame.server_info_panel.jTextField_ip.setText(serverInfo.getIp());
        chat_server_frame.server_info_panel.jTextField_port.setText(""+serverInfo.getPort());
        chat_server_frame.server_info_panel.jTextPane_log.setText("** SERVER SUCCESS **");

    }

    public static void main(String[] args){
        new ChatServer();
    }
}
