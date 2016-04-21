package cz.martlin.hg6.mrs.diff;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.mrs.situation.GuardingStatus;
import cz.martlin.hg6.mrs.situation.Situation;

public class SituationDifferTest {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final SituationDiffer differ = new SituationDiffer();

	@Test
	public void firstBasicTest() {
		Situation s1 = new Situation();
		Situation s2 = new Situation();

		SituationsDiff diff1 = differ.computeDifferences(s1, s2);
		LOG.debug("Diff1: {}", diff1);
		assertTrue(diff1.hasNoChanges());
	}

	@Test
	public void someSecondTest() {
		Situation s1 = new Situation();
		Situation s2 = new Situation();

		s1.setStatus(GuardingStatus.STARTED);
		s2.setStatus(GuardingStatus.STOPPED);

		SituationsDiff diff1 = differ.computeDifferences(s1, s2);
		LOG.debug("Diff1: {}", diff1);

		assertFalse(diff1.hasNoChanges());
		assertTrue(diff1.has(SituationDifference.CHANGED_STATUS));
	}

}
