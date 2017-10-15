package bean;

public class Endereco{
	private int fk_cliente_cod;
	private String tipolog;
	private String log;
	private String bairro;
	private String municipio;
	private String cep;
	private String complemento;
	private String estado;

	public int getClientecod(){
		return this.fk_cliente_cod;
	}
	public String getTipolog(){
		return this.tipolog;
	}
	public String getLog(){
		return this.log;
	}
	public String getBairro(){
		return this.bairro;
	}
	public String getMunicipio(){
		return this.municipio;
	}
	public String getCep(){
		return this.cep;
	}
	public String getComplemento(){
		return this.complemento;
	}
	public String getEstado(){
		return this.estado;
	}

	public void setClientecod(int c){
		this.fk_cliente_cod = c;
	}
	public void setTipolog(String t){
		this.tipolog=t;
	}
	public void setLog(String l){
		this.log=l;
	}
	public void setBairro(String b){
		this.bairro=b;
	}
	public void setMunicipio(String m){
		this.municipio=m;
	}
	public void setCep(String c){
		this.cep=c;
	}
	public void setComplemento(String c){
		this.complemento=c;
	}
	public void setEstado(String e){
		this.estado=e;
	}

}