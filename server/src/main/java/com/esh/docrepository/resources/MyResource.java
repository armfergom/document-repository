package com.esh.docrepository.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.esh.docrepository.dao.StudentDao;
import com.esh.docrepository.dao.StudentDaoImpl;
import com.esh.docrepository.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        StudentDao dao = new StudentDaoImpl();
        
        Student student = dao.findStudent("30243614V");
        if (student == null) {
            return "Not found mate";
        }
        ObjectMapper mapper = new ObjectMapper();

        String value = "";
        try {
            value = mapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            return "Error deserializing";
        }
        return value;
    }
}
