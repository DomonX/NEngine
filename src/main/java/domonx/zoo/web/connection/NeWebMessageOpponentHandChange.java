package domonx.zoo.web.connection;

public class NeWebMessageOpponentHandChange extends NeWebMessageResponse {
	public NeWebMessageOpponentHandChange() {
		type = "oppHandChange";
	}

	public boolean isRemoved;
	public String cardGuid;
}
