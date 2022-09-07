package application.ai.outputfacts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("nextS")
public class NextS {
	@Param(0)
	private Integer direction;
	@Param(1)
	private Integer policemanName;
	
	public NextS() {
		// TODO Auto-generated constructor stub
	}

	public NextS(Integer direction, Integer policemanName) {
	
		this.direction = direction;
		this.policemanName = policemanName;
	}
	
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public void setPolicemanName(Integer policemanName) {
		this.policemanName = policemanName;
	}
	public Integer getDirection() {
		return direction;
	}
	public Integer getPolicemanName() {
		return policemanName;
	}
}
