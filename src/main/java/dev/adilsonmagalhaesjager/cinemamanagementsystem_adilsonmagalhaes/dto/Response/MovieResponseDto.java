package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response;

import lombok.Builder;

@Builder
public record MovieResponseDto (
        int id,
        String title,
        int duration,
        String synopsis,
        String genre,
        int classification,
        String poster
){}
