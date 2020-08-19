package com.laioffer.communitypropertymanagementsystem.dao;

import com.laioffer.communitypropertymanagementsystem.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
