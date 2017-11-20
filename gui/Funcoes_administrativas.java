package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sql.Sql;

public class Funcoes_administrativas extends JFrame {

    private String username;
    private String password;

    private JPanel painel_admin;
    private JLabel admin_l;
    private JLabel cadastros_a;
    private JCheckBox cadastrar_produtos_a;
    private JCheckBox cadastrar_clientes_a;
    private JCheckBox cadastrar_fornecedor_a;
    private JLabel edicoes_a;
    private JCheckBox editar_produtos_a;
    private JCheckBox editar_clientes_a;
    private JCheckBox editar_fornecedor_a;
    private JLabel financeiro_a;
    private JCheckBox realizar_venda_a;
    private JCheckBox gerar_relatorio_a;
    private JButton alterar_permissoes_a;
    private JLabel exclusao_a;
    private JCheckBox deletar_clientes_a;
    private JCheckBox deletar_produtos_a;
    private JCheckBox deletar_fornecedor_a;

    private JPanel painel_gerente;
    private JLabel gerente_l;
    private JLabel cadastros_g;
    private JCheckBox cadastrar_produtos_g;
    private JCheckBox cadastrar_clientes_g;
    private JCheckBox cadastrar_fornecedor_g;
    private JLabel edicoes_g;
    private JCheckBox editar_produtos_g;
    private JCheckBox editar_clientes_g;
    private JCheckBox editar_fornecedor_g;
    private JLabel financeiro_g;
    private JCheckBox realizar_venda_g;
    private JCheckBox gerar_relatorio_g;
    private JButton alterar_permissoes_g;
    private JLabel exclusao_g;
    private JCheckBox deletar_clientes_g;
    private JCheckBox deletar_produtos_g;
    private JCheckBox deletar_fornecedor_g;

    private JPanel painel_vendedor;
    private JLabel vendedor_l;
    private JLabel cadastros_v;
    private JCheckBox cadastrar_produtos_v;
    private JCheckBox cadastrar_clientes_v;
    private JCheckBox cadastrar_fornecedor_v;
    private JLabel edicoes_v;
    private JCheckBox editar_produtos_v;
    private JCheckBox editar_clientes_v;
    private JCheckBox editar_fornecedor_v;
    private JLabel financeiro_v;
    private JCheckBox realizar_venda_v;
    private JCheckBox gerar_relatorio_v;
    private JButton alterar_permissoes_v;
    private JLabel exclusao_v;
    private JCheckBox deletar_clientes_v;
    private JCheckBox deletar_produtos_v;
    private JCheckBox deletar_fornecedor_v;

    public Funcoes_administrativas(String currentpassword) {
        this.username = "admin";
        this.password = currentpassword;

        Connection con = Sql.getConnection(username, password);
        if (con != null) {
            initAll();
            Sql.closeConnection(con);
        } else {
            JOptionPane.showMessageDialog(null, "Conexão negada.");
        }

    }

