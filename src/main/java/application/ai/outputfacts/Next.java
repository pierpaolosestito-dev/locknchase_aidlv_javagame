package application.ai.outputfacts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("next")
public class Next {
	@Param(0)
	private Integer direction;
	
	public Next() {
		// TODO Auto-generated constructor stub
	}
	
	public Next(Integer direction) {
		this.direction=direction;
	}
	
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public Integer getDirection() {
		return direction;
	}
}
