package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import DAO_Interfaces.EmployeeDAO;
import models.Employee;
import models.EmployeeParameter;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public List<Employee> getAllEmployees(HttpSession session) {
		logger.info("fetching list of all employees ");

		int adminId = (int) session.getAttribute("adminId");
		String query = "SELECT e FROM Employee e WHERE e.emplHrEmplId = :adminId";
		return entityManager.createQuery(query, Employee.class).setParameter("adminId", adminId).getResultList();
	}

	@Override
	@Transactional
	public Employee getEmployeeById(int id) {
		logger.info("fetching an employee by id ");

		return entityManager.find(Employee.class, id);
	}

	@Override
	@Transactional
	public void insertEmployee(Employee employee) {
		logger.info("Inserted employee ");

		entityManager.persist(employee);
	}

	@Override
	@Transactional
	public void updateEmployeeStatus(int id, String newStatus) {
		logger.info("Updatiing an employee status");

		Employee employee = entityManager.find(Employee.class, id);
		employee.setEmpl_status(newStatus);
		entityManager.merge(employee);
	}

	@Override
	@Transactional
	public void updateEmployee(Employee employee) {
		logger.info("Updating an employee details");

		entityManager.merge(employee);
	}

	@Override
	@Transactional
	public List<EmployeeParameter> getEmployeeParametersById(Integer employeeId) {
		logger.info("Retrieve given employee parameters ");

		TypedQuery<EmployeeParameter> parameterQuery = entityManager.createQuery(
				"SELECT p FROM EmployeeParameter p WHERE p.employeeId = :employeeId", EmployeeParameter.class);
		parameterQuery.setParameter("employeeId", employeeId);
		List<EmployeeParameter> parameters = parameterQuery.getResultList();

		return parameters;
	}

	@Override
	@Transactional
	public List<Employee> getEmployeesByHRAndManager(int employeeId) {
		logger.info("fetching employees by hr and manager ");

		String query = "SELECT e FROM Employee e "
				+ "WHERE e.emplHrEmplId = :employeeId OR e.emplRmanagerEmplId = :employeeId";

		return entityManager.createQuery(query, Employee.class).setParameter("employeeId", employeeId).getResultList();
	}

	@Override
	@Transactional
	public Employee getEmployee(int employeeId) {
		logger.info(" Retrieving an employee by id");

		return entityManager.find(Employee.class, employeeId);
	}
}
