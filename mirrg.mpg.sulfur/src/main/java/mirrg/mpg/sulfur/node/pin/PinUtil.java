package mirrg.mpg.sulfur.node.pin;

public class PinUtil
{

	public static <T> void connect(PinOutput<T> pinOutput, PinInput<T> pinInput)
	{
		pinOutput.hook(pinInput);
		pinInput.hook(pinOutput);
	}

	public static void connectRaw(PinOutput pinOutput, PinInput pinInput)
	{
		pinOutput.hook(pinInput);
		pinInput.hook(pinOutput);
	}

}
