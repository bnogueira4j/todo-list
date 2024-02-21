package tech.ada.java.todolist.todoitem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import tech.ada.java.todolist.todolist.TodoList;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@EqualsAndHashCode
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private Boolean concluida;
    private LocalDateTime dataHora;
    private LocalDateTime dataHoraAtualizacao;
    private LocalDate prazoFinal;

    public TodoItem(){
        this.concluida = false;
        this.dataHora = LocalDateTime.now();
        this.dataHoraAtualizacao = LocalDateTime.now();
    }

    public TodoItem(String titulo,
                    String descricao,
                    LocalDate prazoFinal){
        this.titulo = titulo;
        this.descricao = descricao;
        this.prazoFinal = prazoFinal;
        this.concluida = false;
        this.dataHora = LocalDateTime.now();
        this.dataHoraAtualizacao = LocalDateTime.now();
    }
}