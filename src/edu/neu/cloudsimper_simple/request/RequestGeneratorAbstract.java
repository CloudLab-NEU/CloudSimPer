package edu.neu.cloudsimper_simple.request;

import edu.neu.cloudsimper_simple.Component;
import edu.neu.cloudsimper_simple.region.Region;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class RequestGeneratorAbstract extends Component implements RequestGenerator {

	protected Region region;
	
	public RequestGeneratorAbstract() {
	}
	public final void setRegion(Region region) {
		this.region = region;
	}
}
