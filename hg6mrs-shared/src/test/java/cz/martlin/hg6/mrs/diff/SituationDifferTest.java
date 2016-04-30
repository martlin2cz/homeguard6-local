package cz.martlin.hg6.mrs.diff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.hg6.mrs.situation.Status;

public class SituationDifferTest {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final SituationDiffer differ = new SituationDiffer();

	@Test
	public void firstBasicTest() {
		Situation s1 = new Situation();
		Situation s2 = new Situation();

		SituationsDiff diff1 = differ.computeDifferences(s1, s2);
		LOG.debug("Diff1: {}", diff1);
		assertTrue(diff1.isHasNoChanges());
	}

	@Test
	public void someSecondTest() {
		Situation s1 = new Situation();
		Situation s2 = new Situation();

		s1.setStatus(new Status());
		s1.getStatus().setCoreRunning(null);

		s2.setStatus(new Status());
		s2.getStatus().setCoreRunning(false);

		SituationsDiff diff1 = differ.computeDifferences(s1, s2);
		LOG.debug("Diff1: {}", diff1);

		assertFalse(diff1.isHasNoChanges());
		assertTrue(diff1.has(SituationDifference.CHANGED_CORE_STATUS));
	}

}
