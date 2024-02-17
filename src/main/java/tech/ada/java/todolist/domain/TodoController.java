package tech.ada.java.todolist.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController("/todo")
public class TodoController {


    private final TodoItemRepository todoItemRepository; // nossa depedencia do repositorio
    private final ModelMapper modelMapper; // nova dependencia modelmapper

    @Autowired // Injetamos as dependencia vira construtor com padrao inversao de dependencia
    public TodoController(TodoItemRepository todoItemRepository, ModelMapper modelMapper) {
        this.todoItemRepository = todoItemRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/todo-item")
    public ResponseEntity<TodoItem> cadastrarItem(@RequestBody TodoItemRequest request) {

        //Vamos converter a request "TodoItemRequest" que chegou no body para uma entidade "TodoItem" atraves a funcao que criamos toEntity
        //TodoItem todoItemConvertido = request.toEntity();

        //Vamos converter a request "TodoItemRequest" que chegou no body para uma entidade "TodoItem" atraves do componente model mapper
        TodoItem todoItemConvertido = modelMapper.map(request, TodoItem.class);

        //Vamos salvar a entidade criada no repositorio
        TodoItem novoTodoItem = todoItemRepository.save(todoItemConvertido);

        //Retornamos o status 201 com o body "corpo da resposta" o novoTodoItem que foi criado pelo repositorio
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTodoItem);
    }

    @GetMapping("/todo-item")
    public List<TodoItem> buscarTodos(){
        List<TodoItem> listaComTodos = todoItemRepository.findAll();
        return listaComTodos;
    }

    @GetMapping(value = "/todo-item", params = {"titulo"})
    public List<TodoItem> buscarPorFiltro(@RequestParam String titulo){
        String tituloSemEspacos = titulo.replaceAll("\\s", "");
        return todoItemRepository.findByTituloQuery(tituloSemEspacos);
    }

    //Criamos uma nova rota para atualizar partes especificas do nosso recurso /todo-item
    // identificando pelo path variable id
    @PatchMapping("/todo-item/{id}")
    public ResponseEntity<TodoItem> alterarStatus(
            @PathVariable Long id,
            @RequestBody AlteraStatusRequest request) throws Exception {

        // Buscamos pelo metodo findById que retorna um Optional<TodoItem> pois o mesmo pode nao existir no banco
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        // Verificamos se existe valor dentro do Optional
        if(optionalTodoItem.isPresent()) {
            // Se existir vamos fazer o get() para tirar o valor de dentro do optional
            TodoItem todoItemModificado = optionalTodoItem.get();

            // verificamos se um das tres variaveis que esperamos foi passada para ser atualizada
            if(request.status() != null) todoItemModificado.setConcluida(request.status());
            if(request.titulo() != null) todoItemModificado.setTitulo(request.titulo());
            if(request.descricao() != null) todoItemModificado.setDescricao(request.descricao());

            //Depois de atualizar o que precisamos, vamos salvar
            TodoItem todoItemSalvo =  todoItemRepository.save(todoItemModificado);
            return ResponseEntity.ok(todoItemSalvo);

        } else {
            // Caso nao encontramos na valor no Optional retornamos o codigo 404 - nao encontrado
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/todo-item/{id}")
    public ResponseEntity<TodoItem> alteraTodoItemCompleto(
            @PathVariable Long id,
            @RequestBody AlteraTodoItemCompletoRequest request
    ) {
        // Buscamos pelo metodo findById que retorna um Optional<TodoItem> pois o mesmo pode nao existir no banco
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        // Verificamos se existe valor dentro do Optional
        if(optionalTodoItem.isPresent()) {
            TodoItem todoItemExistente = optionalTodoItem.get();

            todoItemExistente.setTitulo( request.titulo() );
            todoItemExistente.setDescricao( request.descricao() );
            todoItemExistente.setConcluida( request.concluida() );
            todoItemExistente.setPrazoFinal( request.prazoFinal() );
            todoItemExistente.setDataHoraAtualizacao( LocalDateTime.now());

            TodoItem todoItemSalvo = todoItemRepository.save(todoItemExistente);

            return ResponseEntity.ok(todoItemSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}