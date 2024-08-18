package google.translation;

import lombok.Getter;

@Getter
public class TranslateRequest{
	private String q;
	private String target;
	private String source;
	private String key;
	private String format = "TEXT";

	public TranslateRequest(String q, String target, String source) {
		this.q = q;
		this.target = target;
		this.source = source;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
