package com.example.crudapp.soap;

import com.example.crudapp.model.Professor;
import java.util.List;

public class GetAllProfessorsResponse {
    private List<Professor> professors;
    public List<Professor> getProfessors() { return professors; }
    public void setProfessors(List<Professor> professors) { this.professors = professors; }
}
