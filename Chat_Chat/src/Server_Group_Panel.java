import javax.swing.*;
import java.awt.*;

public class Server_Group_Panel {
    public JPanel jPanel_Server_Group_west;
    public JPanel choose;
    public CardLayout cardLayout;
    public JPanel jPanel_Server_Group_east_create;
    public JPanel jPanel_Server_Group_east_delete;
    public JList jList_GroupList;
    public JLabel jLabel_create;
    public JLabel jLabel_delete;
    public JLabel jLabel_group_name_create;
    public JLabel jLabel_group_name_delete;
    public JTextField jTextField_group_name_create;
    public JTextField jTextField_group_name_delete;
    public JButton jButton_create;
    public JButton jButton_delete;

    /*返回JLabel类型
    创建服务器群聊创建删除界面
    将服务器群聊创建删除界面加入标签中
    返回给服务器界面作为JTabbedPane的一部分
     */
    public JLabel get_jLabel_Server_Group_Panel() {
        JPanel jPanel_Server_Group_Panel = new JPanel(new BorderLayout());
        jPanel_Server_Group_Panel.setSize(Chat_Server_Frame.FRAME_WIDTH - 20, Chat_Server_Frame.FRAME_HEIGHT - 75);
        jPanel_Server_Group_Panel.add(jPanel_Server_Group_west(), BorderLayout.WEST);
        jPanel_Server_Group_Panel.add(jPanel_Server_Group_east(), BorderLayout.CENTER);
        cardLayout.show(choose, "create");
        JLabel jLabel_Server_Group_Panel = new JLabel();
        jLabel_Server_Group_Panel.add(jPanel_Server_Group_Panel);
        return jLabel_Server_Group_Panel;
    }

    /*
    创建群聊列表界面
    返回作为west部件
     */
    public JPanel jPanel_Server_Group_west() {
        jPanel_Server_Group_west = new JPanel(new BorderLayout());

        JLabel jLabel_current_list = new JLabel("Current Group List :");
        jLabel_current_list.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_current_list.setForeground(Color.BLACK);
        jLabel_current_list.setHorizontalAlignment(JLabel.CENTER);
        jPanel_Server_Group_west.add(jLabel_current_list, BorderLayout.NORTH);

        jPanel_Server_Group_west.add(jPanel_Server_Group_west_GroupList(), BorderLayout.CENTER);
        return jPanel_Server_Group_west;
    }

    /*
    创建群聊列表
    返回作为west的部件
     */
    public JPanel jPanel_Server_Group_west_GroupList() {
        JPanel jPanel = new JPanel();
        jList_GroupList = new JList<>();
        jList_GroupList.setFont(new Font("PingFang SC", Font.BOLD, 14));
        jList_GroupList.setVisibleRowCount(17);
        jList_GroupList.setFixedCellWidth(180);
        jList_GroupList.setFixedCellHeight(60);
        JScrollPane jScrollPane=new JScrollPane(jList_GroupList);
        jScrollPane.setPreferredSize(new Dimension(200, 490));
        jPanel.add(jScrollPane);
        return jPanel;
    }

