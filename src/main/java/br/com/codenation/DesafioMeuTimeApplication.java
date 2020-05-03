package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.codenation.dados.CapitaesTimes;
import br.com.codenation.dados.Jogador;
import br.com.codenation.dados.Time;
import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	public List<Time> times = new ArrayList<>();
	public List<Jogador> jogadores = new ArrayList<>();
	public List<CapitaesTimes> capitaesTimes = new ArrayList<>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		try {
			if (id == null){
				throw new NullPointerException("Valor id invalido");
			}
			if (nome == null){
				throw new NullPointerException("Valor nome invalido");
			}
			if (dataCriacao == null){
				throw new NullPointerException("Valor dataCriacao invalido");
			}
			if (corUniformePrincipal == null){
				throw new NullPointerException("Valor corUniformePrincipal invalido");
			}
			if (corUniformeSecundario == null){
				throw new NullPointerException("Valor corUniformeSecundario invalido");
			}

			if (id < 0){
				throw new IllegalArgumentException("Valor id invalido");
			}
			incluirTimeValidate(id);
			times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
		} catch (NullPointerException e) {
			throw new NullPointerException();
		} catch (IllegalArgumentException e){
			throw new IllegalArgumentException();
		} catch (IdentificadorUtilizadoException e){
			throw new IdentificadorUtilizadoException();
		}catch (Exception e){
			throw new NullPointerException();
		}
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		try {

			if(id == null){
				throw new NullPointerException("Valor id invalido");
			}

			if(idTime == null){
				throw new NullPointerException("Valor idTime invalido");
			}

			if(nome == null){
				throw new NullPointerException("Valor nome invalido");
			}

			if(dataNascimento == null){
				throw new NullPointerException("Valor dataNascimento invalido");
			}

			if(nivelHabilidade == null){
				throw new NullPointerException("Valor nivelHabilidade invalido");
			}

			if(salario == null){
				throw new NullPointerException("Valor salario invalido");
			}

			if(salario.compareTo(BigDecimal.ZERO) < 0 ){
				throw new IllegalArgumentException("Valor salario invalido");
			}

			if(nivelHabilidade.compareTo(100) > 0 || nivelHabilidade.compareTo(0) < 0){
				throw new IllegalArgumentException();
			}

			incluirJogadorValidate(id, idTime);
			jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		} catch (NullPointerException e){
			throw new NullPointerException();
		}catch (IdentificadorUtilizadoException e){
			throw new IdentificadorUtilizadoException();
		}catch (TimeNaoEncontradoException e){
			throw new TimeNaoEncontradoException();
		}catch (Exception e){
			throw new NullPointerException();
		}
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		Long timeAssociar = 0l;
		int i = 0;
		int remover = 0;
		try {
			validaJogador(idJogador);
			for (Jogador jogador : jogadores) {
				if (jogador.getId().equals(idJogador)){
					timeAssociar = jogador.getIdTime();
					break;
				}
			}
			for (CapitaesTimes capitaes : capitaesTimes) {
				if (capitaes.getIdTime().equals(timeAssociar)){
					remover = i;
				}
				i++;
			}
			if(remover > 0){
				capitaesTimes.remove(i);
			}
			capitaesTimes.add(new CapitaesTimes(timeAssociar, idJogador) );

		} catch (Exception e) {
			throw new JogadorNaoEncontradoException();
		}
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		Long jogador = null;
		try {
			validaTime(idTime);
			validaCapitao(idTime);
			for (CapitaesTimes capitao: capitaesTimes) {
				if(capitao.getIdTime().equals(idTime)){
					jogador = capitao.getIdJogador();
				}
			}
		} catch (TimeNaoEncontradoException e) {
			throw new TimeNaoEncontradoException();
		} catch (CapitaoNaoInformadoException e) {
			throw new CapitaoNaoInformadoException();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		//retirar que nao eh o melhor tratamento
		//if (jogador == null){
		//	throw new TimeNaoEncontradoException();
		//}
		return jogador;
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		String nomeJogador = null;
		try {
			validaJogador(idJogador);
			for (Jogador jogador: jogadores) {
				if (jogador.getId().equals(idJogador)){
					nomeJogador = jogador.getNome();
				}
			}

		} catch (Exception e) {
			throw new JogadorNaoEncontradoException();
		}

		return nomeJogador;
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		String nomeTime = null;
		try {
			validaTime(idTime);
			for(Time time: times){
				if(time.getId().equals(idTime)){
					nomeTime = time.getNome();
				}
			}
		} catch (Exception e) {
			throw new TimeNaoEncontradoException();
		}
		return nomeTime;
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		List<Long> jogadoresTime = new ArrayList<>();;
		try {
			validaTime(idTime);
			for (Jogador jogador: jogadores) {
				if (jogador.getIdTime().equals(idTime)){
					jogadoresTime.add(jogador.getId());
				}
			}
			Collections.sort(jogadoresTime);
		} catch (Exception e) {
			throw new TimeNaoEncontradoException();
		}
		return jogadoresTime;
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		Long jogadorRet = 0l;
		try {
			validaTime(idTime);
			Integer nivelHabilidade = 0;
			for (Jogador jogador: jogadores){
				if(jogador.getIdTime().equals(idTime)){
					if(jogador.getNivelHabilidade() > nivelHabilidade){
						jogadorRet = jogador.getId();
						nivelHabilidade = jogador.getNivelHabilidade();
					} else if(jogador.getNivelHabilidade().equals(nivelHabilidade) &&
							jogador.getId() < jogadorRet){
						jogadorRet = jogador.getId();
						nivelHabilidade = jogador.getNivelHabilidade();
					}
				}
			}
		} catch (Exception e) {
			throw new TimeNaoEncontradoException();
		}
		return jogadorRet;
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		Long jogadorRet = 0l;
		try {
			validaTime(idTime);
			LocalDate dtNascimento = LocalDate.of(9999,01,01);
			for (Jogador jogador: jogadores){
				if(jogador.getIdTime().equals(idTime)){
					if(jogador.getDataNascimento().compareTo(dtNascimento) < 0){
						jogadorRet = jogador.getId();
						dtNascimento = jogador.getDataNascimento();
					}else if(jogador.getDataNascimento().compareTo(dtNascimento) == 0 &&
							                       jogador.getId() < jogadorRet ){
						jogadorRet = jogador.getId();
						dtNascimento = jogador.getDataNascimento();
					}
				}
			}
		} catch (Exception e) {
			throw new TimeNaoEncontradoException();
		}
		return jogadorRet;
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		List<Long> listaIdTimes = new ArrayList<>();

		for (Time time : times) {
			listaIdTimes.add(time.getId());
		}

		Collections.sort(listaIdTimes);
		return listaIdTimes;
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		Long jogadorRet = null;
		BigDecimal salario = BigDecimal.valueOf(0);
		try {
			validaTime(idTime);
			Collections.sort(jogadores);
			for (Jogador jogador: jogadores){
				if(jogador.getIdTime().equals(idTime)){
					if(jogador.getSalario().compareTo(salario) > 0){
						jogadorRet = jogador.getId();
						salario = jogador.getSalario();
					} else if (jogador.getSalario().compareTo(salario) == 0 &&
							jogador.getId() < jogadorRet){
						jogadorRet = jogador.getId();
						salario = jogador.getSalario();
					}
				}
			}

		} catch (Exception e) {
			throw new TimeNaoEncontradoException();
		}
		return jogadorRet;
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		BigDecimal salario = null;
		try {
			validaJogador(idJogador);
			for (Jogador jogador:jogadores) {
				if (jogador.getId().equals(idJogador) ){
					salario = jogador.getSalario();
					break;
				}
			}
		} catch (Exception e) {
			throw new JogadorNaoEncontradoException();
		}
		return salario;
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		List<Jogador> topJogadores = new ArrayList<>();
		List<Long> ret = new ArrayList<>();
		List<Long> retLimitado = new ArrayList<>();
		Collections.sort(jogadores);
		topJogadores = jogadores;
		Long jogAdd = null;

		if (jogadores != null && !jogadores.isEmpty()){
			if (jogadores.size() < top){
				throw new IllegalArgumentException("Solicitado Numero Maior do que Existente");
			}

			for (Jogador j:topJogadores) {
				jogAdd = j.getId();

				for (Jogador j2:jogadores) {
					if (j.getNivelHabilidade().compareTo(j2.getNivelHabilidade()) > 0) {
						jogAdd = j.getId();
					}
				}
				ret.add(jogAdd);
			}
			for(int i = 0; i < top; i++){
				retLimitado.add(ret.get(i));
			}
		}
		return retLimitado;
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		String corCamisaAdversario = null;
		String cmTmCasa  = null;
		String cmTmFora = null;
		String cmSecFora = null;

		try {
			validaTime(timeDaCasa);
		} catch (Exception e) {
			throw new TimeNaoEncontradoException();
		}
		try {
			validaTime(timeDeFora);
		} catch (Exception e) {
			throw new TimeNaoEncontradoException();
		}

		for (Time t:times) {
			if(t.getId().equals(timeDaCasa)){
				cmTmCasa = t.getCorUniformePrincipal();
			}else if(t.getId().equals(timeDeFora)){
				cmTmFora = t.getCorUniformePrincipal();
				cmSecFora = t.getCorUniformeSecundario();
			}
		}

		if(cmTmCasa.equals(cmTmFora)){
			corCamisaAdversario = cmSecFora;
		} else {
			corCamisaAdversario = cmTmFora;
		}

		return corCamisaAdversario;
	}

	private void incluirTimeValidate(long idTime) throws Exception {
		if (isTimeExists(idTime)){
			throw new IdentificadorUtilizadoException("O identificador não existe");
		}
	}
	private boolean isTimeExists(Long idTime){
		Boolean existe = false;
		if (times != null && !times.isEmpty()){
			for (Time time: times){
				if(time.getId().equals(idTime)){
					existe = true;
					return existe;
				}
			}
		}
		return existe;
	}
	private void incluirJogadorValidate(long idJogador, long idTime) throws Exception {
		if (!isTimeExists(idTime)){
			throw new TimeNaoEncontradoException();
		}
		for (Jogador jogador: jogadores){
			if (jogador.getId().equals(idJogador)){
				throw new IdentificadorUtilizadoException();
			}
		}
	}
	private void validaJogador(Long idJogador) throws  Exception{
		if(!isJogadorExists(idJogador)){
			throw new JogadorNaoEncontradoException();
		}
	}
	private boolean isJogadorExists(Long idJogador){
		Boolean existe = false;
		if (jogadores != null && !jogadores.isEmpty()){
			for (Jogador j: jogadores){
				if(j.getId().equals(idJogador)){
					existe = true;
					return existe;
				}
			}
		}
		return existe;
	}

	private void validaTime(Long idTime)throws Exception {
		if (!isTimeExists(idTime)){
			throw new TimeNaoEncontradoException();
		}
	}
	private void validaCapitao(Long idTime) throws Exception{
		if (!isCapitaoExists(idTime)){
			throw new CapitaoNaoInformadoException();
		}
	}
	private boolean isCapitaoExists(Long idTime){
		Boolean existe = false;
		if (capitaesTimes != null && !capitaesTimes.isEmpty()){
			for (CapitaesTimes ctime: capitaesTimes){
				if(ctime.getIdTime().equals(idTime)){
					existe = true;
					return existe;
				}
			}
		}
		return existe;
	}
}