package com.tuyendv.web.backend.api.util.system;

import com.tuyendv.web.backend.api.repository.system.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author khoinm
 * generator sequence id String
 * prefix(DOCR), tableName(DOC_ROOM) => DOCR000000000000062
 **/

@Component
public class SequenceGenerator {

    @Autowired
    private SequenceRepository sequenceRepository;

    public String getNextSequence(String prefix, String tableName) {
        return sequenceRepository.getSequence(prefix, tableName);
    }

}
