package cz.martlin.hg6.mrsJRest;

import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;

public interface Hg6MrsCmdsProcessor extends JarmilTarget {

	public void startLoop();

	public void stopLoop();

	public void synchronize() throws Hg6MrsException;

	public boolean isLoopRunning();
}
