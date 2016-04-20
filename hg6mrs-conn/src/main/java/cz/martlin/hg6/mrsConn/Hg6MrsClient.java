package cz.martlin.hg6.mrsConn;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cz.martlin.hg6.mrs.Hg6MrsException;
import cz.martlin.hg6.mrs.Protocol;
import cz.martlin.hg6.mrs.SituationSerialization;
import cz.martlin.hg6.mrs.situation.Situation;

public class Hg6MrsClient {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final Client client;
	private final WebResource getRemSitResource;
	private final WebResource updateLocSitResource;

	private final SituationSerialization serialization;

	public Hg6MrsClient(String baseURL) throws Hg6MrsException {
		super();
		this.client = Client.create();
		this.getRemSitResource = client.resource(getRemSitResourceURI(baseURL));
		this.updateLocSitResource = client.resource(updateLocSitResourceURI(baseURL));
		this.serialization = new SituationSerialization();

		LOG.debug("HG6 mrs client ready with base url " + baseURL);
	}

	private static URI updateLocSitResourceURI(String base) throws Hg6MrsException {
		StringBuilder result = new StringBuilder(base);
		result.append("/");
		result.append(Protocol.WS_ROOT_PATH);
		result.append(Protocol.SUBMIT_SERVER_SITUATION_PATH);

		try {
			return new URI(result.toString());
		} catch (URISyntaxException e) {
			throw new Hg6MrsException("Invalid syntax of update local situation resource URI", e);
		}
	}

	private static URI getRemSitResourceURI(String base) throws Hg6MrsException {
		StringBuilder result = new StringBuilder(base);
		result.append("/");
		result.append(Protocol.WS_ROOT_PATH);
		result.append(Protocol.GET_SERVER_SITUATION_PATH);

		try {
			return new URI(result.toString());
		} catch (URISyntaxException e) {
			throw new Hg6MrsException("Invalid syntax of get remote situation resource URI", e);
		}
	}

	public Situation getRemoteSituation() throws Hg6MrsException {
		LOG.trace("Getting remote situation");

		String xml;
		try {
			ClientResponse response = getRemSitResource//
					.accept(MediaType.APPLICATION_XML_TYPE)//
					.get(ClientResponse.class);
			xml = response.getEntity(String.class);
		} catch (Exception e) {
			throw new Hg6MrsException("Seding get remote situation failed", e);
		}

		Situation situation = serialization.deserialize(xml);

		LOG.debug("Got remote situation: " + situation);
		return situation;

	}

	public void submitLocalSituation(Situation situation) throws Hg6MrsException {
		LOG.debug("Submitting local situation: " + situation);

		String xml = serialization.serialize(situation);

		String text;
		try {
			ClientResponse response = updateLocSitResource//
					.accept(MediaType.TEXT_PLAIN_TYPE)//
					.entity(xml, MediaType.APPLICATION_XML_TYPE)//
					.post(ClientResponse.class);
			text = response.getEntity(String.class);
		} catch (Exception e) {
			throw new Hg6MrsException("Seding submit local situation failed", e);
		}

		if (!Protocol.OK_RESPONSE.equals(text)) {
			throw new Hg6MrsException("Server did not respond " + Protocol.OK_RESPONSE + ", but " + text);
		}

		LOG.trace("Submitted local situation: " + situation + ", xml: " + xml);
	}

}
