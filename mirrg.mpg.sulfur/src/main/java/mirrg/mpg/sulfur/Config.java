package mirrg.mpg.sulfur;

public class Config
{

	public ConfigNode[] nodes;
	public ConfigConnection[] connections;

	public static class ConfigNode
	{

		public String name;
		public String id;
		public Object argument;

	}

	public static class ConfigConnection
	{

		public ConfigDestination from;
		public ConfigDestination to;

	}

	public static class ConfigDestination
	{

		public String node;
		public int pin;

	}

}
