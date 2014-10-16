package com.fluffycloud.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fluffycloud.aws.response.entity.BaseResponse;

public interface IAWSResponseRepository extends MongoRepository<BaseResponse, String>
{
//TODO generate Ids for responses also.
}
