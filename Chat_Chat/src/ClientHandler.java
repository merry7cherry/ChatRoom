import javax.swing.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

// 客户端开启一个线程，来一起读消息
public class ClientHandler extends Thread {
    String Username;//用户号名标识身份
    Socket socket;    //声明socket
    Client_Login_Frame loginFrame;    //登录界面
    Chat_Client_Frame chatFrame;    //聊天界面

    public void setIs_exist(boolean is_exist) {
        this.is_exist = is_exist;
    }

    public boolean isIs_exist() {
        return is_exist;
    }

    boolean is_exist = true;

    public static HashMap<String, String> UserMessageHashMap = new HashMap<>();//用户的聊天信息
    public static HashMap<String, String> GroupMessageHashMap = new HashMap<>();//群组的聊天信息

    public static int petition;//列表群聊与用户分界线

    //含参构造方法(socket，登录界面)
    public ClientHandler(Socket socket, Client_Login_Frame loginFrame) {
        this.socket = socket;
        this.loginFrame = loginFrame;
    }

    @Override
    public void run() {
        while (is_exist) {
            // 模拟一直读消息
            System.out.println(isIs_exist());
            Object object = IOStream.readMessage(socket);
            //如果读到的对象属于传输消息这类
            if (object.getClass() == TransferInfo.class) {
                TransferInfo transferInfo = (TransferInfo) object;
                if (transferInfo.getStatusEnum() == InfoType.LOGIN) {
                    //登录消息
                    loginResult(transferInfo);
                } else if (transferInfo.getStatusEnum() == InfoType.CHAT_WITH_USER) {
                    //用户聊天消息
                    chat_User_Result(transferInfo);
                } else if (transferInfo.getStatusEnum() == InfoType.CHAT_WITH_GROUP) {
                    //群聊信息
                    chat_Group_Result(transferInfo);
                } else if (transferInfo.getStatusEnum() == InfoType.NOTICE) {
                    //系统消息
                    noticeResult(transferInfo);
                } else if (transferInfo.getStatusEnum() == InfoType.ULIST) {
                    //刷新当前在线用户列表
                    onlineUsersResult(transferInfo);
                } else if (transferInfo.getStatusEnum() == InfoType.EXIT) {
                    //服务器将该客户踢出系统
                    exitResult(transferInfo);
                }
            }
        }
    }

    /**
     * 登录消息处理
     */
    public void loginResult(TransferInfo transferInfo) {
        if (transferInfo.getLoginSuccess()) {
            //登录成功，跳转主页面
            Username = transferInfo.getUsername();
            chatFrame = new Chat_Client_Frame(Username, socket, this);

            //关闭登录窗口
            loginFrame.dispose();

        } else {
            //登录失败
            JOptionPane.showMessageDialog(null, "该用户名已被占用，请重新输入");
            System.out.println("客户端接收到登录失败");
        }
    }

    /**
     * 系统消息处理
     */
    public void noticeResult(TransferInfo transferInfo) {
        String originText = chatFrame.jTextPane_notice.getText();
        String changedText = originText + "\n" + transferInfo.getNotice();
        if (!originText.isEmpty()) {
            chatFrame.jTextPane_notice.setText(changedText);
        } else {
            chatFrame.jTextPane_notice.setText(transferInfo.getNotice());
        }
    }

    /**
     * 用户聊天消息处理
     */
    public void chat_User_Result(TransferInfo transferInfo) {
        String sender = transferInfo.getSender();
        String receiver = transferInfo.getReceiver_personnel();
        if (!UserMessageHashMap.containsKey(sender) || !UserMessageHashMap.containsKey(receiver)) {
            return;
        }
        if (sender.equals(Username)) {//本客户端为发生者
            String originText = UserMessageHashMap.get(receiver);
            String newline = "";
            if (!originText.equals("")) {
                newline = "\n";
            }
            String changedText = originText + newline + ">>> " + sender + " :" + "\n" + transferInfo.getContent();
            UserMessageHashMap.replace(receiver, changedText);
            if (chatFrame.jList_online.getSelectedIndex() != -1 && chatFrame.jList_online.getSelectedIndex() >= petition && chatFrame.jList_online.getSelectedValue().equals(receiver)) {
                chatFrame.jTextPane_text.setText(changedText);
            }
        } else if (receiver.equals(Username)) {//本客户端为接受者
            String originText = UserMessageHashMap.get(sender);
            String newline = "";
            if (!originText.equals("")) {
                newline = "\n";
            }
            String changedText = originText + newline + ">>> " + sender + " :" + "\n" + transferInfo.getContent();
            UserMessageHashMap.replace(sender, changedText);
            if (chatFrame.jList_online.getSelectedIndex() != -1 && chatFrame.jList_online.getSelectedIndex() >= petition && chatFrame.jList_online.getSelectedValue().equals(sender)) {
                chatFrame.jTextPane_text.setText(changedText);
            }
        }
    }

