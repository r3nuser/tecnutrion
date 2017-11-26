package bean;

import java.sql.Date;
public class Cliente {
    //DADOS DE CADASTRO PADRÃO DE CLIENTE
    //ID, NOME DO CLIENTE E DATA DE NASCIMENTO DO CLIENTE.

    private int id;
    private String nome;
    private Date data_nascimento;
    private String email;
    
    //GETTERS E SETTERS
    public int getId() {
        return this.id;
    }
    

    public String getNome() {
        return this.nome;
    }

    public Date getData_nascimento() {
        return this.data_nascimento;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public void setData_nascimento(Date data) {
        this.data_nascimento = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
