package ir.maktabsharif.models;

import ir.maktabsharif.models.enums.Degree;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Teacher.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends Person{

    public static final String TABLE_NAME = "teachers";
    public static final String TEACHER_CODE_COLUMN = "teacher_code";
    public static final String QUALIFICATION_COLUMN = "qualification";
    public static final String DEGREE_COLUMN = "degree";
    public static final String MONTHLY_SALARY_COLUMN = "monthly_salary";

    @Column(name = TEACHER_CODE_COLUMN, unique = true, nullable = false)
    private String teacherCode;

    @Column(name = QUALIFICATION_COLUMN)
    private String qualification;

    @Enumerated(EnumType.STRING)
    @Column(name = DEGREE_COLUMN)
    private Degree degree;

    @Column(name = MONTHLY_SALARY_COLUMN)
    private Double monthlySalary;
}
