
package demo.gestion.etudiant.mp;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import demo.gestion.etudiant.mp.models.Student;
import demo.gestion.etudiant.mp.models.Marks;
import demo.gestion.etudiant.mp.services.StudentService;


@Path("/students")
@RequestScoped
public class StudentResource {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    /**
     * The sutdentProvider message provider.
     */
    private final StudentProvider sutdentProvider;
    
    private StudentService studentService;
    

    /**
     * Using constructor injection to get a configuration property.
     * By default this gets the value from META-INF/microprofile-config
     *
     * @param sutdentProvider the configured greeting message
     */
    @Inject
    public StudentResource(StudentProvider sutdentProvider, StudentService studentService) {
        this.sutdentProvider = sutdentProvider;
        this.studentService=studentService;
    }

    /**
     * Return List of students
     *
     * @return {@link JsonObject}
     */
    @SuppressWarnings("checkstyle:designforextension")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDefaultMessage() {
    	List<Student> list=studentService.getEtudiantsList();
    	
        return createResponse(list);
    }

    /**
     * Return a specific student
     *
     * @param name the name to greet
     * @return {@link JsonObject}
     */
    @SuppressWarnings("checkstyle:designforextension")
    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage(@PathParam("name") String name) {
    	
    	Optional<Student> opt=studentService.getEtudiant(name);
    	if (opt.isPresent()) {
    		//Get notes 
    		Student etudiant=opt.get();
    		try {
    			// Only one time
    			Unirest.setObjectMapper(new ObjectMapper() {
    			    private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
    			                = new com.fasterxml.jackson.databind.ObjectMapper();

    			    public <T> T readValue(String value, Class<T> valueType) {
    			        try {
    			            return jacksonObjectMapper.readValue(value, valueType);
    			        } catch (IOException e) {
    			            throw new RuntimeException(e);
    			        }
    			    }

    			    public String writeValue(Object value) {
    			        try {
    			            return jacksonObjectMapper.writeValueAsString(value);
    			        } catch (JsonProcessingException e) {
    			            throw new RuntimeException(e);
    			        }
    			    }
    			});
    			HttpResponse<List> rep = Unirest.get("http://localhost:9090/notes/"+etudiant.getId())
    				    .asObject(List.class);
				
    			etudiant.setNotes((List<Marks>)rep.getBody());
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return createResponse(etudiant);
    	}
    		
        return createResponse(" not found!");
    }

    /**
     * Set the greeting to use in future messages.
     *
     * @param jsonObject JSON containing the new greeting
     * @return {@link Response}
     */
    @SuppressWarnings("checkstyle:designforextension")
    @Path("/greeting")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGreeting(JsonObject jsonObject) {

        if (!jsonObject.containsKey("greeting")) {
            JsonObject entity = JSON.createObjectBuilder()
                    .add("error", "No greeting provided")
                    .build();
            return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
        }

        String newGreeting = jsonObject.getString("greeting");

        sutdentProvider.setMessage(newGreeting);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private String createResponse(Object etudiants) {
        Gson  gson =new Gson();
        
       return  gson.toJson(etudiants);
        
    }
}
