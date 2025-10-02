// SOAP Endpoint for CRUD operations
// Requirement: The CRUD must be exposed as a SOAP web service

package com.example.crudapp.soap;

import com.example.crudapp.model.Student;
import com.example.crudapp.model.Professor;
import com.example.crudapp.model.Address;
import com.example.crudapp.service.StudentService;
import com.example.crudapp.service.ProfessorService;
import com.example.crudapp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

// This is a simplified SOAP endpoint for demonstration purposes
@Endpoint
public class CrudEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/crudapp/soap";

    @Autowired
    private StudentService studentService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private AddressService addressService;

    // ...existing code...

    // Get all students
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllStudentsRequest")
    @ResponsePayload
    public GetAllStudentsResponse getAllStudents(@RequestPayload GetAllStudentsRequest request) {
        GetAllStudentsResponse response = new GetAllStudentsResponse();
        java.util.List<StudentSoapDTO> dtos = new java.util.ArrayList<>();
        for (Student s : studentService.getAllStudents()) {
            StudentSoapDTO dto = new StudentSoapDTO();
            dto.setId(s.getId());
            dto.setFirstName(s.getFirstName());
            dto.setLastName(s.getLastName());
            dto.setEmail(s.getEmail());
            dto.setPhone(s.getPhone());
            dto.setStudentNumber(s.getStudentNumber());
            dto.setAverageMark(s.getAverageMark());
            if (s.getAddress() != null) {
                AddressSoapDTO a = new AddressSoapDTO();
                a.setId(s.getAddress().getId());
                a.setStreet(s.getAddress().getStreet());
                a.setCity(s.getAddress().getCity());
                a.setState(s.getAddress().getState());
                a.setPostalCode(s.getAddress().getPostalCode());
                a.setCountry(s.getAddress().getCountry());
                dto.setAddress(a);
            }
            dtos.add(dto);
        }
        response.setStudents(dtos);
        return response;
    }

    // Get all professors
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllProfessorsRequest")
    @ResponsePayload
    public GetAllProfessorsResponse getAllProfessors(@RequestPayload GetAllProfessorsRequest request) {
        GetAllProfessorsResponse response = new GetAllProfessorsResponse();
        java.util.List<ProfessorSoapDTO> dtos = new java.util.ArrayList<>();
        for (Professor p : professorService.getAllProfessors()) {
            ProfessorSoapDTO dto = new ProfessorSoapDTO();
            dto.setId(p.getId());
            dto.setFirstName(p.getFirstName());
            dto.setLastName(p.getLastName());
            dto.setEmail(p.getEmail());
            dto.setPhone(p.getPhone());
            // Assume there's an employeeNumber; if not, leave null
            // Map salary
            dto.setSalary(p.getSalary());
            if (p.getAddress() != null) {
                AddressSoapDTO a = new AddressSoapDTO();
                a.setId(p.getAddress().getId());
                a.setStreet(p.getAddress().getStreet());
                a.setCity(p.getAddress().getCity());
                a.setState(p.getAddress().getState());
                a.setPostalCode(p.getAddress().getPostalCode());
                a.setCountry(p.getAddress().getCountry());
                dto.setAddress(a);
            }
            dtos.add(dto);
        }
        response.setProfessors(dtos);
        return response;
    }

    // Get student by id
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStudentByIdRequest")
    @ResponsePayload
    public GetStudentByIdResponse getStudentById(@RequestPayload GetStudentByIdRequest request) {
        GetStudentByIdResponse response = new GetStudentByIdResponse();
        java.util.Optional<Student> sOpt = studentService.getStudentById(request.getId());
        if (sOpt.isPresent()) {
            Student s = sOpt.get();
            StudentSoapDTO dto = new StudentSoapDTO();
            dto.setId(s.getId());
            dto.setFirstName(s.getFirstName());
            dto.setLastName(s.getLastName());
            dto.setEmail(s.getEmail());
            dto.setPhone(s.getPhone());
            dto.setStudentNumber(s.getStudentNumber());
            dto.setAverageMark(s.getAverageMark());
            if (s.getAddress() != null) {
                AddressSoapDTO a = new AddressSoapDTO();
                a.setId(s.getAddress().getId());
                a.setStreet(s.getAddress().getStreet());
                a.setCity(s.getAddress().getCity());
                a.setState(s.getAddress().getState());
                a.setPostalCode(s.getAddress().getPostalCode());
                a.setCountry(s.getAddress().getCountry());
                dto.setAddress(a);
            }
            response.setStudent(dto);
        }
        return response;
    }

    // Get professor by id
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetProfessorByIdRequest")
    @ResponsePayload
    public GetProfessorByIdResponse getProfessorById(@RequestPayload GetProfessorByIdRequest request) {
        GetProfessorByIdResponse response = new GetProfessorByIdResponse();
        java.util.Optional<Professor> pOpt = professorService.getProfessorById(request.getId());
        if (pOpt.isPresent()) {
            Professor p = pOpt.get();
            ProfessorSoapDTO dto = new ProfessorSoapDTO();
            dto.setId(p.getId());
            dto.setFirstName(p.getFirstName());
            dto.setLastName(p.getLastName());
            dto.setEmail(p.getEmail());
            dto.setPhone(p.getPhone());
            dto.setSalary(p.getSalary());
            if (p.getAddress() != null) {
                AddressSoapDTO a = new AddressSoapDTO();
                a.setId(p.getAddress().getId());
                a.setStreet(p.getAddress().getStreet());
                a.setCity(p.getAddress().getCity());
                a.setState(p.getAddress().getState());
                a.setPostalCode(p.getAddress().getPostalCode());
                a.setCountry(p.getAddress().getCountry());
                dto.setAddress(a);
            }
            response.setProfessor(dto);
        }
        return response;
    }

    // Create student
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateStudentRequest")
    @ResponsePayload
    public CreateStudentResponse createStudent(@RequestPayload CreateStudentRequest request) {
        CreateStudentResponse response = new CreateStudentResponse();
        Student s = new Student();
        StudentSoapDTO dto = request.getStudent();
        if (dto != null) {
            s.setFirstName(dto.getFirstName());
            s.setLastName(dto.getLastName());
            s.setEmail(dto.getEmail());
            s.setPhone(dto.getPhone());
            s.setStudentNumber(dto.getStudentNumber());
            s.setAverageMark(dto.getAverageMark());
            if (dto.getAddress() != null) {
                Address a = new Address();
                a.setId(dto.getAddress().getId());
                a.setStreet(dto.getAddress().getStreet());
                a.setCity(dto.getAddress().getCity());
                a.setState(dto.getAddress().getState());
                a.setPostalCode(dto.getAddress().getPostalCode());
                a.setCountry(dto.getAddress().getCountry());
                s.setAddress(a);
            }
        }
        Student saved = studentService.createStudent(s);
        StudentSoapDTO out = new StudentSoapDTO();
        out.setId(saved.getId());
        out.setFirstName(saved.getFirstName());
        out.setLastName(saved.getLastName());
        out.setEmail(saved.getEmail());
        out.setPhone(saved.getPhone());
        out.setStudentNumber(saved.getStudentNumber());
        out.setAverageMark(saved.getAverageMark());
        if (saved.getAddress() != null) {
            AddressSoapDTO as = new AddressSoapDTO();
            as.setId(saved.getAddress().getId());
            as.setStreet(saved.getAddress().getStreet());
            as.setCity(saved.getAddress().getCity());
            as.setState(saved.getAddress().getState());
            as.setPostalCode(saved.getAddress().getPostalCode());
            as.setCountry(saved.getAddress().getCountry());
            out.setAddress(as);
        }
        response.setStudent(out);
        return response;
    }

    // Update student
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateStudentRequest")
    @ResponsePayload
    public UpdateStudentResponse updateStudent(@RequestPayload UpdateStudentRequest request) {
        UpdateStudentResponse response = new UpdateStudentResponse();
        Student in = new Student();
        StudentSoapDTO dto = request.getStudent();
        if (dto != null) {
            in.setFirstName(dto.getFirstName());
            in.setLastName(dto.getLastName());
            in.setEmail(dto.getEmail());
            in.setPhone(dto.getPhone());
            in.setStudentNumber(dto.getStudentNumber());
            in.setAverageMark(dto.getAverageMark());
            if (dto.getAddress() != null) {
                Address a = new Address();
                a.setId(dto.getAddress().getId());
                a.setStreet(dto.getAddress().getStreet());
                a.setCity(dto.getAddress().getCity());
                a.setState(dto.getAddress().getState());
                a.setPostalCode(dto.getAddress().getPostalCode());
                a.setCountry(dto.getAddress().getCountry());
                in.setAddress(a);
            }
        }
        Student saved = studentService.updateStudent(request.getId(), in);
        if (saved != null) {
            StudentSoapDTO out = new StudentSoapDTO();
            out.setId(saved.getId());
            out.setFirstName(saved.getFirstName());
            out.setLastName(saved.getLastName());
            out.setEmail(saved.getEmail());
            out.setPhone(saved.getPhone());
            out.setStudentNumber(saved.getStudentNumber());
            out.setAverageMark(saved.getAverageMark());
            if (saved.getAddress() != null) {
                AddressSoapDTO as = new AddressSoapDTO();
                as.setId(saved.getAddress().getId());
                as.setStreet(saved.getAddress().getStreet());
                as.setCity(saved.getAddress().getCity());
                as.setState(saved.getAddress().getState());
                as.setPostalCode(saved.getAddress().getPostalCode());
                as.setCountry(saved.getAddress().getCountry());
                out.setAddress(as);
            }
            response.setStudent(out);
        }
        return response;
    }

    // Delete student
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteStudentRequest")
    @ResponsePayload
    public DeleteStudentResponse deleteStudent(@RequestPayload DeleteStudentRequest request) {
        DeleteStudentResponse response = new DeleteStudentResponse();
        response.setSuccess(studentService.deleteStudent(request.getId()));
        return response;
    }

    // Create professor
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateProfessorRequest")
    @ResponsePayload
    public CreateProfessorResponse createProfessor(@RequestPayload CreateProfessorRequest request) {
        CreateProfessorResponse response = new CreateProfessorResponse();
        Professor p = new Professor();
        ProfessorSoapDTO dto = request.getProfessor();
        if (dto != null) {
            p.setFirstName(dto.getFirstName());
            p.setLastName(dto.getLastName());
            p.setEmail(dto.getEmail());
            p.setPhone(dto.getPhone());
            p.setSalary(dto.getSalary());
            if (dto.getAddress() != null) {
                Address a = new Address();
                a.setId(dto.getAddress().getId());
                a.setStreet(dto.getAddress().getStreet());
                a.setCity(dto.getAddress().getCity());
                a.setState(dto.getAddress().getState());
                a.setPostalCode(dto.getAddress().getPostalCode());
                a.setCountry(dto.getAddress().getCountry());
                p.setAddress(a);
            }
        }
        Professor saved = professorService.createProfessor(p);
        ProfessorSoapDTO out = new ProfessorSoapDTO();
        out.setId(saved.getId());
        out.setFirstName(saved.getFirstName());
        out.setLastName(saved.getLastName());
        out.setEmail(saved.getEmail());
        out.setPhone(saved.getPhone());
        out.setSalary(saved.getSalary());
        if (saved.getAddress() != null) {
            AddressSoapDTO as = new AddressSoapDTO();
            as.setId(saved.getAddress().getId());
            as.setStreet(saved.getAddress().getStreet());
            as.setCity(saved.getAddress().getCity());
            as.setState(saved.getAddress().getState());
            as.setPostalCode(saved.getAddress().getPostalCode());
            as.setCountry(saved.getAddress().getCountry());
            out.setAddress(as);
        }
        response.setProfessor(out);
        return response;
    }

    // Update professor
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateProfessorRequest")
    @ResponsePayload
    public UpdateProfessorResponse updateProfessor(@RequestPayload UpdateProfessorRequest request) {
        UpdateProfessorResponse response = new UpdateProfessorResponse();
        Professor in = new Professor();
        ProfessorSoapDTO dto = request.getProfessor();
        if (dto != null) {
            in.setFirstName(dto.getFirstName());
            in.setLastName(dto.getLastName());
            in.setEmail(dto.getEmail());
            in.setPhone(dto.getPhone());
            in.setSalary(dto.getSalary());
            if (dto.getAddress() != null) {
                Address a = new Address();
                a.setId(dto.getAddress().getId());
                a.setStreet(dto.getAddress().getStreet());
                a.setCity(dto.getAddress().getCity());
                a.setState(dto.getAddress().getState());
                a.setPostalCode(dto.getAddress().getPostalCode());
                a.setCountry(dto.getAddress().getCountry());
                in.setAddress(a);
            }
        }
        Professor saved = professorService.updateProfessor(request.getId(), in);
        if (saved != null) {
            ProfessorSoapDTO out = new ProfessorSoapDTO();
            out.setId(saved.getId());
            out.setFirstName(saved.getFirstName());
            out.setLastName(saved.getLastName());
            out.setEmail(saved.getEmail());
            out.setPhone(saved.getPhone());
            out.setSalary(saved.getSalary());
            if (saved.getAddress() != null) {
                AddressSoapDTO as = new AddressSoapDTO();
                as.setId(saved.getAddress().getId());
                as.setStreet(saved.getAddress().getStreet());
                as.setCity(saved.getAddress().getCity());
                as.setState(saved.getAddress().getState());
                as.setPostalCode(saved.getAddress().getPostalCode());
                as.setCountry(saved.getAddress().getCountry());
                out.setAddress(as);
            }
            response.setProfessor(out);
        }
        return response;
    }

    // Delete professor
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteProfessorRequest")
    @ResponsePayload
    public DeleteProfessorResponse deleteProfessor(@RequestPayload DeleteProfessorRequest request) {
        DeleteProfessorResponse response = new DeleteProfessorResponse();
        response.setSuccess(professorService.deleteProfessor(request.getId()));
        return response;
    }

    // Get all addresses
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllAddressesRequest")
    @ResponsePayload
    public GetAllAddressesResponse getAllAddresses(@RequestPayload GetAllAddressesRequest request) {
        GetAllAddressesResponse response = new GetAllAddressesResponse();
        java.util.List<AddressSoapDTO> dtos = new java.util.ArrayList<>();
        for (Address a : addressService.getAllAddresses()) {
            AddressSoapDTO dto = new AddressSoapDTO();
            dto.setId(a.getId());
            dto.setStreet(a.getStreet());
            dto.setCity(a.getCity());
            dto.setState(a.getState());
            dto.setPostalCode(a.getPostalCode());
            dto.setCountry(a.getCountry());
            dtos.add(dto);
        }
        response.setAddresses(dtos);
        return response;
    }
}
// You must also create the corresponding request/response classes and configure Spring WS in your project.
