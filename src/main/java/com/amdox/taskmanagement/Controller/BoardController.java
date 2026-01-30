package com.amdox.taskmanagement.Controller;

import com.amdox.taskmanagement.Entity.Board;
import com.amdox.taskmanagement.Entity.BoardCard;
import com.amdox.taskmanagement.Entity.BoardColumn;
import com.amdox.taskmanagement.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) {
        return ResponseEntity.ok(boardService.createBoard(board));
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoardById(id));
    }

    @PostMapping("/{boardId}/columns")
    public ResponseEntity<BoardColumn> addColumn(@PathVariable Long boardId, @RequestBody BoardColumn column) {
        return ResponseEntity.ok(boardService.addColumn(boardId, column));
    }

    @PostMapping("/{boardId}/cards")
    public ResponseEntity<BoardCard> addCard(@PathVariable Long boardId, 
                                             @RequestParam Long columnId, 
                                             @RequestParam Long issueId) {
        return ResponseEntity.ok(boardService.addCard(boardId, columnId, issueId));
    }

    @PutMapping("/cards/{cardId}/move")
    public ResponseEntity<Void> moveCard(@PathVariable Long cardId,
                                         @RequestParam Long targetColumnId,
                                         @RequestParam Integer newPosition) {
        boardService.moveCard(cardId, targetColumnId, newPosition);
        return ResponseEntity.ok().build();
    }
}
