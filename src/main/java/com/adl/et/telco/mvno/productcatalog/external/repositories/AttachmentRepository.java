package com.adl.et.telco.mvno.productcatalog.external.repositories;

import com.adl.et.telco.mvno.productcatalog.domain.entities.AttachmentRefOrValueDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class AttachmentRepository extends AbstractSubResourceRepository<AttachmentRefOrValueDocument> {

    protected AttachmentRepository(MongoTemplate mongoTemplate) {

        super(mongoTemplate, AttachmentRefOrValueDocument.class);
    }
}
