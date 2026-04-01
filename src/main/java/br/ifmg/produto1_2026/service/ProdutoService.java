package br.ifmg.produto1_2026.service;


import br.ifmg.produto1_2026.dto.CategoriaDTO;
import br.ifmg.produto1_2026.dto.ProdutoDTO;
import br.ifmg.produto1_2026.entities.Categoria;
import br.ifmg.produto1_2026.entities.Produto;
import br.ifmg.produto1_2026.repositories.CategoriaRepository;
import br.ifmg.produto1_2026.repositories.ProdutoRepository;
import br.ifmg.produto1_2026.service.exception.ErroNoBancoDeDados;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(Pageable pageRequest) {

        //Lista com os dados do BD.
        Page<Produto> produtos =
                repository.findAll(pageRequest);


        return produtos.map(ProdutoDTO::new);
    }


    @Transactional(readOnly = true)
    public ProdutoDTO findById(Long id) {
        //buscamos no BD o produto. O resultado é
        //um objeto do tipo Optional.
        Optional<Produto> opt
                = repository.findById(id);
        //buscamos o produto dentro do objeto Optional
        Produto produto = opt.orElseThrow(
                () -> new RegistroNaoEncontrado("Produto não encontrado."));
        //Convertemos a entidade em DTO
        return new ProdutoDTO(produto);
    }

    @Transactional
    public ProdutoDTO insert(ProdutoDTO dto) {

        Produto entity = new Produto();
        coptDtoToEntity(dto, entity);


        Produto novo = repository.save(entity);
        return new ProdutoDTO(novo);
    }

    @Transactional
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RegistroNaoEncontrado("Produto não encontrado, ao ser excluído.");
        }

        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new ErroNoBancoDeDados(e.getMessage());
        }
    }


    public ProdutoDTO update(Long id, ProdutoDTO dto) {

        if (!repository.existsById(id)) {
            throw new RegistroNaoEncontrado("Produto não encontrado, para ser alterado.");
        }

        Produto entity =
                repository.getReferenceById(id);

        coptDtoToEntity(dto, entity);

        entity = repository.save(entity);
        return new ProdutoDTO(entity);
    }

    private void coptDtoToEntity(ProdutoDTO dto, Produto entity) {
        entity.setNome(dto.getNome());//sobrescrevi o nome antigo
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        entity.setImgUrl(dto.getImgUrl());

        entity.getCategorias().clear();
        for (CategoriaDTO catDto : dto.getCategorias()){
            Categoria cat =
                    categoriaRepository
                            .getReferenceById(catDto.getId());
            entity.getCategorias().add(cat);
        }
    }


}
