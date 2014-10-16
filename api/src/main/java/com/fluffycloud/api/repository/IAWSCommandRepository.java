package com.fluffycloud.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fluffycloud.aws.entity.Command;

public interface IAWSCommandRepository extends MongoRepository<Command, String>
{

}
