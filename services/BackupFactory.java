public class BackupFactory extends Sql{

    public BackupFactory(){
        
    }

    static void AllTimeBackup(){
        ArrayList<Cliente> clientes = ClienteDAO().read("root","root");
        //telefones e endereços
        ArrayList<Produto> produtos = ProdutoDAO().read("root","root")
        //estoques + pedidos + pedido_item
        ArrayList<Fornecedor> fornecedores = FornecedorDAO().read("root","root");
        
    }

    static void BackupDate(){

    }

    private Date converterData(String datatexto){
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date data = null;
        try{
            data = new java.sql.Date(fmt.parse(datatexto).getTime());
        }catch(Exception e){
            System.out.println(e);
        }
        return data;
    }

    

}