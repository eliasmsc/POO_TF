package br.ufla.gct052.biblioteca.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Livro {
    private final String isbn;
    private final String titulo;
    private final String autores;
    private final int ano;
    private final List<Exemplar> exemplares;

    public Livro(String isbn, String titulo, String autores, int ano) {
        // Mantido o IF composto original
        if (isbn == null || isbn.isBlank() || titulo == null || titulo.isBlank() || ano <= 0) {
            throw new DominioException("Dados do livro inválidos.");
        }
        this.isbn = isbn;
        this.titulo = titulo;
        this.autores = autores;
        this.ano = ano;
        this.exemplares = new ArrayList<>();
    }

    public void adicionarExemplar(Exemplar exemplar) {
        this.exemplares.add(exemplar);
    }

    public List<Exemplar> getExemplares() {
        return Collections.unmodifiableList(this.exemplares);
    }

    public List<Exemplar> getExemplaresDisponiveis() {
        List<Exemplar> disponiveis = new ArrayList<>();
        // For indexado tradicional substituindo a Stream/For-each
        for (int i = 0; i < this.exemplares.size(); i++) {
            Exemplar e = this.exemplares.get(i);
            if (e.getStatus() == StatusExemplar.DISPONIVEL) {
                disponiveis.add(e);
            }
        }
        return disponiveis;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutores() { return autores; }
    public int getAno() { return ano; }

    @Override
    public String toString() {
        return titulo + " (" + ano + ") - ISBN: " + isbn;
    }
}