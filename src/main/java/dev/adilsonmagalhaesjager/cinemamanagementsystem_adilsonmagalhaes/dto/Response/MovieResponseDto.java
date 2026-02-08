package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response;

import lombok.Builder;

@Builder
public record MovieResponseDto (
        int id,
        String title,
        int time
){}
