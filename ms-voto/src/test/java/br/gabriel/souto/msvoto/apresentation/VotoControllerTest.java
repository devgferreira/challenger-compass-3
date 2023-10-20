package br.gabriel.souto.msvoto.apresentation;


import br.gabriel.souto.msvoto.application.service.VotoService;
import br.gabriel.souto.msvoto.domain.enums.VotoStatus;
import br.gabriel.souto.msvoto.domain.model.voto.Voto;
import br.gabriel.souto.msvoto.domain.model.voto.request.VotoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VotoController.class)
class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VotoService votoService;


    @Test
    public void testVotar() throws Exception {
        String funcionarioCpf = "12345678901";
        Long propostaId = 1L;
        VotoRequest votoRequest = new VotoRequest();
        votoRequest.setStatus(VotoStatus.APROVADO);
        doNothing().when(votoService).votar(funcionarioCpf, propostaId, votoRequest.getStatus());
        mockMvc.perform(post("/votos/funcionario/" + funcionarioCpf + "/proposta/" + propostaId)
                .content(objectMapper.writeValueAsString(votoRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$").value("Voto feito com sucesso"));

    }
}