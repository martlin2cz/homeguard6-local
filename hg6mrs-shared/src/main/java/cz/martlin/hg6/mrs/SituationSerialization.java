package cz.martlin.hg6.mrs;

import cz.martlin.hg6.mrs.situation.Situation;
import cz.martlin.jaxon.config.Config;
import cz.martlin.jaxon.jaxon.JaxonConverter;
import cz.martlin.jaxon.jaxon.JaxonException;

public class SituationSerialization {

	private final JaxonConverter jaxon;

	public SituationSerialization() {
		Config config = new Config();
		jaxon = new JaxonConverter(config);
	}

	public String serialize(Situation situation) throws Hg6MrsException {
		try {
			return jaxon.objectToString(situation);
		} catch (JaxonException e) {
			throw new Hg6MrsException("Cannot serialize situation " + situation, e);
		}
	}

	public Situation deserialize(String xml) throws Hg6MrsException {
		try {
			return (Situation) jaxon.objectFromString(xml);
		} catch (JaxonException e) {
			throw new Hg6MrsException("Cannot deserialize situation from xml " + xml, e);
		}
	}
}
