package io.nightovis.ztp.repository;

import io.nightovis.ztp.model.mongo.ProductMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductMongo, String> {
}
