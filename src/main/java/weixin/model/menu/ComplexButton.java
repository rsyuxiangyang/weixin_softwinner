package weixin.model.menu;

/**
 * Created by XIANGYANG on 2015-8-3.
 * �������͵İ�ť
 */
public class ComplexButton extends Button {
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
