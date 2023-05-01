package br.com.gab.Sistema.ERP.Generics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ControllerGenerics<T extends DTO, Model extends br.com.gab.Sistema.ERP.Generics.Model> {

    public abstract <SERVICE extends CrudRepository> SERVICE getService();

    public T field;

    private Class<T> dtoClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @ResponseBody
    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public String teste() {
        return "Got it";
    }

    @ResponseBody
    @PostMapping(value = "/salvar")
    public RetornoDTO salvar(@RequestBody T dto) {
        RetornoDTO retornoDTO = new RetornoDTO();
        try {

            System.out.println("CAIU AQUI");
            this.beforeSave(dto);
            this.getService().save(dto.toEntity());
            retornoDTO.setMessage("Salvo com sucesso");
            return retornoDTO;
        } catch (Exception e) {
            retornoDTO.setMessage(e.getMessage());
            return retornoDTO;
        }
    }

    public T getIntanceOfIt(Class<T> aClass) throws InstantiationException, IllegalAccessException {
        return aClass.newInstance();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public RetornoDTO buscarID(@PathVariable("id") Long id) {
        RetornoDTO retornoDTO = new RetornoDTO();
        try {
            Optional model = this.getService().findById(id);
            if (!model.isEmpty()) {
                field = dtoClass.newInstance();
                retornoDTO.setObject(model.get());
                retornoDTO.setMessage("Registro encontrado com sucesso");
                return retornoDTO;
            }
            throw new Exception("Registo não encontrado");
        } catch (Exception e) {
            retornoDTO.setMessage(e.getMessage());
            return retornoDTO;
        }
    }


    @ResponseBody
    @GetMapping("/")
    public RetornoDTO buscarTodos() throws Exception{
        RetornoDTO retornoDTO = new RetornoDTO();
        try {
            List<Object> listaResposta = new ArrayList<>();
//            this.getService().findAll().stream().map(T::toDTO).collect(Collectors.toList());
            this.getService().findAll().forEach(i -> {
                try {
                    T t = dtoClass.newInstance();
                    listaResposta.add(t.toDTO(i));
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            retornoDTO.setObject(listaResposta);
            retornoDTO.setMessage("Todos usuários resgatados com sucesso");
            return retornoDTO;
        }catch (Exception e){
            retornoDTO.setMessage("um erro ocorreu");
            return retornoDTO;
        }
    }

    public void beforeSave(T t) throws Exception{}
    public void afterSave(T t) throws Exception{}

}
