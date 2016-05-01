package cz.martlin.hg6db.fsman;


import org.junit.Before;

import cz.martlin.hg6.config.Configuration;
import cz.martlin.hg6.config.Hg6Config;

public class Test {
	private final Configuration config = Hg6Config.get().createDefault();
	//private final FileSystemManTools fsmant = new FileSystemManTools(config);
	
	
	@Before
	public void before() {
		System.out.println("Using config: " + config);
	}

	
	//TODO do some tests?


}
