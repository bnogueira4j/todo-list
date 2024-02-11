package tech.ada.java.todolist.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoItemRequest {
    private String titulo;
    private String descricao;
    private LocalDate prazoFinal;

    //Transformar uma request em entidade
    public TodoItem toEntity() {
        return new TodoItem(titulo, descricao, prazoFinal);
    }
}
