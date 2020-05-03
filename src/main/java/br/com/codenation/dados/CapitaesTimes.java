package br.com.codenation.dados;

public class CapitaesTimes {
    private Long idTime;
    private Long idJogador;

    public CapitaesTimes(Long idTime, Long idJogador) {
        this.idTime = idTime;
        this.idJogador = idJogador;
    }

    public Long getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Long idJogador) {
        this.idJogador = idJogador;
    }

    public Long getIdTime() {
        return idTime;
    }

    public void setIdTime(Long idTime) {
        this.idTime = idTime;
    }
}
