package mirrg.mpg.sulfur.nodes.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.InputPin;
import mirrg.mpg.sulfur.node.pin.OutputPin;

public class NodeStruct2Elements extends Node
{

	public final InputPin<Object> inputPin;
	public final OutputPin<String[]> outputPin;

	public NodeStruct2Elements()
	{
		addInputPin(inputPin = new InputPin<Object>() {

			@Override
			protected void onClosed()
			{
				outputPin.fireClose();
			}

			@Override
			protected void accept(Object packet)
			{
				outputPin.fire(toStream("", packet)
					.toArray(String[]::new));
			}

		});
		addOutputPin(outputPin = new OutputPin<>());
	}

	protected Stream<String> toStream(String prefix, Object packet)
	{
		if (packet instanceof Map) {
			return ((Map<String, Object>) packet).entrySet().stream()
				.flatMap(e -> toStream(prefix + "/" + e.getKey(), e.getValue()));
		}
		if (packet instanceof List) {
			int[] i = new int[1];
			return ((List<Object>) packet).stream()
				.flatMap(o -> {
					Stream<String> s = toStream(prefix + "/" + i[0], o);
					i[0]++;
					return s;
				});
		}
		return Stream.of(prefix + " = " + packet);
	}

}
