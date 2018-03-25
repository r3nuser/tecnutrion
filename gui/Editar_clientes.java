package gui;

import bean.Cliente;
import bean.Endereco;
import bean.Telefone;
import dao.ClienteDAO;
import dao.EnderecoDAO;
import dao.MiscDAO;
import dao.TelefoneDAO;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

public class Editar_clientes extends JFrame {

    //TODOS COMPONENTES DA CLASSE.
    //DADOS DE USUÁRIO ATUAL
    private final String username;
    private final String password;
    private Cliente c;
    //OPÇÃO UTILIZADA PARA VERIFICAR SE O USUARIO QUER CADASTRAR ENDEREÇO
    private JCheckBox option = null;
    private byte mode;
    //BOTÃO DE CADASTRO
    private JButton cadastro = null;
    //TODOS CAMPOS DE TEXTO
    private JTextField nome = null;
    private JTextField email = null;
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
    private JButton add;
    private JButton rm;
    private JLabel telefone_l;
    private JTable tabela_telefone;
    private DefaultTableModel dtm_telefone;
    private JScrollPane scroll_telefone;
    //TODOS LABELS INFORMATIVOS
    private JLabel nome_l = null;
    private JLabel email_l = null;
    private JLabel data_nascimento_l = null;
    private JLabel tipolog_l = null;
    private JLabel log_l = null;
    private JLabel bairro_l = null;
    private JLabel municipio_l = null;
    private JLabel cep_l = null;
    private JLabel estado_l = null;
    private JLabel complemento_l = null;
    private JLabel cadastrar_cliente = null;
    
    private String campos_invalidos;

    public Editar_clientes(String currentusername, String currentpassword, int id) {
        this.username = currentusername;
        this.password = currentpassword;
        c = MiscDAO.search_cliente_por_id(username, password, id);
        initAll();
    }

