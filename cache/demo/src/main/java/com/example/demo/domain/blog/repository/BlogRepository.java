package com.example.demo.domain.blog.repository;

import com.example.demo.domain.account.Account;
import com.example.demo.domain.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}
