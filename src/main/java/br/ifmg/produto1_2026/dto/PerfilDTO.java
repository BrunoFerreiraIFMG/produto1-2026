package br.ifmg.produto1_2026.dto;

import br.ifmg.produto1_2026.entities.Perfil;

import java.util.Objects;

public class PerfilDTO {

    private Long id;
    private String nome;

    public PerfilDTO() {
    }

    public PerfilDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public PerfilDTO(Perfil perfil) {
        this.id = perfil.getId();
        this.nome = perfil.getNome();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    @Override
    public String toString() {
        return "PerfilDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

}
