package com.ase.test.model;

import javax.persistence.*;

@Entity
@Table(name = "solution")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_provided", nullable = false)
    private boolean user_provided;

    @Column(name = "equation", nullable = true)
    private String equation;

    @Column(name = "moves", nullable = true)
    private int moves;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = true)
    private Request request_id;

    public Solution() {}

    public Solution(boolean user_provided, String equation, int moves) {
        this.user_provided = user_provided;
        this.equation = equation;
        this.moves = moves;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isUser_provided() {
        return user_provided;
    }

    public void setUser_provided(boolean user_provided) {
        this.user_provided = user_provided;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public Request getRequest_id() {
        return request_id;
    }

    public void setRequest_id(Request request_id) {
        this.request_id = request_id;
        if (!request_id.getSolutions().contains(this)) {
            request_id.addSolution(this);
        }
    }
}
