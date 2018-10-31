package io.makana.mechwar.domain.events.movement.validation;

import io.makana.mechwar.domain.entities.Entity;
import io.makana.mechwar.domain.entities.EntityId;
import io.makana.mechwar.domain.entities.EntityRepository;
import io.makana.mechwar.domain.entities.board.Hex;
import io.makana.mechwar.domain.entities.board.terrain.Clear;
import io.makana.mechwar.domain.events.movement.MoveOrderRequest;
import io.makana.mechwar.domain.events.movement.MovePath;
import io.makana.mechwar.domain.game.GameId;
import io.makana.mechwar.domain.player.Player;
import io.makana.mechwar.domain.units.GameUnit;
import io.makana.mechwar.domain.units.GameUnitId;
import io.makana.mechwar.domain.units.GameUnitRepository;
import io.makana.mechwar.domain.units.capabilities.movement.MovementCapability;
import io.makana.mechwar.domain.units.capabilities.movement.StandStill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MoveOrderRequestValidatorTest {

    @MockBean
    private GameUnitRepository gameUnitRepository;

    @MockBean
    private EntityRepository entityRepository;

    @Autowired
    private MoveOrderRequestValidator cut;

    @Test
    public void supports_correct_class() {
        assertTrue(cut.supports(MoveOrderRequest.class));
    }

    @Test
    public void valid_target_passes() {
        GameUnitId gameUnitId = new GameUnitId();
        EntityId entityId = new EntityId();
        when(gameUnitRepository.getGameUnitById(eq(gameUnitId))).thenReturn(new GameUnit(gameUnitId, entityId));
        Set<MovementCapability> movementCapabilities = new HashSet<>();
        movementCapabilities.add(new StandStill());
        when(entityRepository.getById(eq(entityId))).thenReturn(new Entity(entityId, movementCapabilities));
        MoveOrderRequest validRequest = MoveOrderRequest.builder()
                .gameId(new GameId())
                .player(new Player("player 1"))
                .round(1)
                .unitToMove(gameUnitId)
                .addMovePath(new MovePath(new StandStill(), new Hex(0101, 0, Arrays.asList(new Clear()))))
                .build();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(validRequest, "MoveOrderRequest");
        cut.validate(validRequest, errors);
        for (ObjectError error : errors.getAllErrors()) {
            System.out.println(error.getDefaultMessage());
        }
        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void missing_gameId_fails() {
        MoveOrderRequest request = MoveOrderRequest.builder()
                .player(new Player("player 1"))
                .round(1)
                .unitToMove(new GameUnitId())
                .addMovePath(new MovePath(new StandStill(), new Hex(0101, 0, Arrays.asList(new Clear()))))
                .build();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(request, "MoveOrderRequest");
        cut.validate(request, errors);
        for (ObjectError error : errors.getAllErrors()) {
            System.out.println(error.getDefaultMessage());
        }
        assertEquals(2, errors.getErrorCount());
        assertEquals(1, errors.getFieldErrorCount("gameId"));
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public MoveOrderRequestValidator moveOrderRequestValidator(GameUnitRepository gameUnitRepository, EntityRepository entityRepository) {
            return new MoveOrderRequestValidator(gameUnitRepository, entityRepository);
        }
    }
}