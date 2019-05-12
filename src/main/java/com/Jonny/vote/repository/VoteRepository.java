package com.Jonny.vote.repository;

import com.Jonny.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=:date")
    Optional<Vote> getForUserAndDate(@Param("userId") int userId, @Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    List<Vote> getAllByDate(LocalDate localDate);

    Vote save(Vote vote);

    @Override
    void deleteById(Integer integer);
}
