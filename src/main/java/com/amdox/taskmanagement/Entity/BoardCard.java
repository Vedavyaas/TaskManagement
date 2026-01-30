package com.amdox.taskmanagement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="board_cards", indexes = {@Index(columnList = "board_id,column_id,position")})
public class BoardCard {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="board_id")
    private Long boardId;

    private Long issueId;
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="column_id")
    private BoardColumn column;

    public BoardCard() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public BoardColumn getColumn() {
        return column;
    }

    public void setColumn(BoardColumn column) {
        this.column = column;
    }
}
