package com.collins.backend.services;

import com.collins.backend.dtos.CommentDto;
import com.collins.backend.dtos.PostDto;
import com.collins.backend.entities.Comment;
import com.collins.backend.entities.Post;
import com.collins.backend.exceptions.BlogAPIException;
import com.collins.backend.exceptions.ResourceNotFoundException;
import com.collins.backend.mappers.CommentMapper;
import com.collins.backend.mappers.PostMapper;
import com.collins.backend.repositories.CommentRepository;
import com.collins.backend.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.collins.backend.mappers.CommentMapper.mapToCommentDto;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentDto createComment(CommentDto commentDto,long postId){

        //retrieving post entity by its id
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        //setting post to comment entity
//        Comment comment = CommentMapper.mapToComment(commentDto);
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        //comment entity saving on db
        Comment newComment = commentRepository.save(comment);
        return modelMapper.map(newComment, CommentDto.class);
    }

    public List<CommentDto> getCommentsByPostId(Long postId){
        //retrieving comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        //converting list of comments  entities into list of comment dto's
//        return comments.stream().map(CommentMapper::mapToCommentDto).collect(Collectors.toList());
        return comments.stream().map((comment) -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    public CommentDto getCommentById(Long postId, Long commentId){
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        //retrieving comment by its id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to this Post");
        }

//        return mapToCommentDto(comment);
        return modelMapper.map(comment, CommentDto.class);
    }

    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto){
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        //retrieving comment by its id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to this Post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
//        return CommentMapper.mapToCommentDto(updatedComment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    public void deleteComment(Long postId, Long commentId){
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        //retrieving comment by its id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to this Post");
        }

        commentRepository.delete(comment);
    }
}
