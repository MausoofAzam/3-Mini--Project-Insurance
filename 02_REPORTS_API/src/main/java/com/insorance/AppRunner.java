package com.insorance;

import com.insorance.entity.EligibilityDetails;
import com.insorance.repository.EligibilityDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class AppRunner implements ApplicationRunner {

    @Autowired
    private EligibilityDetailsRepo repo;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        EligibilityDetails ed1 = new EligibilityDetails();
        ed1.setEligId(1);
        ed1.setName("Azam");
        ed1.setMobile(78477787L);
        ed1.setGender('M');
        ed1.setSsn(778564777L);
        ed1.setPlanName("CCAP");
        ed1.setPlanStatus("Approved");
        repo.save(ed1);

        EligibilityDetails ed2 = new EligibilityDetails();
        ed2.setEligId(2);
        ed2.setName("Nahid");
        ed2.setMobile(78476587L);
        ed2.setGender('F');
        ed2.setSsn(565564777L);
        ed2.setPlanName("Medicate");
        ed2.setPlanStatus("Approved");
        repo.save(ed2);

        EligibilityDetails ed3 = new EligibilityDetails();
        ed3.setEligId(3);
        ed3.setName("Akhtar");
        ed3.setMobile(78277787L);
        ed3.setGender('F');
        ed3.setSsn(778532777L);
        ed3.setPlanName("Medicare");
        ed3.setPlanStatus("Rejected");
        repo.save(ed3);

        EligibilityDetails ed4 = new EligibilityDetails();
        ed4.setEligId(4);
        ed4.setName("Lubna");
        ed4.setMobile(78242487L);
        ed4.setGender('F');
        ed4.setSsn(771232777L);
        ed4.setPlanName("CCAP");
        ed4.setPlanStatus("Pending");
        repo.save(ed4);

    }
}
