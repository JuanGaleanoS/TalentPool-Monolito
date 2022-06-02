package com.talentpool.monolito.util;

import com.talentpool.monolito.model.MongoSequences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        // get the sequence number
        final Query q = new Query(Criteria.where("id").is(seqName));
        // increment the sequence number by 1
        // "seq" should match the attribute value specified in DbSequence.java class.
        final Update u = new Update().inc("seq", 1);
        // modify in document
        final MongoSequences counter = mongoOperations.findAndModify(q, u,
                FindAndModifyOptions.options().returnNew(true).upsert(true), MongoSequences.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
