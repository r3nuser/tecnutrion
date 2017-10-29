package bean;

public class Telefone{
	private int fk_cliente_cod;
	private String ddd;
	private String antesh;
	private String depoish;

	public int getFk_cliente_cod(){
		return this.fk_cliente_cod;
	}
	public String getDdd(){
		return this.ddd;
	}
	public String getAntesh(){
		return this.antesh;
	}
	public String getDepoish(){
		return this.depoish;
	}

	public void setFk_cliente_cod(int f){
		this.fk_cliente_cod=f;
	}
	public void setDdd(String d){
		this.ddd=d;
	}
	public void setAntesh(String a){
		this.antesh=a;
	}
	public void setDepoish(String d){
		this.depoish=d;
	}
}