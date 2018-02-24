package com.komak.kero.keroapi.image;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ImageRepository extends MongoRepository<Image, String> {

}
