package Entity;

public class ChatUser {

	private String name;
	private long lastInteractionTime;
	private String sessionId;
	private String color;
	private String sessionIdColor;

	public ChatUser(String name,long lastInteractionTime,String sessionId,String color, String sessionIdColor) {

		super();
		this.name = name;
		this.lastInteractionTime = lastInteractionTime;
		this.sessionId = sessionId;
		this.sessionIdColor = sessionIdColor;
		this.color = color;

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getLastInteractionTime() {
		return lastInteractionTime;
	}
	public void setLastInteractionTime(long lastInteractionTime) {
		this.lastInteractionTime = lastInteractionTime;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getColor() {
		return color;
	}
	public void setColorT(String color) {
		this.color = color;
	}
	public String getSessionIdColor() {
		return sessionIdColor;
	}
	public void setSessionIdColor(String sessionIdColor) {
		this.sessionIdColor = sessionIdColor;
	}
}