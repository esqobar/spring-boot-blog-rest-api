package com.collins.backend.services;

import com.collins.backend.dtos.PostDto;
import com.collins.backend.dtos.PostResponse;
import com.collins.backend.entities.Category;
import com.collins.backend.entities.Post;
import com.collins.backend.exceptions.ResourceNotFoundException;
import com.collins.backend.exceptions.TitleAlreadyExistsException;
import com.collins.backend.repositories.CategoryRepository;
import com.collins.backend.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public PostDto createPost(PostDto postDto){
        //check if post already exists
        Optional<Post> optionalPost = postRepository.findByTitle(postDto.getTitle());
        if(optionalPost.isPresent()){
            throw new TitleAlreadyExistsException("This Title Already Exists!");
        }

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        //converting Dto to entity
//        Post post = PostMapper.mapToPost(postDto);
        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        //converting entity to dto
//        PostDto postResponse = PostMapper.mapToPostDto(newPost);
        PostDto postResponse = modelMapper.map(newPost, PostDto.class);
        return postResponse;
//        return modelMapper.map(newPost, PostDto.class);
    }

    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        //create pageable instance
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        //getting content for page content
        List<Post> listOfPosts = posts.getContent();

//        List<PostDto> content =  listOfPosts.stream().map(PostMapper::mapToPostDto).collect(Collectors.toList());
        List<PostDto> content =  listOfPosts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

//    public List<PostDto> getAllPosts(){
//        List<Post> posts = postRepository.findAll();
//        return posts.stream().map(PostMapper::mapToPostDto).collect(Collectors.toList());
//
//          return users.stream().map((user) -> modelMapper.map(user, UserDto.class))
//                .collect(Collectors.toList());
//    }

    public PostDto getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto updatePost(PostDto postDto, Long id){
        Optional<Post> optionalPost = postRepository.findByTitle(postDto.getTitle());
        if(optionalPost.isPresent()){
            throw new TitleAlreadyExistsException("This Title Already Exists!");
        }
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)
        );

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        existingPost.setTitle(postDto.getTitle());
        existingPost.setDescription(postDto.getDescription());
        existingPost.setContent(postDto.getContent());
        existingPost.setCategory(category);
        Post updatedPost = postRepository.save(existingPost);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    public void deletePost(Long id){
        Post ExistingPost = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );

        postRepository.deleteById(id);
    }

    public List<PostDto> getPostsByCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

          return posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

}
