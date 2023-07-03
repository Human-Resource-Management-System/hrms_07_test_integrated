package test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.EmployeeDAOImpl;
import models.Employee;
import models.EmployeeParameter;

public class EmployeeDAOImplTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private TypedQuery<Employee> query;

	@Mock
	private TypedQuery<EmployeeParameter> parameterQuery;

	@Mock
	private HttpSession session;

	private EmployeeDAOImpl employeeDAO;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
		employeeDAO = new EmployeeDAOImpl();
		employeeDAO.setEntityManager(entityManager);
	}

	@Test
	public void testGetAllEmployees() {
		// Mock the session attribute
		int adminId = 1;
		Mockito.when(session.getAttribute("adminId")).thenReturn(adminId);

		// Create a list of mock employees
		List<Employee> expectedEmployees = new ArrayList<>();
		expectedEmployees.add(new Employee());
		expectedEmployees.add(new Employee());

		// Mock the behavior of the EntityManager and TypedQuery
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Employee.class))).thenReturn(query);
		Mockito.when(query.setParameter(Mockito.anyString(), Mockito.eq(adminId))).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expectedEmployees);

		// Call the method under test
		List<Employee> employees = employeeDAO.getAllEmployees(session);

		// Assert the result
		Assert.assertEquals(employees, expectedEmployees);
	}

	@Test
	public void testGetEmployeeById() {
		// Create a mock employee
		int employeeId = 1;
		Employee expectedEmployee = new Employee();
		Mockito.when(entityManager.find(Employee.class, employeeId)).thenReturn(expectedEmployee);

		// Call the method under test
		Employee employee = employeeDAO.getEmployeeById(employeeId);

		// Assert the result
		Assert.assertEquals(employee, expectedEmployee);
	}

	@Test
	public void testInsertEmployee() {
		// Create a mock employee
		Employee employee = new Employee();

		// Call the method under test
		employeeDAO.insertEmployee(employee);

		// Verify the EntityManager method was called
		Mockito.verify(entityManager).persist(employee);
	}

	@Test
	public void testUpdateEmployeeStatus() {
		// Create a mock employee
		int employeeId = 1;
		String newStatus = "Active";
		Employee employee = new Employee();
		employee.setEmpl_status("Inactive");

		// Mock the behavior of the EntityManager
		Mockito.when(entityManager.find(Employee.class, employeeId)).thenReturn(employee);

		// Call the method under test
		employeeDAO.updateEmployeeStatus(employeeId, newStatus);

		// Verify the EntityManager methods were called and the employee status was updated
		Mockito.verify(entityManager).find(Employee.class, employeeId);
		Mockito.verify(entityManager).merge(employee);
		Assert.assertEquals(employee.getEmpl_status(), newStatus);
	}

	@Test
	public void testUpdateEmployee() {
		// Create a mock employee
		Employee employee = new Employee();

		// Mock the behavior of the EntityManager
		Mockito.when(entityManager.merge(employee)).thenReturn(employee);

		// Call the method under test
		employeeDAO.updateEmployee(employee);

		// Verify that the merge method was called on the EntityManager with the employee object
		Mockito.verify(entityManager, Mockito.times(1)).merge(employee);
	}

	@Test
	public void testGetEmployeeParametersById() {
		// Create a mock employee ID
		int employeeId = 1;

		// Create a list of mock employee parameters
		List<EmployeeParameter> expectedParameters = new ArrayList<>();
		expectedParameters.add(new EmployeeParameter());
		expectedParameters.add(new EmployeeParameter());

		// Mock the behavior of the EntityManager and TypedQuery
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(EmployeeParameter.class)))
				.thenReturn(parameterQuery);
		Mockito.when(parameterQuery.setParameter(Mockito.anyString(), Mockito.eq(employeeId)))
				.thenReturn(parameterQuery);
		Mockito.when(parameterQuery.getResultList()).thenReturn(expectedParameters);

		// Call the method under test
		List<EmployeeParameter> parameters = employeeDAO.getEmployeeParametersById(employeeId);

		// Assert the result
		Assert.assertEquals(parameters, expectedParameters);
	}

	@Test
	public void testGetEmployeesByHRAndManager() {
		// Create a mock employee ID
		int employeeId = 1;

		// Create a list of mock employees
		List<Employee> expectedEmployees = new ArrayList<>();
		expectedEmployees.add(new Employee());
		expectedEmployees.add(new Employee());

		// Mock the behavior of the EntityManager and TypedQuery
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Employee.class))).thenReturn(query);
		Mockito.when(query.setParameter(Mockito.anyString(), Mockito.eq(employeeId))).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(expectedEmployees);

		// Call the method under test
		List<Employee> employees = employeeDAO.getEmployeesByHRAndManager(employeeId);

		// Assert the result
		Assert.assertEquals(employees, expectedEmployees);
	}

	@Test
	public void testGetEmployee() {
		// Create a mock employee
		int employeeId = 1;
		Employee expectedEmployee = new Employee();
		Mockito.when(entityManager.find(Employee.class, employeeId)).thenReturn(expectedEmployee);

		// Call the method under test
		Employee employee = employeeDAO.getEmployee(employeeId);

		// Assert the result
		Assert.assertEquals(employee, expectedEmployee);
	}
}
