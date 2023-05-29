import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ServerHandler extends Thread {
    Socket socket;
    Chat_Server_Frame chat_server_frame;
    Boolean is_exist = true;

    public ServerHandler(Socket socket, Chat_Server_Frame server_frame) {
        this.socket = socket;
        this.chat_server_frame = server_frame;
    }

    public static HashMap<String,String> UserNoticeHashMap=new HashMap<>();
    public static HashMap<String, Socket> UserSocketHashMap = new HashMap<>();
    public static HashMap<String, HashMap<String, Socket>> GroupUserHashMap = new HashMap<>();

    public void run() {

        while (is_exist) {
            Object object = IOStream.readMessage(socket);
            System.out.println("Server receive: " + object);
            if (object.getClass() == TransferInfo.class) {
                TransferInfo transferInfo = (TransferInfo) object;
                if (transferInfo.getStatusEnum() == InfoType.LOGIN) {
                    login_Handler(transferInfo);
                } else if (transferInfo.getStatusEnum() == InfoType.CHAT_WITH_USER) {
                    chat_with_user_Handler(transferInfo);
                } else if (transferInfo.getStatusEnum() == InfoType.CHAT_WITH_GROUP) {
                    chat_with_group_Handler(transferInfo);
                } else if (transferInfo.getStatusEnum() == InfoType.EXIT) {
                    logout(transferInfo);
                    try {
                        TransferInfo transferInfo_over=new TransferInfo();
                        IOStream.writeMessage(socket,transferInfo_over);
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    is_exist = false;
                }
            } else {
                System.out.println("not TransferInfo");
            }
        }

    }

    public void login_Handler(TransferInfo transferInfo) {
        transferInfo.setLoginSuccess(false);
        if (checkUserLogin(transferInfo)) {
            transferInfo.setLoginSuccess(true);
            transferInfo.setStatusEnum(InfoType.LOGIN);
            IOStream.writeMessage(socket, transferInfo);

            UserSocketHashMap.put(transferInfo.getUsername(), socket);
            UserNoticeHashMap.put(transferInfo.getUsername(),"");
            Iterator<String> iterator=GroupUserHashMap.keySet().iterator();
            while (iterator.hasNext()){
                GroupUserHashMap.get(iterator.next()).put(transferInfo.getUsername(),socket);
            }

            TransferInfo broadcast_login = new TransferInfo();
            broadcast_login.setStatusEnum(InfoType.NOTICE);
            String notice = ">>> 用户 " + transferInfo.getUsername() + " 登入系统 <<<";
            broadcast_login.setNotice(notice);
            sendAll(broadcast_login);

            TransferInfo broadcast_list = new TransferInfo();
            broadcast_list.setStatusEnum(InfoType.ULIST);
            broadcast_list.setUsernameHashSet(new HashSet<>(UserSocketHashMap.keySet()));
            broadcast_list.setGroupnameHashSet(new HashSet<>(GroupUserHashMap.keySet()));
            sendAll(broadcast_list);

            flushOnlineList();
            chat_server_frame.server_user_panel.cardLayout.show(chat_server_frame.server_user_panel.jPanel_choose,"tip");

            log(notice);

        } else {
            transferInfo.setStatusEnum(InfoType.LOGIN);
            IOStream.writeMessage(socket, transferInfo);
            log(">>> 用户 " + transferInfo.getUsername() + " 登入系统失败 <<<");
        }
    }

    /*
    检查用户名是否已经存在
     */
    public boolean checkUserLogin(TransferInfo transferInfo) {
        return !(UserSocketHashMap.containsKey(transferInfo.getUsername()));
    }

    /*
    向所有客户端发送信息TransferInfo
     */
    public void sendAll(TransferInfo transferInfo) {
        Iterator<String> iterator = UserSocketHashMap.keySet().iterator();
        while (iterator.hasNext()) {
            IOStream.writeMessage(UserSocketHashMap.get(iterator.next()), transferInfo);
        }
    }

    /*
    刷新服务器在线用户列表
     */
    public void flushOnlineList() {
        JList jList = chat_server_frame.server_user_panel.jList_online;
        String[] userArray = new String[UserSocketHashMap.keySet().size()];
        int index = 0;
        for (String user_name : UserSocketHashMap.keySet()) {
            userArray[index] = user_name;
            index++;
        }
        jList.setListData(userArray);
        chat_server_frame.server_info_panel.jTextField_online_user_num.setText("" + userArray.length);
    }

    /*
    填写服务器日志
     */
    public void log(String log) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        JTextPane jTextPane = chat_server_frame.server_info_panel.jTextPane_log;
        String currentLogText = jTextPane.getText();
        jTextPane.setText(currentLogText + "\n" + dateStr + " : " + log);
    }

    public void chat_with_user_Handler(TransferInfo transferInfo) {
        if (UserSocketHashMap.containsKey(transferInfo.getReceiver_personnel())) {
            IOStream.writeMessage(UserSocketHashMap.get(transferInfo.getReceiver_personnel()), transferInfo);
            IOStream.writeMessage(UserSocketHashMap.get(transferInfo.getSender()), transferInfo);
        }
    }

    public void chat_with_group_Handler(TransferInfo transferInfo) {
        if (GroupUserHashMap.containsKey(transferInfo.getReceiver_group())) {
            Iterator<String> iterator = GroupUserHashMap.get(transferInfo.getReceiver_group()).keySet().iterator();
            while (iterator.hasNext()) {
                IOStream.writeMessage(GroupUserHashMap.get(transferInfo.getReceiver_group()).get(iterator.next()), transferInfo);
            }
        }
    }

    public void logout(TransferInfo transferInfo) {

        UserSocketHashMap.remove(transferInfo.getUsername());
        UserNoticeHashMap.remove(transferInfo.getUsername());
        Iterator<String> groupIterator = GroupUserHashMap.keySet().iterator();
        while (groupIterator.hasNext()) {
            GroupUserHashMap.get(groupIterator.next()).remove(transferInfo.getUsername());
        }

        TransferInfo broadcast_leave = new TransferInfo();
        broadcast_leave.setStatusEnum(InfoType.NOTICE);
        String notice;
        if (transferInfo.Is_exile()){
            notice = ">>> 用户 " + transferInfo.getUsername() + " 被服务器踢出系统 <<<";
        }else {
            notice = ">>> 用户 " + transferInfo.getUsername() + " 退出系统 <<<";
        }
        broadcast_leave.setNotice(notice);
        sendAll(broadcast_leave);

        TransferInfo broadcast_list = new TransferInfo();
        broadcast_list.setStatusEnum(InfoType.ULIST);
        broadcast_list.setUsernameHashSet(new HashSet<>(UserSocketHashMap.keySet()));
        broadcast_list.setGroupnameHashSet(new HashSet<>(GroupUserHashMap.keySet()));
        sendAll(broadcast_list);

        flushOnlineList();
        chat_server_frame.server_user_panel.cardLayout.show(chat_server_frame.server_user_panel.jPanel_choose,"tip");

        log(notice);

        is_exist=false;
    }


}
