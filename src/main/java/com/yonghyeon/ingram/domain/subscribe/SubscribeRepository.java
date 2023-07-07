package com.yonghyeon.ingram.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long>, SubscribeRepositoryCustom {
}
