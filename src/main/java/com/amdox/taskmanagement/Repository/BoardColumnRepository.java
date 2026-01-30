package com.amdox.taskmanagement.Repository;

import com.amdox.taskmanagement.Entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    List<BoardColumn> findByBoardIdOrderByPosition(Long boardId); // Fixed logic: return BoardColumn not Board. Fixed camelCase.
}
