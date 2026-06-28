package br.ufla.gct052.biblioteca.model;

public abstract class Usuario implements Identificavel {
    private final String id;
    private final String nome;
    private final String email;
    private int emprestimosAtivos;

    public Usuario(String id, String nome, String email) {
        // Mantido o IF composto original com os operadores ||
        if (id == null || id.isBlank() || nome == null || nome.isBlank() || email == null || email.isBlank()) {
            throw new DominioException("ID, nome e e-mail não podem ser nulos ou vazios.");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.emprestimosAtivos = 0;
    }

    public boolean podeEmprestar() {
        return this.emprestimosAtivos < getLimiteEmprestimos();
    }

    protected void incrementarEmprestimos() {
        this.emprestimosAtivos++;
    }

    protected void decrementarEmprestimos() {
        if (this.emprestimosAtivos > 0) {
            this.emprestimosAtivos--;
        }
    }

    public abstract int getLimiteEmprestimos();
    public abstract int getPrazoEmprestimoDias();

    @Override
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public int getEmprestimosAtivos() { return emprestimosAtivos; }

    @Override
    public String toString() {
        return "[" + id + "] " + nome + " - Empréstimos Ativos: " + emprestimosAtivos;
    }
}