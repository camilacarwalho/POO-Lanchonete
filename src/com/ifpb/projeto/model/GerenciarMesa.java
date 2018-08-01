package com.ifpb.projeto.model;

import java.util.ArrayList;
import java.util.List;

public class GerenciarMesa {
    //Oi//Oie

    //Esta lista irá gerenciar a comanda presente em cada mesa;
    private List<Comanda> mesas;

    //Construtor
    public GerenciarMesa() {
        mesas = new ArrayList<Comanda>();
    }

    //Getters and Setters

    public List<Comanda> getMesas() {
        return mesas;
    }

    public void setMesas(List<Comanda> mesas) {
        this.mesas = mesas;
    }

    public Comanda getComanda(int numMesa){
        for (Comanda comanda:mesas ) {
            if(comanda.getNumMesa()==numMesa){
                return comanda;
            }
        }
        return null;
    }
    //Esta função irá criar uma nova comanda para a mesa que for selecionada;
    //NECESSÁRIO TESTES. SUJEITO A MUDANÇAS;
    public boolean gerarComanda(int numeroMesa) {
        //Esta variável irá informar a mesa informada já possui uma comanda;
        boolean existeComanda = false;
        //Roda a lista até que se ache (ou não) alguma comanda na mesa informada;
        for (Comanda comanda : mesas) {
            if (comanda.getNumMesa() == numeroMesa) {
                //se existir, a variavel passa a ser TRUE;
                existeComanda = true;
                break;
            }
        }
        //Se não existir nenhuma comanda:
        if (!existeComanda) {
            //Ele gera a nova comanda solicitada;
            Comanda comanda = new Comanda(numeroMesa);
            mesas.add(comanda);
            return true;

        } else {
            //Caso exista, a função informa que não pode gerar uma comanda nesta mesa;
            return false;
        }
    }

    //Para testar ahsudhasudhasudhaus;
    //Esta função ira mostrar ao usuário todas os pedidos de uma mesa;
    public String verPedidos(int numeroMesa) {
        //É feito o teste de onde se encontra mesa
        for (Comanda comanda : mesas) {
            if (comanda.getNumMesa() == numeroMesa) {
                //Quando a comanda da mesa solicitada é encontrada, é impresso todos os seus respectivos pedidos;
                //SUJEITO A MUDANÇAS;
                return comanda.toString();
            }
        }
        return "";
    }

    //Para testar também asudhausdjinqwuenbqujdba
    public boolean fazerPedido(int numeroMesa, Pedido pedido,Cozinha cozinha) {
        for (Comanda comanda : mesas) {
            if (comanda.getNumMesa() == numeroMesa) {
                comanda.adicionarPedido(pedido);
                cozinha.adicionarPedido(pedido);
                return true;
            }
        }
        return false;
    }

    //Esta função tem como objetivo encerrar a comanda de uma mesa desejada;
    //NECESSÁRIO TESTES. SUJEITO A MUDANÇAS;
    public boolean encerrarComanda(int numeroMesa) {
        //É incializado um indice impossivel, para o cado de não existir comanda na mesa informada;
        int indice = -1;
        //É feita uma busca pela comanda da mesa informada;
        for (Comanda comanda : mesas) {
            if (comanda.getNumMesa() == numeroMesa) {
                System.out.println("entrou aqui");
                //Caso realmente exista uma comanda naquela mesa;
                if(comanda.allIsAtendido()){
                    //E caso todos os pedidos tenham sido atendidos;
                    //É salvo o indice de onde esta comadna se encontra na lista "mesas";
                    System.out.println("Entrou nesse if");
                    Gerencia.adicionaComanda(comanda);
                    indice = mesas.indexOf(comanda);
                    System.out.println("Valor final:"+comanda.getValorFinal());
                    break;
                }
                else break;

            }
        }
        //Testa se a variável do indice mudou;
        //Caso tenha mudado, então será possivel Encerrar a comanda da mesa solicitada;
        if (indice != -1) {
            mesas.remove(indice);
            return true;
        } else return false;

    }

    public boolean editarPedido(int idPedidoAntigo, int numMesa, Pedido novo) {  // Editar um pedido com List
        for (Comanda comanda : mesas) {
            if (numMesa == comanda.getNumMesa()) {
                List<Pedido> mesa = comanda.getComanda();
                for (Pedido pedido : mesa) {
                    if (idPedidoAntigo == pedido.getIdPedido()) {
                        int indice = mesa.indexOf(pedido);
                        mesa.add(indice, novo);
                        return true;
                    }
                }
            }
        }return false;
    }
}