    //METODO INICIALIZADOR DE COMPONENTES.
    private void initAll() {
        dtm_telefone = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        dtm_telefone.addColumn("DDD");
        dtm_telefone.addColumn("Prefixo");
        dtm_telefone.addColumn("Sufixo");
        tabela_telefone = new JTable(dtm_telefone);
        scroll_telefone = new JScrollPane(tabela_telefone);

        formatarTextboxes();
        this.option = new JCheckBox("Atualizar Endereço");
        this.cadastro = new JButton("Atualizar !");
        this.add = new JButton("", new ImageIcon(getClass().getResource("abas/ico_mais.png")));
        this.rm = new JButton("", new ImageIcon(getClass().getResource("abas/ico_deletar.png")));

        this.add.setBackground(new Color(30, 30, 30));
        this.add.setForeground(new Color(255, 255, 255));
        rm.setBackground(new Color(30, 30, 30));
        rm.setForeground(new Color(255, 255, 255));

        this.nome = new JTextField();
        this.email = new JTextField();
        this.tipolog = new JTextField();
        this.log = new JTextField();
        this.bairro = new JTextField();
        this.municipio = new JTextField();
        this.estado = new JTextField();
        this.complemento = new JTextField();

        this.nome_l = new JLabel("Nome Completo:*");
        this.email_l = new JLabel("Email:*");
        this.telefone_l = new JLabel("Nº de Contato:*");
        this.data_nascimento_l = new JLabel("Data de Nascimento:");
        this.tipolog_l = new JLabel("Tipo de Logradouro:*");
        this.log_l = new JLabel("Logradouro:*");
        this.bairro_l = new JLabel("Bairro:*");
        this.municipio_l = new JLabel("Municipio:*");
        this.cep_l = new JLabel("CEP:*");
        this.estado_l = new JLabel("Estado:*");
        this.complemento_l = new JLabel("Complemento:");
        this.cadastrar_cliente = new JLabel("Editar cliente ID = " + c.getId());

        this.nome_l.setFont(new java.awt.Font("Dialog", 1, 14));
        this.email.setFont(new java.awt.Font("Dialog", 1, 14));
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

        add(this.cadastro);
        add(this.option);

        add(this.nome);
        add(this.email);
        add(this.ddd);
        add(this.antesh);
        add(this.depoish);
        add(this.data_nascimento);
        add(this.tipolog);
        add(this.log);
        add(this.bairro);
        add(this.municipio);
        add(this.cep);
        add(this.estado);
        add(this.complemento);

        add(this.nome_l);
        add(this.email_l);
        add(this.telefone_l);
        add(this.data_nascimento_l);
        add(this.log_l);
        add(this.tipolog_l);
        add(this.bairro_l);
        add(this.municipio_l);
        add(this.cep_l);
        add(this.estado_l);
        add(this.complemento_l);
        add(this.cadastrar_cliente);
        add(this.rm);
        add(this.add);

        add(this.scroll_telefone);

        this.nome.setBounds(12, 102, 585, 19);
        this.email.setBounds(12, 146, 585, 19);
        this.ddd.setBounds(230, 192, 34, 19);
        this.antesh.setBounds(235 + 34, 192, 54, 19);
        this.depoish.setBounds(235 + 34 + 54 + 5, 192, 44, 19);
        this.data_nascimento.setBounds(12, 192, 80, 19);
        this.tipolog.setBounds(12, 285, 250, 19);
        this.log.setBounds(12, 331, 585, 19);
        this.bairro.setBounds(12, 377, 149, 19);
        this.municipio.setBounds(173, 377, 200, 19);
        this.cep.setBounds(12, 423, 100, 19);
        this.complemento.setBounds(125, 423, 200, 19);
        this.estado.setBounds(310, 285, 20, 19);

        this.nome_l.setBounds(12, 125 - (171 - 125), 150, 17);
        this.email_l.setBounds(12, 125, 150, 17);
        this.telefone_l.setBounds(230, 171, 140, 17);
        this.data_nascimento_l.setBounds(12, 171, 200, 17);
        this.tipolog_l.setBounds(12, 264, 200, 17);
        this.log_l.setBounds(12, 310, 130, 17);
        this.estado_l.setBounds(300, 264, 90, 17);
        this.bairro_l.setBounds(12, 356, 60, 17);
        this.municipio_l.setBounds(173, 356, 87, 17);
        this.cep_l.setBounds(12, 402, 43, 17);
        this.complemento_l.setBounds(125, 402, 115, 17);
        this.cadastrar_cliente.setBounds(12, 50, 500, 40);

        this.add.setBounds(235 + 34 + 54 + 5, 192 + 25, 20, 20);
        this.rm.setBounds(235 + 34 + 54 + 5 + 25, 192 + 25, 20, 20);

        this.scroll_telefone.setBounds(230 + 140 + 10, 171, 200, 100);

        this.option.setBounds(12, 223, 166, 23);
        this.cadastro.setBounds(230, 455, 114, 25);

        preenche_dados();

        //EVENTOS DO BOTÃO DE CADASTRAR E DA CHECKBOX.
        //OPÇÃO QUE ABRE E FECHA OS CAMPOS DO ENDEREÇO PARA CADASTRO.
        option.addActionListener((ActionEvent evento) -> {
            if (option.isSelected()) {
                abreEndereco();
            } else {
                fechaEndereco();
            }
        });
        cadastro.addActionListener((ActionEvent) -> {
            if (validacao()) {
                try {
                    c.setNome(this.nome.getText());
                    DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        java.sql.Date data = new java.sql.Date(fmt.parse(this.data_nascimento.getText()).getTime());
                        c.setData_nascimento(data);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    c.setEmail(this.email.getText());
                    ClienteDAO.update(username, password, c);

                    Telefone t = new Telefone();
                    t.setFk_cliente_cod(c.getId());
                    TelefoneDAO.delete(username, password, t);

                    for (int i = 0; i < tabela_telefone.getRowCount(); i++) {
                        t = new Telefone();
                        t.setFk_cliente_cod(c.getId());
                        t.setDdd("" + tabela_telefone.getValueAt(i, 0));
                        t.setAntesh("" + tabela_telefone.getValueAt(i, 1));
                        t.setDepoish("" + tabela_telefone.getValueAt(i, 2));
                        TelefoneDAO.create(this.username, this.password, t);
                    }
                    Endereco e = new Endereco();
                    e.setBairro(this.bairro.getText());
                    e.setCep(this.cep.getText());
                    e.setComplemento(this.complemento.getText());
                    e.setEstado(this.estado.getText());
                    e.setLog(this.log.getText());
                    e.setMunicipio(this.municipio.getText());
                    e.setTipolog(this.tipolog.getText());
                    e.setClientecod(c.getId());
                    if (this.mode == 0) {
                        EnderecoDAO.update(username, password, e);
                    } else {
                        EnderecoDAO.create(username, password, e);
                    }

                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    dispose();
                }
            }else{
                JOptionPane.showMessageDialog(null,campos_invalidos);
            }
        });
        add.addActionListener((ActionEvent) -> {
            dtm_telefone.addRow(new Object[]{ddd.getText(), antesh.getText(), depoish.getText()});
            ddd.setText("");
            antesh.setText("");
            depoish.setText("");
        });
        rm.addActionListener((ActionEvent) -> {
            try {
                dtm_telefone.removeRow(tabela_telefone.getSelectedRow());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Impossivel remover, selecione uma coluna na tabela e tente novamente !");
            }
        });

        setLayout(null);
        setSize(610, 510);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        URL url = this.getClass().getResource("abas/ico_cadastro.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        setTitle("Editar cliente ID:" + c.getId());
        setVisible(true);

    }
    
     private boolean validacao() {
        Boolean completo = true;

        campos_invalidos = "Por Favor, Preencha O(s) Seguinte(s) Campo(s) Obrigatório(s):";

        if ("".equals(nome.getText())) {
            nome.setBorder(BorderFactory.createLineBorder(Color.red));
            completo = false;
            campos_invalidos += "Nome";
        } else {
            nome.setBorder(BorderFactory.createLineBorder(Color.green));

        }
        if ("".equals(email.getText())) {
            email.setBorder(BorderFactory.createLineBorder(Color.red));
            completo = false;
            campos_invalidos += " Email";
        } else {
            email.setBorder(BorderFactory.createLineBorder(Color.green));
        }

        if (option.isSelected()) {

            if (tipolog.getText().equals("")) {
                tipolog.setBorder(BorderFactory.createLineBorder(Color.red));
                completo = false;
                campos_invalidos += " Tipo de Logradouro";
            } else {
                tipolog.setBorder(BorderFactory.createLineBorder(Color.green));
            }

            if (log.getText().equals("")) {
                log.setBorder(BorderFactory.createLineBorder(Color.red));
                completo = false;
                campos_invalidos += " Logradouro";
            } else {
                log.setBorder(BorderFactory.createLineBorder(Color.green));
            }

            if (bairro.getText().equals("")) {
                bairro.setBorder(BorderFactory.createLineBorder(Color.red));
                completo = false;
                campos_invalidos += " Bairro";
            } else {
                bairro.setBorder(BorderFactory.createLineBorder(Color.green));
            }

            if (municipio.getText().equals("")) {
                municipio.setBorder(BorderFactory.createLineBorder(Color.red));
                completo = false;
                campos_invalidos += " Município";
            } else {
                municipio.setBorder(BorderFactory.createLineBorder(Color.green));
            }

            if (cep.getText().equals("     -   ")) {
                cep.setBorder(BorderFactory.createLineBorder(Color.red));
                completo = false;
                campos_invalidos += " CEP";
            } else {
                cep.setBorder(BorderFactory.createLineBorder(Color.green));
            }

            if (estado.getText().equals("")) {
                estado.setBorder(BorderFactory.createLineBorder(Color.red));
                completo = false;
                campos_invalidos += " Estado";
            } else {
                estado.setBorder(BorderFactory.createLineBorder(Color.green));
            }

        }
        campos_invalidos += ".";

        return completo;
    }
    
    private void preenche_dados() {
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        this.nome.setText(c.getNome());
        this.email.setText(c.getEmail());
        this.data_nascimento.setText(formatdata.format(c.getData_nascimento()) + "");
        for (Telefone t : MiscDAO.search_telefone_por_id(username, password, c.getId())) {
            this.dtm_telefone.addRow(new Object[]{
                t.getDdd(),
                t.getAntesh(),
                t.getDepoish()
            });
        }
        Endereco e = MiscDAO.search_endereco_por_id(username, password, c.getId());
        if (MiscDAO.search_endereco_por_id(username, password, c.getId()) != null) {
            this.option.setEnabled(false);
            this.option.setSelected(true);
            mode = 0;
            this.tipolog.setText(e.getTipolog());
            this.log.setText(e.getLog());
            this.bairro.setText(e.getBairro());
            this.municipio.setText(e.getMunicipio());
            this.cep.setText(e.getCep());
            this.complemento.setText(e.getComplemento());
            this.estado.setText(e.getEstado());
        } else {
            fechaEndereco();
            mode = 1;
        }
    }

    //METODO DE FORMATAÇÃO DE TODAS AS CAIXAS QUE NECESSITAM DE MASCARAS
    private void formatarTextboxes() {
        try {
            this.ddd = new NumberField();
            this.antesh = new NumberField();
            this.depoish = new NumberField();

            ddd.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent ke) {
                    if (ddd.getText().length() >= 4) {
                        ddd.setText(ddd.getText().substring(0, 3));
                    }
                }
            });

            antesh.addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(KeyEvent ke) {
                    if (antesh.getText().length() >= 6) {
                        antesh.setText(antesh.getText().substring(0, 5));
                    }
                }
            });
            depoish.addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(KeyEvent ke) {
                    if (depoish.getText().length() >= 5) {
                        depoish.setText(depoish.getText().substring(0, 4));
                    }
                }
            });

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

    public class NumberField extends JTextField {

        public NumberField() {
            this(null);
        }

        public NumberField(String text) {
            super(text);
            setDocument(new PlainDocument() {
                @Override
                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                    //normalmente apenas uma letra é inserida por vez,
                    //mas fazendo assim também previne caaso o usuário
                    //cole algum texto
                    for (int i = 0; i < str.length(); i++) {
                        if (Character.isDigit(str.charAt(i)) == false) {
                            return;
                        }
                    }
                    super.insertString(offs, str, a);
                }
            });
        }
    }

}
