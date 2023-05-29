import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class TransferInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -8501366066504250232L;
    private String username;         //用户名
    private String content;             //聊天内容
    private Boolean loginSuccess;     //登录成功标志
    private String notice;          //系统消息
    private InfoType statusEnum;      //消息类型
    private HashSet<String> usernameHashSet;  //在线用户列表
    private HashSet<String> groupnameHashSet;  //当前群聊列表
    private String sender;          //消息发送人
    private String receiver_personnel;             //私人消息接收者
    private String receiver_group;          //群聊信息接收者
    private boolean exile = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(Boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public InfoType getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(InfoType statusEnum) {
        this.statusEnum = statusEnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public HashSet<String> getUsernameHashSet() {
        return usernameHashSet;
    }

    public void setUsernameHashSet(HashSet<String> usernameHashSet) {
        this.usernameHashSet = usernameHashSet;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver_personnel() {
        return receiver_personnel;
    }

    public void setReceiver_personnel(String receiver_personnel) {
        this.receiver_personnel = receiver_personnel;
    }

    public String getReceiver_group() {
        return receiver_group;
    }

    public void setReceiver_group(String receiver_group) {
        this.receiver_group = receiver_group;
    }

    public HashSet<String> getGroupnameHashSet() {
        return groupnameHashSet;
    }

    public void setGroupnameHashSet(HashSet<String> groupnameHashSet) {
        this.groupnameHashSet = groupnameHashSet;
    }

    public boolean Is_exile() {
        return exile;
    }

    public void setIs_exile(boolean is_exile) {
        this.exile = is_exile;
    }
}
