package br.com.todo_simples.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity // Marca esta classe como uma entidade JPA (será uma tabela no banco)
@Table(name = "tarefas") // Define o nome da tabela
public class Tarefa {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Id // Marca este campo como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de auto-incremento
    private Long id;

    @Column(nullable = false) // Garante que a coluna não pode ser nula
    @JsonProperty("descricao")
    private String descricao;

    private boolean concluida;

    private LocalDate dataCriacao;

    @PrePersist // Executa o método antes de salvar a entidade pela primeira vez
    public void antesDeSalvar() {
        this.dataCriacao = LocalDate.now();
        this.concluida = false; // Garante que toda nova tarefa começa como não concluída
    }
}