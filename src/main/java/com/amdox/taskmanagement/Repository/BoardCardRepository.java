package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Entity.BoardCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardCardRepository extends JpaRepository<BoardCard, Long> {
    List<BoardCard> findByBoardIdAndColumnIdOrderByPosition(Long boardId, Long columnId); // Fixed camelCase
    long countByBoardIdAndColumnId(Long boardId, Long columnId);
    Optional<BoardCard> findByIssueId(Long issueId);
}
