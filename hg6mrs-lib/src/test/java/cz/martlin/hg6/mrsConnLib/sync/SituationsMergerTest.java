package cz.martlin.hg6.mrsConnLib.sync;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.test.TestingSituationsCreator;

public class SituationsMergerTest {
	private final TestingSituationsCreator tsc = new TestingSituationsCreator();
	private final SituationsMerger merger = new SituationsMerger();

	@Test
	public void testReflexivity() {
		Situation local = tsc.createBasic();
		Situation remote = tsc.createBasic();

		Situation actual = merger.computeNewSituation(local, remote);
		Situation expected = tsc.createBasic();

		check(actual, expected);
	}

	@Test
	public void testChangedReport() {
		Situation local = tsc.createNewerBasic();
		Situation remote = tsc.createBasic();

		Situation actual = merger.computeNewSituation(local, remote);
		Situation expected = tsc.createNewerBasic();

		check(actual, expected);
	}

	@Test
	public void testSomeChanges() {
		Situation local = tsc.createBasic();
		Situation remote = tsc.createBasic();
		remote.getStatus().setCoreRunning(true);
		remote.getConfig().setInterval(440);
		remote.getReport().setDescription("Changed description remotelly");

		Situation actual = merger.computeNewSituation(local, remote);

		Situation expected = tsc.createBasic();
		expected.getConfig().setTo(remote.getConfig());
		expected.getStatus().setCoreRunning(remote.getStatus().getCoreRunning());
		expected.getReport().setDescription(remote.getReport().getDescription());

		check(actual, expected);
	}

	private void check(Situation actual, Situation expected) {
		assertEquals("Different statuses", expected.getStatus(), actual.getStatus());
		assertEquals("Different configs", expected.getConfig(), actual.getConfig());
		assertEquals("Different reports", expected.getReport(), actual.getReport());

		assertEquals("Different", expected, actual);
	}

}
