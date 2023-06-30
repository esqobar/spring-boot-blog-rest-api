package com.collins.backend.resources;

import com.collins.backend.dtos.CommentDto;
import com.collins.backend.dtos.PostDto;
import com.collins.backend.entities.Comment;
import com.collins.backend.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save comment into the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createPost(@Valid @RequestBody CommentDto commentDto,
                                                 @PathVariable(value = "postId") long postId){
        CommentDto savedComment = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Comments REST API",
            description = "Get All Comments REST API is used to fetch all comments from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Comment By Id REST API",
            description = "Get Comment By Id REST API is used to fetch a single comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
        CommentDto comment = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment REST API",
            description = "Update Comment REST API is used to update a particular comment in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable(value = "postId") Long postId
            , @PathVariable(value = "id") Long commentId){
        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment REST API",
            description = "Delete Comment REST API is used to delete a particular comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }
}
