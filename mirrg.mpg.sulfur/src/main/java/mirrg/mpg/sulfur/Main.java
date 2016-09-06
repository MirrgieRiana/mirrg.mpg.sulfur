package mirrg.mpg.sulfur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.NodeEntryPoint;
import mirrg.mpg.sulfur.node.RegistryNode;
import mirrg.mpg.sulfur.node.pin.PinUtil;
import mirrg.mpg.sulfur.nodes.converter.NodeCSV2Struct;
import mirrg.mpg.sulfur.nodes.converter.NodeFlatMap;
import mirrg.mpg.sulfur.nodes.converter.NodeIS2Image;
import mirrg.mpg.sulfur.nodes.converter.NodeImage2OS;
import mirrg.mpg.sulfur.nodes.converter.NodeImageNegation;
import mirrg.mpg.sulfur.nodes.converter.NodeStruct2Elements;
import mirrg.mpg.sulfur.nodes.converter.NodeToString;
import mirrg.mpg.sulfur.nodes.input.NodeInputCSV;
import mirrg.mpg.sulfur.nodes.input.NodeInputInterval;
import mirrg.mpg.sulfur.nodes.input.NodeInputLines;
import mirrg.mpg.sulfur.nodes.input.NodeInputStream;
import mirrg.mpg.sulfur.nodes.output.NodeOutputStdout;
import mirrg.mpg.sulfur.nodes.output.NodeOutputStream;
import net.arnx.jsonic.JSON;

public class Main
{

	public static PrintStream out = System.out;

	static {
		RegistryNode.register("standard.input.lines", a -> new NodeInputLines(new File((String) a)));
		RegistryNode.register("standard.input.csv", a -> new NodeInputCSV(new File((String) a)));
		RegistryNode.register("standard.input.interval", a -> new NodeInputInterval());
		RegistryNode.register("standard.input.bytes", a -> {
			try {
				return new NodeInputStream(new FileInputStream(new File((String) a)), 1024);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		});
		RegistryNode.register("standard.converter.csv.struct", a -> new NodeCSV2Struct());
		RegistryNode.register("standard.converter.struct.elements", a -> new NodeStruct2Elements());
		RegistryNode.register("standard.converter.flatMap", a -> new NodeFlatMap<>());
		RegistryNode.register("standard.converter.toString", a -> new NodeToString());
		RegistryNode.register("standard.converter.bytes.image", a -> new NodeIS2Image());
		RegistryNode.register("standard.converter.imageNegation", a -> new NodeImageNegation());
		RegistryNode.register("standard.converter.image.bytes", a -> new NodeImage2OS());
		RegistryNode.register("standard.output.stdout", a -> new NodeOutputStdout());
		RegistryNode.register("standard.output.bytes", a -> {
			try {
				return new NodeOutputStream(new FileOutputStream(new File((String) a)));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public static void main(String[] args) throws Exception
	{
		if (args.length > 0) {
			main(new File(args[0]));
		} else {
			main(new File("default.mpg.json"));
		}
	}

	public static ArrayList<Thread> main(File fileJson) throws Exception
	{
		Config config = JSON.decode(new FileInputStream(fileJson), Config.class);

		Hashtable<String, Node> nodes = new Hashtable<>();
		Stream.of(config.nodes)
			.forEach(c -> nodes.put(c.id, RegistryNode.create(c.name, c.argument)));
		Stream.of(config.connections)
			.forEach(c -> PinUtil.connectRaw(
				nodes.get(c.from.node).getOutputPins().get(c.from.pin),
				nodes.get(c.to.node).getInputPins().get(c.to.pin)));

		return nodes.values().stream()
			.filter(NodeEntryPoint.class::isInstance)
			.map(NodeEntryPoint.class::cast)
			.map(NodeEntryPoint::start)
			.collect(Collectors.toCollection(ArrayList::new));
	}

	@Deprecated
	public static void main5(String[] args) throws Exception
	{
		NodeEntryPoint node1 = (NodeEntryPoint) RegistryNode.create("standard.input.bytes", "in.png");
		Node node2 = RegistryNode.create("standard.converter.bytes.image", null);
		Node node3 = RegistryNode.create("standard.converter.imageNegation", null);
		Node node4 = RegistryNode.create("standard.converter.image.bytes", null);
		Node node5 = RegistryNode.create("standard.output.bytes", "out1.png");
		Node node6 = RegistryNode.create("standard.output.bytes", "out2.png");

		PinUtil.connectRaw(node1.getOutputPins().get(0), node5.getInputPins().get(0));
		PinUtil.connectRaw(node1.getOutputPins().get(0), node2.getInputPins().get(0));
		PinUtil.connectRaw(node2.getOutputPins().get(0), node3.getInputPins().get(0));
		PinUtil.connectRaw(node3.getOutputPins().get(0), node4.getInputPins().get(0));
		PinUtil.connectRaw(node4.getOutputPins().get(0), node6.getInputPins().get(0));

		Main.out.println("[begin]");
		node1.start();
		Main.out.println("[end]");
	}

	@Deprecated
	public static void main4(String[] args) throws Exception
	{
		NodeEntryPoint node1 = (NodeEntryPoint) RegistryNode.create("standard.input.interval", null);
		Node node2 = RegistryNode.create("standard.converter.toString", null);
		Node node3 = RegistryNode.create("standard.output.stdout", null);

		PinUtil.connectRaw(node1.getOutputPins().get(0), node2.getInputPins().get(0));
		PinUtil.connectRaw(node2.getOutputPins().get(0), node3.getInputPins().get(0));

		Main.out.println("[begin]");
		node1.start();
		Main.out.println("[end]");
	}

	@Deprecated
	public static void main3(String[] args) throws Exception
	{
		NodeEntryPoint node1 = (NodeEntryPoint) RegistryNode.create("standard.input.csv", "in.csv");
		Node node2 = RegistryNode.create("standard.converter.csv.struct", null);
		Node node3 = RegistryNode.create("standard.converter.struct.elements", null);
		Node node4 = RegistryNode.create("standard.converter.flatMap", null);
		Node node5 = RegistryNode.create("standard.output.stdout", null);

		PinUtil.connectRaw(node1.getOutputPins().get(0), node2.getInputPins().get(0));
		PinUtil.connectRaw(node2.getOutputPins().get(0), node3.getInputPins().get(0));
		PinUtil.connectRaw(node3.getOutputPins().get(0), node4.getInputPins().get(0));
		PinUtil.connectRaw(node4.getOutputPins().get(0), node5.getInputPins().get(0));

		Main.out.println("[begin]");
		node1.start();
		Main.out.println("[end]");
	}

	@Deprecated
	public static void main2(String[] args) throws Exception
	{
		NodeEntryPoint node1 = (NodeEntryPoint) RegistryNode.create("standard.input.lines", "in.csv");
		Node node2 = RegistryNode.create("standard.output.stdout", null);

		PinUtil.connectRaw(node1.getOutputPins().get(0), node2.getInputPins().get(0));

		Main.out.println("[begin]");
		node1.start();
		Main.out.println("[end]");
	}

	@Deprecated
	public static void main999(String[] args) throws Exception
	{
		File xml = new File("mpg.json");
		if (args.length > 0) {
			xml = new File(args[0]);
		}

		Object decode = JSON.decode(new FileInputStream(xml));

		Main.out.println(decode.getClass());
		Main.out.println(decode);
		Main.out.println(((ArrayList<?>) decode).stream()
			.map(i -> i.getClass())
			.collect(Collectors.toCollection(ArrayList::new)));

	}

}
