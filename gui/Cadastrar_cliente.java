package gui;
//CLASSE RESPONSÁVEL PELO CADASTRO DE CLIENTES.

import bean.Cliente;
import bean.Endereco;
import bean.Telefone;
import dao.ClienteDAO;
import dao.EnderecoDAO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class Cadastrar_cliente extends JFrame {

    //TODOS COMPONENTES DA CLASSE.
    //DADOS DE USUÁRIO ATUAL
    private final String currentusername;
    private final String currentpassword;
    //OPÇÃO UTILIZADA PARA VERIFICAR SE O USUARIO QUER CADASTRAR ENDEREÇO
    private JCheckBox option = null;
    //BOTÃO DE CADASTRO
    private JButton cadastro = null;
    //TODOS CAMPOS DE TEXTO
    private JTextField nome = null;
    private JTextField ddd = null;
    private JTextField antesh = null;
    private JTextField depoish = null;
    private JTextField data_nascimento = null;
    private JTextField tipolog = null;
    private JTextField log = null;
    private JTextField bairro = null;
    private JTextField municipio = null;
    private JTextField cep = null;
    private JTextField complemento = null;
    private JTextField estado = null;
    //TODOS LABELS INFORMATIVOS
    private JLabel nome_l = null;
    private JLabel telefone_l = null;
    private JLabel data_nascimento_l = null;
    private JLabel tipolog_l = null;
    private JLabel log_l = null;
    private JLabel bairro_l = null;
    private JLabel municipio_l = null;
    private JLabel cep_l = null;
    private JLabel estado_l = null;
    private JLabel complemento_l = null;
    private JLabel cadastrar_cliente = null;

    //CHAMADA DA CLASSE.
    public Cadastrar_cliente(String username, String password) {
        initComponents();
        this.currentusername = username;
        this.currentpassword = password;
    }
    //METODO INICIALIZADOR DE COMPONENTES.

    private void initComponents() {

        formatarTextboxes();
        this.option = new JCheckBox("Cadastrar Endereço");
        this.cadastro = new JButton("Cadastrar !");

        this.nome = new JTextField();
        this.tipolog = new JTextField();
        this.log = new JTextField();
        this.bairro = new JTextField();
        this.municipio = new JTextField();
        this.estado = new JTextField();
        this.complemento = new JTextField();

        this.nome_l = new JLabel("Nome Completo:*");
        this.telefone_l = new JLabel("Telefone:*");
        this.data_nascimento_l = new JLabel("Data de Nascimento:");
        this.tipolog_l = new JLabel("Tipo de Logradouro:*");
        this.log_l = new JLabel("Logradouro:*");
        this.bairro_l = new JLabel("Bairro:*");
        this.municipio_l = new JLabel("Municipio:*");
        this.cep_l = new JLabel("CEP:*");
        this.estado_l = new JLabel("Estado:*");
        this.complemento_l = new JLabel("Complemento:");
        this.cadastrar_cliente = new JLabel("Cadastrar Novo Cliente:");

        this.nome_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.telefone_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.data_nascimento_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.tipolog_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.log_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.bairro_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.bairro_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.municipio_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.cep_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.estado_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.complemento_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.cadastrar_cliente.setFont(new java.awt.Font("Dialog", 1, 16));

        getContentPane().add(this.cadastro);
        getContentPane().add(this.option);

        getContentPane().add(this.nome);
        getContentPane().add(this.ddd);
        getContentPane().add(this.antesh);
        getContentPane().add(this.depoish);
        getContentPane().add(this.data_nascimento);
        getContentPane().add(this.tipolog);
        getContentPane().add(this.log);
        getContentPane().add(this.bairro);
        getContentPane().add(this.municipio);
        getContentPane().add(this.cep);
        getContentPane().add(this.estado);
        getContentPane().add(this.complemento);

        getContentPane().add(this.nome_l);
        getContentPane().add(this.telefone_l);
        getContentPane().add(this.data_nascimento_l);
        getContentPane().add(this.log_l);
        getContentPane().add(this.tipolog_l);
        getContentPane().add(this.bairro_l);
        getContentPane().add(this.municipio_l);
        getContentPane().add(this.cep_l);
        getContentPane().add(this.estado_l);
        getContentPane().add(this.complemento_l);
        getContentPane().add(this.cadastrar_cliente);

        this.nome.setBounds(12, 146, 585, 19);
        this.ddd.setBounds(12, 192, 34, 19);
        this.antesh.setBounds(52, 192, 54, 19);
        this.depoish.setBounds(112, 192, 44, 19);
        this.data_nascimento.setBounds(230, 192, 80, 19);
        this.tipolog.setBounds(12, 285, 250, 19);
        this.log.setBounds(12, 331, 585, 19);
        this.bairro.setBounds(12, 377, 149, 19);
        this.municipio.setBounds(173, 377, 200, 19);
        this.cep.setBounds(12, 423, 100, 19);
        this.complemento.setBounds(125, 423, 200, 19);
        this.estado.setBounds(310, 285, 20, 19);

        this.nome_l.setBounds(12, 125, 150, 17);
        this.telefone_l.setBounds(12, 171, 83, 17);
        this.data_nascimento_l.setBounds(230, 171, 200, 17);
        this.tipolog_l.setBounds(12, 264, 200, 17);
        this.log_l.setBounds(12, 310, 130, 17);
        this.estado_l.setBounds(300, 264, 90, 17);
        this.bairro_l.setBounds(12, 356, 60, 17);
        this.municipio_l.setBounds(173, 356, 87, 17);
        this.cep_l.setBounds(12, 402, 43, 17);
        this.complemento_l.setBounds(125, 402, 115, 17);
        this.cadastrar_cliente.setBounds(12, 50, 500, 40);

        this.option.setBounds(12, 223, 166, 23);
        this.cadastro.setBounds(230, 455, 114, 25);

        fechaEndereco();

        //EVENTOS DO BOTÃO DE CADASTRAR E DA CHECKBOX.
        this.cadastro.addActionListener((ActionEvent evento) -> {
            Cliente c = null;
            Telefone t = null;
            try {
                c = new Cliente();
                c.setNome(this.nome.getText());
                //CONVERSÃO DE STRING PARA DATA DE NASCIMENTO NO FORMATO DO MYSQL
                //ANO-MES-DIA
                String dataNascimento = this.data_nascimento.getText();
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    java.sql.Date data = new java.sql.Date(fmt.parse(dataNascimento).getTime());
                    c.setData_nascimento(data);
                } catch (Exception e) {
                    System.out.println(e);
                }
                //PEGANDO TODOS DADOS DO TELEFONE
                //DDD= DDD
                //NUMEROS ANTES DO HIFEN = ANTESH
                //NUMEROS DEPOIS DO HIFEN = DEPOISH
                t = new Telefone();
                t.setDdd(this.ddd.getText());
                t.setAntesh(this.antesh.getText());
                t.setDepoish(this.depoish.getText());
                
                ClienteDAO.create(this.currentusername, this.currentpassword, c,t);
                //CONDIÇÃO QUE CONTROLA SE O USUÁRIO CLICOU EM CADASTRAR O ENDEREÇO
                //CASO SIM, CHAMA O METODO DE CADASTRO DE ENDEREÇO
                if (option.isSelected()) {
                    Endereco e = new Endereco();
                    e.setBairro(this.bairro.getText());
                    e.setCep(this.cep.getText());
                    e.setComplemento(this.complemento.getText());
                    e.setEstado(this.estado.getText());
                    e.setLog(this.log.getText());
                    e.setMunicipio(this.municipio.getText());
                    e.setTipolog(this.tipolog.getText());
                    EnderecoDAO.create(currentusername, currentpassword, e, c);
                }
                dispose();
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        //OPÇÃO QUE ABRE E FECHA OS CAMPOS DO ENDEREÇO PARA CADASTRO.
        option.addActionListener((ActionEvent evento) -> {
            if (option.isSelected()) {
                abreEndereco();
            } else {
                fechaEndereco();
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(610, 510);
        setTitle("Cadastro de novo cliente !");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    //METODO DE FORMATAÇÃO DE TODAS AS CAIXAS QUE NECESSITAM DE MASCARAS
    private void formatarTextboxes() {
        try {
            this.ddd = new JFormattedTextField(new MaskFormatter("###"));
            this.antesh = new JFormattedTextField(new MaskFormatter("#####"));
            this.depoish = new JFormattedTextField(new MaskFormatter("####"));
            this.cep = new JFormattedTextField(new MaskFormatter("#####-###"));
            this.data_nascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //ABRINDO ENDEREÇO PARA EDIÇÃO DE DADOS.
    private void abreEndereco() {
        this.tipolog.setEnabled(true);
        this.log.setEnabled(true);
        this.bairro.setEnabled(true);
        this.municipio.setEnabled(true);
        this.estado.setEnabled(true);
        this.cep.setEnabled(true);
        this.complemento.setEnabled(true);

        this.tipolog_l.setForeground(new Color(00, 00, 00));
        this.log_l.setForeground(new Color(00, 00, 00));
        this.bairro_l.setForeground(new Color(00, 00, 00));
        this.bairro_l.setForeground(new Color(00, 00, 00));
        this.municipio_l.setForeground(new Color(00, 00, 00));
        this.cep_l.setForeground(new Color(00, 00, 00));
        this.estado_l.setForeground(new Color(00, 00, 00));
        this.complemento_l.setForeground(new Color(00, 00, 00));

    }

    //FECHANDO ENDEREÇO PARA EDIÇÃO DE DADOS.
    private void fechaEndereco() {
        this.tipolog.setEnabled(false);
        this.log.setEnabled(false);
        this.bairro.setEnabled(false);
        this.municipio.setEnabled(false);
        this.cep.setEnabled(false);
        this.estado.setEnabled(false);
        this.complemento.setEnabled(false);
        this.tipolog.setText("");
        this.log.setText("");
        this.bairro.setText("");
        this.municipio.setText("");
        this.cep.setText("");
        this.complemento.setText("");

        this.tipolog_l.setForeground(new Color(200, 200, 200));
        this.log_l.setForeground(new Color(200, 200, 200));
        this.bairro_l.setForeground(new Color(200, 200, 200));
        this.bairro_l.setForeground(new Color(200, 200, 200));
        this.municipio_l.setForeground(new Color(200, 200, 200));
        this.cep_l.setForeground(new Color(200, 200, 200));
        this.estado_l.setForeground(new Color(200, 200, 200));
        this.complemento_l.setForeground(new Color(200, 200, 200));

    }
}
