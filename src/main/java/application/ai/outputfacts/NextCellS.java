package application.ai.outputfacts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("nextCellS")
public class NextCellS {
	@Param(0)
	private Integer x;
	
	@Param(1)
	private Integer y;
	
	@Param(2)
	private Integer nome;
	
	public NextCellS() {
		// TODO Auto-generated constructor stub
	}

	public NextCellS(Integer x, Integer y, Integer nome) {
		
		this.x = x;
		this.y = y;
		this.nome = nome;
	}
	
	
	public void setNome(Integer nome) {
		this.nome = nome;
	}
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public void setY(Integer y) {
		this.y = y;
	}
	
	public Integer getNome() {
		return nome;
	}
	
	public Integer getX() {
		return x;
	}
	
	public Integer getY() {
		return y;
	}

}
