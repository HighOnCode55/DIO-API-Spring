package br.com.todo_simples.repository;

import br.com.todo_simples.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, mas boa prática para indicar que é um bean de repositório
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    // A mágica acontece aqui!
    // Não precisamos escrever nenhuma implementação. O Spring Data JPA
    // já nos fornece métodos como save(), findAll(), findById(), deleteById(), etc.
}