package mirrg.mpg.sulfur.nodes.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.pin.PinInput;
import mirrg.mpg.sulfur.node.pin.PinOutput;

public class NodeStruct2Elements extends Node
{

	public final PinInput<Object> pinInput;
	public final PinOutput<String[]> pinOutput;

	public NodeStruct2Elements()
	{
		addPinInput(pinInput = new PinInput<Object>() {

			@Override
			protected void onClosed()
			{
				pinOutput.fireClose();
			}

			@Override
			protected void accept(Object packet)
			{
				pinOutput.fire(toStream("", packet)
					.toArray(String[]::new));
			}

		});
		addPinOutput(pinOutput = new PinOutput<>());
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
		if (packet instanceof Object[]) {
			int[] i = new int[1];
			return Stream.of((Object[]) packet)
				.flatMap(o -> {
					Stream<String> s = toStream(prefix + "/" + i[0], o);
					i[0]++;
					return s;
				});
		}
		return Stream.of(prefix + " = " + packet);
	}

}
