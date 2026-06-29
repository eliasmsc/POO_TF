package br.ufla.gct052.biblioteca.app;

import br.ufla.gct052.biblioteca.model.*;
import br.ufla.gct052.biblioteca.service.BibliotecaService;
import java.util.List;

public class App {
    public static void main(String[] args) {
        BibliotecaService bibliotecaService = new BibliotecaService();

        System.out.println("=== 1. Cadastro de Usuários ===");
        bibliotecaService.cadastrarUsuario(new Aluno("U1", "Lucas Oliveira", "lucas.oliveira@ufla.br", "Engenharia de Software", 4));
        bibliotecaService.cadastrarUsuario(new Professor("U2", "Roberto Almeida", "roberto.almeida@ufla.br", "DEG", "Mestre"));
        bibliotecaService.cadastrarUsuario(new Servidor("U3", "Fernanda Costa", "fernanda.costa@ufla.br", "TI", "Analista de Sistemas"));
        System.out.println("Filiados registrados no sistema.\n");

        System.out.println("=== 2. Exibição Polimórfica (Limites e Prazos) ===");
        List<Usuario> listaGeralUsuarios = bibliotecaService.listarUsuarios();
        int totalUsuarios = listaGeralUsuarios.size();
        for (int i = 0; i < totalUsuarios; i++) {
            Usuario usuarioDaVez = listaGeralUsuarios.get(i);
            System.out.printf("Nome: %s | Limite: %d | Prazo: %d dias%n", 
                usuarioDaVez.getNome(), usuarioDaVez.getLimiteEmprestimos(), usuarioDaVez.getPrazoEmprestimoDias());
        }

        System.out.println("\n=== 3 e 4. Cadastro de Livros e Exemplares ===");
        Livro javaObra = new Livro("111", "Java Como Programar", "Deitel", 2017);
        Livro codeCleanObra = new Livro("222", "Clean Code", "Robert C. Martin", 2008);
        Livro patternObra = new Livro("333", "Padrões de Projetos", "GoF", 1994);
        
        bibliotecaService.cadastrarLivro(javaObra); 
        bibliotecaService.cadastrarLivro(codeCleanObra); 
        bibliotecaService.cadastrarLivro(patternObra);

        bibliotecaService.cadastrarExemplar(new Exemplar("EX1", javaObra));
        bibliotecaService.cadastrarExemplar(new Exemplar("EX2", javaObra));
        bibliotecaService.cadastrarExemplar(new Exemplar("EX3", codeCleanObra));
        bibliotecaService.cadastrarExemplar(new Exemplar("EX4", patternObra));
        bibliotecaService.cadastrarExemplar(new Exemplar("EX5", patternObra));
        System.out.println("Títulos e volumes físicos indexados.\n");

        System.out.println("=== 5 e 6. Testes de Empréstimo ===");
        try {
            Emprestimo locacaoValida = bibliotecaService.realizarEmprestimo("U1", "EX1");
            System.out.println("Locação aceita: " + locacaoValida);

            System.out.println("\nTentativa indesejada de locar volume já retido:");
            bibliotecaService.realizarEmprestimo("U2", "EX1");
        } catch (DominioException erroEx) {
            System.out.println("Captura esperada: " + erroEx.getMessage());
        }

        System.out.println("\n=== 7. Tentativa de empréstimo acima do limite ===");
        try {
            bibliotecaService.realizarEmprestimo("U1", "EX2");
            bibliotecaService.realizarEmprestimo("U1", "EX3");
            System.out.println("Aviso: Usuário atingiu sua cota de empréstimos simultâneos.");
            
            bibliotecaService.realizarEmprestimo("U1", "EX4");
        } catch (DominioException erroEx) {
            System.out.println("Captura esperada: " + erroEx.getMessage());
        }

        System.out.println("\n=== Teste de Bônus (Renovação) ===");
        try {
            bibliotecaService.renovarEmprestimo("EMP-1");
        } catch (DominioException erroEx) {
            System.out.println("Exceção capturada: " + erroEx.getMessage());
        }

        System.out.println("\n=== 8 e 9. Devolução e Bônus (Multa) ===");
        try {
            bibliotecaService.realizarDevolucao("EMP-1");
            System.out.println("Operação de recebimento concluída.");
            
            System.out.println("\nTentativa de reenvio de volume já entregue:");
            bibliotecaService.realizarDevolucao("EMP-1");
        } catch (DominioException erroEx) {
            System.out.println("Captura esperada: " + erroEx.getMessage());
        }
        
        System.out.println("\n=== 10, 11 e 12. Relatórios ===");
        System.out.println("Contagem de locações pendentes: " + bibliotecaService.listarEmprestimosAtivos().size());
        
        List<Emprestimo> relatorioLucas = bibliotecaService.listarEmprestimosPorUsuario("U1");
        System.out.println("Extrato de empréstimos do Lucas Oliveira (U1):");
        int totalHistorico = relatorioLucas.size();
        for (int idx = 0; idx < totalHistorico; idx++) {
            Emprestimo itemHistorico = relatorioLucas.get(idx);
            System.out.println(" -> " + itemHistorico.getExemplar().getLivro().getTitulo() + " [" + itemHistorico.getStatus() + "]");
        }

        System.out.println("Soma de volumes livres para 'Java Como Programar': " + javaObra.getExemplaresDisponiveis().size());
    }
}
