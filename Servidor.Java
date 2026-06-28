package br.ufla.gct052.biblioteca.model;

public class Servidor extends Usuario {
    private final String setor;
    private final String cargo;

    public Servidor(String idColaborador, String nomeColaborador, String emailColaborador, String reparticaoSetor, String funcaoCargo) {
        super(idColaborador, nomeColaborador, emailColaborador);
        this.setor = reparticaoSetor;
        this.cargo = funcaoCargo;
    }

    public String getSetor() { return this.setor; }
    public String getCargo() { return this.cargo; }

    @Override
    public int getLimiteEmprestimos() { return 4; }

    @Override
    public int getPrazoEmprestimoDias() { return 10; }
}