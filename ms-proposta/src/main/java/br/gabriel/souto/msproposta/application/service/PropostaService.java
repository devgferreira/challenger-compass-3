package br.gabriel.souto.msproposta.application.service;

import br.gabriel.souto.msproposta.infra.constants.ErrorConstants;
import br.gabriel.souto.msproposta.domain.enums.ErrorCodes;
import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import br.gabriel.souto.msproposta.application.interfaces.IPropostaService;
import br.gabriel.souto.msproposta.domain.interfaces.IPropostaRepository;
import br.gabriel.souto.msproposta.domain.model.Proposta;
import br.gabriel.souto.msproposta.infra.exceptions.ExceptionResponse;
import br.gabriel.souto.msproposta.infra.exceptions.PropostaNaoEncontradoExeception;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class PropostaService implements IPropostaService {

    private final IPropostaRepository _propostaRepository;
    private final ModelMapper _modelMapper;
    @Autowired
    public PropostaService(IPropostaRepository propostaRepository, ModelMapper modelMapper) {
        _propostaRepository = propostaRepository;
        _modelMapper = modelMapper;
    }

    @Override
    public PropostaDTO criarProposta(PropostaDTO propostaDTO) {
        Proposta proposta = _modelMapper.map(propostaDTO, Proposta.class);
        if (proposta.getTempo() == null) {
            proposta.setTempo(LocalTime.of(0, 1));
        }
        proposta.setAberta(true);
        return _modelMapper.map(_propostaRepository.save(proposta), PropostaDTO.class);
    }

    @Override
    public PropostaDTO buscarPropostaPorId(Long id) {
        Proposta proposta = _propostaRepository.findById(id).orElseThrow(
                () -> new PropostaNaoEncontradoExeception(
                        new ExceptionResponse(ErrorCodes.PROPOSTA_NAO_ENCONTRADA,
                                ErrorConstants.PROPOSTA_NAO_ENCONTRADA)));

        return _modelMapper.map(proposta, PropostaDTO.class);
    }
    @Override
    @Scheduled(fixedRate = 60000)
    public void verificarPropostas() {
        LocalTime agora = LocalTime.now();
        _propostaRepository.findAll().forEach(proposta -> {
            if (proposta.getTempo().isBefore(agora) && proposta.isAberta()) {
                proposta.setAberta(false);
                _propostaRepository.save(proposta);
            }
        });
    }
}
