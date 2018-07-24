package bean;

import java.sql.Date;

public class Pedido {

    private int cod_pedido;
    private Date dt_pedido;
    private float pedido_vl_tot;
    private String pagamento;
    private int desconto;

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }
    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public int getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(int cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public Date getDt_pedido() {
        return dt_pedido;
    }

    public void setDt_pedido(Date dt_pedido) {
        this.dt_pedido = dt_pedido;
    }

    public float getPedido_vl_tot() {
        return pedido_vl_tot;
    }

    public void setPedido_vl_tot(float pedido_vl_tot) {
        this.pedido_vl_tot = pedido_vl_tot;
    }

}
