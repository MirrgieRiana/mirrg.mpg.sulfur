package mirrg.mpg.sulfur.node;

import java.util.ArrayList;
import java.util.List;

import mirrg.mpg.sulfur.node.pin.InputPin;
import mirrg.mpg.sulfur.node.pin.OutputPin;

public abstract class Node
{

	private ArrayList<InputPin<?>> inputPins = new ArrayList<>();
	private ArrayList<OutputPin<?>> outputPins = new ArrayList<>();

	protected void addInputPin(InputPin<?> inputPin)
	{
		inputPins.add(inputPin);
	}

	protected void addOutputPin(OutputPin<?> outputPin)
	{
		outputPins.add(outputPin);
	}

	public List<InputPin<?>> getInputPins()
	{
		return inputPins;
	}

	public List<OutputPin<?>> getOutputPins()
	{
		return outputPins;
	}

}
