package fr.pantheonsorbonne.ufr27.miage.dto;

public record OrderDTO(

        long id,
        String status,
        MenuDTO menu
) {
}
