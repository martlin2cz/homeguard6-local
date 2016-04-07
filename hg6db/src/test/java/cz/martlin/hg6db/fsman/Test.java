package cz.martlin.hg6db.fsman;


import org.junit.Before;

import cz.martlin.hg5.logic.config.Configuration;
import cz.martlin.hg5.logic.processV1.fsman.FileSystemManTools;

public class Test {
	private final Configuration config = new Configuration();
	
	private final FileSystemManTools fsmant = new FileSystemManTools(config);
	
	
	@Before
	public void before() {
		System.out.println("Using config: " + config);
	}

	
	//TODO do some tests?


}
