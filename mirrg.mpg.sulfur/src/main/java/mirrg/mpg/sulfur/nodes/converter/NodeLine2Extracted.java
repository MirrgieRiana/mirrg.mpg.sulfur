package mirrg.mpg.sulfur.nodes.converter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeLine2Extracted extends Node
{

	public static Pattern pattern = Pattern.compile("[0-9.A-Za-z_]+");

	public final PinInput<String> pinInput;
	public final PinOutput<String[]> pinOutput;

	public NodeLine2Extracted()
	{
		addPinInput(pinInput = new PinInput<String>() {

			@Override
			protected void onClosed()
			{
				pinOutput.fireClose();
			}

			@Override
			protected void accept(String packet)
			{
				Matcher matcher = pattern.matcher(packet);

				ArrayList<String> list = new ArrayList<>();
				while (matcher.find()) {
					list.add(matcher.group());
				}

				pinOutput.fire(list.stream()
					.toArray(String[]::new));
			}

		});
		addPinOutput(pinOutput = new PinOutput<>());
	}

}
