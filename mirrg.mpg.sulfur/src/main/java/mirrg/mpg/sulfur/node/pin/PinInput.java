package mirrg.mpg.sulfur.node.pin;

import java.util.ArrayList;

public abstract class PinInput<T>
{

	private ArrayList<PinOutput<T>> pinOutputs = new ArrayList<>();
	private int closed = 0;

	void hook(PinOutput<T> pinOutput)
	{
		pinOutputs.add(pinOutput);
	}

	void close(PinOutput<T> pinOutput)
	{
		closed++;
		if (closed >= pinOutputs.size()) onClosed();
	}

	protected abstract void onClosed();

	protected abstract void accept(T packet);

}
