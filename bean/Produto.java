package bean;

public class Produto{

	private byte[] produto_foto;
	private int produto_cod;
	private String produto_nome;
	private int fk_cod_fornecedor;
	private float preco_uni_compra;
	private float preco_uni_venda;
	private String categoria;
	private String descricao_produto;
	private String unidade_medida_peso;
	private float peso_produto;
	private int fk_estoque_cod;

	public int getProduto_cod(){
		return this.produto_cod;
	}
	public String getProduto_nome(){
		return this.produto_nome;
	}
	public int getFk_cod_fornecedor(){
		return this.fk_cod_fornecedor;
	}
	public float getPreco_uni_compra(){
		return this.preco_uni_compra;
	}
	public float getPreco_uni_venda(){
		return this.preco_uni_venda;
	}
	public String getCategoria(){
		return this.categoria;
	}
	public String getDescricao_produto(){
		return this.descricao_produto;
	}
	public String getUnidade_medida_peso(){
		return this.unidade_medida_peso;
	}
	public float getPeso_produto(){
		return this.peso_produto;
	}
	public int getFk_estoque_cod(){
		return this.fk_estoque_cod;
	}

	private void setProduto_cod(int p){
		this.produto_cod=p;
	}
	private void setProduto_nome(String n){
		this.produto_nome=n;
	}
	private void setFk_cod_fornecedor(int c){
		this.fk_cod_fornecedor=c;
	}
	private void setPreco_uni_compra(float f){
		this.preco_uni_compra=f;
	}
	private void setPreco_uni_venda(float f){
		this.preco_uni_venda=f;
	}
	private void setCategoria(String c){
		this.categoria=c;
	}
	private void setDescricao_produto(String d){
		this.descricao_produto=d;
	}
	private void setUnidade_medida_peso(String u){
		this.unidade_medida_peso=u;
	}
	private void setPeso_produto(float p){
		this.peso_produto=p;
	}
	private void setFk_estoque_cod(int e){
		this.fk_estoque_cod=e;
	}
	}
		

}