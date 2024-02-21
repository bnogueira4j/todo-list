package tech.ada.java.todolist.todolist;

import jakarta.persistence.*;
import lombok.*;
import tech.ada.java.todolist.todoitem.TodoItem;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;
    @OneToMany
    private List<TodoItem> listaItems;
    private Long prioridade;
}