    /**
     * 群聊信息处理
     */
    public void chat_Group_Result(TransferInfo transferInfo) {
        String sender = transferInfo.getSender();
        String group = transferInfo.getReceiver_group();
        if (!GroupMessageHashMap.containsKey(group) || !UserMessageHashMap.containsKey(sender)) {
            System.out.println("chat_Group_Result return");
            return;
        }
        String originText = GroupMessageHashMap.get(group);
        String newline = "";
        if (!originText.equals("")) {
            newline = "\n";
        }
        String changedText = originText + newline + ">>> " + sender + " :" + "\n" + transferInfo.getContent();
        GroupMessageHashMap.replace(group, changedText);
        if (chatFrame.jList_online.getSelectedIndex() != -1 && chatFrame.jList_online.getSelectedIndex() < petition && chatFrame.jList_online.getSelectedValue().equals(group)) {
            System.out.println("setText");
            chatFrame.jTextPane_text.setText(changedText);
        }
    }

    /**
     * 刷新当前界面的用户列表
     */
    public void onlineUsersResult(TransferInfo transferInfo) {
        //删除已下线的用户和群聊
        HashSet<String> new_User_list = transferInfo.getUsernameHashSet();
        Iterator<String> iterator_User_current = UserMessageHashMap.keySet().iterator();
        while (iterator_User_current.hasNext()) {
            String User_current_tmp = iterator_User_current.next();
            if (!new_User_list.contains(User_current_tmp)) {
                iterator_User_current.remove();
                if (chatFrame.jList_online.getSelectedIndex() != -1 && chatFrame.jList_online.getSelectedValue().equals(User_current_tmp) && chatFrame.jList_online.getSelectedIndex() >= petition) {
                    chatFrame.jList_online.clearSelection();
                    chatFrame.cardLayout.show(chatFrame.jPanel_choose, "tip");
                }
            }
        }
        HashSet<String> new_Group_list = transferInfo.getGroupnameHashSet();
        Iterator<String> iterator_Group_current = GroupMessageHashMap.keySet().iterator();
        while (iterator_Group_current.hasNext()) {
            String Group_current_tmp = iterator_Group_current.next();
            if (!new_Group_list.contains(Group_current_tmp)) {
                iterator_Group_current.remove();
                if (chatFrame.jList_online.getSelectedIndex() != -1 && chatFrame.jList_online.getSelectedValue().equals(Group_current_tmp) && chatFrame.jList_online.getSelectedIndex() < petition) {
                    chatFrame.jList_online.clearSelection();
                    chatFrame.cardLayout.show(chatFrame.jPanel_choose, "tip");
                }
            }
        }
        //增加新上线的用户和群聊
        Iterator<String> iterator_User_new = new_User_list.iterator();
        while (iterator_User_new.hasNext()) {
            String User_new_tmp = iterator_User_new.next();
            if (!UserMessageHashMap.containsKey(User_new_tmp)) {
                UserMessageHashMap.put(User_new_tmp, "");
            }
        }
        Iterator<String> iterator_Group_new = new_Group_list.iterator();
        while (iterator_Group_new.hasNext()) {
            String Group_new_tmp = iterator_Group_new.next();
            if (!GroupMessageHashMap.containsKey(Group_new_tmp)) {
                GroupMessageHashMap.put(Group_new_tmp, "");
            }
        }
        //刷新列表
        JList jList = chatFrame.jList_online;
        String[] userArray = new String[UserMessageHashMap.keySet().size() + GroupMessageHashMap.keySet().size() - 1];
        int index = 0;
        for (String group_name : GroupMessageHashMap.keySet()) {
            userArray[index] = group_name;
            index++;
        }
        petition = index;
        for (String user_name : UserMessageHashMap.keySet()) {
            if (user_name.equals(Username)) {
                continue;
            }
            userArray[index] = user_name;
            index++;
        }
        jList.setListData(userArray);
    }

    /**
     * 踢出信息处理
     */
    public void exitResult(TransferInfo transferInfo) {
        if (transferInfo.Is_exile()) {
            TransferInfo exit = new TransferInfo();
            exit.setStatusEnum(InfoType.EXIT);
            exit.setIs_exile(true);
            exit.setUsername(Username);
            IOStream.writeMessage(socket, exit);
            is_exist = false;
            chatFrame.dispose();
            JOptionPane.showMessageDialog(null, "您已被服务器踢出系统");
        }
    }
}
