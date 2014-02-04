import java.awt.Color;


public class SimpleFormatter implements ColorFormatter{

	@Override
	public Color formatPitColorGreen() {
		return Color.green;
	}

	@Override
	public Color formatPitColorBlue() {
		return Color.blue;
	}

}
