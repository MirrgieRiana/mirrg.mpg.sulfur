package mirrg.mpg.sulfur.node.pin;

import java.util.ArrayList;

public abstract class InputPin<T>
{

	private ArrayList<OutputPin<T>> outputPins = new ArrayList<>();
	private int closed = 0;

	void hook(OutputPin<T> outputPin)
	{
		outputPins.add(outputPin);
	}

	void close(OutputPin<T> outputPin)
	{
		closed++;
		if (closed >= outputPins.size()) onClosed();
	}

	protected abstract void onClosed();

	protected abstract void accept(T packet);

}
