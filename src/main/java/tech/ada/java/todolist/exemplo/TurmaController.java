package tech.ada.java.todolist.exemplo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TurmaController {

    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;

    public TurmaController(TurmaRepository turmaRepository, AlunoRepository alunoRepository) {
        this.turmaRepository = turmaRepository;

        this.alunoRepository = alunoRepository;
    }

    @PostMapping("/turma")
    public Turma cadastrarLista(@RequestBody String nome) {
       var turma = new Turma();
       turma.setNome(nome);
       var aluno = alunoRepository.findAll();
       turma.setAlunos(aluno);
       return turmaRepository.save(turma);
    }

    @PostMapping("/aluno")
    public Aluno saveAluno(@RequestBody String nome) {
        var aluno = new Aluno();

        var turmas = turmaRepository.findAll().getFirst();
        aluno.setNome(nome);
        aluno.setTurma(turmas);
        return alunoRepository.save(aluno);
    }
}
