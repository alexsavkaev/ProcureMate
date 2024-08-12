package ru.aston.demo.accounting.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.aston.demo.accounting.dto.StockMovementDto;
import ru.aston.demo.accounting.mapper.StockMovementDtoMapper;
import ru.aston.demo.accounting.model.StockMovement;
import ru.aston.demo.accounting.service.StockMovementService;
import ru.aston.demo.accounting.type.MovementType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.aston.demo.accounting.TestUtils.toJson;

@SpringBootTest
@AutoConfigureMockMvc
class StockMovementControllerTest {

    private static final StockMovementDto MOVEMENT_DTO = new StockMovementDto(
            null, 10, "test", 3, BigDecimal.TEN, Instant.now(), MovementType.INCOME);

    private static final StockMovement MOVEMENT = new StockMovement
            (null, 10, "test", 3, BigDecimal.TEN, Instant.now(), MovementType.INCOME);

    private static final Instant MOVED_AT_FROM = Instant.parse("2024-07-01T00:00:00Z");
    private static final Instant MOVED_AT_TO = Instant.parse("2024-07-30T23:59:59Z");

    @MockBean
    private StockMovementService movementService;

    @MockBean
    private StockMovementDtoMapper movementDtoMapper;

    @MockBean
    private Supplier<Instant> getCurrentTime;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_movementJson_created() throws Exception {
        // given
        given(movementDtoMapper.toStockMovement(MOVEMENT_DTO)).willReturn(MOVEMENT);
        given(movementService.create(MOVEMENT)).willReturn(123L);

        // when
        ResultActions result = mockMvc.perform(post("/movements")
                .contentType(APPLICATION_JSON)
                .content(toJson(MOVEMENT_DTO)));

        // then
        result
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/movements/123"));
    }

    @Test
    void findById_thereIsNoMovement_notFount() throws Exception {
        // given
        given(movementService.findById(1L)).willReturn(Optional.empty());

        // when
        ResultActions result = mockMvc.perform(get("/movements/{id}", 1));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    void findById_thereIsMovement_ok() throws Exception {
        // given
        given(movementService.findById(1L)).willReturn(Optional.of(MOVEMENT));
        given(movementDtoMapper.toStockMovementDto(MOVEMENT)).willReturn(MOVEMENT_DTO);

        // when
        ResultActions result = mockMvc.perform(get("/movements/{id}", 1));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(MOVEMENT_DTO)));
    }

    @Test
    void findAll_thereAreNoMovements_emptyJsonArray() throws Exception {
        // given
        given(getCurrentTime.get()).willReturn(Instant.parse("2024-07-31T14:00:00Z"));
        given(movementService.findByMovedAtBetween(any(), any())).willReturn(emptyList());

        // when
        ResultActions result = mockMvc.perform(get("/movements"));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void findAll_thereAreMovements_ok() throws Exception {
        // given
        given(getCurrentTime.get()).willReturn(Instant.parse("2024-07-31T14:00:00Z"));
        given(movementService.findByMovedAtBetween(MOVED_AT_FROM, MOVED_AT_TO)).willReturn(List.of(MOVEMENT));
        given(movementDtoMapper.toStockMovementDto(MOVEMENT)).willReturn(MOVEMENT_DTO);

        // when
        ResultActions result = mockMvc.perform(get("/movements")
                .param("moved_at_from", MOVED_AT_FROM.toString())
                .param("moved_at_to", MOVED_AT_TO.toString()));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(List.of(MOVEMENT_DTO))));
    }

    @Test
    void findAll_parametersAreMissing_defaultsAreUsed() throws Exception {
        // given
        given(getCurrentTime.get()).willReturn(Instant.parse("2024-07-31T14:00:00Z"));

        // when
        mockMvc.perform(get("/movements"));

        // then
        then(movementService).should().findByMovedAtBetween(
                Instant.parse("2024-07-01T14:00:00Z"), Instant.parse("2024-07-31T14:00:00Z"));
    }

    @Test
    void delete_movementId_delegateToServiceAndNoContent() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(delete("/movements/{id}", 1));

        // then
        result.andExpect(status().isNoContent());
        then(movementService).should().delete(1);
    }
}