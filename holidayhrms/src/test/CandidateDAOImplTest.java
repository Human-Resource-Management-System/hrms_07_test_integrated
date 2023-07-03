package test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import DAO.CandidateDAOImpl;
import models.Candidate;

public class CandidateDAOImplTest {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private CandidateDAOImpl candidateDAO;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveCandidate() {
		Candidate candidate = new Candidate();
		doNothing().when(entityManager).persist(candidate);

		candidateDAO.saveCandidate(candidate);

		verify(entityManager, times(1)).persist(candidate);
	}

	@Test
	public void testGetCandidateById() {
		int candidateId = 1;
		Candidate expectedCandidate = new Candidate();
		when(entityManager.find(Candidate.class, candidateId)).thenReturn(expectedCandidate);

		Candidate candidate = candidateDAO.getCandidateById(candidateId);

		verify(entityManager, times(1)).find(Candidate.class, candidateId);
		assertEquals(candidate, expectedCandidate);
	}

	@Test
	public void testGetAllCandidates() {
		List<Candidate> expectedCandidates = new ArrayList<>();
		expectedCandidates.add(new Candidate());
		expectedCandidates.add(new Candidate());
		TypedQuery<Candidate> query = mock(TypedQuery.class);
		when(entityManager.createQuery("SELECT c FROM Candidate c", Candidate.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(expectedCandidates);

		List<Candidate> candidates = candidateDAO.getAllCandidates();

		verify(entityManager, times(1)).createQuery("SELECT c FROM Candidate c", Candidate.class);
		verify(query, times(1)).getResultList();
		assertEquals(candidates, expectedCandidates);
	}
}
