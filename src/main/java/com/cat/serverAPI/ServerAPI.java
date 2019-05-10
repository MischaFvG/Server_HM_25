package com.cat.serverAPI;

import com.cat.dao.MariaDBDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Path("/")
public class ServerAPI {
    private MariaDBDao mariaDBDao = new MariaDBDao();

    public ServerAPI() throws SQLException {
    }

    @GET
    @Path("/getAllStudents")
    public Response getAllStudents() {
        String allStudentsString = mariaDBDao.getAllStudents();
        return Response.status(Response.Status.OK).entity(allStudentsString).build();
    }

    @GET
    @Path("/getStudentById")
    public Response getStudentById(@QueryParam("id") int id) {
        String studentById = mariaDBDao.getStudentById(id);
        return Response.status(Response.Status.OK).entity(studentById).build();
    }

    @GET
    @Path("/getStudentByName")
    public Response getStudentByName(@QueryParam("name") String studentName) {
        String studentByName = mariaDBDao.getStudentByName(studentName);
        return Response.status(Response.Status.OK).entity(studentByName).build();
    }

    @GET
    @Path("/deleteStudentById")
    public Response deleteStudentById(@QueryParam("id") int id) {
        mariaDBDao.deleteStudentById(id);
        return Response.status(Response.Status.OK).entity("Student with id " + id + " has been deleted from your database").build();
    }

    @GET
    @Path("/updateStudent")
    public Response updateStudent(@QueryParam("id") int id, @QueryParam("age") int age, @QueryParam("name") String name, @QueryParam("birth_date") String birthDate) {
        mariaDBDao.updateStudent(id, age, name, birthDate);
        return Response.status(Response.Status.OK).entity("Student with id " + id + " has been updated").build();
    }

    @GET
    @Path("/insertStudent")
    public Response insertStudent(@QueryParam("age") int age, @QueryParam("name") String name, @QueryParam("birth_date") String birthDate) {
        mariaDBDao.insertStudent(age, name, birthDate);
        return Response.status(Response.Status.OK).entity("Student " + name + " age " + age + " birthDate " + birthDate + " has been inserted").build();
    }

    @GET
    @Path("/getAllStudentsByAge")
    public Response getAllStudentsByAge(@QueryParam("birth_date") String firstDate) {
        Timestamp secondDate = new Timestamp(System.currentTimeMillis());
        String studentsByAge = mariaDBDao.getAllStudentsByAge(firstDate, secondDate);
        return Response.status(Response.Status.OK).entity(studentsByAge).build();
    }
}
