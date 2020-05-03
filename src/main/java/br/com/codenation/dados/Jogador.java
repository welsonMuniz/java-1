package br.com.codenation.dados;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Jogador implements Comparable<Jogador>{

    private Long id;
    private Long idTime;
    private String nome;
    private LocalDate dataNascimento;
    private Integer nivelHabilidade;
    private BigDecimal salario;

    public Jogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        this.id = id;
        this.idTime = idTime;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nivelHabilidade = nivelHabilidade;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTime() {
        return idTime;
    }

    public void setIdTime(Long idTime) {
        this.idTime = idTime;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getNivelHabilidade() {
        return nivelHabilidade;
    }

    public void setNivelHabilidade(Integer nivelHabilidade) {
        this.nivelHabilidade = nivelHabilidade;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    @Override
    public int compareTo(Jogador o) {
        if(this.getNivelHabilidade() > o.getNivelHabilidade()){
            return -1;
        } else if (this.getNivelHabilidade() < o.getNivelHabilidade()){
            return 1;
        }

        if(this.getId() < o.getId()){
            return -1;
        } else {
            return 1;
        }
    }
// Excessoes que devem ser lançadas
    //Caso o `identificador` já exista, retornar `br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException`
    //Caso o time informado não exista, retornar `br.com.codenation.desafio.exceptions.TimeNaoEncontradoException`
}