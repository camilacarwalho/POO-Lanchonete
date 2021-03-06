package com.ifpb.projeto.view;

import com.ifpb.projeto.Exceptions.NumeroMesaPositivoException;
import com.ifpb.projeto.Exceptions.QuantidadePorPedidoPositivaException;
import com.ifpb.projeto.control.CadastroProduto;
import com.ifpb.projeto.model.GerenciarMesa;
import com.ifpb.projeto.model.Pedido;
import com.ifpb.projeto.model.Produto;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class EditarPedido extends JDialog {

    private JPanel panel1;
    private JButton editarButton;
    private JButton voltarButton;
    private JSpinner spinnerQuantidade;
    private JList listProdutos;
    private JButton excluirButton;
    private static int idPedido = 0;

    public EditarPedido(){
        setContentPane(panel1);
        setTitle("Ver Pedidos");
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(GerenciarMesa.excluirPedido(VerPedidos.getNumMesa(),idPedido)){
                        JOptionPane.showMessageDialog(null, "Pedido excluido com sucesso!", "Mensagem de Confirmação",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();

                    }
                } catch (NumeroMesaPositivoException e1) {
                    JOptionPane.showMessageDialog(null, "O número informado para a mesa deve ser um valor positivo",
                            "Mensagem de Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Pedido novo = new Pedido(CadastroProduto.buscarPorCodigo(Integer.parseInt
                            (listProdutos.getSelectedValue().toString().split("-")[0])),(int) spinnerQuantidade.getValue());
                    if(novo.equals(GerenciarMesa.getComanda(VerPedidos.getNumMesa()).getPedido(idPedido))){
                        JOptionPane.showMessageDialog(null, "Não houve nenhuma alteração no pedido!",
                                "Mensagem de Confirmação",
                                JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        if(GerenciarMesa.editarPedido(idPedido,VerPedidos.getNumMesa(),novo)){
                            JOptionPane.showMessageDialog(null, "Pedido alterado com sucesso!",
                                    "Mensagem de Confirmação",
                                    JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                    }
                } catch (QuantidadePorPedidoPositivaException e1) {
                    JOptionPane.showMessageDialog(null, "A quantidade de produtos por pedido deve ser um valo positivo",
                            "Mensagem de Erro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Problema com a classe Produto", "Mensagem de Erro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(null, "Falha na conexão com o arquivo", "Mensagem de Erro",
                            JOptionPane.ERROR_MESSAGE);
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Problema com a conversão do código do produto", "Mensagem de Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void setIdPedido(int id){
        idPedido = id;
    }

    private void createUIComponents() {
        spinnerQuantidade = new JSpinner();
        spinnerQuantidade.setModel(new SpinnerNumberModel(1, 1, null, 1));
        JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor)spinnerQuantidade.getEditor();
        listProdutos = new JList();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            for(Produto p:CadastroProduto.getProdutos()) {
                listModel.addElement(p.getCodigo()+"-"+p.getNome());
            }
            listProdutos.setModel(listModel);
            listProdutos.setSelectedIndex(0);
            listProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Problema com a classe Produto", "Mensagem de Erro",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Falha na conexão com o arquivo", "Mensagem de Erro",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
