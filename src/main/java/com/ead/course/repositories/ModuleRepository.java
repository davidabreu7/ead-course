package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends MongoRepository<ModuleModel, String>, CustomModuleRepository {
    

}
