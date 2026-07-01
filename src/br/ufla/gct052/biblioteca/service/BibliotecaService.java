package br.ufla.gct052.biblioteca.service;

import br.ufla.gct052.biblioteca.model.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BibliotecaService {
    private final Map<String, Usuario> usuarios = new HashMap<>();
    private final Map<String, Livro> livros = new HashMap<>();
    private final Map<String, Exemplar> exemplares = new HashMap<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();
    private int geradorIdEmprestimo = 1;

    public void cadastrarUsuario(Usuario u) { usuarios.put(u.getId(), u); }
    public void cadastrarLivro(Livro l) { livros.put(l.getIsbn(), l); }
    public void cadastrarExemplar(Exemplar e) { exemplares.put(e.getCodigo(), e); }

    public List<Usuario> listarUsuarios() { 
        List<Usuario> valores = new ArrayList<>(usuarios.values());
        List<Usuario> lista = new ArrayList<>();
        // For indexado para transferir os dados do mapa
        for (int i = 0; i < valores.size(); i++) {
            lista.add(valores.get(i));
        }
        return lista;
    }
    
    public Emprestimo realizarEmprestimo(String idUsuario, String codExemplar) {
        Usuario u = usuarios.get(idUsuario);
        Exemplar e = exemplares.get(codExemplar);

        // Retornado para o IF composto original com operador ||
        if (u == null || e == null) {
            throw new DominioException("Usuário ou exemplar não encontrado.");
        }
        if (!u.podeEmprestar()) {
            throw new DominioException("Limite de empréstimos atingido.");
        }
        if (e.getStatus() != StatusExemplar.DISPONIVEL) {
            throw new DominioException("Exemplar indisponível.");
        }

        Emprestimo novoEmprestimo = new Emprestimo("EMP-" + (geradorIdEmprestimo++), u, e);
        e.setStatus(StatusExemplar.EMPRESTADO);
        emprestimos.add(novoEmprestimo);
        return novoEmprestimo;
    }

    public void realizarDevolucao(String idEmprestimo) {
        Emprestimo emp = buscarEmprestimo(idEmprestimo);
        if (emp.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new DominioException("Já devolvido.");
        }

        emp.registrarDevolucao();
        emp.getExemplar().setStatus(StatusExemplar.DISPONIVEL);

        if (LocalDate.now().isAfter(emp.getDataPrevistaDevolucao())) {
            long dias = ChronoUnit.DAYS.between(emp.getDataPrevistaDevolucao(), LocalDate.now());
            double taxaMulta = dias * 2.50;
            System.out.printf("Aviso: Devolução com %d dias de atraso. Multa gerada: R$ %.2f%n", dias, taxaMulta);
        }
    }

    public void renovarEmprestimo(String idEmprestimo) {
        Emprestimo emp = buscarEmprestimo(idEmprestimo);
        if (emp.getStatus() != StatusEmprestimo.ATIVO) {
            throw new DominioException("Apenas empréstimos ativos podem ser renovados.");
        }
        if (LocalDate.now().isAfter(emp.getDataPrevistaDevolucao())) {
            throw new DominioException("Não é possível renovar empréstimo atrasado.");
        }
        
        LocalDate novaData = emp.getDataPrevistaDevolucao().plusDays(emp.getUsuario().getPrazoEmprestimoDias());
        emp.setDataPrevistaDevolucao(novaData);
        System.out.println("Empréstimo renovado com sucesso para: " + novaData);
    }

    private Emprestimo buscarEmprestimo(String id) {
        // For indexado para busca
        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo e = emprestimos.get(i);
            if (e.getId().equals(id)) {
                return e;
            }
        }
        throw new DominioException("Empréstimo não encontrado.");
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();
        // For indexado substituindo o forEach/Stream
        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo e = emprestimos.get(i);
            e.verificarAtraso();
            // Mantido o IF composto original com o operador ||
            if (e.getStatus() == StatusEmprestimo.ATIVO || e.getStatus() == StatusEmprestimo.ATRASADO) {
                ativos.add(e);
            }
        }
        return ativos;
    }
    
    public List<Emprestimo> listarEmprestimosPorUsuario(String idUsuario) {
        List<Emprestimo> filtrados = new ArrayList<>();
        // For indexado substituindo o filter da Stream
        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo e = emprestimos.get(i);
            if (e.getUsuario().getId().equals(idUsuario)) {
                filtrados.add(e);
            }
        }
        return filtrados;
    }
}