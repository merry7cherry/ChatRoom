import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Chat_Server_Frame extends JFrame implements ActionListener, MouseListener {
    //服务器窗体宽度
    public static final Integer FRAME_WIDTH = 900;
    //服务器窗体高度
    public static final Integer FRAME_HEIGHT = 600;
    public Server_Info_Panel server_info_panel;
    public Server_User_Panel server_user_panel;
    public Server_Group_Panel server_group_panel;
    public JTabbedPane jTabbedPane;

    public static void main(String[] args) {
        new Chat_Server_Frame();
    }

    public Chat_Server_Frame() {
        this.setTitle("Server of CHAT_CHAT");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        jTabbedPane.setBackground(Color.WHITE);
        jTabbedPane.setFont(new Font("PingFang SC", Font.BOLD, 20));

        server_info_panel = new Server_Info_Panel();
        server_user_panel = new Server_User_Panel();
        server_group_panel = new Server_Group_Panel();

        jTabbedPane.add("Server Info", server_info_panel.get_jLabel_Server_Info_Panel());
        jTabbedPane.add("Online User", server_user_panel.get_jLabel_Server_User_Panel());
        jTabbedPane.add("Management Group", server_group_panel.get_jLabel_Server_Group_Panel());

        this.add(jTabbedPane);

        server_info_panel.jButton_emit.addActionListener(this);

        server_user_panel.jButton_send.addMouseListener(this);
        server_user_panel.jButton_delete.addMouseListener(this);
        server_user_panel.jList_online.addMouseListener(this);

        server_group_panel.jButton_create.addMouseListener(this);
        server_group_panel.jButton_delete.addMouseListener(this);
        server_group_panel.jList_GroupList.addMouseListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == server_info_panel.jButton_emit) {
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setStatusEnum(InfoType.NOTICE);
            transferInfo.setNotice(">>服务器群发消息<<" + "\n" + server_info_panel.jTextPane_massage.getText());
            Iterator<String> iterator = ServerHandler.UserSocketHashMap.keySet().iterator();
            while (iterator.hasNext()) {
                IOStream.writeMessage(ServerHandler.UserSocketHashMap.get(iterator.next()), transferInfo);
            }
            server_info_panel.jTextPane_massage.setText("");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == server_user_panel.jButton_send) {
            String current = (String) (server_user_panel.jList_online.getSelectedValue());
            String notice = ">>服务器指定发送消息<<" + "\n" + server_user_panel.jTextPane_massage.getText();
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setStatusEnum(InfoType.NOTICE);
            transferInfo.setNotice(notice);
            IOStream.writeMessage(ServerHandler.UserSocketHashMap.get(current), transferInfo);
            String oldText = ServerHandler.UserNoticeHashMap.get(current);
            String newline = "";
            if (!oldText.equals("")) {
                newline = "\n";
            }
            String newText = oldText + newline + notice;
            ServerHandler.UserNoticeHashMap.replace(current, newText);
            server_user_panel.jTextPane_massage.setText("");
            server_user_panel.jTextPane_text.setText(ServerHandler.UserNoticeHashMap.get(current));
        } else if (e.getSource() == server_user_panel.jButton_delete) {
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setStatusEnum(InfoType.EXIT);
            transferInfo.setIs_exile(true);
            String current = (String) (server_user_panel.jList_online.getSelectedValue());
//            System.out.println(ServerHandler.UserSocketHashMap);
            IOStream.writeMessage(ServerHandler.UserSocketHashMap.get(current), transferInfo);
            server_user_panel.cardLayout.show(server_user_panel.jPanel_choose, "tip");
        } else if (e.getSource() == server_group_panel.jButton_create) {
            String groupName = server_group_panel.jTextField_group_name_create.getText();
            if(groupName.isEmpty()||groupName.length()>10){
                JOptionPane.showMessageDialog(null, "该群聊名称不合法，请重新输入");
                return;
            }
            ServerHandler.GroupUserHashMap.put(groupName,new HashMap<>(ServerHandler.UserSocketHashMap));
            server_group_panel.jTextField_group_name_create.setText("");

            TransferInfo broadcast_list = new TransferInfo();
            broadcast_list.setStatusEnum(InfoType.ULIST);
            broadcast_list.setUsernameHashSet(new HashSet<>(ServerHandler.UserSocketHashMap.keySet()));
            broadcast_list.setGroupnameHashSet(new HashSet<>(ServerHandler.GroupUserHashMap.keySet()));
            Iterator<String> iterator_group=ServerHandler.UserSocketHashMap.keySet().iterator();
            while(iterator_group.hasNext()){
                IOStream.writeMessage(ServerHandler.UserSocketHashMap.get(iterator_group.next()), broadcast_list);
            }

            JList jList=server_group_panel.jList_GroupList;
            String[] groupArray=new String[ServerHandler.GroupUserHashMap.keySet().size()];
            int index=0;
            for(String group_name:ServerHandler.GroupUserHashMap.keySet()){
                groupArray[index]=group_name;
                index++;
            }
            jList.setListData(groupArray);

            String notice = ">>服务器系统消息<<" + "\n" + "群聊 ："+groupName+" 已上线";
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setStatusEnum(InfoType.NOTICE);
            transferInfo.setNotice(notice);
            Iterator<String> iterator_notice=ServerHandler.UserSocketHashMap.keySet().iterator();
            while(iterator_notice.hasNext()){
                IOStream.writeMessage(ServerHandler.UserSocketHashMap.get(iterator_notice.next()), transferInfo);
            }

            String log=">>>群聊 ："+groupName+" 已上线<<<";
            log(log);
        } else if (e.getSource()==server_group_panel.jButton_delete) {
            String groupName=(String) server_group_panel.jList_GroupList.getSelectedValue();
            ServerHandler.GroupUserHashMap.remove(groupName);
            server_group_panel.cardLayout.show(server_group_panel.choose,"create");

            TransferInfo broadcast_list = new TransferInfo();
            broadcast_list.setStatusEnum(InfoType.ULIST);
            broadcast_list.setUsernameHashSet(new HashSet<>(ServerHandler.UserSocketHashMap.keySet()));
            broadcast_list.setGroupnameHashSet(new HashSet<>(ServerHandler.GroupUserHashMap.keySet()));
            Iterator<String> iterator_group=ServerHandler.UserSocketHashMap.keySet().iterator();
            while(iterator_group.hasNext()){
                IOStream.writeMessage(ServerHandler.UserSocketHashMap.get(iterator_group.next()), broadcast_list);
            }

            JList jList=server_group_panel.jList_GroupList;
            String[] groupArray=new String[ServerHandler.GroupUserHashMap.keySet().size()];
            int index=0;
            for(String group_name:ServerHandler.GroupUserHashMap.keySet()){
                groupArray[index]=group_name;
                index++;
            }
            jList.setListData(groupArray);

            String notice = ">>服务器系统消息<<" + "\n" + "群聊 ："+groupName+" 已下线";
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setStatusEnum(InfoType.NOTICE);
            transferInfo.setNotice(notice);
            Iterator<String> iterator_notice=ServerHandler.UserSocketHashMap.keySet().iterator();
            while(iterator_notice.hasNext()){
                IOStream.writeMessage(ServerHandler.UserSocketHashMap.get(iterator_notice.next()), transferInfo);
            }

            String log=">>>群聊 ："+groupName+" 已下线<<<";
            log(log);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == server_user_panel.jList_online) {
            if (server_user_panel.jList_online.getSelectedIndex() == -1) {//未选
                server_user_panel.cardLayout.show(server_user_panel.jPanel_choose, "tip");
                server_user_panel.jLabel_name_of_object.setText("");
            } else {
                server_user_panel.cardLayout.show(server_user_panel.jPanel_choose, "chat");
                server_user_panel.jTextPane_massage.setText("");
                String current = (String) (server_user_panel.jList_online.getSelectedValue());
                server_user_panel.jLabel_name_of_object.setText(current);
                server_user_panel.jTextPane_text.setText(ServerHandler.UserNoticeHashMap.get(current));
            }
        } else if (e.getSource()==server_group_panel.jList_GroupList) {
            if(server_group_panel.jList_GroupList.getSelectedIndex()==-1){
                server_group_panel.cardLayout.show(server_group_panel.choose,"create");
                server_group_panel.jTextField_group_name_create.setText("");
            }else {
                server_group_panel.cardLayout.show(server_group_panel.choose,"delete");
                server_group_panel.jTextField_group_name_delete.setText((String) server_group_panel.jList_GroupList.getSelectedValue());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void log(String log) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        JTextPane jTextPane = server_info_panel.jTextPane_log;
        String currentLogText = jTextPane.getText();
        jTextPane.setText(currentLogText + "\n" + dateStr + " : " + log);
    }
}
