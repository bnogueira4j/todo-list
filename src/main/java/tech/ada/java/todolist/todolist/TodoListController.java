package tech.ada.java.todolist.todolist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.todolist.client.Result;
import tech.ada.java.todolist.client.TodoExterno;
import tech.ada.java.todolist.client.TodoRestClient;
import tech.ada.java.todolist.todoitem.TodoItem;
import tech.ada.java.todolist.todoitem.TodoItemRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoListController {

    private TodoListRepository todoListRepository;
    private TodoItemRepository todoItemRepository;
    private TodoRestClient todoRestClient;

    public TodoListController(TodoListRepository todoListRepository, TodoItemRepository todoItemRepository, TodoRestClient todoRestClient) {
        this.todoListRepository = todoListRepository;
        this.todoItemRepository = todoItemRepository;
        this.todoRestClient = todoRestClient;
    }

    @PostMapping("/todo-list")
    public ResponseEntity<TodoList> cadastrarLista(@RequestBody TodoListRequest request) {
       var todoList = new TodoList();
       todoList.setNome(request.getNome());

//
//       var todoItems =  todoItemRepository.findAll();
//
//       todoList.setTodoItems(todoItems);

       var todoListSalvo =  todoListRepository.save(todoList);

       return ResponseEntity.ok(todoListSalvo);
    }

    @GetMapping("/todo-externo")
    public List<TodoItem> buscarTodosExternos() {
        List<TodoExterno> listTodoExterno = todoRestClient.getAllTodos().todos();

//         listTodoExterno.stream()
//                .map(this::converter)
//                .forEach(this.todoItemRepository::save);

        List<TodoItem> listaDeRetorno = new ArrayList<>();
        for (var todoExterno : listTodoExterno) {
            TodoItem novoItem = converter(todoExterno);
            var itemSalvo = todoItemRepository.save(novoItem);
            listaDeRetorno.add(itemSalvo);
        }

        return listaDeRetorno;

    }

    private TodoItem converter(TodoExterno todoExterno) {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitulo(todoExterno.todo());
        todoItem.setDescricao("Descricao: " + todoExterno.todo());
        todoItem.setConcluida(todoExterno.completed());
        return todoItem;
    }
}
