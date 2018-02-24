package com.komak.kero.keroapi.event;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EventRepository extends MongoRepository<Event, String> {

}
