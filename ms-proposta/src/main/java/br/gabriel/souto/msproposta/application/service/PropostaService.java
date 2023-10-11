package br.gabriel.souto.msproposta.application.service;

import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import br.gabriel.souto.msproposta.application.interfaces.IPropostaService;
import br.gabriel.souto.msproposta.domain.interfaces.IPropostaRepository;
import br.gabriel.souto.msproposta.domain.model.Proposta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return _modelMapper.map(_propostaRepository.save(proposta), PropostaDTO.class);
    }

    @Override
    public PropostaDTO buscarPropostaPorId(Long id) {
        return _modelMapper.map(_propostaRepository.findById(id).orElse(new Proposta()), PropostaDTO.class);
    }
}
