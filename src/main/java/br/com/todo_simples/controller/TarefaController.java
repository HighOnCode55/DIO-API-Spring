package br.com.todo_simples.controller;

import br.com.todo_simples.model.Tarefa;
import br.com.todo_simples.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Combina @Controller e @ResponseBody, simplificando a criação de APIs REST
@RequestMapping("/tarefas") // Todas as requisições para /tarefas cairão neste controller
public class TarefaController {

    @Autowired // Spring injeta uma instância do TarefaRepository para nós
    private TarefaRepository tarefaRepository;

    // CREATE -> Criar uma nova tarefa
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED); // Retorna 201 Created
    }

    // READ -> Listar todas as tarefas
    @GetMapping
    public List<Tarefa> listarTodasAsTarefas() {
        return tarefaRepository.findAll();
    }

    // READ -> Buscar uma tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        // Se a tarefa existir, retorna 200 OK. Senão, retorna 404 Not Found.
        return tarefa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE -> Atualizar uma tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
        return tarefaRepository.findById(id)
                .map(tarefaExistente -> {
                    tarefaExistente.setDescricao(tarefaDetalhes.getDescricao());
                    tarefaExistente.setConcluida(tarefaDetalhes.isConcluida());
                    Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);
                    return ResponseEntity.ok(tarefaAtualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE -> Deletar uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefaRepository.delete(tarefa);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}