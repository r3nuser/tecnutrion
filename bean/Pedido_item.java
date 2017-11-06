package bean;

public class Pedido_item {

    private int fk_cod_cliente;
    private int fk_cod_pedido;
    private int fk_cod_produto;
    private int quantidade;
    private float pedido_item_vl_tot;

    public int getFk_cod_cliente() {
        return fk_cod_cliente;
    }

    public void setFk_cod_cliente(int fk_cod_cliente) {
        this.fk_cod_cliente = fk_cod_cliente;
    }

    public int getFk_cod_pedido() {
        return fk_cod_pedido;
    }

    public void setFk_cod_pedido(int fk_cod_pedido) {
        this.fk_cod_pedido = fk_cod_pedido;
    }

    public int getFk_cod_produto() {
        return fk_cod_produto;
    }

    public void setFk_cod_produto(int fk_cod_produto) {
        this.fk_cod_produto = fk_cod_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPedido_item_vl_tot() {
        return pedido_item_vl_tot;
    }

    public void setPedido_item_vl_tot(float pedido_item_vl_tot) {
        this.pedido_item_vl_tot = pedido_item_vl_tot;
    }
}
