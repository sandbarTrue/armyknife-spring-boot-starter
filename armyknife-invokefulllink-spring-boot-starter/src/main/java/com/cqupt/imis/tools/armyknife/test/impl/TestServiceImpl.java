package com.cqupt.imis.tools.armyknife.test.impl;

import com.cqupt.imis.tools.armyknife.test.TestData;
import com.cqupt.imis.tools.armyknife.test.TestService;
import org.springframework.stereotype.Service;


/**
 * @author zhoujun
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public TestData getTestData(Long var1, String va2) {
        TestData testData=new TestData();
        testData.setId(1);
        testData.setName("zhoujun");
        return testData;
    }
}
