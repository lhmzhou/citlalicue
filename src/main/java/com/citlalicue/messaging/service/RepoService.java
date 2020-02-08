package com.citlalicue.messaging.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citlalicue.messaging.exception.ResourceNotFoundException;
import com.citlalicue.messaging.model.Employee;
import com.citlalicue.messaging.model.EmployeeAddressInfo;
import com.citlalicue.messaging.model.Projects;
import com.citlalicue.messaging.model.Skills;
import com.citlalicue.messaging.msg.EmployeeUpdateMsg;
import com.citlalicue.messaging.repository.EmployeeAddressRepository;
import com.citlalicue.messaging.repository.EmployeeRepository;
import com.citlalicue.messaging.repository.ProjectsRepository;
import com.citlalicue.messaging.repository.SkillsRepository;

@Service
public class RepoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepoService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeAddressRepository employeeAddressRepository;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    private SimpleDateFormat date = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

    /**
     * applies updates to Employee and Address, inserts new skill information, insert new project
     * information for the employee
     * 
     * @param employeeUpdate
     */
    @Transactional
    public void applyMessageUpdate(EmployeeUpdateMsg employeeUpdate) {

        LOGGER.debug("Applying Message...");
        try {

            if (!employeeRepository.existsById(employeeUpdate.getEmployeeId())) {
                throw new ResourceNotFoundException("EmployeeId " + employeeUpdate.getEmployeeId() + " not found.");
            } else { // if POST creation is successful, return 201 created and response body
                /** Update employee salary, work dept, and job **/
                Employee employee = employeeRepository.getOne(employeeUpdate.getEmployeeId());
                boolean updated = false;
                if (employeeUpdate.getSalary() != null) {
                    employee.setSalary(employeeUpdate.getSalary());
                    updated = true;
                }
                if (employeeUpdate.getWorkDepartment() != null) {
                    employee.setWorkDepartment(employeeUpdate.getWorkDepartment());
                    updated = true;
                }
                if (employeeUpdate.getJob() != null) {
                    employee.setJob(employeeUpdate.getJob());
                    updated = true;
                }
                if (updated) {
                    employeeRepository.save(employee);
                }

                /** Update employee address **/
                if (employeeUpdate.getAddress() != null) {
                    Optional<EmployeeAddressInfo> addressOption = employeeAddressRepository.findByEmployeeId(employeeUpdate.getEmployeeId());
                if (addressOption.isPresent()) {
                    EmployeeAddressInfo address = addressOption.get();
                    address.setPrimaryAddress(employeeUpdate.getAddress());
                    employeeAddressRepository.save(address);
                } else {
                    throw new ResourceNotFoundException(
                        "Oops, address for EmployeeId " + employeeUpdate.getEmployeeId() + " was not found");
                }

                /** Insert project **/
                if (employeeUpdate.getProject() != null) {
                    Projects project = new Projects();
                    project.setProject(employeeUpdate.getProject());
                    project.setEmployee(employee);
                    projectsRepository.save(project);
                }

                /** Insert skills **/
                if (employeeUpdate.getSkillName() != null) {
                    Skills skill = new Skills();
                    skill.setSkillName(employeeUpdate.getSkillName());
                    skill.setEmployee(employee);
                    skillsRepository.save(skill);
                }
            }

            LOGGER.debug("Database updated successfully for employee {} ", employeeUpdate.getEmployeeId());
       
        } catch (ResourceNotFoundException e) { 
            LOGGER.error("An exception occurred while applying {}", employeeUpdate, e);
        }

    }

    /**
     * set employee demographics
     * 
     * @param employeeInformation
     * @throws ParseException
     * @return employee
     */
    private Employee setEmployee(EmployeeUpdateMsg employeeInformation) throws ParseException {
        try {
            Employee employee = new Employee();
            employee.setFirstname(employeeInformation.getFirstName());
            employee.setLastName(employeeInformation.getLastName());
            employee.setWorkDepartment(employeeInformation.getWorkDepartment());
            employee.setJob(employeeInformation.getJob());
            employee.setGender(employeeInformation.getGender());
            employee.setHireDate(date.parse(employeeInformation.getHireDate()));
            employee.setBirthDate(date.parse(employeeInformation.getBirthDate()));
            employee.setSalary(employeeInformation.getSalary());

            return employee;
        } catch (ParseException e) {
            e.printStackTrace(); 
        }
    }

    /**
     * inserts new employee information along with skills, address, and projects
     * 
     * @param employeeUpdate
     * @return employeeID
     * @throws ParseException
     */
    @Transactional
    public long insertEmployee(EmployeeUpdateMsg employeeUpdate) throws ParseException {

        LOGGER.debug("Inserting employee");
        try {
            /** Add employee **/
            Employee employee = employeeRepository.save(setEmployee(employeeUpdate));

            /** Add employee Address **/
            EmployeeAddressInfo address = new EmployeeAddressInfo();
            address.setPrimaryAddress(employeeUpdate.getAddress());
            address.setEmployee(employee);
            employeeAddressRepository.save(address);

            /** Insert project **/
            Projects project = new Projects();
            project.setProject(employeeUpdate.getProject());
            project.setEmployee(employee);
            projectsRepository.save(project);

            /** Add skills **/
            Skills skill = new Skills();
            skill.setSkillName(employeeUpdate.getSkillName());
            skill.setEmployee(employee);
            skillsRepository.save(skill);

            return employee.getId();
        } catch (ParseException e) {
            e.printStackTrace();  
        }
    }

    /**
     * returns all employees from the database
     * 
     * @return database employees
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * returns employee from the database
     * @param employeeId
     * @return database employees
     */
    public Optional<Employee> getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    /**
     * delete all employees from the database
     */
    public void deleteAllEmployees(){
        employeeRepository.deleteAll();
    }
  
}