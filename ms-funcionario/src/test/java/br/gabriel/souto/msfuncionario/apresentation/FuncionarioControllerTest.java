package br.gabriel.souto.msfuncionario.apresentation;

import br.gabriel.souto.msfuncionario.application.dtos.FuncionarioDTO;
import br.gabriel.souto.msfuncionario.application.service.FuncionarioService;
import br.gabriel.souto.msfuncionario.domain.enums.Setor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(FuncionarioController.class)
class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FuncionarioService funcionarioService;
    @Test
    void criarFuncionario() throws Exception {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(1L, "12345678901", "Gabriel", Setor.RH);
        when(funcionarioService.criarFuncionario(funcionarioDTO)).thenReturn(funcionarioDTO);
        mockMvc.perform(post("/funcionarios").content(objectMapper.writeValueAsString(funcionarioDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(jsonPath("$").value(funcionarioDTO));
    }

    @Test
    void buscarFuncionarioPorCpf() throws Exception {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(1L, "12345678901", "Gabriel", Setor.RH);
        when(funcionarioService.buscarFuncionarioPorCpf(funcionarioDTO.getCpf())).thenReturn(funcionarioDTO);

        mockMvc.perform(get("/funcionarios/" + funcionarioDTO.getCpf())).andExpect(status().isOk())
                .andExpect(jsonPath("$").value(funcionarioDTO));
    }
}