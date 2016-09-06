package mirrg.mpg.sulfur.nodes.converter;

import java.util.ArrayList;
import java.util.Hashtable;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeCSV2Struct extends Node
{

	public final PinInput<String[]> pinInput;
	public final PinOutput<Object> pinOutput;

	public NodeCSV2Struct()
	{
		addPinInput(pinInput = new PinInput<String[]>() {

			@Override
			protected void onClosed()
			{
				pinOutput.fireClose();
			}

			@Override
			protected void accept(String[] packet)
			{
				Hashtable<String, Object> hash = new Hashtable<>();
				hash.put("A", packet[0]);
				hash.put("T", new ArrayList<>());
				((ArrayList<Object>) hash.get("T")).add(packet[1]);
				((ArrayList<Object>) hash.get("T")).add(packet[2]);
				hash.put("B", packet[3]);
				hash.put("H", new ArrayList<>());
				((ArrayList<Object>) hash.get("H")).add(packet[4]);
				((ArrayList<Object>) hash.get("H")).add(packet[5]);

				pinOutput.fire(hash);
			}

		});
		addPinOutput(pinOutput = new PinOutput<>());
	}

}
