package mirrg.mpg.sulfur.node;

import java.util.ArrayList;
import java.util.List;

import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public abstract class Node
{

	private ArrayList<PinInput<?>> pinInputs = new ArrayList<>();
	private ArrayList<PinOutput<?>> pinOutputs = new ArrayList<>();

	protected void addPinInput(PinInput<?> pinInput)
	{
		pinInputs.add(pinInput);
	}

	protected void addPinOutput(PinOutput<?> pinOutput)
	{
		pinOutputs.add(pinOutput);
	}

	public List<PinInput<?>> getPinInputs()
	{
		return pinInputs;
	}

	public List<PinOutput<?>> getPinOutputs()
	{
		return pinOutputs;
	}

}
