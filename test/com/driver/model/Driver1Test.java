package com.driver.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

class Driver1Test {

	@Test
	void test() throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String lastdate="2020-01-04";
		Date l=sdf.parse(lastdate);
		
		Testing d=new Testing();
		d.checkEligibility(23, "New Jersey", "Clifton", 4, l, 4, "yes", l);
		
	}

}