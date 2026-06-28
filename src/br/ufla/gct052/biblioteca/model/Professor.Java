package br.ufla.gct052.biblioteca.model;

public class Professor extends Usuario {
    private final String departamento;
    private final String titulacao;

    public Professor(String idDocente, String nomeDocente, String emailDocente, String areaDepto, String grauAcademico) {
        super(idDocente, nomeDocente, emailDocente);
        this.departamento = areaDepto;
        this.titulacao = grauAcademico;
    }

    public String getDepartamento() { return this.departamento; }
    public String getTitulacao() { return this.titulacao; }

    @Override
    public int getLimiteEmprestimos() { return 5; }

    @Override
    public int getPrazoEmprestimoDias() { return 14; }
}