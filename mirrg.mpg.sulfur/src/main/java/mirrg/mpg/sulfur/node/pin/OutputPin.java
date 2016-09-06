package mirrg.mpg.sulfur.node.pin;

import java.util.ArrayList;

public class OutputPin<T>
{

	private ArrayList<InputPin<T>> inputPins = new ArrayList<>();

	void hook(InputPin<T> inputPin)
	{
		inputPins.add(inputPin);
	}

	public void fire(T packet)
	{
		inputPins.forEach(l -> l.accept(packet));
	}

	public void fireClose()
	{
		inputPins.forEach(i -> i.close(this));
	}

}
