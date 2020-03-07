package io.makana.mechwar.domain.players;

import io.makana.mechwar.domain.entities.Team;
import lombok.Value;

@Value
public class Player {

    private String name;
    private Team team;

}
