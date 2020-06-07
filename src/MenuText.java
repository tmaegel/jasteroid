public class MenuText {

	private boolean selection;

	private String text;

	public MenuText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public boolean isSelected() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
	}
}
