package bean;
//CLASSE RESPONSÁVEL POR ATUAR COMO 'MOLDE' DO PRODUTO
import javax.swing.ImageIcon;

public class Produto{

    //TODAS AS CARACTERISTICAS DO OBJETO
    //AS MESMAS DO BANCO DE DADOS
    private byte[] produto_foto;
    private ImageIcon produto_foto_para_tabela;
    private int produto_cod;
    private String produto_nome;
    private float preco_uni_compra;
    private float preco_uni_venda;
    private String categoria;
    private String descricao_produto;
    private String unidade_medida_peso;
    private float peso_produto;
    private int fk_estoque_cod;
    private int fk_fornecedor_cod;
    
    //METODOS RESPONSÁVEIS POR ACESSAR OS ATRIBUTOS OU EDITA-LOS

    public ImageIcon getProduto_foto_para_tabela() {
        //CONVERSÃO DE ARRAY DE BYTES PARA IMAGEICON        
        return new ImageIcon(this.produto_foto);
    }

    public void setProduto_foto_para_tabela(ImageIcon produto_foto_para_tabela) {
        this.produto_foto_para_tabela = produto_foto_para_tabela;
    }
    
    public int getFk_fornecedor_cod(){
        return this.fk_fornecedor_cod;
    }
    public void setFk_fornecedor_cod(int a){
        this.fk_fornecedor_cod = a;
    }

    public byte[] getProduto_foto() {
        return produto_foto;
    }

    public void setProduto_foto(byte[] produto_foto) {
        this.produto_foto = produto_foto;
    }

    public int getProduto_cod() {
        return produto_cod;
    }

    public void setProduto_cod(int produto_cod) {
        this.produto_cod = produto_cod;
    }

    public String getProduto_nome() {
        return produto_nome;
    }

    public void setProduto_nome(String produto_nome) {
        this.produto_nome = produto_nome;
    }

    public float getPreco_uni_compra() {
        return preco_uni_compra;
    }

    public void setPreco_uni_compra(float preco_uni_compra) {
        this.preco_uni_compra = preco_uni_compra;
    }

    public float getPreco_uni_venda() {
        return preco_uni_venda;
    }

    public void setPreco_uni_venda(float preco_uni_venda) {
        this.preco_uni_venda = preco_uni_venda;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao_produto() {
        return descricao_produto;
    }

    public void setDescricao_produto(String descricao_produto) {
        this.descricao_produto = descricao_produto;
    }

    public String getUnidade_medida_peso() {
        return unidade_medida_peso;
    }

    public void setUnidade_medida_peso(String unidade_medida_peso) {
        this.unidade_medida_peso = unidade_medida_peso;
    }

    public float getPeso_produto() {
        return peso_produto;
    }

    public void setPeso_produto(float peso_produto) {
        this.peso_produto = peso_produto;
    }

    public int getFk_estoque_cod() {
        return fk_estoque_cod;
    }

    public void setFk_estoque_cod(int fk_estoque_cod) {
        this.fk_estoque_cod = fk_estoque_cod;
    }
}
