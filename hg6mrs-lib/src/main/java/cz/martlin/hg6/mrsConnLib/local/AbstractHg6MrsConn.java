package cz.martlin.hg6.mrsConnLib.local;

import cz.martlin.hg6.mrs.misc.Hg6MrsException;

public interface AbstractHg6MrsConn {

	boolean isLoopRunning() throws Hg6MrsException;

	void startLoop() throws Hg6MrsException;

	void stopLoop() throws Hg6MrsException;

}
