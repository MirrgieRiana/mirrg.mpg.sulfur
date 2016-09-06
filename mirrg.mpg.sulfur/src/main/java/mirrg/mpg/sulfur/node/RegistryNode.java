package mirrg.mpg.sulfur.node;

import java.util.Hashtable;
import java.util.function.Function;

public class RegistryNode
{

	private static Hashtable<String, Function<Object, Node>> registry = new Hashtable<>();

	public static void register(String name, Function<Object, Node> creator)
	{
		registry.put(name, creator);
	}

	public static Node create(String name, Object argument)
	{
		return registry.get(name).apply(argument);
	}

}
