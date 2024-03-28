package com.tutorial.rediscache.repository;

import com.tutorial.rediscache.dao.entity.party.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.HashMap;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, Neo4jRepository<User, Long>, QueryByExampleExecutor<User> {

    <T> T findById(Long id, Class<T> type);
    @Query(value="MATCH(p:User {status: 'ACTIVE'}) WHERE ID(p)=$0 " +
            "MATCH(p)-[:PREFERENCES]-(pref)-[:PRIVACY]-(priv)" +
            "OPTIONAL MATCH(p)-[:HAS_LINK]-(pl)-[:PARTY_LINK]-(link) " +
            "WITH p, pref, priv, pl, collect(link{.*, id:ID(link)}) AS links " +
            "RETURN p{.*, id:ID(p), " +
            "hasNotification: EXISTS((p)-[:FOLLOWED {userId: {1}, notification: 'ENABLED'}]-())," +
            "hasFollowed: EXISTS((p)-[:FOLLOWED {userId: $1}]-())," +
            "noOfFollowers:size((p)<-[:FOLLOWED]-()), " +
            "noOfFollowings:size((p)-[:FOLLOWED]->()), " +
            "preferences:pref{.*, id:ID(pref), privacy: priv{.*, id:ID(priv)}}, " +
            "links: links " +
            "} ")
    Optional<Object> findById(Long id, Long viewerId);


    Optional<User> findByTagname(String tagname);

    @Query("MATCH(p:User) WHERE ID(p)=$1 " +
            "MATCH (u:User {status: 'ACTIVE', tagname: $0}) " +
            "MATCH (u)-[:PREFERENCES]-(pref)-[:PRIVACY]-(priv) " +
            "OPTIONAL MATCH(u)-[:HAS_LINK]-(pl)-[:PARTY_LINK]-(link) " +
            "WITH u, p, pref, priv, pl, collect(link{.*, id:ID(link)}) AS links " +
            "RETURN u{.*, id: ID(u), " +
            "   hasFollowed: EXISTS((p)-[:FOLLOWED]->(u)), " +
            "   preferences:pref{.*, id:ID(pref), privacy: priv{.*, id:ID(priv)}}, " +
            "   links: links" +

            "}")
    Optional<HashMap<String, Object>> findByTagname(String tagname, Long viewerId);

    @Query(value="MATCH(p:User) WHERE ID(p)={0} " +
            "MATCH(p)-[:PREFERENCES]-(pref)-[:PRIVACY]-(priv)" +
            "MATCH(p)-[:ADDRESS]-(address {isPrimary: true}) " +
            "MATCH(p)-[:PARTY_CONTACT]-(partyContact)-[:HAS_CONTACT]-(contact) " +
            "OPTIONAL MATCH(p)-[:HAS_LINK]-(pl)-[:PARTY_LINK]-(link) " +
            "RETURN p{.*, id:ID(p), " +
            "noOfFollowers:size((p)<-[:FOLLOWED]-()), " +
            "noOfFollowings:size((p)-[:FOLLOWED]->()), " +
            "primaryAddress:address{.*, id:ID(address)}, " +
            "partyContact: partyContact{.*, id:ID(partyContact), contacts: collect(contact{.*, id:ID(contact)})},"  +
            "preferences:pref{.*, id:ID(pref), privacy: priv{.*, id:ID(priv)}}, " +
            "partyLink: pl{.*, id:ID(pl), links: collect(link{.*, id:ID(link)})}" +
            "} ")
    Object getUserDetail(Long id);

}
