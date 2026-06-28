package br.ufla.gct052.biblioteca.model;

public class Exemplar {
    private final String codigo;
    private final Livro livro;
    private StatusExemplar status;

    public Exemplar(String idExemplar, Livro obraReferencia) {
        if (idExemplar == null || idExemplar.isBlank() || obraReferencia == null) {
            throw new DominioException("Falha de Acervo: Identificação nula ou referência de obra inexistente.");
        }
        this.codigo = idExemplar;
        this.livro = obraReferencia;
        this.status = StatusExemplar.DISPONIVEL;
        obraReferencia.adicionarExemplar(this);
    }

    public String getCodigo() { return this.codigo; }
    public Livro getLivro() { return this.livro; }
    public StatusExemplar getStatus() { return this.status; }
    
    public void setStatus(StatusExemplar novoStatus) { 
        this.status = novoStatus; 
    }

    @Override
    public String toString() {
        return "Código de Barra: " + codigo + " (" + status + ")";
    }
}