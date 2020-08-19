package com.laioffer.communitypropertymanagementsystem.service;

import com.laioffer.communitypropertymanagementsystem.dao.PostRepository;
import com.laioffer.communitypropertymanagementsystem.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postService")
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
