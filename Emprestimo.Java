package br.ufla.gct052.biblioteca.model;

import java.time.LocalDate;

public class Emprestimo implements Identificavel {
    private final String id;
    private final Usuario usuario;
    private final Exemplar exemplar;
    private final LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;

    public Emprestimo(String chaveId, Usuario locatario, Exemplar itemAcervo) {
        this.id = chaveId;
        this.usuario = locatario;
        this.exemplar = itemAcervo;
        this.dataEmprestimo = LocalDate.now();
        int diasConcedidos = locatario.getPrazoEmprestimoDias();
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(diasConcedidos);
        this.status = StatusEmprestimo.ATIVO;
        this.usuario.incrementarEmprestimos();
    }

    @Override
    public String getId() { return this.id; }
    public Usuario getUsuario() { return this.usuario; }
    public Exemplar getExemplar() { return this.exemplar; }
    public LocalDate getDataPrevistaDevolucao() { return this.dataPrevistaDevolucao; }
    public LocalDate getDataDevolucao() { return this.dataDevolucao; }
    public StatusEmprestimo getStatus() { return this.status; }
    
    public void setDataPrevistaDevolucao(LocalDate novaDataPrevista) { 
        this.dataPrevistaDevolucao = novaDataPrevista; 
    }

    public void registrarDevolucao() {
        this.dataDevolucao = LocalDate.now();
        this.status = StatusEmprestimo.DEVOLVIDO;
        this.usuario.decrementarEmprestimos();
    }

    public void verificarAtraso() {
        LocalDate momentoAtual = LocalDate.now();
        if (this.status == StatusEmprestimo.ATIVO && momentoAtual.isAfter(this.dataPrevistaDevolucao)) {
            this.status = StatusEmprestimo.ATRASADO;
        }
    }

    @Override
    public String toString() {
        return "Protocolo [" + id + "] -> " + status + " | Destinatário: " + usuario.getNome() + " | Item: " + exemplar.getCodigo();
    }
}