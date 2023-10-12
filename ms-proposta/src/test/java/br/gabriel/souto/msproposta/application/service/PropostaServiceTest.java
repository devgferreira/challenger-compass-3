package br.gabriel.souto.msproposta.application.service;

import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import br.gabriel.souto.msproposta.domain.interfaces.IPropostaRepository;
import br.gabriel.souto.msproposta.domain.model.Proposta;
import br.gabriel.souto.msproposta.infra.exceptions.PropostaNaoEncontradoExeception;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PropostaServiceTest {
    @InjectMocks
    private PropostaService _propostaService;

    @Mock
    private IPropostaRepository _propostaRepository;

    @Mock
    private ModelMapper _modelMapper;

    @Test
    void criarProposta() {
        Proposta proposta = new Proposta(1L, "Mesa quebrada", "Arrumar a messa quebrada no setor 5");
        PropostaDTO propostaDTO = new PropostaDTO(1L, "Mesa quebrada", "Arrumar a messa quebrada no setor 5");
        when(_modelMapper.map(propostaDTO, Proposta.class)).thenReturn(proposta);
        when(_propostaRepository.save(proposta)).thenReturn(proposta);
        when(_modelMapper.map(proposta, PropostaDTO.class)).thenReturn(propostaDTO);

        PropostaDTO result = _propostaService.criarProposta(propostaDTO);

        assertEquals(result, propostaDTO);

    }

    @Test
    void buscarPropostaPorId(){
        Long id = 1L;
        Proposta proposta = new Proposta();
        proposta.setId(id);
        PropostaDTO propostaDTO = new PropostaDTO();
        propostaDTO.setId(id);

        when(_propostaRepository.findById(proposta.getId())).thenReturn(Optional.of(proposta));
        when(_modelMapper.map(proposta, PropostaDTO.class)).thenReturn(propostaDTO);

        PropostaDTO result = _propostaService.buscarPropostaPorId(id);
        assertEquals(result, propostaDTO);
    }



}