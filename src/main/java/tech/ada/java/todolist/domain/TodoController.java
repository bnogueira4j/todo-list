package tech.ada.java.todolist.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController("/todo")
public class TodoController {

    private final TodoItemRepository todoItemRepository;

    @Autowired
    public TodoController(TodoItemRepository todoItemRepository){
        this.todoItemRepository = todoItemRepository;
    }

    @PostMapping("/todo-item")
    public ResponseEntity<TodoItem> cadastrarItem(@RequestBody TodoItemRequest request) {

        //Vamos converter a request "TodoItemRequest" que chegou no body para uma entidade "TodoItem"
        TodoItem todoItemConvertido = new TodoItem();
        todoItemConvertido.setTitulo(request.titulo());
        todoItemConvertido.setDescricao(request.descricao());
        todoItemConvertido.setPrazoFinal(request.prazoFinal());

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
}