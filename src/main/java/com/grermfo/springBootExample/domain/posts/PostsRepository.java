package com.grermfo.springBootExample.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query(value="select * from posts p  order by p.id desc", nativeQuery = true)
    List<Posts> findAllDesc();
}
