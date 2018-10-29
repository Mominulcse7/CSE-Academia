package com.cseacademia.mominulcse1213.cseacademia;

/**
 * Created by Mominul on 10/28/2018.
 */

public class TeacherConstructor {
    private String teacherId;
    private String teacherName;
    private String teacherDesignation;
    private String teacherEmail;
    private String teacherPhone;

    public TeacherConstructor() {}

    public TeacherConstructor(String teacherId, String teacherName, String teacherDesignation, String teacherEmail, String teacherPhone) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherDesignation = teacherDesignation;
        this.teacherEmail = teacherEmail;
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherDesignation() {
        return teacherDesignation;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }
}
