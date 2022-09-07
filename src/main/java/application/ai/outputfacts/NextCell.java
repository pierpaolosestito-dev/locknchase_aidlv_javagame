package application.ai.outputfacts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("nextCell")
public class NextCell {
	@Param(0)
	private Integer X;
	@Param(1)
	private Integer Y;
	
	public NextCell() {
		// TODO Auto-generated constructor stub
	}

	public NextCell(Integer x, Integer y) {
	
		X = x;
		Y = y;
	}
	public Integer getX() {
		return X;
	}
	public void setX(Integer x) {
		X = x;
	}
	public Integer getY() {
		return Y;
	}
	public void setY(Integer y) {
		Y = y;
	}
}
