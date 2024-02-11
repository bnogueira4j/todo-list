package tech.ada.java.todolist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private LocalDate prazoFinal;

    public TodoItem(){
        this.concluida = false;
        this.dataHora = LocalDateTime.now();
    }

    public TodoItem(String titulo,
                    String descricao,
                    LocalDate prazoFinal){
        this.titulo = titulo;
        this.descricao = descricao;
        this.prazoFinal = prazoFinal;
        this.concluida = false;
        this.dataHora = LocalDateTime.now();
    }
}