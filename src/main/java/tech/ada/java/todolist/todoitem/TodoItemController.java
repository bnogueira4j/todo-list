package tech.ada.java.todolist.todoitem;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.todolist.todolist.TodoList;
import tech.ada.java.todolist.todolist.TodoListRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController("/todo")
public class TodoItemController {


    private final TodoItemRepository itemRepository; // nossa depedencia do repositorio
    private final ModelMapper modelMapper; // nova dependencia modelmapper

    private final TodoListRepository listRepository; // nossa depedencia do repositorio

    @Autowired // Injetamos as dependencia vira construtor com padrao inversao de dependencia
    public TodoItemController(TodoItemRepository itemRepository, ModelMapper modelMapper, TodoListRepository listRepository) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.listRepository = listRepository;
    }

    @PostMapping("/todo-item")
    public ResponseEntity<TodoItem> cadastrarItem(@RequestBody TodoItemRequest request) {

        //Vamos converter a request "TodoItemRequest" que chegou no body para uma entidade "TodoItem" atraves a funcao que criamos toEntity
        //TodoItem todoItemConvertido = request.toEntity();

        //Vamos converter a request "TodoItemRequest" que chegou no body para uma entidade "TodoItem" atraves do componente model mapper
        TodoItem todoItemConvertido = modelMapper.map(request, TodoItem.class);

        //Vamos salvar a entidade criada no repositorio
        TodoItem novoTodoItem = itemRepository.save(todoItemConvertido);

        //Retornamos o status 201 com o body "corpo da resposta" o novoTodoItem que foi criado pelo repositorio
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTodoItem);
    }

    @GetMapping("/todo-item")
    public List<TodoItem> buscarTodos(){
        List<TodoItem> listaComTodos = itemRepository.findAll();
        return listaComTodos;
    }

    @GetMapping(value = "/todo-item", params = {"id"})
    public ResponseEntity<TodoItemResponse> buscarPorFiltro(@RequestParam Long id){
        Optional<TodoItem> optionalItem = itemRepository.findById(id);
        if(optionalItem.isPresent()){
            TodoItem todoItem = optionalItem.get();

            TodoItemResponse response = new TodoItemResponse();
            response.setTitulo(todoItem.getTitulo());
            response.setDescricao(todoItem.getDescricao());
            response.setConcluida(todoItem.getConcluida());
            response.setPrazoFinal(todoItem.getPrazoFinal());

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    //Criamos uma nova rota para atualizar partes especificas do nosso recurso /todo-item
    // identificando pelo path variable id
    @PatchMapping("/todo-item/{id}")
    public ResponseEntity<TodoItem> alterarStatus(
            @PathVariable Long id,
            @RequestBody AlteraStatusRequest request) throws Exception {

        // Buscamos pelo metodo findById que retorna um Optional<TodoItem> pois o mesmo pode nao existir no banco
        Optional<TodoItem> optionalTodoItem = itemRepository.findById(id);

        // Verificamos se existe valor dentro do Optional
        if(optionalTodoItem.isPresent()) {
            // Se existir vamos fazer o get() para tirar o valor de dentro do optional
            TodoItem todoItemModificado = optionalTodoItem.get();

            // verificamos se um das tres variaveis que esperamos foi passada para ser atualizada
            if(request.status() != null) todoItemModificado.setConcluida(request.status());
            if(request.titulo() != null) todoItemModificado.setTitulo(request.titulo());
            if(request.descricao() != null) todoItemModificado.setDescricao(request.descricao());

            //Depois de atualizar o que precisamos, vamos salvar
            TodoItem todoItemSalvo =  itemRepository.save(todoItemModificado);
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
        Optional<TodoItem> optionalTodoItem = itemRepository.findById(id);

        // Verificamos se existe valor dentro do Optional
        if(optionalTodoItem.isPresent()) {
            TodoItem todoItemExistente = optionalTodoItem.get();

            todoItemExistente.setTitulo( request.titulo() );
            todoItemExistente.setDescricao( request.descricao() );
            todoItemExistente.setConcluida( request.concluida() );
            todoItemExistente.setPrazoFinal( request.prazoFinal() );
            todoItemExistente.setDataHoraAtualizacao( LocalDateTime.now());

            TodoItem todoItemSalvo = itemRepository.save(todoItemExistente);

            return ResponseEntity.ok(todoItemSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/todo-item/lista")
    public ResponseEntity<TodoItem> colocarItemNaLista(@RequestBody ItemAdicionaListaRequest request) {

        //Vou buscar o item baseado pelo itemId
        TodoItem ItemRecuperado = itemRepository.findById(request.getIdItem()).get();
        // Vou buscar a lista pelo listId
        TodoList listaRecuperada = listRepository.findById(request.getIdLista()).get();

        listaRecuperada.getListaItems().add(ItemRecuperado);
        listRepository.save(listaRecuperada);

        return ResponseEntity.status(HttpStatus.CREATED).body(ItemRecuperado);
    }

    @Setter
    @Getter
    public class TodoItemResponse {
        private String titulo;
        private String descricao;
        private Boolean concluida;
        private LocalDate prazoFinal;
        private String lista;
    }
}