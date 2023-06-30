package com.collins.backend.mappers;

import com.collins.backend.dtos.CommentDto;
import com.collins.backend.entities.Comment;

public class CommentMapper {

    //convert JPA Entity into Dto
    public static CommentDto mapToCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    //convert Dto into JPA Entity
    public static Comment mapToComment(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
