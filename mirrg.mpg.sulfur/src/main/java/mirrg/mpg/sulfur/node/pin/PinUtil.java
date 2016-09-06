package mirrg.mpg.sulfur.node.pin;

public class PinUtil
{

	public static <T> void connect(OutputPin<T> outputPin, InputPin<T> inputPin)
	{
		outputPin.hook(inputPin);
		inputPin.hook(outputPin);
	}

	public static void connectRaw(OutputPin outputPin, InputPin inputPin)
	{
		outputPin.hook(inputPin);
		inputPin.hook(outputPin);
	}

}
