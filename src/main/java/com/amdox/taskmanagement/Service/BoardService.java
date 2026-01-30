package com.amdox.taskmanagement.Service;

import com.amdox.taskmanagement.Entity.Board;
import com.amdox.taskmanagement.Entity.BoardCard;
import com.amdox.taskmanagement.Entity.BoardColumn;
import com.amdox.taskmanagement.Repository.BoardCardRepository;
import com.amdox.taskmanagement.Repository.BoardColumnRepository;
import com.amdox.taskmanagement.Repository.BoardRepository;
import com.amdox.taskmanagement.Repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private BoardColumnRepository boardColumnRepo;

    @Autowired
    private BoardCardRepository boardCardRepo;
    
    @Autowired
    private IssueRepository issueRepo;

    public Board createBoard(Board board) {
        return boardRepo.save(board);
    }

    public List<Board> getAllBoards() {
        return boardRepo.findAll();
    }
    
    public Board getBoardById(Long id) {
        return boardRepo.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));
    }

    public BoardColumn addColumn(Long boardId, BoardColumn column) {
        Board board = getBoardById(boardId);
        column.setBoard(board);
        return boardColumnRepo.save(column);
    }

    @Transactional
    public BoardCard addCard(Long boardId, Long columnId, Long issueId) {
        BoardColumn column = boardColumnRepo.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
        
        long count = boardCardRepo.countByBoardIdAndColumnId(boardId, columnId);
        if (column.getWipLimit() != null && count >= column.getWipLimit()) {
            throw new RuntimeException("WIP Limit reached for column: " + column.getName());
        }

        BoardCard card = new BoardCard();
        card.setBoardId(boardId);
        card.setColumn(column); // Set relation
        card.setIssueId(issueId);
        card.setPosition((int) count + 1); // Append to end

        return boardCardRepo.save(card);
    }

    @Transactional
    public void moveCard(Long cardId, Long targetColumnId, Integer newPosition) {
        BoardCard card = boardCardRepo.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        
        BoardColumn targetColumn = boardColumnRepo.findById(targetColumnId)
                .orElseThrow(() -> new RuntimeException("Target Column not found"));

        // Check WIP limit if moving to a different column
        if (!card.getColumn().getId().equals(targetColumnId)) {
             long count = boardCardRepo.countByBoardIdAndColumnId(card.getBoardId(), targetColumnId);
             if (targetColumn.getWipLimit() != null && count >= targetColumn.getWipLimit()) {
                 throw new RuntimeException("WIP Limit reached for target column");
             }
        }

        card.setColumn(targetColumn);
        card.setPosition(newPosition);
        boardCardRepo.save(card);
        
        // Note: Ideally we should re-position other cards, but keeping simple for now as per requirement
    }
}
