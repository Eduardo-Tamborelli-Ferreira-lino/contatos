package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner SC = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        int qtd = 0;
        while (true){
            inicio(qtd);
        }
    }

    public static void inicio(int qtd) throws SQLException{
        qtd = quantidadeCadastrada(qtd);
        System.out.println(" Quantidade de cadastros: " + qtd +
                """
                \n-----------------------------------
                1 - Cadastrar Contato
                2 - Atualizar um Contato
                3 - Deletar Contato
                4 - Buscar Contato
                5 - Listar Todos
                6 - Quantidade Cadastrada
                Digite a opção desejada:
                """);
        int opcao = SC.nextInt();
        SC.nextLine();

        switch (opcao){
            case 1: {
                cadastrarContato();
                break;
            }
            case 2: {
                atualizarContato();
                break;
            }
            case 3:{
                deletarContato();
                break;
            }
            case 4:{
                buscarContato();
                break;
            }
            case 5:{
                listarTodos();
                break;
            }
            case 6:{
                quantidadeCadastrada(qtd);
                break;
            }
            default:{
                System.out.println("Opção selecionada incorretamente");
            }
        }
    }

    public static void cadastrarContato(){
        System.out.println("Digite o nome de contato: ");
        String nome = SC.nextLine();
        System.out.println("Digite o número de contato: ");
        String numero = SC.nextLine();
        var contatoCriado = new ContatoDao();
        try{
            String contatoNome = contatoCriado.verificarNome(nome);
            if (contatoNome == nome){
                System.out.println("""
                        Nome já existente.
                        Você deseja inserir mesmo assim? 
                        1 - Sim
                        2 - Não 
                        """);
                int opcao = SC.nextInt();
                while (true){
                    switch (opcao){
                        case 1:{
                            var dao = new ContatoDao();
                            try{
                                dao.salvar(new Contato(nome,numero));
                                var contato = new ContatoDao();
                                System.out.println("Contato salvo com sucesso.");
                                return;
                            }catch (SQLException e){
                                System.out.println("Erro ao acessar o banco de dados");
                                e.printStackTrace();
                            }
                        }
                        case 2:{
                            System.out.println("Ok vamos retornar ao inicio.");
                            return;
                        }
                        default:{
                            System.out.println("Você inseriu o número errado");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados");
            e.printStackTrace();
        }
    }

    public static void atualizarContato() throws SQLException{
        ArrayList <Contato> contatos = new ArrayList<>();
        var contatosDao = new ContatoDao();
        while(true){
            contatos = contatosDao.buscarTodos();
            if (contatos == null || contatos.isEmpty()){
                System.out.println("""
                        Nenhum contato está cadastrado no sistema, 
                        Cadastre um contato antes de querer deletar.
                        """);
                return;
            }
            else{
                for (int i = 0; i < contatos.size(); i++) {
                    System.out.println(contatos.get(i));
                }
                while (true) {
                    System.out.println("Digite o id de contato que deseja atualizar");
                    int id = SC.nextInt();
                    for (Contato contato : contatos){
                        if (contato.getId() == id){
                            System.out.println(contato.toString());
                            System.out.println("Deseja alterar o numero ou nome? ");
                            System.out.println("""
                                1 - Nome
                                2 - Número
                                3 - Trocar nome e número
                                
                                Caso queria retornar digite 0
                                
                                Digite a opção desejada.
                                """);
                            int opcao = SC.nextInt();
                            switch (opcao) {
                                case 1: {
                                    System.out.println("Insira o novo nome de contato: ");
                                    SC.nextLine();
                                    String nome = SC.nextLine();
                                    var dao = new ContatoDao();
                                    try {
                                        dao.alterarNomePorId(id, nome);
                                        System.out.println("Contato atualizado");
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao acessar o banco de dados");
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                case 2: {
                                    System.out.println("Digite o novo numero de contato");
                                    SC.nextLine();
                                    String numero = SC.nextLine();
                                    var dao = new ContatoDao();
                                    try {
                                        dao.alterarNumeroPorId(id, numero);
                                        System.out.println("Contato atualizado");
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao acessar o banco de dados");
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                case 3: {
                                    System.out.println("Digite o novo número.");
                                    SC.nextLine();
                                    String numero = SC.nextLine();
                                    System.out.println("Digite o novo nome de contato.");
                                    String nome = SC.nextLine();
                                    var dao = new ContatoDao();
                                    try {
                                        dao.alterarNomeENumeroPorId(id, nome, numero);
                                        System.out.println("Contato atualizado");
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao acessar o banco de dados");
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                case 0: {
                                    System.out.println("Ok, estamos retornando.");
                                    return;
                                }
                                default: {
                                    System.out.println("Escolha informada incorretamente.");
                                    break;
                                }
                            }
                            return;
                        }
                    }
                    System.out.println("""
                               ID não encontrado.
                               Coloque o ID correto.
                               """);
                }
            }
        }
    }

    public static void deletarContato(){
        ArrayList <Contato> contatos = new ArrayList<>();
        var contatosDao = new ContatoDao();
        try {
            contatos = contatosDao.buscarTodos();
            if (contatos == null || contatos.isEmpty()){
                System.out.println("""
                        Nenhum contato está cadastrado no sistema, 
                        Cadastre um contato antes de querer deletar.
                        """);
                return;
            }
            else {
                for (int i = 0; i < contatos.size(); i++) {
                    System.out.println(contatos.get(i));
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados");
            e.printStackTrace();
        }
        System.out.println("""
                        Insira o id do contato que deseja deletar:
                        Ou digite 0 para retornar
                        """);
        int id = SC.nextInt();
        if(id == 0 ){
            System.out.println("Retornando...");
            return;
        }
        else {
            System.out.println(" Você tem certeza que deseja deletar o contato: ");
            int i = 0;
            for (Contato contato : contatos) {
                if (contato.getId() == id) {
                    System.out.println(contatos.get(i));
                    break;
                }
                i++;
            }
            System.out.println("""
                    Se realmente deseja deletar digite: 
                    1 - Sim!
                    2 - Não!
                    """);
            int opcao = SC.nextInt();
            while (true) {
                switch (opcao) {
                    case 1: {
                        var dao = new ContatoDao();
                        try {
                            dao.deletarContatoPorId(id);
                            System.out.println("""
                                    Contato removido da sua tabela.
                                    Retornando...
                                    """);
                            return;
                        } catch (SQLException e) {
                            System.out.println("Erro ao acessar o banco de dados");
                            e.printStackTrace();
                        }
                    }
                    case 2: {
                        System.out.println("""
                                Ok vamos retornar para o menu principal
                                Retornando...
                                """);
                        return;
                    }
                    default: {
                        System.out.println("""
                                Insira um opção correta.
                                Escolha novamente.
                                """);
                    }
                }
            }
        }
    }

    public static void buscarContato(){
        while (true){
            System.out.println("""
                Deseja buscar por: 
                1 - ID
                2 - NOME 
                3 - NUMERO
                4 - Buscar todos
                Caso queria retornar digite 0
                """);
            int opcao = SC.nextInt();
            switch (opcao){
                case 1:{
                    buscarContatoPorId();
                    break;
                }
                case 2:{
                    buscarContatoPorNome();
                    break;
                }
                case 3:{
                    buscarContatoPorNumero();
                    break;
                }
                case 4:{
                    buscarTodos();
                    break;
                }
                case 0:{
                    System.out.println("Ok, estamos retornando...");
                    return;
                }
                default:{
                    System.out.println("Opção escolhida incorretamente");
                }
            }
        }
    }

    public static void buscarContatoPorId(){
        System.out.println("Insira o id do contato que deseja buscar: ");
        int id = SC.nextInt();
        var dao = new ContatoDao();
        try{
            Contato contato = dao.buscarContatoPorId(id);
            if (contato != null){
                System.out.println("ID: " + contato.getId());
                System.out.println("Nome: " + contato.getNome());
                System.out.println("Número: " + contato.getNumero());
            }
            else{
                System.out.println("Contato não encontrado.");
            }
        }catch (SQLException e){
            System.out.println("Erro ao acessar o banco de dados");
            e.printStackTrace();
        }
    }

    public static void buscarContatoPorNome(){
        System.out.println("Insira o nome do contato que você deseja buscar: ");
        SC.nextLine();
        String nome = SC.nextLine();
        var dao = new ContatoDao();
        try{
            Contato contato = dao.buscarContatoPorNome(nome);
            if (contato != null){
                System.out.println("ID: " + contato.getId());
                System.out.println("Nome: " + contato.getNome());
                System.out.println("Número: " + contato.getNumero());
            }
            else{
                System.out.println("Contato não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados");
            e.printStackTrace();
        }
    }

    public static void buscarContatoPorNumero(){
        System.out.println("Insira o número do contato que deseja buscar: ");
        SC.nextLine();
        String numero = SC.nextLine();
        var dao = new ContatoDao();
        try{
            Contato contato = dao.buscarContatoPorNumero(numero);
            if (contato != null){
                System.out.println("ID: " + contato.getId());
                System.out.println("Nome: " + contato.getNome());
                System.out.println("Número: " + contato.getNumero());
            }
            else {
                System.out.println("Contato não encontrado.");
            }
        }catch (SQLException e){
            System.out.println("Erro ao acessar o banco de dados");
            e.printStackTrace();
        }
    }

    public static void buscarTodos(){
        ArrayList<Contato> contatos = new ArrayList<>();
        System.out.println("Vamos iniciar as buscas");
        var dao = new ContatoDao();
        try {
            contatos = dao.buscarTodos();
            if (contatos == null || contatos.isEmpty()){
                System.out.println("Nenhum contato encontrado");
            }
            else{
                for (int i = 0; i < contatos.size(); i++){
                    System.out.println(contatos.get(i));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados");
            e.printStackTrace();
        }
    }

    public static void listarTodos(){
        ArrayList<Contato> contatos = new ArrayList<>();
        var dao = new ContatoDao();
        try{
            contatos = dao.buscarTodos();
            if (contatos == null || contatos.isEmpty()){
                System.out.println("""
                        Nenhum contato está cadastrado no sistema, 
                        Cadastre um contato antes de querer deletar.
                        """);
            }
            else{
                System.out.println("Contatos salvos: ");
                for (int i = 0; i < contatos.size(); i++) {
                    System.out.println(contatos.get(i));
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados");
            e.printStackTrace();
        }
    }

    public static int quantidadeCadastrada(int qtd){
        int acesso = 0;
        System.out.println("Estamos somando os cadastrados...");
        var dao = new ContatoDao();
        try{
            qtd = dao.quantidadeTotal();
            if (qtd == 0 ){
                System.out.println("""
                        Nenhum contato está cadastrado no sistema, 
                        Cadastre um contato antes de querer deletar.
                        """);
                return qtd;
            }
            else {
                if (acesso == 0){
                    return qtd;
                }
                else {
                    System.out.println("Quantidade de contatos cadastrados é: " + qtd);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados");
            e.printStackTrace();
        }
        return qtd;
    }
}