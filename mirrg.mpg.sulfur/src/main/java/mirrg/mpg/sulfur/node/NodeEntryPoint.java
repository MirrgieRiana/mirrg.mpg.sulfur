package mirrg.mpg.sulfur.node;

public abstract class NodeEntryPoint extends Node
{
	private Thread thread;

	public abstract void run();

	public Thread start()
	{
		thread = new Thread(this::run);
		thread.start();
		return thread;
	}

}