    private void initAll() {
        setLayout(new BorderLayout());
        inicializa_painel_admin();
        inicializa_painel_gerente();
        inicializa_painel_vendedor();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(906, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Funções Administrativas");
        requestFocus();
        setVisible(true);

    }

    private void inicializa_painel_admin() {
        painel_admin = new JPanel(null);

        admin_l = new JLabel("          Admin");
        admin_l.setBorder(BorderFactory.createLineBorder(Color.black));
        admin_l.setBounds(10, 10, 280, 40);
        admin_l.setFont(new java.awt.Font("Dialog", 1, 26));
        painel_admin.add(admin_l);

        cadastros_a = new JLabel("CADASTROS:");
        cadastrar_produtos_a = new JCheckBox("Cadastrar Produto");
        cadastrar_clientes_a = new JCheckBox("Cadastrar Cliente");
        cadastrar_fornecedor_a = new JCheckBox("Cadastrar Fornecedor");

        edicoes_a = new JLabel("EDIÇÕES:");
        editar_produtos_a = new JCheckBox("Editar Dados do Produto");
        editar_clientes_a = new JCheckBox("Editar Dados do Cliente");
        editar_fornecedor_a = new JCheckBox("Editar Dados do Fornecedor");

        financeiro_a = new JLabel("FINANCEIRO:");
        realizar_venda_a = new JCheckBox("Realizar Venda");
        gerar_relatorio_a = new JCheckBox("Gerar Relatório");

        exclusao_a = new JLabel("EXCLUSÃO:");
        deletar_clientes_a = new JCheckBox("Excluir Dados do Cliente");
        deletar_produtos_a = new JCheckBox("Excluir Dados do Produto");
        deletar_fornecedor_a = new JCheckBox("Excluir Dados do Fornecedor");

        alterar_permissoes_a = new JButton("Alterar Permissões", new ImageIcon(getClass().getResource("abas/ico_permissao.png")));

        cadastrar_produtos_a.setEnabled(false);
        cadastrar_clientes_a.setEnabled(false);
        cadastrar_fornecedor_a.setEnabled(false);
        editar_produtos_a.setEnabled(false);
        editar_clientes_a.setEnabled(false);
        editar_fornecedor_a.setEnabled(false);
        realizar_venda_a.setEnabled(false);
        gerar_relatorio_a.setEnabled(false);
        deletar_clientes_a.setEnabled(false);
        deletar_produtos_a.setEnabled(false);
        deletar_fornecedor_a.setEnabled(false);
        alterar_permissoes_a.setEnabled(false);

        cadastrar_produtos_a.setSelected(true);
        cadastrar_clientes_a.setSelected(true);
        cadastrar_fornecedor_a.setSelected(true);
        editar_produtos_a.setSelected(true);
        editar_clientes_a.setSelected(true);
        editar_fornecedor_a.setSelected(true);
        realizar_venda_a.setSelected(true);
        gerar_relatorio_a.setSelected(true);
        deletar_produtos_a.setSelected(true);
        deletar_clientes_a.setSelected(true);
        deletar_fornecedor_a.setSelected(true);

        cadastros_a.setBounds(10, 60, 200, 18);
        cadastrar_clientes_a.setBounds(10, 80, 200, 18);
        cadastrar_produtos_a.setBounds(10, 100, 200, 18);
        cadastrar_fornecedor_a.setBounds(10, 120, 200, 18);

        edicoes_a.setBounds(10, 160, 200, 18);
        editar_clientes_a.setBounds(10, 180, 200, 18);
        editar_produtos_a.setBounds(10, 200, 200, 18);
        editar_fornecedor_a.setBounds(10, 220, 230, 18);

        financeiro_a.setBounds(10, 260, 200, 18);
        realizar_venda_a.setBounds(10, 280, 200, 18);
        gerar_relatorio_a.setBounds(10, 300, 200, 18);

        exclusao_a.setBounds(10, 340, 200, 18);
        deletar_clientes_a.setBounds(10, 360, 250, 18);
        deletar_produtos_a.setBounds(10, 380, 250, 18);
        deletar_fornecedor_a.setBounds(10, 400, 250, 18);

        alterar_permissoes_a.setBounds(50, 440, 200, 18);

        painel_admin.add(cadastros_a);
        painel_admin.add(cadastrar_produtos_a);
        painel_admin.add(cadastrar_clientes_a);
        painel_admin.add(cadastrar_fornecedor_a);

        painel_admin.add(edicoes_a);
        painel_admin.add(editar_produtos_a);
        painel_admin.add(editar_clientes_a);
        painel_admin.add(editar_fornecedor_a);

        painel_admin.add(financeiro_a);
        painel_admin.add(realizar_venda_a);
        painel_admin.add(gerar_relatorio_a);

        painel_admin.add(exclusao_a);
        painel_admin.add(deletar_clientes_a);
        painel_admin.add(deletar_produtos_a);
        painel_admin.add(deletar_fornecedor_a);

        painel_admin.add(alterar_permissoes_a);

        painel_admin.setPreferredSize(new Dimension(300, 480));
        painel_admin.setBorder(BorderFactory.createLineBorder(Color.black));
        add(painel_admin, BorderLayout.LINE_START);
    }

    private void inicializa_painel_gerente() {
        painel_gerente = new JPanel(null);

        gerente_l = new JLabel("         Gerente");
        gerente_l.setBorder(BorderFactory.createLineBorder(Color.black));
        gerente_l.setBounds(10, 10, 280, 40);
        gerente_l.setFont(new java.awt.Font("Dialog", 1, 26));
        painel_gerente.add(gerente_l);

        cadastros_g = new JLabel("CADASTROS:");
        cadastrar_produtos_g = new JCheckBox("Cadastrar Produto");
        cadastrar_clientes_g = new JCheckBox("Cadastrar Cliente");
        cadastrar_fornecedor_g = new JCheckBox("Cadastrar Fornecedor");

        edicoes_g = new JLabel("EDIÇÕES:");
        editar_produtos_g = new JCheckBox("Editar Dados do Produto");
        editar_clientes_g = new JCheckBox("Editar Dados do Cliente");
        editar_fornecedor_g = new JCheckBox("Editar Dados do Fornecedor");

        financeiro_g = new JLabel("FINANCEIRO:");
        realizar_venda_g = new JCheckBox("Realizar Venda/Troca");
        gerar_relatorio_g = new JCheckBox("Gerar Relatório");

        exclusao_g = new JLabel("EXCLUSÃO:");
        deletar_clientes_g = new JCheckBox("Excluir Dados do Cliente");
        deletar_produtos_g = new JCheckBox("Excluir Dados do Produto");
        deletar_fornecedor_g = new JCheckBox("Excluir Dados do Fornecedor");

        alterar_permissoes_g = new JButton("Alterar Permissões", new ImageIcon(getClass().getResource("abas/ico_permissao.png")));

        cadastros_g.setBounds(10, 60, 200, 18);
        cadastrar_clientes_g.setBounds(10, 80, 200, 18);
        cadastrar_produtos_g.setBounds(10, 100, 200, 18);
        cadastrar_fornecedor_g.setBounds(10, 120, 200, 18);

        edicoes_g.setBounds(10, 160, 200, 18);
        editar_clientes_g.setBounds(10, 180, 200, 18);
        editar_produtos_g.setBounds(10, 200, 200, 18);
        editar_fornecedor_g.setBounds(10, 220, 230, 18);

        financeiro_g.setBounds(10, 260, 200, 18);
        realizar_venda_g.setBounds(10, 280, 200, 18);
        gerar_relatorio_g.setBounds(10, 300, 200, 18);

        exclusao_g.setBounds(10, 340, 200, 18);
        deletar_clientes_g.setBounds(10, 360, 250, 18);
        deletar_produtos_g.setBounds(10, 380, 250, 18);
        deletar_fornecedor_g.setBounds(10, 400, 250, 18);

        alterar_permissoes_g.setBounds(50, 440, 200, 18);

        painel_gerente.add(cadastros_g);
        painel_gerente.add(cadastrar_produtos_g);
        painel_gerente.add(cadastrar_clientes_g);
        painel_gerente.add(cadastrar_fornecedor_g);

        painel_gerente.add(edicoes_g);
        painel_gerente.add(editar_produtos_g);
        painel_gerente.add(editar_clientes_g);
        painel_gerente.add(editar_fornecedor_g);

        painel_gerente.add(financeiro_g);
        painel_gerente.add(realizar_venda_g);
        painel_gerente.add(gerar_relatorio_g);

        painel_gerente.add(exclusao_g);
        painel_gerente.add(deletar_clientes_g);
        painel_gerente.add(deletar_produtos_g);
        painel_gerente.add(deletar_fornecedor_g);

        painel_gerente.add(alterar_permissoes_g);

        alterar_permissoes_g.addActionListener((ActionEvent) -> {
            Connection con = null;
            PreparedStatement stmt = null;
            try {
                con = Sql.getConnection(username, password);
                String s = "";
                stmt = con.prepareStatement("revoke all privileges on *.* from 'gerente'@'%';");
                stmt.executeUpdate();
                if (cadastrar_produtos_g.isSelected()) {
                    s = " grant create,select on visualnutrion.produtos to 'gerente'@'%';";
                    stmt = con.prepareStatement(s);
                    stmt.executeUpdate();
                }
                if (cadastrar_clientes_g.isSelected()) {
                    s = " grant create,select on visualnutrion.clientes to 'gerente'@'%';";
                    stmt = con.prepareStatement(s);
                    stmt.executeUpdate();
                }
                if (cadastrar_fornecedor_g.isSelected()) {
                    s = " grant create,select on visualnutrion.fornecedor to 'gerente'@'%';";
                    stmt = con.prepareStatement(s);
                    stmt.executeUpdate();
                }
		if (editar_produtos_g.isSelected()){
			s = "grant update on visualnutrion.produtos to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
		}
		if(editar_clientes_g.isSelected()){
			s="grant update on visualnutrion.clientes to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
		}
		if(editar_fornecedor_g.isSelected()){
			s="grant update on visualnutrion.fornecedor to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
		}
		if(realizar_venda_g.isSelected()){
			s="grant create,update,select,delete on visualnutrion.pedido to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
			s="grant create,update,select,delete on visualnutrion.pedido_item to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
		}
		if(gerar_relatorio_g.isSelected()){
			s="grant select on visualnutrion.pedido to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
			s="grant select on visualnutrion.pedido_item to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
		}
		if(deletar_clientes_g.isSelected()){
			s="grant delete on visualnutrion.clientes to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
		}
		if(deletar_produtos_g.isSelected()){
			s="grant delete on visualnutrion.produtos to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
		}
		if(deletar_fornecedor_g.isSelected()){
			s="grant delete on visualnutrion.fornecedor to 'gerente'@'%';";
			stmt = con.prepareStatement(s);
			stmt.executeUpdate();
		}

                JOptionPane.showMessageDialog(null, "Privilegios de gerente alterados com sucesso !");
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                Sql.closeConnection(con, stmt);
            }
        });

        painel_gerente.setPreferredSize(new Dimension(300, 480));
        painel_gerente.setBorder(BorderFactory.createLineBorder(Color.black));
        add(painel_gerente, BorderLayout.CENTER);
    }

    private void inicializa_painel_vendedor() {
        painel_vendedor = new JPanel(null);

        vendedor_l = new JLabel("       Vendedor");
        vendedor_l.setBorder(BorderFactory.createLineBorder(Color.black));
        vendedor_l.setBounds(10, 10, 280, 40);
        vendedor_l.setFont(new java.awt.Font("Dialog", 1, 26));
        painel_vendedor.add(vendedor_l);

        cadastros_v = new JLabel("CADASTROS:");
        cadastrar_produtos_v = new JCheckBox("Cadastrar Produto");
        cadastrar_clientes_v = new JCheckBox("Cadastrar Cliente");
        cadastrar_fornecedor_v = new JCheckBox("Cadastrar Fornecedor");

        edicoes_v = new JLabel("EDIÇÕES:");
        editar_produtos_v = new JCheckBox("Editar Dados do Produto");
        editar_clientes_v = new JCheckBox("Editar Dados do Cliente");
        editar_fornecedor_v = new JCheckBox("Editar Dados do Fornecedor");

        financeiro_v = new JLabel("FINANCEIRO:");
        realizar_venda_v = new JCheckBox("Realizar Venda");
        gerar_relatorio_v = new JCheckBox("Gerar Relatório");

        exclusao_v = new JLabel("EXCLUSÃO:");
        deletar_clientes_v = new JCheckBox("Excluir Dados do Cliente");
        deletar_produtos_v = new JCheckBox("Excluir Dados do Produto");
        deletar_fornecedor_v = new JCheckBox("Excluir Dados do Fornecedor");

        alterar_permissoes_v = new JButton("Alterar Permissões", new ImageIcon(getClass().getResource("abas/ico_permissao.png")));

        cadastros_v.setBounds(10, 60, 200, 18);
        cadastrar_clientes_v.setBounds(10, 80, 200, 18);
        cadastrar_produtos_v.setBounds(10, 100, 200, 18);
        cadastrar_fornecedor_v.setBounds(10, 120, 200, 18);

        edicoes_v.setBounds(10, 160, 200, 18);
        editar_clientes_v.setBounds(10, 180, 200, 18);
        editar_produtos_v.setBounds(10, 200, 200, 18);
        editar_fornecedor_v.setBounds(10, 220, 230, 18);

        financeiro_v.setBounds(10, 260, 200, 18);
        realizar_venda_v.setBounds(10, 280, 200, 18);
        gerar_relatorio_v.setBounds(10, 300, 200, 18);

        exclusao_v.setBounds(10, 340, 200, 18);
        deletar_clientes_v.setBounds(10, 360, 250, 18);
        deletar_produtos_v.setBounds(10, 380, 250, 18);
        deletar_fornecedor_v.setBounds(10, 400, 250, 18);

        alterar_permissoes_v.setBounds(50, 440, 200, 18);

        painel_vendedor.add(cadastros_v);
        painel_vendedor.add(cadastrar_produtos_v);
        painel_vendedor.add(cadastrar_clientes_v);
        painel_vendedor.add(cadastrar_fornecedor_v);

        painel_vendedor.add(edicoes_v);
        painel_vendedor.add(editar_produtos_v);
        painel_vendedor.add(editar_clientes_v);
        painel_vendedor.add(editar_fornecedor_v);

        painel_vendedor.add(financeiro_v);
        painel_vendedor.add(realizar_venda_v);
        painel_vendedor.add(gerar_relatorio_v);

        painel_vendedor.add(exclusao_v);
        painel_vendedor.add(deletar_clientes_v);
        painel_vendedor.add(deletar_produtos_v);
        painel_vendedor.add(deletar_fornecedor_v);

        painel_vendedor.add(alterar_permissoes_v);

        painel_vendedor.setPreferredSize(new Dimension(300, 480));
        painel_vendedor.setBorder(BorderFactory.createLineBorder(Color.black));
        add(painel_vendedor, BorderLayout.LINE_END);
    }
}
