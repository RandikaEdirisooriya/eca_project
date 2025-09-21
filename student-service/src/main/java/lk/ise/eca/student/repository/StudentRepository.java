package lk.ise.eca.student.repository;

import lk.ise.eca.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface StudentRepository extends JpaRepository<Student, String> {
}