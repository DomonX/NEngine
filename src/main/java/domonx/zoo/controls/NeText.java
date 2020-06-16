package domonx.zoo.controls;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import domonx.zoo.core.entity.NeEntity;

public class NeText extends NeEntity{
	
	private String value = "0";
	
	private Color backgroundColor;
	
	private Color textColor;

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public NeText(String guid) {
		super(guid);
		this.backgroundColor = new Color(255,255,255);
		this.textColor = new Color(0,0,0);
	}

	@Override
	public void render(Graphics g) {
		Color retColor = g.getColor();
		Font retFont = g.getFont();
		g.setColor(backgroundColor);
		g.fillRect((int)(getX()), (int)(getY()), (int)(getWidth()), (int)(getHeight()));
		g.setColor(textColor);
		g.setFont(new Font("Bauhaus 93", 0, 25));
		g.drawString(value, (int)(getX()), (int)(getY() + g.getFont().getSize()));		
		g.setColor(retColor);
		g.setFont(retFont);
	}

	@Override
	protected void reload() {
		// Text doesn't need to reload
	}

}
