public enum InfoType {

    LOGIN(1, "登录"),
    NOTICE(2, "系统消息"),
    CHAT_WITH_USER(3, "私人聊天消息"),
    CHAT_WITH_GROUP(4, "群组聊天消息"),
    ULIST(5, "在线用户及群聊列表"),
    EXIT(6, "退出");

    private Integer status;
    private String desc;

    private InfoType(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}