package com.base.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.basesetup.entity.CommentsVO;

public interface CommentsRepo extends JpaRepository<CommentsVO, Long> {

}
