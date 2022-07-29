package com.example.demo1.service.impl;

import com.example.demo1.entity.TestUniqueColumn;
import com.example.demo1.repo.entityrepo.TestUniqueRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UniqueService {

    private TestUniqueRepo testUniqueRepo;

    @Autowired
    public void setTestUniqueRepo(TestUniqueRepo testUniqueRepo) {
        this.testUniqueRepo = testUniqueRepo;
    }

    @Transactional(rollbackFor = Exception.class)
    public String save() {
//        try {
//            TestUniqueColumn testUniqueColumn1 = new TestUniqueColumn();
//            testUniqueColumn1.setUsername("aaa");
//            testUniqueColumn1.setCouplename("bbb");
//            testUniqueColumn1.setAge(18);
//
//            TestUniqueColumn testUniqueColumn3 = new TestUniqueColumn();
//            testUniqueColumn3.setUsername("ddd");
//            testUniqueColumn3.setCouplename("uuu");
//            testUniqueColumn3.setAge(18);
//            List<TestUniqueColumn> list = new ArrayList<>();
//            list.add(testUniqueColumn1);
//            list.add(testUniqueColumn3);
//            testUniqueRepo.saveAll(list);
//            TestUniqueColumn testUniqueColumn2 = new TestUniqueColumn();
//            testUniqueColumn2.setUsername("ccc");
//            testUniqueColumn2.setCouplename("bbb");
//            testUniqueColumn2.setAge(18);
//            testUniqueRepo.save(testUniqueColumn2);
        Optional<TestUniqueColumn> tucOpt = testUniqueRepo.findById(1);
        TestUniqueColumn testUniqueColumn = tucOpt.get();
        testUniqueColumn.setUsername("ggg");
        System.out.println(testUniqueRepo.save(testUniqueColumn));
//        throw new RuntimeException("GG");

//        } catch (Exception e) {
//            log.error("no unique{}" ,e);
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return "false";
//        }
        return "";
    }



}
