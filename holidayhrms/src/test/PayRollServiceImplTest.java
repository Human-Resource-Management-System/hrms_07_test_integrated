package test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import service.PayRollServiceImpl;
import service_interfaces.PayRollService;

public class PayRollServiceImplTest {

	private PayRollService payRollService;

	@BeforeClass
	public void setUp() {
		payRollService = new PayRollServiceImpl();
	}

	@Test
	public void testCalculateBasicPay() {
		double basicPay = 22500;
		double result = payRollService.calculateBasicPay(basicPay);
		Assert.assertEquals(result, basicPay);
	}

	@Test
	public void testCalculateHRA() {
		double fixedPay = 18000;
		double expectedHRA = fixedPay * 0.4;
		double result = payRollService.calculateHRA(fixedPay);
		Assert.assertEquals(result, expectedHRA);
	}

	@Test
	public void testCalculateDA() {
		double fixedPay = 18000;
		double expectedDA = fixedPay * 0.3;
		double result = payRollService.calculateDA(fixedPay);
		Assert.assertEquals(result, expectedDA);
	}

	@Test
	public void testCalculateTA() {
		double fixedPay = 18000;
		double expectedTA = fixedPay * 0.3;
		double result = payRollService.calculateTA(fixedPay);
		Assert.assertEquals(result, expectedTA);
	}

	@Test
	public void testCalculateAdditions() {
		double additionalPay = 4500;
		double result = payRollService.calculateAdditions(additionalPay);
		Assert.assertEquals(result, additionalPay);
	}

	@Test
	public void testCalculatePF() {
		double ctc = 600000;
		double expectedPF = (0.6 * ctc) / 12;
		double result = payRollService.calculatePF(ctc);
		Assert.assertEquals(result, expectedPF);
	}

	@Test
	public void testCalculateESI() {
		double ctc = 600000;
		double expectedESI = (0.15 * ctc) / 12;
		double result = payRollService.calculateESI(ctc);
		Assert.assertEquals(result, expectedESI);
	}

	@Test
	public void testCalculateGratuity() {
		double ctc = 600000;
		double expectedGratuity = (0.25 * ctc) / 12;
		double result = payRollService.calculateGratuity(ctc);
		Assert.assertEquals(result, expectedGratuity);
	}

	@Test
	public void testCalculateTax() {
		double ctc = 600000;
		double expectedTax = ((0.05 * (ctc - 500000)) / 12);
		double result = payRollService.calculateTax(ctc);
		Assert.assertEquals(result, expectedTax);
	}

	@Test
	public void testCalculateDeductions() {
		// Set up the required variables and dependencies
		double ctc = 600000;
		double expectedDeductions = ((0.25 * ctc) / 12) + ((0.15 * ctc) / 12) + ((0.6 * ctc) / 12)
				+ ((0.05 * (ctc - 500000)) / 12) + 200;
		double result = payRollService.calculateDeductions() + ((0.25 * ctc) / 12) + ((0.15 * ctc) / 12)
				+ ((0.6 * ctc) / 12) + ((0.05 * (ctc - 500000)) / 12);
		Assert.assertEquals(result, expectedDeductions);
	}

	@Test
	public void testCalculateTotalPay() {
		double fixedPay = 18000;
		double expectedTotalPay = 22500 + (fixedPay * 0.4) + (fixedPay * 0.3) + (fixedPay * 0.3);
		double result = payRollService.calculateTotalPay();
		Assert.assertEquals(result, expectedTotalPay);
	}

	@Test
	public void testCalculateNetPay() {
		// Set up the required variables and dependencies
		double fixedPay = 18000;
		double ctc = 600000;
		double expectedNetPay = (22500 + (fixedPay * 0.4) + (fixedPay * 0.3) + (fixedPay * 0.3) + 4500)
				- ((0.25 * ctc) / 12) + ((0.15 * ctc) / 12) + ((0.6 * ctc) / 12) + ((0.05 * (ctc - 500000)) / 12) + 200;
		double result = payRollService.calculateNetPay() + 200
				+ (22500 + (fixedPay * 0.4) + (fixedPay * 0.3) + (fixedPay * 0.3)) - ((0.25 * ctc) / 12)
				+ ((0.15 * ctc) / 12) + ((0.6 * ctc) / 12) + ((0.05 * (ctc - 500000)) / 12) + 200;
		Assert.assertEquals(result, expectedNetPay);
	}

}
