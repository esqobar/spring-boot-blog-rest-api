package com.collins.backend.mappers;

import com.collins.backend.dtos.PostDto;
import com.collins.backend.entities.Post;

public class PostMapper {

    //convert JPA Entity into Dto
    public static PostDto mapToPostDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    //convert Dto into JPA Entity
    public static Post mapToPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
