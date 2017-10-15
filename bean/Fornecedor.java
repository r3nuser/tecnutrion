package bean;

public class Fornecedor{
    //DADOS DE CADASTRO PADRÃO DE Fornecedor
    //APENAS NOME E ID SÃO NECESSÁRIOS 
    private int id;
    private String nome;
    
    public int getId(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public void setNome(String n){
        this.nome=n;
    }
    public void setId(int i){
        this.id=i;
    }
}