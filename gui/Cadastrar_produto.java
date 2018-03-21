package gui;

/*CLASSE RESPONSÁVEL POR:
*REALIZAR CADASTRO DE PRODUTOS

AUTOR: RENAN
 */
import bean.Estoque;
import bean.Fornecedor;
import bean.Produto;
import dao.EstoqueDAO;
import dao.MiscDAO;
import dao.ProdutoDAO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class Cadastrar_produto extends JFrame {

    //TODOS OBJETOS UTILIZADOS NA TELA
    private final String currentusername;
    private final String currentpassword;

    private JLabel titulo_l;

    private JButton cadastrar;
    private JButton inserir_foto;

    private JLabel produto_foto_l;
    private JLabel produto_nome_l;
    private JLabel fornecedor_l;
    private JLabel preco_uni_c_l;
    private JLabel preco_uni_v_l;
    private JLabel peso_l;
    private JLabel descricao_l;
    private JLabel uni_compradas_l;
    private JLabel categoria_l;
    private JLabel unidade_medida_l;
    private JLabel foto;
    private JLabel validade_l;
    private JLabel cod_barras_l;

    private JTextField produto_nome;
    private JTextField preco_uni_venda;
    private JTextField preco_uni_compra;
    private JTextField peso;
    private JTextField descricao;
    private JTextField uni_compradas;
    private JTextField validade = null;
    private JTextField cod_barras;

    private JButton buscar_fornecedor;
    private JButton leitura_cod_barras;
    private JTextField fornecedor;

    private JComboBox categoria;
    private JComboBox unidade_medida;

    private Fornecedor f;

    private BufferedImage imagem;
    private Produto p;

    String textovalidacao;

    //METODO CONSTRUTOR QUE RECEBE USUARIO E SENHA
    //E CHAMADA DO METODO QUE INICIALIZA TODOS OS COMPONENTES
    public Cadastrar_produto(String u, String p) {
        this.currentusername = u;
        this.currentpassword = p;
        initComponents();
    }

    //METODO QUE INICIALIZA TODOS OS COMPONENTES
    private void initComponents() {
        try {
            this.validade = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (Exception e) {
            System.out.println(e);
        }
        p = new Produto();
        titulo_l = new JLabel("CADASTRO DE PRODUTO");
        unidade_medida_l = new JLabel("Unidade de Medida:");
        produto_nome_l = new JLabel("Nome do Produto:*");
        fornecedor_l = new JLabel("Fornecedor:*");
        preco_uni_c_l = new JLabel("Preço Uni. de Compra:*");
        preco_uni_v_l = new JLabel("Preco Uni. de Venda:*");
        peso_l = new JLabel("Peso:");
        descricao_l = new JLabel("Descrição:");
        uni_compradas_l = new JLabel("Unidades Compradas:*");
        categoria_l = new JLabel("Categoria:*");
        produto_foto_l = new JLabel("Foto do produto:*");
        validade_l = new JLabel("Validade:");
        cod_barras_l = new JLabel("Cód. Barras:");
        foto = new JLabel("");
        produto_nome = new JTextField();
        fornecedor = new JTextField();
        preco_uni_compra = new JTextField();
        preco_uni_venda = new JTextField();
        peso = new JTextField();
        descricao = new JTextField();
        uni_compradas = new JTextField();
        cod_barras = new JTextField();
        categoria = new JComboBox();
        unidade_medida = new JComboBox();
        cadastrar = new JButton("Cadastrar !");
        inserir_foto = new JButton("Procurar...");
        leitura_cod_barras = new JButton("Ler Cod. Barras");
        buscar_fornecedor = new JButton(new ImageIcon(getClass().getResource("abas/ico_lupa2.png")));
        f = new Fornecedor();

        cadastrar.setBackground(new Color(30, 30, 30));
        cadastrar.setForeground(new Color(255, 255, 255));
        inserir_foto.setBackground(new Color(30, 30, 30));
        inserir_foto.setForeground(new Color(255, 255, 255));
        buscar_fornecedor.setBackground(new Color(30, 30, 30));
        buscar_fornecedor.setForeground(new Color(255, 255, 255));
        leitura_cod_barras.setBackground(new Color(30, 30, 150));
        leitura_cod_barras.setForeground(new Color(255, 255, 255));

        setSize(555, 600);
        setLocationRelativeTo(null);

        add(titulo_l);
        add(produto_nome_l);
        add(fornecedor_l);
        add(preco_uni_v_l);
        add(preco_uni_c_l);
        add(peso_l);
        add(descricao_l);
        add(uni_compradas_l);
        add(categoria_l);
        add(unidade_medida_l);
        add(produto_foto_l);
        add(validade_l);
        add(produto_nome);
        add(fornecedor);
        add(preco_uni_venda);
        add(preco_uni_compra);
        add(peso);
        add(descricao);
        add(uni_compradas);
        add(categoria);
        add(unidade_medida);
        add(validade);
        add(cadastrar);
        add(inserir_foto);
        add(foto);
        add(buscar_fornecedor);
        add(leitura_cod_barras);
        add(cod_barras_l);
        add(cod_barras);
        
        titulo_l.setBounds(148, 40, 400, 30);
        titulo_l.setFont(new java.awt.Font("Arial", 1, 19));

        produto_nome_l.setBounds(30, 100, 200, 30);
        produto_nome_l.setFont(new java.awt.Font("Arial", 1, 13));

        produto_nome.setBounds(30, 130, 200, 30);

        fornecedor_l.setBounds(30, 160, 200, 30);
        fornecedor_l.setFont(new java.awt.Font("Arial", 1, 13));

        fornecedor.setBounds(30, 190, 175, 30);
        fornecedor.setEditable(false);
        buscar_fornecedor.setBounds(30 + 175 + 5, 190, 30, 30);

        preco_uni_c_l.setBounds(30, 220, 200, 30);
        preco_uni_c_l.setFont(new java.awt.Font("Arial", 1, 13));

        preco_uni_compra.setBounds(30, 250, 200, 30);

        preco_uni_v_l.setBounds(30, 220 + 60, 200, 30);
        preco_uni_v_l.setFont(new java.awt.Font("Arial", 1, 13));

        preco_uni_venda.setBounds(30, 250 + 60, 200, 30);

        categoria_l.setBounds(30, 280 + 60, 200, 30);
        categoria_l.setFont(new java.awt.Font("Arial", 1, 13));

        categoria.setBounds(30, 310 + 60, 200, 30);

        categoria.addItem("Proteina");
        categoria.addItem("Multivitaminico");
        categoria.addItem("Acessorio");
        categoria.addItem("Pro-Hormonal");
        categoria.addItem("Hipercalorico");
        categoria.addItem("Termogenico");
        categoria.addItem("Anti-oxidante");
        categoria.addItem("Repositor energetico");
        categoria.addItem("Repositor hidroeletrolitico");
        categoria.addItem("Aminoacidos");
        categoria.addItem("Outro");

        uni_compradas_l.setBounds(30, 340 + 60, 200, 30);
        uni_compradas_l.setFont(new java.awt.Font("Arial", 1, 13));
           
        uni_compradas.setBounds(30, 370 + 60, 200, 30);
        
        cod_barras_l.setBounds(30, 400 + 60, 200, 30);
        cod_barras_l.setFont(new java.awt.Font("Arial", 1, 13));
        
        cod_barras.setBounds(30,430+60,200,30);

        peso_l.setBounds(300, 220 + 60 + 10, 200, 30);
        peso_l.setFont(new java.awt.Font("Arial", 1, 13));

        peso.setBounds(300, 250 + 60 + 10, 120, 30);

        unidade_medida_l.setBounds(300, 160 + 60 + 10, 200, 30);
        unidade_medida_l.setFont(new java.awt.Font("Arial", 1, 13));
        unidade_medida.setBounds(300, 190 + 60 + 10, 130, 30);

        leitura_cod_barras.setBounds(250,430+65,200,20);
        
        unidade_medida.addItem("Kg");
        unidade_medida.addItem("g");
        unidade_medida.addItem("ml");
        unidade_medida.addItem("L");
        unidade_medida.addItem("Capsulas");
        unidade_medida.addItem("Tabletes");

        unidade_medida.addActionListener((ActionEvent) -> {
            if (unidade_medida.getSelectedItem() == "Kg" || unidade_medida.getSelectedItem() == "g") {
                peso_l.setText("Peso:");
            } else if (unidade_medida.getSelectedItem() == "ml" || unidade_medida.getSelectedItem() == "L") {
                peso_l.setText("Volume:");
            } else {
                peso_l.setText("Unidades:");
            }
        });

        descricao_l.setBounds(300, 280 + 60 + 10, 150, 30);
        descricao_l.setFont(new java.awt.Font("Arial", 1, 13));

        descricao.setBounds(300, 310 + 60 + 10, 200, 30);

        validade_l.setBounds(300, 280 + 130, 150, 30);
        validade_l.setFont(new java.awt.Font("Arial", 1, 13));

        validade.setBounds(300, 310 + 130, 200, 30);

        cadastrar.setBounds(210, 450 + 80, 110, 30);
        cadastrar.setFont(new java.awt.Font("Arial", 1, 11));
        //EVENTO DE CADASTRO QUE PEGA TODOS OS DADOS PASSADOS
        //PELO USUÁRIO E CHAMA OS METODOS DE INSERÇÃO
        cadastrar.addActionListener((ActionEvent) -> {
            if (validacao()) {
                p.setProduto_nome(produto_nome.getText());
                p.setPreco_uni_compra(Float.parseFloat(preco_uni_compra.getText()));
                p.setPreco_uni_venda(Float.parseFloat(preco_uni_venda.getText()));
                p.setCategoria((String) categoria.getItemAt(categoria.getSelectedIndex()));
                p.setDescricao_produto(descricao.getText());
                p.setPeso_produto(Float.parseFloat(peso.getText()));
                p.setCod_barra(cod_barras.getText());
                if (!("".equals(peso.getText()))) {
                    p.setUnidade_medida_peso((String) unidade_medida.getItemAt(unidade_medida.getSelectedIndex()));
                }
                p.setFk_fornecedor_cod(f.getId());
                Estoque e = new Estoque();
                String validade = this.validade.getText();
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    java.sql.Date data = new java.sql.Date(fmt.parse(validade).getTime());
                    e.setValidade(data);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                e.setQnt_estoque(Integer.parseInt(this.uni_compradas.getText()));
                //INSERÇÃO : ESTOQUE
                EstoqueDAO.create(this.currentusername, this.currentpassword, e);
                //INSERÇÃO : PRODUTO            
                p.setFk_estoque_cod(MiscDAO.get_ultimo_estoque_id(currentusername, currentpassword));
                ProdutoDAO.create(this.currentusername, this.currentpassword, p);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, textovalidacao);
            }
        });
        
        leitura_cod_barras.addActionListener((ActionEvent)->{
            new Cod_barras(cod_barras);
        });
        
        produto_foto_l.setBounds(260, 100, 150, 30);
        produto_foto_l.setFont(new java.awt.Font("Arial", 1, 13));
        inserir_foto.setBounds(400, 100, 110, 30);
        inserir_foto.setFont(new java.awt.Font("Arial", 1, 11));

        foto.setBorder(BorderFactory.createLineBorder(Color.black));
        foto.setBounds(360, 135, 100, 100);

        //METODO RESPONSÁVEL POR BUSCAR E CONVERTER A FOTO
        //A FOTO É REDIMENSIONADA PARA 100x100 PARA EVITAR FOTOS MUITO GRANDES
        //APÓS ISSO ELA É CONVERTIDA PARA UM ARRAY DE BYTES
        inserir_foto.addActionListener((ActionEvent) -> {
            int a;
            JFileChooser jf = new JFileChooser();
            jf.setDialogTitle("Inserir Imagem !");
            jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
            a = jf.showOpenDialog(null);

            if (a == 0) {
                String caminho = jf.getSelectedFile().getAbsolutePath();
                ImageIcon img = new ImageIcon(caminho);
                //REDIMENSIONANDO                
                img = new ImageIcon(img.getImage().getScaledInstance(100, 100, 100));
                BufferedImage bf = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
                Graphics g = bf.createGraphics();
                img.paintIcon(null, g, 0, 0);
                //REALIZANDO CONVERSÃO PARA ARRAY DE BYTES.                
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                try {
                    ImageIO.write(bf, "png", stream);
                } catch (Exception e) {
                    System.out.println(e);
                }
                p.setProduto_foto(stream.toByteArray());
                foto.setIcon(new ImageIcon(img.getImage().getScaledInstance(100, 100, 100)));
            }
        });
        //CHAMA A CLASSE QUE BUSCA O FORNECEDOR DO PRODUTO EM QUESTÃO
        buscar_fornecedor.addActionListener((ActionEvent) -> {
            new Buscar_fornecedor(this.currentusername, this.currentpassword, f, fornecedor);
        });
        setTitle("Cadastro de Produto !");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        URL url = this.getClass().getResource("abas/ico_cadastro.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        setVisible(true);

    }

    private boolean validacao() {
        boolean validado = true;
        textovalidacao = "Não foi possível salvar o produto, campos obrigatórios não preenchidos:";
        if (foto.getIcon() == null) {
            foto.setBorder(BorderFactory.createLineBorder(Color.red));
            validado = false;
            textovalidacao += " | Foto do produto";
        } else {
            foto.setBorder(BorderFactory.createLineBorder(Color.green));
        }
        if ("".equals(produto_nome.getText())) {
            produto_nome.setBorder(BorderFactory.createLineBorder(Color.red));
            validado = false;
            textovalidacao += " | nome";
        } else {
            produto_nome.setBorder(BorderFactory.createLineBorder(Color.green));
        }

        if ("".equals(fornecedor.getText())) {
            fornecedor.setBorder(BorderFactory.createLineBorder(Color.red));
            validado = false;
            textovalidacao += " | fornecedor";
        } else {
            fornecedor.setBorder(BorderFactory.createLineBorder(Color.green));
        }

        if ("".equals(preco_uni_compra.getText())) {
            preco_uni_compra.setBorder(BorderFactory.createLineBorder(Color.red));
            validado = false;
            textovalidacao += " | preço unitario de compra";
        } else {
            try {
                Float.parseFloat(preco_uni_compra.getText());
                preco_uni_compra.setBorder(BorderFactory.createLineBorder(Color.green));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Caracteres Invalidos no campo "
                        + "de preço unitário de compra. Insira apenas numeros");
                validado = false;
                preco_uni_compra.setBorder(BorderFactory.createLineBorder(Color.red));
            }

        }
        if ("".equals(preco_uni_venda.getText())) {
            preco_uni_venda.setBorder(BorderFactory.createLineBorder(Color.red));
            validado = false;
            textovalidacao += " | preço unitário de venda";
        } else {
            try {
                Float.parseFloat(preco_uni_venda.getText());
                preco_uni_venda.setBorder(BorderFactory.createLineBorder(Color.green));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Caracteres Invalidos no campo "
                        + "de preço unitário de venda. Insira apenas numeros");
                validado = false;
                preco_uni_venda.setBorder(BorderFactory.createLineBorder(Color.red));
            }

        }
        if (!("".equals(peso.getText()))) {
            try {
                Float.parseFloat(peso.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Caracteres Invalidos no campo de peso. Insira apenas numeros");
                validado = false;
                peso.setBorder(BorderFactory.createLineBorder(Color.red));
            }
        }
        if ("".equals(uni_compradas.getText())) {
            uni_compradas.setBorder(BorderFactory.createLineBorder(Color.red));
            validado = false;
            textovalidacao += " | unidades compradas";
        } else {
            try {
                Integer.parseInt(uni_compradas.getText());
                uni_compradas.setBorder(BorderFactory.createLineBorder(Color.green));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Caracteres Invalidos no campo "
                        + "de unidades compradas. Insira apenas numeros inteiros");
                validado = false;
                uni_compradas.setBorder(BorderFactory.createLineBorder(Color.red));
            }

        }

        textovalidacao += ".";

        return validado;
    }

}