    /*
    创建群聊创建删除编辑面板
    返回作为east部件
    分为“create”、“delete”两种页面
     */
    public JPanel jPanel_Server_Group_east() {
        cardLayout = new CardLayout();
        choose = new JPanel(cardLayout);

        //create panel
        jPanel_Server_Group_east_create = new JPanel(new BorderLayout());
        jLabel_create = new JLabel("Create Group");
        jLabel_create.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_create.setPreferredSize(new Dimension(150, 30));
        jLabel_group_name_create = new JLabel("Group Name : ");
        jLabel_group_name_create.setHorizontalAlignment(JLabel.CENTER);
        jLabel_group_name_create.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jLabel_group_name_create.setPreferredSize(new Dimension(200, 30));
        jButton_create = new JButton("create");
        jButton_create.setForeground(Color.BLUE);
        jButton_create.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jButton_create.setPreferredSize(new Dimension(100, 30));

        jTextField_group_name_create = new JTextField();
        jTextField_group_name_create.setFont(new Font("PingFang SC", Font.PLAIN, 15));
        jTextField_group_name_create.setPreferredSize(new Dimension(150, 30));


        JPanel jPanel_Server_Group_east_center = new JPanel();
        JPanel jPanel_Server_Group_east_north = new JPanel();
        jPanel_Server_Group_east_north.add(jLabel_create);
        jPanel_Server_Group_east_north.setPreferredSize(new Dimension(0, 150));
        JPanel jPanel_Server_Group_east_south = new JPanel();
        jPanel_Server_Group_east_south.setPreferredSize(new Dimension(0, 150));
        JPanel jPanel_Server_Group_east_west = new JPanel();
        jPanel_Server_Group_east_west.setPreferredSize(new Dimension(150, 0));
        JPanel jPanel_Server_Group_east_east = new JPanel();
        jPanel_Server_Group_east_east.setPreferredSize(new Dimension(150, 0));

        jPanel_Server_Group_east_create.add(jPanel_Server_Group_east_west, BorderLayout.WEST);
        jPanel_Server_Group_east_create.add(jPanel_Server_Group_east_east, BorderLayout.EAST);
        jPanel_Server_Group_east_create.add(jPanel_Server_Group_east_north, BorderLayout.NORTH);
        jPanel_Server_Group_east_create.add(jPanel_Server_Group_east_south, BorderLayout.SOUTH);

        jPanel_Server_Group_east_center.add(jLabel_group_name_create);
        jPanel_Server_Group_east_center.add(jTextField_group_name_create);


        jPanel_Server_Group_east_center.add(jButton_create);
        jPanel_Server_Group_east_create.add(jPanel_Server_Group_east_center, BorderLayout.CENTER);

        choose.add(jPanel_Server_Group_east_create, "create");

        //delete panel
        jPanel_Server_Group_east_delete = new JPanel(new BorderLayout());
        jLabel_delete = new JLabel("Delete Group");
        jLabel_delete.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_delete.setPreferredSize(new Dimension(150, 30));
        jLabel_group_name_delete = new JLabel("Group Name : ");
        jLabel_group_name_delete.setHorizontalAlignment(JLabel.CENTER);
        jLabel_group_name_delete.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jLabel_group_name_delete.setPreferredSize(new Dimension(200, 30));
        jButton_delete = new JButton("delete");
        jButton_delete.setForeground(Color.BLUE);
        jButton_delete.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jButton_delete.setPreferredSize(new Dimension(100, 30));

        jTextField_group_name_delete = new JTextField();
        jTextField_group_name_delete.setEditable(false);
        jTextField_group_name_delete.setFont(new Font("PingFang SC", Font.PLAIN, 15));
        jTextField_group_name_delete.setPreferredSize(new Dimension(150, 30));

        JPanel jPanel_Server_Group_east_center1 = new JPanel();
        JPanel jPanel_Server_Group_east_north1 = new JPanel();
        jPanel_Server_Group_east_north1.add(jLabel_delete);
        jPanel_Server_Group_east_north1.setPreferredSize(new Dimension(0, 150));
        JPanel jPanel_Server_Group_east_south1 = new JPanel();
        jPanel_Server_Group_east_south1.setPreferredSize(new Dimension(0, 150));
        JPanel jPanel_Server_Group_east_west1 = new JPanel();
        jPanel_Server_Group_east_west1.setPreferredSize(new Dimension(150, 0));
        JPanel jPanel_Server_Group_east_east1 = new JPanel();
        jPanel_Server_Group_east_east1.setPreferredSize(new Dimension(150, 0));

        jPanel_Server_Group_east_delete.add(jPanel_Server_Group_east_west1, BorderLayout.WEST);
        jPanel_Server_Group_east_delete.add(jPanel_Server_Group_east_east1, BorderLayout.EAST);
        jPanel_Server_Group_east_delete.add(jPanel_Server_Group_east_north1, BorderLayout.NORTH);
        jPanel_Server_Group_east_delete.add(jPanel_Server_Group_east_south1, BorderLayout.SOUTH);

        jPanel_Server_Group_east_center1.add(jLabel_group_name_delete);
        jPanel_Server_Group_east_center1.add(jTextField_group_name_delete);
        jPanel_Server_Group_east_center1.add(jButton_delete);
        jPanel_Server_Group_east_delete.add(jPanel_Server_Group_east_center1, BorderLayout.CENTER);

        choose.add(jPanel_Server_Group_east_delete, "delete");

        return choose;
    }

}
