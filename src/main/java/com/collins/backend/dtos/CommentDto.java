package com.collins.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "CommentDto Model Information"
)
public class CommentDto {
    private Long id;
    @Schema(
            description = "Blog Comment Name"
    )
    @NotEmpty(message = "Comment name should not be empty")
    private String name;
    @Schema(
            description = "Blog Comment Email"
    )
    @NotEmpty(message = "Comment email should not be empty")
    @Email(message = "Comment email should be valid")
    private String email;
    @Schema(
            description = "Blog Comment Body"
    )
    @NotEmpty(message = "Comment body should not be empty")
    @Size(min= 10, message = "Comment body should have atleast 10 characters")
    private String body;
}
