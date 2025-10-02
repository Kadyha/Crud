package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeleteStudentResponse", namespace = "http://example.com/crudapp/soap")
public class DeleteStudentResponse {
    private boolean success;
    @XmlElement(name = "success")
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}
