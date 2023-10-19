package br.gabriel.souto.msvoto.infra.constants;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class ErrorConstants {
    public static final String FUNCIONARIO_NAO_ENCONTRADO = "[MS-VOTO] Funcionario não encontrad.";
    public static final String PROPOSTA_NAO_ENCONTRADA = "[MS-VOTO] Proposta não encontrada.";
    public static final String NAO_PODE_VOTAR = "[MS-VOTO] O Funcionario não tem permissão para votar nessa proposta.";
    public static final String VOTACAO_ENCERRADA = "[MS-VOTO] A votação ja foi encerrada.";
    public static final String FUNCIONARIO_JA_VOTO = "[MS-VOTO] O funcionario ja votou nessa proposta.";
    public static final String VOTO_INVALIDO = "[MS-VOTO] O voto não pode ser inválido.";
}
