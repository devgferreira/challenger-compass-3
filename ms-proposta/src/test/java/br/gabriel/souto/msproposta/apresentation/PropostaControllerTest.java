package br.gabriel.souto.msproposta.apresentation;

import br.gabriel.souto.msproposta.application.dtos.PropostaDTO;
import br.gabriel.souto.msproposta.application.service.PropostaService;
import br.gabriel.souto.msproposta.domain.enums.Setor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropostaController.class)
class PropostaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PropostaService propostaService;

    @Test
    void criarProposta() throws Exception {
        PropostaDTO propostaDTO = new PropostaDTO(1L, "Mesa quebrada", "Arrumar a messa quebrada no setor 5", Setor.RH);
        when(propostaService.criarProposta(propostaDTO)).thenReturn(propostaDTO);
        mockMvc.perform(post("/propostas").content(objectMapper.writeValueAsString(propostaDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(jsonPath("$").value(propostaDTO));
    }

    @Test
    void buscarPropostaPorId() throws Exception {
        PropostaDTO propostaDTO = new PropostaDTO(1L, "Mesa quebrada", "Arrumar a messa quebrada no setor 5", Setor.RH);

        when(propostaService.buscarPropostaPorId(propostaDTO.getId())).thenReturn(propostaDTO);

        mockMvc.perform(get("/propostas/" + propostaDTO.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$").value(propostaDTO));
    }

    @Test
    void buscarTodasAsPropostas() throws Exception {
        List<PropostaDTO> propostas = Arrays.asList(new PropostaDTO(), new PropostaDTO());

        when(propostaService.buscarTodasAsPropostas()).thenReturn(propostas);

        mockMvc.perform(get("/propostas")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));


    }
}