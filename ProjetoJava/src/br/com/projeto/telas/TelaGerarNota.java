/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.projeto.telas;

import java.sql.*;
import br.com.projeto.dal.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Karol
 */
public class TelaGerarNota extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form Tela
     */
    public TelaGerarNota() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionar() {
        String sql = "insert into tbProdutoComprado(idProduto_tbProduto,quantidade,idCompra_tbCompra) values (?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIDProduto.getText());
            pst.setString(2, txtQuantidade.getText());
            pst.setString(3, txtNota.getText());

            // validando os campos 
            if (txtProduto.getText().isEmpty() || txtQuantidade.getText().isEmpty() || txtNota.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int add = pst.executeUpdate();
                if (add > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        preencher_compra();
        calcula_total();
        limpar();
    }

    private void calcula_total() {
        String sql = "select sum(pcom.quantidade * p.preco) as 'Total' "
                + "from tbProdutoComprado as pcom inner join tbCompra as com on pcom.idCompra_tbCompra = com.idCompra  "
                + "inner join tbProduto as p on p.idProduto = pcom.idProduto_tbProduto "
                + "and com.idCompra = ?;";

        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa
            pst.setString(1, txtNota.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtTotal.setText(rs.getString(1));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }

    // inserindo NF
    private void Nota() {
        String sql = "insert into tbNotaFiscal(idNota,idVendedor_tbVendedor,idCompra_tbCompra) values (?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNota.getText());
            pst.setString(2, txtVendedor.getText());
            pst.setString(3, txtNota.getText());

            // validando os campos 
            if (txtNota.getText().isEmpty() || txtVendedor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Nota Gerada com sucesso");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // Metodo para setar a Data

    // metodo para preencher tabela de compra
    private void preencher_compra() {
        String sql = """
               select pcom.quantidade as 'Quantidade', p.nome as 'Produto', p.preco as 'Valor', pcom.IdProdutoComprado as 'Id'
               from tbProdutoComprado as pcom inner join tbCompra as com on pcom.idCompra_tbCompra = com.idCompra 
               inner join tbProduto as p on p.idProduto = pcom.idProduto_tbProduto 
               and com.idCompra = ?;""";

        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa
            pst.setString(1, txtNota.getText());
            rs = pst.executeQuery();
            // usando a biblioteca rs2xml.jar para preencher a tabela 
            tblCompra.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }

    // metodo para pesquisar cliente 
    private void pesquisar_produto() {
        String sql = "select idProduto as ID, nome as Produto, preco as Valor from tbProduto where nome like ?";

        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa
            pst.setString(1, txtNomeProduto.getText() + "%");
            rs = pst.executeQuery();
            // usando a biblioteca rs2xml.jar para preencher a tabela 
            tblProduto.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void pesquisar_cliente() {
        String sql = "select idCliente as ID, nome as Nome from tbCliente where nome like ?";

        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa
            pst.setString(1, txtNomeCliente.getText() + "%");
            rs = pst.executeQuery();
            // usando a biblioteca rs2xml.jar para preencher a tabela 
            tblCliente.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    // metodo para setar os campos do formulário com o conteudo da tabela
    public void setar_campos() {
        int setar = tblProduto.getSelectedRow();
        txtIDProduto.setText(tblProduto.getModel().getValueAt(setar, 0).toString());
        txtProduto.setText(tblProduto.getModel().getValueAt(setar, 1).toString());
        txtValor.setText(tblProduto.getModel().getValueAt(setar, 2).toString());
        // desabilitando o botão editar
        btnEditar.setEnabled(false);
        // desabilitando o botão adicionar
        btnDeletar.setEnabled(false);
    }

    public void setar_edicao() {
        int setar = tblCompra.getSelectedRow();
        txtProduto.setText(tblCompra.getModel().getValueAt(setar, 1).toString());
        txtQuantidade.setText(tblCompra.getModel().getValueAt(setar, 0).toString());
        txtValor.setText(tblCompra.getModel().getValueAt(setar, 2).toString());
        txtIDProdutoComprado.setText(tblCompra.getModel().getValueAt(setar, 3).toString());
        btnEditar.setEnabled(true);
        btnDeletar.setEnabled(true);
        btnAdicionar.setEnabled(false);
    }

    public void setar_cliente() {
        int setar = tblCliente.getSelectedRow();
        txtIDCliente.setText(tblCliente.getModel().getValueAt(setar, 0).toString());
        String sql = "insert into tbCompra(idCliente_tbCliente) values (?);";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIDCliente.getText());
            // validando o campo
            if (txtIDCliente.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            } else {
                int add = pst.executeUpdate();
                if (add > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente selecionado com sucesso");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        buscar_nota();

    }

    private void buscar_nota() {
        String sql = "select MAX(idCompra) from tbCompra";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtNota.setText(rs.getString(1));
            } else {
                JOptionPane.showMessageDialog(null, "Nota não cadastrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    private void editar() {
        String sql = "update tbProdutoComprado set quantidade = ? where idProdutoComprado = ?;";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtQuantidade.getText());
            pst.setString(2, txtIDProdutoComprado.getText());

            // validando os campos 
            if (txtQuantidade.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int add = pst.executeUpdate();
                if (add > 0) {
                    JOptionPane.showMessageDialog(null, "Produto alterado com sucesso");
                    preencher_compra();
                    calcula_total();
                    limpar();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void deletar() {
        String sql = "delete from tbProdutoComprado where idProdutoComprado = ?;";
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este Produto", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIDProdutoComprado.getText());
                int ap = pst.executeUpdate();
                if (ap > 0) {
                    JOptionPane.showMessageDialog(null, "Produto deletado com sucesso");
                    preencher_compra();
                    calcula_total();
                    limpar();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limpar() {
        txtProduto.setText(null);
        txtNomeProduto.setText(null);
        txtQuantidade.setText(null);
        txtValor.setText(null);
        txtIDProdutoComprado.setText(null);
        btnEditar.setEnabled(true);
        btnDeletar.setEnabled(true);
        btnAdicionar.setEnabled(true);
        txtIDProduto.setText(null);
        ((DefaultTableModel) tblProduto.getModel()).setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNota = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNomeCliente = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        txtIDCliente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtNomeProduto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProduto = new javax.swing.JTable();
        txtIDProduto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCompra = new javax.swing.JTable();
        txtTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        txtProduto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtIDProdutoComprado = new javax.swing.JTextField();
        btnGerarNota = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtVendedor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gerar Nota");
        setPreferredSize(new java.awt.Dimension(904, 505));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        txtNota.setEnabled(false);
        txtNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotaActionPerformed(evt);
            }
        });
        txtNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNotaKeyReleased(evt);
            }
        });

        jLabel6.setText("N° Nota");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeClienteActionPerformed(evt);
            }
        });
        txtNomeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeClienteKeyReleased(evt);
            }
        });

        jLabel8.setText("Buscar");

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Nome"
            }
        ));
        tblCliente.setFocusable(false);
        tblCliente.getTableHeader().setReorderingAllowed(false);
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliente);

        txtIDCliente.setEnabled(false);
        txtIDCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDClienteActionPerformed(evt);
            }
        });
        txtIDCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIDClienteKeyReleased(evt);
            }
        });

        jLabel12.setText("ID");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(txtIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Produto"));

        txtNomeProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeProdutoActionPerformed(evt);
            }
        });
        txtNomeProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeProdutoKeyReleased(evt);
            }
        });

        jLabel10.setText("Buscar");

        tblProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Produto", "Valor"
            }
        ));
        tblProduto.setFocusable(false);
        tblProduto.getTableHeader().setReorderingAllowed(false);
        tblProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProduto);

        txtIDProduto.setEnabled(false);
        txtIDProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDProdutoActionPerformed(evt);
            }
        });
        txtIDProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIDProdutoKeyReleased(evt);
            }
        });

        jLabel13.setText("ID");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtIDProduto)
                                .addGap(25, 25, 25))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(122, 122, 122))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Compra"));

        tblCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Quantidade", "Produto", "Valor", "ID"
            }
        ));
        tblCompra.getTableHeader().setReorderingAllowed(false);
        tblCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCompraMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCompra);

        txtTotal.setEnabled(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        jLabel3.setText("Total");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        btnAdicionar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setMaximumSize(new java.awt.Dimension(95, 27));
        btnEditar.setMinimumSize(new java.awt.Dimension(95, 27));
        btnEditar.setPreferredSize(new java.awt.Dimension(95, 27));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDeletar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeletar.setText("Deletar");
        btnDeletar.setMaximumSize(new java.awt.Dimension(95, 27));
        btnDeletar.setMinimumSize(new java.awt.Dimension(95, 27));
        btnDeletar.setPreferredSize(new java.awt.Dimension(95, 27));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        txtProduto.setEnabled(false);
        txtProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdutoActionPerformed(evt);
            }
        });

        jLabel1.setText("Produto");

        jLabel2.setText("*Quantidade");

        txtQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeActionPerformed(evt);
            }
        });

        jLabel4.setText("Preço");

        txtIDProdutoComprado.setEnabled(false);
        txtIDProdutoComprado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDProdutoCompradoActionPerformed(evt);
            }
        });

        btnGerarNota.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGerarNota.setText("Gerar Nota");
        btnGerarNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarNotaActionPerformed(evt);
            }
        });

        jLabel14.setText("*IDVendedor");

        txtVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVendedorActionPerformed(evt);
            }
        });
        txtVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtVendedorKeyReleased(evt);
            }
        });

        jLabel5.setText("ID Compra");

        txtValor.setEnabled(false);
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtIDProdutoComprado, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtProduto)
                                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGerarNota, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdicionar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(8, 8, 8)))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNota, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(41, 41, 41)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtIDProdutoComprado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdicionar)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGerarNota))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 92, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // chama o metodo adiciona
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // chamando o metodo editar
        editar();

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        // chama o metodo deletar
        deletar();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void txtNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNotaActionPerformed
    //este evento execulta enquanto digitamos
    private void txtNotaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotaKeyReleased
        // chama o metodo pesquisar cliente 
        pesquisar_produto();
    }//GEN-LAST:event_txtNotaKeyReleased

    private void txtNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeClienteActionPerformed

    private void txtNomeProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeProdutoActionPerformed

    private void txtProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdutoActionPerformed

    private void txtQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtIDProdutoCompradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDProdutoCompradoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDProdutoCompradoActionPerformed

    private void btnGerarNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarNotaActionPerformed
        // chama a tela nota fiscal
        Nota();
    }//GEN-LAST:event_btnGerarNotaActionPerformed

    private void txtIDClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDClienteActionPerformed

    private void txtIDClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDClienteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDClienteKeyReleased

    private void txtIDProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDProdutoActionPerformed

    private void txtIDProdutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDProdutoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDProdutoKeyReleased

    private void tblProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutoMouseClicked
        // chamar setar campo
        setar_campos();
    }//GEN-LAST:event_tblProdutoMouseClicked

    private void tblCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCompraMouseClicked
        // chamar setar edicao
        setar_edicao();
    }//GEN-LAST:event_tblCompraMouseClicked

    private void txtNomeClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeClienteKeyReleased
        // chama pesquisar cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtNomeClienteKeyReleased

    private void txtNomeProdutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeProdutoKeyReleased
        // chama pesquisar produto
        pesquisar_produto();
    }//GEN-LAST:event_txtNomeProdutoKeyReleased

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
        // chamar setar cliente
        setar_cliente();
    }//GEN-LAST:event_tblClienteMouseClicked

    private void txtVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVendedorActionPerformed

    }//GEN-LAST:event_txtVendedorActionPerformed

    private void txtVendedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVendedorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVendedorKeyReleased

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir o form analizar qual a ultima nota gerada para criar um nova nota

    }//GEN-LAST:event_formInternalFrameOpened

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGerarNota;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTable tblCliente2;
    private javax.swing.JTable tblCompra;
    private javax.swing.JTable tblProduto;
    private javax.swing.JTextField txtIDCliente;
    private javax.swing.JTextField txtIDProduto;
    private javax.swing.JTextField txtIDProdutoComprado;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtNomeProduto;
    private javax.swing.JTextField txtNomeProduto1;
    private javax.swing.JTextField txtNota;
    private javax.swing.JTextField txtProduto;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
