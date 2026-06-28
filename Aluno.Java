package br.ufla.gct052.biblioteca.model;

public class Aluno extends Usuario {
    private final String curso;
    private final int periodo;

    public Aluno(String idEstudante, String nomeEstudante, String emailEstudante, String habilitacaoCurso, int etapaPeriodo) {
        super(idEstudante, nomeEstudante, emailEstudante);
        this.curso = habilitacaoCurso;
        this.periodo = etapaPeriodo;
    }

    public String getCurso() { return this.curso; }
    public int getPeriodo() { return this.periodo; }

    @Override
    public int getLimiteEmprestimos() { return 3; }

    @Override
    public int getPrazoEmprestimoDias() { return 7; }
}