package br.edu.idp.tech.poo;

import br.edu.idp.tech.poo.dao.CarroDao;
import br.edu.idp.tech.poo.dao.CarroJdbcDao;
import br.edu.idp.tech.poo.dao.CarroJpaDao;
import br.edu.idp.tech.poo.entidade.Carro;
import br.edu.idp.tech.poo.ui.CliIavel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Programa {

    private String sufixo;
    private CliIavel ui;

    public Programa(CliIavel cli) {
        this.ui = cli;
    }

    public void executar() {
        sufixo = " /" + gerarLetraAletoria();
        ui.exibirInformacao("Sufixo da execução: " + sufixo);
        prepararBD();
        ui.exibirInformacao("execução via JDBC - início");
        executarComDao(new CarroJdbcDao(), 3);
        ui.exibirInformacao("execução via JDBC - término");
        ui.exibirInformacao("execução via JPA - início");
        executarComDao(new CarroJpaDao(), 4);
        ui.exibirInformacao("execução via JPA - término");
    }

    private void executarComDao(CarroDao carroDao, int quantidadeCarros) {
        CarregadorDados carregador = new CarregadorDados(sufixo);
        Carro novoCarro = carregador.gerarCarro();
        carroDao.salvar(novoCarro);
        List<Carro> novosCarros = new ArrayList<>();
        novosCarros.addAll(carregador.gerarListaCarros(quantidadeCarros - 1));
        carroDao.salvar(novosCarros);
        List<Carro> carros = carroDao.findAll();
        ui.exibirListaCarros(carros);
    }

    private void prepararBD() {
        try {
            CarroJdbcDao.criarTabela();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private char gerarLetraAletoria() {
        final int NUM_LETRA_BASE = 97;
        final int QUANTIDADE_LETRAS = 25;
        int numero = GeradorNumAletorio.gerarInt(QUANTIDADE_LETRAS);
        return (char) (NUM_LETRA_BASE + numero);
    }
}


