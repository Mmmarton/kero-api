package com.komak.kero.keroapi.image;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface ImageRepository extends MongoRepository<Image, String> {

  @Query("{ 'eventId' : ?0}")
  List<Image> findAllByEventId(String eventId);
}
