package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReservationRequestDto(
        String email_user,
        @NotNull Integer id_showtime,
        @NotEmpty List<Integer> seats
        ){
}
