package bean;

import java.sql.Date;

public class Estoque {
    private int estoque_cod;
    private int qnt_estoque;
    private Date validade;

    public int getEstoque_cod() {
        return estoque_cod;
    }

    public void setEstoque_cod(int estoque_cod) {
        this.estoque_cod = estoque_cod;
    }

    public int getQnt_estoque() {
        return qnt_estoque;
    }

    public void setQnt_estoque(int qnt_estoque) {
        this.qnt_estoque = qnt_estoque;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }
    
}
