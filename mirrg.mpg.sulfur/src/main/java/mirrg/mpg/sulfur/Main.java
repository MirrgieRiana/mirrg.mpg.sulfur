package mirrg.mpg.sulfur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import mirrg.mpg.sulfur.node.Node;
import mirrg.mpg.sulfur.node.NodeEntryPoint;
import mirrg.mpg.sulfur.node.RegistryNode;
import mirrg.mpg.sulfur.node.pin.PinUtil;
import mirrg.mpg.sulfur.nodes.converter.NodeCSV2Struct;
import mirrg.mpg.sulfur.nodes.converter.NodeFlatMap;
import mirrg.mpg.sulfur.nodes.converter.NodeIS2Image;
import mirrg.mpg.sulfur.nodes.converter.NodeImage2OS;
import mirrg.mpg.sulfur.nodes.converter.NodeImageNegation;
import mirrg.mpg.sulfur.nodes.converter.NodeLine2CSV;
import mirrg.mpg.sulfur.nodes.converter.NodeLine2Extracted;
import mirrg.mpg.sulfur.nodes.converter.NodeStruct2Elements;
import mirrg.mpg.sulfur.nodes.converter.NodeToString;
import mirrg.mpg.sulfur.nodes.input.NodeInputCSV;
import mirrg.mpg.sulfur.nodes.input.NodeInputInterval;
import mirrg.mpg.sulfur.nodes.input.NodeInputLines;
import mirrg.mpg.sulfur.nodes.input.NodeInputSerialLine;
import mirrg.mpg.sulfur.nodes.input.NodeInputStream;
import mirrg.mpg.sulfur.nodes.output.NodeOutputStdout;
import mirrg.mpg.sulfur.nodes.output.NodeOutputStream;

public class Main
{

	public static PrintStream out = System.out;

	static {
		RegistryNode.register("standard.input.file.line", a -> new NodeInputLines(new File((String) a)));
		RegistryNode.register("standard.input.file.csv", a -> new NodeInputCSV(new File((String) a)));
		RegistryNode.register("standard.input.interval", a -> new NodeInputInterval());
		RegistryNode.register("standard.input.stream.bytes", a -> {
			try {
				return new NodeInputStream(new FileInputStream(new File((String) a)), 1024);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		});
		RegistryNode.register("standard.input.serial.line", a -> {
			SerialPort port;
			try {
				port = (SerialPort) CommPortIdentifier.getPortIdentifier((String) a).open("MPG", 2000);
			} catch (PortInUseException e) {
				throw new RuntimeException(e);
			} catch (NoSuchPortException e) {
				throw new RuntimeException(e);
			}

			try {
				port.setSerialPortParams(
					9600,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			} catch (UnsupportedCommOperationException e) {
				throw new RuntimeException(e);
			}

			try {
				return new NodeInputSerialLine(port.getInputStream());
			} catch (IOException e) {
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
		RegistryNode.register("standard.converter.line.csv", a -> new NodeLine2CSV());
		RegistryNode.register("standard.converter.line.extracted", a -> new NodeLine2Extracted());
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
		Config config = Config.load(fileJson);

		Hashtable<String, Node> nodes = new Hashtable<>();
		Stream.of(config.nodes)
			.forEach(c -> nodes.put(c.id, RegistryNode.create(c.name, c.argument)));
		Stream.of(config.connections)
			.forEach(c -> PinUtil.connectRaw(
				nodes.get(c.from.node).getPinOutputs().get(c.from.pin),
				nodes.get(c.to.node).getPinInputs().get(c.to.pin)));

		return nodes.values().stream()
			.filter(NodeEntryPoint.class::isInstance)
			.map(NodeEntryPoint.class::cast)
			.map(NodeEntryPoint::start)
			.collect(Collectors.toCollection(ArrayList::new));
	}

}
