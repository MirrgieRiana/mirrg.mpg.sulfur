package mirrg.mpg.sulfur.node.pin;

import java.util.ArrayList;

public class PinOutput<T>
{

	private ArrayList<PinInput<T>> pinInputs = new ArrayList<>();

	void hook(PinInput<T> pinInput)
	{
		pinInputs.add(pinInput);
	}

	public void fire(T packet)
	{
		pinInputs.forEach(l -> l.accept(packet));
	}

	public void fireClose()
	{
		pinInputs.forEach(i -> i.close(this));
	}

}
