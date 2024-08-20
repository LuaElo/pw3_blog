package br.com.etechoracio.blog.controller;
import br.com.etechoracio.blog.entity.Post;
import br.com.etechoracio.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // fica escutando qualquer requisição http que chega
@RequestMapping("/posts") // escuta um determinado post -> usar no plural (colecao de usuarios)
public class PostController {
    @Autowired // ele cria um objeto
    private PostRepository repository;
    @GetMapping // CONSULTAR
   public List<Post> listarPosts(){
       return repository.findAll();
   }
   @GetMapping("/{id}")//vai listar
   public ResponseEntity<Object> buscarPorId(@PathVariable Long id){ // essa variavel vai vir do path
        var resposta = repository.findById(id);
        if (resposta.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(resposta.get());
        }
   }
   @PostMapping
   public ResponseEntity<Post> inserir(@RequestBody Post post){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(post));
   }

   @PutMapping("/{id}")
    public ResponseEntity <Post> atualizar (@PathVariable Long id, @RequestBody Post post){
        var existe = repository.findById(id);
        if (!existe.isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(repository.save(post));
   }
}
