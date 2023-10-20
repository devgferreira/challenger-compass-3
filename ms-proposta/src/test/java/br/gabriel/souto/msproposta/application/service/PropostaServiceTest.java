package br.gabriel.souto.msproposta.application.service;

import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import br.gabriel.souto.msproposta.domain.enums.Setor;
import br.gabriel.souto.msproposta.domain.interfaces.IPropostaRepository;
import br.gabriel.souto.msproposta.domain.model.Proposta;
import br.gabriel.souto.msproposta.infra.exceptions.PropostaNaoEncontradoExeception;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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
        Proposta proposta = new Proposta(1L, "Mesa quebrada", "Arrumar a messa quebrada no setor 5", Setor.RH);
        PropostaDTO propostaDTO = new PropostaDTO(1L, "Mesa quebrada", "Arrumar a messa quebrada no setor 5", Setor.RH);
        when(_modelMapper.map(propostaDTO, Proposta.class)).thenReturn(proposta);
        when(_propostaRepository.save(proposta)).thenReturn(proposta);
        when(_modelMapper.map(proposta, PropostaDTO.class)).thenReturn(propostaDTO);

        PropostaDTO result = _propostaService.criarProposta(propostaDTO);

        assertEquals(result, propostaDTO);

    }

    @Test
    void buscarPropostaPorId() {
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

    @Test
    void buscarPropostaPorId_Nao_Encontrado() {
        Long id = 1L;
        when(_propostaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PropostaNaoEncontradoExeception.class, () -> {
            _propostaService.buscarPropostaPorId(id);
        });

    }

    @Test
    public void testBuscarTodasAsPropostas() {
        List<Proposta> propostas = Arrays.asList(new Proposta(), new Proposta());

        List<PropostaDTO> propostaDTOs = propostas.stream()
                .map(proposta -> new PropostaDTO())
                .collect(Collectors.toList());

        when(_propostaRepository.findAll()).thenReturn(propostas);
        for (int i = 0; i < propostas.size(); i++) {
            when(_modelMapper.map(propostas.get(i), PropostaDTO.class)).thenReturn(propostaDTOs.get(i));
        }

        List<PropostaDTO> result = _propostaService.buscarTodasAsPropostas();

        assertEquals(propostaDTOs, result);
    }

    @Test
    public void testVerificarTempo() {
        List<Proposta> propostas = Arrays.asList(new Proposta(LocalTime.now()), new Proposta(LocalTime.now()));

        when(_propostaRepository.findAll()).thenReturn(propostas);

        _propostaService.verificarTempo();

        Mockito.verify(_propostaRepository, times(propostas.size())).save(any(Proposta.class));
    }

}