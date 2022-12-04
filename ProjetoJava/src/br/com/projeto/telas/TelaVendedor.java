/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.projeto.telas;

import java.sql.*;
import br.com.projeto.dal.ModuloConexao;
import javax.swing.JOptionPane;

/**
 *
 * @author Karol
 */
public class TelaVendedor extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaVendedor() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void consultar() {
        String sql = "select * from tbVendedor where idVendedor = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdVendedor.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtNomeVendedor.setText(rs.getString(2));
                txtSalario.setText(rs.getString(3));
                txtTelefone.setText(rs.getString(4));
                txtEndVendedor.setText(rs.getString(5));
                txtDtVendedor.setText(rs.getString(6));
                txtSSNVendedor.setText(rs.getString(7));
            } else {
                JOptionPane.showMessageDialog(null, "Vendedor não cadastrado");
                // limpando os campos 
                limpar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void adicionar() {
        String sql = "insert into tbVendedor(idVendedor,nome,salario,telefone,endereco,data_nasc,SSN) values (?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdVendedor.getText());
            pst.setString(2, txtNomeVendedor.getText());
            pst.setString(3, txtSalario.getText());
            pst.setString(4, txtTelefone.getText());
            pst.setString(5, txtEndVendedor.getText());
            pst.setString(6, txtDtVendedor.getText());
            pst.setString(7, txtSSNVendedor.getText());

            // validando os campos 
            if (txtIdVendedor.getText().isEmpty() || txtNomeVendedor.getText().isEmpty() || txtSalario.getText().isEmpty() || txtTelefone.getText().isEmpty() || txtDtVendedor.getText().isEmpty() || txtEndVendedor.getText().isEmpty() || txtSSNVendedor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int add = pst.executeUpdate();
                if (add > 0) {
                    JOptionPane.showMessageDialog(null, "Vendedor cadastrado com sucesso");
                    limpar();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    // metodo para realizar alterações na tabela usuario
    private void editar() {
        String sql = "update tbVendedor set nome = ?, salario = ?, telefone = ?, endereco = ?, data_nasc = ?, SSN = ? where idVendedor = ?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtNomeVendedor.getText());
            pst.setString(2, txtSalario.getText());
            pst.setString(3, txtTelefone.getText());
            pst.setString(4, txtEndVendedor.getText());
            pst.setString(5, txtDtVendedor.getText());
            pst.setString(6, txtSSNVendedor.getText());
            pst.setString(7, txtIdVendedor.getText());

            // validando os campos 
            if (txtIdVendedor.getText().isEmpty() || txtNomeVendedor.getText().isEmpty() || txtSalario.getText().isEmpty() || txtTelefone.getText().isEmpty() || txtEndVendedor.getText().isEmpty() || txtDtVendedor.getText().isEmpty() || txtSSNVendedor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int add = pst.executeUpdate();
                if (add > 0) {
                    JOptionPane.showMessageDialog(null, "Vendedor alterado com sucesso");
                    limpar();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    // metodo deletar usuario
    private void deletar() {
        String sql = "delete from tbVendedor where idVendedor = ?;";
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este Vendedor", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdVendedor.getText());
                int ap = pst.executeUpdate();
                if (ap > 0) {
                    JOptionPane.showMessageDialog(null, "Vendedor deletado com sucesso");
                    limpar();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void limpar() {
        txtIdVendedor.setText(null);
        txtNomeVendedor.setText(null);
        txtSalario.setText(null);
        txtTelefone.setText(null);
        txtEndVendedor.setText(null);
        txtDtVendedor.setText(null);
        txtSSNVendedor.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdVendedor = new javax.swing.JTextField();
        txtNomeVendedor = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        txtSalario = new javax.swing.JTextField();
        btnUsuCreat = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        txtEndVendedor = new javax.swing.JTextField();
        txtDtVendedor = new javax.swing.JTextField();
        txtSSNVendedor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(905, 504));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVerifyInputWhenFocusTarget(false);

        jLabel1.setText("ID");

        jLabel2.setText("Nome");

        jLabel3.setText("Salário");

        jLabel4.setText("Telefone");

        jLabel5.setText("Endereço");

        txtIdVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdVendedorActionPerformed(evt);
            }
        });

        txtNomeVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeVendedorActionPerformed(evt);
            }
        });

        txtTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneActionPerformed(evt);
            }
        });

        txtSalario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSalarioActionPerformed(evt);
            }
        });

        btnUsuCreat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUsuCreat.setText("Adicionar");
        btnUsuCreat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreatActionPerformed(evt);
            }
        });

        btnUsuRead.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUsuRead.setText("Consultar");
        btnUsuRead.setMaximumSize(new java.awt.Dimension(95, 27));
        btnUsuRead.setMinimumSize(new java.awt.Dimension(95, 27));
        btnUsuRead.setPreferredSize(new java.awt.Dimension(95, 27));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });

        btnUsuUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUsuUpdate.setText("Editar");
        btnUsuUpdate.setMaximumSize(new java.awt.Dimension(95, 27));
        btnUsuUpdate.setMinimumSize(new java.awt.Dimension(95, 27));
        btnUsuUpdate.setPreferredSize(new java.awt.Dimension(95, 27));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });

        btnUsuDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUsuDelete.setText("Deletar");
        btnUsuDelete.setMaximumSize(new java.awt.Dimension(95, 27));
        btnUsuDelete.setMinimumSize(new java.awt.Dimension(95, 27));
        btnUsuDelete.setPreferredSize(new java.awt.Dimension(95, 27));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });

        txtEndVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEndVendedorActionPerformed(evt);
            }
        });

        txtDtVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDtVendedorActionPerformed(evt);
            }
        });

        txtSSNVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSSNVendedorActionPerformed(evt);
            }
        });

        jLabel6.setText("Data de Nascimento");

        jLabel7.setText("SSN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(143, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtEndVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtIdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNomeVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(38, 38, 38)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(69, 69, 69)
                                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSSNVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtDtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnUsuCreat)
                        .addGap(70, 70, 70)
                        .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(132, 132, 132))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomeVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEndVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSSNVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(txtDtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnUsuCreat)
                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeVendedorActionPerformed

    private void txtTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneActionPerformed

    private void txtSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSalarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSalarioActionPerformed

    private void btnUsuCreatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreatActionPerformed
        // adiciona dados para BD
        adicionar();
    }//GEN-LAST:event_btnUsuCreatActionPerformed

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        //Chama o metodo consultar
        consultar();
    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void txtIdVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdVendedorActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        // chamando o metodo editar
        editar();
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        // chama o metodo deletar
        deletar();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed

    private void txtEndVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndVendedorActionPerformed

    private void txtDtVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDtVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDtVendedorActionPerformed

    private void txtSSNVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSSNVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSSNVendedorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuCreat;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtDtVendedor;
    private javax.swing.JTextField txtEndVendedor;
    private javax.swing.JTextField txtIdVendedor;
    private javax.swing.JTextField txtNomeVendedor;
    private javax.swing.JTextField txtSSNVendedor;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
