package com.ase.test.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "correct", nullable = false)
    private boolean correct;

    @Column(name = "equation", nullable = true)
    private String equation;

    @Column(name = "max_moves", nullable = true)
    private int max_moves;

    @OneToMany(mappedBy = "request_id")
    private Set<Solution> solutions = new HashSet<>();

    public Request() {}

    public Request(boolean correct, String equation, int max_moves) {
        this.correct = correct;
        this.equation = equation;
        this.max_moves = max_moves;
    }

    public void addSolution(Solution newSolution) {
        this.solutions.add(newSolution);
        if (newSolution.getRequest_id() != this){
            newSolution.setRequest_id(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public int getMax_moves() {
        return max_moves;
    }

    public void setMax_moves(int max_moves) {
        this.max_moves = max_moves;
    }

    public Set<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(Set<Solution> solutions) {
        this.solutions = solutions;
    }
}
