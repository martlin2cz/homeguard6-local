package cz.martlin.hg6.mrsConnJRest;

import cz.martlin.hg6.config.Hg6ConfigException;
import cz.martlin.hg6.mrs.misc.Hg6MrsException;
import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;

public interface Hg6MrsConnCmdsProcessor extends JarmilTarget {

	public void startLoop() throws Hg6MrsException;

	public void stopLoop() throws Hg6MrsException;

	public void synchronize() throws Hg6MrsException;

	public boolean isLoopRunning() throws Hg6MrsException;

	public void configChanged() throws Hg6ConfigException;
}
