package br.ufla.gct052.biblioteca.model;

public class DominioException extends RuntimeException {
    public DominioException(String msgErro) {
        super(msgErro);
    }
}