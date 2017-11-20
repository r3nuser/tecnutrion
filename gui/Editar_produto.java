package gui;

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
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class Editar_produto extends JFrame {

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

    private JTextField produto_nome;
    private JTextField preco_uni_venda;
    private JTextField preco_uni_compra;
    private JTextField peso;
    private JTextField descricao;
    private JTextField uni_compradas;
    private JTextField validade;

    private JButton buscar_fornecedor;
    private JTextField fornecedor;

    private JComboBox categoria;
    private JComboBox unidade_medida;

    private Fornecedor f;
    private Estoque e;
    private BufferedImage imagem;
    private Produto p;

    public Editar_produto(String u, String p, int id) {
        this.p = MiscDAO.search_produto_por_id(u, p, id);
        f = MiscDAO.search_fornecedor_por_id(u, p, this.p.getFk_fornecedor_cod());
        e = MiscDAO.search_estoque_por_id(u, p, this.p.getFk_estoque_cod());
        System.out.println(this.p.getFk_estoque_cod());
        this.currentusername = u;
        this.currentpassword = p;
        initComponents();
    }

    private void initComponents() {
        try {
            this.validade = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (Exception e) {
            System.out.println(e);
        }
        titulo_l = new JLabel("EDIÇÃO DE PRODUTO");
        unidade_medida_l = new JLabel("Unidade de Medida:");
        produto_nome_l = new JLabel("Nome do Produto:");
        fornecedor_l = new JLabel("Fornecedor:");
        preco_uni_c_l = new JLabel("Preço Uni. de Compra:");
        preco_uni_v_l = new JLabel("Preco Uni. de Venda:");
        peso_l = new JLabel("Peso:");
        descricao_l = new JLabel("Descrição:");
        uni_compradas_l = new JLabel("Unidades Compradas:");
        categoria_l = new JLabel("Categoria:");
        produto_foto_l = new JLabel("Foto do produto:");
        validade_l = new JLabel("Validade:");
        foto = new JLabel("");
        produto_nome = new JTextField();
        fornecedor = new JTextField();
        preco_uni_compra = new JTextField();
        preco_uni_venda = new JTextField();
        peso = new JTextField();
        descricao = new JTextField();
        uni_compradas = new JTextField();
        categoria = new JComboBox();
        unidade_medida = new JComboBox();
        cadastrar = new JButton("Atualizar !");
        inserir_foto = new JButton("Procurar...");
        buscar_fornecedor = new JButton(new ImageIcon(getClass().getResource("abas/ico_lupa2.png")));

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

        peso_l.setBounds(300, 220 + 60 + 10, 200, 30);
        peso_l.setFont(new java.awt.Font("Arial", 1, 13));

        peso.setBounds(300, 250 + 60 + 10, 120, 30);

        unidade_medida_l.setBounds(300, 160 + 60 + 10, 200, 30);
        unidade_medida_l.setFont(new java.awt.Font("Arial", 1, 13));
        unidade_medida.setBounds(300, 190 + 60 + 10, 130, 30);

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

        cadastrar.setBounds(210, 450 + 40, 110, 30);
        cadastrar.setFont(new java.awt.Font("Arial", 1, 11));
        //EVENTO DE CADASTRO QUE PEGA TODOS OS DADOS PASSADOS
        //PELO USUÁRIO E CHAMA OS METODOS DE INSERÇÃO
        cadastrar.addActionListener((ActionEvent) -> {
            p.setProduto_nome(produto_nome.getText());
            p.setPreco_uni_compra(Float.parseFloat(preco_uni_compra.getText()));
            p.setPreco_uni_venda(Float.parseFloat(preco_uni_venda.getText()));
            p.setCategoria((String) categoria.getItemAt(categoria.getSelectedIndex()));
            p.setDescricao_produto(descricao.getText());
            p.setUnidade_medida_peso((String) unidade_medida.getItemAt(unidade_medida.getSelectedIndex()));
            p.setPeso_produto(Float.parseFloat(peso.getText()));
            p.setFk_fornecedor_cod(f.getId());

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
            EstoqueDAO.update(this.currentusername, this.currentpassword, e);
            //INSERÇÃO : PRODUTO            
            ProdutoDAO.update(this.currentusername, this.currentpassword, p);
            dispose();
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

        mostrar_dados_atuais();

        setTitle("Editar dados do produto ID:" + p.getProduto_cod());
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        URL url = this.getClass().getResource("abas/ico_cadastro.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        setVisible(true);

    }

    private void mostrar_dados_atuais() {
        foto.setIcon(p.getProduto_foto_para_tabela());
        produto_nome.setText(p.getProduto_nome());
        preco_uni_venda.setText(p.getPreco_uni_venda() + "");
        preco_uni_compra.setText(p.getPreco_uni_compra() + "");
        peso.setText(p.getPeso_produto() + "");
        descricao.setText(p.getDescricao_produto());
        uni_compradas.setText(e.getQnt_estoque()+"");
        
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        
        validade.setText(formatdata.format(e.getValidade())+"");
        fornecedor.setText(f.getNome());
        categoria.setSelectedItem(p.getCategoria());
        unidade_medida.setSelectedItem(p.getUnidade_medida_peso());
    }

}
