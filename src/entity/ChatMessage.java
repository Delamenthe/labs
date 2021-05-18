package entity;

public class ChatMessage {
    // Текстсообщения
    private String message;
    private String style;
    private ChatUser author;


    public ChatMessage(String message, ChatUser author, long timestamp,String style) {
        super();
        this.message= message;
        this.author= author;
        this.style = style;
    }
    public String getMessage() {

        return message;
    }
    public void setMessage(String message)
    {
        this.message= message;
    }
    public ChatUser getAuthor() {

        return author;
    }
    public String getStyle() {

        return style;
    }
    public void setAuthor(ChatUser author) {
        this.author= author;
    }



}