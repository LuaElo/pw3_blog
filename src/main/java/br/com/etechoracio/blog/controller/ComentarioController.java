//LUARA E ELOAH 3 AI
package br.com.etechoracio.blog.controller;

import br.com.etechoracio.blog.entity.Comentario;
import br.com.etechoracio.blog.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository repository;

    @GetMapping
    public List<Comentario> listarComentario(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id){
        var resposta = repository.findById(id);
        if (resposta.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(resposta.get());
        }
    }
    @PostMapping
    public ResponseEntity<Comentario> inserir(@RequestBody Comentario comentario){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(comentario));
    }

    @PutMapping("/{id}")
    public ResponseEntity <Comentario> atualizar (@PathVariable Long id, @RequestBody Comentario comentario){
        var existe = repository.findById(id);
        if (!existe.isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(repository.save(comentario));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Comentario> deletar (@PathVariable Long id){
        var existe = repository.findById(id);
        if (!existe.isPresent())
            return ResponseEntity.notFound().build();
        repository.delete(existe.get());
        return ResponseEntity.ok().build();
    }
}
